package net.bottomtextdanny.danny_expannny.capabilities.chunk;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbRoot;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbSection;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSerializer;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCrumbsModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class ChunkCrumbModule extends CapabilityModule<LevelChunk, ChunkCapability> {
    public static final String CRUMB_TAG = "crumbs";
    public static final String CRUMB_ROOT_TAG = "root";
    private boolean loaded;
    @Nullable
    private LinkedList<CompoundTag> serializedCrumbs = Lists.newLinkedList();
    private final Int2ObjectMap<Crumb> crumbsById = new Int2ObjectOpenHashMap<>();
    private LevelCrumbsModule levelCrumbsModule;

    public ChunkCrumbModule(ChunkCapability capability) {
        super("crumbs", capability);
        this.loaded = true;
    }

    public void load(LevelCrumbsModule levelCrumbsModule) {

        try {
            this.levelCrumbsModule = levelCrumbsModule;
            ChunkPos pos = getHolder().getPos();

            for (CompoundTag crumbTag : this.serializedCrumbs) {
                CrumbRoot<?> root = CrumbRoot.getByKey(new ResourceLocation(crumbTag.getString(CRUMB_ROOT_TAG)));
                Crumb crumb = root.create(levelCrumbsModule.getHolder());
                this.levelCrumbsModule.addCrumb(crumb);
                crumb.setChunk(getHolder());
                ((CrumbSerializer) crumb).read(crumbTag);
                addCrumb(crumb);
            }
            this.serializedCrumbs = null;
            this.loaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void unload() {
        if (this.loaded) {
            this.levelCrumbsModule = CapabilityHelper.get(getHolder().getLevel(), LevelCapability.CAPABILITY).getCrumbsModule();
            ChunkPos pos = getHolder().getPos();
            for (int i = 0; i < CrumbSection.CHUNK_DISTRIBUTION; i++) {
                this.levelCrumbsModule.removeCrumbSection(CrumbSection.computeChunkSectionSerial(pos.x, pos.z, i));
            }

            for (Map.Entry<Integer, Crumb> entry : this.crumbsById.entrySet()) {
                Crumb crumb = entry.getValue();
                this.levelCrumbsModule.removeCrumb(crumb);
                removeCrumb(crumb);
            }
            this.loaded = false;
        }
    }

    public void saveData(CompoundTag nbt) {
        if (true) {
            ListTag list = new ListTag();
            this.crumbsById.forEach((id, crumb) -> {
                if (crumb instanceof CrumbSerializer serializer) {
                    System.out.println("poopyFART true");
                    CompoundTag crumbTag = new CompoundTag();
                    crumbTag.putString(CRUMB_ROOT_TAG, crumb.getRoot().getKey().toString());
                    crumbTag = serializer.write(crumbTag);
                    list.add(crumbTag);
                } else {
                    System.out.println("poopyFART false");
                }
            });
            nbt.put(CRUMB_TAG, list);
        }
    }

    public void loadData(CompoundTag nbt) {
        ListTag list = nbt.getList(CRUMB_TAG, 10);

        if (list != null) {
            Iterator<Tag> reader = list.iterator();
            while (reader.hasNext()) {
                if (reader.next() instanceof CompoundTag crumbTag) {
                    this.serializedCrumbs.add(crumbTag);
                }
            }
        }
    }

    public void addCrumb(Crumb crumb) {
        if (this.loaded) this.getHolder().setUnsaved(true);
        this.crumbsById.put(crumb.getTransientId(), crumb);
    }

    public void removeCrumb(Crumb crumb) {
        if (this.loaded) this.getHolder().setUnsaved(true);
        this.crumbsById.remove(crumb.getTransientId());
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {


    }
}

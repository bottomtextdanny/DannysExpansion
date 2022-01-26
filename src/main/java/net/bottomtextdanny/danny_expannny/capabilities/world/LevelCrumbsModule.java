package net.bottomtextdanny.danny_expannny.capabilities.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.bottomtextdanny.dannys_expansion.common.crumbs.Crumb;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbMultiMap;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbRoot;
import net.bottomtextdanny.dannys_expansion.common.crumbs.CrumbSection;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSearchable;
import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbTicker;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelCrumbsModule extends CapabilityModule<Level, LevelCapability> {
    private static final AtomicInteger CRUMB_ID_COUNT = new AtomicInteger();
    private final Int2ObjectMap<Crumb> crumbsById = new Int2ObjectLinkedOpenHashMap<>();
    private final CrumbMultiMap bulks = new CrumbMultiMap(CrumbRoot.specialRootIterator());
    private final Map<Long, CrumbSection> lookupSections = Maps.newIdentityHashMap();
    private final LinkedList<Crumb> deferredRemovalList = Lists.newLinkedList();

    public LevelCrumbsModule(LevelCapability capability) {
        super("crumbs", capability);
    }

    public void tick() {
        Iterator<Crumb> removalIterator = this.deferredRemovalList.iterator();
        while (removalIterator.hasNext()) {
            Crumb toBeRemoved = removalIterator.next();

            if (toBeRemoved.getChunkModule() != null) {
                toBeRemoved.getChunkModule().removeCrumb(toBeRemoved);
            } else if (toBeRemoved.getChunk() != null) {
                toBeRemoved.updateChunkModule();
                toBeRemoved.getChunkModule().removeCrumb(toBeRemoved);
            }

            removeCrumb(toBeRemoved);
            removalIterator.remove();
        }
        this.bulks.getRootCrumbs(CrumbRoot.TICKABLE).forEach(CrumbTicker::tickCrumb);
    }

    public void addToSection(long serial, Crumb crumb) {
        CrumbSection section = getOrCreateSection(serial);
        if (section != null) {
            section.getBulks().add(crumb);
        }
    }

    public void removeFromSection(long serial, Crumb crumb) {
        if (this.lookupSections.containsKey(serial)) {
            this.lookupSections.get(serial).getBulks().remove(crumb);
        }
    }

    @Nullable
    public CrumbSection getOrCreateSection(Crumb crumb) {
        if (crumb.getChunk() != null) {
            long serial = CrumbSection.computeChunkSectionSerial(crumb.getBlockPosition());
            if (this.lookupSections.containsKey(serial)) {
                return this.lookupSections.get(serial);
            } else {
                CrumbSection newSection = new CrumbSection();
                this.lookupSections.put(serial, newSection);
                return newSection;
            }
        }
        return null;
    }

    public CrumbSection getOrCreateSection(long serial) {
        if (this.lookupSections.containsKey(serial)) {
            return this.lookupSections.get(serial);
        } else {
            CrumbSection newSection = new CrumbSection();
            this.lookupSections.put(serial, newSection);
            return newSection;
        }
    }

    public void putCrumbSection(long serial, CrumbSection section) {
        this.lookupSections.put(serial, section);
    }

    public void removeCrumbSection(long serial) {
        this.lookupSections.remove(serial);
    }

    public int getIdForCrumb(Crumb crumb) {
        return CRUMB_ID_COUNT.incrementAndGet();
    }

    public void addCrumb(Crumb crumb) {
        this.bulks.add(crumb);
    }

    public void removeCrumb(Crumb crumb) {

        if (crumb instanceof CrumbSearchable searchable) {
            this.lookupSections.get(searchable.getChunkSection()).getBulks().remove(crumb);
        }
        this.crumbsById.remove(crumb.getTransientId());
        this.bulks.remove(crumb);
    }

    public void deferRemoval(Crumb crumb) {
        this.deferredRemovalList.add(crumb);
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {}

    @Override
    public void deserializeNBT(CompoundTag nbt) {}
}

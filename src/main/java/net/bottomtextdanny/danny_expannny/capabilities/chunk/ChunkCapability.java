package net.bottomtextdanny.danny_expannny.capabilities.chunk;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityWrap;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.loading.FMLLoader;

public class ChunkCapability extends CapabilityWrap<ChunkCapability, LevelChunk> {
    public static Capability<ChunkCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    private ChunkCrumbModule crumbsModule;

    public ChunkCapability(LevelChunk holder) {
        super(holder);
    }

    @Override
    protected void populateModuleList(ImmutableList.Builder<CapabilityModule<LevelChunk, ChunkCapability>> moduleList) {
        if (FMLLoader.getDist().isDedicatedServer()) {
            this.crumbsModule = new ChunkCrumbModule(this);
            moduleList.add(this.crumbsModule);
        }
    }

    public ChunkCrumbModule getCrumbsModule() {
        return this.crumbsModule;
    }

    @Override
    protected Capability<ChunkCapability> getToken() {
        return CAPABILITY;
    }
}

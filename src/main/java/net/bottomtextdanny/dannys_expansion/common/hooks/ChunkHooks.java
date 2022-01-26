package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.chunk.ChunkCapability;
import net.bottomtextdanny.danny_expannny.capabilities.chunk.ChunkCrumbModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCrumbsModule;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.loading.FMLLoader;

public class ChunkHooks {

    public static void chunkLoadingHook(ChunkEvent.Load event) {
        if (FMLLoader.getDist() == Dist.DEDICATED_SERVER) {
            ChunkCrumbModule chunkCrumbsModule = CapabilityHelper.get((LevelChunk) event.getChunk(), ChunkCapability.CAPABILITY).getCrumbsModule();
            LevelCrumbsModule levelCrumbsModule = CapabilityHelper.get((Level)event.getWorld(), LevelCapability.CAPABILITY).getCrumbsModule();
            chunkCrumbsModule.load(levelCrumbsModule);
        }
    }

    public static void chunkDataLoadingHook(ChunkDataEvent.Load event) {
        if (FMLLoader.getDist() == Dist.DEDICATED_SERVER) {
            if (event.getChunk() instanceof LevelChunk levelChunk) {
                ChunkCrumbModule chunkCrumbsModule = CapabilityHelper.get(levelChunk, ChunkCapability.CAPABILITY).getCrumbsModule();
                chunkCrumbsModule.loadData(event.getData());
            }
        }
    }

    public static void chunkUnloadingHook(ChunkEvent.Unload event) {
        if (FMLLoader.getDist() == Dist.DEDICATED_SERVER) {
            ChunkCrumbModule chunkCrumbsModule = CapabilityHelper.get((LevelChunk) event.getChunk(), ChunkCapability.CAPABILITY).getCrumbsModule();
            chunkCrumbsModule.unload();
        }
    }

    public static void chunkDataUnloadingHook(ChunkDataEvent.Save event) {
        if (FMLLoader.getDist() == Dist.DEDICATED_SERVER) {
            if (event.getChunk() instanceof LevelChunk chunk) {
                ChunkCrumbModule chunkCrumbsModule = CapabilityHelper.get(chunk, ChunkCapability.CAPABILITY).getCrumbsModule();
                chunkCrumbsModule.saveData(event.getData());
            }
        }
    }
}

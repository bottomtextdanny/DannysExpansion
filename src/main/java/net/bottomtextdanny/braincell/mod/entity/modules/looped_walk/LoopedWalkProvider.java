package net.bottomtextdanny.braincell.mod.entity.modules.looped_walk;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface LoopedWalkProvider extends ModuleProvider {
    LoopedWalkProvider.LoopStepSoundPlayer LSP_DOUBLE = (entity, blockstate, lSLoop) -> {
        LoopedWalkProvider provider = (LoopedWalkProvider)entity;
        LoopedWalkModule module = provider.loopedWalkModule();

        lSLoop = DEMath.loop(lSLoop, 0.5F, 0.0F);
        if (DEMath.loop(module.prevLimbSwingLoop, 0.5F, 0.0F) > lSLoop) provider.playLoopStepSound(entity.getOnPos(), blockstate);
    };

    float hurtLoopLimbSwingFactor();

    LoopedWalkModule loopedWalkModule();

    default boolean operateWalkModule() {
        return loopedWalkModule() != null;
    }

    float getLoopWalkMultiplier();

    default LoopStepSoundPlayer lSSPlayer() {
        return LSP_DOUBLE;
    }

    void playLoopStepSound(BlockPos posBelow, BlockState blockStateBelow);

    @FunctionalInterface
    interface LoopStepSoundPlayer {
        void getLogic(LivingEntity entity, BlockState groundBlockState, float lSLoop);
    }
}

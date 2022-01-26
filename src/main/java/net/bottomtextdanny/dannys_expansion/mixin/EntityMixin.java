package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.BaseAnimatableProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At(value = "TAIL"), method = "tick", remap = false)
    public void tickHook(CallbackInfo ci) {
        if (this instanceof BaseAnimatableProvider provider) {
            if (provider.operateAnimatableModule()) {
                provider.animatableModule().tick();
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "playStepSound", remap = false, cancellable = true)
    public void cancelStepSound(BlockPos positionBelow, BlockState blockStateBelow, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LoopedWalkProvider provider) {
            if (provider.operateWalkModule()) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "playAmethystStepSound", remap = false, cancellable = true)
    public void cancelAmethystStepSound(BlockState blockStateBelow, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LoopedWalkProvider provider) {
            if (provider.operateWalkModule()) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.BEFORE), method = "saveWithoutId", remap = false)
    public void preSaveExtraHook(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        if (this instanceof BCDataManagerProvider provider) {
            provider.bcDataManager().writeTag(tag);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.BEFORE), method = "load", remap = false)
    public void preLoadExtraHook(CompoundTag tag, CallbackInfo ci) {
        if (this instanceof BCDataManagerProvider provider) {
            provider.bcDataManager().readTag(tag);
        }
    }
}

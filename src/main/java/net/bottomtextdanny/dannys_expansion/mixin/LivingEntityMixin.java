package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.LivingAnimatableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.LivingAnimatableProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.motion_util.MotionUtilProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariableProvider;
import net.bottomtextdanny.danny_expannny.network.servertoclient.MSGTrivialEntityActions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract float getSpeed();

	@Shadow public float flyingSpeed;

	public LivingEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(at = @At(value = "HEAD"), method = "getDimensions", remap = false, cancellable = true)
	public void getDimensionsHook(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
		if (this instanceof VariableProvider provider && provider.operatingVariableModule() && provider.variableModule().isUpdated()) {
			EntityDimensions formDimensions = provider.variableModule().getForm().boxSize();
			if (formDimensions != null) {
				cir.setReturnValue(provider.variableModule().getForm().boxSize());
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "tick", remap = false)
	public void tickHook(CallbackInfo ci) {
		if (this instanceof LoopedWalkProvider provider) {
			if (provider.operateWalkModule()) {
				provider.loopedWalkModule().tick();
			}
		}
	}

	@Inject(at = @At(value = "HEAD"), method = "travel", remap = false)
	public void travelHook(CallbackInfo ci) {
		if (((LivingEntity)(Object)this).isEffectiveAi() || this.isControlledByLocalInstance()) {
			if (this instanceof ExtraMotionProvider provider) {
				if (provider.operateExtraMotionModule()) {
					provider.extraMotionModule().travelHook();
				}
			}
		}
	}

	@Inject(at = @At(value = "HEAD"), method = "tickDeath", remap = false, cancellable = true)
	public void cancelTickDeath(CallbackInfo ci) {
		if (this instanceof LivingAnimatableProvider provider && provider.operateAnimatableModule()) {
			LivingAnimatableModule module = provider.animatableModule();
			if (module.deathHasBegun()) {
				module.tickDeathHook();
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"), method = "handleRelativeFrictionAndCalculateMovement")
	public void  cancelInputMovement(Vec3 travelVector, float p_21076_, CallbackInfoReturnable<Vec3> cir) {
		if (this instanceof MotionUtilProvider provider) {
			this.setDeltaMovement(getDeltaMovement().subtract(deGetInputVector(travelVector, this.getRelevantMoveFactor(p_21076_), this.getYRot()).scale(provider.movementReduction())));
		}
	}

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"), method = "handleRelativeFrictionAndCalculateMovement", index = 1, remap = false)
	public Vec3  calculateMovementHook(Vec3 vec) {
		if (this instanceof ExtraMotionProvider provider) {
			if (provider.operateExtraMotionModule()) {
				return vec.add(provider.extraMotionModule().getAdditionalMotion());
			}
		}

		return vec;
	}


	@Inject(at = @At(value = "HEAD"), method = "actuallyHurt", remap = false)
	public void hurtHook(CallbackInfo ci) {
		if (this instanceof LoopedWalkProvider provider) {
			if (provider.operateWalkModule()) {
				if (this.invulnerableTime == 20) {
					if (provider.hurtLoopLimbSwingFactor() > 0.0F) {
						new MSGTrivialEntityActions(this.getId(), MSGTrivialEntityActions.HIT_EVENT).sendTo(PacketDistributor.TRACKING_CHUNK.with(() -> this.level.getChunkAt(this.blockPosition())));
					}
				}
			}
		}
	}

	private static Vec3 deGetInputVector(Vec3 p_20016_, float p_20017_, float p_20018_) {
		double d0 = p_20016_.lengthSqr();
		if (d0 < 1.0E-7D) {
			return Vec3.ZERO;
		} else {
			Vec3 vec3 = (d0 > 1.0D ? p_20016_.normalize() : p_20016_).scale(p_20017_);
			float f = Mth.sin(p_20018_ * ((float)Math.PI / 180F));
			float f1 = Mth.cos(p_20018_ * ((float)Math.PI / 180F));
			return new Vec3(vec3.x * (double)f1 - vec3.z * (double)f, vec3.y, vec3.z * (double)f1 + vec3.x * (double)f);
		}
	}

	private float getRelevantMoveFactor(float p_213335_1_) {
		return this.onGround ? this.getSpeed() * (0.21600002F / (p_213335_1_ * p_213335_1_ * p_213335_1_)) : this.flyingSpeed;
	}
}

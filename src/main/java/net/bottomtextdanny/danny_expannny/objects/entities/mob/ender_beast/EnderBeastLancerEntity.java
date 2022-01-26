package net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnderBeastLancerEntity extends EnderBeastEntity{
    public Animation impale;
    public Animation swing;

    public EnderBeastLancerEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.impale = addAnimation(new Animation(40));
        this.swing = addAnimation(new Animation(30));
    }

    protected void registerExtraGoals() {
        super.registerExtraGoals();
        this.goalSelector.addGoal(0, new PlayAnimationGoal(this, this.swing,
                o -> hasAttackTarget() && this.mainAnimationHandler.inactive() && this.meleeTimer.hasEnded() && distanceToSqr(getTarget()) <= 11.0F,
                dannyEntity -> this.meleeTimer.reset()));
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, this.impale,
                o -> hasAttackTarget() && this.mainAnimationHandler.inactive() && this.rangedTimer.hasEnded() && distanceToSqr(getTarget()) <= 17 && distanceToSqr(getTarget()) > 11,
                dannyEntity -> this.rangedTimer.reset()));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FOLLOW_RANGE, 50.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide() && hasAttackTarget()) {
	        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
        }
	  
            if (!this.level.isClientSide()) {
                if (this.mainAnimationHandler.isPlaying(this.impale)) {
                    this.sleepPathSchedule.sleepForNow();
	                getNavigation().stop();
                    if (hasAttackTarget()) {
                        float yawToTarget = DEMath.getTargetYaw(this, getTarget());
	
	                    if (this.mainAnimationHandler.getTick() == 2) {
		                    playSound(DESounds.ES_ENDER_BEAST_EFFORT.get(), 1.0F, 0.8F + (float) this.random.nextInt(2) * 0.1F);
                            this.jawModule.play(this.jawAnimation20);
	                    } else if (this.mainAnimationHandler.getTick() == 10) {
                            if (distanceTo(getTarget()) <= 20) {
                                Vec3 vec = DEMath.fromPitchYaw(0, getYRot());

                                getTarget().push(1.0F * vec.x, 0F, 1.0F * vec.z);
                                doHurtTarget(getTarget());
                            }
                            
                        }
	                    setYRot(yawToTarget);
                        this.yBodyRot = yawToTarget;
                        getLookControl().setLookAt(getTarget(), 7.0F, 30.0F);

                    }
                } else if (this.mainAnimationHandler.isPlaying(this.swing)) {
                    this.sleepPathSchedule.sleepForNow();
	                getNavigation().stop();
                    if (hasAttackTarget()) {
                        float yawToTarget = DEMath.getTargetYaw(this, getTarget());
                        if (this.mainAnimationHandler.getTick() == 9) {
                            if (distanceTo(getTarget()) <= 18) {
                                Vec3 vec = DEMath.fromPitchYaw(0, getYRot());

                                getTarget().push(1.0F * vec.x, 0.0F, 1.0F * vec.z);
                                doHurtTarget(getTarget());
                            }
                        }

                        setYRot(yawToTarget);
                        this.yBodyRot = yawToTarget;
                        getLookControl().setLookAt(getTarget(), 7.0F, 30.0F);

                    }
                }
            }

    }
}

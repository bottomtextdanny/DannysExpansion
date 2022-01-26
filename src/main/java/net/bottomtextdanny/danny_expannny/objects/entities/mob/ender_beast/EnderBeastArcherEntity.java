package net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.EnderArrowEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnderBeastArcherEntity extends EnderBeastEntity {
    public Animation shot;
    public Animation punch;

    public EnderBeastArcherEntity(EntityType<? extends EnderBeastArcherEntity> type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = 50;
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.shot = addAnimation(new Animation(40));
        this.punch = addAnimation(new Animation(28));
    }

    protected void registerExtraGoals() {
        super.registerExtraGoals();
        this.goalSelector.addGoal(0, new PlayAnimationGoal(this, this.punch,
                o -> hasAttackTarget() && this.mainAnimationHandler.isPlayingNull() && this.meleeTimer.hasEnded() && distanceToSqr(getTarget()) <= 10.0F,
                dannyEntity -> this.meleeTimer.reset()));
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, this.shot,
                o -> hasAttackTarget() && this.mainAnimationHandler.isPlayingNull() && this.rangedTimer.hasEnded() && hasLineOfSight(getTarget()) && distanceToSqr(getTarget()) > 10,
                dannyEntity -> this.rangedTimer.reset()));
    }

    public static AttributeSupplier.Builder Attributes() {
        return createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MAX_HEALTH, 125.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22F)
                .add(Attributes.FOLLOW_RANGE, 50.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mainAnimationHandler.isPlaying(this.shot)) {
            if (this.onGround) {
                setDeltaMovement(0.0F, 0.0F, 0.0F);
            } else {
                setDeltaMovement(0.0F, -0.8F, 0.0F);
            }

            if (this.mainAnimationHandler.getTick() == 27) {
                EnderArrowEntity enderArrowEntity = DEEntities.ENDER_ARROW.get().create(this.level);

                enderArrowEntity.setPos(getX(), getEyeY() - 0.4, getZ());
                enderArrowEntity.setRotations(this.yHeadRot, getXRot());
                enderArrowEntity.setCaster(this);
                this.level.addFreshEntity(enderArrowEntity);
            }

            if (hasAttackTarget()) {
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
                setYRot(DEMath.getTargetYaw(this, getTarget()));
            }


            this.getNavigation().stop();
        } else if (this.mainAnimationHandler.isPlaying(this.punch)) {
        	if (this.mainAnimationHandler.getTick() == 8) playSound(DESounds.ES_SWOOSH.get(), 1.0F, 0.7F + this.random.nextFloat() * 0.4F);

            if (hasAttackTarget()) {
                if (this.mainAnimationHandler.getTick() == 9) {
                    if (distanceTo(getTarget()) <= 16) {
                        Vec3 vec = DEMath.fromPitchYaw(0, getYRot());

                        getTarget().push(7.0F * vec.x, 0F, 7.0F * vec.z);
                        doHurtTarget(getTarget());
                    }
                }
            }
            this.getNavigation().stop();
        }
    }
}

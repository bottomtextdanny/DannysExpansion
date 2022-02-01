package net.bottomtextdanny.dannys_expansion.common.Entities.living.floating;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.RangedAvoidingEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.CursedFireEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CursedSkullEntity extends RangedAvoidingEntity implements Enemy {
    public static final SimpleAnimation SPIT_FIRE = new SimpleAnimation(28);

    public CursedSkullEntity(EntityType<? extends CursedSkullEntity> type, Level worldIn) {
        super(type, worldIn);
        this.bobbingTicks = 120;
        this.bobbingAmount = 0.004F;
        this.rangedTimer.setBoundBase(60);
    }

    @Override
    public AnimationGetter getAnimations() {
        return SPIT_FIRE;
    }

    protected void registerExtraGoals() {

        this.goalSelector.addGoal(1, new CursedSkullEntity.SpitFireGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }


    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        double d0 = this.random.nextGaussian() * (getBoundingBox().getXsize() + 0.15) / 2 ;
        double d1 = this.random.nextFloat() * getBoundingBox().getYsize() + 0.1;
        double d2 = this.random.nextGaussian() * (getBoundingBox().getXsize() + 0.15) / 2 ;

        EntityUtil.particleAt(this.level, DEParticles.CURSED_FLAME.get(), 1,getX() + d0, getY() + d1, getZ() + d2, 0.0F, 0.0F, 0.0F, 0.0F);
    }
    
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BONE_BLOCK_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BONE_BLOCK_BREAK;
    }

    class SpitFireGoal extends Goal {

        public void start() {
            super.start();
            CursedSkullEntity.this.mainHandler.play(SPIT_FIRE);
            CursedSkullEntity.this.rangedTimer.reset();
        }

        @Override
        public void tick() {
            super.tick();

            if (hasAttackTarget()) {
                if (CursedSkullEntity.this.mainHandler.getTick() == 16) {
                    CursedFireEntity cursedFireEntity = DEEntities.CURSED_FIRE.get().create(CursedSkullEntity.this.level);

                    if (cursedFireEntity != null) {
                        Vec3 forward = Vec3.directionFromRotation(getXRot(), CursedSkullEntity.this.yHeadRot);
                        double x = 0.5 * forward.x;
                        double y = 0.1875 * forward.y;
                        double z = 0.5 * forward.z;
                        cursedFireEntity.setPos(getX() + x, getY() + y, getZ() + z);

                        float yaw = DEMath.getTargetYaw(cursedFireEntity, getTarget());
                        float pitch = DEMath.getTargetPitch(cursedFireEntity, getTarget());

                        cursedFireEntity.setRotations(yaw, pitch);
                        cursedFireEntity.setCaster(CursedSkullEntity.this);
                        CursedSkullEntity.this.level.addFreshEntity(cursedFireEntity);
                    }
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return CursedSkullEntity.this.mainHandler.isPlaying(SPIT_FIRE);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && CursedSkullEntity.this.mainHandler.isPlayingNull() && CursedSkullEntity.this.rangedTimer.hasEnded() && hasLineOfSight(getTarget());
        }
    }
}

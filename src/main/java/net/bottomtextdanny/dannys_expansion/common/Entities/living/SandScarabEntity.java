package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.NullAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SandScarabEntity extends BasicSummonEntity {
    public Animation leap = addAnimation(new Animation(20));
    public Animation death = addAnimation(new Animation(15));

    public SandScarabEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.meleeTimer.setBoundBase(30);
        this.max_life_ticks.set(300);
        this.useless_timer.set(IntScheduler.simple(100));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 11.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.33D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(1, new LeapAttackGoal());
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2F, 2.5F));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this){

            @Override
            public boolean canUse() {
                if (isTamed()) {
                    return false;
                }

                return super.canUse();
            }
        }.setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.mainAnimationHandler.isPlaying(this.leap)) {

            if (hasAttackTarget()) {

                getLookControl().setLookAt(getTarget(), 999.0F, 30.0F);
            }

            setYRot(this.yHeadRot);

            if (this.level.isClientSide) {

                this.yBodyRot = this.yHeadRot;
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }
    
    @Override
    public ParticleOptions getDeathParticle() {
        return DEParticles.DEATH_MUMMY.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_SAND_SCARAB_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_SAND_SCARAB_DEATH.get();
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.5F;
    }

    @Nullable
    @Override
    public IAnimation getDeathAnimation() {
        return this.death;
    }
    
    class LeapAttackGoal extends Goal {
        public LeapAttackGoal() {
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public void start() {
            super.start();

            SandScarabEntity.this.mainAnimationHandler.play(SandScarabEntity.this.leap);

            Vec3 vec0 = DEMath.fromPitchYaw(0, SandScarabEntity.this.yHeadRot);
            push(vec0.x, 0.35F, vec0.z);

            playSound(DESounds.ES_SAND_SCARAB_ATTACK.get(), 0.75F, 1.0F + SandScarabEntity.this.random.nextFloat() * 0.1F);

            SandScarabEntity.this.meleeTimer.reset();
        }

        @Override
        public void tick() {
            super.tick();
            if (hasAttackTarget()) {
                if (reachTo(getTarget()) < 1.2F) {
                    doHurtTarget(getTarget());
                    boolean hurted = true;

                    SandScarabEntity.this.mainAnimationHandler.play(NullAnimation.UNI);
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return SandScarabEntity.this.mainAnimationHandler.isPlaying(SandScarabEntity.this.leap);
        }

        @Override
        public boolean canUse() {
            return hasAttackTarget() && SandScarabEntity.this.mainAnimationHandler.isPlayingNull() && SandScarabEntity.this.meleeTimer.hasEnded() && distanceTo(getTarget()) <=  3.0;
        }
    }
}

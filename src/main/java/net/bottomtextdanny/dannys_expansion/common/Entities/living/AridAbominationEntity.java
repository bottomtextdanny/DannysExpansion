package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayShuffledAnimationsGoal;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class AridAbominationEntity extends BasicSummonEntity {
    public static final SimpleAnimation CLAP = new SimpleAnimation(18);
    public static final SimpleAnimation HURT = new SimpleAnimation(10);
    public static final AnimationManager ANIMATIONS = new AnimationManager(CLAP, HURT);
    public Vec3 rightHandPosition = Vec3.ZERO;

    public AridAbominationEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.meleeTimer.setBoundBase(30);
        this.max_life_ticks.set(500);
        this.useless_timer.set(IntScheduler.simple(120));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 11.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Override
    protected void commonInit() {
        this.loopedWalkModule = new LoopedWalkModule(this);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    protected void registerExtraGoals() {

        this.goalSelector.addGoal(1, new PlayShuffledAnimationsGoal(this, List.of(CLAP), o -> ifAttackMeleeParamsAnd(target -> reachTo(target) < 1.2F)));

        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d, 0));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this){

            @Override
            public boolean canUse() {
                if (isTamed()) {
                    return false;
                }

                return super.canUse();
            }
        }.setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mainHandler.isPlaying(CLAP)) {

            if (!this.level.isClientSide()) {
                this.sleepPathSchedule.sleepForNow();

                if (this.mainHandler.getTick() == 10) {
                    playSound(DESounds.ES_ARID_ABOMINATION_CLAP.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);


                } else if (this.mainHandler.getTick() == 11) {
                    if (hasAttackTarget() && reachTo(getTarget()) < 1.6F) {
                        doHurtTarget(getTarget());
                    }
                }

            } else {
                if (this.mainHandler.getTick() == 11) {
                    for (int i = 0; i < 6; i++) {
                        float speed = 0.5F + this.random.nextFloat() * 0.2F;
                        Vec3 vec0 = DEMath.fromPitchYaw((float) this.random.nextGaussian() * 45F, this.random.nextFloat() * 360).scale(speed);
                        this.level.addParticle(DEParticles.SAND_CLOUD.get(), this.rightHandPosition.x, this.rightHandPosition.y, this.rightHandPosition.z, vec0.x, vec0.y, vec0.z);
                    }
                }
            }
        }
    }

    @Override
    public ParticleOptions getDeathParticle() {
        return DEParticles.DEATH_MUMMY.get();
    }

    @Override
    public void animateHurt() {
        super.animateHurt();
        this.mainHandler.play(HURT);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_ARID_ABOMINATION_HURT.get();
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_ARID_ABOMINATION_DEATH.get();
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.33F;
    }

}

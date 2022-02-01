package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayShuffledAnimationsGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.GolemDroneEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Arrays;

public class JungleGolemEntity extends ModuledMob implements Enemy {
    public static final SimpleAnimation SLAM = new SimpleAnimation(15);
    public static final SimpleAnimation PUNCH = new SimpleAnimation(15);
    public static final SimpleAnimation HEAVY_PUNCH = new SimpleAnimation(23);
    public static final SimpleAnimation THROW_DRONE = new SimpleAnimation(28);
    public static final AnimationManager ANIMATIONS = new AnimationManager(SLAM, PUNCH, HEAVY_PUNCH, THROW_DRONE);
    public Timer heavyTimer;

    public JungleGolemEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);

        this.meleeTimer = new Timer(30);
        this.rangedTimer = new Timer(280);
        this.heavyTimer = new Timer(70, i -> DEMath.intRandomOffset(i, 0.3F));
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        push(0.1, 0, 0);
    }

    protected void registerExtraGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new PlayAnimationGoal(this, HEAVY_PUNCH, o -> hasAttackTarget() && this.heavyTimer.hasEnded() && this.mainHandler.isPlayingNull() && hasLineOfSight(getTarget()) && reachTo(getTarget()) < 1.65F, o -> this.heavyTimer.reset()));
        this.goalSelector.addGoal(0, new PlayShuffledAnimationsGoal(this, Arrays.asList(SLAM, PUNCH), o -> hasAttackTarget() && this.meleeTimer.hasEnded() && this.mainHandler.isPlayingNull() && hasLineOfSight(getTarget()) && reachTo(getTarget()) < 1.65F, o -> this.meleeTimer.reset()));
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, THROW_DRONE, o -> hasAttackTarget() && this.mainHandler.isPlayingNull() && hasLineOfSight(getTarget()) && this.rangedTimer.hasEnded(), o -> this.rangedTimer.reset()));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (isEffectiveAi()) {

            if (this.mainHandler.isPlaying(SLAM)) {
                this.heavyTimer.tryUp();
                this.sleepPathSchedule.sleepForNow();

                if (this.mainHandler.getTick() == 4) {

                    playSound(SoundEvents.BASALT_BREAK, 1.0F, 0.6F + this.random.nextFloat() * 0.4F);
                }

                else if (hasAttackTarget() && this.mainHandler.getTick() == 7 && reachTo(getTarget()) < 2) doHurtTarget(getTarget());
            }

            else if (this.mainHandler.isPlaying(PUNCH)) {
                this.heavyTimer.tryUp();
                this.sleepPathSchedule.sleepForNow();

                if (this.mainHandler.getTick() == 8) {

                    playSound(SoundEvents.BASALT_BREAK, 1.0F, 0.6F + this.random.nextFloat() * 0.4F);
                }

                else if (hasAttackTarget() && this.mainHandler.getTick() == 7 && reachTo(getTarget()) < 2) doHurtTarget(getTarget());
            }

            else if (this.mainHandler.isPlaying(HEAVY_PUNCH)) {
                this.sleepPathSchedule.sleepForNow();

                if (!this.level.isClientSide()) {
                    if (this.mainHandler.getTick() == 10) {

                        setYRot(this.yHeadRot);
                        playSound(DESounds.ES_SWOOSH.get(), 1.0F, 0.6F + this.random.nextFloat() * 0.4F);
                    }

                    if (hasAttackTarget()) {
                        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);


                        if (hasAttackTarget() && this.mainHandler.getTick() == 12 && reachTo(getTarget()) < 2.5) {
                            playSound(DESounds.ES_JUNGLE_GOLEM_HEAVY_HIT.get(), 1.0F, 0.6F + this.random.nextFloat() * 0.4F);

                            attackWithMultiplier(getTarget(), 1.75F);
                            disableShield(getTarget(), 60);
                        }

                    }
                }
            }
            else if (this.mainHandler.isPlaying(THROW_DRONE)) {
                this.sleepPathSchedule.sleepForNow();

                if (hasAttackTarget()) {

                    getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

                    if (this.mainHandler.getTick() == 12) {

                        Vec3 vec = DEMath.fromPitchYaw(0, this.yBodyRot);
                        Vec3 vecCounterClockwise90 = Vec3.directionFromRotation(0, Mth.wrapDegrees(this.yBodyRot) + 270);
                        GolemDroneEntity golemDroneEntity = DEEntities.GOLEM_DRONE.get().create(this.level);
                        float yaw = DEMath.getTargetYaw(golemDroneEntity, getTarget());
                        float pitch = DEMath.getTargetPitch(golemDroneEntity, getTarget());
                        double x = 0.1 * vecCounterClockwise90.x;
                        double y = 1.5;
                        double z = 0.1 * vecCounterClockwise90.z;
                        double x1 = 0.8 * vec.x;
                        double z1 = 0.8 * vec.z;

                        golemDroneEntity.setCaster(this);
                        golemDroneEntity.setPos(getX() + x + x1, getY() + y, getZ() + z + z1);
                        golemDroneEntity.setRotations(yaw, pitch);
                        this.level.addFreshEntity(golemDroneEntity);

                        playSound(DESounds.ES_JUNGLE_GOLEM_RELEASE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);

                    }
                }
            }
        }
    }


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_JUNGLE_GOLEM_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_JUNGLE_GOLEM_DEATH.get();
    }

    protected SoundEvent getStepSound() {
        return DESounds.ES_JUNGLE_GOLEM_STEP.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.35F, 0.5F);
    }
}

package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayShuffledAnimationsGoal;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;

public class FrozenGhoulEntity extends ModuledMob {
    public static final SimpleAnimation MELEE0 = new SimpleAnimation(15);
    public static final SimpleAnimation MELEE1 = new SimpleAnimation(20);
    public static final SimpleAnimation MELEE2 = new SimpleAnimation(20);
    public static final AnimationManager ANIMATIONS = new AnimationManager(MELEE0, MELEE1, MELEE2);

    public FrozenGhoulEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        getLivingSoundTimer().setBoundBase(160);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    protected void registerExtraGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PlayShuffledAnimationsGoal(this, Arrays.asList(MELEE0, MELEE1, MELEE2), o -> ifAttackMeleeParamsAnd(target -> reachTo(target) < 1.0F)));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d, 0));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));

    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (getTarget() != null) {
            if (this.mainHandler.isPlaying(MELEE0)) {
                this.sleepPathSchedule.setSleep(1);
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
                this.navigation.stop();
                setYRot(this.yHeadRot);

                if (this.mainHandler.getTick() == 7) {
                    playSound(DESounds.ES_SWOOSH.get(), 0.8F, 0.9F + this.random.nextFloat() * 0.2F);
                    playSound(DESounds.ES_FROZEN_GHOUL_HEAVY_ATTACK.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                }

                if (this.mainHandler.getTick() == 9) {
                    if (reachTo(getTarget()) <= 2.5F) {
                        doHurtTarget(getTarget());
                    }
                }
            } else if (this.mainHandler.isPlaying(MELEE1)) {
                this.sleepPathSchedule.setSleep(1);
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
                this.navigation.stop();
                setYRot(this.yHeadRot);

                if (this.mainHandler.getTick() == 9) {
                    playSound(DESounds.ES_SWOOSH.get(), 0.4F, 1.0F + this.random.nextFloat() * 0.2F);
                    playSound(DESounds.ES_FROZEN_GHOUL_LIGHT_ATTACK.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                }

                if (this.mainHandler.getTick() == 10) {
                    if (reachTo(getTarget()) <= 3.5F) {
                        doHurtTarget(getTarget());
                    }
                }
            } else if (this.mainHandler.isPlaying(MELEE2)) {
                this.sleepPathSchedule.setSleep(1);
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
                this.navigation.stop();
                setYRot(this.yHeadRot);

                if (this.mainHandler.getTick() == 5) {
                    playSound(DESounds.ES_SWOOSH.get(), 1.2F, 0.8F + this.random.nextFloat() * 0.2F);
                    playSound(DESounds.ES_FROZEN_GHOUL_LIGHT_ATTACK.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                }

                if (this.mainHandler.getTick() == 7) {
                    if (reachTo(getTarget()) <= 3.5F) {
                        doHurtTarget(getTarget());
                    }
                }
            }
        }

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_FROZEN_GHOUL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_FROZEN_GHOUL_DEATH.get();
    }

    @Nullable
    @Override
    public SoundEvent getLivingSound() {
        return DESounds.ES_FROZEN_GHOUL_IDLE.get();
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.40F;
    }
}

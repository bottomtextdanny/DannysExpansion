package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class SporeWightEntity extends ModuledMob {
    public static final SimpleAnimation ATTACK = new SimpleAnimation(20);
    public static final SimpleAnimation TICK = new SimpleAnimation(10);
    public static final AnimationManager ANIMATIONS = new AnimationManager(ATTACK, TICK);
	public final AnimationHandler<SporeWightEntity> livingModule = addAnimationHandler(new AnimationHandler<>(this));

    public SporeWightEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
        this.livingSoundTimer = new Timer(250, b -> DEMath.intRandomOffset(b, 0.7F, this.random));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
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
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, ATTACK, o -> ifAttackMeleeParamsAnd(target -> reachTo(target) < 1.6F)));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            if (hasAttackTarget()) {
                if (this.mainHandler.isPlaying(ATTACK)) {
                    if (this.mainHandler.getTick() == 9 && reachTo(getTarget()) < 2F) {
                        doHurtTarget(getTarget());
                    }
                }
            }
        }
    }
	
	
	@Override
	public void doLivingSound() {
		super.doLivingSound();
        this.livingModule.play(TICK);
	}

    @Override
    public float getLoopWalkMultiplier() {
        return 0.32F;
    }
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DESounds.ES_SPORE_WIGHT_HURT.get();
	}
	
	@Override
	public void playLivingSound() {
		super.playLivingSound();
	}
	
	@Nullable
	@Override
	public SoundEvent getLivingSound() {
		return DESounds.ES_SPORE_WIGHT_IDLE.get();
	}
	
	@Override
    public float hurtLoopLimbSwingFactor() {
        return 0.6F;
    }
}

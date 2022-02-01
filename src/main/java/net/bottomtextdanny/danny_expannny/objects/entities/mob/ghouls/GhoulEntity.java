package net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.VomitEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class GhoulEntity extends ModuledMob implements Enemy {
	public static final Form<GhoulEntity> NORMAL_FORM = new GhoulNormalForm();
	public static final Form<GhoulEntity> SWAMP_FORM = new GhoulSwampForm();
    public static final IndexedFormManager FORMS =
            IndexedFormManager.builder()
                    .add(NORMAL_FORM)
                    .add(SWAMP_FORM)
                    .create();
	public AnimationHandler<GhoulEntity> livingModule;
    public static final SimpleAnimation VOMIT = new SimpleAnimation(24);
    public static final SimpleAnimation GRAB = new SimpleAnimation(26);
    public static final SimpleAnimation BREATH = new SimpleAnimation(22);
    public static final SimpleAnimation WTF = new SimpleAnimation(16);
    public static final AnimationManager ANIMATIONS = new AnimationManager(VOMIT, GRAB, BREATH, WTF);
    public Timer respirationTimer;
	
    public GhoulEntity(EntityType<? extends GhoulEntity> type, Level worldIn) {
        super(type, worldIn);
        getLivingSoundTimer().setBoundBase(340);
        this.rangedTimer.setBoundBase(160);
        this.respirationTimer = new Timer(600, o -> DEMath.intRandomOffset(o, 0.7F));
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.variableModule = new IndexedVariableModule(this, FORMS);
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.livingModule = addAnimationHandler(new AnimationHandler<>(this));
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new PlayAnimationGoal(this, GRAB,
                o -> hasAttackTarget() && this.mainHandler.isPlayingNull() && this.meleeTimer.hasEnded() && reachTo(getTarget()) < 1.0F,
                dannyEntity -> this.meleeTimer.reset()));
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, VOMIT,
                o -> false && hasAttackTarget() && this.mainHandler.isPlayingNull() && this.rangedTimer.hasEnded() && hasLineOfSight(getTarget()),
                dannyEntity -> this.rangedTimer.reset()));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Zombie.class, 10, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));

        
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.mainHandler.isPlaying(GRAB)) {
            if (this.mainHandler.getTick() == 10) {
                playSound(DESounds.ES_SWOOSH.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                playSound(DESounds.ES_GHOUL_PUNCH.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
            }
            if (this.mainHandler.getTick() == 14 && hasAttackTarget() && reachTo(getTarget()) < 2.0F) attackWithMultiplier(getTarget(), 1.0F);
        }
        else if (this.mainHandler.isPlaying(VOMIT)) {
            getNavigation().stop();

            if (this.mainHandler.getTick() == 1) playSound(DESounds.ES_GHOUL_VOMIT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);

            if (hasAttackTarget()) {
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

                if (this.mainHandler.getTick() == 12 && isEffectiveAi()) {
                    VomitEntity vomit = new VomitEntity(DEEntities.VOMIT.get(), this.level);
                    vomit.setOwner(this);
                    vomit.setPos(getX(), getY() + 1.65, getZ());
                    vomit.shootFromRotation(this, getXRot(), this.yHeadRot, -2.0F,  2F, 0F);
                    this.level.addFreshEntity(vomit);
                }
            }
        }

        if (!this.level.isClientSide()) {
            if (this.livingModule.isPlayingNull() && this.respirationTimer.hasEnded()) {
                this.livingModule.play(BREATH);
                playSound(DESounds.ES_GHOUL_RESPIRATION.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
                this.respirationTimer.reset();
            }
            else this.respirationTimer.tryUp();
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstanceIn, @Nullable Entity target) {
        if (effectInstanceIn.getEffect() == MobEffects.POISON) return false;
        return super.addEffect(effectInstanceIn, target);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_GHOUL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_GHOUL_DEATH.get();
    }

    @Nullable
    @Override
    public SoundEvent getLivingSound() {
        return DESounds.ES_GHOUL_IDLE.get();
    }

    @Override
    public void playLivingSound() {
        super.playLivingSound();
        this.livingModule.play(WTF);
    }
	
	@Override
	public Form<GhoulEntity> chooseVariant() {
    	if (this.level.getBiome(blockPosition()).getBiomeCategory() == Biome.BiomeCategory.SWAMP) {
		    if (this.random.nextInt(3) != 2) {
		    	return SWAMP_FORM;
		    }
	    }
    	return NORMAL_FORM;
	}


}

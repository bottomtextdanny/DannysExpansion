package net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.helpers.EffectHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;

public class PetrifiedGhoul extends SmartyMob implements Enemy {
    public static final float COMBAT_SPEED_MULTIPLIER = 1.25F;
    public static final float ATTACK_RANGE = 2.0F;
    public static final int ATTACK_DELAY_MIN = 18;
    public static final int ATTACK_DELAY_MAX = 25;
    public static final int ATTACKS_PER_STRONG_ATTACK_MIN = 2;
    public static final int ATTACKS_PER_STRONG_ATTACK_MAX = 4;
    public static final EntityDataReference<IntScheduler.Variable> ATTACKS_TILL_STRONG_ATTACK_REF =
            BCDataManager.attribute(PetrifiedGhoul.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACKS_PER_STRONG_ATTACK_MIN, ATTACKS_PER_STRONG_ATTACK_MAX),
                            "attacks_till_strong_attack")
            );
    public static final EntityDataReference<IntScheduler.Variable> ATTACK_DELAY_REF =
            BCDataManager.attribute(PetrifiedGhoul.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    private Animation sideAttackAnimation;
    private Animation strongAttackAnimation;
    public final EntityData<IntScheduler.Variable> attacksTillStrongAttack;
    public final EntityData<IntScheduler.Variable> attackDelay;


    public PetrifiedGhoul(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.attacksTillStrongAttack = bcDataManager().addNonSyncedData(EntityData.of(ATTACKS_TILL_STRONG_ATTACK_REF));
        this.attackDelay = bcDataManager().addNonSyncedData(EntityData.of(ATTACK_DELAY_REF));
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 15.0F);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.sideAttackAnimation = addAnimation(new Animation(23));
        this.strongAttackAnimation = addAnimation(new Animation(32));
    }

    @Override
    public Psyche<?> makePsyche() {
        return new PetrifiedGhoulPsyche(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public SoundEvent getAttackSound() {
        return DESounds.ES_PETRIFIED_GHOUL_ATTACK.get();
    }

    public Animation getSideAttackAnimation() {
        return this.sideAttackAnimation;
    }

    public Animation getStrongAttackAnimation() {
        return this.strongAttackAnimation;
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.42F;
    }

    @Override
    public float hurtLoopLimbSwingFactor() {
        return 0.20F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return DESounds.ES_PETRIFIED_GHOUL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_PETRIFIED_GHOUL_DEATH.get();
    }

    @Nullable
    @Override
    public SoundEvent getLivingSound() {
        return DESounds.ES_PETRIFIED_GHOUL_IDLE.get();
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstance, @Nullable Entity entityIn) {
        if (EffectHelper.isBadAndOnlyAppliesToLiving(effectInstance.getEffect())) return false;
        return super.addEffect(effectInstance, entityIn);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
}

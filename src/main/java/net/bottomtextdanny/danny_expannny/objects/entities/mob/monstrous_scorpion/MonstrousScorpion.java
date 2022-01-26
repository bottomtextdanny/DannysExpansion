package net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.helpers.EffectHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour.KlifourEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MonstrousScorpion extends SmartyMob {
	public static final int CLAW_ATTACK_IDENTIFIER = DEUtil.getIdentifier();
    public static final float COMBAT_SPEED = 1.2F;
    public static final float ATTACK_RANGE = 1.4F;
    public static final float CLAW_ATTACK_RANGE = 0.9F;
    public static final int CLAW_ATTACK_DELAY_MIN = 60;
    public static final int CLAW_ATTACK_DELAY_MAX = 85;
    public static final int ATTACK_DELAY_MIN = 18;
    public static final int ATTACK_DELAY_MAX = 25;
    public static final MonstrousScorpionForm SANDY_FORM = new MonstrousScorpionSandyForm();
    public static final MonstrousScorpionForm BLACK_FORM = new MonstrousScorpionBlackForm();
    public static final MonstrousScorpionForm BROWN_FORM = new MonstrousScorpionBrownForm();
    public static final IndexedFormManager FORMS =
            IndexedFormManager.builder()
                    .add(SANDY_FORM)
                    .add(BLACK_FORM)
                    .add(BROWN_FORM)
                    .create();
    public static final EntityDataReference<IntScheduler.Variable> ATTACK_DELAY_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    public static final EntityDataReference<IntScheduler.Variable> CLAW_ATTACK_DELAY_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(CLAW_ATTACK_DELAY_MIN, CLAW_ATTACK_DELAY_MAX),
                            "claw_attack_delay")
            );
    public final EntityData<IntScheduler.Variable> attackDelay;
    public final EntityData<IntScheduler.Variable> clawAttackDelay;
    private Animation sting;
    private Animation rightClawAttack;
    private Animation leftClawAttack;
    private Animation bothClawsAttack;

    public MonstrousScorpion(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.0F;
        this.attackDelay = bcDataManager().addNonSyncedData(EntityData.of(ATTACK_DELAY_REF));
        this.clawAttackDelay = bcDataManager().addNonSyncedData(EntityData.of(CLAW_ATTACK_DELAY_REF));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 28.0D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.7D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.variableModule = new IndexedVariableModule(this, FORMS);
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.sting = addAnimation(new Animation(15));
        this.rightClawAttack = addAnimation(new Animation(15).identifier(CLAW_ATTACK_IDENTIFIER));
        this.leftClawAttack = addAnimation(new Animation(15).identifier(CLAW_ATTACK_IDENTIFIER));
        this.bothClawsAttack = addAnimation(new Animation(15).identifier(CLAW_ATTACK_IDENTIFIER));
    }

    @Override
    public Psyche<?> makePsyche() {
        return new MonstrousScorpionPsyche(this);
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public Animation getStingAnimation() {
        return this.sting;
    }

    public Animation[] getClawAttackAnimations() {
        return new Animation[] {this.rightClawAttack, this.leftClawAttack, this.bothClawsAttack};
    }

    public Animation getRightClawAttack() {
        return this.rightClawAttack;
    }

    public Animation getLeftClawAttack() {
        return this.leftClawAttack;
    }

    public Animation getBothClawsAttack() {
        return this.bothClawsAttack;
    }

    @Override
    public void playLoopStepSound(BlockPos pos, BlockState blockIn) {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_INSECT_STEP.get(), SoundSource.HOSTILE, 0.1F + 0.07F * this.random.nextFloat(), 0.8F + 0.2F * this.random.nextFloat());
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.6F;
    }

    @Override
    public float hurtLoopLimbSwingFactor() {
        return 0.7F;
    }

    @Override
    public Form<?> chooseVariant() {
        if (this.level.getBiome(blockPosition()).getBiomeCategory() == Biome.BiomeCategory.JUNGLE) {
            return BLACK_FORM;
        } else if (this.level.getBiome(blockPosition()).getBiomeCategory() == Biome.BiomeCategory.DESERT) {
            return SANDY_FORM;
        }
        return BROWN_FORM;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_BLACK_SCORPION_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_BLACK_SCORPION_DEATH.get();
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstance, Entity entityIn) {
        if (EffectHelper.isToxin(effectInstance.getEffect())) return false;
        return super.addEffect(effectInstance, entityIn);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

}

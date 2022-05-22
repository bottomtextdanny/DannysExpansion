package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.tables.items.DEItems;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mixin_support.ItemEntityExtensor;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class MonstrousScorpion extends SmartyMob {
	public static final int CLAW_ATTACK_IDENTIFIER = 1;
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
    public static final EntityDataReference<IntScheduler.Ranged> ATTACK_DELAY_REF =
            BCDataManager.attribute(MonstrousScorpion.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    public static final EntityDataReference<IntScheduler.Ranged> CLAW_ATTACK_DELAY_REF =
            BCDataManager.attribute(MonstrousScorpion.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(CLAW_ATTACK_DELAY_MIN, CLAW_ATTACK_DELAY_MAX),
                            "claw_attack_delay")
            );
    public static final SimpleAnimation STING = new SimpleAnimation(15);
    public static final SimpleAnimation RIGHT_CLAW_ATTACk = new SimpleAnimation(15).identifier(CLAW_ATTACK_IDENTIFIER);
    public static final SimpleAnimation LEFT_CLAW_ATTACk = new SimpleAnimation(15).identifier(CLAW_ATTACK_IDENTIFIER);
    public static final SimpleAnimation BOTH_CLAWS_ATTACK = new SimpleAnimation(15).identifier(CLAW_ATTACK_IDENTIFIER);
    public static final AnimationArray ANIMATIONS = new AnimationArray(STING, RIGHT_CLAW_ATTACk, LEFT_CLAW_ATTACk, BOTH_CLAWS_ATTACK);
    public final EntityData<IntScheduler.Ranged> attackDelay;
    public final EntityData<IntScheduler.Ranged> clawAttackDelay;

    public MonstrousScorpion(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 5;
        this.maxUpStep = 1.0F;
        this.attackDelay = bcDataManager().addNonSyncedData(EntityData.of(ATTACK_DELAY_REF));
        this.clawAttackDelay = bcDataManager().addNonSyncedData(EntityData.of(CLAW_ATTACK_DELAY_REF));
    }

    public static AttributeSupplier.Builder attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 28.0D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.7D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    public static boolean spawnPlacement(EntityType<? extends MonstrousScorpion> entityType,
                                         ServerLevelAccessor level, MobSpawnType reason,
                                         BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random)
                && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.variableModule = new IndexedVariableModule(this, FORMS);
        this.loopedWalkModule = new LoopedWalkModule(this);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
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

    @Override
    public Form<?> chooseVariant() {
        Biome.BiomeCategory biomeCategory = Biome.getBiomeCategory(this.level.getBiome(blockPosition()));

        if (biomeCategory == Biome.BiomeCategory.JUNGLE)
            return BLACK_FORM;
        else if (biomeCategory == Biome.BiomeCategory.DESERT)
            return SANDY_FORM;

        return BROWN_FORM;
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstance, Entity entityIn) {
        Optional<Holder<MobEffect>> op = Registry.MOB_EFFECT.getHolder(MobEffect.getId(effectInstance.getEffect()));
       // if (op.isPresent() && op.get().is(DEEffects.TOXIN)) return false;
        return super.addEffect(effectInstance, entityIn);
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

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_BLACK_SCORPION_HURT.get();
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemStack itemStack, float idk) {
        if (itemStack.getItem() == DEItems.SCORPION_GLAND.get()) {
            if (itemStack.isEmpty()) {
                return null;
            } else if (this.level.isClientSide) {
                return null;
            } else {
                ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY() + (double)idk, this.getZ(), itemStack);
                itementity.setDefaultPickUpDelay();

                ((ItemEntityExtensor)itementity)
                        .setShowingModel(((MonstrousScorpionForm)variableModule().getForm()).gland());
                if (captureDrops() != null) captureDrops().add(itementity);
                else this.level.addFreshEntity(itementity);
                return itementity;
            }
        }
        return super.spawnAtLocation(itemStack, idk);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_BLACK_SCORPION_DEATH.get();
    }

    public static SimpleAnimation[] getClawAttackAnimations() {
        return new SimpleAnimation[] {RIGHT_CLAW_ATTACk, LEFT_CLAW_ATTACk, BOTH_CLAWS_ATTACK};
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

}

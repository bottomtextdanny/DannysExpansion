package bottomtextdanny.dannys_expansion.content.entities.mob.hollow_armor;

import bottomtextdanny.dannys_expansion._util.EffectHelper;
import bottomtextdanny.dannys_expansion.content.entities.mob.ghoul.Ghoul;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider.PhaseAffectedModule;
import bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider.PhaseAffectedProvider;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExternalMotion;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import bottomtextdanny.braincell.mod.entity.modules.motion_util.MotionUtilProvider;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod.world.builtin_sound_instances.EntityMovingSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class HollowArmor extends SmartyMob
        implements Enemy, MotionUtilProvider, ExtraMotionProvider, PhaseAffectedProvider {
    public static final float START_DASHING_RANGE = 7.0F;
    public static final float ATTACK_RANGE = 2.0F;
    public static final int PLAY_DASH_SOUND_CLIENT_CALL = 0;
    public static final float COMBAT_MOVE_SPEED = 1.2F;
    public static final int LETHAL_ATTACK_DELAY_MIN = 60;
    public static final int LETHAL_ATTACK_DELAY_MAX = 85;
    public static final int ATTACK_DELAY_MIN = 20;
    public static final int ATTACK_DELAY_MAX = 25;
    public static final int DEFAULT_DASH_DELAY = 160;
    @OnlyIn(Dist.CLIENT)
    public static final int BUBBLE_LOOP_TICKS = 240;
    @OnlyIn(Dist.CLIENT)
    public static final float BUBBLE_APPEARING_RATIO = 0.3F;
    @OnlyIn(Dist.CLIENT)
    public static final float BUBBLE_APPEAR_RARITY = 0.15F;
    public static final EntityDataReference<IntScheduler.Ranged> LETHAL_ATTACK_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(LETHAL_ATTACK_DELAY_MIN, LETHAL_ATTACK_DELAY_MAX),
                            "lethal_attack")
            );
    public static final EntityDataReference<IntScheduler.Ranged> ATTACKS_DELAY_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    public static final EntityDataReference<IntScheduler.Simple> DASH_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.INT_SCHEDULER,
                            () -> IntScheduler.simple(DEFAULT_DASH_DELAY),
                            "dash")
            );
    public static final EntityDataReference<Boolean> HEALING_USED_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "healing_used")
            );
    public static final SimpleAnimation BLUNT = new SimpleAnimation(17);
    public static final SimpleAnimation SLASH = new SimpleAnimation(21);
    public static final SimpleAnimation SWING = new SimpleAnimation(20);
    public static final SimpleAnimation DOUBLE_SWING = new SimpleAnimation(21);
    public static final SimpleAnimation IMPALE = new SimpleAnimation(26);
    public static final SimpleAnimation HEAL = new SimpleAnimation(28);
    public static final SimpleAnimation DASH = new SimpleAnimation(40);
    public static final SimpleAnimation DEATH = new SimpleAnimation(24);
    public static final AnimationArray ANIMATIONS =
            new AnimationArray(BLUNT, SLASH, SWING, DOUBLE_SWING, IMPALE, HEAL, DASH, DEATH);
    private ExtraMotionModule extraMotionModule;
    private PhaseAffectedModule phaseAffectedModule;
    public final EntityData<IntScheduler.Ranged> lethalAttackTimer;
    public final EntityData<IntScheduler.Ranged> attackTimer;
    public final EntityData<IntScheduler.Simple> dashTimer;
    public final EntityData<Boolean> healingUsed;
    private ExternalMotion dashMotion;
    private float movementReduction;

    public HollowArmor(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 8;
        this.maxUpStep = 1.2F;
        this.attackTimer = bcDataManager().addNonSyncedData(EntityData.of(ATTACKS_DELAY_REF));
        this.lethalAttackTimer = bcDataManager().addNonSyncedData(EntityData.of(LETHAL_ATTACK_REF));
        this.dashTimer = bcDataManager().addNonSyncedData(EntityData.of(DASH_REF));
        this.healingUsed = bcDataManager().addNonSyncedData(EntityData.of(HEALING_USED_REF));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MAX_HEALTH, 36.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.29D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.5D);
    }

    public static boolean spawnPlacement(EntityType<? extends Ghoul> entityType,
                                             ServerLevelAccessor level, MobSpawnType reason,
                                             BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random)
                && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.extraMotionModule = new ExtraMotionModule(this);
        this.phaseAffectedModule = new PhaseAffectedModule(this);
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.dashMotion = addCustomMotion(new ExternalMotion(0.2F));
    }

    @Override
    public PhaseAffectedModule phaseAffectedModule() {
        return this.phaseAffectedModule;
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public ExtraMotionModule extraMotionModule() {
        return this.extraMotionModule;
    }

    @Override
    public Psyche<?> makePsyche() {
        return new HollowArmorPsyche(this);
    }

    @Override
    public void tick() {
        super.tick();
        Connection.doClientSide(() -> clientTick());
    }

    @OnlyIn(Dist.CLIENT)
    protected void clientTick() {
        if (isUnderWater()) {
            if ((float)this.tickCount % (float)BUBBLE_LOOP_TICKS / (float)BUBBLE_LOOP_TICKS < BUBBLE_APPEARING_RATIO) {
                if (random.nextFloat() < BUBBLE_APPEAR_RARITY) {
                    this.level.addParticle(ParticleTypes.BUBBLE, this.getX(), this.getEyeY(), this.getZ(), 0.0D, 0.7D, 0.0D);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == PLAY_DASH_SOUND_CLIENT_CALL) {
            Minecraft.getInstance().getSoundManager()
                    .play(EntityMovingSound.builder(this, DESounds.ES_POSSESSED_ARMOR_DASH.get(), SoundSource.HOSTILE).build());
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstanceIn, @Nullable Entity entityIn) {
        if (EffectHelper.isBadAndOnlyAppliesToLiving(effectInstanceIn.getEffect())) return false;
        return super.addEffect(effectInstanceIn, entityIn);
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_POSSESSED_ARMOR_HIT.get();
    }

    @Nullable
    @Override
    public SoundEvent getLivingSound() {
        return DESounds.ES_POSSESSED_ARMOR_CREAK.get();
    }

    protected SoundEvent getStepSound() {
        return DESounds.ES_POSSESSED_ARMOR_STEP.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.25F, 1.0F);
        this.playSound(DESounds.ES_POSSESSED_ARMOR_JIGGLES.get(), 0.15F, 1.0F);
    }


    @Override
    public float getLoopWalkMultiplier() {
        return 0.4F;
    }

    @Nullable
    @Override
    public SimpleAnimation getDeathAnimation() {
        return DEATH;
    }

    public ExternalMotion getDashMotion() {
        return dashMotion;
    }

    @Override
    public float inputMovementMultiplier() {
        return this.movementReduction;
    }

    @Override
    public void setInputMovementMultiplier(float newFactor) {
        movementReduction = newFactor;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
}

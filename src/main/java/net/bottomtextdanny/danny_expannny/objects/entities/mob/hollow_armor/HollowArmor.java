package net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.PhaseAffectedModule;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.entity.modules.motion_util.MotionUtilProvider;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_sound_instances.EntityMovingSound;
import net.bottomtextdanny.braincell.mod.world.helpers.EffectHelper;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class HollowArmor extends SmartyMob implements MotionUtilProvider, ExtraMotionProvider {
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
    public static final EntityDataReference<IntScheduler.Variable> LETHAL_ATTACK_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(LETHAL_ATTACK_DELAY_MIN, LETHAL_ATTACK_DELAY_MAX),
                            "lethal_attack")
            );
    public static final EntityDataReference<IntScheduler.Variable> ATTACKS_DELAY_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
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
    private ExtraMotionModule extraMotionModule;
    public final EntityData<IntScheduler.Variable> lethal_attack_timer;
    public final EntityData<IntScheduler.Variable> attack_timer;
    public final EntityData<IntScheduler.Simple> dash_timer;
    public final EntityData<Boolean> healing_used;
    private ExternalMotion dashMotion;
    private Animation bluntAnimation;
    private Animation slashAnimation;
    private Animation swingAnimation;
    private Animation doubleSwingAnimation;
    private Animation impaleAnimation;
    private Animation healAnimation;
    private Animation dashAnimation;
    private Animation deathAnimation;
    private float movementReduction;

    public HollowArmor(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
        this.attack_timer = bcDataManager().addNonSyncedData(EntityData.of(ATTACKS_DELAY_REF));
        this.lethal_attack_timer = bcDataManager().addNonSyncedData(EntityData.of(LETHAL_ATTACK_REF));
        this.dash_timer = bcDataManager().addNonSyncedData(EntityData.of(DASH_REF));
        this.healing_used = bcDataManager().addNonSyncedData(EntityData.of(HEALING_USED_REF));
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

    @Override
    protected void commonInit() {
        super.commonInit();
        this.extraMotionModule = new ExtraMotionModule(this);
        this.phaseAffectedModule = new PhaseAffectedModule(this);
        this.loopedWalkModule = new LoopedWalkModule(this);
        this.bluntAnimation = addAnimation(new Animation(17));
        this.slashAnimation = addAnimation(new Animation(21));
        this.swingAnimation = addAnimation(new Animation(20));
        this.doubleSwingAnimation = addAnimation(new Animation(21));
        this.impaleAnimation = addAnimation(new Animation(26));
        this.healAnimation = addAnimation(new Animation(28));
        this.dashAnimation = addAnimation(new Animation(40));
        this.deathAnimation = addAnimation(new Animation(24));
        this.dashMotion = addCustomMotion(new ExternalMotion(0.2F));
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
                if (DEUtil.C_ENTITY_LOGIC_RANDOM.nextFloat() < BUBBLE_APPEAR_RARITY) {
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
    public Animation getDeathAnimation() {
        return this.deathAnimation;
    }

    @Override
    public void setMovementReduction(float newFactor) {
        this.movementReduction = newFactor;
    }

    @Override
    public float movementReduction() {
        return this.movementReduction;
    }

    public ExternalMotion getDashMotion() {
        return this.dashMotion;
    }

    public Animation getBluntAnimation() {
        return this.bluntAnimation;
    }

    public Animation getSlashAnimation() {
        return this.slashAnimation;
    }

    public Animation getSwingAnimation() {
        return this.swingAnimation;
    }

    public Animation getDoubleSwingAnimation() {
        return this.doubleSwingAnimation;
    }

    public Animation getImpaleAnimation() {
        return this.impaleAnimation;
    }

    public Animation getDashAnimation() {
        return this.dashAnimation;
    }

    public Animation getHealAnimation() {
        return this.healAnimation;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
}

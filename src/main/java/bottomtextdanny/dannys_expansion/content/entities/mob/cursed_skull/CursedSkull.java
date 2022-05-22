package bottomtextdanny.dannys_expansion.content.entities.mob.cursed_skull;

import bottomtextdanny.dannys_expansion._util.EffectHelper;
import bottomtextdanny.dannys_expansion.content.entities.ai.controllers.EasedLookControl;
import bottomtextdanny.dannys_expansion.content.entities.ai.controllers.FluentFlyingControl;
import bottomtextdanny.dannys_expansion.content.entities.mob.BCFlyingPathNavigation;
import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod.world.entity_utilities.EntityHurtCallout;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class CursedSkull extends SmartyMob implements Enemy, ExtraMotionProvider, EntityHurtCallout {
    private static final int JAW_FLAG = 0;
    public static final float WANDER_MOVE_SPEED = 0.8F;
    public static final float COMBAT_MOVE_SPEED = 1.0F;
    public static final int ATTACK_DELAY_MIN = 50;
    public static final int ATTACK_DELAY_MAX = 65;
    public static final float FLEE_RANGE = 0.4F;
    public static final EntityDataReference<IntScheduler.Ranged> ATTACKS_DELAY_REF =
            BCDataManager.attribute(CursedSkull.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    public static final SimpleAnimation SPIT = new SimpleAnimation(20);
    public static final AnimationArray ANIMATIONS = new AnimationArray(SPIT);
    private final FluentFlyingControl wanderMoveControl;
    private final FluentFlyingControl combatMoveControl;
    private ExtraMotionModule extraMotionModule;
    public final EntityData<IntScheduler.Ranged> attackDelay;
    private float jawPointer;
    public float jawAnimation;
    public float jawAnimationO;
    private boolean combatMode;

    public CursedSkull(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 5;
        this.wanderMoveControl = new FluentFlyingControl(this, 70, true);
        this.moveControl = this.wanderMoveControl;
        this.combatMoveControl = new FluentFlyingControl(this, 180, false);
        this.lookControl = new EasedLookControl(this, () -> this.combatMode ? 90.0F : 25.0F, () -> this.combatMode ? 20.0F : 15.0F);
        this.attackDelay = bcDataManager().addNonSyncedData(EntityData.of(ATTACKS_DELAY_REF));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FOLLOW_RANGE, 25.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    public static boolean spawnPlacement(EntityType<? extends CursedSkull> entityType,
                                         ServerLevelAccessor level, MobSpawnType reason,
                                         BlockPos pos, Random random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Monster.isDarkEnoughToSpawn(level, pos, random)
                && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    protected void commonInit() {
        this.extraMotionModule = new ExtraMotionModule(this);
    }

    @Override
    public Psyche<?> makePsyche() {
        return new CursedSkullPsyche(this);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        BCFlyingPathNavigation flyingpathnavigation = new BCFlyingPathNavigation(this, this.level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public MoveControl getMoveControl() {
        return this.getTarget() != null && getTarget().isAlive() ? this.combatMoveControl : this.wanderMoveControl;
    }

    @Override
    public AnimationArray getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public ExtraMotionModule extraMotionModule() {
        return this.extraMotionModule;
    }

    @Override
    public void tick() {
        super.tick();
        Connection.doClientSide(() -> clientTick());
        this.fallDistance = 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public void clientTick() {
        jawAnimationO = jawAnimation;
        jawPointer = Mth.clamp(jawPointer, 0.0F, BCMath.FPI_HALF);
        jawAnimation = Mth.lerp(0.5F, jawAnimation, jawPointer);
        jawPointer *= 0.84F;

        if (this.random.nextInt(7) == 0) {
            double posOffsetX = this.random.nextGaussian() * 0.2;
            double posOffsetY = this.random.nextGaussian() * 0.2 + 0.25;
            double posOffsetZ = this.random.nextGaussian() * 0.2;
            this.level.addParticle(DEParticles.CURSED_FLAMES.get(), this.getX() + posOffsetX, this.getY() + posOffsetY, this.getZ() + posOffsetZ, posOffsetX * 0.4, posOffsetY  * 0.4, posOffsetZ * 0.4);
        }
    }

    @Override
    public void aiStep() {
        if (this.isAlive() && this.isEffectiveAi()) {
            setNoGravity(true);
            this.combatMode = this.getTarget() != null && getTarget().isAlive();
            this.moveControl = this.combatMode ? this.combatMoveControl : this.wanderMoveControl;
        }
        super.aiStep();
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == JAW_FLAG) {
            jawPointer += fetcher.get(0, Float.class) * BCMath.FRAD;
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance effectInstanceIn, @Nullable Entity entityIn) {
        if (EffectHelper.isBadAndOnlyAppliesToLiving(effectInstanceIn.getEffect())) return false;
        return super.addEffect(effectInstanceIn, entityIn);
    }

    @Override
    public float hurtCallOut(float damage, DamageSource damageSource) {
        if (!level.isClientSide) {
            updateClientJaw(25.0F);
        }
        return damage;
    }

    public void updateClientJaw(float openDegrees) {
        sendClientMsg(JAW_FLAG, WorldPacketData.of(BuiltinSerializers.FLOAT, openDegrees));
    }

    @Override
    public void playAmbientSound() {
        if (level.isClientSide) {
            SoundEvent soundevent = this.getAmbientSound();
            if (soundevent != null) {
                Vec3 position = position();
                level.playLocalSound(position.x, position.y, position.z, soundevent, SoundSource.NEUTRAL, this.getSoundVolume(), this.getVoicePitch(), false);
            }
            jawPointer += 50.0F * BCMath.FRAD;
        }
    }

    public boolean isCombatMode() {
        return this.combatMode;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return DESounds.ES_CURSED_SKULL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_CURSED_SKULL_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DESounds.ES_CURSED_SKULL_IDLE.get();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
}

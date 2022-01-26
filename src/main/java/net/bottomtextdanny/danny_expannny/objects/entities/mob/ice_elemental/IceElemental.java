package net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.StartCalloutAnimation;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.FrozenCriticalProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers.EasedLookControl;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers.FluentFlyingControl;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SmartyMob;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class IceElemental extends SmartyMob implements Enemy, ExtraMotionProvider, FrozenCriticalProvider {
    public static final float WANDER_MOVE_SPEED = 0.8F;
    public static final float COMBAT_MOVE_SPEED = 1.0F;
    public static final int ATTACK_DELAY_MIN = 50;
    public static final int ATTACK_DELAY_MAX = 65;
    public static final float FLEE_RANGE = 0.4F;
    public static final EntityDataReference<IntScheduler.Variable> ATTACKS_DELAY_REF =
            BCDataManager.attribute(HollowArmor.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(ATTACK_DELAY_MIN, ATTACK_DELAY_MAX),
                            "attack_delay")
            );
    private final FluentFlyingControl wanderMoveControl;
    private final FluentFlyingControl combatMoveControl;
    private ExtraMotionModule extraMotionModule;
    private StartCalloutAnimation iceSpikeAnimation;
    public final EntityData<IntScheduler.Variable> attackDelay;
    public float spikeAnimationRotationMultiplier;
    private boolean combatMode;

    public IceElemental(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.wanderMoveControl = new FluentFlyingControl(this, 180, true);
        this.moveControl = this.wanderMoveControl;
        this.combatMoveControl = new FluentFlyingControl(this, 180, false);
        this.lookControl = new EasedLookControl(this, () -> this.combatMode ? 90.0F : 25.0F, () -> this.combatMode ? 20.0F : 15.0F);
        this.attackDelay = bcDataManager().addNonSyncedData(EntityData.of(ATTACKS_DELAY_REF));
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
       // this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
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

    @Override
    protected void commonInit() {
        this.extraMotionModule = new ExtraMotionModule(this);
        this.iceSpikeAnimation = addAnimation(new StartCalloutAnimation(20, () -> this.spikeAnimationRotationMultiplier = this.random.nextBoolean() ? 1.0F : -1.0F));
    }

    @Override
    public Psyche<?> makePsyche() {
        return new IceElementalPsyche(this);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, this.level);
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
        if (this.random.nextInt(4) == 0) {
            double posOffsetX = this.random.nextGaussian() * 0.2;
            double posOffsetY = this.random.nextGaussian() * 0.2 + 0.25;
            double posOffsetZ = this.random.nextGaussian() * 0.2;
            this.level.addParticle(DEParticles.SNOWFLAKE.get(), this.getX() + posOffsetX, this.getY() + posOffsetY, this.getZ() + posOffsetZ, posOffsetX * 0.4, posOffsetY  * 0.4, posOffsetZ * 0.4);
        }
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            setNoGravity(true);
            this.combatMode = this.getTarget() != null && getTarget().isAlive();
            this.moveControl = this.combatMode ? this.combatMoveControl : this.wanderMoveControl;
        }
        super.aiStep();
    }

    public Animation getIceSpikeAnimation() {
        return this.iceSpikeAnimation;
    }

    public boolean isCombatMode() {
        return this.combatMode;
    }

    @Override
    public void move(MoverType p_19973_, Vec3 p_19974_) {
        super.move(p_19973_, moveHook(p_19974_));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return DESounds.ES_ICE_ELEMENTAL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_ICE_ELEMENTAL_DEATH.get();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}

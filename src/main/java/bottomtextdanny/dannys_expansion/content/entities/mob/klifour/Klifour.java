package bottomtextdanny.dannys_expansion.content.entities.mob.klifour;

import bottomtextdanny.dannys_expansion.content.entities.mob.SmartyMob;
import bottomtextdanny.dannys_expansion.tables.DEBlocks;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.entity.modules.animatable.*;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod.world.builtin_items.BCSpawnEggItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Klifour extends SmartyMob {
    private static final int TICK_INFORMATION_CALL_FLAG = 0;
    private static final int ATTACH_DIRECTION_CALL_FLAG = 1;
    public static final float SCRUB_REACH = 0.3F;
    public static final int HIDDEN_TIME_MIN = 120;
    public static final int HIDDEN_TIME_MAX = 280;
    public static final int UNHIDDEN_TIME_MIN = 150;
    public static final int UNHIDDEN_TIME_MAX = 300;
    public static final int SPIT_TIME_MIN = 80;
    public static final int SPIT_TIME_MAX = 100;
    public static final BCSpawnEggItem.SpawnLogic EGG_LOGIC = (type, world, direction, blockPos, itemStack, player) -> {
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos1;

        if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
            blockPos1 = blockPos;
        } else {
            blockPos1 = blockPos.relative(direction);
        }

        Klifour entity = (Klifour) type.spawn((ServerLevel)world, itemStack, player, blockPos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);

        if (entity != null) {
            entity.setAttachingDirection(direction.getOpposite());
            entity.syncAttachingDirection();
            itemStack.shrink(1);
        }
    };
    public static final EntityDataReference<IntScheduler.Ranged> HIDE_TIMER_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(HIDDEN_TIME_MIN, HIDDEN_TIME_MAX),
                            "hide_timer")
            );
    public static final EntityDataReference<IntScheduler.Ranged> UNHIDE_TIMER_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(UNHIDDEN_TIME_MIN, UNHIDDEN_TIME_MAX),
                            "unhide_timer")
            );
    public static final EntityDataReference<IntScheduler.Ranged> SPIT_TIMER_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(SPIT_TIME_MIN, SPIT_TIME_MAX),
                            "spit_timer")
            );
    public static final EntityDataReference<BlockPos> ATTACHING_LOCATION_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "attaching_location")
            );
    public static final EntityDataReference<Direction> ATTACHING_DIRECTION_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.DIRECTION,
                            () -> Direction.DOWN,
                            "attaching_direction")
            );
    public static final EntityDataReference<Boolean> HIDDEN_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "hidden")
            );
    public static final EntityDataReference<Boolean> NAUSEOUS_REF =
            BCDataManager.attribute(Klifour.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "nauseous")
            );
    public static final SimpleAnimation DEATH = new SimpleAnimation(20);
    public static final SimpleAnimation NAUSEA = new SimpleAnimation(54);
    public static final SimpleAnimation SHOW_UP = new SimpleAnimation(20);
    public static final SimpleAnimation HIDE = new SimpleAnimation(15);
    public static final SimpleAnimation SPIT = new SimpleAnimation(20);
    public static final SimpleAnimation SCRUB = new SimpleAnimation(20);
    public static final AnimationArray ANIMATIONS =
            new AnimationArray(DEATH, NAUSEA, SHOW_UP, HIDE, SPIT, SCRUB);
    public AnimationHandler<?> hideHandler;
    private final EntityData<IntScheduler.Ranged> hideTimer;
    private final EntityData<IntScheduler.Ranged> unhideTimer;
    private final EntityData<IntScheduler.Ranged> spitTimer;
    private final EntityData<BlockPos> attachingLocation;
    private final EntityData<Direction> attachingDirection;
    private final EntityData<Boolean> hidden;
    private final EntityData<Boolean> nauseous;

    public Klifour(EntityType<? extends Klifour> type, Level worldIn) {
        super(type, worldIn);
        xpReward = 5;
        this.hideTimer = bcDataManager().addNonSyncedData(EntityData.of(HIDE_TIMER_REF));
        this.unhideTimer = bcDataManager().addNonSyncedData(EntityData.of(UNHIDE_TIMER_REF));
        this.spitTimer = bcDataManager().addNonSyncedData(EntityData.of(SPIT_TIMER_REF));
        this.attachingLocation = bcDataManager().addNonSyncedData(EntityData.of(ATTACHING_LOCATION_REF));
        this.attachingDirection = bcDataManager().addSyncedData(EntityData.of(ATTACHING_DIRECTION_REF));
        this.hidden = bcDataManager().addNonSyncedData(EntityData.of(HIDDEN_REF));
        this.nauseous = bcDataManager().addNonSyncedData(EntityData.of(NAUSEOUS_REF));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void commonInit() {
        hideHandler = new AnimationHandler<>(this);
        addAnimationHandler(hideHandler);
    }

    @Override
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

    @Override
    public Psyche<?> makePsyche() {
        return new KlifourPsyche(this);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setLocation(Mth.floor(getX()), Mth.floor(getY()), Mth.floor(getZ()));
    }

    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == TICK_INFORMATION_CALL_FLAG) {
            boolean sHidden = fetcher.get(0, Boolean.class);
            setHidden(sHidden);
        } else if (flag == ATTACH_DIRECTION_CALL_FLAG) {
            Direction sDirection = fetcher.get(0, Direction.class);

            setAttachingDirection(sDirection);
        }
    }

    public void syncHiddenState() {
        sendClientMsg(TICK_INFORMATION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.BOOLEAN, isHidden()));
    }

    public void syncAttachingDirection() {
        sendClientMsg(Klifour.ATTACH_DIRECTION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.DIRECTION, getAttachingDirection()));
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (this.level.getBlockState(getAttachingBlock()).getBlock() == DEBlocks.PLANT_MATTER.get()) {
            List<Klifour> hangingKlifours = this.level.getEntitiesOfClass(Klifour.class, new AABB(getAttachingBlock()).inflate(1));

            boolean validator = true;

            for (Klifour klifour : hangingKlifours) {

                if (klifour != this && klifour.getAttachingBlock().equals(getAttachingBlock())) {
                    validator = false;
                    break;
                }
            }

            if (validator) {
                this.level.destroyBlock(getAttachingBlock(), false);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.25F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean hasLineOfSight(Entity entityIn) {
        return !isHidden() && super.hasLineOfSight(entityIn);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isAffectedByPotions() {
        return !isHidden();
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    protected void doPush(Entity entityIn) {
    }

    @Override
    public boolean isPickable() {
        return !isHidden();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
    }

    @Override
    public void updateEffectVisibility() {
        super.updateEffectVisibility();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_KLIFOUR_HURT.get();
    }

    @Nullable
    @Override
    public Animation<?> getDeathAnimation() {
        return DEATH;
    }

    //SETTERS

    public void setAttachingDirection(Direction value) {
        this.attachingDirection.set(value);
    }

    public void setHidden(boolean value) {
        this.hidden.set(value);
    }

    public void setNauseous(boolean value) {
        this.nauseous.set(value);
    }

    public void setLocation(int x, int y, int z) {
        this.attachingLocation.set(new BlockPos(x, y, z));
    }

    public void setLocation(BlockPos value) {
        this.attachingLocation.set(value);
    }

    //GETTERS

    public boolean hasBadEffects() {
        return getActiveEffects()
                .stream()
                .anyMatch(effectInstance -> effectInstance.getEffect().getCategory()
                == MobEffectCategory.HARMFUL);
    }

    public IntScheduler.Ranged getHideTimer() {
        return hideTimer.get();
    }

    public IntScheduler.Ranged getUnhideTimer() {
        return unhideTimer.get();
    }

    public IntScheduler.Ranged getSpitTimer() {
        return spitTimer.get();
    }

    public BlockPos getAttachingBlock() {
        return blockPosition().relative(getAttachingDirection());
    }

    public Direction getAttachingDirection() {
        return this.attachingDirection.get();
    }

    public boolean isHidden() {
        return this.hidden.get();
    }

    public boolean isNauseous() {
        return this.nauseous.get();
    }

    public BlockPos getAttachingLocation() {
        return this.attachingLocation.get();
    }

    public int getXLocation() {
        return this.attachingLocation.get().getX();
    }

    public int getYLocation() {
        return this.attachingLocation.get().getY();
    }

    public int getZLocation() {
        return this.attachingLocation.get().getZ();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}

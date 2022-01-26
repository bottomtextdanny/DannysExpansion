package net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.PlayAnimationGoal;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RammerEntity extends ModuledMob {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.APPLE, Items.GOLDEN_APPLE);
    public static final int LOVE_TICKS = 200;
    public static final int TRANSFORM_TICKS = 100;
    public static final int STUFFED_TICKS = 500;
    public static final int EAT_TICKS = 200;
    public static final EntityDataReference<IntScheduler.Simple> STUFFED_TIMER_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.INT_SCHEDULER,
                            () -> IntScheduler.simple(STUFFED_TICKS),
                            "stuffed_timer")
            );
    public static final EntityDataReference<Float> SIZE_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.FLOAT,
                            () -> 0.0F,
                            "size")
            );
    public static final EntityDataReference<Integer> TRANSFORMATION_TICKS_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "transformation_ticks")
            );
    public static final EntityDataReference<Integer> LOVE_TICKS_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.INTEGER,
                            () -> 0,
                            "love_ticks")
            );
    public static final EntityDataReference<Boolean> TRANSFORMING_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "transforming")
            );
    public static final EntityDataReference<Boolean> IN_LOVE_REF =
            BCDataManager.attribute(RammerEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "in_love")
            );
    public final EntityData<IntScheduler.Simple> stuffed_timer;
    public final EntityData<Float> size;
    public final EntityData<Integer> transforming_ticks;
    public final EntityData<Integer> love_ticks;
    public final EntityData<Boolean> is_transforming;
    public final EntityData<Boolean> is_in_love;
    public Animation ram;
    private boolean updatedSize;

    public RammerEntity(EntityType<? extends RammerEntity> type, Level worldIn) {
        super(type, worldIn);
        this.stuffed_timer = bcDataManager().addNonSyncedData(EntityData.of(STUFFED_TIMER_REF));
        this.size = bcDataManager().addNonSyncedData(EntityData.of(SIZE_REF));
        this.transforming_ticks = bcDataManager().addSyncedData(EntityData.of(TRANSFORMATION_TICKS_REF));
        this.love_ticks = bcDataManager().addSyncedData(EntityData.of(LOVE_TICKS_REF));
        this.is_transforming = bcDataManager().addNonSyncedData(EntityData.of(TRANSFORMING_REF));
        this.is_in_love = bcDataManager().addNonSyncedData(EntityData.of(IN_LOVE_REF));
        startUsingItem(InteractionHand.MAIN_HAND);
        this.setCanPickUpLoot(true);
    }

    protected void registerExtraGoals() {
        this.ram = addAnimation(new Animation(16));
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new RammerEntity.RammerBreedGoal());
        this.goalSelector.addGoal(1, new PlayAnimationGoal(this, this.ram, o -> hasAttackTarget() && this.mainAnimationHandler.isPlayingNull() && reachTo(getTarget()) < 1.5F));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
        this.goalSelector.addGoal(3, new RammerEntity.RammerPickAppleGoal());
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0F, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder Attributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }
	
	@Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.8F;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        setSize(1 + this.random.nextFloat() * 0.2F);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

    }

    public void setSize(float sizeIn) {
        this.size.set(sizeIn);
        this.updatedSize = false;
    }

    public void tick() {
        super.tick();
        if (!this.updatedSize) {
            refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttribute(Attributes.MAX_HEALTH).getBaseValue() * this.getSize());
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() * this.getSize());
            this.updatedSize = true;
        }


        boolean sendHearts = false;
        boolean sendWitch = false;
        if (!this.level.isClientSide()) {

            if (this.is_transforming.get() && isAlive()) {
                if (this.transforming_ticks.get() == 0) this.doTransform();

                this.transforming_ticks.set(this.transforming_ticks.get() - 1);

                if (this.random.nextFloat() < 0.1F) {
                    sendWitch = true;
                }
            }

            if (this.love_ticks.get() > 0) {
                this.love_ticks.set(this.love_ticks.get() - 1);

                if (this.random.nextInt(9) == 1) {
                    sendHearts = true;
                }
            }else {
                setLoveState(false);
            }

            if (hasAttackTarget()) {
                getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);

                if (this.mainAnimationHandler.isPlaying(this.ram) && this.mainAnimationHandler.getTick() == 5 && reachTo(getTarget()) < 2) {
                    this.sleepPathSchedule.sleepForNow();
                    Vec3 vec0 = Vec3.directionFromRotation(getRotationVector());
                    doHurtTarget(getTarget());
                    getTarget().push(vec0.x, 0.5, vec0.z);
                }
            }

            sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.BOOLEAN, sendHearts), WorldPacketData.of(BuiltinSerializers.BOOLEAN, sendWitch));
        }

        if (this.stuffed_timer.get().hasEnded()) {
            if (getMainHandItem().getItem() == Items.APPLE || getMainHandItem().getItem() == Items.GOLDEN_APPLE) {
                this.stuffed_timer.get().reset();
            }
        } else {
            this.stuffed_timer.get().advance();

            if (this.stuffed_timer.get().current() < EAT_TICKS) {
                Vec3 forward = Vec3.directionFromRotation(0, this.yBodyRot);
                double x = 1 * forward.x;
                double y = 0.575;
                double z = 1 * forward.z;

                if (this.random.nextInt(3) == 1) {
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, getMainHandItem()), getX() + x, getY() + y, getZ() + z, this.random.nextGaussian() * 0.07, this.random.nextGaussian() * 0.07, this.random.nextGaussian() * 0.07);
                }
            }

            for (int eatSoundTick = 0; eatSoundTick < 10; eatSoundTick++) {
                if (this.stuffed_timer.get().current() == eatSoundTick * 20) {
                    playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                }
            }

            if (this.stuffed_timer.get().current() == EAT_TICKS) {
                if (getMainHandItem().getItem() == Items.GOLDEN_APPLE) startTransforming();
                else if (getMainHandItem().getItem() == Items.APPLE) setLoveState(true);
                setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }


    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == 0) {
            if (fetcher.get(0, Boolean.class)) {
                this.level.addParticle(ParticleTypes.HEART, this.getX() + this.random.nextGaussian(), this.getY() + this.random.nextGaussian(), this.getZ() + this.random.nextGaussian(), 0.0, 0.0, 0.0);
            }

            if (fetcher.get(1, Boolean.class)) {
                this.level.addParticle(ParticleTypes.WITCH, this.getX() + this.random.nextGaussian(), this.getY() + this.random.nextGaussian(), this.getZ() + this.random.nextGaussian(), 0.0, 0.0, 0.0);
            }
        } else if (flag == 1) {
        	int count = fetcher.get(0, Integer.class);
            for (int i = 0; i < count; i++) {
                this.level.addParticle(fetcher.get(0, ParticleOptions.class), this.getX() + this.random.nextGaussian(), this.getY() + this.random.nextGaussian(), this.getZ() + this.random.nextGaussian(), 0.0, 0.0, 0.0);
            }
        } else if (flag == 2) {
            refreshDimensions();
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.stuffed_timer.get().hasEnded()) {
            if (itemstack.getItem() == Items.GOLDEN_APPLE) {
                this.consumeItemFromStack(player, itemstack);
                setItemInHand(InteractionHand.MAIN_HAND, Items.GOLDEN_APPLE.getDefaultInstance());
            } else if (itemstack.getItem() == Items.APPLE) {
                this.consumeItemFromStack(player, itemstack);
                setItemInHand(InteractionHand.MAIN_HAND, Items.APPLE.getDefaultInstance());
            }
        }
        return super.mobInteract(player, hand);
    }

    public void startTransforming() {
        this.is_transforming.set(true);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 720, 1));

        sendClientMsg(1, WorldPacketData.of(BuiltinSerializers.INTEGER, this.random.nextInt(10) + 10), WorldPacketData.of(BuiltinSerializers.PARTICLE_OPTIONS, ParticleTypes.HAPPY_VILLAGER));
    }
    
    public void doTransform() {
        GrandRammerEntity grandRammerEntity = new GrandRammerEntity(DEEntities.GRAND_RAMMER.get(), this.level);
        grandRammerEntity.setSize(this.getSize());
        grandRammerEntity.copyPosition(this);
        if (this.hasCustomName()) {
            grandRammerEntity.setCustomName(this.getCustomName());
            grandRammerEntity.setCustomNameVisible(this.isCustomNameVisible());
        }
        this.level.explode(this, getX(), getY(), getZ(), 2, Explosion.BlockInteraction.NONE);
        this.level.addFreshEntity(grandRammerEntity);
        this.remove(RemovalReason.DISCARDED);
    }

    public void setLoveState(boolean value) {
        this.love_ticks.set(value ? LOVE_TICKS : 0);
        this.is_in_love.set(value);
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }


    public float getSize() {
        return this.size.get();
    }

    public EntityDimensions getDimensions(Pose poseIn) {
        return super.getDimensions(poseIn).scale(getSize());
    }
    
    //********* reflection starts ***********

    @Override
    public boolean canPickUpLoot() {
        return this.stuffed_timer.get().hasEnded() && super.canPickUpLoot();
    }

    public boolean canTakeItem(ItemStack itemstackIn) {
        return itemstackIn.getItem() == Items.APPLE || itemstackIn.getItem() == Items.GOLDEN_APPLE;
    }

    private void spitOutItem(ItemStack stackIn) {
        if (!stackIn.isEmpty() && !this.level.isClientSide) {
            ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, stackIn);
            itementity.setPickUpDelay(40);
            itementity.setThrower(this.getUUID());
            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level.addFreshEntity(itementity);
        }
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (itemEntity.getItem().getItem() == Items.APPLE || itemEntity.getItem().getItem() == Items.GOLDEN_APPLE) {
            int i = itemstack.getCount();
            if (i > 1) EntityUtil.spawnItem(itemstack.split(i - 1), this.level, position());
            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(itemEntity, itemstack.getCount());
            itemEntity.remove(RemovalReason.DISCARDED);
        }

    }

    //********* reflection ends ***********

    class RammerBreedGoal extends Goal {
        protected RammerEntity rammerMate;
        protected int spawnBabyDelay;

        @Override
        public boolean canUse() {
            if (!RammerEntity.this.is_in_love.get()) return false;
            else {
                this.rammerMate = this.getNearbyMate();
                return this.rammerMate != null;
            }
        }

        public boolean canContinueToUse() {
            return this.rammerMate.isAlive() && this.rammerMate.is_in_love.get();
        }

        @Nullable
        private RammerEntity getNearbyMate() {
            TargetingConditions predicate = TargetingConditions.forNonCombat().range(8.0D);

            List<RammerEntity> list = RammerEntity.this.level.getNearbyEntities(RammerEntity.class, predicate, RammerEntity.this, getBoundingBox().inflate(8.0D));
            double d0 = Double.MAX_VALUE;
            RammerEntity rammerEntity = null;

            for(RammerEntity rammerEntity1 : list) {
                if (rammerEntity1.is_in_love.get() && distanceToSqr(rammerEntity1) < d0) {
                    rammerEntity = rammerEntity1;
                    d0 = distanceToSqr(rammerEntity1);
                }
            }

            return rammerEntity;
        }

        public void tick() {
            getNavigation().moveTo(this.rammerMate, 1F);
            if (reachTo(this.rammerMate) < 1.0D) this.spawnBaby();
        }

        protected void spawnBaby() {
            this.spawnBabyDelay++;

            if (this.spawnBabyDelay > 60) {
                ChildRammerEntity childRammerEntity = new ChildRammerEntity(DEEntities.CHILD_RAMMER.get(), RammerEntity.this.level);
                childRammerEntity.setPos(getX(), getY(), getZ());
                RammerEntity.this.level.addFreshEntity(childRammerEntity);
                setLoveState(false);
                this.rammerMate.setLoveState(false);

                this.spawnBabyDelay = 0;
            }
        }
    }

    class RammerPickAppleGoal extends Goal {
        public ItemEntity item;

        @Override
        public boolean canUse() {
            if (RammerEntity.this.stuffed_timer.get().hasEnded()) return false;
            List<ItemEntity> items = RammerEntity.this.level.getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(10, 4, 10), null);

            for (ItemEntity itemEntity : items) {
                if (itemEntity.getItem().getItem() == Items.APPLE || itemEntity.getItem().getItem() == Items.GOLDEN_APPLE) {
                    if (getSensing().hasLineOfSight(itemEntity)) {
                        this.item = itemEntity;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return getMainHandItem() != ItemStack.EMPTY;
        }

        @Override
        public void tick() {
            super.tick();
            getNavigation().moveTo(this.item, 1.1F);
        }
    }
}

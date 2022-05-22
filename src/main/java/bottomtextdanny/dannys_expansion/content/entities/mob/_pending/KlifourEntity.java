package bottomtextdanny.dannys_expansion.content.entities.mob._pending;

import bottomtextdanny.dannys_expansion.tables.DEBlocks;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.Animation;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import bottomtextdanny.braincell.mod.world.builtin_items.BCSpawnEggItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class KlifourEntity extends ModuledMob {
	public static final int TICK_INFORMATION_CALL_FLAG = 0;
	public static final int ATTACH_DIRECTION_CALL_FLAG = 1;
    public static final int HIDDEN_TIME_MIN = 120;
    public static final int HIDDEN_TIME_MAX = 280;
    public static final int UNHIDDEN_TIME_MIN = 150;
    public static final int UNHIDDEN_TIME_MAX = 300;
    public static final BCSpawnEggItem.SpawnLogic SL_KLIFOUR = (type, world, direction, blockPos, itemStack, player) -> {
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos1;

        if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
            blockPos1 = blockPos;
        } else {
            blockPos1 = blockPos.relative(direction);
        }

        KlifourEntity entity = (KlifourEntity) type.spawn((ServerLevel)world, itemStack, player, blockPos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);

        if (entity != null) {
            entity.setAttachingDirection(direction.getOpposite());
            entity.sendClientMsg(KlifourEntity.ATTACH_DIRECTION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.DIRECTION, direction.getOpposite()));
            itemStack.shrink(1);
        }
    };
    public static final EntityDataReference<IntScheduler.Ranged> HIDE_TIMER_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(HIDDEN_TIME_MIN, HIDDEN_TIME_MAX),
                            "hide_timer")
            );
    public static final EntityDataReference<IntScheduler.Ranged> UNHIDE_TIMER_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.RANGED_INT_SCHEDULER,
                            () -> IntScheduler.ranged(UNHIDDEN_TIME_MIN, UNHIDDEN_TIME_MAX),
                            "unhide_timer")
            );
    public static final EntityDataReference<BlockPos> ATTACHING_LOCATION_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "attaching_location")
            );
    public static final EntityDataReference<Direction> ATTACHING_DIRECTION_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.DIRECTION,
                            () -> Direction.DOWN,
                            "attaching_direction")
            );
    public static final EntityDataReference<Boolean> HIDDEN_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BOOLEAN,
                            () -> false,
                            "hidden")
            );
    public static final SimpleAnimation DEATH = new SimpleAnimation(20);
    public static final SimpleAnimation NAUSEA = new SimpleAnimation(54);
    public static final SimpleAnimation SHOW_UP = new SimpleAnimation(20);
    public static final SimpleAnimation HIDE = new SimpleAnimation(15);
    public static final SimpleAnimation SPIT = new SimpleAnimation(20);
    public static final SimpleAnimation SCRUB = new SimpleAnimation(20);
    public static final AnimationArray ANIMATIONS =
            new AnimationArray(DEATH, NAUSEA, SHOW_UP, HIDE, SPIT, SCRUB);
    private final EntityData<IntScheduler.Ranged> hideTimer;
    private final EntityData<IntScheduler.Ranged> unhideTimer;
    private final EntityData<BlockPos> attaching_location;
    private final EntityData<Direction> attaching_direction;
    private final EntityData<Boolean> hidden;
    public boolean isBadEffectAware;
    public int attackCount;
    public int blockUpdate;

    public KlifourEntity(EntityType<? extends KlifourEntity> type, Level worldIn) {
        super(type, worldIn);
        this.hideTimer = bcDataManager().addNonSyncedData(EntityData.of(HIDE_TIMER_REF));
        this.unhideTimer = bcDataManager().addNonSyncedData(EntityData.of(UNHIDE_TIMER_REF));
        this.attaching_location = bcDataManager().addNonSyncedData(EntityData.of(ATTACHING_LOCATION_REF));
        this.attaching_direction = bcDataManager().addSyncedData(EntityData.of(ATTACHING_DIRECTION_REF));
        this.hidden = bcDataManager().addNonSyncedData(EntityData.of(HIDDEN_REF));
//        this.rangedTimer = new Timer(60, baseBound -> baseBound + (int)(baseBound * this.random.nextGaussian() * 0.2F));
//        this.meleeTimer = new Timer(70);
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
    public AnimationGetter getAnimations() {
        return ANIMATIONS;
    }

//    protected void registerExtraGoals() {
//        this.goalSelector.addGoal(0, new KlifourEntity.NauseaGoal());
//        this.goalSelector.addGoal(1, new KlifourEntity.ScrubGoal());
//        this.goalSelector.addGoal(2, new KlifourEntity.SpitGoal());
//        this.goalSelector.addGoal(7, new KlifourEntity.KlifourLookRandomGoal());
//        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8F) {
//            @Override
//            public boolean canUse() {
//                return KlifourEntity.this.getTarget() == null && super.canUse();
//            }
//
//        });
//        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, e -> !PlayerHelper.hasAccessory((Player)e, KlifourTalismanAccessory.class)));
//    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }



//    @Override
//    public void tick() {
//        super.tick();
//
//        if (!this.level.isClientSide()) {
//	        if (isHidden()) {
//                this.hideTimer.get().advance();
//
//		        if (this.isBadEffectAware) {
//			        getActiveEffects().removeIf(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
//                    this.isBadEffectAware = false;
//			        updateEffectVisibility();
//		        }
//
//		        if (this.hideTimer.get().hasEnded()) {
//                    this.mainHandler.play(SHOW_UP);
//			        setHidden(false);
//                    this.unhideTimer.get().reset();
//		        }
//
//	        } else {
//                this.unhideTimer.get().advance();
//
//		        if (getTarget() != null) {
//			        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
//		        }
//
//		        if (this.unhideTimer.get().hasEnded() && this.mainHandler.isPlayingNull()) {
//			        if (getTarget() == null) {
//                        this.mainHandler.play(HIDE);
//                        this.hideTimer.get().reset();
//			        } else if (this.attackCount > 8) {
//                        this.mainHandler.play(HIDE);
//                        this.hideTimer.get().setCurrentBound(DEMath.intRandomOffset(80, 0.3F));
//                        this.hideTimer.get().reset();
//			        }
//
//		        }
//
//		        if (this.mainHandler.isPlaying(SPIT)) {
//
//			        if (true) {
//
//				        if (this.mainHandler.getTick() == 4)
//					        playSound(DESounds.ES_KLIFOUR_SPIT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
//
//				        if (hasAttackTarget()) {
//					        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
//
//					        if (this.mainHandler.getTick() == 6) {
//
//						        KlifourSpitEntity spit = DEEntities.KLIFOUR_SPIT.get().create(this.level);
//						        spit.setPosCore(getX(), getEyeY(), getZ());
//						        spit.setRotations(DEMath.getTargetYaw(this, getTarget()), DEMath.getTargetPitch(this, getTarget()));
//						        spit.setCaster(this);
//                                this.level.addFreshEntity(spit);
//					        }
//				        }
//
//			        }
//		        } else if (this.mainHandler.isPlaying(SCRUB)) {
//			        if (this.mainHandler.getTick() == 1)
//				        playSound(DESounds.ES_KLIFOUR_SCRUB.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
//			        else if (getTarget() != null && this.mainHandler.getTick() == 5 && this.getBoundingBox().intersects(getTarget().getBoundingBox().inflate(0.2)))
//				        this.getTarget().addEffect(new MobEffectInstance(MobEffects.POISON, 200, this.level.getDifficulty().getId() - 1));
//		        } else if (this.mainHandler.isPlaying(HIDE)) {
//			        if (this.mainHandler.getTick() == 1)
//				        playSound(DESounds.ES_KLIFOUR_HIDE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
//			        if (this.mainHandler.getTick() == 15) setHidden(true);
//		        } else if (this.mainHandler.isPlaying(SHOW_UP)) {
//			        if (this.mainHandler.getTick() == 1)
//				        playSound(DESounds.ES_KLIFOUR_SHOW_UP.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
//		        } else if (this.mainHandler.isPlaying(NAUSEA) && this.mainHandler.getTick() == 54) {
//                    this.isBadEffectAware = true;
//                    this.mainHandler.play(HIDE);
//		        }
//
//	        }
//            if (getAttachingLocation() == BlockPos.ZERO) setLocation(blockPosition());
//
//            if (getAttachingDirection().get3DDataValue() == 0) setPosCore(getXLocation() + 0.5F, getYLocation() + 0.0, getZLocation() + 0.5F);
//            else if (getAttachingDirection().get3DDataValue() == 1) setPosCore(getXLocation() + 0.5F, getYLocation() + 0.5, getZLocation() + 0.5F);
//            else setPosCore(getXLocation() + 0.5F, getYLocation() + 0.25, getZLocation() + 0.5F);
//            setNoGravity(true);
//
//            sendClientMsg(TICK_INFORMATION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.BOOLEAN, isHidden()));
//        }
//
//	    checkAttachingBlock();
//    }
	
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
	
	private void checkAttachingBlock() {
		if (!this.level.isClientSide()) {
		
		    if (this.blockUpdate > 0) this.blockUpdate--;
		     else {
		         if (this.level.getBlockState(blockPosition()).is(BlockTags.LEAVES)) {
                     this.level.destroyBlock(blockPosition(), false);
		         }
		
		         if (!this.level.getBlockState(getAttachingBlock()).getCollisionShape(this.level, getAttachingBlock()).equals(Shapes.block())) {
		             if (!this.mainHandler.isPlaying(DEATH)) this.mainHandler.play(DEATH);
		         }

                this.blockUpdate = 5;
		    }
		}
	}
	
	@Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (this.level.getBlockState(getAttachingBlock()).getBlock() == DEBlocks.PLANT_MATTER.get()) {
            List<KlifourEntity> hangingKlifours = this.level.getEntitiesOfClass(KlifourEntity.class, new AABB(getAttachingBlock()).inflate(1));

            boolean validator = true;

            for (KlifourEntity klifour : hangingKlifours) {

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
	public void animationEndCallout(AnimationHandler<?> module, Animation animation) {
		if (animation == DEATH)  {
			onDeathAnimationEnd();
			remove(RemovalReason.KILLED);
		} else if (animation == HIDE) {
			setHidden(true);
		} else if (animation == SHOW_UP) {
			setHidden(false);
		}
	}
	
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setLocation(Mth.floor(getX()), Mth.floor(getY()), Mth.floor(getZ()));
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.25F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return !isHidden() && super.hurt(source, amount);
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
        this.attaching_direction.set(value);
    }

    public void setHidden(boolean value) {
        this.hidden.set(value);
    }

    public void setLocation(int x, int y, int z) {
        this.attaching_location.set(new BlockPos(x, y, z));
    }

    public void setLocation(BlockPos value) {
        this.attaching_location.set(value);
    }

    //GETTERS

    public BlockPos getAttachingBlock() {
        return blockPosition().relative(getAttachingDirection());
    }

    public Direction getAttachingDirection() {
        return this.attaching_direction.get();
    }

    public boolean isHidden() {
        return this.hidden.get();
    }

    public BlockPos getAttachingLocation() {
        return this.attaching_location.get();
    }

    public int getXLocation() {
        return this.attaching_location.get().getX();
    }

    public int getYLocation() {
        return this.attaching_location.get().getY();
    }

    public int getZLocation() {
        return this.attaching_location.get().getZ();
    }

    //CLASSES

//    class SpitGoal extends Goal {
//
//        @Override
//        public void start() {
//            super.start();
//            KlifourEntity.this.mainHandler.play(KlifourEntity.SPIT);
//            KlifourEntity.this.rangedTimer.reset();
//            KlifourEntity.this.attackCount++;
//            LivingEntity target = KlifourEntity.this.getTarget();
//            if (KlifourEntity.this.distanceTo(target) > KlifourEntity.this.getAttributeValue(Attributes.FOLLOW_RANGE) && target.isSpectator() || target instanceof Player && ((Player) target).isCreative()) KlifourEntity.this.setTarget(null);
//
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        @Override
//        public boolean canUse() {
//            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainHandler.isPlayingNull() && KlifourEntity.this.getTarget() != null && KlifourEntity.this.rangedTimer.hasEnded() && hasLineOfSight(KlifourEntity.this.getTarget());
//        }
//    }
//
//    class ScrubGoal extends Goal {
//
//        @Override
//        public void start() {
//            super.start();
//            KlifourEntity.this.mainHandler.play(KlifourEntity.SCRUB);
//            KlifourEntity.this.meleeTimer.reset();
//            KlifourEntity.this.attackCount++;
//            LivingEntity target = KlifourEntity.this.getTarget();
//            if (KlifourEntity.this.distanceTo(target) > KlifourEntity.this.getAttributeValue(Attributes.FOLLOW_RANGE) && target.isSpectator() || target instanceof Player && ((Player) target).isCreative()) KlifourEntity.this.setTarget(null);
//
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        @Override
//        public boolean canUse() {
//            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainHandler.isPlayingNull() && KlifourEntity.this.getTarget() != null && KlifourEntity.this.meleeTimer.hasEnded() && KlifourEntity.this.getBoundingBox().intersects(KlifourEntity.this.getTarget().getBoundingBox());
//        }
//    }
//
//    class NauseaGoal extends Goal {
//
//        @Override
//        public void start() {
//            super.start();
//            KlifourEntity.this.mainHandler.play(NAUSEA);
//
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        @Override
//        public boolean canUse() {
//            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainHandler.isPlayingNull() && KlifourEntity.this.getActiveEffects().stream().anyMatch(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
//        }
//    }
//
//    class KlifourLookRandomGoal extends Goal {
//        private double lookX;
//        private double lookY;
//        private double lookZ;
//        private int idleTime;
//
//        public KlifourLookRandomGoal() {
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//        }
//
//        @Override
//        public void start() {
//            double d0 = Math.PI * 2 * KlifourEntity.this.getRandom().nextDouble();
//            this.lookX = DEMath.cos(d0);
//            this.lookY = 0.1 * KlifourEntity.this.getRandom().nextGaussian();
//            this.lookZ = DEMath.sin(d0);
//            this.idleTime = 20 + KlifourEntity.this.getRandom().nextInt(20);
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            return this.idleTime >= 0;
//        }
//
//        @Override
//        public boolean canUse() {
//            return !KlifourEntity.this.isHidden() && KlifourEntity.this.getTarget() == null && KlifourEntity.this.getRandom().nextFloat() < 0.02F;
//        }
//
//        @Override
//        public void tick() {
//            --this.idleTime;
//            KlifourEntity.this.getLookControl().setLookAt(KlifourEntity.this.getX() + this.lookX, KlifourEntity.this.getEyeY() + this.lookY, KlifourEntity.this.getZ() + this.lookZ);
//        }
//    }
}

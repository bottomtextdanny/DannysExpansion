package net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.BCSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.KlifourSpitEntity;
import net.bottomtextdanny.danny_expannny.objects.accessories.KlifourTalismanAccessory;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class KlifourEntity extends ModuledMob {
	public static final int TICK_INFORMATION_CALL_FLAG = 0;
	public static final int ATTACH_DIRECTION_CALL_FLAG = 1;
    public static final int HIDDEN_TIME_MIN = 120;
    public static final int HIDDEN_TIME_MAX = 280;
    public static final int UNHIDDEN_TIME_MIN = 150;
    public static final int UNHIDDEN_TIME_MAX = 300;
    public static final EntityDataReference<IntScheduler.Variable> HIDE_TIMER_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
                            () -> IntScheduler.ranged(HIDDEN_TIME_MIN, HIDDEN_TIME_MAX),
                            "hide_timer")
            );
    public static final EntityDataReference<IntScheduler.Variable> UNHIDE_TIMER_REF =
            BCDataManager.attribute(KlifourEntity.class,
                    RawEntityDataReference.of(
                            BCSerializers.VARIABLE_INT_SCHEDULER,
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
    private final EntityData<IntScheduler.Variable> hideTimer;
    private final EntityData<IntScheduler.Variable> unhideTimer;
    private final EntityData<BlockPos> attaching_location;
    private final EntityData<Direction> attaching_direction;
    private final EntityData<Boolean> hidden;
    public Animation death;
    public Animation nausea;
    public Animation showUp;
    public Animation hide;
    public Animation spit;
    public Animation scrub;
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
        this.rangedTimer = new Timer(60, baseBound -> baseBound + (int)(baseBound * this.random.nextGaussian() * 0.2F));
        this.meleeTimer = new Timer(70);
    }

	
	protected void registerExtraGoals() {
        this.death = addAnimation(new Animation(20));
        this.nausea = addAnimation(new Animation(54));
        this.showUp = addAnimation(new Animation(20));
        this.hide = addAnimation(new Animation(15));
        this.spit = addAnimation(new Animation(20));
        this.scrub = addAnimation(new Animation(20));
        this.goalSelector.addGoal(0, new KlifourEntity.NauseaGoal());
        this.goalSelector.addGoal(1, new KlifourEntity.ScrubGoal());
        this.goalSelector.addGoal(2, new KlifourEntity.SpitGoal());
        this.goalSelector.addGoal(7, new KlifourEntity.KlifourLookRandomGoal());
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8F) {
            @Override
            public boolean canUse() {
                return KlifourEntity.this.getTarget() == null && super.canUse();
            }

        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, e -> !PlayerHelper.hasAccessory((Player)e, KlifourTalismanAccessory.class)));
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
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }



    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide()) {
	        if (isHidden()) {
                this.hideTimer.get().advance();
		
		        if (this.isBadEffectAware) {
			        getActiveEffects().removeIf(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
                    this.isBadEffectAware = false;
			        updateEffectVisibility();
		        }
		
		        if (this.hideTimer.get().hasEnded()) {
                    this.mainAnimationHandler.play(this.showUp);
			        setHidden(false);
                    this.unhideTimer.get().reset();
		        }
		
	        } else {
                this.unhideTimer.get().advance();
		
		        if (getTarget() != null) {
			        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
		        }
		
		        if (this.unhideTimer.get().hasEnded() && this.mainAnimationHandler.isPlayingNull()) {
			        if (getTarget() == null) {
                        this.mainAnimationHandler.play(this.hide);
                        this.hideTimer.get().reset();
			        } else if (this.attackCount > 8) {
                        this.mainAnimationHandler.play(this.hide);
                        this.hideTimer.get().setCurrentBound(DEMath.intRandomOffset(80, 0.3F));
                        this.hideTimer.get().reset();
			        }
			
		        }
		
		        if (this.mainAnimationHandler.isPlaying(this.spit)) {
			
			        if (true) {
				
				        if (this.mainAnimationHandler.getTick() == 4)
					        playSound(DESounds.ES_KLIFOUR_SPIT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
				
				        if (hasAttackTarget()) {
					        getLookControl().setLookAt(getTarget(), 30.0F, 30.0F);
					
					        if (this.mainAnimationHandler.getTick() == 6) {
						
						        KlifourSpitEntity spit = DEEntities.KLIFOUR_SPIT.get().create(this.level);
						        spit.setPos(getX(), getEyeY(), getZ());
						        spit.setRotations(DEMath.getTargetYaw(this, getTarget()), DEMath.getTargetPitch(this, getTarget()));
						        spit.setCaster(this);
                                this.level.addFreshEntity(spit);
					        }
				        }
				
			        }
		        } else if (this.mainAnimationHandler.isPlaying(this.scrub)) {
			        if (this.mainAnimationHandler.getTick() == 1)
				        playSound(DESounds.ES_KLIFOUR_SCRUB.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
			        else if (getTarget() != null && this.mainAnimationHandler.getTick() == 5 && this.getBoundingBox().intersects(getTarget().getBoundingBox().inflate(0.2)))
				        this.getTarget().addEffect(new MobEffectInstance(MobEffects.POISON, 200, this.level.getDifficulty().getId() - 1));
		        } else if (this.mainAnimationHandler.isPlaying(this.hide)) {
			        if (this.mainAnimationHandler.getTick() == 1)
				        playSound(DESounds.ES_KLIFOUR_HIDE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
			        if (this.mainAnimationHandler.getTick() == 15) setHidden(true);
		        } else if (this.mainAnimationHandler.isPlaying(this.showUp)) {
			        if (this.mainAnimationHandler.getTick() == 1)
				        playSound(DESounds.ES_KLIFOUR_SHOW_UP.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
		        } else if (this.mainAnimationHandler.isPlaying(this.nausea) && this.mainAnimationHandler.getTick() == 54) {
                    this.isBadEffectAware = true;
                    this.mainAnimationHandler.play(this.hide);
		        }
		
	        }
            if (getAttachingLocation() == BlockPos.ZERO) setLocation(blockPosition());

            if (getAttachingDirection().get3DDataValue() == 0) setPos(getXLocation() + 0.5F, getYLocation() + 0.0, getZLocation() + 0.5F);
            else if (getAttachingDirection().get3DDataValue() == 1) setPos(getXLocation() + 0.5F, getYLocation() + 0.5, getZLocation() + 0.5F);
            else setPos(getXLocation() + 0.5F, getYLocation() + 0.25, getZLocation() + 0.5F);
            setNoGravity(true);

            sendClientMsg(TICK_INFORMATION_CALL_FLAG, WorldPacketData.of(BuiltinSerializers.BOOLEAN, isHidden()));
        }

	    checkAttachingBlock();
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
	
	private void checkAttachingBlock() {
		if (!this.level.isClientSide()) {
		
		    if (this.blockUpdate > 0) this.blockUpdate--;
		     else {
		         if (this.level.getBlockState(blockPosition()).is(BlockTags.LEAVES)) {
                     this.level.destroyBlock(blockPosition(), false);
		         }
		
		         if (!this.level.getBlockState(getAttachingBlock()).getCollisionShape(this.level, getAttachingBlock()).equals(Shapes.block())) {
		             if (!this.mainAnimationHandler.isPlaying(this.death)) this.mainAnimationHandler.play(this.death);
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
	public void animationEndCallout(AnimationHandler<?> module, IAnimation animation) {
		if (animation == this.death)  {
			onDeathAnimationEnd();
			remove(RemovalReason.KILLED);
		} else if (animation == this.hide) {
			setHidden(true);
		} else if (animation == this.showUp) {
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
    public IAnimation getDeathAnimation() {
        return this.death;
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

    class SpitGoal extends Goal {

        @Override
        public void start() {
            super.start();
            KlifourEntity.this.mainAnimationHandler.play(KlifourEntity.this.spit);
            KlifourEntity.this.rangedTimer.reset();
            KlifourEntity.this.attackCount++;
            LivingEntity target = KlifourEntity.this.getTarget();
            if (KlifourEntity.this.distanceTo(target) > KlifourEntity.this.getAttributeValue(Attributes.FOLLOW_RANGE) && target.isSpectator() || target instanceof Player && ((Player) target).isCreative()) KlifourEntity.this.setTarget(null);

        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public boolean canUse() {
            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainAnimationHandler.isPlayingNull() && KlifourEntity.this.getTarget() != null && KlifourEntity.this.rangedTimer.hasEnded() && hasLineOfSight(KlifourEntity.this.getTarget());
        }
    }

    class ScrubGoal extends Goal {

        @Override
        public void start() {
            super.start();
            KlifourEntity.this.mainAnimationHandler.play(KlifourEntity.this.scrub);
            KlifourEntity.this.meleeTimer.reset();
            KlifourEntity.this.attackCount++;
            LivingEntity target = KlifourEntity.this.getTarget();
            if (KlifourEntity.this.distanceTo(target) > KlifourEntity.this.getAttributeValue(Attributes.FOLLOW_RANGE) && target.isSpectator() || target instanceof Player && ((Player) target).isCreative()) KlifourEntity.this.setTarget(null);

        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public boolean canUse() {
            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainAnimationHandler.isPlayingNull() && KlifourEntity.this.getTarget() != null && KlifourEntity.this.meleeTimer.hasEnded() && KlifourEntity.this.getBoundingBox().intersects(KlifourEntity.this.getTarget().getBoundingBox());
        }
    }

    class NauseaGoal extends Goal {

        @Override
        public void start() {
            super.start();
            KlifourEntity.this.mainAnimationHandler.play(KlifourEntity.this.nausea);

        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public boolean canUse() {
            return !KlifourEntity.this.isHidden() && KlifourEntity.this.mainAnimationHandler.isPlayingNull() && KlifourEntity.this.getActiveEffects().stream().anyMatch(effectInstance -> effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL);
        }
    }

    class KlifourLookRandomGoal extends Goal {
        private double lookX;
        private double lookY;
        private double lookZ;
        private int idleTime;

        public KlifourLookRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public void start() {
            double d0 = Math.PI * 2 * KlifourEntity.this.getRandom().nextDouble();
            this.lookX = DEMath.cos(d0);
            this.lookY = 0.1 * KlifourEntity.this.getRandom().nextGaussian();
            this.lookZ = DEMath.sin(d0);
            this.idleTime = 20 + KlifourEntity.this.getRandom().nextInt(20);
        }

        @Override
        public boolean canContinueToUse() {
            return this.idleTime >= 0;
        }

        @Override
        public boolean canUse() {
            return !KlifourEntity.this.isHidden() && KlifourEntity.this.getTarget() == null && KlifourEntity.this.getRandom().nextFloat() < 0.02F;
        }

        @Override
        public void tick() {
            --this.idleTime;
            KlifourEntity.this.getLookControl().setLookAt(KlifourEntity.this.getX() + this.lookX, KlifourEntity.this.getEyeY() + this.lookY, KlifourEntity.this.getZ() + this.lookZ);
        }
    }
}
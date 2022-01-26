package net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.IAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.braincell.mod.world.builtin_sound_instances.EntityMovingSound;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class EnderBeastEntity extends ModuledMob implements Enemy {
	public static final int PLAY_STEP_SOUND_CALL = 0;
	public final AnimationHandler<EnderBeastEntity> jawModule = addAnimationHandler(new JawAnimationModule());
	public final Animation jawAnimation15 = addAnimation(new Animation(15));
	public final Animation jawAnimation20 = addAnimation(new Animation(20));
	public final Animation jawAnimation10 = addAnimation(new Animation(10));
	public final Animation jawAnimation5 = addAnimation(new Animation(5));
	protected NBLookController mgLookController = new NBLookController(this);
    public final Timer hurtColorTimer;
    private int hurtUpdateChecker;
   
    public EnderBeastEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
        getLivingSoundTimer().setBoundBase(100);
        this.lookControl = this.mgLookController;
        this.hurtColorTimer = new Timer(15).ended();
        this.hurtUpdateChecker = -1;
    }

	@Override
	protected void commonInit() {
		super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
	}

	@Override
    protected void registerExtraGoals() {
        super.registerExtraGoals();
        
       // this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.25));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtGoal(this, Player.class, 20.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, EnderBeastEntity.class).setAlertOthers());
    }

	protected BodyRotationControl createBodyControl() {
		return new BodyRotationControl(this);
	}
	
	@Override
	public LookControl getLookControl() {
		return this.mgLookController;
	}
	
	public int getMaxHeadYRot() {
		return 90;
	}

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }
	
	
	@Override
    public void tick() {
	    if (this.level.isClientSide()) {
	    	
	    	if (this.hurtUpdateChecker >= 0) {
	    		if (this.hurtUpdateChecker != this.hurtTime) {
                    this.hurtUpdateChecker = -2;
			    }
		    } else {
                this.hurtUpdateChecker = this.hurtTime;
		    }

            this.hurtColorTimer.tryUp();
		
		    if (this.hurtUpdateChecker == -2 && this.hurtTime == this.hurtDuration) {
                this.hurtColorTimer.reset();
		    }
	    }
	    
        super.tick();
        
        while (this.yHeadRot > 180.0F) {
            this.yHeadRot -= 360.0F;
        }
	
	    while (this.yHeadRot < -180.0F) {
            this.yHeadRot += 360.0F;
	    }
	
	    while (this.yBodyRot > 180.0F) {
			setYRot(this.yBodyRot -= 360.0F);
	    }
	
	    while (this.yBodyRot < -180.0F) {
			setYRot(this.yBodyRot += 360.0F);
	    }
	    
	    float offset = Mth.degreesDifference(this.yBodyRot, this.yHeadRot);
	    
	    if (!this.level.isClientSide()) {
		    if (offset > 90.0F) {
                this.yHeadRot = Mth.wrapDegrees(this.yBodyRot + 90.0F);
		    } else if (offset < -90.0F) {
                this.yHeadRot = Mth.wrapDegrees(this.yBodyRot - 90.0F);
		    }
		   // offset = Mth.degreesDifference(yBodyRot, yHeadRot);
	    }
    }
	
	@Override
	public void animateHurt() {
        this.hurtColorTimer.reset();
		super.animateHurt();
	}
	
	
	@Override
	public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
//    	if (flag == PLAY_STEP_SOUND_CALL) {
//            this.hurtColorTimer.reset();
//	    }
	}
	
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 2 || id == 33 || id == 36 || id == 37 || id == 44) {
            this.hurtColorTimer.reset();
		}
		super.handleEntityEvent(id);
	}
	
	@Override
	protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
  
		if (!this.level.isClientSide()) {
			//sendClientManagerMsg(0);
		}
		
    	super.actuallyHurt(damageSrc, damageAmount);
		
	}
	
	@Override
	public boolean isPushable() {
		return false;
	}
	
	public void push(Entity entityIn) {
		if (!this.isPassengerOfSameVehicle(entityIn)) {
			if (!entityIn.noPhysics && !this.noPhysics) {
				double d0 = entityIn.getX() - this.getX();
				double d1 = entityIn.getZ() - this.getZ();
				double d2 = Mth.absMax(d0, d1);
				if (d2 >= (double)0.01F) {
					d2 = Math.sqrt(d2);
					d0 = d0 / d2;
					d1 = d1 / d2;
					double d3 = 1.0D / d2;
					if (d3 > 1.0D) {
						d3 = 1.0D;
					}

					d0 = d0 * d3;
					d1 = d1 * d3;
					d0 = d0 * (double)0.05F;
					d1 = d1 * (double)0.05F;
					if (!this.isVehicle()) {
						this.push(-d0 / 4.0, 0.0D, -d1 / 4.0);
					}

					if (!entityIn.isVehicle()) {
						entityIn.push(d0 * 1.5, 0.0D, d1 * 1.5);
					}
				}
			}
		}
	}
	
	public void playLivingSound() {
    	if (!this.level.isClientSide()) {
		    SoundEvent soundevent;
		    float rng = this.random.nextFloat();
		    
		    if (hasAttackTarget()) {
		    	soundevent = DESounds.ES_ENDER_BEAST_MMH.get();
		    } else if (this.mgLookController.isLookingAtTarget()) {
		    	
		    	if (rng > 0.5F) {
				    soundevent = DESounds.ES_ENDER_BEAST_CONFUSION.get();
			    } else {
		    		if (rng > 0.2F) {
					    soundevent = DESounds.ES_ENDER_BEAST_GRUNT.get();
				    } else {
					    soundevent = DESounds.ES_ENDER_BEAST_MMH.get();
				    }
			    }
		    	if (rng > 0.95F) {
				    soundevent = DESounds.ES_ENDER_BEAST_LARGE_GRUNT.get();
			    }
		    	
		    } else {
			    if (rng > 0.95F) {
				    soundevent = DESounds.ES_ENDER_BEAST_LARGE_GRUNT.get();
			    } else {
				    soundevent = this.random.nextFloat() > 0.3F ? DESounds.ES_ENDER_BEAST_GRUNT.get() : DESounds.ES_ENDER_BEAST_MMH.get();
			    }
		    }
		    
		    if (soundevent == DESounds.ES_ENDER_BEAST_LARGE_GRUNT.get()) {
                this.jawModule.play(this.jawAnimation15);
		    } else {
                this.jawModule.play(this.jawAnimation10);
		    }
		
		    this.playSound(soundevent, 1.0F, 0.8F + this.random.nextInt(4) * 0.1F);
	    }
    	
	}
	
	@Nullable
	@Override
	public SoundEvent getLivingSound() {
		return DESounds.ES_ENDER_BEAST_GRUNT.get();
	}
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        this.jawModule.play(this.jawAnimation5);
		return DESounds.ES_ENDER_BEAST_HURT.get();
	}
	
	@Override
	public void playLoopStepSound(BlockPos pos, BlockState blockIn) {
		Connection.doClientSide(() -> playEnderBeastStepSound());
		super.playLoopStepSound(pos, blockIn);
    }

	@OnlyIn(Dist.CLIENT)
	protected void playEnderBeastStepSound() {
		Minecraft.getInstance().getSoundManager().play(
				EntityMovingSound.builder(this, DESounds.ES_ENDER_BEAST_STEP.get(), SoundSource.HOSTILE)
						.volume(0.7F * Math.min(loopedWalkModule().renderLimbSwingAmount * 11.0F, 1.0F))
						.pitch(0.4F + 0.1F * (float) this.random.nextInt(4))
						.distance(20.0F).build());
	}
	
	
	@Override
	public float getLoopWalkMultiplier() {
		return 0.385F;
	}
	
	@Override
	public float hurtLoopLimbSwingFactor() {
		return 0.07F;
	}
	
	static class Navigator extends GroundPathNavigation {
		public Navigator(Mob p_i50754_1_, Level p_i50754_2_) {
			super(p_i50754_1_, p_i50754_2_);
		}
		
		protected PathFinder createPathFinder(int p_179679_1_) {
			this.nodeEvaluator = new EnderBeastEntity.Processor();
			return new PathFinder(this.nodeEvaluator, p_179679_1_);
		}
	}
	
	static class Processor extends WalkNodeEvaluator {
		Processor() {
		}
		
		protected BlockPathTypes evaluateBlockPathType(BlockGetter p_215744_1_, boolean p_215744_2_, boolean p_215744_3_, BlockPos p_215744_4_, BlockPathTypes p_215744_5_) {
			return p_215744_5_ == BlockPathTypes.DANGER_FIRE ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_215744_1_, p_215744_2_, p_215744_3_, p_215744_4_, p_215744_5_);
		}
	}
	
	static class BodyRotationControl extends net.minecraft.world.entity.ai.control.BodyRotationControl {
		private final Mob mob;
		private int rotationTickCounter;
		private float prevRenderYawHead;
		
		public BodyRotationControl(Mob mob) {
			super(mob);
			this.mob = mob;
		}
		
		/**
		 * Update the Head and Body rendenring angles
		 */
		public void clientTick() {
			if (this.isMoving()) {
				this.mob.yBodyRot = this.mob.getYRot();
				this.rotateHeadIfNecessary();
				this.prevRenderYawHead = this.mob.yHeadRot;
				this.rotationTickCounter = 0;
			} else {
				if (this.notCarryingMobPassengers()) {
					if (Math.abs(this.mob.yHeadRot - this.prevRenderYawHead) > 15.0F) {
						this.rotationTickCounter = 0;
						this.prevRenderYawHead = this.mob.yHeadRot;
						this.rotateBodyIfNecessary();
					} else {
						++this.rotationTickCounter;
						if (this.rotationTickCounter > 10) {
							this.rotateHeadTowardsFront();
						}
					}
				}
				
			}
		}
		
		private void rotateBodyIfNecessary() {
			//this.mob.renderYawOffset = Mth.rotateIfNecessary(this.mob.renderYawOffset, this.mob.rotationYawHead, (float)this.mob.getHorizontalFaceSpeed());
		}
		
		private void rotateHeadIfNecessary() {
			//this.mob.rotationYawHead = Mth.rotateIfNecessary(this.mob.rotationYawHead, this.mob.renderYawOffset, (float)this.mob.getHorizontalFaceSpeed());
		}
		
		private void rotateHeadTowardsFront() {
			int i = this.rotationTickCounter - 10;
			float f = Mth.clamp((float)i / 10.0F, 0.0F, 1.0F);
			float f1 = (float)this.mob.getMaxHeadYRot() * (1.0F - f);
			//this.mob.renderYawOffset = Mth.rotateIfNecessary(this.mob.renderYawOffset, this.mob.rotationYawHead, f1);
		}
		
		private boolean notCarryingMobPassengers() {
			return this.mob.getPassengers().isEmpty() || !(this.mob.getPassengers().get(0) instanceof Mob);
		}
		
		private boolean isMoving() {
			double d0 = this.mob.getX() - this.mob.xo;
			double d1 = this.mob.getZ() - this.mob.zo;
			return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
		}
	}
	
	static class NBLookController extends LookControl {
		protected float targetYaw;
		protected float targetPitch;
		
		public NBLookController(Mob mob) {
			super(mob);
		}
		
		public void tick() {
			float targetYaw = this.getYRotD().get();
			float targetPitch = this.getXRotD().get();
			if (this.resetXRotOnTick()) {
				this.mob.setXRot(0.0F);
			}
			
			if (this.isLookingAtTarget()) {
				this.lookAtCooldown = 2;
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, targetYaw, this.yMaxRotSpeed);
				this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), targetPitch, this.xMaxRotAngle));
			}
			else {
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
			}
		}
		
		public void setTargetYaw(float targetYaw, float delta) {
			this.targetYaw = targetYaw;
			if (!isLookingAtTarget())this.lookAtCooldown = 2;
			this.yMaxRotSpeed = delta;
		}
		
		public void setTargetPitch(float targetPitch, float delta) {
			this.targetPitch = targetPitch;
			if (!isLookingAtTarget())this.lookAtCooldown = 2;
			this.xMaxRotAngle = delta;
		}
	}
	
	class LookAtGoal extends Goal {
		protected final Mob entity;
		protected Entity closestEntity;
		protected final float maxDistance;
		private int lookTime;
		protected final float chance;
		protected final Class<? extends LivingEntity> watchedClass;
		protected final TargetingConditions lookAtContext;
		
		public LookAtGoal(Mob entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
			this(entityIn, watchTargetClass, maxDistance, 0.02F);
		}
		
		public LookAtGoal(Mob entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chanceIn) {
			this.entity = entityIn;
			this.watchedClass = watchTargetClass;
			this.maxDistance = maxDistance;
			this.chance = chanceIn;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
			if (watchTargetClass == Player.class) {
				this.lookAtContext = TargetingConditions.forNonCombat().range(maxDistance).selector(target -> {
					float yawTo = DEMath.getTargetYaw(this.entity, target);
					if (Mth.degreesDifferenceAbs(EnderBeastEntity.this.yBodyRot, yawTo) > 110.0F) {
						return false;
					}

					return EntitySelector.notRiding(entityIn).test(target);
				});
			} else {
				this.lookAtContext = TargetingConditions.forNonCombat().range(maxDistance);
			}
			
		}
		
		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			if (this.entity.getRandom().nextFloat() >= this.chance) {
				return false;
			} else {
				if (this.entity.getTarget() != null) {
					this.closestEntity = this.entity.getTarget();
				}
				
				if (this.watchedClass == Player.class) {
					this.closestEntity = this.entity.level.getNearestPlayer(this.lookAtContext, this.entity, this.entity.getX(), this.entity.getEyeY(), this.entity.getZ());
				} else {
					this.closestEntity = this.entity.level.getNearestEntity(this.watchedClass, this.lookAtContext, this.entity, this.entity.getX(), this.entity.getEyeY(), this.entity.getZ(), this.entity.getBoundingBox().inflate(this.maxDistance, 3.0D, this.maxDistance));
				}
				
				return this.closestEntity != null;
			}
		}
		
		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean canContinueToUse() {
			if (!this.closestEntity.isAlive()) {
				return false;
			} else if (this.entity.distanceToSqr(this.closestEntity) > (double)(this.maxDistance * this.maxDistance)) {
				return false;
			} else {
				return this.lookTime > 0;
			}
		}
		
		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void start() {
			this.lookTime = 40 + this.entity.getRandom().nextInt(40);
		}
		
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void stop() {
			this.closestEntity = null;
		}
		
		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			this.entity.getLookControl().setLookAt(this.closestEntity.getX(), this.closestEntity.getEyeY(), this.closestEntity.getZ());
			--this.lookTime;
		}
	}
	
	private class JawAnimationModule extends AnimationHandler<EnderBeastEntity> {
		public JawAnimationModule() {
			super(EnderBeastEntity.this);
		}
		
		@Override
		public void play(IAnimation animation) {
			if (animation.getDuration() >= this.get().getDuration()) {
				super.play(animation);
			}
		}
	}
}

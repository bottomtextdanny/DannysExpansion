package bottomtextdanny.dannys_expansion.content.entities.mob._pending.squig;

import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion.content.entities.ai.controllers.MGLookController;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExternalMotion;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationArray;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import bottomtextdanny.braincell.mod.entity.modules.animatable.SimpleAnimation;
import bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedFormManager;
import bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import bottomtextdanny.braincell.mod.serialization.BCSerializers;
import bottomtextdanny.braincell.mod._base.serialization.builtin.BuiltinSerializers;
import bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import bottomtextdanny.braincell.mod.world.entity_utilities.EntityHurtAnimation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class SquigDeprecated extends ModuledMob implements EntityHurtAnimation, ExtraMotionProvider {
	public static final int UPDATE_ROTATIONS_CALL = 0;
	public static final int IMPULSE_TIMER_MIN = 48;
	public static final int IMPULSE_TIMER_MAX = 72;
	private static final SquigForm SKY_FORM = new SquigBlueForm();
	private static final SquigForm RED_FORM = new SquigRedForm();
	private static final SquigForm GREEN_FORM = new SquigGreenForm();
	private static final SquigForm PURPLE_FORM = new SquigPurpleForm();
	private static final SquigForm MELANISTIC_FORM = new SquigMelanisticForm();
	private static final IndexedFormManager FORMS = IndexedFormManager.builder()
			.add(SKY_FORM)
			.add(RED_FORM)
			.add(GREEN_FORM)
			.add(PURPLE_FORM)
			.add(MELANISTIC_FORM)
			.create();
	public static final EntityDataReference<IntScheduler.Ranged> IMPULSE_TIMER_REF =
			BCDataManager.attribute(SquigDeprecated.class,
					RawEntityDataReference.of(
							BCSerializers.RANGED_INT_SCHEDULER,
							() -> IntScheduler.ranged(IMPULSE_TIMER_MIN, IMPULSE_TIMER_MAX),
							"impulse_timer")
			);
	public static final EntityDataReference<Float> SQUIG_YAW_REF =
			BCDataManager.attribute(SquigDeprecated.class,
					RawEntityDataReference.of(
							BuiltinSerializers.FLOAT,
							() -> 0.0F,
							"squig_yaw")
			);
	public static final SimpleAnimation HURT = new SimpleAnimation(12);
	public static final SimpleAnimation MOVE = new SimpleAnimation(20);
	public static final AnimationArray ANIMATIONS = new AnimationArray(HURT, MOVE);
	private final MGLookController mgLookController = new LocLookController(this);
	public final EntityData<IntScheduler.Ranged> impulseTimer;
	public final EntityData<Float> squigYaw;
	private ExtraMotionModule motionUtilModule;
	public AnimationHandler<SquigDeprecated> hurtModule;
	public float renderSquigYaw, prevRenderSquigYaw;
	public float prevYRotOff, yRotOff, yRotOffPointer;
	private boolean attackSwitch;
	private ExternalMotion goMotion;
	
	public SquigDeprecated(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
		this.squigYaw = bcDataManager().addSyncedData(EntityData.of(SQUIG_YAW_REF));
		this.impulseTimer = bcDataManager().addSyncedData(EntityData.of(IMPULSE_TIMER_REF));
        this.lookControl = this.mgLookController;
       // this.livingSoundTimer = new Timer(500, b -> DEMath.intRandomOffset(b, 0.4F));
        this.xpReward = 10;
	}
	
	public static AttributeSupplier.Builder Attributes() {
		return createMobAttributes()
			.add(Attributes.MAX_HEALTH, 60.0D)
			.add(Attributes.ARMOR, 5.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.25D)
			.add(Attributes.FOLLOW_RANGE, 40.0D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 0.9D);
	}

	@Override
	protected void commonInit() {
		super.commonInit();
		this.variableModule = new IndexedVariableModule(this, FORMS);
		this.motionUtilModule = new ExtraMotionModule(this);
        this.goMotion = addCustomMotion(new ExternalMotion(0.96F));
		this.hurtModule = addAnimationHandler(new AnimationHandler<>(this));
	}

	@Override
	public AnimationGetter getAnimations() {
		return ANIMATIONS;
	}

	@Override
	public ExtraMotionModule extraMotionModule() {
		return this.motionUtilModule;
	}

	@Override
	public void move(MoverType typeIn, Vec3 pos) {
		super.move(typeIn, moveHook(pos));
	}

//	@Override
//	protected void registerExtraGoals() {
//		this.goalSelector.addGoal(0, new SquigEntity.LookRandomGoal());
//		this.targetSelector.addGoal(1, new SquigEntity.HurtByTargetGoal(this));
//	}
	
	@Override
	public LookControl getLookControl() {
		return this.mgLookController;
	}
	
	@Override
	public float getEyeHeight(Pose pose) {
		return super.getEyeHeight(pose);
	}
	
//	@Override
//	public void tick() {
//		super.tick();
//		setNoGravity(true);
//        this.fallDistance = 0;
//
//        this.prevRenderSquigYaw = this.renderSquigYaw;
//        this.renderSquigYaw = Mth.rotLerp(0.5F, this.renderSquigYaw, this.squigYaw.get());
//
//		if (!this.level.isClientSide) {
//            this.impulseTimer.get().advance();
//
//
//			sendClientMsg(UPDATE_ROTATIONS_CALL,
//					PacketDistributor.TRACKING_ENTITY.with(() -> this),
//					WorldPacketData.of(BuiltinSerializers.FLOAT, this.squigYaw.get())
//			);
//		}
//
//		if (isEffectiveAi() && this.mainHandler.isPlayingNull() && this.impulseTimer.get().hasEnded()) {
//
//			Vec3 ray = position().add(DEMath.fromPitchYaw(getXRot(), this.squigYaw.get()).scale(1.5F));
//
//			if (canBlockBeSeen(new BlockPos(ray))) {
//                this.mainHandler.play(MOVE);
//                this.impulseTimer.get().reset();
//			}
//		}
//
//		if (this.mainHandler.isPlaying(MOVE)) {
//			Connection.doClientSide(() -> onClientJump());
//			if (this.mainHandler.getTick() == 9) {
//				Vec3 look = DEMath.fromPitchYaw(getXRot(), this.squigYaw.get()).scale(0.32);
//
//				playSound(DESounds.ES_SQUIG_JUMP.get(), 1.5F, 1.0F + (float) this.random.nextInt(3) * 0.1F);
//
//				if (isEffectiveAi()) {
//                    this.goMotion.addMotion(look.scale(getAttributeValue(Attributes.MOVEMENT_SPEED) * 3.0F));
//					Vec3 pos = position();
//					if (hasAttackTarget()) {
//						if (this.attackSwitch) {
//							int rgn = this.random.nextInt(4) + 1;
//							for (int i = 0; i < rgn; i++) {
//								Vec3 move = Vec3.directionFromRotation(getXRot() + 20.0F * (float) this.random.nextGaussian(), this.squigYaw.get() + 20.0F * (float) this.random.nextGaussian()).scale(-1.0);
//								Vec3 mot = move.scale(0.45 + 0.25 * this.random.nextGaussian());
//
//								SquigBubbleEntity bubble = new SquigBubbleEntity(DEEntities.SQUIG_BUBBLE.get(), this.level);
//								bubble.setCaster(this);
//								bubble.setBobbleDamage(1.5F);
//								bubble.setTarget(getTarget());
//								bubble.absMoveTo(pos.x + move.x, pos.y + 0.31 + move.y, pos.z + move.z, 0.0F, 0.0F);
//								bubble.hurtMotion.addMotion(mot.x, mot.y, mot.z);
//                                this.level.addFreshEntity(bubble);
//							}
//                            this.attackSwitch = false;
//						} else {
//                            this.attackSwitch = true;
//						}
//
//					}
//				}
//
//			} else if (this.mainHandler.getTick() == 11) {
//                this.yRotOffPointer += 260.0F + (float) this.random.nextInt(150);
//			}
//		}
//
//        this.prevYRotOff = this.yRotOff;
//        this.yRotOff = Mth.lerp(0.1F, this.yRotOff, this.yRotOffPointer);
//	}
	
	@OnlyIn(Dist.CLIENT)
	public void onClientJump() {
		ClientLevel clientLevel = (ClientLevel) this.level;

		if (this.mainHandler.getTick() != 9) return;

		SquigForm form = (SquigForm) variableModule().getForm();

		if (form == null) return;

		Vec3 pos = position();
		Vec3 lookVec = Vec3.directionFromRotation(getXRot(), this.renderSquigYaw).scale(-1.5);
		Vec3 ringPos = pos.add(lookVec).add(0.0, 0.31, 0.0);

		DannyParticleData ringParticle = new DannyParticleData(DEParticles.SQUIG_RING, form.getRingSprite(), this.renderSquigYaw - 180.0F, -getXRot());
		DannyParticleData crossData = new DannyParticleData(DEParticles.SQUIG_CROSS, form.getCrossSprite());

		clientLevel.addParticle(ringParticle, ringPos.x, ringPos.y, ringPos.z, lookVec.x * 0.2, lookVec.y * 0.2, lookVec.z * 0.2);

		int rgn = this.random.nextInt(4) + 3;

		for (int i = 0; i < rgn; i++) {
			Vec3 move = Vec3.directionFromRotation(getXRot() + 30.0F * (float) this.random.nextGaussian(), this.squigYaw.get() + 30.0F * (float) this.random.nextGaussian()).scale(-1.0);
			Vec3 mot = move.scale(0.25 + 0.15 * this.random.nextGaussian());

            clientLevel.addParticle(crossData, pos.x + move.x, pos.y + 0.31 + move.y, pos.z + move.z, mot.x, mot.y, mot.z);
		}
	}
	
	@Override
	public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
		if (flag == UPDATE_ROTATIONS_CALL) {
            this.squigYaw.set(fetcher.get(0, Float.class));
		}
	}
	
	@Override
	public void animateHurt() {
		super.animateHurt();
		
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 2.0F) {
            this.impulseTimer.get().end();
		}
		return super.hurt(source, amount);
	}
	
	@Override
	public boolean skipAttackInteraction(Entity entityIn) {
		
		return super.skipAttackInteraction(entityIn);
	}
	
	@Override
	public void playLivingSound() {
		SoundEvent soundevent = this.getLivingSound();
		if (soundevent != null) {
			this.playSound(soundevent, 0.5F, 0.8F + this.random.nextFloat() * 0.4F);
		}
	}
	
	@Nullable
	@Override
	public SoundEvent getLivingSound() {
		return DESounds.ES_SQUIG_IDLE.get();
	}
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DESounds.ES_SQUIG_HURT.get();
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}
	
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	
	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}
	
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return true;
	}
	
	@Override
	public Form<?> chooseVariant() {
		int rng = this.random.nextInt(100);
		
		if (rng <= 50) {
			return SKY_FORM;
		} else if (rng <= 69) {
			return RED_FORM;
		} else if (rng <= 88) {
			return GREEN_FORM;
		} else if (rng <= 95) {
			return PURPLE_FORM;
		} else {
			return MELANISTIC_FORM;
		}
	}

	@Override
	public void playHurtAnimation(float damage, DamageSource source) {
        this.hurtModule.play(HURT);
	}

//	private float[] bestLook(int tries, Function<Vec3, Float> pos) {
//		float[] bestLook = {0.0F, 90.0F};
//		boolean validation = false;
//
//		for (int i = 0; i < tries; i++) {
//			float newPitch = 180.0F * this.random.nextFloat() - 90.0F;
//			float newYaw = this.random.nextFloat() * 360.0F - 180.0F;
//			Vec3 ray = position().add(DEMath.fromPitchYaw(newPitch, newYaw));
//
//
//			if (canBlockBeSeen(new BlockPos(ray))) {
//
//				Vec3 bestVec = position().add(DEMath.fromPitchYaw(bestLook[0], bestLook[1]));
//
//				float process = pos.apply(ray);
//				float current = pos.apply(bestVec);
//
//				if (process < current) {
//					validation = true;
//					bestLook = new float[] {newPitch, newYaw};
//				}
//			}
//		}
//
//		if (!validation) return null;
//		return bestLook;
//	}
//

//
//	class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
//		public HurtByTargetGoal(SquigEntity squig) {
//			super(squig);
//		}
//
//		/**
//		 * Returns whether an in-progress EntityAIBase should continue executing
//		 */
//		public boolean canContinueToUse() {
//
//			return super.canContinueToUse();
//		}
//	}
//
//	class LookRandomGoal extends Goal {
//
//		public LookRandomGoal() {
//			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
//		}
//
//		@Override
//		public void start() {
//			float newPitch;
//			float newYaw;
//
//
//				if (hasAttackTarget()) {
//
//					float dist = targetDistance(distanceTo(getTarget()));
//					float range = (float)getAttributeValue(Attributes.FOLLOW_RANGE);
//					if (dist > range * 0.5) {
//						float[] bestLook = bestLook(5, vec -> DEMath.getDistance(vec, getTarget().position()));
//
//						if (bestLook != null) {
//                            SquigEntity.this.mgLookController.setTargetPitch(bestLook[0], 3.0F);
//                            SquigEntity.this.mgLookController.setTargetYaw(bestLook[1], 3.0F);
//
//							return;
//						}
//					} else if (dist < range * 0.25) {
//						float[] bestLook = bestLook(5, vec -> Float.MAX_VALUE - DEMath.getDistance(vec, getTarget().position()));
//
//						if (bestLook != null) {
//                            SquigEntity.this.mgLookController.setTargetPitch(bestLook[0], 3.0F);
//                            SquigEntity.this.mgLookController.setTargetYaw(bestLook[1], 3.0F);
//
//
//							return;
//						}
//					}
//				}
//
//				HoverProfile prof = new HoverProfile(SquigEntity.this, 20, true);
//				prof.update(SquigEntity.this.position());
//
//				if (prof.isGroundLower(18)) {
//					newPitch = 40.0F * SquigEntity.this.random.nextFloat() + 40.0F;
//				} else if (!prof.isGroundLower(7)) {
//					newPitch = -40.0F * SquigEntity.this.random.nextFloat() - 40.0F;
//				} else {
//					newPitch = 120.0F * SquigEntity.this.random.nextFloat() - 60.0F;
//				}
//
//				newYaw = SquigEntity.this.random.nextFloat() * 360.0F - 180.0F;
//
//				Vec3 ray = position().add(DEMath.fromPitchYaw(newPitch, newYaw).scale(1.5));
//
//				if (canBlockBeSeen(new BlockPos(ray))) {
//                    SquigEntity.this.mgLookController.setTargetPitch(newPitch, 3.0F);
//                    SquigEntity.this.mgLookController.setTargetYaw(newYaw, 3.0F);
//				} else {
//
//					for (int i = 0; i < 10; i++) {
//						newPitch = 180.0F * SquigEntity.this.random.nextFloat() - 90.0F;
//						newYaw = SquigEntity.this.random.nextFloat() * 360.0F - 180.0F;
//						ray = position().add(DEMath.fromPitchYaw(newPitch, newYaw).scale(1));
//						if (canBlockBeSeen(new BlockPos(ray))) {
//                            SquigEntity.this.mgLookController.setTargetPitch(newPitch, 3.0F);
//                            SquigEntity.this.mgLookController.setTargetYaw(newYaw, 3.0F);
//							break;
//						}
//					}
//				}
//
//		}
//
//		@Override
//		public boolean canContinueToUse() {
//			return false;
//		}
//
//		@Override
//		public boolean canUse() {
//
//			return !SquigEntity.this.mgLookController.isLookingAtTarget();
//		}
//	}

	
	public class LocLookController extends MGLookController {
		
		public LocLookController(Mob mob) {
			super(mob);
            this.yMaxRotSpeed = 5.0F;
		}
		
		public void tick() {
			float targetYaw = this.getYRotD().get();
			float targetPitch = this.getXRotD().get();

			if (this.isLookingAtTarget()) {
				if ((int) SquigDeprecated.this.squigYaw.get().floatValue() == (int)targetYaw) {
					this.lookAtCooldown = 0;
				}

				this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), targetPitch, this.xMaxRotAngle));
				
				float newYaw = DEMath.approachDegrees(this.yMaxRotSpeed, SquigDeprecated.this.squigYaw.get(), targetYaw);

				this.mob.setYRot(newYaw);
				this.mob.yHeadRot = newYaw;

                SquigDeprecated.this.squigYaw.set(newYaw);
				
			}
		}
	}
}


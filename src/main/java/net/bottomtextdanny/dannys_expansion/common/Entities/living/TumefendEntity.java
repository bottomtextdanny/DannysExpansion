package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationGetter;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers.MGLookController;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.ActionGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.pathnavigators.DEFlyingNavigator;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.aigimmicks.AIState;
import net.bottomtextdanny.dannys_expansion.core.Util.aigimmicks.HoverProfile;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.IFlapper;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.base.pl.TumefendLight;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class TumefendEntity extends ModuledMob implements IFlapper, ExtraMotionProvider {
    public static final EntityDataReference<BlockPos> HOME_REF =
            BCDataManager.attribute(TumefendEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "home")
            );
    public static final SimpleAnimation PUMP = new SimpleAnimation(30);
    public static final SimpleAnimation ATTACK = new SimpleAnimation(14);
    public static final AnimationManager ANIMATIONS = new AnimationManager(PUMP, ATTACK);
    public final EntityData<BlockPos> home;
    private final PathNavigation flapNavigator;
    public AnimationHandler<TumefendEntity> attackModule;
    private AIState aiState = AIState.WANDERING;
    private Vec3 lightPosition = Vec3.ZERO;
    private ExternalMotion upMotion;
    private float flapStrength;
    public double prevRenderMovement;
    public double renderMovement;
    public float prevRenderYawRot;
    public float renderYawRot;
    @OnlyIn(Dist.CLIENT)
    private TumefendLight pumpingLight;
    private ExtraMotionModule motionUtilModule;

    public TumefendEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.home = bcDataManager().addNonSyncedData(EntityData.of(HOME_REF));
        this.flapNavigator = new DEFlyingNavigator(this, this.level);
        this.navigation = this.flapNavigator;
        this.moveControl = new TumefendEntity.InController(this);
        this.lookControl = new MGLookController(this);
        this.meleeTimer = new Timer(25);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.motionUtilModule = new ExtraMotionModule(this);
        this.upMotion = addCustomMotion(new ExternalMotion(0.75F));
        this.attackModule = addAnimationHandler(new AnimationHandler<>(this));
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

    protected void registerExtraGoals() {

        this.goalSelector.addGoal(0, new ActionGoal(this, o -> ifCollisionMeleeParamsAnd(target -> reachTo(target) < 1.0F && this.attackModule.isPlayingNull()), o -> this.attackModule.play(ATTACK)));
        this.goalSelector.addGoal(1, new FollowTargetGoal(this, 1.8d, 0, 12){
            @Override
            public void tick() {
                super.tick();
                TumefendEntity.this.aiState = AIState.AT_MELEE;
            }
        });
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.FLYING_SPEED, 1.5F)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 13.0D);
    }

    protected PathNavigation createNavigation(Level worldIn) {
        return this.flapNavigator;
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : -1.0F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return super.getMaxSpawnClusterSize();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return (spawnReasonIn != MobSpawnType.NATURAL || CapabilityHelper.get((Level)worldIn, LevelCapability.CAPABILITY).getPhaseModule().getPhase().ordinal() > 0) && !worldIn.getBlockState(getBlockPosBelowThatAffectsMyMovement()).isAir();
    }

    //
    //***LOGIC START***//


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (this.home.get().getX() == 0 && this.home.get().getY() == 0 && this.home.get().getZ() == 0) {
            this.home.set(blockPosition());
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void tick() {
        super.tick();

        setNoGravity(true);
        this.fallDistance = 0;

        if (!hasAttackTarget() && this.navigation.isDone()) this.aiState = AIState.WANDERING;

        if (this.level.isClientSide()) {
            this.prevRenderYawRot = this.renderYawRot;
            this.prevRenderMovement = this.renderMovement;
            Connection.doClientSide(() -> handlePumpingLight());
            this.renderYawRot = (float) Mth.lerp(0.2, this.renderYawRot, DEMath.getTargetYaw(this, this.xOld, this.zOld));

            this.renderMovement = Mth.lerp(0.2, this.renderMovement, Math.min(DEMath.getDistance(this, this.xOld, this.yOld, this.zOld) * 6, 1));
        } else {
        	
            if (this.mainHandler.isPlaying(PUMP)) {
            	if (this.mainHandler.getTick() == 5) {
	                playSound(DESounds.ES_TUMEFEND_HOP.get(), 0.7F, 1.0F);
                    this.upMotion.setMotion(0, this.flapStrength, 0);

                } else if (this.mainHandler.getTick() == 8) {
                    sendClientMsg(1);
                }
            }

            if (this.mainHandler.isPlaying(ATTACK) && this.attackModule.getTick() == 4) {
                doHurtTarget(getTarget());
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    private void handlePumpingLight() {
    	if (this.pumpingLight == null) {
            this.pumpingLight = new TumefendLight(this.level, pl -> {
			    return !(this.isAlive() && this.isAddedToWorld());
		    }, 20);
            this.pumpingLight.addToWorld();
	    } else {
            this.pumpingLight.setPosition(position().add(0.0, 0.5, 0.0));

            this.pumpingLight.setLight(new SimplePointLight(new Vec3(0.75, 0, 1), 4, 1.5F, 1.0F));
	    }
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
    }

    @Override
    public void flap(float strenght) {
        this.flapStrength = strenght;
        this.mainHandler.play(PUMP);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == 0) {
            ((MGLookController)getLookControl()).setTargetYaw(fetcher.get(0, Float.class), 10.0F);
        } else if (flag == 1) {
            this.level.addParticle(new DannyParticleData(DEParticles.TUMEFEND_HOP), getX(), getY() - 0.2F, getZ(), 0D, -0.1D, 0D);
        }
    }

    //***LOGIC END***//
    //
    //***SETTINGS START***//

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_GENERIC_MEAT_HIT.get();
    }

    @Override
    public int getHeadRotSpeed() {
        return isWanderingAI() ? 50 : 70;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public boolean canFlap() {
        return this.mainHandler.isPlayingNull();
    }
    
    //***SETTINGS END***//
    //
    //***UTILITY START***//

    public boolean isWanderingAI() {
        return this.aiState == AIState.WANDERING;
    }

    //***UTILITY END***//
    //
    //***COMMON SETTERS START***//

    public void setUpMotion(ExternalMotion upMotion) {
        this.upMotion = upMotion;
    }

    public void setLightPosition(Vec3 lightPosition) {
        this.lightPosition = lightPosition;
    }

    //***COMMON SETTERS END***//
    //
    //***COMMON GETTERS START***//

    public ExternalMotion getUpMotion() {
        return this.upMotion;
    }

    public AIState getAIState() {
        return this.aiState;
    }

    public Vec3 getLightPosition() {
        return this.lightPosition;
    }

    //***COMMON GETTERS END***//

    public class InController extends MoveControl {
        private final HoverProfile profile;
        private final Timer findFreeDelay = new Timer(20);
        private final Timer collisionDelay = new Timer(40);
        private final Timer pathfindDelay;

        public <E extends Mob & IFlapper> InController(E mob) {
            super(mob);
            this.profile = new HoverProfile(mob, 11, true);
            this.pathfindDelay = new Timer(20);
        }

        public void tick() {
            this.pathfindDelay.tryUp();

            if (this.operation == MoveControl.Operation.MOVE_TO) {


                if (TumefendEntity.this.aiState == AIState.WANDERING_DIR && this.mob.getBoundingBox().intersects(new AABB(TumefendEntity.this.home.get(), TumefendEntity.this.home.get()).inflate(25, 5, 25))) {
                    this.operation = MoveControl.Operation.WAIT;
                    TumefendEntity.this.aiState = AIState.WANDERING;
                    return;
                }

                this.operation = MoveControl.Operation.WAIT;
                this.mob.setNoGravity(true);
                double yDif = this.wantedY - this.mob.getY();

                float yawToTarget = DEMath.getTargetYaw(this.mob, this.wantedX, this.wantedZ);
                float pitchToTarget = DEMath.getTargetPitch(this.mob, this.wantedX, this.wantedY, this.wantedZ);
                Vec3 fromPitch = DEMath.fromPitchYaw(pitchToTarget, yawToTarget);

                this.mob.yHeadRot = this.rotlerp(this.mob.yHeadRot, yawToTarget, this.mob.getHeadRotSpeed());


//                if (!PieceUtil.noCollision(mob.world, mob.getPosition().down())) {
//                    mob.addVelocity(0, 0.025, 0);
//                }
                float aiSpeedFactor;
                if (!this.mob.isOnGround()) {
                    aiSpeedFactor = (float) ((this.speedModifier / 2 + DEMath.getHorizontalDistance(0, 0, fromPitch.x, fromPitch.z) / 2) * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }
                else {

                    aiSpeedFactor = (float)((this.speedModifier / 2 + DEMath.getHorizontalDistance(0, 0, fromPitch.x, fromPitch.z) / 2) * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                }

                this.mob.setSpeed(aiSpeedFactor);

                this.mob.setXRot(this.rotlerp(this.mob.getXRot(), pitchToTarget, 20));

                if (yDif > 0.0D) {

                    IFlapper flapMob = (IFlapper) this.mob;

                    if (flapMob.canFlap()) {

                        if (yDif > 0.3F) {

                            flapMob.flap(0.6F);

                        } else {

                            this.mob.setYya((float)Math.min(fromPitch.y * aiSpeedFactor, 0.2));

                        }



                    } else {

                    this.mob.setYya((float) (fromPitch.y * aiSpeedFactor));

                    }

                } else {

                    this.mob.setYya((float) fromPitch.y * aiSpeedFactor);

                }

            } else {

                IFlapper flapMob = (IFlapper) this.mob;
                if (isWanderingAI()) {
                    this.profile.update(this.mob.position().add(0, this.mob.getBoundingBox().getYsize() / 2, 0));

                    if (this.profile.isCeilingHigher(6)) {
                        if (!this.profile.isGroundLower(6)) {
                            if (flapMob.canFlap()) {
                                flapMob.flap(0.7F);
                            }
                        }
                    } else {
                        if (this.mob.isInWater() || this.profile.equilibriumOffset() > 0) {
                            if (flapMob.canFlap()) {
                                flapMob.flap(1.0F);
                            }
                        }
                    }

                    if (this.mob.position().y() - TumefendEntity.this.home.get().getY() < -10) {
                        if (flapMob.canFlap()) {
                            flapMob.flap(1F);
                        }
                    }

                    this.collisionDelay.tryUp();

                    if (this.pathfindDelay.hasEnded() && !new AABB(TumefendEntity.this.home.get(), TumefendEntity.this.home.get()).inflate(30, 200, 30).intersects(this.mob.getBoundingBox())) {

                        TumefendEntity.this.aiState = AIState.WANDERING_DIR;
                        getNavigation().moveTo(TumefendEntity.this.home.get().getX(), this.mob.getY(), TumefendEntity.this.home.get().getZ(), 1.0D);
                        this.pathfindDelay.reset();

                    } else {
                        boolean flag = this.mob.horizontalCollision || !PieceUtil.noCollision(TumefendEntity.this.level, new BlockPos(this.mob.position().add(this.mob.getViewVector(0))));
                        if (flag) {
                            this.findFreeDelay.tryUp();

                            if (this.findFreeDelay.hasEnded()) {

                                Vec3 vector3d;

                                vector3d = TumefendEntity.this.getViewVector(0.0F);

                                Vec3 vector3d2 = HoverRandomPos.getPos(TumefendEntity.this, 8, 7, vector3d.x, vector3d.z, (float)Math.PI / 2F, 2, 1);
                                vector3d2 = vector3d2 != null ? vector3d2 : AirAndWaterRandomPos.getPos(TumefendEntity.this, 6, 4, -1, vector3d.x, vector3d.z, (float)Math.PI / 2F);

                                if (vector3d2 != null) {
                                    this.collisionDelay.reset();
                                    TumefendEntity.this.aiState = AIState.WANDERING_DIR;
                                    getNavigation().moveTo(vector3d2.x(), vector3d2.y(), vector3d2.z(), 1.0D);
                                    this.findFreeDelay.reset();
                                }
                            }
                        }

                        if (flag && this.collisionDelay.hasEnded() || !flag && TumefendEntity.this.random.nextInt(50) == 2) {
                            float newYaw = TumefendEntity.this.random.nextInt(360);
                            if (this.collisionDelay.hasEnded()) {
                                this.collisionDelay.reset();
                            }
                            ((MGLookController)getLookControl()).setTargetYaw(newYaw, 10.0F);
                        }
                    }

                    float vSpeed;

                    if (this.profile.isGroundLower(10)) {
                        vSpeed = -0.25F;
                    } else {
                        if (this.profile.blocksDown + this.profile.blocksUp < 5) {
                            vSpeed = -0.25F;
                        } else {
                            vSpeed = -0.125F;
                        }
                    }

                    this.mob.setYya(vSpeed);

                    this.mob.setSpeed(TumefendEntity.this.onGround ? (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) : (float) this.mob.getAttributeValue(Attributes.FLYING_SPEED));


                }

            }
            setYRot(TumefendEntity.this.yHeadRot);

        }
    }
}

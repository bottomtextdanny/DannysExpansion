package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityData;
import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.entity.serialization.RawEntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers.MGLookController;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.ActionGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.pathnavigators.DEFlyingNavigator;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.aigimmicks.AIState;
import net.bottomtextdanny.dannys_expansion.core.Util.aigimmicks.HoverProfile;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
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

public class JemossellyEntity extends ModuledMob {
    public static final EntityDataReference<BlockPos> HOME_REF =
            BCDataManager.attribute(JemossellyEntity.class,
                    RawEntityDataReference.of(
                            BuiltinSerializers.BLOCK_POS,
                            () -> BlockPos.ZERO,
                            "home")
            );
    public final EntityData<BlockPos> home;
    private AIState aiState = AIState.WANDERING;
    public double prevRenderMovement;
    public double renderMovement;
    public float prevRenderYawRot;
    public float renderYawRot;

    public JemossellyEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.home = bcDataManager().addNonSyncedData(EntityData.of(HOME_REF));
        this.lookControl = new MGLookController(this);
        this.moveControl = new JemossellyEntity.InController(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.meleeTimer = new Timer(15);
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(0, new ActionGoal(this, o -> ifCollisionMeleeParamsAnd(target -> DEMath.cylindersCollide(
                position().subtract(0, -0.2, 0)
                , getBbHeight() + 0.2F
                , getBbWidth() * 0.7F,
                target.position().subtract(0, -0.2, 0)
                , target.getBbHeight() + 0.2F
                , target.getBbWidth()  * 0.7F
        )), o -> {
            doHurtTarget(getTarget());
            this.meleeTimer.reset();
        }));
        this.goalSelector.addGoal(1, new FollowTargetGoal(this, 1.8d, 0, 12){
            @Override
            public void tick() {
                super.tick();
                JemossellyEntity.this.aiState = AIState.AT_MELEE;
            }
        });
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
    }
    

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
                .add(Attributes.FLYING_SPEED, 2.5F)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    protected PathNavigation createNavigation(Level worldIn) {
        return new DEFlyingNavigator(this, this.level);
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : -1.0F;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return !worldIn.getBlockState(getBlockPosBelowThatAffectsMyMovement()).isAir();
    }
	
	@Override
	public int getHeadRotSpeed() {
		return 50;
	}

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (this.home.get().getX() == 0 && this.home.get().getY() == 0 && this.home.get().getZ() == 0) {
            this.home.set(blockPosition());
        }
        ClientInstance.chatMsg("xd42");
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void tick() {
        super.tick();
        this.fallDistance = 0;
        setNoGravity(true);
        
	       
        if (!hasAttackTarget() && this.navigation.isDone()) this.aiState = AIState.WANDERING;

        if (this.level.isClientSide()) {
            this.prevRenderYawRot = this.renderYawRot;
            this.prevRenderMovement = this.renderMovement;

            this.renderYawRot = Mth.rotlerp(this.renderYawRot, DEMath.getTargetYaw(this, this.xOld, this.zOld), 0.2F);

            this.renderMovement = Mth.lerp(0.2, this.renderMovement, Math.min(DEMath.getDistance(this, this.xOld, this.yOld, this.zOld) * 6, 1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        if (flag == 0) {
            ((MGLookController)getLookControl()).setTargetYaw(fetcher.get(0, Float.class), 5.000F);
        }
    }
	
	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DESounds.ES_GENERIC_MEAT_HIT.get();
	}
	
	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return DESounds.ES_FOAMSHROOM_GENERIC_DEATH.get();
	}

    public class InController extends MoveControl {
        private final HoverProfile profile;
        private final Timer findFreeDelay = new Timer(20);
        private final Timer collisionDelay = new Timer(40);
        private final Timer pathfindDelay;

        public <E extends Mob> InController(E mob) {
            super(mob);
            this.profile = new HoverProfile(mob, 11, true);
            this.pathfindDelay = new Timer(20);
        }

        public void tick() {
            this.pathfindDelay.tryUp();


            if (this.operation == MoveControl.Operation.MOVE_TO) {
                float yawToTarget = DEMath.getTargetYaw(this.mob, this.wantedX, this.wantedZ);
                float pitchToTarget = DEMath.getTargetPitch(this.mob, this.wantedX, this.wantedY, this.wantedZ);
                Vec3 fromPitch = DEMath.fromPitchYaw(pitchToTarget, yawToTarget);
                if (JemossellyEntity.this.aiState == AIState.WANDERING_DIR && this.mob.getBoundingBox().intersects(new AABB(JemossellyEntity.this.home.get(), JemossellyEntity.this.home.get()).inflate(10, 3, 10))) {
                    this.operation = MoveControl.Operation.WAIT;
                    JemossellyEntity.this.aiState = AIState.WANDERING;
                    return;
                }

                //Rotates yaw
                //this.mob.rotationYawHead = this.limitAngle(this.mob.rotationYawHead, yawToTarget, 50);

                ((MGLookController)getLookControl()).setTargetYaw(yawToTarget, hasAttackTarget() ? 12.000F : 5.000F);
                sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.FLOAT, yawToTarget));

                setYRot(JemossellyEntity.this.yHeadRot);
                float aiSpeedFactor;

                if (!this.mob.isOnGround()) {
                    aiSpeedFactor = (float) (getSpeedModifier() * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }
                else {
                    aiSpeedFactor = (float) (getSpeedModifier() * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                }
	
	            float yawDif = Mth.degreesDifferenceAbs(DEMath.getTargetYaw(JemossellyEntity.this, this.wantedX, this.wantedZ), JemossellyEntity.this.yHeadRot);
	            float hDist = DEMath.getHorizontalDistance(JemossellyEntity.this, this.wantedX, this.wantedZ);
	
	            
	            float hSpeed = aiSpeedFactor;
	            if (hDist < 2.0F && hDist > 0.3F && yawDif > 10.0F) {
		            hSpeed = 0.0F;
	            }

                this.mob.setSpeed(hSpeed);

	            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), pitchToTarget, 20));

                this.mob.setYya((float) (fromPitch.y * aiSpeedFactor + (JemossellyEntity.this.aiState == AIState.WANDERING_DIR ? DEMath.sin((float) JemossellyEntity.this.tickCount / 3) * 0.75F : 0) ));

                this.operation = MoveControl.Operation.WAIT;

            } else {
                if (JemossellyEntity.this.aiState == AIState.WANDERING) {
                    this.profile.update(this.mob.position().add(0, this.mob.getBoundingBox().getYsize() / 2, 0));
                    float vSpeed = DEMath.sin((float) JemossellyEntity.this.tickCount / 6) * 0.3F;

                    if (this.profile.isCeilingHigher(6)) {
                        if (!this.profile.isGroundLower(6)) {
                            vSpeed += 0.6;
                        }
                    } else {
                        if (this.mob.isInWater()) {
                            vSpeed += 0.6;
                        } else {

                            vSpeed += Mth.sign(this.profile.equilibriumOffset()) * 0.6;

                        }

                    }

                    if (this.mob.position().y() - JemossellyEntity.this.home.get().getY() < -7) {
                        vSpeed += 0.6;
                    }

                    this.collisionDelay.tryUp();

                    if (this.pathfindDelay.hasEnded() && !new AABB(JemossellyEntity.this.home.get(), JemossellyEntity.this.home.get()).inflate(25, 200, 25).intersects(this.mob.getBoundingBox())) {

                        JemossellyEntity.this.aiState = AIState.WANDERING_DIR;
                        getNavigation().moveTo(JemossellyEntity.this.home.get().getX(), this.mob.getY(), JemossellyEntity.this.home.get().getZ(), 1.0D);
                        this.pathfindDelay.reset();

                    } else {
                        boolean flag = this.mob.horizontalCollision || !PieceUtil.noCollision(JemossellyEntity.this.level, new BlockPos(this.mob.position().add(this.mob.getViewVector(0))));
                        if (flag) {
                            this.findFreeDelay.tryUp();

                            if (this.findFreeDelay.hasEnded()) {

                                Vec3 vector3d;

                                vector3d = JemossellyEntity.this.getViewVector(0.0F);

                                Vec3 vector3d2 = HoverRandomPos.getPos(JemossellyEntity.this, 8, 7, vector3d.x, vector3d.z, (float)Math.PI / 2F, 3, 1);
                                vector3d2 = vector3d2 != null ? vector3d2 : AirAndWaterRandomPos.getPos(JemossellyEntity.this, 8, 4, -2, vector3d.x, vector3d.z, (float)Math.PI / 2F);

                                if (vector3d2 != null) {
                                    this.collisionDelay.reset();
                                    JemossellyEntity.this.aiState = AIState.WANDERING_DIR;
                                    getNavigation().moveTo(vector3d2.x(), vector3d2.y(), vector3d2.z(), 1.0D);
                                    this.findFreeDelay.reset();
                                }
                            }
                        }

                        if (flag && this.collisionDelay.hasEnded() || !flag && JemossellyEntity.this.random.nextInt(50) == 2) {
                            float newYaw = JemossellyEntity.this.random.nextInt(360);
                            if (this.collisionDelay.hasEnded()) {
                                this.collisionDelay.reset();
                            }
                            ((MGLookController)getLookControl()).setTargetYaw(newYaw, 5.000F);
                            sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.FLOAT, newYaw));
                        }
                    }

                    if (this.profile.isGroundLower(10)) {
                        vSpeed += -0.25F;
                    } else {
                        if (this.profile.blocksDown + this.profile.blocksUp < 5) {
                            vSpeed += -0.25F;
                        } else {
                            vSpeed += -0.125F;
                        }
                    }

                    this.mob.setYya(vSpeed);
                    
                    
	                float speedMult = 1.0F;
	                float yawDif = Mth.degreesDifferenceAbs(DEMath.getTargetYaw(JemossellyEntity.this, this.wantedX, this.wantedZ), JemossellyEntity.this.yHeadRot);
	                float hDist = DEMath.getHorizontalDistance(JemossellyEntity.this, this.wantedX, this.wantedZ);
	                
	                
	                this.mob.setSpeed((JemossellyEntity.this.onGround ? (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMult : (float) this.mob.getAttributeValue(Attributes.FLYING_SPEED) / 3) * speedMult);
	
                }
                setYRot(JemossellyEntity.this.yHeadRot);
            }


        }
    }
}

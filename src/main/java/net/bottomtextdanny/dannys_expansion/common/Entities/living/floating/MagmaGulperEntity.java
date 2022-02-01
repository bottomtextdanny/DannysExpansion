package net.bottomtextdanny.dannys_expansion.common.Entities.living.floating;

import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionModule;
import net.bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationHandler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.AnimationManager;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.SimpleAnimation;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.CuttedAnimation;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers.MGLookController;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FloatingEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.bottomtextdanny.dannys_expansion.core.Util.ExternalMotion;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MagmaGulperEntity extends FloatingEntity implements Enemy, ExtraMotionProvider {
    public static final CuttedAnimation RAM = new CuttedAnimation(21, 11);
    public static final SimpleAnimation FLAP = new SimpleAnimation(20);
    public static final SimpleAnimation BEAT = new SimpleAnimation(17);
    public static final AnimationManager ANIMATIONS = new AnimationManager(RAM, FLAP, BEAT);
	public final AnimationHandler<MagmaGulperEntity> beatHandler = addAnimationHandler(new AnimationHandler<>(this));
	public final AnimationHandler<MagmaGulperEntity> attackHandler = addAnimationHandler(new AnimationHandler<>(this));
    public MagmaGulperPhase phase;
    public AttackPhase attackPhase = AttackPhase.WAIT;
    public ExternalMotion retrayMotion;
    BlockHitResult rayTraceResult;
    public Timer beatTimer;
    public Timer avoidCollisionTimer;
    public Timer randomParticleTimer;
    public int timeCollidedHorizontally;
    public float glidingVelocityMult = 1.0F;
    public int extraFlaps;
    public int setActiveTimer = 600;
    public float prevRenderYawRot;
    public float renderYawRot;
    private ExtraMotionModule motionUtilModule;

    public MagmaGulperEntity(EntityType<? extends MagmaGulperEntity> type, Level worldIn) {
        super(type, worldIn);
        this.beatTimer = new Timer(220);
        this.avoidCollisionTimer = new Timer(22);
        this.bobbingAmount = 0.0F;
        this.randomParticleTimer = new Timer(80, b -> DEMath.intRandomOffset(b, 0.5F));
        this.phase = MagmaGulperPhase.ACTIVE;
        this.moveControl = new MagmaGulperMovementController(this);
        this.lookControl = new MGLookController(this);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.motionUtilModule = new ExtraMotionModule(this);
        this.retrayMotion = new ExternalMotion(8, 0.96F);
    }

    @Override
    public AnimationManager getAnimations() {
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
        this.goalSelector.addGoal(0, new MagmaGulperEntity.AttackGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, false));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        setNoGravity(true);
        this.fallDistance = 0;

        if (this.level.isClientSide()) {
            this.prevRenderYawRot = this.renderYawRot;

            this.renderYawRot = (float)Mth.lerp(0.2, this.renderYawRot, Mth.degreesDifference(this.yHeadRotO, this.yHeadRot));
        } else {
        	
            if (this.mainHandler.isPlaying(FLAP)) {
                if (this.mainHandler.getTick() == 1) {
                    playSound(DESounds.ES_MAGMA_GULPER_FLAP.get(), 1.5F, 0.5F);
                } else if (this.mainHandler.getTick() == 3) {
                    addAccMotion(0.0F, 0.45F + 0.04F * this.glidingVelocityMult, 0.0F);
                    this.glidingVelocityMult = 1.0F;
                }
            }

            this.avoidCollisionTimer.tryUp();

            if (!hasAttackTarget() && this.attackHandler.isPlaying(RAM)) {
                RAM.setPass(this.attackHandler, true);
                sendClientMsg(1);
            }

            if (this.glidingVelocityMult > 1.2F) {
                this.beatTimer.tryUp();

                if (this.beatTimer.hasEnded()) {
                    this.beatTimer.reset();
                    playSound(DESounds.ES_MAGMA_GULPER_EXPULSE.get(), 1.0F, 1.0F);

                    this.beatHandler.play(BEAT);

                    sendClientMsg(2);
                }
            }
        }

        if (!hasAttackTarget()) {
            if (this.random.nextInt(50) == 1 ) {
                ((MGLookController)getLookControl()).setTargetYaw(this.random.nextInt(360), 2.0F);
            }
        }

        if (this.extraFlaps > 10) {
            this.phase = MagmaGulperPhase.TIRED;
        }

        if (this.phase == MagmaGulperPhase.TIRED) {
            this.setActiveTimer--;

            if (this.setActiveTimer <= 0) {
                this.phase = MagmaGulperPhase.ACTIVE;
                this.extraFlaps = 0;
                this.setActiveTimer = 600;
            }
        }



        setYRot(this.yHeadRot);
    }

    @Override
    public void baseTick() {
        this.rayTraceResult = DannyRayTraceHelper.rayTraceBlocks(this);
        super.baseTick();
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);

        if (this.horizontalCollision) {
            this.timeCollidedHorizontally = Mth.clamp(this.timeCollidedHorizontally + 1, 0, 120);
        } else {
            if (this.timeCollidedHorizontally > 0) {
                this.timeCollidedHorizontally = this.timeCollidedHorizontally - 3;
            }
        }

        float gravity = -0.042F;

        if (!hasAttackTarget()) {
            gravity -= 0.03F;
        }



        if (hasAttackTarget()) {
            if (this.attackPhase == AttackPhase.WAIT || this.attackPhase == AttackPhase.MOMENTUM) {
                gravity =- 0.22F;
                if (this.mainHandler.isPlaying(FLAP) && this.mainHandler.getTick() == 3) {
                    addAccMotion(0.0F, 0.28F, 0.0F);
                }
            }
        }

        addSimpleMotion(0, gravity, 0);
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public int getHeadRotSpeed() {
        return 5;
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isFire()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public boolean isOnFire() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == 0) {
            ((MGLookController)getLookControl()).setTargetYaw(fetcher.get(0, Float.class), 5.0F);
        } else if (flag == 1) {
            if (this.attackHandler.isPlaying(RAM)) {
                RAM.setPass(this.attackHandler, true);
            }

        } else if (flag == 2) {
            int factor1 = this.random.nextInt(3) + 2;
            Vec3 horizontalDir = DEMath.fromPitchYaw(0, this.yHeadRot).multiply(1.2, 0, 1.2);
            for (int i = 0; i < factor1; i++) {
                this.level.addParticle(DEParticles.MG_BIG_BLOB.get(), getX(), getY() + 0.2, getZ(), this.random.nextGaussian() * 0.4 + horizontalDir.x, 0.4 + this.random.nextFloat() * 0.4, this.random.nextGaussian() * 0.4 + horizontalDir.z);
            }
        } else  if (flag == 3) {
            ((MGLookController)getLookControl()).setTargetPitch(fetcher.get(0, Float.class), 7.0F);
        } else if (flag == 4) {
            ((MGLookController)getLookControl()).setTargetYaw(fetcher.get(0, Float.class), 10.0F);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DESounds.ES_MAGMA_GULPER_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DESounds.ES_MAGMA_GULPER_DEATH.get();
    }


    public class MagmaGulperMovementController extends MoveControl {
        final MagmaGulperEntity magmaGulper;

        public MagmaGulperMovementController(Mob mob) {
            super(mob);
            this.magmaGulper = (MagmaGulperEntity) mob;
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockpos = this.magmaGulper.blockPosition();
            double d0 = 0;
            BlockState tracedBlockstate = this.magmaGulper.level.getBlockState(MagmaGulperEntity.this.rayTraceResult.getBlockPos());

            Block tracedBlock = tracedBlockstate.getBlock();

            Vec3 vec = DEMath.fromPitchYaw(0, MagmaGulperEntity.this.yHeadRot);

            do {
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = this.magmaGulper.level.getBlockState(blockpos1);
                if (blockstate.getCollisionShape(MagmaGulperEntity.this.level, blockpos1) != Shapes.empty() || blockstate.getBlock() == Blocks.LAVA || blockstate.getBlock() == Blocks.WATER) {
                    if (!this.magmaGulper.level.isEmptyBlock(blockpos)) {
                        BlockState blockstate1 = this.magmaGulper.level.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(this.magmaGulper.level, blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.max(Direction.Axis.Y);
                        }
                    }
                    break;
                }
                blockpos = blockpos.below();
            } while(blockpos.getY() >= Mth.floor(d0) - 1);

            if (blockpos.getY() + d0 > this.magmaGulper.blockPosition().getY() -2 && MagmaGulperEntity.this.mainHandler.isPlayingNull() || !hasAttackTarget() && MagmaGulperEntity.this.random.nextInt(280) == 1 && this.magmaGulper.phase == MagmaGulperPhase.ACTIVE && MagmaGulperEntity.this.attackPhase != AttackPhase.ATTACK) {
                MagmaGulperEntity.this.mainHandler.play(FLAP);
            }
            
            if (this.magmaGulper.getTarget() == null) {

                addSimpleMotion(vec.x * 0.15 * MagmaGulperEntity.this.glidingVelocityMult, 0, vec.z * 0.15 * MagmaGulperEntity.this.glidingVelocityMult);
                MagmaGulperEntity.this.glidingVelocityMult = Mth.clamp(MagmaGulperEntity.this.glidingVelocityMult + 0.07F, 0, 3.0F);

                if (MagmaGulperEntity.this.avoidCollisionTimer.hasEnded()) {
                    if (!tracedBlockstate.isAir()) {
                        float newRot = DEMath.getTargetYaw(this.magmaGulper, MagmaGulperEntity.this.rayTraceResult.getBlockPos().getX(), MagmaGulperEntity.this.rayTraceResult.getBlockPos().getZ()) + 180;
                        ((MGLookController)getLookControl()).setTargetYaw(newRot, 10.0F);

                        sendClientMsg(4, WorldPacketData.of(BuiltinSerializers.FLOAT, newRot));

                        MagmaGulperEntity.this.glidingVelocityMult = 0.75F;

                        MagmaGulperEntity.this.avoidCollisionTimer.reset();

                        if (MagmaGulperEntity.this.timeCollidedHorizontally > 60) {
                            MagmaGulperEntity.this.avoidCollisionTimer.setCurrentBound(50);
                        }
                    }
                }

                if (MagmaGulperEntity.this.timeCollidedHorizontally > 30 && MagmaGulperEntity.this.phase == MagmaGulperEntity.MagmaGulperPhase.ACTIVE) {
                    if (MagmaGulperEntity.this.mainHandler.isPlayingNull()) {
                        MagmaGulperEntity.this.mainHandler.play(FLAP);
                        MagmaGulperEntity.this.extraFlaps++;
                    }
                }
            }
        }
    }
    
    public void passAttack() {
	    if (this.attackHandler.isPlaying(RAM)) {
            RAM.setPass(this.attackHandler, true);
		    sendClientMsg(1);
	    }
    }

    class AttackGoal extends Goal {
        Timer attackTimer;
        Timer waitTimer;
        Timer momentumTimer;
        Timer collidedTimer;

        public AttackGoal() {
            super();
            this.attackTimer = new Timer(220);
            this.waitTimer = new Timer(80, b -> 20 + MagmaGulperEntity.this.random.nextInt(10));
            this.momentumTimer = new Timer(30, b -> 20 + MagmaGulperEntity.this.random.nextInt(20));
            this.collidedTimer = new Timer(70);
        }

        @Override
        public void tick() {
            setNoGravity(true);
            super.tick();
            Vec3 head = DEMath.fromPitchYaw(getXRot(), MagmaGulperEntity.this.yHeadRot);
            Vec3 headH = DEMath.fromPitchYaw(0, MagmaGulperEntity.this.yHeadRot);
            Vec3 headClamp = DEMath.fromPitchYaw(Mth.clamp(getXRot(), -40, 40), MagmaGulperEntity.this.yHeadRot);
            getLookControl().setLookAt(getTarget(), 20, 90);

            if (MagmaGulperEntity.this.attackPhase == AttackPhase.WAIT) {

                if (blockPosition().getY() < getTarget().blockPosition().getY() + 2 && MagmaGulperEntity.this.mainHandler.isPlayingNull()) {
                    MagmaGulperEntity.this.mainHandler.play(FLAP);
                }

                float hDist = DEMath.getHorizontalDistance(MagmaGulperEntity.this, getTarget());

                if (hDist < 6) {
                    if (hDist >= 3) {
                        float mult = -DEMath.getLerp(6, 3, hDist);
                        MagmaGulperEntity.this.retrayMotion.setMotion(headH.multiply(mult, mult, mult));
                    } else {
                        MagmaGulperEntity.this.retrayMotion.setMotion(headH.multiply(-1.0, -1.0, -1.0));
                    }

                }

                if (this.waitTimer.hasEnded()) {
                    this.waitTimer.reset();
                    MagmaGulperEntity.this.attackPhase = AttackPhase.ATTACK;
                    MagmaGulperEntity.this.attackHandler.play(RAM);
                }

                this.waitTimer.tryUp();
            } else if (MagmaGulperEntity.this.attackPhase == AttackPhase.ATTACK) {
                float extraDeg = Mth.degreesDifference(MagmaGulperEntity.this.yHeadRot, DEMath.getTargetYaw(MagmaGulperEntity.this, getTarget())) > 0 ? -20 : 20;

                BlockState tracedBlockstate = MagmaGulperEntity.this.level.getBlockState(MagmaGulperEntity.this.rayTraceResult.getBlockPos());
                Block tracedBlock = tracedBlockstate.getBlock();

                addSimpleMotion(head.multiply(0.57, 0.57, 0.57));

                if (getBoundingBox().inflate(0.6).intersects(getTarget().getBoundingBox())) {
                    if (doHurtTarget(getTarget())) {
                        this.attackTimer.reset();
                        this.collidedTimer.reset();
                        MagmaGulperEntity.this.attackPhase = AttackPhase.MOMENTUM;
                        passAttack();
                    }
                }

                if (blockPosition().getY() < getTarget().blockPosition().getY() - 1 && MagmaGulperEntity.this.mainHandler.isPlayingNull()) {
                    MagmaGulperEntity.this.mainHandler.play(FLAP);
                }

                if (tracedBlock.getBlockSupportShape(MagmaGulperEntity.this.level.getBlockState(MagmaGulperEntity.this.rayTraceResult.getBlockPos()), MagmaGulperEntity.this.level, MagmaGulperEntity.this.rayTraceResult.getBlockPos()) != Shapes.empty() ) {

                    this.collidedTimer.tryUp();

                    ((MGLookController)getLookControl()).setTargetYaw(MagmaGulperEntity.this.yHeadRot + extraDeg, 5.0F);
                    sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.FLOAT, MagmaGulperEntity.this.yHeadRot + 20));
                }

                if (MagmaGulperEntity.this.timeCollidedHorizontally > 10) {
                    ((MGLookController)getLookControl()).setTargetYaw(MagmaGulperEntity.this.yHeadRot + extraDeg, 5.0F);
                    sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.FLOAT, MagmaGulperEntity.this.yHeadRot + 20));
                }

                if (MagmaGulperEntity.this.verticalCollision && Mth.abs(getXRot()) > 70) {
                    ((MGLookController)getLookControl()).setTargetPitch(getXRot() - Mth.sign(getXRot()) * 20, 7.0F);
                    sendClientMsg(0, WorldPacketData.of(BuiltinSerializers.FLOAT, getXRot() - Mth.sign(getXRot()) * 20));
                }

                if (this.collidedTimer.hasEnded() || MagmaGulperEntity.this.hurtTime > 0 || tracedBlockstate.is(DETags.MAGMA_GULPER_DANGER_BLOCKS)) {
                    this.attackTimer.end();
                }

                if (this.attackTimer.hasEnded()) {
                    this.attackTimer.reset();
                    this.collidedTimer.reset();
                    MagmaGulperEntity.this.attackPhase = AttackPhase.MOMENTUM;
                    passAttack();

                }

                this.attackTimer.tryUp();
            } else if (MagmaGulperEntity.this.attackPhase == AttackPhase.MOMENTUM) {

                if (distanceToSqr(getTarget()) < 16) {
                    addSimpleMotion(headClamp.multiply(-0.3, -0.3, -0.3));
                } else {
                    addSimpleMotion(headClamp.multiply(-0.05, -0.05, -0.05));
                }

                if (this.momentumTimer.hasEnded()) {
                    this.momentumTimer.reset();
                    MagmaGulperEntity.this.attackPhase = AttackPhase.WAIT;
                }

                this.momentumTimer.tryUp();
            }
        }

        @Override
        public boolean canUse() {
            return getTarget() != null && getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return getTarget() != null && getTarget().isAlive();
        }
    }

    public enum AttackPhase {
        WAIT,
        MOMENTUM,
        ATTACK,
    }
    public enum MagmaGulperPhase {
        ACTIVE,
        TIRED
    }
}

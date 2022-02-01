package net.bottomtextdanny.braincell.mod.world.builtin_entities;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.*;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.PhaseAffectedModule;
import net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider.PhaseAffectedProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.motion_util.MotionUtilProvider;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.IndexedVariableModule;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariantProvider;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.Schedule;
import net.bottomtextdanny.dannys_expansion.core.interfaces.entity.EntityClientMessenger;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

public abstract class ModuledMob extends PathfinderMob
        implements EntityClientMessenger, BCDataManagerProvider, LoopedWalkProvider,
        LivingAnimatableProvider, VariantProvider, PhaseAffectedProvider {
    private BCDataManager deDataManager;
	public AnimationHandler<ModuledMob> mainHandler;
    @Deprecated
    public Schedule sleepPathSchedule = new Schedule();
    @Deprecated
    public Timer livingSoundTimer;
    @Deprecated
    public Timer meleeTimer;
    @Deprecated
    public Timer rangedTimer;
    @Nullable
    protected LoopedWalkModule loopedWalkModule;
    @Nullable
    protected PhaseAffectedModule phaseAffectedModule;
    @Nullable
    protected IndexedVariableModule variableModule;
    protected LivingAnimatableModule animatableModule;
	
	public ModuledMob(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.livingSoundTimer = new Timer(1000, baseBound -> DEMath.intRandomOffset(baseBound, 0.8F));
        this.meleeTimer = new Timer(10);
        this.rangedTimer = new Timer(10);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.animatableModule = new LivingAnimatableModule(this, getAnimations());
        this.mainHandler = addAnimationHandler(new AnimationHandler<>(this));
        this.deDataManager = new BCDataManager(this);
        commonInit();
    }

    protected void commonInit() {}

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

    }

    //-----------looped walk module
    @Override
    public LoopedWalkModule loopedWalkModule() {
        return this.loopedWalkModule;
    }

    @Override
    public boolean operateWalkModule() {
        return this.loopedWalkModule != null;
    }

    @Override
    public void playLoopStepSound(BlockPos pos, BlockState blockIn) {
        playStepSound(pos, blockIn, Math.min(loopedWalkModule().renderLimbSwingAmount * 8.0F, 1.0F));
    }

    @Override
    public float getLoopWalkMultiplier() {
        return 0.25F;
    }

    @Override
    public float hurtLoopLimbSwingFactor() {
        return 0.25F;
    }

    //-----------looped walk module

    //-----------animatable module

    public AnimationGetter getAnimations() {
        return null;
    }

    @Override
    public LivingAnimatableModule animatableModule() {
        return this.animatableModule;
    }

    @Nullable
    public Animation getDeathAnimation() {
        return null;
    }

    //-----------animatable module

    //-----------phase affected module

    @Override
    public PhaseAffectedModule phaseAffectedModule() {
        return this.phaseAffectedModule;
    }

    //-----------phase affected module

    //-----------variable module

    @Override
    public IndexedVariableModule variableModule() {
        return this.variableModule;
    }

    //-----------variable module

    @Override
	protected final void registerGoals() {
		super.registerGoals();
        registerExtraGoals();
	}


	
	@Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }
	
	@Override
    public void tick() {
        super.tick();

        this.level.getProfiler().push("DE:dannyBaseTick");

        this.meleeTimer.tryUp();
        this.rangedTimer.tryUp();
        this.sleepPathSchedule.tryDown();
        
        if (!this.level.isClientSide()) {
            if (getLivingSoundTimer().hasEnded()) {
                doLivingSound();
                getLivingSoundTimer().reset();
            } else {
                getLivingSoundTimer().tryUp();
            }
        }

        this.level.getProfiler().pop();
    }

	@Override
	public EntityDimensions getDimensions(Pose poseIn) {
		return super.getDimensions(poseIn);
	}

	protected void registerExtraGoals() {
	}

    /**
     * Reflection, verify this method each minecraft update
     */
    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, (float)strength, ratioX, ratioZ);
        if(event.isCanceled()) return;
        float resistantFactor = (float) (1.0D - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        strength = event.getStrength();
        ratioX = event.getRatioX();
        ratioZ = event.getRatioZ();
        strength *= resistantFactor;
        if (!(strength <= 0.0F)) {
            this.hasImpulse = true;
            Vec3 motionFactor = this.getDeltaMovement();
            //change here //////////////////
            Vec3 knockBackFactor = modifyKnockBack(new Vec3(ratioX, 0, ratioZ).normalize().scale(strength)).add(0.0, 0.4 * resistantFactor, 0.0);
            this.setDeltaMovement(new Vec3(motionFactor.x / 2.0D - knockBackFactor.x, this.onGround ? Math.max(knockBackFactor.y, motionFactor.y) : motionFactor.y, motionFactor.z / 2.0D - knockBackFactor.z));
            ////////////////////////////////
            //was :
            //this.setMotion(vector3d.x / 2.0D - vector3d1.x, this.onGround ? Math.min(0.4D, vector3d.y / 2.0D + (double)strength) : vector3d.y, vector3d.z / 2.0D - vector3d1.z);
            ////////////////////////////////
        }
    }

    public Vec3 modifyKnockBack(Vec3 knockback) {
        return knockback;
    }

    @Override
    public abstract boolean removeWhenFarAway(double distanceToClosestPlayer);

    @Nullable
    public SoundEvent getLivingSound() {
        return super.getAmbientSound();
    }

    public void playLivingSound() {
        SoundEvent soundevent = this.getLivingSound();
        if (soundevent != null) {
            this.playSound(soundevent, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        }
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
    }

    public Timer getLivingSoundTimer() {
        return this.livingSoundTimer;
    }

    public void playStepSound(BlockPos pos, BlockState blockIn, float volume) {
        if (!blockIn.getMaterial().isLiquid()) {
            BlockState blockstate = this.level.getBlockState(pos.above());
            SoundType soundtype = blockstate.is(Blocks.SNOW) ? blockstate.getSoundType(this.level, pos, this) : blockIn.getSoundType(this.level, pos, this);
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F * volume, soundtype.getPitch());
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return true;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    /**
     * fired on living sound perform
     */
    public void doLivingSound() {
        playLivingSound();
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {

        return new ModuledMob.DannyGroundNavigator(this, worldIn);
    }

    public boolean canBlockBeSeen(BlockPos entityIn) {
        Vec3 vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 vector3d1 = new Vec3(entityIn.getX() + 0.5, entityIn.getY() + 0.5, entityIn.getZ() + 0.5);
        return this.level.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS;
    }

    //Networking
    //---------*



    //Utils
    //----*


    public boolean onGround() {
        return this.onGround;
    }

    public boolean hasAttackTarget() {
    	
    	if (getTarget() instanceof Player) {
    		Player player = (Player) getTarget();
    		if (player.isCreative() || player.isSpectator()) {
    			return false;
		    }
	    }
        return getTarget() != null && getTarget().isAlive();
    }

    public boolean ifAttackMeleeParamsAnd(Predicate<LivingEntity> target) {
       return hasAttackTarget() && this.mainHandler.isPlayingNull() && this.meleeTimer.hasEnded() && target.test(getTarget());
    }

    public boolean ifCollisionMeleeParamsAnd(Predicate<LivingEntity> target) {
        return hasAttackTarget() && this.meleeTimer.hasEnded() && target.test(getTarget());
    }

    public boolean ifAttackRangedParamsAnd(Predicate<LivingEntity> target) {
        return hasAttackTarget() && this.mainHandler.isPlayingNull() && this.rangedTimer.hasEnded() && target.test(getTarget());
    }

    public static void disableShield(Entity entity, int ticks) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.isBlocking()) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
                player.stopUsingItem();
                player.level.broadcastEntityEvent(entity, (byte) 30);
            }
        }
    }

    public float reachTo(Entity entity) {
        return Math.max(distanceTo(entity) - entity.getBbWidth() / 2, 0);
    }

    public void mayDisableShield(Entity entity, int ticks, float possibility) {
        if (entity instanceof Player && this.random.nextFloat() < possibility) {
            Player player = (Player) entity;
            if (player.isBlocking()) {
                if (!player.getCooldowns().isOnCooldown(player.getUseItem().getItem())) {
                    player.level.broadcastEntityEvent(entity, (byte)30);
                }
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
                player.stopUsingItem();

            }
        }
    }

    /**
     * @return the parameterized distance plus the enemy's bounding box width divided by two
     * @throws NullPointerException if the attackTarget is null
     */

    public float targetDistance(float distance) {
        return distance + getTarget().getBbWidth() / 2;
    }

    /**
     * @return if is in water or lava
     */
    public boolean InFluid() {
        return isInWater() || isInLava();
    }

    protected void consumeItemFromStack(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    /**
     * attacks a living entity with a multiplication parameter, useful for entities
     * having more than one attack
     * @param livingEntity the entity to be attacked
     * @param mult the multiplier
     */
    public void attackWithMultiplier(LivingEntity livingEntity, float mult) {
        livingEntity.hurt(DamageSource.mobAttack(this), (float)getAttributeValue(Attributes.ATTACK_DAMAGE) * mult);
    }
    


    public class DannyGroundNavigator extends GroundPathNavigation {

        public DannyGroundNavigator(ModuledMob entityIn, Level worldIn) {
            super(entityIn, worldIn);
        }

        @Override
        public boolean moveTo(Entity entityIn, double speedIn) {
            if (this.mob instanceof MotionUtilProvider provider) {
                this.mob.zza *= 1.0F - provider.movementReduction();
                this.mob.xxa *= 1.0F - provider.movementReduction();
                if (provider.movementReduction() >= 1.0F) {
                    stop();
                    return false;
                }
            } else if (!ModuledMob.this.sleepPathSchedule.isWoke()) {
                stop();

                this.mob.zza = 0.0F;
                this.mob.xxa = 0.0F;
                return false;
            }
            return super.moveTo(entityIn, speedIn);
        }


        @Override
        public boolean moveTo(double x, double y, double z, double speedIn) {
            if (this.mob instanceof MotionUtilProvider provider) {
                this.mob.zza *= 1.0F - provider.movementReduction();
                this.mob.xxa *= 1.0F - provider.movementReduction();
                if (provider.movementReduction() >= 0.99F) {
                    stop();
                    return false;
                }
            } else if (!ModuledMob.this.sleepPathSchedule.isWoke()) {
                stop();

                this.mob.zza = 0.0F;
                this.mob.xxa = 0.0F;
                return false;
            }
            return super.moveTo(x, y, z, speedIn);
        }

        @Override
        public void tick() {
            super.tick();
            if (this.mob instanceof MotionUtilProvider provider) {
                if (provider.movementReduction() >= 0.99F) stop();
                this.mob.zza *= 1.0F - provider.movementReduction();
                this.mob.xxa *= 1.0F - provider.movementReduction();
            } else if (!ModuledMob.this.sleepPathSchedule.isWoke()) {
                stop();

                this.mob.zza = 0.0F;
                this.mob.xxa = 0.0F;
            }
        }
    }

    @Override
    public BCDataManager bcDataManager() {
        return this.deDataManager;
    }
}

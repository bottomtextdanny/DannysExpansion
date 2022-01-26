package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkModule;
import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals.FollowTargetGoal;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.FoamshroomProjectileEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;

public class DecayBroaderEntity extends ModuledMob {
    public Animation throwProjectile = addAnimation(new Animation(40));
	public ArrayList<FoamieEntity> foamieLoaded = Lists.newArrayListWithCapacity(5);

    public DecayBroaderEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.2F;
    }

    public static AttributeSupplier.Builder Attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(ForgeMod.SWIM_SPEED.get(), 1.5D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0F);
    }

    @Override
    protected void commonInit() {
        super.commonInit();
        this.loopedWalkModule = new LoopedWalkModule(this);
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 1.2d));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1d));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, true, null));
    }

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

	}

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide() && shouldUpdateFoamies() && this.tickCount % 700 == 0) {
            this.mainAnimationHandler.play(this.throwProjectile);
        }

        if (this.mainAnimationHandler.isPlaying(this.throwProjectile)) {
            this.sleepPathSchedule.sleepForNow();

            if (this.mainAnimationHandler.getTick() == 6) {
	            playSound(DESounds.ES_DECAY_BROADER_EXPULSE.get(), 1.0F, 1.0F);
            } else if (this.mainAnimationHandler.getTick() == 7) {
                FoamshroomProjectileEntity projectile = DEEntities.FOAMSHROOM_PROJECTILE.get().create(this.level);
                int projectileYaw = this.random.nextInt(360);
	            int projectilePitch = -70 - this.random.nextInt(10);
	
	            Vec3 projectileProjection = DEMath.fromPitchYaw(projectilePitch, projectileYaw).scale(1 + this.random.nextFloat() * 0.2);

                if (projectile != null) {
                    projectile.absMoveTo(getX(), getY() + getBbHeight(), getZ(), projectileYaw, 0);
                    projectile.push(projectileProjection.x, projectileProjection.y, projectileProjection.z);
                    projectile.setCaster(this);
                    this.level.addFreshEntity(projectile);
                }
                
                
            }
        }

        EntityUtil.postDragonEndCreatureTick(this);
    }
	
	@Override
	public void playLoopStepSound(BlockPos pos, BlockState blockIn) {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), DESounds.ES_DECAY_BROADER_STEP.get(), SoundSource.HOSTILE, (0.9F + 0.07F * this.random.nextFloat()) * Easing.EASE_IN_CUBIC.progression(Math.min(loopedWalkModule().renderLimbSwingAmount * 17.0F, 1.0F)) , 0.8F + 0.4F * this.random.nextFloat());
	}
    
    public boolean shouldUpdateFoamies() {
    	return this.tickCount > 60;
    }



    @Override
    public float getLoopWalkMultiplier() {
        return 0.48F;
    }

    @Override
    public float hurtLoopLimbSwingFactor() {
        return 0.8F;
    }
}

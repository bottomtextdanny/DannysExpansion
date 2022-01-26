package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.common.Entities.ai.pathnavigators.FreeWaterNavigator;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class DarialynEntity extends ModuledMob {

    public DarialynEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new DarialynEntity.MoveHelperController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 12);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.76D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    protected void registerExtraGoals() {
        this.goalSelector.addGoal(4, new DarialynEntity.RandomSwimmingGoal(this, 1.0D, 10));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Guardian.class).setAlertOthers());
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    protected PathNavigation createNavigation(Level worldIn) {
        return new FreeWaterNavigator(this, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
    }

    protected void updateAir(int p_209207_1_) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(p_209207_1_ - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }

    /**
     * Gets called every tick from main Entity class
     */
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.updateAir(i);
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.7D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }


    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(Player player) {
        return false;
    }

    public boolean checkSpawnObstruction(LevelReader worldIn) {
        return worldIn.isUnobstructed(this);
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 0.25F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    class MoveHelperController extends MoveControl {
        private final DarialynEntity darialyn;

        public MoveHelperController(DarialynEntity darialynIn) {
            super(darialynIn);
            this.darialyn = darialynIn;
        }

        public void tick() {
            if (this.darialyn.isInWater()) {
                this.darialyn.setDeltaMovement(this.darialyn.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.darialyn.getNavigation().isDone()) {
                double d0 = this.wantedX - this.darialyn.getX();
                double d1 = this.wantedY - this.darialyn.getY();
                double d2 = this.wantedZ - this.darialyn.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    float pitchToTarget = DEMath.getTargetPitch(DarialynEntity.this, d0, d1, d2);
                    this.darialyn.setYRot(this.rotlerp(this.darialyn.getYRot(), f, 10.0F));
                    this.darialyn.yBodyRot = this.darialyn.getYRot();
                    this.darialyn.yHeadRot = this.darialyn.getYRot();
                    float f1 = (float)(this.speedModifier * this.darialyn.getAttributeValue(Attributes.MOVEMENT_SPEED));

                    if (this.darialyn.isInWater()) {
                        this.darialyn.setSpeed(f1 * 0.02F);
                        this.darialyn.setXRot(this.rotlerp(this.darialyn.getXRot(), pitchToTarget, 5.0F));
                        float f3 = DEMath.cos(this.darialyn.getXRot() * ((float)Math.PI / 180F));
                        float f4 = DEMath.sin(this.darialyn.getXRot() * ((float)Math.PI / 180F));
                        this.darialyn.zza = f3 * f1;
                        this.darialyn.yya = -f4 * f1;
                    } else {
                        this.darialyn.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.darialyn.setSpeed(0.0F);
                this.darialyn.setXxa(0.0F);
                this.darialyn.setYya(0.0F);
                this.darialyn.setZza(0.0F);
            }
        }
    }


    class RandomSwimmingGoal extends RandomStrollGoal {
        public RandomSwimmingGoal(PathfinderMob creature, double speed, int chance) {
            super(creature, speed, chance);
        }

        @Override
        public void tick() {
            super.tick();
            if (DEMath.getDistance(getX(), getY(), getZ(), this.wantedX, this.wantedY, this.wantedZ) < 4) {
                DarialynEntity.this.navigation.stop();
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = DefaultRandomPos.getPos(this.mob, 10, 7);

            for(int i = 0; vector3d != null && !this.mob.level.getBlockState(new BlockPos(vector3d)).isPathfindable(this.mob.level, new BlockPos(vector3d), PathComputationType.WATER) && i++ < 10; vector3d = DefaultRandomPos.getPos(this.mob, 15, 7)) {
            }

            return vector3d;
        }
    }
}

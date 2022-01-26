package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class RangedAvoidingEntity extends OldFloatingEntity {

    public RangedAvoidingEntity(EntityType<? extends RangedAvoidingEntity> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new HoveringMovementController(this);
    }
    
    @Override
    public void tick() {
        super.tick();
    }



    public class HoveringMovementController extends MoveControl {
        final RangedAvoidingEntity iceElement;
        boolean outOfBounds;
        boolean isAvoidingTarget;
        boolean isFollowingTarget;
        int randomAngleTimer;
        int angleTransTicks;
        int f0;
        double r0;
        double r1;
        double boost;

        public HoveringMovementController(RangedAvoidingEntity mob) {
            super(mob);
            this.iceElement = mob;
        }

        @Override
        public void tick() {
            super.tick();
            float speed = 1F;
            float verticalAir = DEMath.verticalNonSolid(this.iceElement.level, new BlockPos(this.iceElement.position()), -1, 15);

            if (verticalAir > 6 || verticalAir <= 1) {
                this.outOfBounds = true;
            }
            if (this.iceElement.horizontalCollision || this.iceElement.verticalCollision) {
                this.iceElement.setDeltaMovement(getDeltaMovement().add(0.0F, 0.05F, 0.0F));
            }

            if (this.iceElement.getTarget() != null) {
                double distance;
                Vec3 forward = Vec3.directionFromRotation(getXRot(), RangedAvoidingEntity.this.yHeadRot);

                this.isFollowingTarget = false;

                distance = this.iceElement.distanceToSqr(this.iceElement.getTarget());
                verticalAir = (float) (this.iceElement.getY() - this.iceElement.getTarget().getEyeY());


                if (distance > 120) {
                    this.boost *= 3.0F;
                }

                if (distance > 80) {

                    this.iceElement.addMotion(0.0002F * (distance / 2 - 80) / 2 * forward.x + 0.005, 0.0002F * (distance / 2 - 80) / 2 * forward.y + 0.005, 0.0002F * (distance / 2 - 80) / 2 * forward.z + 0.005);
                    this.isAvoidingTarget = false;
                    this.isFollowingTarget = true;

                } else if (distance < 40){
                    this.isAvoidingTarget = true;
                    this.iceElement.addMotion(Mth.clamp(0.006F * (distance - 40) / 1.4, 0, 0.1) * forward.x, Mth.clamp(0.01F * (-distance + 40) / 1.2, 0, 1.5), Mth.clamp(0.006F * (distance - 40), 0, 0.1) * forward.z);
                }

                if (--this.randomAngleTimer > 0) {
                } else {
                    int r2 = Mth.nextInt(RangedAvoidingEntity.this.random, 30, 70);
                    this.angleTransTicks = r2;
                    this.f0 = r2;
                    this.randomAngleTimer = r2;
                    this.r0 = RangedAvoidingEntity.this.random.nextGaussian() * 1.0F;
                    if (verticalAir >= 4) {
                        this.r1 = RangedAvoidingEntity.this.random.nextFloat() * -2F - verticalAir / 4;
                    } else if (verticalAir < 2) {
                        this.r1 = RangedAvoidingEntity.this.random.nextFloat() * 1.7F + 1F;
                    } else {
                        this.r1 = RangedAvoidingEntity.this.random.nextGaussian() * 1.25F;
                    }
                }

                if (--this.angleTransTicks > 0) {
                    Vec3 counterClockwise90 = Vec3.directionFromRotation(Mth.wrapDegrees(getXRot()) + 270, Mth.wrapDegrees(RangedAvoidingEntity.this.yHeadRot) + 270);

                    this.iceElement.addMotion(this.r0 / this.f0 * counterClockwise90.x, this.r1 / this.f0 * counterClockwise90.y + 0.1 * this.r1 / this.f0, this.r0 / this.f0 * counterClockwise90.z);
                }

                this.iceElement.getLookControl().setLookAt(this.iceElement.getTarget(), 30, 90);

                this.iceElement.setYRot(DEMath.getTargetYaw(this.iceElement, this.iceElement.getTarget()));
                this.iceElement.yBodyRot = DEMath.getTargetYaw(this.iceElement, this.iceElement.getTarget());
            } else {
                if (this.outOfBounds) {
                    if (verticalAir > 3) {
                        this.iceElement.addMotion(0.0F, -0.1F * speed, 0.0F);
                    } else if (verticalAir < 3) {
                        this.iceElement.addMotion(0.0F, 0.1F * speed, 0.0F);
                    } else {
                        this.outOfBounds = false;
                    }
                }
            }
        }
    }
}

package bottomtextdanny.dannys_expansion.content.entities.ai.controllers;

import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExternalMotion;
import bottomtextdanny.braincell.mod.entity.modules.additional_motion.ExtraMotionProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class FluentFlyingControl extends MoveControl {
    private final ExtraMotionProvider util;
    private final int maxTurn;
    private final ExternalMotion advanceMotion;
    private final boolean lockToView;

    public FluentFlyingControl(Mob mob, int maxTurns, boolean lockToView) {
        super(mob);
        this.lockToView = lockToView;
        this.maxTurn = maxTurns;


        if (mob instanceof ExtraMotionProvider provider) {
            this.advanceMotion = provider.addCustomMotion(new ExternalMotion(0.8F));
            this.util = provider;
        }
        else throw new UnsupportedOperationException("Danny's Expansion: FluentFlyingControl needs holder to be an instance of MotionUtilProvider.");
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            this.mob.setNoGravity(true);
            double deltaX = this.wantedX - this.mob.getX();
            double deltaY = this.wantedY - this.mob.getY() + 0.5 - this.mob.getBoundingBox().getYsize() / 2.0;
            double deltaZ = this.wantedZ - this.mob.getZ();
            double delta = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            if (delta < (double)2.5000003E-7F) {
                moveVertically(0.0F);
                moveHorizontally(0.0F);
                return;
            }

            float f = (float)(Mth.atan2(deltaZ, deltaX) * (double)(180F / (float)Math.PI)) - 90.0F;

            float f1;
            if (this.mob.isOnGround()) {
                f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
                f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            }

            if (this.lockToView) {
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 10.0F));
                double yawDegreeDifference = Mth.degreesDifferenceAbs(this.mob.yHeadRot, f);
                if (yawDegreeDifference < 20.0F) {
                    moveHorizontally((float) (f1 * (1.0F - yawDegreeDifference / 20.0F)));
                }
            } else {
                moveHorizontally(f1);
            }

            double d4 = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            if (Math.abs(deltaY) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
                float f2 = (float) -(Mth.atan2(deltaY, d4) * (double)(180F / (float)Math.PI));
                //this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, (float)this.maxTurn));

                if (this.lockToView) {
                    double pitchDegreeDifference = Mth.degreesDifferenceAbs(this.mob.getXRot(), DEMath.getTargetPitch(this.mob, this.wantedX, this.wantedY, this.wantedZ));

                    if (pitchDegreeDifference < 20.0F) {
                        f1 *= 1.0F - pitchDegreeDifference / 20.0F;
                    }
                }

                moveVertically(deltaY > 0.0D ? (float) Math.min(f1, deltaY) : (float) Math.max(-f1, deltaY));
            }

          //  ((ServerLevel)this.mob.level).sendParticles(ParticleTypes.HEART, this.wantedX, this.wantedY, this.wantedZ, 1, 0.0, 0.0, 0.0, 0.0);
        } else {

//            moveVertically(0.0F);
//            moveHorizontally(0.0F);
        }

    }

    private void moveHorizontally(float movementAmount) {
        this.advanceMotion.addMotion(DEMath.fromPitchYaw(0, DEMath.getTargetYaw(this.mob, this.wantedX, this.wantedZ)).scale(movementAmount * 0.07));
    }

    private void moveVertically(float movementAmount) {
        this.advanceMotion.addMotion(0, movementAmount * 0.07, 0);
    }
}

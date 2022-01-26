package net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers;

import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.dannybaseentity.IFlapper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlapController extends MoveControl {
    private final int maxTurns;
    private final boolean hoversInPlace;

    public <E extends Mob & IFlapper> FlapController(E mob, int maxTurns, boolean hoversInPlace) {
        super(mob);
        this.maxTurns = maxTurns;
        this.hoversInPlace = hoversInPlace;
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            this.mob.setNoGravity(true);
            double xDif = this.wantedX - this.mob.getX();
            double yDif = this.wantedY - this.mob.getY();
            double zDif = this.wantedZ - this.mob.getZ();
            double rawDistance = xDif * xDif + yDif * yDif + zDif * zDif;
            double d4 = Mth.sqrt((float) (xDif * xDif + zDif * zDif));

            float yawToTarget = DEMath.getTargetYaw(this.mob, this.wantedX, this.wantedZ);
            float pitchToTarget = (float) -(Mth.atan2(yDif, d4) * (double)(180F / (float)Math.PI));
            Vec3 fromPitch = DEMath.fromPitchYaw(pitchToTarget, yawToTarget);
            float yawModifier = DEMath.getHorizontalDistance(0, 0, fromPitch.x, fromPitch.z);



            if (rawDistance < (double)2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }

            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yawToTarget, this.mob.getHeadRotSpeed()));

            if (!PieceUtil.noCollision(this.mob.level, this.mob.blockPosition().below())) {
                this.mob.push(0, 0.025, 0);
            }

            float aiSpeedFactor = (float)( this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            if (this.mob.isOnGround()) {
                aiSpeedFactor = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }

            this.mob.setSpeed(aiSpeedFactor * 2);

            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), pitchToTarget, (float)this.maxTurns));

//            if (yDif > 0.0D) {
//                IFlapper flapMob = (IFlapper)mob;
//                if (flapMob.canFlap()) {
//                    if (yDif > 0.3F) {
//                        flapMob.flap();
//                    } else {
//                        this.mob.setMoveVertical((float)Math.min(fromPitch.y * aiSpeedFactor, 0.2));
//                    }
//
//                } else {
                    this.mob.setYya((float) (2 * fromPitch.y * aiSpeedFactor));
//                }
//            } else {
//                this.mob.setMoveVertical((float) (4.5F * (-fromPitch.y * fromPitch.y) * aiSpeedFactor));
//            }

        } else {
            if (!this.hoversInPlace) {
                this.mob.setNoGravity(false);
            }

            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }

    }

    public void doFlap() {

    }
}

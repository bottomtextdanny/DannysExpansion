package bottomtextdanny.dannys_expansion.content.entities.ai.controllers;

import bottomtextdanny.dannys_expansion._util.DEMath;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

import java.util.Optional;

public class MGLookController extends LookControl {
    protected float targetYaw;
    protected float targetPitch;

    public MGLookController(Mob mob) {
        super(mob);
        this.yMaxRotSpeed = 5.0F;
    }

    public void tick() {

        if (this.isLookingAtTarget()) {
            float targetYaw = this.getYRotD().get();
            float targetPitch = this.getXRotD().get();

            if ((int)this.mob.yHeadRot == targetYaw) {
                this.lookAtCooldown = 0;
            }



            this.mob.yHeadRot = DEMath.approachDegrees(this.yMaxRotSpeed, this.mob.yHeadRot, targetYaw);

            this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), targetPitch, this.xMaxRotAngle));
        }
    }

    public void setLookAt(double x, double y, double z, float deltaYaw, float deltaPitch) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        setTargetYaw(vecToYaw(), deltaYaw);
        setTargetPitch(vecToPitch(), deltaPitch);
    }

    public void setTargetYaw(float targetYaw, float delta) {
        this.targetYaw = targetYaw;
        if (!isLookingAtTarget())this.lookAtCooldown = 2;
        this.yMaxRotSpeed = delta;

    }

    public void setTargetPitch(float targetPitch, float delta) {
        this.targetPitch = targetPitch;
        if (!isLookingAtTarget())this.lookAtCooldown = 2;
        this.xMaxRotAngle = delta;
    }

    @Override
    public Optional<Float> getYRotD() {
        return Optional.of(this.targetYaw);
    }

    @Override
    public Optional<Float> getXRotD() {
        return Optional.of(this.targetPitch);
    }


    protected float vecToPitch() {
        double d0 = this.wantedX - this.mob.getX();
        double d1 = this.wantedY - this.mob.getEyeY();
        double d2 = this.wantedZ - this.mob.getZ();
        double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
        return (float) -(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI));
    }

    protected float vecToYaw() {
        double d0 = this.wantedX - this.mob.getX();
        double d1 = this.wantedZ - this.mob.getZ();
        return (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    }
}

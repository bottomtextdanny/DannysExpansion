package net.bottomtextdanny.dannys_expansion.common.Entities.ai.controllers;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

import java.util.function.Supplier;

public class EasedLookControl extends LookControl {
    private final Supplier<Float> yRotLerp, xRotLerp;

    public EasedLookControl(Mob mob, Supplier<Float> yRotLerp, Supplier<Float> xRotLerp) {
        super(mob);
        this.yRotLerp = yRotLerp;
        this.xRotLerp = xRotLerp;
    }

    @Override
    public void tick() {
        float yawLerpFactor = this.yRotLerp.get();
        float pitchLerpFactor = this.xRotLerp.get();

        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;

            if (this.getYRotD().isPresent()) {
                float yRot = DEMath.approachDegrees(yawLerpFactor, this.mob.yHeadRot, this.getYRotD().get());
                this.mob.setYRot(yRot);
                this.mob.yBodyRot = yRot;
                this.mob.yHeadRot = yRot;
            }

            if (this.getXRotD().isPresent()) {
                this.mob.setXRot(DEMath.approachDegrees(pitchLerpFactor, this.mob.getXRot(), this.getXRotD().get()));
            }
        } else {
            if (this.getYRotD().isPresent()) {
                this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.getYRotD().get(), this.yMaxRotSpeed);
            }
        }

      //  this.clampHeadRotationToBody();
    }
}

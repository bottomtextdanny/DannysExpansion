package net.bottomtextdanny.dannys_expansion.core.Util.animation;

import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class Vec3Animator extends SimpleAnimator<Vec3> {

    public Vec3Animator(float actual) {
        this.actual = actual;
    }

    public Vec3Animator(Timer actual) {
        this.actual = actual.getCount();
    }

    @Override
    public Vec3 fallback(Vec3 obj) {
        return obj == null ? Vec3.ZERO : obj;
    }

    @Override
    public Vec3 get(Easing easing, float ptc) {
        ptc += this.actual;
        double easeFactor = easing.progression(ptc - this.prevKeyframe) / (this.keyframe - this.prevKeyframe);

        return new Vec3(
                Mth.lerp(easeFactor, this.prevGoal.x, this.goal.x),
                Mth.lerp(easeFactor, this.prevGoal.y, this.goal.y),
                Mth.lerp(easeFactor, this.prevGoal.z, this.goal.z));
    }
}

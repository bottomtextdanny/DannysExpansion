package bottomtextdanny.dannys_expansion._base.animation;

import bottomtextdanny.braincell.base.Easing;
import net.minecraft.util.Mth;

public class FloatAnimator extends SimpleAnimator<Float> {

    public FloatAnimator(float actual) {
        this.actual = actual;
    }

    @Override
    public Float fallback(Float obj) {
        return obj == null ? 0.0F : obj;
    }

    @Override
    public Float get(Easing easing, float ptc) {
        ptc += this.actual;
        return Mth.lerp(easing.progression((ptc - this.prevKeyframe) / (this.keyframe - this.prevKeyframe)), this.prevGoal, this.goal);
    }
}

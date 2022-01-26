package net.bottomtextdanny.dannys_expansion.core.Util.random;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

import java.util.SplittableRandom;

public class Noise {
    private final SplittableRandom random;
    private float prevPoint, nextPoint;
    private float progression;

    public Noise(SplittableRandom rand) {
        this.random = rand;
        this.prevPoint = (float)rand.nextDouble();
        this.nextPoint = (float)rand.nextDouble();
    }

    public double getNext(float addition) {
        float loopedAddition = DEMath.loop(this.progression, 1.0F, addition);

        if (loopedAddition < this.progression) {
            this.prevPoint = this.nextPoint;
            this.nextPoint = (float) this.random.nextDouble();
        }

        this.progression = loopedAddition;

        return Mth.lerp(Easing.EASE_IN_OUT_SINE.progression(loopedAddition), this.prevPoint, this.nextPoint);
    }
}

package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import java.util.Random;
import java.util.SplittableRandom;

public class RandomFloatMapper {
    protected final float minimum, maximum;

    private RandomFloatMapper(float minimum, float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static RandomFloatMapper of(float minimum, float maximum) {
        return new RandomFloatMapper(minimum, maximum);
    }

    public static RandomFloatMapper of(float value) {
        return new FakeRangedInteger(value);
    }

    public float map(SplittableRandom random) {
        return this.minimum + random.nextFloat(this.maximum - this.minimum);
    }

    public float map(Random random) {
        return this.minimum + random.nextFloat(this.maximum - this.minimum);
    }

    private static class FakeRangedInteger extends RandomFloatMapper {

        private FakeRangedInteger(float value) {
            super(value, value);
        }

        @Override
        public float map(SplittableRandom random) {
            return this.minimum;
        }

        @Override
        public float map(Random random) {
            return this.minimum;
        }
    }
}

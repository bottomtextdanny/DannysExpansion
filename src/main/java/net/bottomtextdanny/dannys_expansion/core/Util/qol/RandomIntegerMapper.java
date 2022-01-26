package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import java.util.Random;
import java.util.SplittableRandom;

public class RandomIntegerMapper {
    protected final int minimum, maximum;

    private RandomIntegerMapper(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static RandomIntegerMapper of(int minimum, int maximum) {
        return new RandomIntegerMapper(minimum, maximum);
    }

    public static RandomIntegerMapper of(int value) {
        return new FakeRangedInteger(value);
    }

    public int map(SplittableRandom random) {
        return this.minimum + random.nextInt(this.maximum - this.minimum);
    }

    public int map(Random random) {
        return this.minimum + random.nextInt(this.maximum - this.minimum);
    }

    private static class FakeRangedInteger extends RandomIntegerMapper {

        private FakeRangedInteger(int value) {
            super(value, value);
        }

        @Override
        public int map(SplittableRandom random) {
            return this.minimum;
        }

        @Override
        public int map(Random random) {
            return this.minimum;
        }
    }
}

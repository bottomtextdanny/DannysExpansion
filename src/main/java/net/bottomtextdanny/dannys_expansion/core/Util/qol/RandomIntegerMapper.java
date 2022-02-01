package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import java.util.Random;
import java.util.SplittableRandom;

public abstract class RandomIntegerMapper {

    private RandomIntegerMapper() {}

    public static RandomIntegerMapper of(int minimum, int maximum) {
        return new RangedInteger(minimum, maximum);
    }

    public static RandomIntegerMapper of(int value) {
        return new FakeRangedInteger(value);
    }

    public abstract int map(SplittableRandom random);

    public abstract int map(Random random);

    private static class FakeRangedInteger extends RandomIntegerMapper {
        private final int value;

        private FakeRangedInteger(int value) {
            this.value = value;
        }

        @Override
        public int map(SplittableRandom random) {
            return this.value;
        }

        @Override
        public int map(Random random) {
            return this.value;
        }
    }

    private static class RangedInteger extends RandomIntegerMapper {
        private final int minimum;
        private final int maximum;

        private RangedInteger(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public int map(SplittableRandom random) {
            return this.minimum + random.nextInt(this.maximum - this.minimum);
        }

        public int map(Random random) {
            return this.minimum + random.nextInt(this.maximum - this.minimum);
        }
    }
}

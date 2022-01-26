package net.bottomtextdanny.braincell.underlying.util.random;

import java.util.Random;
import java.util.SplittableRandom;

public final class DCRandomRange {

    public static int mapInt(Random random, int min, int max) {
        return random.nextInt(max-min)+min;
    }

    public static int mapInt(SplittableRandom random, int min, int max) {
        return random.nextInt(max-min)+min;
    }

    public static float mapFloat(Random random, float min, float max) {
        return random.nextFloat(max-min)+min;
    }

    public static float mapFloat(SplittableRandom random, float min, float max) {
        return random.nextFloat(max-min)+min;
    }

    public static double mapDouble(Random random, double min, double max) {
        return random.nextDouble(max-min)+min;
    }

    public static double mapDouble(SplittableRandom random, double min, double max) {
        return random.nextDouble(max-min)+min;
    }
}

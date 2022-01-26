package net.bottomtextdanny.braincell.underlying.util;

import net.minecraft.util.Mth;

public final class BCMath {
    public static final double SQRT_2 = 1.4142135623730951;
    public static final double RAD = Math.PI / 180.0;
    public static final float FRAD = (float)RAD;
    public static final float FPI = (float)Math.PI;
    public static final double PI_BY_TWO = Math.PI * 2.0;
    public static final float FPI_BY_TWO = (float)PI_BY_TWO;
    public static final double PI_HALF = Math.PI / 2.0;
    public static final float FPI_HALF = (float)PI_HALF;

    public static double fastpow(final double val, final double expo) {
        long tmp = Double.doubleToLongBits(val);
        long tmp2 = (long)(expo * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
    }

    public static float sin(float rad) {
        return Mth.sin(rad);
    }

    public static float cos(float rad) {
        return Mth.cos(rad);
    }

    public static float sin(double rad) {
        return Mth.sin((float)rad);
    }

    public static float cos(double rad) {
        return Mth.cos((float)rad);
    }
}

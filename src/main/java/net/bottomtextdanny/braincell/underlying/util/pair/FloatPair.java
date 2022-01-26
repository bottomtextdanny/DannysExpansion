package net.bottomtextdanny.braincell.underlying.util.pair;

/**
 * <p>Record implementation of {@link Tuple} that defaults {@link Float} as left type.</p>
 *
 * @param <T> right element type.
 */
public record FloatPair<T>(Float left, T right) implements Tuple<Float, T> {

    /**
     * <p>A fancy instantiation.</p>
     * @param left the {@code float} value that will be taken as {@link #left} value.
     * @param right the {@param <T>} value that will be taken as {@link #right} value.
     * @return a newly created instance with the given parameters.
     */
    public static <T> FloatPair<T> of(float left, T right) {
        return new FloatPair<>(left, right);
    }
}

package net.bottomtextdanny.braincell.underlying.util.pair;

/**
 * <p>Record implementation of {@link Tuple} that defaults {@link Double} as left type.</p>
 *
 * @param <T> right element type.
 */
public record DoublePair<T>(Double left, T right) implements Tuple<Double, T>  {

    /**
     * <p>A fancy instantiation.</p>
     * @param left the {@code double} value that will be taken as {@link #left} value.
     * @param right the {@param <T>} value that will be taken as {@link #right} value.
     * @return a newly created instance with the given parameters.
     */
    public static <T> DoublePair<T> of(double left, T right) {
        return new DoublePair<>(left, right);
    }
}

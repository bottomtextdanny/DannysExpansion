package net.bottomtextdanny.braincell.underlying.util.pair;

/**
 * <p>Record implementation of {@link Tuple} that defaults {@link Byte} as left type.</p>
 *
 * @param <T> right element type.
 */
public record BytePair<T>(Byte left, T right) implements Tuple<Byte, T> {

    /**
     * <p>A fancy instantiation.</p>
     * @param left the {@code byte} value that will be taken as {@link #left} value.
     * @param right the {@param <T>} value that will be taken as {@link #right} value.
     * @return a newly created instance with the given parameters.
     */
    public static <T> BytePair<T> of(byte left, T right) {
        return new BytePair<>(left, right);
    }
}

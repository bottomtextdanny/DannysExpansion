package net.bottomtextdanny.braincell.underlying.util.pair;

/**
 * <p>Record implementation of {@link Tuple} that defaults {@link Long} as left type.</p>
 *
 * @param <T> right element type.
 */
public record LongPair<T>(Long left, T right) implements Tuple<Long, T> {

    /**
     * <p>A fancy instantiation.</p>
     * @param left the {@code long} value that will be taken as {@link #left} value.
     * @param right the {@param <T>} value that will be taken as {@link #right} value.
     * @return a newly created instance with the given parameters.
     */
    public static <T> LongPair<T> of(long left, T right) {
        return new LongPair<>(left, right);
    }
}

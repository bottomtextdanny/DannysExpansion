package net.bottomtextdanny.braincell.underlying.util.pair;

/**
 * <p>Basic tuple interface mostly thought for polymorphism between implementations.</p>
 *
 * <p>Immutability is not enforced,
 * however would be nice for mutable implementations to explicitly state it.</p>
 * @see MutablePair
 *
 * <p>implementations should store an instance of both {@param <L>} and {@param <R>} parameters,
 * which are enforced to be called by their corresponding methods.<p>
 *
 * @param <L> left element type.
 * @param <R> right element type.
 *
 * @see IntPair
 */
public interface Tuple<L, R> {

    L left();

    R right();
}

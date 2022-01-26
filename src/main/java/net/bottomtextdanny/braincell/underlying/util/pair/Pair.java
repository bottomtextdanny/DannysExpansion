package net.bottomtextdanny.braincell.underlying.util.pair;

public record Pair<L,R>(L left, R right) implements Tuple<L,R> {

    public static <L,R> Pair<L,R> of(L left, R right) {
        return new Pair<>(left, right);
    }
}

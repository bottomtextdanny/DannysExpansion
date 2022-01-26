package net.bottomtextdanny.braincell.underlying.util.pair;

public class MutablePair<L, R> implements Tuple<L, R> {
    private L left;
    private R right;

    public MutablePair(L leftValue, R rightValue) {
        super();
        this.left = leftValue;
        this.right = rightValue;
    }

    @Override
    public L left() {
        return this.left;
    }

    @Override
    public R right() {
        return this.right;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public void setRight(R right) {
        this.right = right;
    }
}

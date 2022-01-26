package net.bottomtextdanny.braincell.mod.base.misc.timer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;

public abstract class IntScheduler implements Scheduler<Integer> {
    public static final ThreadLocalRandom INNER_RANDOM = ThreadLocalRandom.current();
    protected int current;

    public static IntScheduler.Simple simple(int bound) {
        return new IntScheduler.Simple(bound);
    }

    public static IntScheduler.Variable variable(IntSupplier bound) {
        return new IntScheduler.Variable(bound);
    }

    public static IntScheduler.Variable ranged(int min, int max) {
        return new IntScheduler.Variable(() -> INNER_RANDOM.nextInt(max - min) + min);
    }

    public void incrementFreely(int step) {
        this.current = Math.min(step + this.current, bound());
    }

    @Override
    public void reset() {
        this.current = 0;
    }

    @Override
    public void end() {
        this.current = bound();
    }

    @Override
    public Integer current() {
        return this.current;
    }

    @Override
    public Integer bound() {
        return null;
    }

    @Override
    public boolean hasEnded() {
        return this.current >= bound();
    }

    public static final class Simple extends IntScheduler {
        private final int bound;

        private Simple(int bound) {
            this.bound = bound;
        }

        public Simple(int bound, int current) {
            this.bound = bound;
            this.current = current;
        }

        @Override
        public void advance() {
            if (this.current < this.bound)
                this.current++;
        }

        @Override
        public Integer bound() {
            return this.bound;
        }
    }

    public static final class Variable extends IntScheduler {
        private final IntSupplier nextBoundSupplier;
        private int currentBound;

        private Variable(IntSupplier boundSupplier) {
            this.nextBoundSupplier = boundSupplier;
            this.currentBound = boundSupplier.getAsInt();
        }

        public Variable(IntSupplier boundSupplier, int currentBound, int current) {
            this.nextBoundSupplier = boundSupplier;
            this.currentBound = currentBound;
            this.current = current;
        }

        @Override
        public void reset() {
            super.reset();
            this.currentBound = this.nextBoundSupplier.getAsInt();
        }

        @Override
        public void advance() {
            if (this.current < this.currentBound)
                this.current++;
        }

        @Override
        public Integer bound() {
            return this.currentBound;
        }

        public void setCurrentBound(int currentBound) {
            this.currentBound = currentBound;
        }

        public IntSupplier getNextBoundSupplier() {
            return this.nextBoundSupplier;
        }
    }
}

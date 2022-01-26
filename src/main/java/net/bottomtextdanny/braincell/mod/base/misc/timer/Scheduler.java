package net.bottomtextdanny.braincell.mod.base.misc.timer;

public interface Scheduler<T> {

    void reset();

    void end();

    void advance();

    T current();

    T bound();

    boolean hasEnded();
}

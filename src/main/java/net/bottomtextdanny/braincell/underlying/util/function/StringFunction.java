package net.bottomtextdanny.braincell.underlying.util.function;

@FunctionalInterface
public interface StringFunction<T> {

    T apply(String value);
}

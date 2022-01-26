package net.bottomtextdanny.braincell.mod.entity.psyche.data;

import java.util.function.Function;

public interface FunctionalActionInput<T, R> extends ActionInput, Function<T, R> {
}

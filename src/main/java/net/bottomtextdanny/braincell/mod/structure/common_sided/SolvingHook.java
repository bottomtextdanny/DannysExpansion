package net.bottomtextdanny.braincell.mod.structure.common_sided;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;

import java.util.Objects;

@FunctionalInterface
public interface SolvingHook<T> {

    void execute(T object, ModDeferringManager solving);

    default SolvingHook<T> append(SolvingHook<T> after) {
        Objects.requireNonNull(after);
        return (object, solving) -> {
            execute(object, solving);
            after.execute(object, solving);
        };
    }
}

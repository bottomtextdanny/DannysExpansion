package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;

public interface ExtraBlockRegisterer<T> {

    void registerExtra(T object, ModDeferringManager solving);

    default boolean executeSideRegistry() {
        return true;
    }
}

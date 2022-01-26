package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features;

import com.google.common.collect.Lists;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class BCRegistry<T extends IForgeRegistryEntry<T>> {
    private final List<Supplier<? extends T>> insideEntries = Lists.newLinkedList();
    private final List<Supplier<? extends T>> registryEntries = Lists.newLinkedList();

    public BCRegistry(boolean sort) {
        super();
    }

    public BCRegistry() {
        super();
    }

    public void addDeferredSolving(Supplier<? extends T> objectSupplier) {
        this.insideEntries.add(objectSupplier);
        addDeferredRegistry(objectSupplier);
    }

    public void addDeferredRegistry(Supplier<? extends T> objectSupplier) {
        this.registryEntries.add(objectSupplier);
    }


    public final void registerAll(RegistryEvent.Register<T> event) {
        this.registryEntries.forEach(entry -> {
            event.getRegistry().register(entry.get());
        });
    }

    public Collection<Supplier<? extends T>> getSolvingEntries() {
        return Collections.unmodifiableCollection(this.insideEntries);
    }

    public Collection<Supplier<? extends T>> getRegistryEntries() {
        return Collections.unmodifiableCollection(this.registryEntries);
    }

}

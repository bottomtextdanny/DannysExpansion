package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features;

import com.google.common.collect.Maps;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.structure.common_sided.SolvingHook;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public final class ModDeferringManager {
    public static final Supplier<UnsupportedOperationException> CANNOT_PERFORM_OPERATIONS_ON_CLOSED_STATE =
            () -> new UnsupportedOperationException("Attempted to perform operation on closed solving state");
    private final LinkedHashMap<DeferrorType<?>, BCRegistry<?>> deferrorAccessors;
    private final Map<DeferrorType<?>, SolvingHook<?>> hooks;
    private final String modID;
    boolean open = true;

    public ModDeferringManager(String modID) {
        super();
        this.modID = modID;
        this.deferrorAccessors = Maps.newLinkedHashMap();
        this.hooks = Maps.newIdentityHashMap();
    }

    public <T extends IForgeRegistryEntry<T>> void addRegistryDeferror(DeferrorType<T> type, BCRegistry<T> registry) {
        if (this.open) {
            if (this.deferrorAccessors.containsKey(type)) {
                throw repeatedAccessorEntryException(type, this.modID);
            } else {
                this.deferrorAccessors.put(type, registry);
            }
        } else throw CANNOT_PERFORM_OPERATIONS_ON_CLOSED_STATE.get();
    }

    @SuppressWarnings("unchecked")
    public <T extends IForgeRegistryEntry<T>> Optional<BCRegistry<T>> getRegistryDeferror(DeferrorType<T> type) {
        return Optional.ofNullable((BCRegistry<T>)this.deferrorAccessors.get(type));
    }

    public void solveAndLockForeverEver() {
        if (this.open) {
            this.deferrorAccessors.forEach((type, registry) -> {
                solveDeferror(type, registry);
            });
            this.open = false;
        }
    }

    public <T extends IForgeRegistryEntry<T>> void addHook(DeferrorType<T> type, SolvingHook<T> hook) {
        if (this.open) {
            if (this.hooks.containsKey(type)) {
                this.hooks.put(type, ((SolvingHook<T>) this.hooks.get(type)).append(hook));
            } else {
                this.hooks.put(type, hook);
            }
        } else throw CANNOT_PERFORM_OPERATIONS_ON_CLOSED_STATE.get();
    }

    @Nullable
    public <T extends IForgeRegistryEntry<T>> SolvingHook<T> getHook(DeferrorType<T> type) {
        if (this.hooks.containsKey(type)) {
            return (SolvingHook<T>) this.hooks.get(type);
        } else {
            return null;
        }
    }

    public <T extends IForgeRegistryEntry<T>> void doHooksForObject(DeferrorType<T> type, T object) {
        SolvingHook<T> defaultSolvingHook = Braincell.common().getSolvingHooks().getHook(type);
        SolvingHook<T> solvingHook = getHook(type);

        if (defaultSolvingHook != null) {
            defaultSolvingHook.execute(object, this);
        }

        if (solvingHook != null) {
            solvingHook.execute(object, this);
        }
    }

    private <T extends IForgeRegistryEntry<T>> void solveDeferror(DeferrorType<T> type, BCRegistry<?> rawRegistry) {
        BCRegistry<T> registry = (BCRegistry<T>) rawRegistry;
        SolvingHook<T> solvingHook = getHook(type);
        if (solvingHook != null) {
            SolvingHook<T> defaultSolvingHook = Braincell.common().getSolvingHooks().getHook(type);
            if (defaultSolvingHook != null) {
                solvingHook = solvingHook.append(defaultSolvingHook);
            }
            SolvingHook<T> finalSolvingHook = solvingHook;
            Iterator<Supplier<? extends T>> iterator = registry.getSolvingEntries().iterator();
            while(iterator.hasNext()) {
                Supplier<? extends T> entry = iterator.next();
                if (entry instanceof Wrap<?> wrap) {
                    wrap.solve();
                    if (wrap.obj == null) {
                        throw new IllegalStateException("Solving Object shouldn't be null after solving process");
                    }
                }
                finalSolvingHook.execute(entry.get(), this);
            }
        } else {
            solvingHook = Braincell.common().getSolvingHooks().getHook(type);
            Iterator<Supplier<? extends T>> iterator = registry.getSolvingEntries().iterator();
            while(iterator.hasNext()) {
                Supplier<? extends T> entry = iterator.next();
                if (entry instanceof Wrap<?> wrap) {
                    wrap.solve();
                    if (wrap.obj == null) {
                        throw new IllegalStateException("Solving Object shouldn't be null after solving process.");
                    }
                }
                if (solvingHook != null) {
                    solvingHook.execute(entry.get(), this);
                }
            }
        }
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(type.getClassRef(), registry::registerAll);
    }

    public boolean isOpen() {
        return this.open;
    }

    public String getModID() {
        return this.modID;
    }

    private static IllegalArgumentException repeatedAccessorEntryException(DeferrorType<?> accessorKey, String modId) {
        String message = new StringBuilder("Cannot assign value to accessor key because there is already one; Accessor key:")
                .append(accessorKey.getKey())
                .append(", Mod id:")
                .append(modId)
                .append('.')
                .toString();
        return new IllegalArgumentException(message);
    }
}

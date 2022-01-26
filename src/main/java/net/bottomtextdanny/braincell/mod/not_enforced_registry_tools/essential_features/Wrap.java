package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features;

import net.bottomtextdanny.braincell.Braincell;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Wrap<T> implements Supplier<T> {
	protected final ResourceLocation key;
	protected DeferrorType<? super T> type;
	protected @Nullable Supplier<T> sup;
	protected Consumer<T> solvingCallOut;
	protected T obj;
	@Nullable
	private ModDeferringManager modSolvingState;
	
	public Wrap(ResourceLocation name, Supplier<T> sup) {
		Objects.requireNonNull(sup);
		this.sup = sup;
		this.key = name;
	}

	public void setupForDeferring(ModDeferringManager modSolvingState) {
		if (modSolvingState.isOpen()) {
			this.modSolvingState = modSolvingState;
		}
	}
	
	public void addSolveCallout(Consumer<T> callOut) {
		if (this.solvingCallOut == null) {
            this.solvingCallOut = callOut;
		}
        this.solvingCallOut = this.solvingCallOut.andThen(callOut);
	}
	
	public void solve() {
        this.obj = this.sup.get();
		if (IForgeRegistryEntry.class.isAssignableFrom(this.obj.getClass())) {
			((IForgeRegistryEntry<?>) this.obj).setRegistryName(this.key);
		}
		Object maybeHook = Braincell.common().getSolvingHooks().getHook(this.type);
		if (maybeHook instanceof Consumer<?> hook) {
			((Consumer<? super T>)hook).accept(this.obj);
		}

		if (this.solvingCallOut != null) {
            this.solvingCallOut.accept(this.obj);
		}
        this.sup = null;
	}
	
	public T get() {
		return Objects.requireNonNull(this.obj, String.join(this.key.getNamespace(), " object called before being solved! key: ", this.key.toString()));
	}

	@Nullable
	public ModDeferringManager getModSolvingState() {
		return this.modSolvingState;
	}

	public ResourceLocation getKey() {
		return this.key;
	}
}

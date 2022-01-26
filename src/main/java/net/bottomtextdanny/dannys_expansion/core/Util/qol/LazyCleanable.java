package net.bottomtextdanny.dannys_expansion.core.Util.qol;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LazyCleanable<T> implements Supplier<T> {
    private final Supplier<T> provider;
    private @Nullable T maybeValue;
    private boolean gotten;

    private LazyCleanable(Supplier<T> provider) {
        super();
        this.provider = provider;
    }

    public static <T> LazyCleanable<T> of(Supplier<T> provider) {
        return new LazyCleanable<>(provider);
    }

    public void assertClear() {
        if (this.gotten) {
            this.maybeValue = null;
            this.gotten = false;
        }
    }

    @Override
    public T get() {
        if (!this.gotten) {
            this.maybeValue = this.provider.get();
            this.gotten = true;
        }
        return this.maybeValue;
    }
}

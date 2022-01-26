package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.minecraft.world.entity.PathfinderMob;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ConstantThoughtAction<E extends PathfinderMob> extends Action<E> {
    @Nullable
    protected final Consumer<E> updateFunction;

    public ConstantThoughtAction(E mob, @Nullable Consumer<E> updateFunction) {
        super(mob);
        this.updateFunction = updateFunction;
    }

    public static <E extends PathfinderMob> ConstantThoughtAction<E> withUpdateCallback(E mob, Consumer<E> doOnUpdate) {
        return new ConstantThoughtAction<>(mob, doOnUpdate);
    }

    @Override
    protected void update() {
        if (this.updateFunction != null) this.updateFunction.accept(this.mob);
    }

    @Override
    public final boolean shouldKeepGoing() {
        return active();
    }

    @Override
    public final void onEnd() {
        super.onEnd();
    }
}

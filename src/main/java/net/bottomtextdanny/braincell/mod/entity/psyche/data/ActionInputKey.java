package net.bottomtextdanny.braincell.mod.entity.psyche.data;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.resources.ResourceLocation;

public class ActionInputKey<E extends ActionInput> {

    public static final ActionInputKey<RunnableActionInput> SET_TARGET_CALL = new ActionInputKey<>("set_target");
    public static final ActionInputKey<SupplierActionInput<IntScheduler.Simple>> UNSEEN_TIMER = new ActionInputKey<>("unseen_timer");

    private static int counter;
    public final int id;
    public final ResourceLocation name;

    public ActionInputKey(String name) {
        this.id = counter;
        counter++;
        this.name = new ResourceLocation(DannysExpansion.ID, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ActionInputKey<?> that = (ActionInputKey<?>) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}

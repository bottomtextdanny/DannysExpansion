package net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionGoal extends Goal {
    protected final ModuledMob entity;
    protected final Consumer<ModuledMob> executionConsumer;
    protected final Predicate<ModuledMob> shoulExec;

    public ActionGoal(ModuledMob entity, Predicate<ModuledMob> shoulExec, Consumer<ModuledMob> executionConsumer) {
        this.entity = entity;
        this.shoulExec = shoulExec;
        this.executionConsumer = executionConsumer;
    }

    public ActionGoal(ModuledMob entity, Predicate<ModuledMob> shoulExec) {
        this.entity = entity;
        this.shoulExec = shoulExec;
        this.executionConsumer = dannyEntity -> {};
    }

    @Override
    public void start() {
        this.executionConsumer.accept(this.entity);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return this.shoulExec.test(this.entity);
    }
}

package net.bottomtextdanny.braincell.mod.entity.psyche;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.Action;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputs;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

import java.util.*;

public abstract class Psyche<E extends PathfinderMob> {
    public static final int CHECKS_MODULE = 0;
    private final ActionInputs inputs;
    private final List<List<Action<?>>> runningActionsByModule = Lists.newArrayList();
    private final LinkedList<DeferredAction> addActionsOnNextTick = Lists.newLinkedList();
    private final BooleanList deactivatedModules = new BooleanArrayList();
    private final LinkedList<Integer> deactivateModulesOnNextTick = Lists.newLinkedList();
    private int currentModuleUpdating;
    private final E mob;
    private final Level level;
    private boolean isUpdating;

    public Psyche(E mob) {
        super();
        this.mob = mob;
        this.level = mob.level;
        this.runningActionsByModule.add(Lists.newLinkedList());
        this.deactivatedModules.add(false);
        UnbuiltActionInputs rawInputs = new UnbuiltActionInputs();
        populateInputs(rawInputs);
        this.inputs = new ActionInputs(rawInputs);
    }

    public E getMob() {
        return this.mob;
    }

    public Level getLevel() {
        return this.level;
    }

    public final void coreInitialization() {
        initialize();
    }

    protected abstract void populateInputs(UnbuiltActionInputs inputs);

    protected abstract void initialize();

    public void tryAddRunningAction(Integer module, Action<?> action) {
        if (!action.isRunning() && action.tryStart(module)) {
            if (this.isUpdating) {
                this.addActionsOnNextTick.add(new DeferredAction(module, action));
            } else {
                this.runningActionsByModule.get(module).add(action);
            }
        }
    }

    public void update() {
        for (int i = 0; i < this.deactivatedModules.size(); i++) {
            this.deactivatedModules.set(i, false);
        }

        Iterator<Integer> deactivateModuleIterator = this.deactivateModulesOnNextTick.iterator();
        while (deactivateModuleIterator.hasNext()) {
            int module = deactivateModuleIterator.next();
            forceBlockModuleNow(module);
            deactivateModuleIterator.remove();
        }

        addDeferredActions();

        this.isUpdating = true;

        List<List<Action<?>>> actionsByModule = this.runningActionsByModule;
        for (int i = 0, actionsByModuleSize = actionsByModule.size(); i < actionsByModuleSize; i++) {
            List<Action<?>> actions = actionsByModule.get(i);
            this.currentModuleUpdating = i;
            Iterator<Action<?>> actionIterator = actions.iterator();

            while (actionIterator.hasNext()) {
                Action<?> next = actionIterator.next();
                boolean shouldEnd = !next.shouldKeepGoing();
                boolean stopOnNext = next.cancelNext();
                if (shouldEnd) {
                    next.onEnd();
                    actionIterator.remove();
                } else {
                    next.coreUpdate();
                }

                if (stopOnNext) break;
            }
        }
        this.isUpdating = false;
    }

    private void addDeferredActions() {
        Iterator<DeferredAction> deferredActionsIterator = this.addActionsOnNextTick.iterator();

        while (deferredActionsIterator.hasNext()) {
            DeferredAction deferred = deferredActionsIterator.next();
            this.runningActionsByModule.get(deferred.module).add(deferred.action);
            deferredActionsIterator.remove();
        }
    }

    public boolean isUpdating() {
        return this.isUpdating;
    }

    public void blockModule(int module) {
        if (module <= this.currentModuleUpdating) {
            this.deactivateModulesOnNextTick.add(module);
        } else {
            this.deactivatedModules.set(module, true);
        }
    }

    private void forceBlockModuleNow(int module) {
        this.deactivatedModules.set(module, true);
    }

    public boolean isModuleDeactivated(int module) {
        return this.deactivatedModules.getBoolean(module);
    }

    protected void allocateModules(int modules) {
        for (int i = 0; i < modules; i++) {
            this.runningActionsByModule.add(Lists.newLinkedList());
            this.deactivatedModules.add(false);
        }
    }

    public ActionInputs getInputs() {
        return this.inputs;
    }

    record DeferredAction(int module, Action<?> action) {}
}

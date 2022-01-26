package net.bottomtextdanny.braincell.mod.entity.psyche;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.Action;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputs;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

import java.util.*;

public abstract class Psyche<E extends PathfinderMob> {
    public static final int CHECKS_MODULE = 0;
    private final ActionInputs inputs;
    private final Map<Integer, LinkedList<Action<?>>> runningActionsByModule = Maps.newHashMap();
    private final LinkedList<DeferredAction> addActionsOnNextTick = Lists.newLinkedList();
    private final HashSet<Integer> deactivatedModules = Sets.newHashSet();
    private final LinkedList<Integer> deactivateModulesOnNextTick = Lists.newLinkedList();
    private int currentModuleUpdating;
    private final E mob;
    private final Level level;
    private boolean isUpdating;

    public Psyche(E mob) {
        super();
        this.mob = mob;
        this.level = mob.level;
        this.runningActionsByModule.put(CHECKS_MODULE, Lists.newLinkedList());
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
        this.deactivatedModules.clear();

        Iterator<Integer> deactivateModuleIterator = this.deactivateModulesOnNextTick.iterator();
        while (deactivateModuleIterator.hasNext()) {
            int module = deactivateModuleIterator.next();
            forceBlockModuleNow(module);
            deactivateModuleIterator.remove();
        }

        addDeferredActions();

        this.isUpdating = true;
        for (Map.Entry<Integer, LinkedList<Action<?>>> entry : this.runningActionsByModule.entrySet()) {
            int module;
            LinkedList<Action<?>> actionList;

            try {
                module = entry.getKey();
                this.currentModuleUpdating = module;
                actionList = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException("tickingAI", ise);
            }

            Iterator<Action<?>> actionIterator = actionList.iterator();

            while (actionIterator.hasNext()) {
                Action<?> next = actionIterator.next();
                next.coreUpdate();
                boolean shouldEnd = !next.shouldKeepGoing();
                boolean stopOnNext = next.cancelNext();
                if (shouldEnd) {
                    next.onEnd();
                    actionIterator.remove();
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
            this.deactivatedModules.add(module);
        }
    }

    private void forceBlockModuleNow(int module) {
        this.deactivatedModules.add(module);
    }

    public boolean isModuleDeactivated(int module) {
        return this.deactivatedModules.contains(module);
    }

    protected void initializeActionMap(int... modules) {
        for (int module : modules) {
            this.runningActionsByModule.put(module, Lists.newLinkedList());
        }
    }

    public ActionInputs getInputs() {
        return this.inputs;
    }

    record DeferredAction(int module, Action<?> action) {}
}

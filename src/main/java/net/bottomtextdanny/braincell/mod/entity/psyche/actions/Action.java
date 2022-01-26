package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.world.entity_utilities.PsycheEntity;
import net.minecraft.world.entity.PathfinderMob;

import javax.annotation.Nullable;
import java.util.LinkedList;

public abstract class Action<E extends PathfinderMob> {
    private final LinkedList<Integer> modules = Lists.newLinkedList();
    private int principalModule;
    @Nullable
    private LinkedList<Integer> blockOtherModules;
    protected E mob;
    private final Psyche<E> psyche;
    protected int ticksPassed;
    private boolean active;
    private int lastUpdatedActiveState;
    private boolean running;

    public Action(E mob) {
        super();
        this.mob = mob;
        this.psyche = (Psyche<E>) ((PsycheEntity)mob).getPsyche();
    }

    public boolean active() {

        if (this.lastUpdatedActiveState != this.mob.tickCount) {
            this.lastUpdatedActiveState = this.mob.tickCount;
            this.active = activeParameters();
        }
        return this.active;
    }

    protected boolean activeParameters() {
        return this.modules.stream().noneMatch(module -> this.psyche.isModuleDeactivated(module));
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean tryStart(int module) {
        this.principalModule = module;
        if (canStart()) {
            this.running = true;
            start();
            return true;
        }
        return false;
    }

    protected void start() {}

    public void coreUpdate() {
        if (active()) {
            this.ticksPassed++;
            if (this.blockOtherModules != null) this.blockOtherModules.forEach(module -> this.psyche.blockModule(module));
            update();
        }
    }

    protected void update() {}

    public boolean shouldKeepGoing() {
        return false;
    }

    public boolean canStart() {
        return true;
    }

    public void onEnd() {
        this.running = false;
        this.principalModule = -1;
    }

    public boolean cancelNext() {return false;}

    public void addModule(int module) {
        this.modules.add(module);
    }

    public void addBlockedModule(int module) {
        if (this.blockOtherModules == null) {
            this.blockOtherModules = Lists.newLinkedList();
        }
        this.blockOtherModules.add(module);
    }

    public Psyche<E> getPsyche() {
        return this.psyche;
    }

    protected int getTicksPassed() {
        return this.ticksPassed;
    }
}

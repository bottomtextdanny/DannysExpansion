package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.world.entity.PathfinderMob;

public abstract class OccasionalThoughtAction<E extends PathfinderMob> extends Action<E> {
    protected Timer thoughtSchedule;
    private boolean updateTime;

    public OccasionalThoughtAction(E mob, Timer timedSchedule) {
        super(mob);
        this.thoughtSchedule = timedSchedule;
    }

    public final void update() {
        this.updateTime = false;
        this.thoughtSchedule.tryUpOrElse(timer -> {
            thoughtAction(timer.getCount());
            timer.reset();
            this.updateTime = true;
        });
    }

    public abstract void thoughtAction(int timeSinceBefore);

    public boolean isUpdateTime() {
        return this.updateTime;
    }

    @Override
    public boolean shouldKeepGoing() {
        return active();
    }
}

package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.ai;

import net.bottomtextdanny.braincell.mod.entity.psyche.actions.Action;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;

public class SlimeRotateRandomlyAction extends Action<AbstractSlime> {
    private final RandomIntegerMapper nextDelayGetter;
    private int tillNextRotationCounter;

    public SlimeRotateRandomlyAction(AbstractSlime mob, RandomIntegerMapper nextDelayMapper) {
        super(mob);
        this.nextDelayGetter = nextDelayMapper;
    }

    @Override
    public boolean canStart() {
        return active();
    }

    @Override
    protected void update() {
        if (--this.tillNextRotationCounter < 0) {
            this.tillNextRotationCounter = this.nextDelayGetter.map(UNSAFE_RANDOM);
            this.mob.setYRot(RANDOM.nextInt(360));
        }
    }

    @Override
    public void onEnd() {
        super.onEnd();
        this.tillNextRotationCounter = this.nextDelayGetter.map(UNSAFE_RANDOM);
    }

    @Override
    public boolean shouldKeepGoing() {
        return active();
    }
}

package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.ai;

import net.bottomtextdanny.braincell.mod.entity.psyche.actions.Action;
import net.bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.bottomtextdanny.braincell.mod.world.helpers.ReachHelper;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;

public class SlimeTryAttackTargetAction extends Action<AbstractSlime> {
    private static final int LOOK_UPDATE_MODULO = 4;
    private final int attackDelay;
    private int tillNextAttackCounter;

    public SlimeTryAttackTargetAction(AbstractSlime mob, int attackDelay) {
        super(mob);
        this.attackDelay = attackDelay;
    }

    @Override
    public boolean canStart() {
        return active() && CombatHelper.hasValidAttackTarget(this.mob);
    }

    @Override
    protected void update() {
        if (this.ticksPassed % LOOK_UPDATE_MODULO == 0) {
            this.mob.getLookControl().setLookAt(this.mob.getTarget(), 30.0F, 30.0F);;
            this.mob.setYRot(this.mob.getYHeadRot());
        }

        if (this.tillNextAttackCounter > 0) {
            --this.tillNextAttackCounter;
        } else if (ReachHelper.reachSqr(this.mob, this.mob.getTarget()) < 0.7) {
            this.tillNextAttackCounter = this.attackDelay;
            CombatHelper.attackWithMultiplier(this.mob, this.mob.getTarget(), 0.8F + UNSAFE_RANDOM.nextFloat() * 0.4F);
        }
    }

    @Override
    public void onEnd() {
        super.onEnd();
        this.tillNextAttackCounter = this.attackDelay;
    }

    @Override
    public boolean shouldKeepGoing() {
        return active() && CombatHelper.hasValidAttackTarget(this.mob);
    }
}

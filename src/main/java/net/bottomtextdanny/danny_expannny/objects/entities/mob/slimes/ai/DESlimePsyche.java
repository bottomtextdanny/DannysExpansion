package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.ai;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.ConstantThoughtAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.EntityTarget;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.TargetRange;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.AbstractSlime;
import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DESlimePsyche extends Psyche<AbstractSlime> {
    public static final EntityTarget<LivingEntity> BULLY_TARGETER =
            EntityTarget.Builder.start(TargetRange.followRange())
                    .hasToBeOnSight()
                    .isForCombat()
                    .targetRangeForInvisible(TargetRange.followRange())
                    .build();
    public static final EntityTarget<LivingEntity> PLAYER_TARGETER =
            EntityTarget.Builder.start(TargetRange.followRange())
                    .hasToBeOnSight()
                    .isForCombat()
                    .targetRangeForInvisible(TargetRange.followRange())
                    .prePredicate((slime, target) -> !((AbstractSlime) slime).isSurfaceSlime() ||
                            !(target instanceof Player player && Evaluations.COOL_WITH_SURFACE_SLIMES.test(player)))
                    .build();
    public static final int
            COMBAT_MODULE = 1,
            IDLE_MODULE = 2;
    private final IntScheduler.Simple unseenTimer;
    private SlimeTryAttackTargetAction attackTargetAction;
    private SlimeRotateRandomlyAction rotateRandomlyAction;
    private SlimeHopAction hopAction;

    public DESlimePsyche(AbstractSlime mob) {
        super(mob);
        allocateModules(2);
        this.unseenTimer = IntScheduler.simple(50);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.UNSEEN_TIMER, () -> this.unseenTimer);
    }

    @Override
    protected void initialize() {
        this.hopAction = new SlimeHopAction(this.getMob());
        this.rotateRandomlyAction = new SlimeRotateRandomlyAction(this.getMob(), RandomIntegerMapper.of(70, 120));
        this.attackTargetAction = new SlimeTryAttackTargetAction(this.getMob(), 20);
        this.attackTargetAction.addBlockedModule(IDLE_MODULE);
        tryAddRunningAction(CHECKS_MODULE, new ConstantThoughtAction<>(getMob(), mob -> {
            if (getMob().getTarget() != null) {
                tryAddRunningAction(COMBAT_MODULE, this.attackTargetAction);
            } else {
                tryAddRunningAction(IDLE_MODULE, this.rotateRandomlyAction);
            }
        }));
        tryAddRunningAction(CHECKS_MODULE, this.hopAction);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(this.getMob(), BULLY_TARGETER));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(getMob(), LookForAttackTargetAction.PLAYER_CLASS_TARGET).setEntityTarget(PLAYER_TARGETER));

    }
}

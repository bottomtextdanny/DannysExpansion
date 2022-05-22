package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.ai;

import bottomtextdanny.dannys_expansion.content.entities.Shared;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.AbstractSlime;
import bottomtextdanny.dannys_expansion.DEEvaluations;
import bottomtextdanny.braincell.base.scheduler.IntScheduler;
import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.psyche.MarkedTimer;
import bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import bottomtextdanny.braincell.mod.entity.psyche.actions.ConstantThoughtAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import bottomtextdanny.braincell.mod.entity.psyche.input.ActionInputKey;
import bottomtextdanny.braincell.mod.entity.psyche.input.UnbuiltActionInputs;
import bottomtextdanny.braincell.mod.entity.psyche.targeting.SearchNearestPredicates;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class DESlimePsyche extends Psyche<AbstractSlime> {
    public static final int
            COMBAT_MODULE = 1,
            IDLE_MODULE = 2;
    private final MarkedTimer unseenTimer;
    private SlimeTryAttackTargetAction attackTargetAction;
    private SlimeRotateRandomlyAction rotateRandomlyAction;
    private SlimeHopAction hopAction;

    public DESlimePsyche(AbstractSlime mob) {
        super(mob);
        allocateModules(2);
        this.unseenTimer = new MarkedTimer(IntScheduler.simple(100));
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.MARKED_UNSEEN, () -> this.unseenTimer);
    }

    @Override
    protected void initialize() {
        this.hopAction = new SlimeHopAction(this.getMob());
        this.rotateRandomlyAction = new SlimeRotateRandomlyAction(this.getMob(), RandomIntegerMapper.of(70, 120));
        this.attackTargetAction = new SlimeTryAttackTargetAction(this.getMob(), 20);
        this.attackTargetAction.addBlockedModule(IDLE_MODULE);

        tryAddRunningAction(CHECKS_MODULE, new ConstantThoughtAction<>(getMob(), mob -> {
            if (getMob().getTarget() != null) {
                mob.attackDelay.incrementFreely(1);
                tryAddRunningAction(COMBAT_MODULE, this.attackTargetAction);
            } else {
                tryAddRunningAction(IDLE_MODULE, this.rotateRandomlyAction);
            }
        }));

        tryAddRunningAction(CHECKS_MODULE, this.hopAction);

        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob(), Shared.DEFAULT_TARGET_PARAMETERS));

        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(6),
                Shared.DEFAULT_TARGET_PARAMETERS.and(DESlimePsyche::testAccessories), SearchNearestPredicates.nearestPlayer()));

        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(
                getMob(), IntScheduler.simple(20),
                Shared.GOODIE.and(Shared.DEFAULT_TARGET_PARAMETERS), SearchNearestPredicates.nearestLiving()));
    }

    private static boolean testAccessories(Mob mob, LivingEntity le) {
        return !((AbstractSlime) mob).isSurfaceSlime() || !(le instanceof Player player && DEEvaluations.COOL_WITH_SURFACE_SLIMES.test(player));
    }
}

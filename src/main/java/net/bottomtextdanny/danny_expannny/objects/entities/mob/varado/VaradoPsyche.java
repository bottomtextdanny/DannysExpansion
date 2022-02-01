package net.bottomtextdanny.danny_expannny.objects.entities.mob.varado;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAristocratAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoul;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoulPsyche;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomFloatMapper;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;

public class VaradoPsyche extends Psyche<Varado> {
    public static final int
            MAIN_MODULE = 1,
            ANIMATION_ACTIONS_MODULE = 2,
            IDLE_ACTIONS_MODULE = 3;
    private final IntScheduler.Simple unseenTimer;
    private LookRandomlyAction lookRandomlyAction;
    private RandomStrollAction randomStrollAction;
    private FollowTargetAction<Varado> followTargetAction;

    public VaradoPsyche(Varado mob) {
        super(mob);
        allocateModules(3);
        this.unseenTimer = IntScheduler.simple(20);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.UNSEEN_TIMER, () -> this.unseenTimer);
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void initialize() {
        this.followTargetAction = new FollowTargetAction<>(getMob(), target -> Varado.COMBAT_SPEED_MULTIPLIER).setRefreshRate(12);
        this.followTargetAction.addBlockedModule(IDLE_ACTIONS_MODULE);
        this.randomStrollAction = new RandomStrollAction(getMob(), RandomIntegerMapper.of(60, 120));
        this.randomStrollAction.addModule(IDLE_ACTIONS_MODULE);
        this.lookRandomlyAction = new LookRandomlyAction(getMob(), RandomIntegerMapper.of(120, 160)).vertical(RandomFloatMapper.of(-0.48F, 0.1F));
        this.lookRandomlyAction.addModule(IDLE_ACTIONS_MODULE);
        ConstantThoughtAction<Varado> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {
            if (mobo.getTarget() != null) {
                tryAddRunningAction(MAIN_MODULE, this.followTargetAction);
            } else {
                tryAddRunningAction(MAIN_MODULE, this.randomStrollAction);
                tryAddRunningAction(MAIN_MODULE, this.lookRandomlyAction);
            }
        });
        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(getMob(), LookForAttackTargetAction.PLAYER_CLASS_TARGET));
    }
}

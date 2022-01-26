package net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.braincell.mod.entity.psyche.Psyche;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.*;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAristocratAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.TargetBullyAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.entity.psyche.actions.target.LookForAttackTargetAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.EntityTarget;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.TargetRange;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.UnbuiltActionInputs;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.IceSpike;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class IceElementalPsyche extends Psyche<IceElemental> {
    public static final EntityTarget<LivingEntity> TARGET_PREDICATE =
            EntityTarget.Builder.start(TargetRange.followRange())
                    .isForCombat()
                    .targetRangeForInvisible(TargetRange.followRangeMultiplied(0.75F))
                    .hasToBeOnSight()
                    .build();
    public static final int
            COMBAT_MODULE = 1,
            AVOID_MODULE = 2,
            IDLE_MODULE = 3,
            ATTACK_MODULE = 4;
    private final IntScheduler.Simple unseenTimer = IntScheduler.simple(100);
    private AlfWanderInTheAirAction<IceElemental> wanderAction;
    private AlfFlyFromLookAction<IceElemental> flyAwayFromLookAction;
    private AlfFlyFromLookAction<IceElemental> flyToLookAction;
    private IceSpikeAttackAction iceSpikeAttackAction;
    public IceElementalPsyche(IceElemental mob) {
        super(mob);
        initializeActionMap(COMBAT_MODULE, AVOID_MODULE, IDLE_MODULE, ATTACK_MODULE);
    }

    @Override
    protected void populateInputs(UnbuiltActionInputs inputs) {
        inputs.put(ActionInputKey.UNSEEN_TIMER, () -> this.unseenTimer);
    }

    @Override
    protected void initialize() {
        this.iceSpikeAttackAction = new IceSpikeAttackAction(getMob());
        this.flyAwayFromLookAction = new AlfFlyFromLookAction<>(getMob(), 24.0F, IceElemental.COMBAT_MOVE_SPEED);
        this.flyToLookAction = new AlfFlyFromLookAction<>(getMob(), -24.0F, IceElemental.COMBAT_MOVE_SPEED);
        this.wanderAction = new AlfWanderInTheAirAction<>(getMob(), IceElemental.WANDER_MOVE_SPEED);
        ConstantThoughtAction<IceElemental> globalCheck = ConstantThoughtAction.withUpdateCallback(getMob(), mobo -> {

            if (getMob().isCombatMode()) {
                boolean seen = getMob().getSensing().hasLineOfSight(getMob().getTarget());
                if (seen) {
                    this.unseenTimer.reset();
                } else {
                    this.unseenTimer.advance();
                }
                IntScheduler attackDelay = getMob().attackDelay.get();
                getMob().getLookControl().setLookAt(getMob().getTarget(), 180.0F, 50.0F);
                blockModule(IDLE_MODULE);
                if (seen && TargetRange.followRangeMultiplied(IceElemental.FLEE_RANGE).test(getMob(), getMob().getTarget())) {
                    tryAddRunningAction(AVOID_MODULE, this.flyAwayFromLookAction);
                } else {
                    tryAddRunningAction(COMBAT_MODULE, this.flyToLookAction);
                }
                if (attackDelay.hasEnded()) {
                    if (getMob().getSensing().hasLineOfSight(getMob())) {
                        tryAddRunningAction(ATTACK_MODULE, this.iceSpikeAttackAction);
                    }
                    attackDelay.reset();
                } else {
                    attackDelay.advance();
                }
            } else {
                this.unseenTimer.end();
                tryAddRunningAction(IDLE_MODULE, this.wanderAction);
            }
        });
        tryAddRunningAction(CHECKS_MODULE, globalCheck);
        tryAddRunningAction(CHECKS_MODULE, new TargetBullyAction(getMob()));
        tryAddRunningAction(CHECKS_MODULE, new LookForAttackTargetAction<>(getMob(), LookForAttackTargetAction.PLAYER_CLASS_TARGET).setEntityTarget(TARGET_PREDICATE).setUnseeTimer(new Timer(20)));
        tryAddRunningAction(CHECKS_MODULE, new LookForAristocratAction<>(getMob()).setEntityTarget(TARGET_PREDICATE).setUnseeTimer(new Timer(0)));
        tryAddRunningAction(CHECKS_MODULE, new FloatAction(getMob(), 0.0F));
    }

    public static class IceSpikeAttackAction extends AnimationAction<IceElemental, Animation> {
        private boolean stop;

        public IceSpikeAttackAction(IceElemental mob) {
            super(mob, mob.getIceSpikeAnimation(), mob.mainAnimationHandler);
        }

        @Override
        protected void start() {
            super.start();
            this.stop = this.mob.getRandom().nextFloat() < 0.3F;
        }

        @Override
        public void update() {
            super.update();
            if (this.stop) {
                addBlockedModule(COMBAT_MODULE);
                addBlockedModule(AVOID_MODULE);
            }
            if (this.animationHandler.getTick() == 4) {
                this.mob.level.playSound(null, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), DESounds.ES_ICE_ELEMENTAL_CHARGE_ATTACK.get(), SoundSource.HOSTILE, 1.0F + 0.07F * this.mob.getRandom().nextFloat(), 1.0F + 0.2F * this.mob.getRandom().nextFloat());
            } else if (this.animationHandler.getTick() == 9) {
                float heightDelta = this.mob.getBbHeight() / 2.0F;
                IceSpike iceSpike = new IceSpike(DEEntities.ICE_SPIKE.get(), this.mob.getLevel());
                iceSpike.setCaster(this.mob);
                iceSpike.setPos(this.mob.getX(), this.mob.getY() + heightDelta, this.mob.getZ());
                iceSpike.setXRot(this.mob.getXRot());
                iceSpike.setYRot(this.mob.getYRot());
                this.mob.level.addFreshEntity(iceSpike);
            }
        }
    }
}

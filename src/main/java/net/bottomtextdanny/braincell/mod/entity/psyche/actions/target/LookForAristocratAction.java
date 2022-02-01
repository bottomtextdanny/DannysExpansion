package net.bottomtextdanny.braincell.mod.entity.psyche.actions.target;

import net.bottomtextdanny.braincell.mod.entity.psyche.actions.OccasionalThoughtAction;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.RunnableActionInput;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.EntityTarget;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.TargetRange;
import net.bottomtextdanny.braincell.mod.world.helpers.CombatHelper;
import net.bottomtextdanny.braincell.mod.world.helpers.FilterEntityHelper;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class LookForAristocratAction<E extends PathfinderMob> extends OccasionalThoughtAction<E> {
    public static final int DEFAULT_UPDATE_INTERVAL = 4;
    public static final Predicate<LivingEntity> PLAYER_CLASS_TARGET = e -> e.getClass().isAssignableFrom(Player.class);
    public static final EntityTarget<LivingEntity> DEFAULT_TARGET_PREDICATE =
            EntityTarget.Builder.start(TargetRange.followRange())
                    .isForCombat()
                    .targetRangeForInvisible(TargetRange.fixedRange(2.0D))
                    .hasToBeOnSight()
                    .build();
    @Nullable
    private EntityTarget<LivingEntity> targetPredicate = DEFAULT_TARGET_PREDICATE;
    protected LivingEntity targetAsLocal;
    protected double range;

    public LookForAristocratAction(E mob, Timer updateInterval) {
        super(mob, updateInterval);
    }

    public LookForAristocratAction(E mob) {
        super(mob, new Timer(DEFAULT_UPDATE_INTERVAL));
    }


    public LookForAristocratAction<E> setUnseeTimer(Timer unseeTimer) {
        return this;
    }

    public LookForAristocratAction<E> setEntityTarget(EntityTarget<LivingEntity> predicate) {
        this.targetPredicate = predicate;
        return this;
    }

    public LookForAristocratAction<E> setSearchRange(double range) {
        this.range = range;
        return this;
    }

    protected AABB getTargetSearchArea(double p_26069_) {
        return this.mob.getBoundingBox().inflate(p_26069_, 4.0D, p_26069_);
    }

    protected double getFollowDistance() {
        return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void thoughtAction(int timeSinceBefore) {
        boolean oldTargetIsNull = this.targetAsLocal == null;
        LevelPhaseModule phaseModule = CapabilityHelper.get(this.mob.level, LevelCapability.CAPABILITY).getPhaseModule();
        Predicate<LivingEntity> goodiePredicate = phaseModule.getPhase() == LevelPhaseModule.Phase.NORMAL ? CombatHelper.EntityCheckHelper::isGoodie : CombatHelper.EntityCheckHelper::isPostDragonGoodie;

        this.targetAsLocal = FilterEntityHelper.getNearestLiving((ServerLevel) this.mob.level, this.mob, this.getTargetSearchArea(this.getFollowDistance()), goodiePredicate, this.targetPredicate);

        if (this.targetAsLocal != null) {
            boolean seenTarget = this.mob.hasLineOfSight(this.targetAsLocal);
            if (oldTargetIsNull) {
                this.mob.getNavigation().stop();
            }

            if (this.mob.getTarget() != null && !seenTarget && !this.mob.hasLineOfSight(this.targetAsLocal) && (!this.getPsyche().getInputs().containsInput(ActionInputKey.UNSEEN_TIMER) || this.getPsyche().getInputs().get(ActionInputKey.UNSEEN_TIMER).get().hasEnded())) {
                this.mob.setTarget(null);
                this.mob.getNavigation().stop();
            } else {
                this.mob.setTarget(this.targetAsLocal);
            }
        } else {
            if (this.mob.getTarget() != null && (!this.getPsyche().getInputs().containsInput(ActionInputKey.UNSEEN_TIMER) || this.getPsyche().getInputs().get(ActionInputKey.UNSEEN_TIMER).get().hasEnded())) {
                this.mob.setTarget(null);
                this.mob.getNavigation().stop();
            }
        }

        if (this.targetAsLocal != null && this.targetAsLocal.isAlive() && oldTargetIsNull) {
            RunnableActionInput newTargetInput = getPsyche().getInputs().get(ActionInputKey.SET_TARGET_CALL);
            if (newTargetInput != null) {
                newTargetInput.run();
            }
        }
    }

    @Override
    public boolean cancelNext() {
        return this.targetAsLocal != null || (!this.getPsyche().getInputs().containsInput(ActionInputKey.UNSEEN_TIMER) || !this.getPsyche().getInputs().get(ActionInputKey.UNSEEN_TIMER).get().hasEnded());
    }
}

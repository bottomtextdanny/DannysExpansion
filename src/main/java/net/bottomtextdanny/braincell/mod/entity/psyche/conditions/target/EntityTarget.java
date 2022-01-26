package net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.entity.psyche.data.ActionInputKey;
import net.bottomtextdanny.braincell.mod.world.entity_utilities.PsycheEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;

public class EntityTarget<T extends LivingEntity> implements BiPredicate<PathfinderMob, T> {
    private final TargetRange targetRange;
    @Nullable
    private final TargetRange targetRangeForInvisible;
    private final boolean hasToBeOnSight;
    private final boolean forCombat;
    @Nullable
    private final BiPredicate<PathfinderMob, T> extraPredicate;

    private EntityTarget(TargetRange targetRange, @Nullable TargetRange targetRangeForInvisible, boolean beOnSight, boolean forCombat, @Nullable BiPredicate<PathfinderMob, T> extraPredicate) {
        this.targetRange = targetRange;
        this.targetRangeForInvisible = targetRangeForInvisible;
        this.hasToBeOnSight = beOnSight;
        this.forCombat = forCombat;
        this.extraPredicate = extraPredicate;
    }

    public boolean test(PathfinderMob selector, T posibleTarget) {
        PsycheEntity psycheEntity = (PsycheEntity) selector;
        if (selector == null || selector == posibleTarget) {
            return false;
        } else if (!posibleTarget.canBeSeenByAnyone()) {
            return false;
        } else if (this.extraPredicate != null && !this.extraPredicate.test(selector, posibleTarget)) {
            return false;
        } else {
            if (this.forCombat && (!selector.canAttack(posibleTarget) || !selector.canAttackType(posibleTarget.getType()) || selector.isAlliedTo(posibleTarget))) {
                return false;
            }

            if (posibleTarget.isInvisible()) {
                if (this.targetRangeForInvisible == null || !this.targetRangeForInvisible.test(selector, posibleTarget)) {
                    return false;
                }
            } else if (!this.targetRange.test(selector, posibleTarget)) {
                return false;
            }

            if (!this.hasToBeOnSight) {
                return true;
            } else {

                    return selector.getSensing().hasLineOfSight(posibleTarget);

            }
        }
    }


    public static class Builder<T extends LivingEntity> {
        private final TargetRange targetRange;
        @Nullable
        private TargetRange targetRangeForInvisible;
        private boolean hasToBeOnSight;
        private boolean forCombat;
        private BiPredicate<PathfinderMob, T> extraPredicate;

        private Builder(TargetRange targetRange) {
            this.targetRange = targetRange;
        }

        public static <T extends LivingEntity> Builder<T> start(TargetRange targetRange) {
            return new Builder<>(targetRange);
        }

        public Builder<T> targetRangeForInvisible(TargetRange targetRangeForInvisible) {
            this.targetRangeForInvisible = targetRangeForInvisible;
            return this;
        }

        public Builder<T> hasToBeOnSight() {
            this.hasToBeOnSight = true;
            return this;
        }

        public Builder<T> isForCombat() {
            this.forCombat = true;
            return this;
        }

        public Builder<T> setPostPredicate(BiPredicate<PathfinderMob, T> extraPredicate) {
            this.extraPredicate = extraPredicate;
            return this;
        }

        public EntityTarget<T> build() {
            return new EntityTarget<>(this.targetRange, this.targetRangeForInvisible, this.hasToBeOnSight, this.forCombat, this.extraPredicate);
        }
    }
}

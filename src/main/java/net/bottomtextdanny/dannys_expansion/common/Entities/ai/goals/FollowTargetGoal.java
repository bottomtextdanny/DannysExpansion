package net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.pathfinder.Path;

public class FollowTargetGoal extends TargetGoal {
    protected final PathfinderMob mob;
    private final double moveSpeed;
    private final float stopAt;

    private Path path;
    private int delayCounter;
    private int delayFactor = 5;

    public FollowTargetGoal(ModuledMob creatureEntity, double moveSpeed, float stopAt, int delay) {
        super(creatureEntity, true);
        this.mob = creatureEntity;
        this.moveSpeed = moveSpeed;
        this.stopAt = stopAt;
        this.delayFactor = delay;
    }

    public FollowTargetGoal(ModuledMob creatureEntity, double moveSpeed, float stopAt) {
        super(creatureEntity, true);
        this.mob = creatureEntity;
        this.moveSpeed = moveSpeed;
        this.stopAt = stopAt;
    }

    public FollowTargetGoal(ModuledMob creatureEntity, double moveSpeed) {
        super(creatureEntity, true);
        this.mob = creatureEntity;
        this.moveSpeed = moveSpeed;
        this.stopAt = 0;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null || !livingEntity.isAlive()) {
            return false;
        } else {
            this.path = this.mob.getNavigation().createPath(livingEntity, 0);
            return this.path != null;
        }

    }

    @Override
    public void start() {
        if (((ModuledMob) this.mob).sleepPathSchedule.isWoke()) {
            this.mob.getNavigation().moveTo(this.path, this.moveSpeed);
        }

        this.mob.setAggressive(true);
        this.delayCounter = 0;
    }

    @Override
    public void tick() {
        if (!this.mob.getTarget().isAlive()) {
            this.mob.setTarget(null);
            return;
        }
        LivingEntity livingentity = this.mob.getTarget();
        this.mob.getLookControl().setLookAt(livingentity, 30.0F, 90.0F);
        double d0 = this.mob.distanceToSqr(livingentity);

        if (--this.delayCounter <= 0) {
            this.delayCounter = this.delayFactor;
            this.mob.getNavigation().moveTo(livingentity, this.moveSpeed);
        }

        if (this.mob.hasLineOfSight(livingentity) && d0 < this.stopAt) {
            this.mob.getNavigation().stop();
        }

    }
}

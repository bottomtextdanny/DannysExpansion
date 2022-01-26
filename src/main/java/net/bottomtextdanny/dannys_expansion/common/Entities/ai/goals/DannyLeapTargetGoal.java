package net.bottomtextdanny.dannys_expansion.common.Entities.ai.goals;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DannyLeapTargetGoal extends Goal {
    private final Timer timer;
    private final ModuledMob entity;
    private final float vMovement;
    private final float hMovement;
    private final float attackDistance;
    private float cachedReach;
    @Nullable

    public DannyLeapTargetGoal(ModuledMob entity, int duration, float vMovement, float hMovement, float distance) {
        setFlags(EnumSet.of(Flag.MOVE));
        this.entity = entity;
        this.vMovement = vMovement;
        this.hMovement = hMovement;
        this.attackDistance = distance;
        this.timer = new Timer(duration);
    }

    @Override
    public void start() {
        super.start();
        this.timer.reset();
        this.entity.meleeTimer.reset();
    }

    @Override
    public void tick() {
        super.tick();
        this.timer.tryUp();

        if (this.entity.hasAttackTarget()) {
            Vec3 vec0 = DEMath.fromPitchYaw(0, this.entity.yHeadRot);

            if (this.timer.getCount() < 7) {
                this.entity.hasImpulse = true;
                double motionX = this.entity.getDeltaMovement().x;
                double motionY = this.entity.getDeltaMovement().y;
                double motionZ = this.entity.getDeltaMovement().z;

                float hFactor = Math.max((1 - DEMath.getHorizontalDistance(this.entity, this.entity.getTarget()) / this.attackDistance) * this.hMovement, this.hMovement * 0.4F);
                double yFactor = this.entity.getY() - this.entity.getTarget().getY() > -0.05 ? this.vMovement : 0;
                this.entity.setDeltaMovement(DEMath.maxMinSignA(vec0.x * hFactor, motionX), this.timer.getCount() < 2 ? Math.max(yFactor, motionY) : motionY, DEMath.maxMinSignA(vec0.z * hFactor, motionZ));


                this.entity.setYRot(this.entity.yHeadRot);
                this.entity.yBodyRot = this.entity.yHeadRot;
            }

            LivingEntity target = this.entity.getTarget();
            if (DEMath.cylindersCollide(
                    this.entity.position().subtract(0, -0.2, 0)
                    , this.entity.getBbHeight() + 0.2F
                    , this.entity.getBbWidth() / 2,
                    target.position().subtract(0, -0.2, 0)
                    , target.getBbHeight() + 0.2F
                    , target.getBbWidth() / 2
            )) {
                ModuledMob.disableShield(this.entity.getTarget(), 0);
                this.entity.doHurtTarget(this.entity.getTarget());

                this.timer.end();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.timer.hasEnded();
    }

    @Override
    public void stop() {
        super.stop();
        this.timer.reset();
        this.entity.meleeTimer.reset();
    }

    @Override
    public boolean canUse() {
        boolean flag = this.entity.hasAttackTarget() && Mth.abs(Mth.degreesDifference(this.entity.getYHeadRot(), DEMath.getTargetYaw(this.entity, this.entity.getTarget()))) < 20;
        return flag && this.entity.meleeTimer.hasEnded() && (this.cachedReach = this.entity.reachTo(this.entity.getTarget())) <= this.attackDistance;
    }
}

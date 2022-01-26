package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RandomStrollAction extends Action<PathfinderMob> {
    public static final RandomIntegerMapper DEFAULT_STROLL_TIME = RandomIntegerMapper.of(120, 150);
    protected RandomIntegerMapper strollTime;
    protected int timeTillStroll;
    protected float speedModifier = 1.0F;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;

    public RandomStrollAction(PathfinderMob mob, RandomIntegerMapper strollTime) {
        super(mob);
        this.strollTime = strollTime;
        this.timeTillStroll = strollTime.map(DEUtil.PSYCHE_RANDOM);
    }

    public RandomStrollAction(PathfinderMob mob) {
        this(mob, DEFAULT_STROLL_TIME);
    }

    public void setSpeedModifier(float newSpeed) {
        this.speedModifier = newSpeed;
    }

    @Override
    public boolean canStart() {

        if (!active()) return false;

        if (this.mob.isVehicle()) {
            return false;
        } else {

            if (this.timeTillStroll > 0) {
                this.timeTillStroll--;
                return false;
            }

            this.timeTillStroll = this.strollTime.map(DEUtil.PSYCHE_RANDOM);

            Vec3 vec3 = this.getRandomPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;

                return true;
            }
        }

    }

    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Override
    public boolean shouldKeepGoing() {
        return !this.mob.getNavigation().isDone() && !this.mob.isVehicle() && active();
    }

    @Override
    public void onEnd() {
        super.onEnd();
        this.mob.getNavigation().stop();
    }

    @Nullable
    protected Vec3 getRandomPosition() {
        return DefaultRandomPos.getPos(this.mob, 10, 7);
    }
}

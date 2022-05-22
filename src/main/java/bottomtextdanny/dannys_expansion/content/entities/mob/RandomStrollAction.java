package bottomtextdanny.dannys_expansion.content.entities.mob;

import bottomtextdanny.braincell.base.value_mapper.RandomIntegerMapper;
import bottomtextdanny.braincell.mod.entity.psyche.Action;
import bottomtextdanny.braincell.mod.entity.psyche.pos_finder.MobPosProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RandomStrollAction extends Action<PathfinderMob> {
    public static final RandomIntegerMapper DEFAULT_STROLL_TIME = RandomIntegerMapper.of(120, 150);
    protected RandomIntegerMapper strollTime;
    private final MobPosProcessor<?> posProcessor;
    protected int timeTillStroll;
    protected float speedModifier = 1.0F;
    protected Path path;

    public RandomStrollAction(PathfinderMob mob, MobPosProcessor<?> posProcessor, RandomIntegerMapper strollTime) {
        super(mob);
        this.strollTime = strollTime;
        this.posProcessor = posProcessor;
        this.timeTillStroll = strollTime.map(UNSAFE_RANDOM);
    }

    public void setSpeedModifier(float newSpeed) {
        this.speedModifier = newSpeed;
    }

    public boolean canStart() {
        if (!this.active()) {
            return false;
        } else if (this.mob.isVehicle()) {
            return false;
        } else if (this.timeTillStroll > 0) {
            --this.timeTillStroll;
            return false;
        } else {
            this.timeTillStroll = this.strollTime.map(UNSAFE_RANDOM);
            Vec3 randomPos = this.getRandomPosition();
            if (randomPos == null) {
                return false;
            } else {
                this.path = this.mob.getNavigation().createPath(randomPos.x, randomPos.y, randomPos.z, 0);
                return this.path != null;
            }
        }
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, (double)this.speedModifier);
    }

    public boolean shouldKeepGoing() {
        return !path.isDone() && !this.mob.getNavigation().isDone() && !this.mob.getNavigation().isStuck() && !this.mob.isVehicle() && this.active();
    }

    public void onEnd() {
        super.onEnd();
        this.mob.getNavigation().stop();
    }

    @Nullable
    protected Vec3 getRandomPosition() {
        BlockPos pos = this.posProcessor.compute(this.mob.blockPosition(), this.mob, UNSAFE_RANDOM, null);
        return pos != null ? Vec3.atCenterOf(pos) : null;
    }
}

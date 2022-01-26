package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AlfFlyFromLookAction<E extends PathfinderMob> extends Action<E> {
    private final float deltaSearchPoint;
    private final float moveSpeed;

    public AlfFlyFromLookAction(E mob, float delta, float moveSpeed) {
        super(mob);
        this.moveSpeed = moveSpeed;
        this.deltaSearchPoint = delta;
    }

    @Override
    public boolean canStart() {
        return active() && this.mob.getNavigation().isDone();
    }

    @Override
    protected void start() {
        Vec3 vec3 = this.findPos();
        if (vec3 != null) {
            this.mob.getNavigation().moveTo(this.mob.getNavigation().createPath(new BlockPos(vec3), 1), this.moveSpeed);
        }
    }


    @Override
    protected void update() {
        Path path = this.mob.getNavigation().getPath();
        if (path != null) {
            if (path.getNextNodeIndex() < path.getNodeCount()) {
                Vec3 vec = Vec3.atCenterOf(path.getNextNodePos());
            }
        }
    }

    @Override
    public boolean shouldKeepGoing() {
        return active() && this.mob.getNavigation().isInProgress();
    }

    @Override
    public void onEnd() {
        super.onEnd();
        this.mob.getNavigation().stop();
    }

    @Nullable
    private Vec3 findPos() {
        Vec3 vec3 = this.mob.getViewVector(1.0F).scale(this.deltaSearchPoint).scale(-1.0);
        int i = 8;
        Vec3 vec31 = HoverRandomPos.getPos(this.mob, 7, 10, vec3.x, vec3.z, (float)Math.PI / 2F, 5, 4);
        return vec31 != null ? vec31 : AirAndWaterRandomPos.getPos(this.mob, 8, 0, -4, vec3.x, vec3.z, (float)Math.PI / 2F);
    }
}

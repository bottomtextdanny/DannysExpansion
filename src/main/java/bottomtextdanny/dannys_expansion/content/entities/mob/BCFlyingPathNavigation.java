package bottomtextdanny.dannys_expansion.content.entities.mob;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class BCFlyingPathNavigation extends PathNavigation {

    public BCFlyingPathNavigation(Mob p_26424_, Level p_26425_) {
        super(p_26424_, p_26425_);
    }

    protected PathFinder createPathFinder(int p_26428_) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, p_26428_);
    }

    protected boolean canUpdatePath() {
        return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
    }

    protected Vec3 getTempMobPos() {
        return this.mob.position();
    }

    public Path createPath(Entity p_26430_, int p_26431_) {
        return this.createPath(p_26430_.blockPosition(), p_26431_);
    }

    public void tick() {
        ++this.tick;
        if (this.hasDelayedRecomputation) {
            this.recomputePath();
        }

        if (!this.isDone()) {
            if (this.canUpdatePath()) {
                this.followThePath();
            } else if (this.path != null && !this.path.isDone()) {
                Vec3 vec3 = this.path.getNextEntityPos(this.mob);
                if (this.mob.getBlockX() == Mth.floor(vec3.x) && this.mob.getBlockY() == Mth.floor(vec3.y) && this.mob.getBlockZ() == Mth.floor(vec3.z)) {
                    this.path.advance();
                }
            }

            DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
            if (!this.isDone()) {
                Vec3 vec31 = this.path.getNextEntityPos(this.mob);
                this.mob.getMoveControl().setWantedPosition(vec31.x, vec31.y, vec31.z, this.speedModifier);
            }
        }
    }

    protected void followThePath() {
        Vec3 mobPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = 0.5F;
        Vec3i nodePos = this.path.getNextNodePos();
        double halfWidthBias = this.mob.getBbWidth() / 2D + 0.5;
        double halfHeight = this.mob.getBbHeight() / 2D;
        double halfHeightBias = halfHeight + 0.5;

        double xReach = Math.abs(mobPos.x() - (double)nodePos.getX() + 0.5F - halfWidthBias);
        double yReach = Math.abs(mobPos.y() - (double)nodePos.getY() + 0.5F - halfHeightBias) - halfHeight;
        double zReach = Math.abs(mobPos.z() - (double)nodePos.getZ() + 0.5F - halfWidthBias);

        boolean flag = xReach <= (double)this.maxDistanceToWaypoint && zReach <= (double)this.maxDistanceToWaypoint && yReach <= 0.5;

        if (flag || this.mob.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(mobPos)) {
            this.path.advance();
        }

        this.doStuckDetection(mobPos);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 p_26560_) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vec3 = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!p_26560_.closerThan(vec3, 2.0D)) {
                return false;
            } else if (this.canMoveDirectly(p_26560_, this.path.getNextEntityPos(this.mob))) {
                return true;
            } else {
                Vec3 vec31 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vec32 = vec31.subtract(vec3);
                Vec3 vec33 = p_26560_.subtract(vec3);
                return vec32.dot(vec33) > 0.0D;
            }
        }
    }

    public void setCanOpenDoors(boolean p_26441_) {
        this.nodeEvaluator.setCanOpenDoors(p_26441_);
    }

    public boolean canPassDoors() {
        return this.nodeEvaluator.canPassDoors();
    }

    public void setCanPassDoors(boolean p_26444_) {
        this.nodeEvaluator.setCanPassDoors(p_26444_);
    }

    public boolean canOpenDoors() {
        return this.nodeEvaluator.canPassDoors();
    }

    public boolean isStableDestination(BlockPos p_26439_) {
        return this.level.getBlockState(p_26439_).entityCanStandOn(this.level, p_26439_, this.mob);
    }
}

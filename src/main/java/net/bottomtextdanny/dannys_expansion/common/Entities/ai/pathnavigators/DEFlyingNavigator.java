package net.bottomtextdanny.dannys_expansion.common.Entities.ai.pathnavigators;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class DEFlyingNavigator extends PathNavigation {
    Timer timeOut;

    public DEFlyingNavigator(Mob entityIn, Level worldIn) {
        super(entityIn, worldIn);
        this.timeOut = new Timer(60);
    }

    protected PathFinder createPathFinder(int p_179679_1_) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, p_179679_1_);
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canUpdatePath() {
        return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
    }

    protected Vec3 getTempMobPos() {
        return this.mob.position();
    }

    /**
     * Returns the path to the given EntityLiving. Args : entity
     */
    public Path createPath(Entity entityIn, int p_75494_2_) {
        return this.createPath(entityIn.blockPosition(), p_75494_2_);
    }

    @Override
    public void stop() {
        super.stop();
        this.timeOut.reset();
    }

    public void tick() {
        ++this.tick;
        if (this.hasDelayedRecomputation) {
            this.recomputePath();
        }
        if (this.mob.horizontalCollision) {
            this.timeOut.tryUp();

            if (this.timeOut.hasEnded()) {
                stop();

            }
        } else {
            this.timeOut.reset();
        }
        if (!this.isDone()) {


            Node currentPoint = this.path.getNode(this.path.getNextNodeIndex());
            Vec3 vector3d = new Vec3(currentPoint.x, currentPoint.y, currentPoint.z);
            Vec3 targetVec = new Vec3(Mth.floor(vector3d.x), Mth.floor(vector3d.y), Mth.floor(vector3d.z));

            if (this.canUpdatePath()) {
                this.followThePath();

            } else if (this.path != null && !this.path.isDone()) {
                if (this.path.getNextNodeIndex() < this.path.getNodeCount() - 1 && ((ModuledMob) this.mob).canBlockBeSeen(this.getPath().getNode(this.path.getNodeCount() - 1).asBlockPos())) {
                    this.path.setNextNodeIndex(this.path.getNodeCount() - 1);
                } else {
                    float lFactor = this.path.getNextNodeIndex() < this.path.getNodeCount() - 1 ? 1 : 0;
                    if (this.mob.getBoundingBox().intersects(targetVec.add(0.5 - lFactor, 0.5 - lFactor, 0.5 - lFactor), targetVec.add(0.5 + lFactor, 0.5 + lFactor, 0.5 + lFactor))) {
                        this.path.advance();
                    }
                }
            }

           // DebugPacketSender.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
            if (!this.isDone()) {
                this.mob.getMoveControl().setWantedPosition(targetVec.x + 0.5, targetVec.y + 0.5, targetVec.z + 0.5, this.speedModifier);
            }
        }
    }

    protected void followThePath() {
        Vec3 vector3d = this.getTempMobPos();

        if (this.path.getNextNodeIndex() < this.path.getNodeCount() - 1 && ((ModuledMob) this.mob).canBlockBeSeen(this.getPath().getNode(this.path.getNodeCount() - 1).asBlockPos())) {

            this.path.setNextNodeIndex(this.path.getNodeCount() - 1);
        } else {
            this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
            Vec3i vector3i = this.path.getNextNodePos();
            boolean flag = this.mob.getBoundingBox().intersects(Vec3.atCenterOf(vector3i), Vec3.atCenterOf(vector3i));
            if (flag || this.mob.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vector3d)) {
                this.path.advance();
            }
        }


        this.doStuckDetection(vector3d);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 currentPosition) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vector3d = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!currentPosition.closerThan(vector3d, 2.0D)) {
                return false;
            } else {
                Vec3 vector3d1 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vector3d2 = vector3d1.subtract(vector3d);
                Vec3 vector3d3 = currentPosition.subtract(vector3d);
                return vector3d2.dot(vector3d3) > 0.0D;
            }
        }
    }

    /**
     * Checks if the specified entity can safely walk to the specified image.
     */
    protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
        int i = Mth.floor(posVec31.x);
        int j = Mth.floor(posVec31.y);
        int k = Mth.floor(posVec31.z);
        double d0 = posVec32.x - posVec31.x;
        double d1 = posVec32.y - posVec31.y;
        double d2 = posVec32.z - posVec31.z;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0E-8D) {
            return false;
        } else {
            double d4 = 1.0D / Math.sqrt(d3);
            d0 = d0 * d4;
            d1 = d1 * d4;
            d2 = d2 * d4;
            double d5 = 1.0D / Math.abs(d0);
            double d6 = 1.0D / Math.abs(d1);
            double d7 = 1.0D / Math.abs(d2);
            double d8 = (double)i - posVec31.x;
            double d9 = (double)j - posVec31.y;
            double d10 = (double)k - posVec31.z;
            if (d0 >= 0.0D) {
                ++d8;
            }

            if (d1 >= 0.0D) {
                ++d9;
            }

            if (d2 >= 0.0D) {
                ++d10;
            }

            d8 = d8 / d0;
            d9 = d9 / d1;
            d10 = d10 / d2;
            int l = d0 < 0.0D ? -1 : 1;
            int i1 = d1 < 0.0D ? -1 : 1;
            int j1 = d2 < 0.0D ? -1 : 1;
            int k1 = Mth.floor(posVec32.x);
            int l1 = Mth.floor(posVec32.y);
            int i2 = Mth.floor(posVec32.z);
            int j2 = k1 - i;
            int k2 = l1 - j;
            int l2 = i2 - k;

            while(j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
                if (d8 < d10 && d8 <= d9) {
                    d8 += d5;
                    i += l;
                    j2 = k1 - i;
                } else if (d9 < d8 && d9 <= d10) {
                    d9 += d6;
                    j += i1;
                    k2 = l1 - j;
                } else {
                    d10 += d7;
                    k += j1;
                    l2 = i2 - k;
                }
            }

            return true;
        }
    }

    public void setCanOpenDoors(boolean canOpenDoorsIn) {
        this.nodeEvaluator.setCanOpenDoors(canOpenDoorsIn);
    }

    public void setCanEnterDoors(boolean canEnterDoorsIn) {
        this.nodeEvaluator.setCanPassDoors(canEnterDoorsIn);
    }

    public boolean isStableDestination(BlockPos pos) {
        return PieceUtil.noCollision(this.level, pos);
    }
}

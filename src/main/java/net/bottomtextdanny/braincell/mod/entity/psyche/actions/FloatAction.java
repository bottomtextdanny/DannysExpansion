package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class FloatAction extends ConstantThoughtAction<PathfinderMob> {
    public static final float DEFAULT_FLOAT_HEIGHT = 0.5F;
    public static final int DETECT_GROUND_REFRESH_RATE = 14;
    private final float floatHeight;
    private boolean cachedDetectedGroundState;

    public FloatAction(PathfinderMob mob, float floatHeight) {
        super(mob, null);
        this.floatHeight = floatHeight;
        mob.getNavigation().setCanFloat(true);
        this.cachedDetectedGroundState = true;
    }

    public FloatAction(PathfinderMob mob) {
        this(mob, DEFAULT_FLOAT_HEIGHT);
    }

    @Override
    protected void update() {
        double fluidHeight = this.mob.getFluidHeight(FluidTags.WATER);

        if (fluidHeight > 0.15D && this.ticksPassed % DETECT_GROUND_REFRESH_RATE == 0) {
            float reducedHeight = this.mob.getEyeHeight() - 0.15F;
            this.cachedDetectedGroundState = detectGround(reducedHeight);
        }

        if (this.mob.isInWater() || this.mob.isInLava()) {
            if (this.mob.horizontalCollision || !this.cachedDetectedGroundState && fluidHeight > this.floatHeight) {
                if (this.mob.getRandom().nextFloat() < 0.8F) {
                    this.mob.getJumpControl().jump();
                }
            }
        }
    }

    public boolean detectGround(float height) {
        if (!this.mob.isInLava() && this.mob.getFluidHeight(FluidTags.WATER) < height) {
            float gap = (float) (height - this.mob.getFluidHeight(FluidTags.WATER));
            Vec3 gapVec = this.mob.position().subtract(0.0D, gap, 0.0D);
            BlockPos gapPos = new BlockPos(gapVec);
            double delta = this.mob.level.getBlockState(gapPos).getCollisionShape(this.mob.level, gapPos).max(Direction.Axis.Y);

            if (delta <= 0.0D) {
                BlockPos gapPosBelow = gapPos.below();
                double lowerDelta = this.mob.level.getBlockState(gapPosBelow).getCollisionShape(this.mob.level, gapPosBelow).max(Direction.Axis.Y);
                delta = lowerDelta - 1.0F;
            }

            return delta > 0.0F && delta + gapPos.getY() - 1.0F < gapVec.y;
        }
        return false;
    }
}

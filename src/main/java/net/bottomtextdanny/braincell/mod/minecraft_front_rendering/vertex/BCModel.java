package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;

public interface BCModel {
    float RAD = BCMath.FRAD;

    default float getPartialTick() {
        return DEUtil.PARTIAL_TICK;
    }

    //BB function
    default void setRotationAngle(BCVoxel modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    default void setRotationAngleDegrees(BCVoxel modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x * RAD;
        modelRenderer.yRot = y * RAD;
        modelRenderer.zRot = z * RAD;
    }

    int getTexWidth();

    int getTexHeight();
}

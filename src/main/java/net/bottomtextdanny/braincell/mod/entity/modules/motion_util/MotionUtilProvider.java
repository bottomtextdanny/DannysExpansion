package net.bottomtextdanny.braincell.mod.entity.modules.motion_util;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;

public interface MotionUtilProvider extends ModuleProvider {

    float movementReduction();

    void setMovementReduction(float newFactor);
}

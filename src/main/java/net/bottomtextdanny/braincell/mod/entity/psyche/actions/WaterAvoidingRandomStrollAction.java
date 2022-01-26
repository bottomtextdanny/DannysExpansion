package net.bottomtextdanny.braincell.mod.entity.psyche.actions;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.RandomIntegerMapper;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WaterAvoidingRandomStrollAction extends RandomStrollAction {

    public WaterAvoidingRandomStrollAction(PathfinderMob mob, RandomIntegerMapper strollTime) {
        super(mob, strollTime);
    }

    public WaterAvoidingRandomStrollAction(PathfinderMob mob) {
        super(mob);
    }

    @Nullable
    @Override
    protected Vec3 getRandomPosition() {

        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? super.getRandomPosition() : vec3;
        } else {
            return LandRandomPos.getPos(this.mob, 10, 7);
        }
    }
}

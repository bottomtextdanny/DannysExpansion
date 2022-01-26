package net.bottomtextdanny.braincell.mod.opengl_front.point_lighting;

import net.minecraft.world.phys.Vec3;

public interface IPointLight {

    Vec3 color();

    Vec3 position();

    float radius();

    float brightness();

    float lightupFactor();
}

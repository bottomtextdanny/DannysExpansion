package net.bottomtextdanny.braincell.mod.opengl_front.point_lighting;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SimplePointLight implements IPointLight {
    private final Vec3 position;
    private final Vec3 color;
    private final float radius;
    private final float brightness;
    private final float lightupFactor;

    public SimplePointLight(final Vec3 color, final float radius, final float brightness, final float lightupFactor) {
        this.radius = radius;
        this.position = Vec3.ZERO;
        this.color = color;
        this.brightness = brightness;
        this.lightupFactor = lightupFactor;
    }

    public SimplePointLight(final Vec3 position, final Vec3 color, final float radius, final float brightness, final float lightupFactor) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        this.brightness = brightness;
        this.lightupFactor = lightupFactor;
    }

    @Override
    public Vec3 color() {
        return this.color;
    }

    @Override
    public Vec3 position() {
        return this.position;
    }

    @Override
    public float radius() {
        return Mth.clamp(this.radius, 0.0F, 30.0F);
    }

    @Override
    public float brightness() {
        return this.brightness;
    }

    @Override
    public float lightupFactor() {
        return this.lightupFactor;
    }
}

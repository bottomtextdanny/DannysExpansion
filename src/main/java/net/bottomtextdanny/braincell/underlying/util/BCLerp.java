package net.bottomtextdanny.braincell.underlying.util;

import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.underlying.util.vectors.Vector2f;
import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public final class BCLerp {

    public static double get(double approachValue, double start, double end) {
        return (end - start) * approachValue + start;
    }

    public static float get(float approachValue, float start, float end) {
        return (end - start) * approachValue + start;
    }

    public static Vec2 get(float approachValue, Vec2 start, Vec2 end) {
        return new Vec2(
                get(approachValue, start.x, end.x),
                get(approachValue, start.y, end.y));
    }

    public static Vec3 get(double approachValue, Position start, Position end) {
        return new Vec3(
                get(approachValue, start.x(), end.x()),
                get(approachValue, start.y(), end.y()),
                get(approachValue, start.z(), end.z()));
    }

    public static Vector3f get(float approachValue, Vector3f start, Vector3f end) {
        return new Vector3f(
                get(approachValue, start.x(), end.x()),
                get(approachValue, start.y(), end.y()),
                get(approachValue, start.z(), end.z()));
    }
}

package net.bottomtextdanny.braincell.underlying.util;

import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;

public final class BCArith {

    public static float dot(Vec2 vec0, Vec2 vec1) {
        return vec0.x * vec1.x + vec0.y * vec1.y;
    }

    public static double dot(Position vec0, Position vec1) {
        return vec0.x() * vec1.x() + vec0.y() * vec1.y() + vec0.z() * vec1.z();
    }

    public static float fDot(Position vec0, Position vec1) {
        return (float) (vec0.x() * vec1.x() + vec0.y() * vec1.y() + vec0.z() * vec1.z());
    }

    public static double dot(Vec3i vec0, Vec3i vec1) {
        return (double)vec0.getX() * vec1.getX() + (double)vec0.getY() * vec1.getY() + (double)vec0.getZ() * vec1.getZ();
    }

    public static float fDot(Vec3i vec0, Vec3i vec1) {
        return (float) vec0.getX() * vec1.getX() + (float)vec0.getY() * vec1.getY() + (float)vec0.getZ() * vec1.getZ();
    }
}

package net.bottomtextdanny.braincell.mod.world.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

import java.util.function.Predicate;

public final class ReachHelper {

    public static Predicate<LivingEntity> isEntityCloseToAnother(PathfinderMob opinionated, LivingEntity target, float distance) {
        return livingEntity -> reachSqr(opinionated, target) < distance;
    }

    public static float horizontalReachSqr(PathfinderMob opinionated, Entity target) {
        float xDist = (float)(opinionated.getX() - target.getX());
        float zDist = (float)(opinionated.getZ() - target.getZ());
        float distance = xDist * xDist + zDist * zDist;
        return (float) Math.max(distance - target.getBbWidth() / 2.0, 0.0);
    }

    public static float horizontalReach(PathfinderMob opinionated, Entity target) {
        float xDist = (float)(opinionated.getX() - target.getX());
        float zDist = (float)(opinionated.getZ() - target.getZ());
        float distance = (float) Math.sqrt(xDist * xDist + zDist * zDist);
        return (float) Math.max(distance - target.getBbWidth() / 2.0, 0.0);
    }

    public static float reachSqr(PathfinderMob opinionated, Entity target) {
        return (float) Math.max(opinionated.distanceTo(target) - target.getBbWidth() / 2.0, 0.0);
    }
}

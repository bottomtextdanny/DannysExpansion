package net.bottomtextdanny.braincell.mod.world.helpers;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.entity.psyche.conditions.target.EntityTarget;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public final class FilterEntityHelper {

    @Nullable
    public static <T extends LivingEntity> T getNearestEntityToEntity(List<? extends T> listofEntities, Entity entity) {
        double d0 = -1.0D;
        T t = null;

        for(T t1 : listofEntities) {
            double d1 = t1.distanceToSqr(entity);
            if (d0 == -1.0D || d1 < d0) {
                d0 = d1;
                t = t1;
            }
        }

        return t;
    }

    @Nullable
    public static <T extends LivingEntity> T getNearestEntityToEntity(List<? extends T> listofEntities, EntityTarget<T> target, PathfinderMob entity) {
        double d0 = -1.0D;
        T t = null;

        for(T t1 : listofEntities) {
            if (target.test(entity, t1)) {
                double d1 = t1.distanceToSqr(entity);
                if (d0 == -1.0D || d1 < d0) {
                    d0 = d1;
                    t = t1;
                }
            }
        }

        return t;
    }

    @Nullable
    public static Player getNearestPlayer(EntityGetter level, PathfinderMob mob, double maxSearchRadius, EntityTarget<LivingEntity> targetPredicate, @Nullable Predicate<LivingEntity> customPredicate) {
        double d0 = -1.0D;
        Player player = null;

        for(Player player1 : level.players()) {
            if (targetPredicate.test(mob, player1) && (customPredicate == null || customPredicate.test(player1))) {
                double d1 = player1.distanceToSqr(mob);
                if ((maxSearchRadius < 0.0D || d1 < maxSearchRadius * maxSearchRadius) && (d0 == -1.0D || d1 < d0)) {
                    d0 = d1;
                    player = player1;
                }
            }
        }
        return player;
    }

    @Nullable
    public static LivingEntity getNearestLiving(ServerLevel level, PathfinderMob opinionated, AABB radiusBox, Predicate<LivingEntity> collector, EntityTarget<LivingEntity> target) {
        double d0 = -1.0D;
        LivingEntity t = null;

        List<LivingEntity> entitiesInRange = getEntities(level, opinionated, radiusBox, LivingEntity.class, le -> collector.test(le) && target.test(opinionated, le));

        for(LivingEntity t1 : entitiesInRange) {
            if (target.test(opinionated, t1)) {
                double d1 = t1.distanceToSqr(opinionated);
                if (d0 == -1.0D || d1 < d0) {
                    d0 = d1;
                    t = t1;
                }
            }
        }

        return t;
    }

    public static <T> List<T>  getEntities(ServerLevel level, @Nullable Entity mob, AABB radiusBox, Class<T> cast, Predicate<T> collectorPredicate) {
        List<T> list = Lists.newArrayList();

        level.getEntities().get(radiusBox, judgedEntity -> {
            if (judgedEntity != mob && cast.isAssignableFrom(judgedEntity.getClass()) && collectorPredicate.test((T)judgedEntity)) {
                list.add((T)judgedEntity);
            }

            if (judgedEntity.isMultipartEntity()) {
                for(net.minecraftforge.entity.PartEntity<?> enderdragonpart : judgedEntity.getParts()) {
                    if (judgedEntity != mob && cast.isAssignableFrom(enderdragonpart.getClass()) && collectorPredicate.test((T)enderdragonpart)) {
                        list.add((T)enderdragonpart);
                    }
                }
            }

        });
        return list;
    }
}

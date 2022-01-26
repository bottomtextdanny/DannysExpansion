package net.bottomtextdanny.dannys_expansion.core.Util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public final class DannyRayTraceHelper {

    @Nullable
    public static EntityHitResult rayTraceEntities(Level worldIn, Entity projectile, AABB boundingBox, Predicate<Entity> filter) {
        Entity entity = null;

        for(Entity entity1 : worldIn.getEntities(projectile, boundingBox, filter)) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(1);

            if (axisalignedbb.intersects(boundingBox)) {
                entity = entity1;
            }
        }

        return entity == null ? null : new EntityHitResult(entity);
    }

    @Nullable
    public static EntityHitResult rayTraceEntitiesExpanded(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, AABB expansion, Predicate<Entity> filter) {
        double widthBy2 =  expansion.getXsize() / 2.0;
        double heightBy2 =  expansion.getYsize() / 2.0;
        AABB expandedBoundingBox = boundingBox.inflate(widthBy2, heightBy2, widthBy2);
        double d0 = Double.MAX_VALUE;
        Entity entity = null;
        Vec3 hitPos = Vec3.ZERO;


        for(Entity entity1 : worldIn.getEntities(projectile, expandedBoundingBox, filter)) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(widthBy2, heightBy2, widthBy2);
            Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
            if (optional.isPresent()) {
                double d1 = startVec.distanceToSqr(optional.get());
                if (d1 < d0) {
                    entity = entity1;
                    hitPos = optional.get();
                    d0 = d1;
                }
            } else if (DEMath.intersectsVec(startVec, axisalignedbb)) {
                entity = entity1;
            }
        }

        return entity == null ? null : new EntityHitResult(entity, hitPos);
    }

    @Nullable
    public static EntityHitResult rayTraceEntitiesOrb(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        return rayTraceEntitiesExpanded(worldIn, projectile, startVec, endVec, boundingBox, projectile.getBoundingBox(), filter);
    }

    @Nullable
    public static EntityHitResult rayTraceEntitiesBullet(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;

        Vec3 hitPos = Vec3.ZERO;
        for(Entity entity1 : worldIn.getEntities(projectile, boundingBox, filter)) {
            float distProg = -2.0F;
            float closestDistance = Float.MAX_VALUE;
            Vec3 easedPosition = Vec3.ZERO;
            float w = entity1.getBbWidth() / 2;
            float h = entity1.getBbHeight();

            for (int i = 0; i < 4; i++) {

                Vec3 easedVec = DEMath.getVecLerp(distProg, new Vec3(entity1.xOld, entity1.yOld, entity1.zOld), entity1.position());
                float distanceToEase = DEMath.getDistance(projectile.position(), easedVec);

                if (distanceToEase < closestDistance) {
                    closestDistance = distanceToEase;
                    easedPosition = easedVec;
                }


                AABB axisalignedbb = new AABB(easedVec.add(-w, 0, -w), easedPosition.add(w, h, w));
                Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);

                if (optional.isPresent()) {
                    double d1 = startVec.distanceToSqr(optional.get());
                    if (d1 < d0) {
                        entity = entity1;
                        hitPos = optional.get();
                        d0 = d1;
                    }
                }
                distProg += 1F;
            }

        }


        return entity == null ? null : new EntityHitResult(entity, hitPos);
    }



    public static HitResult bulletRaytrace(Entity entity, Predicate<Entity> predicate, Vec3 vec, ClipContext.Fluid fluidMode) {
        Vec3 vector3d2 = entity.position();
        Vec3 vector3d3 = vector3d2.add(vec);
        HitResult raytraceresult = entity.level.clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, fluidMode, entity));
        if (raytraceresult.getType() != HitResult.Type.MISS) {
            vector3d3 = raytraceresult.getLocation();
        }

        EntityHitResult entityraytraceresult = rayTraceEntitiesBullet(entity.level, entity, vector3d2, vector3d3, entity.getBoundingBox().expandTowards(vec), predicate);
        if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
        }

        return  raytraceresult;
    }

    public static HitResult expandedRaytrace(Entity entity, Predicate<Entity> predicate, Vec3 vec, AABB expansion, ClipContext.Fluid fluidMode) {
        Vec3 vector3d2 = entity.position();
        Vec3 vector3d3 = vector3d2.add(vec);
        HitResult raytraceresult = entity.level.clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, fluidMode, entity));
        if (raytraceresult.getType() != HitResult.Type.MISS) {
            vector3d3 = raytraceresult.getLocation();
        }

        EntityHitResult entityraytraceresult = rayTraceEntitiesExpanded(entity.level, entity, vector3d2, vector3d3, entity.getBoundingBox().expandTowards(vec), expansion, predicate);
        if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
        }

        return  raytraceresult;
    }

    public static HitResult orbRaytrace(Entity entity, Predicate<Entity> predicate, Vec3 vec, ClipContext.Fluid fluidMode) {
        Vec3 vector3d2 = entity.position();
        Vec3 vector3d3 = vector3d2.add(vec);
        HitResult raytraceresult = entity.level.clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, fluidMode, entity));
        if (raytraceresult.getType() != HitResult.Type.MISS) {
            vector3d3 = raytraceresult.getLocation();
        }

        EntityHitResult entityraytraceresult = rayTraceEntitiesOrb(entity.level, entity, vector3d2, vector3d3, entity.getBoundingBox().expandTowards(vec), predicate);
        if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
        }

        return  raytraceresult;
    }

    public static BlockHitResult rayTraceBlocks(Entity p_234618_0_) {
        Vec3 vector3d = p_234618_0_.getLookAngle();
        Level world = p_234618_0_.level;
        Vec3 vector3d1 = p_234618_0_.position();
        Vec3 vector3d2 = vector3d1.add(vector3d);

        return world.clip(new ClipContext(vector3d1, vector3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, p_234618_0_));
    }
}

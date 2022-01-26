package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public abstract class OldFloatingEntity extends ModuledMob {
    public float speed = 1.0F;
    int bobbingTimer;
    public int bobbingTicks = 40;
    public float bobbingAmount = 0.1F;
    public boolean collidedX;
    public boolean collidedZ;
    public int bounceDelay = 3;

    public OldFloatingEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().x, 0F, getDeltaMovement().z);
        setNoGravity(true);
        this.fallDistance = 0;

        bounceInArc();
        if (this.speed < 1 ) {
            setSpeed(this.speed *= 1.05);
        }
        if (++this.bounceDelay < 3);

        if (++this.bobbingTimer >= this.bobbingTicks) {
            this.bobbingTimer = 0;
        }

        float bob = DEMath.motionHelper(this.bobbingAmount, 0, 0, this.bobbingTicks / 2, this.bobbingTimer);
        bob += DEMath.motionHelper(-this.bobbingAmount, 0, this.bobbingTicks / 2, this.bobbingTicks, this.bobbingTimer);

        addMotion(0.0F, bob, 0.0F);
    }



    public void bounceInArc() {
        if (this.bounceDelay == 0) {
            if (this.collidedX) {
                double xMotion = Mth.clamp((this.xOld - getX()) * 2, -0.2F, 0.2);
                double yMotion = -Mth.clamp(this.yOld - getY(), -0.2F, 0.2);
                double zMotion = -Mth.clamp(this.zOld - getZ(), -0.2F, 0.2);
                this.setDeltaMovement(getDeltaMovement().add(xMotion, yMotion, zMotion));
                setSpeed(this.speed *= 0.75);
                this.bounceDelay = 3;
            }

            if (this.collidedZ) {
                double xMotion = -Mth.clamp(this.xOld - getX(), -0.2F, 0.2);
                double yMotion = -Mth.clamp(this.yOld - getY(), -0.2F, 0.2);
                double zMotion = Mth.clamp((this.zOld - getZ()) * 2, -0.2F, 0.2);
                this.setDeltaMovement(getDeltaMovement().add(xMotion, yMotion, zMotion));
                setSpeed(this.speed *= 0.75);
                this.bounceDelay = 3;
            }

            if (this.verticalCollision) {
                double xMotion = -Mth.clamp((this.xOld - getX()) * 0.2, -0.2F, 0.2);
                double yMotion = Mth.clamp((this.yOld - getY()) * 2, -0.2F, 0.2);
                double zMotion = -Mth.clamp((this.zOld - getZ()) * 0.2, -0.2F, 0.2);
                this.setDeltaMovement(getDeltaMovement().add(xMotion, yMotion, zMotion));
                setSpeed(this.speed *= 0.75);
                this.bounceDelay = 3;
            }
        }

    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {
        super.move(typeIn, pos);
        pos = this.maybeBackOffFromEdge(pos, typeIn);
        Vec3 vector3d = this.getAllowedMovement(pos);
        this.collidedX = !Mth.equal(pos.x, vector3d.x);
        this.collidedZ = !Mth.equal(pos.z, vector3d.z);
    }

    public void setSpeed(float speed) {
        this.speed = Mth.clamp(speed, 0.2F, 1.0F);
    }

    public void addMotion(double x, double y, double z) {
        setDeltaMovement(getDeltaMovement().add(x * this.speed, y * this.speed, z * this.speed));
    }

    //reflection
    public Vec3 getAllowedMovement(Vec3 vec) {
	    AABB aabb = this.getBoundingBox();
	    List<VoxelShape> list = this.level.getEntityCollisions(this, aabb.expandTowards(vec));
	    Vec3 vec3 = vec.lengthSqr() == 0.0D ? vec : collideBoundingBox(this, vec, aabb, this.level, list);
	    boolean flag = vec.x != vec3.x;
	    boolean flag1 = vec.y != vec3.y;
	    boolean flag2 = vec.z != vec3.z;
	    boolean flag3 = this.onGround || flag1 && vec.y < 0.0D;
	    if (this.maxUpStep > 0.0F && flag3 && (flag || flag2)) {
		    Vec3 vec31 = collideBoundingBox(this, new Vec3(vec.x, this.maxUpStep, vec.z), aabb, this.level, list);
		    Vec3 vec32 = collideBoundingBox(this, new Vec3(0.0D, this.maxUpStep, 0.0D), aabb.expandTowards(vec.x, 0.0D, vec.z), this.level, list);
		    if (vec32.y < (double)this.maxUpStep) {
			    Vec3 vec33 = collideBoundingBox(this, new Vec3(vec.x, 0.0D, vec.z), aabb.move(vec32), this.level, list).add(vec32);
			    if (vec33.horizontalDistanceSqr() > vec31.horizontalDistanceSqr()) {
				    vec31 = vec33;
			    }
		    }
		
		    if (vec31.horizontalDistanceSqr() > vec3.horizontalDistanceSqr()) {
			    return vec31.add(collideBoundingBox(this, new Vec3(0.0D, -vec31.y + vec.y, 0.0D), aabb.move(vec31), this.level, list));
		    }
	    }
	
	    return vec3;
    }
}
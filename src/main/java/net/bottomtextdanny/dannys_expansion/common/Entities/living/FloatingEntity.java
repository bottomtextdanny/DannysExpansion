package net.bottomtextdanny.dannys_expansion.common.Entities.living;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public abstract class FloatingEntity extends ModuledMob {
    int bobbingTimer;
    public int bobbingTicks = 40;
    public float bobbingAmount = 0.1F;
    public boolean onCeiling;
    public boolean collidedX;
    public boolean collidedZ;
    public Vec3 simpleMotion = Vec3.ZERO;
    public Vec3 acceleratedMotion = Vec3.ZERO;

    public FloatingEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        this.simpleMotion = Vec3.ZERO;
        super.tick();
        setNoGravity(true);
        this.fallDistance = 0;

        if (!this.acceleratedMotion.equals(Vec3.ZERO)) {
            this.acceleratedMotion = this.acceleratedMotion.multiply(0.93, 0.93, 0.93);
        }
        float bob = DEMath.motionHelper(this.bobbingAmount, 0, 0, this.bobbingTicks / 2, this.bobbingTimer);
        bob += DEMath.motionHelper(-this.bobbingAmount, 0, this.bobbingTicks / 2, this.bobbingTicks, this.bobbingTimer);
        if (++this.bobbingTimer >= this.bobbingTicks) {
            this.bobbingTimer = 0;}

        addSimpleMotion(this.acceleratedMotion.x, bob + this.acceleratedMotion.y, this.acceleratedMotion.z);
        setDeltaMovement(this.simpleMotion);
    }

    public void addSimpleMotion(double x, double y, double z) {
        this.simpleMotion = this.simpleMotion.add(x, y, z);
    }

    public void addSimpleMotion(Vec3 vec) {
        this.simpleMotion = this.simpleMotion.add(vec);
    }

    public void addAccMotion(double x, double y, double z) {
        this.acceleratedMotion = this.acceleratedMotion.add(x, y, z);
    }

    public void addAccMotion(Vec3 vec) {
        this.acceleratedMotion = this.acceleratedMotion.add(vec);
    }

    //accMotion
    public void bounceInArc() {
        if (this.collidedX) {
            this.acceleratedMotion = new Vec3(-getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z);
        }

        if (this.collidedZ) {
            this.acceleratedMotion = new Vec3(getDeltaMovement().x, getDeltaMovement().y, -getDeltaMovement().z);

        }

        if (this.verticalCollision) {
            this.acceleratedMotion = new Vec3(getDeltaMovement().x, -getDeltaMovement().y, getDeltaMovement().z);
        }
    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {
        super.move(typeIn, pos);
        pos = this.maybeBackOffFromEdge(pos, typeIn);
        Vec3 vector3d = this.getAllowedMovement(pos);
        this.collidedX = !Mth.equal(pos.x, vector3d.x);
        this.collidedZ = !Mth.equal(pos.z, vector3d.z);
        this.onCeiling = this.verticalCollision && pos.y > 0.0D;
    }

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

    @Override
    public void knockback(double strength, double ratioX, double ratioZ) {
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, (float)strength, ratioX, ratioZ);
        if(event.isCanceled()) return;
        strength = event.getStrength();
        ratioX = event.getRatioX();
        ratioZ = event.getRatioZ();
        strength = (float)(strength * (1.0D - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
        if (!(strength <= 0.0F)) {
            this.hasImpulse = true;
            Vec3 vector3d = this.getDeltaMovement();
            Vec3 vector3d1 = new Vec3(ratioX, 0.0D, ratioZ).normalize().scale(strength);
            this.acceleratedMotion = this.acceleratedMotion.add(vector3d.x / 2.0D - vector3d1.x, this.onGround ? Math.min(0.4D, vector3d.y / 2.0D + strength) : vector3d.y, vector3d.z / 2.0D - vector3d1.z);
        }
    }

    @Override
    public void push(double x, double y, double z) {
        addAccMotion(x, y, z);
        this.hasImpulse = true;
    }
}


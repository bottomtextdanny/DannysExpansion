package net.bottomtextdanny.dannys_expansion.core.Util;

import net.minecraft.world.phys.Vec3;

public class ExternalMotion {
    float decelerationMultiplier;
    Timer accelerationTimer;
    Vec3 motion = Vec3.ZERO;
    Vec3 motionHolder = Vec3.ZERO;
    boolean active;

    public ExternalMotion(float decelerationMultiplier) {
        this.decelerationMultiplier = decelerationMultiplier;
        this.accelerationTimer = new Timer(0);
    }

    public ExternalMotion(int accelerationTicks, float decelerationMultiplier) {
        this.decelerationMultiplier = decelerationMultiplier;
        this.accelerationTimer = new Timer(accelerationTicks);
    }

    public void tick() {

        this.motionHolder = this.motionHolder.scale(this.decelerationMultiplier);
    }

    public Vec3 getAcceleratedMotion() {
        return this.motionHolder;
    }
    
	public void setMotion(Vec3 motion) {
	    this.motionHolder = motion;
        this.active = true;
    }

    public void addMotion(double x, double y, double z) {
        this.motionHolder = this.motionHolder.add(new Vec3(x, y, z));
        this.active = true;
    }

    public void addMotion(Vec3 motion) {
        this.motionHolder = this.motionHolder.add(motion);
        this.active = true;
    }

    public void setMotion(double x, double y, double z) {
	    this.motionHolder = new Vec3(x, y, z);
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }
}

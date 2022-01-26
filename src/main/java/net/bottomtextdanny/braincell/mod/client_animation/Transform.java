package net.bottomtextdanny.braincell.mod.client_animation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class Transform {
    float offsetX, offsetY, offsetZ;
    float rotationX, rotationY, rotationZ;
    float scaleX, scaleY, scaleZ;

    public float getOffsetX() {
        return this.offsetX;
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public float getOffsetZ() {
        return this.offsetZ;
    }

    public float getRotationX() {
        return this.rotationX;
    }

    public float getRotationY() {
        return this.rotationY;
    }

    public float getRotationZ() {
        return this.rotationZ;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public float getScaleZ() {
        return this.scaleZ;
    }

    public void setOffset(float x, float y, float z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
    }

    public void addOffset(float x, float y, float z) {
        this.offsetX += x;
        this.offsetY += y;
        this.offsetZ += z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotationX = (float)Math.toRadians(x);
        this.rotationY = (float)Math.toRadians(y);
        this.rotationZ = (float)Math.toRadians(z);
    }

    public void addRotation(float x, float y, float z) {
        this.rotationX += (float)Math.toRadians(x);
        this.rotationY += (float)Math.toRadians(y);
        this.rotationZ += (float)Math.toRadians(z);
    }

    public void setScale(float x, float y, float z) {
        this.scaleX = x;
        this.scaleY = y;
        this.scaleZ = z;
    }

    public void addScale(float x, float y, float z) {
        this.scaleX += x;
        this.scaleY += y;
        this.scaleZ += z;
    }

}

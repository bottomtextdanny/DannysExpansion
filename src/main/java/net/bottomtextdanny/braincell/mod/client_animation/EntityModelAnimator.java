package net.bottomtextdanny.braincell.mod.client_animation;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class EntityModelAnimator {
    public final BCEntityModel<?> model;
    private final Map<BCVoxel, Transform> modMapPing = Maps.newHashMap();
    private final Map<BCVoxel, Transform> modMapPong = Maps.newHashMap();
    private boolean useFirst;
    private float keyframe;
    private float prevKeyframe;
    private float timer;
    private float mult = 1;

    public EntityModelAnimator(BCEntityModel<?> model, float timer) {
        this.model = model;
        this.timer = timer;
    }

    public EntityModelAnimator multiplier(float mult) {
        this.mult = mult;
        return this;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    private void pingPongTables() {
        this.useFirst = !this.useFirst;
    }

    private Map<BCVoxel, Transform> getActual() {
        return this.useFirst ? this.modMapPing : this.modMapPong;
    }

    private Map<BCVoxel, Transform> getPrevious() {
        return this.useFirst ? this.modMapPong : this.modMapPing;
    }

    private Transform getTransform(BCVoxel part) {
        return getActual().computeIfAbsent(part, t -> new Transform());
    }

    public void rotate(BCVoxel box, float x, float y, float z) {
        this.getTransform(box).addRotation(x * this.mult, y * this.mult, z * this.mult);
    }

    public void move(BCVoxel box, float x, float y, float z) {
        this.getTransform(box).addOffset(x * this.mult, y * this.mult, z * this.mult);
    }

    public void scale(BCVoxel box, float x, float y, float z) {
        this.getTransform(box).addScale(x * this.mult, y * this.mult, z * this.mult);
    }

    public void setupKeyframe(float duration) {
        this.prevKeyframe = this.keyframe;
        this.keyframe += duration;
    }

    public void emptyKeyframe(float duration, Easing easing) {
        setupKeyframe(duration);
        this.apply(easing);
    }
	
	public void emptyKeyframe(float duration) {
		setupKeyframe(duration);
		this.apply(Easing.LINEAR);
	}

    public void staticKeyframe(float duration) {
        setupKeyframe(duration);
        this.overlap(Easing.LINEAR);
    }

    public void apply(Easing easing) {

        if (this.timer >= this.prevKeyframe && this.timer < this.keyframe) {
            float uneasedProg = (this.timer - this.prevKeyframe) / (this.keyframe - this.prevKeyframe);
            float prog = easing.progression(uneasedProg);
            float invProg = 1.0F - prog;

            getPrevious().forEach((box, t) -> {
                box.xRot += invProg * t.getRotationX() * this.mult;
                box.yRot += invProg * t.getRotationY() * this.mult;
                box.zRot += invProg * t.getRotationZ() * this.mult;
                box.x += invProg * t.getOffsetX() * this.mult;
                box.y += invProg * t.getOffsetY() * this.mult;
                box.z += invProg * t.getOffsetZ() * this.mult;
                box.scaleX += invProg * t.getScaleX() * this.mult;
                box.scaleY += invProg * t.getScaleY() * this.mult;
                box.scaleZ += invProg * t.getScaleZ() * this.mult;
            });

            getActual().forEach((box, t) -> {
                box.xRot += prog * t.getRotationX() * this.mult;
                box.yRot += prog * t.getRotationY() * this.mult;
                box.zRot += prog * t.getRotationZ() * this.mult;
                box.x += prog * t.getOffsetX() * this.mult;
                box.y += prog * t.getOffsetY() * this.mult;
                box.z += prog * t.getOffsetZ() * this.mult;
                box.scaleX += prog * t.getScaleX() * this.mult;
                box.scaleY += prog * t.getScaleY() * this.mult;
                box.scaleZ += prog * t.getScaleZ() * this.mult;
            });
        }

        getPrevious().clear();
        pingPongTables();
    }

    public void apply() {
        apply(Easing.LINEAR);
    }

    public void overlap(Easing easing) {
        float animationTick = this.timer;

        if (animationTick >= this.prevKeyframe && animationTick < this.keyframe) {
            float uneasedProg = (animationTick - this.prevKeyframe) / (this.keyframe - this.prevKeyframe);
            float prog = easing.progression(uneasedProg);

            getPrevious().forEach((box, t) -> {
                box.xRot += t.getRotationX() * this.mult;
                box.yRot += t.getRotationY() * this.mult;
                box.zRot += t.getRotationZ() * this.mult;
                box.x += t.getOffsetX() * this.mult;
                box.y += t.getOffsetY() * this.mult;
                box.z += t.getOffsetZ() * this.mult;
                box.scaleX += t.getScaleX() * this.mult;
                box.scaleY += t.getScaleY() * this.mult;
                box.scaleZ += t.getScaleZ() * this.mult;
            });

            getActual().forEach((box, t) -> {
                box.xRot += prog * t.getRotationX() * this.mult;
                box.yRot += prog * t.getRotationY() * this.mult;
                box.zRot += prog * t.getRotationZ() * this.mult;
                box.x += prog * t.getOffsetX() * this.mult;
                box.y += prog * t.getOffsetY() * this.mult;
                box.z += prog * t.getOffsetZ() * this.mult;
                box.scaleX += prog * t.getScaleX() * this.mult;
                box.scaleY += prog * t.getScaleY() * this.mult;
                box.scaleZ += prog * t.getScaleZ() * this.mult;
            });
        }
    }

    public void reset() {
        this.prevKeyframe = 0.0F;
        this.keyframe = 0.0F;
        getActual().clear();
	    getPrevious().clear();
    }

    public float disable(float startTick, float staticTicks, float endTick, float timer) {
        if (timer < startTick) {
            float prog = timer / startTick;
            return 1.0F - prog;
        } else if (timer >= startTick && timer < startTick + staticTicks) {
            return 0.0F;
        } else if (timer >= startTick + staticTicks && timer < startTick + staticTicks + endTick) {
            return (timer - (startTick + staticTicks)) / endTick;
        }
        return 0.0F;
    }

    public float disable(float startTick, float staticTicks, float endTick) {
        if (this.timer < startTick) {
            float prog = this.timer / startTick;
            return 1.0F - prog;
        } else if (this.timer >= startTick && this.timer < startTick + staticTicks) {
            return 0.0F;
        } else if (this.timer >= startTick + staticTicks && this.timer < startTick + staticTicks + endTick) {
            return (this.timer - (startTick + staticTicks)) / endTick;
        }
        return 0.0F;
    }

    public void disableAtomic(AtomicDouble atomic, float startTick, float staticTicks, float endTick, float timer) {
        if (timer < startTick) {
            float prog = timer / startTick;
            atomic.set(1 - prog);
        } else if (timer >= startTick && timer < startTick + staticTicks) {
            atomic.set(0);
        } else if (timer >= startTick + staticTicks && timer < startTick + staticTicks + endTick) {
            float prog = (timer - (startTick + staticTicks)) / endTick;
            atomic.set(prog);
        }
    }

    public void disableAtomic(AtomicDouble atomic, float startTick, float staticTicks, float endTick) {
        if (this.timer < startTick) {
            float prog = this.timer / startTick;
            atomic.set(1 - prog);
        } else if (this.timer >= startTick && this.timer < startTick + staticTicks) {
            atomic.set(0);
        } else if (this.timer >= startTick + staticTicks && this.timer < startTick + staticTicks + endTick) {
            float prog = (this.timer - (startTick + staticTicks)) / endTick;
            atomic.set(prog);
        }
    }
}

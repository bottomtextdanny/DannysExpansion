package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AtomicDouble;
import net.bottomtextdanny.braincell.mod.entity.modules.looped_walk.LoopedWalkProvider;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.client.model.EntityModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class BCEntityModel<E extends Entity> extends EntityModel<E> implements BCModel {
	private E host;
    public List<BCVoxel> defaultValues = Lists.newArrayList();
    public AtomicDouble walkMult = new AtomicDouble(1.0);
    public float globalSpeed;
    public int texWidth, texHeight;

    @Override
    public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.host = entityIn;
        this.walkMult = new AtomicDouble(1.0);
        stitchDefaultState();

        this.handleRotations(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.handleKeyframedAnimations(entityIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
    }

    public void handleRotations(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {}

    public void handleKeyframedAnimations(E entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {}

    public static <D extends LoopedWalkProvider> float caculateLimbSwingEasing(D entity) {
        return entity.operateWalkModule() ? DEMath.loopLerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, 1, entity.loopedWalkModule().prevLimbSwingLoop, entity.loopedWalkModule().limbSwingLoop) : 0.0F;
    }

    public static <D extends LoopedWalkProvider> float caculateLimbSwingAmountEasing(D entity) {
        return entity.operateWalkModule() ? Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.loopedWalkModule().prevRenderLimbSwingAmount, entity.loopedWalkModule().renderLimbSwingAmount) : 0.0F;
    }

    @Override
    public void prepareMobModel(E entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.host = entityIn;
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void setupDefaultState() {
        this.defaultValues.forEach(BCVoxel::getDefaultState);
    }

    public void stitchDefaultState() {
        this.defaultValues.forEach(BCVoxel::setDefaultState);
    }

    public void addPart(BCVoxel model) {
        model.index = this.defaultValues.size();
        this.defaultValues.add(model);
    }

    public void setOffset(BCVoxel part, float x, float y, float z) {
        part.x = x;
        part.y = y;
        part.z = z;
    }

    public void setSize(BCVoxel part, float x, float y, float z) {
        part.scaleX = x;
        part.scaleY = y;
        part.scaleZ = z;
    }

    public void addRotation(BCVoxel part, float x, float y, float z) {
        part.xRot += Math.toRadians(x);
        part.yRot += Math.toRadians(y);
        part.zRot += Math.toRadians(z);
    }

    public void addOffset(BCVoxel part, float x, float y, float z) {
        part.x += x;
        part.y += y;
        part.z += z;
    }

    public void addSize(BCVoxel part, float x, float y, float z) {
        part.scaleX += x;
        part.scaleY += y;
        part.scaleZ += z;
    }

	public E getHost() {
		return this.host;
	}

    @Deprecated
	public float walkRotationHelper(float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        return invert ? (DEMath.cos(limbSwing * speed + desync * (float)Math.PI) * (-amount * RAD * 2) * limbSwingAmount + weight * limbSwingAmount * RAD) * this.walkMult.floatValue() : (DEMath.cos(limbSwing * speed + desync * (float)Math.PI) * (amount * RAD * 2) * limbSwingAmount + weight * limbSwingAmount * RAD) * this.walkMult.floatValue();
    }

    @Deprecated
    public float walkOffsetHelper(float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        return invert ? (DEMath.cos(limbSwing * speed + desync * (float)Math.PI) * (-amount * 2) * limbSwingAmount + weight * limbSwingAmount) * this.walkMult.floatValue() : (DEMath.cos(limbSwing * speed + desync * (float)Math.PI) * (amount * 2) * limbSwingAmount + weight * limbSwingAmount) * this.walkMult.floatValue();
    }

    /**
     * @param degrees will be transformed to radians
     * @param desync is how much the rotation will desynchronize, 1.0F will invert it completely
     * @param invert quick way to invert the rotation
     */

    @Deprecated
    public void walkRotateX(BCVoxel part, float speed, float degrees, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.xRot += walkRotationHelper(speed, degrees, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkRotateY(BCVoxel part, float speed, float degrees, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.yRot += walkRotationHelper(speed, degrees, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkRotateZ(BCVoxel part, float speed, float degrees, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.zRot += walkRotationHelper(speed, degrees, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkOffsetX(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.x += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkOffsetY(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.y += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkOffsetZ(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.z += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkScaleX(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.scaleX += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkScaleY(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.scaleY += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Deprecated
    public void walkScaleZ(BCVoxel part, float speed, float amount, float desync, float weight, float limbSwing, float limbSwingAmount, boolean invert) {
        part.scaleZ += walkOffsetHelper(speed, amount, desync, weight, limbSwing, limbSwingAmount, invert);
    }

    @Override
    public int getTexWidth() {
        return this.texWidth;
    }

    @Override
    public int getTexHeight() {
        return this.texHeight;
    }
}

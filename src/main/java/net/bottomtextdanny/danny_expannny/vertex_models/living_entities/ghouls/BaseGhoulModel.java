package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.GhoulEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public abstract class BaseGhoulModel extends BCEntityModel<GhoulEntity> {
    protected BCVoxel body;
    protected BCVoxel hip;
    protected BCVoxel chest;
    protected BCVoxel rightArm;
    protected BCVoxel rightForearm;
    protected BCVoxel leftArm;
    protected BCVoxel leftForearm;
    protected BCVoxel head;
    protected BCVoxel rightLeg;
    protected BCVoxel rightCalf;
    protected BCVoxel leftLeg;
    protected BCVoxel leftCalf;

    @Override
    public void setupAnim(GhoulEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch){
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.chest.xRot += RAD * 3.0F * idle1;
        this.rightArm.xRot += RAD * -4.0F * idle1;
        this.leftArm.xRot += RAD * -4.0F * idle1;
        this.rightArm.zRot += RAD * 2.0F * idle2;
        this.leftArm.zRot += RAD * -2.0F * idle2;

        this.head.yRot += headYaw * BCMath.FRAD * 0.8F;
        this.head.xRot += Mth.clamp(headPitch * BCMath.FRAD, -80.0F, 80.0F);
        this.chest.yRot += headYaw * BCMath.FRAD * 0.2F;

        walkOffsetY(this.body, 1.0F, 1.5F, (float) 0, 2.2F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.hip, 1.0F, 18.5F, 0.0F, 7.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.chest, 1.0F, 2.5F, 0.05F, 2.5F, limbSwing, limbSwingAmount, false);
        walkRotateY(this.chest, 0.5F, 5.0F, -0.05F, (float) 0, limbSwing, limbSwingAmount, false);
        walkRotateX(this.head, 0.5F, -15.5F, 0.2F, 0.0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightArm, 0.5F, 31.25F, 0.0F, 11.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightForearm, 0.5F, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftArm, 0.5F, 31.25F, 0.0F, 11.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftForearm, 0.5F, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightLeg, 0.5F, 35.0F, 0.0F, -12.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightCalf, 0.5F, -2.5F, 0.0F, 27.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftLeg, 0.5F, 35.0F, 0.0F, -12.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftCalf, 0.5F, -2.5F, 0.0F, 27.5F, limbSwing, limbSwingAmount, false);
    }

    @Override
    public void handleKeyframedAnimations(GhoulEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
        EntityModelAnimator animator = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());
	    
	    if (!entity.livingModule.isPlayingNull()) {
		    EntityModelAnimator livingAnimator = new EntityModelAnimator(this, (float) entity.livingModule.getTick() + getPartialTick());
		
		    if (entity.livingModule.isPlaying(GhoulEntity.BREATH)) {
			
			    livingAnimator.setupKeyframe(7.0F);
			    livingAnimator.rotate(this.head, -30.0F, 0.0F, 0.0F);
			    livingAnimator.apply(Easing.EASE_OUT_SQUARE);
			
			    livingAnimator.setupKeyframe(5.0F);
			    livingAnimator.rotate(this.head, -5.0F, 0.0F, 0.0F);
			    livingAnimator.apply();
			
			    livingAnimator.setupKeyframe(5.0F);
			    livingAnimator.rotate(this.head, -50.0F, 0.0F, 0.0F);
			    livingAnimator.apply(Easing.EASE_OUT_SQUARE);
			
			    livingAnimator.emptyKeyframe(5.0F, Easing.LINEAR);
		    } else if (entity.livingModule.isPlaying(GhoulEntity.WTF)) {
			    livingAnimator.setupKeyframe(5.0F);
			    livingAnimator.rotate(this.head, 0.0F, -70.0F, 0.0F);
			    livingAnimator.apply(Easing.EASE_OUT_SQUARE);
			
			    livingAnimator.setupKeyframe(4.0F);
			    livingAnimator.rotate(this.head, 0.0F, 80.0F, 0.0F);
			    livingAnimator.apply();
			
			    livingAnimator.setupKeyframe(3.0F);
			    livingAnimator.rotate(this.head, 0.0F, -60.0F, 0.0F);
			    livingAnimator.apply(Easing.EASE_OUT_SQUARE);
			
			    livingAnimator.setupKeyframe(2.0F);
			    livingAnimator.rotate(this.head, 0.0F, 30.0F, 0.0F);
			    livingAnimator.apply(Easing.EASE_OUT_SQUARE);
			
			    livingAnimator.emptyKeyframe(2.0F, Easing.EASE_OUT_BACK);
		    }
	    }
	    
        if (entity.mainHandler.isPlaying(GhoulEntity.VOMIT)) {

            animator.setupKeyframe(10.0F);
            animator.move(this.body, 0.0F, 0.3F, -1.0F);
            animator.rotate(this.body, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, -27.5F, 0.0F, 0.0F);
            animator.rotate(this.head, -40.0F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 12.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.move(this.body, 0.0F, -0.3F, 1.5F);
            animator.rotate(this.body, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.head, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -32.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -32.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, -47.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -47.5F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.emptyKeyframe(10.0F, Easing.EASE_OUT_SQUARE);
        }

        if (entity.mainHandler.isPlaying(GhoulEntity.GRAB)) {

            animator.setupKeyframe(10.0F);
            animator.rotate(this.body, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.hip, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -7.5F, 20.0F, 5.0F);
            animator.rotate(this.head, 0.0F, -37.5F, 0.0F);
            animator.rotate(this.rightArm, 25.0F, 5.0F, 140.0F);
            animator.rotate(this.rightForearm, -35.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 30.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, -5.5F, -25.0F, 1.0F);
            animator.rotate(this.leftLeg, 5.5F, -25.0F, 1.0F);
            animator.apply();

            animator.setupKeyframe(6.0F);
            animator.rotate(this.body, 0.0F, -12.5F, 0.0F);
            animator.rotate(this.hip, 22.5F, 0.0F, 0.0F);
            animator. rotate(this.chest, 15.0F, -27.5F, 0.0F);
            animator.rotate(this.head, -25.0F, 32.5F, 0.0F);
            animator.rotate(this.rightArm, -100.0F, 0.0F, 80.0F);
            animator.rotate(this.rightForearm, -37.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 2.5F, -12.5F, 1.0F);
            animator.rotate(this.leftLeg, -2.5F, -12.5F, 1.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.emptyKeyframe(10.0F, Easing.EASE_OUT_SQUARE);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

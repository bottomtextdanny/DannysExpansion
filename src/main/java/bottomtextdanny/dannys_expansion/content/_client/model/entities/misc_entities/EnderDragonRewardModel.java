package bottomtextdanny.dannys_expansion.content._client.model.entities.misc_entities;

import bottomtextdanny.dannys_expansion.content.entities.misc.EnderDragonRewardEntity;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class EnderDragonRewardModel extends BCEntityModel<EnderDragonRewardEntity> {
	private final BCJoint model;
	
	public EnderDragonRewardModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, -8.0F, 0.0F);
        this.model.uvOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		
		//modelInitEnd
	}
	
	@Override
	public void handleRotations(EnderDragonRewardEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        this.model.xRot = RAD * 16 * DEMath.sin(ageInTicks * 0.1F);
        this.model.zRot = RAD * 16 * DEMath.cos(ageInTicks * 0.1F + Math.PI);
	}
	
	@Override
	public void handleKeyframedAnimations(EnderDragonRewardEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
		
		if (entity.rotationLoopHandler.isPlaying(EnderDragonRewardEntity.ROTATE)) {
			ModelAnimator animator = new ModelAnimator(this, entity.rotationLoopHandler.linearProgress());
			
			animator.setupKeyframe(200.0F);
			animator.rotate(this.model, 0.0F, 360.0F, 0.0F);
			animator.apply();
		}
		
		if (entity.mainAnimationHandler.isPlaying(EnderDragonRewardEntity.SPAWN)) {
			ModelAnimator animator = new ModelAnimator(this, entity.mainAnimationHandler.linearProgress());
			
			animator.setupKeyframe(0.0F);
			animator.scale(this.model, -1.0F, -1.0F, -1.0F);
			animator.apply();
			
			animator.emptyKeyframe(15.0F, Easing.EASE_IN_BACK);
		} else if (entity.mainAnimationHandler.isPlaying(EnderDragonRewardEntity.GIVE_ITEMS)) {
			ModelAnimator animator = new ModelAnimator(this, entity.mainAnimationHandler.linearProgress());
			
			animator.staticKeyframe(20.0F);
			
			animator.setupKeyframe(22.0F);
			animator.scale(this.model, -1.0F, -1.0F, -1.0F);
			animator.apply(Easing.EASE_OUT_CUBIC);
			
			animator.setupKeyframe(8.0F);
			animator.scale(this.model, 1.4F, 1.4F, 1.4F);
			animator.apply(Easing.EASE_IN_CUBIC);
			
			animator.setupKeyframe(11.0F);
			animator.scale(this.model, -1.0F, -1.0F, -1.0F);
			animator.apply(Easing.EASE_OUT_CUBIC);
			
			animator.reset();
			animator.staticKeyframe(20.0F);
			
			animator.setupKeyframe(42.0F);
			animator.rotate(this.model, 0.0F, 960.0F, 0.0F);
			animator.apply(Easing.EASE_IN_SINE);
			
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

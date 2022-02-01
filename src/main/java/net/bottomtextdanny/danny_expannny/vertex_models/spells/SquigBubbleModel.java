package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SquigBubbleEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class SquigBubbleModel extends BCEntityModel<SquigBubbleEntity> {
	private final BCVoxel model;
	private final BCVoxel rotator;
	
	public SquigBubbleModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.rotator = new BCVoxel(this);
        this.rotator.setPos(0.0F, -4.0F, 0.0F);
        this.model.addChild(this.rotator);
        this.rotator.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		setupDefaultState();
	}
	
	@Override
	public void handleRotations(SquigBubbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        this.rotator.yRot += DEMath.sin(ageInTicks * 0.025F + 10.0F) * Math.PI;
        this.rotator.zRot += DEMath.sin(ageInTicks * 0.015F + 20.0F) * Math.PI;
	}
	
	@Override
	public void handleKeyframedAnimations(SquigBubbleEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
		EntityModelAnimator lifeAnimator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());
		
		lifeAnimator.setupKeyframe(0.0F);
		lifeAnimator.scale(this.rotator, -1.0F, -1.0F, -1.0F);
		lifeAnimator.apply();
		
		lifeAnimator.emptyKeyframe(23.0F, Easing.BOUNCE_OUT);
		
		if (entity.mainHandler.isPlaying(SquigBubbleEntity.POP)) {
			EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());
			animator.setupKeyframe(2.0F);
			animator.scale(this.rotator, -0.3F, -0.3F, -0.3F);
			animator.apply();
			animator.setupKeyframe(5.0F);
			animator.scale(this.rotator, 0.8F, 0.8F, 0.8F);
			animator.apply(Easing.EASE_IN_CUBIC);
		}
		
		if (entity.hurtModule.isPlaying(SquigBubbleEntity.HURT)) {
			EntityModelAnimator animator = new EntityModelAnimator(this, entity.hurtModule.linearProgress());
			animator.setupKeyframe(2.0F);
			animator.scale(this.rotator, -0.3F, -0.3F, -0.3F);
			animator.apply();
			animator.setupKeyframe(5.0F);
			animator.scale(this.rotator, 0.8F, 0.8F, 0.8F);
			animator.apply(Easing.EASE_IN_CUBIC);
			animator.emptyKeyframe(3.0F, Easing.LINEAR);
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

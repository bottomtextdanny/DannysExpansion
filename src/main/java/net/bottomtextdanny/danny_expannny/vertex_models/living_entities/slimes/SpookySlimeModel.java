package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.mundane_slime.MundaneSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class SpookySlimeModel extends BCEntityModel<MundaneSlimeEntity> {
	private final BCVoxel body;
	private final BCVoxel mask;
	
	public SpookySlimeModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 24.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, -0.15F, false);
        this.body.texOffs(0, 18).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, false);

        this.mask = new BCVoxel(this);
        this.mask.setPos(0.0F, -4.0F, -5.0F);
        this.body.addChild(this.mask);
        this.mask.texOffs(0, 36).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);

		setupDefaultState();
	}
	
	@Override
	public void handleRotations(MundaneSlimeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float idle = DEMath.sin(ageInTicks * 0.4F);
		
		addSize(this.body, 0.1111F * idle, -0.1F * idle, 0.1111F * idle);
		addSize(this.mask, -0.1111F * idle * 0.5F, 0.1F * idle * 0.5F, -0.1111F * idle * 0.5F);
	}
	
	@Override
	public void handleKeyframedAnimations(MundaneSlimeEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());
		
		if(entity.mainAnimationHandler.isPlaying(entity.jump)) {
			
			animator.setupKeyframe(4);
			animator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
			animator.scale(this.mask, -0.7692F * 0.5F, 0.3F * 0.5F, -0.7692F * 0.5F);
			animator.apply();
			
			animator.setupKeyframe(3);
			animator.scale(this.body, -0.3F, 0.7692F, -0.3F);
			animator.scale(this.mask, 0.3F * 0.5F, -0.7692F * 0.5F, 0.3F * 0.5F);
			animator.apply();
			
			animator.emptyKeyframe(10, Easing.LINEAR);
		}
		
		if(entity.mainAnimationHandler.isPlaying(entity.death)) {
			
			animator.setupKeyframe(10);
			animator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
			animator.scale(this.mask, -0.7692F * 0.5F, 0.3F * 0.5F, -0.7692F * 0.5F);
			animator.apply();
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

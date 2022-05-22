package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime.MundaneSlime;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class SpookySlimeModel extends BCEntityModel<MundaneSlime> {
	private final BCJoint body;
	private final BCJoint mask;
	
	public SpookySlimeModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, 24.0F, 0.0F);
        this.body.uvOffset(0, 0).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, -0.15F, false);
        this.body.uvOffset(0, 18).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, false);

        this.mask = new BCJoint(this);
        this.mask.setPosCore(0.0F, -4.0F, -5.0F);
        this.body.addChild(this.mask);
        this.mask.uvOffset(0, 36).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);

		//modelInitEnd
	}
	
	@Override
	public void handleRotations(MundaneSlime entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		float idle = DEMath.sin(ageInTicks * 0.4F);
		
		this.body.addToScale(0.1111F * idle, -0.1F * idle, 0.1111F * idle);
		this.mask.addToScale(-0.1111F * idle * 0.5F, 0.1F * idle * 0.5F, -0.1111F * idle * 0.5F);
	}
	
	@Override
	public void handleKeyframedAnimations(MundaneSlime entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
		
		if(entity.mainHandler.isPlaying(entity.JUMP)) {
			
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
		
		if(entity.mainHandler.isPlaying(entity.DEATH)) {
			
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

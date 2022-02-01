package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.squig.SquigEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class SquigModel extends BCEntityModel<SquigEntity> {
	private final BCVoxel model;
	private final BCVoxel body;
	private final BCVoxel yAux;
	private final BCVoxel tentBackLeft;
	private final BCVoxel nentBackLeft;
	private final BCVoxel tentBackRight;
	private final BCVoxel nentBackRight;
	private final BCVoxel tentFrontLeft;
	private final BCVoxel nentFrontLeft;
	private final BCVoxel tentFrontRight;
	private final BCVoxel nentFrontRight;
	
	public SquigModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -5.0F, 0.0F);
        this.model.addChild(this.body);


        this.yAux = new BCVoxel(this);
        this.yAux.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.yAux);
        this.yAux.texOffs(0, 0).addBox(-5.0F, -11.0F, -3.5F, 11.0F, 8.0F, 10.0F, 0.0F, false);
        this.yAux.texOffs(0, 18).addBox(-3.0F, -3.0F, -4.5F, 7.0F, 3.0F, 8.0F, 0.0F, false);
        this.yAux.texOffs(0, 0).addBox(-4.0F, -5.0F, -4.5F, 3.0F, 3.0F, 2.0F, 0.025F, false);
        this.yAux.texOffs(0, 0).addBox(2.0F, -5.0F, -4.5F, 3.0F, 3.0F, 2.0F, 0.025F, false);
        this.yAux.texOffs(0, 29).addBox(-2.5F, -2.0F, -3.5F, 6.0F, 7.0F, 7.0F, -0.25F, false);
        this.yAux.texOffs(0, 21).addBox(3.0F, -1.0F, -4.501F, 2.0F, 4.0F, 0.0F, 0.0F, false);
        this.yAux.texOffs(4, 21).addBox(-4.0F, -1.0F, -4.501F, 2.0F, 4.0F, 0.0F, 0.0F, false);
        this.yAux.texOffs(0, 18).addBox(-1.0F, 0.0F, -4.501F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        this.tentBackLeft = new BCVoxel(this);
        this.tentBackLeft.setPos(2.5F, 3.25F, 2.0F);
        this.yAux.addChild(this.tentBackLeft);
		setRotationAngle(this.tentBackLeft, 0.0873F, 0.7854F, 0.0F);
        this.tentBackLeft.texOffs(42, 0).addBox(-1.5F, -0.25F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentBackLeft = new BCVoxel(this);
        this.nentBackLeft.setPos(0.0F, 7.75F, 0.0F);
        this.tentBackLeft.addChild(this.nentBackLeft);
        this.nentBackLeft.texOffs(54, 0).addBox(-1.0F, 0.0F, 0.55F, 2.0F, 9.0F, 0.0F, 0.0F, false);
        this.nentBackLeft.texOffs(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentBackRight = new BCVoxel(this);
        this.tentBackRight.setPos(-1.5F, 3.25F, 2.0F);
        this.yAux.addChild(this.tentBackRight);
		setRotationAngle(this.tentBackRight, 0.0873F, -0.7854F, 0.0F);
        this.tentBackRight.texOffs(42, 0).addBox(-1.5F, -0.25F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentBackRight = new BCVoxel(this);
        this.nentBackRight.setPos(0.0F, 7.75F, 0.0F);
        this.tentBackRight.addChild(this.nentBackRight);
        this.nentBackRight.texOffs(54, 0).addBox(-1.0F, 0.0F, 0.55F, 2.0F, 9.0F, 0.0F, 0.0F, true);
        this.nentBackRight.texOffs(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentFrontLeft = new BCVoxel(this);
        this.tentFrontLeft.setPos(2.5F, 4.0F, -2.0F);
        this.yAux.addChild(this.tentFrontLeft);
		setRotationAngle(this.tentFrontLeft, -0.0873F, -0.7854F, 0.0F);
        this.tentFrontLeft.texOffs(42, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentFrontLeft = new BCVoxel(this);
        this.nentFrontLeft.setPos(0.0F, 8.0F, 0.0F);
        this.tentFrontLeft.addChild(this.nentFrontLeft);
        this.nentFrontLeft.texOffs(54, 0).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 9.0F, 0.0F, 0.0F, false);
        this.nentFrontLeft.texOffs(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentFrontRight = new BCVoxel(this);
        this.tentFrontRight.setPos(-1.5F, 4.0F, -2.0F);
        this.yAux.addChild(this.tentFrontRight);
		setRotationAngle(this.tentFrontRight, -0.0873F, 0.7854F, 0.0F);
        this.tentFrontRight.texOffs(42, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentFrontRight = new BCVoxel(this);
        this.nentFrontRight.setPos(0.0F, 8.0F, 0.0F);
        this.tentFrontRight.addChild(this.nentFrontRight);
        this.nentFrontRight.texOffs(54, 0).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 9.0F, 0.0F, 0.0F, true);
        this.nentFrontRight.texOffs(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		
		setupDefaultState();
	}
	
	@Override
	public void handleRotations(SquigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
		float yaw = Mth.rotLerp(getPartialTick(), entity.prevRenderSquigYaw + 180.0F, entity.renderSquigYaw + 180.0F) ;
		
		float off = Mth.rotlerp(entity.prevYRotOff, entity.yRotOff, getPartialTick());


        this.body.xRot += (headPitch + 90.0F) * RAD;
        this.body.yRot += yaw * RAD;

        this.yAux.yRot += off * RAD;
	}
	
	@Override
	public void handleKeyframedAnimations(SquigEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
		
		if (entity.mainHandler.isPlaying(entity.goAnimation)) {
			EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());
			
			animator.setupKeyframe(7.0F);
			animator.rotate(this.tentBackLeft, 60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentBackRight, 60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontLeft, -60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontRight, -60.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.setupKeyframe(4.0F);
			animator.rotate(this.tentBackLeft, -12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentBackRight, -12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontLeft, 12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontRight, 12.5F, 0.0F, 0.0F);
			animator.apply();
			
			animator.emptyKeyframe(8.0F, Easing.LINEAR);
			animator.reset();
			
			animator.staticKeyframe(2.5F);
			
			animator.setupKeyframe(7.0F);
			animator.rotate(this.nentBackLeft, 75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentBackRight, 75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontLeft, -75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontRight, -75.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.setupKeyframe(4.0F);
			animator.rotate(this.nentBackLeft, -15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentBackRight, -15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontLeft, 15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontRight, 15.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.emptyKeyframe(5.5F, Easing.LINEAR);
		}
		
		if (entity.hurtModule.isPlaying(entity.hurtAnimation)) {
			EntityModelAnimator animator = new EntityModelAnimator(this, entity.hurtModule.linearProgress());
			animator.setupKeyframe(2.0F);
			animator.scale(this.body, -0.2F, -0.2F, -0.2F);
			animator.apply();
			animator.setupKeyframe(4.0F);
			animator.scale(this.body, 0.1F, 0.1F, 0.1F);
			animator.apply(Easing.BOUNCE_OUT);
			
			animator.emptyKeyframe(3.0F, Easing.LINEAR);
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

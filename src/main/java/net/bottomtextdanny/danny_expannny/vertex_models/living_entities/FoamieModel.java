package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FoamieEntity;
import net.minecraft.util.Mth;

public class FoamieModel extends BCEntityModel<FoamieEntity> {
	private final BCVoxel model;
	private final BCVoxel body;
	private final BCVoxel rightFoot;
	private final BCVoxel leftFoot;
	private final BCVoxel cap;
	
	public FoamieModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -2.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 5.0F, 8.0F, 0.0F, false);

        this.rightFoot = new BCVoxel(this);
        this.rightFoot.setPos(-4.0F, -1.0F, 0.0F);
        this.body.addChild(this.rightFoot);
		setRotationAngle(this.rightFoot, 0.0F, 0.1745F, 0.0F);
        this.rightFoot.texOffs(0, 33).addBox(-2.0F, 0.0F, -3.5F, 4.0F, 3.0F, 6.0F, 0.0F, false);

        this.leftFoot = new BCVoxel(this);
        this.leftFoot.setPos(4.0F, -1.0F, 0.0F);
        this.body.addChild(this.leftFoot);
		setRotationAngle(this.leftFoot, 0.0F, -0.1745F, 0.0F);
        this.leftFoot.texOffs(0, 33).addBox(-2.0F, 0.0F, -3.5F, 4.0F, 3.0F, 6.0F, 0.0F, true);

        this.cap = new BCVoxel(this);
        this.cap.setPos(0.0F, -4.0F, 0.0F);
        this.body.addChild(this.cap);
        this.cap.texOffs(0, 13).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 8.0F, 12.0F, 0.0F, false);

		setupDefaultState();
	}
	
	@Override
	public void handleRotations(FoamieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
	
	}
	
	@Override
	public void handleKeyframedAnimations(FoamieEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
		float f = Mth.clamp(easedlimbSwingAmount / 0.125F, 0, 1);
		float easedLimbSwing = caculateLimbSwingEasing(entity);
		EntityModelAnimator walkAnimator = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);
		
		walkAnimator.setupKeyframe(0.0F);
		walkAnimator.rotate(this.body, 0.0F, 22.5F, 0.0F);
		walkAnimator.move(this.rightFoot, -1.0F, 0.0F, 2.0F);
		walkAnimator.rotate(this.rightFoot, 12.5F, -32.5F, 0.0F);
		walkAnimator.move(this.leftFoot, 1.0F, 0.0F, -2.0F);
		walkAnimator.rotate(this.leftFoot, 0.0F, -10.0F, 0.0F);
		walkAnimator.apply();
		
		walkAnimator.setupKeyframe(0.25F);
		walkAnimator.rotate(this.body, 0.0F, 0.0F, 17.5F);
		walkAnimator.rotate(this.rightFoot, -20.0F, -11.25F, 0.0F);
		walkAnimator.rotate(this.leftFoot, 0.0F, 11.25F, -17.5F);
		walkAnimator.apply();
		
		walkAnimator.setupKeyframe(0.25F);
		walkAnimator.rotate(this.body, 0.0F, -22.5F, 0.0F);
		walkAnimator.move(this.rightFoot, -1.0F, 0.0F, -2.0F);
		walkAnimator.rotate(this.rightFoot, 0.0F, 12.5F, 0.0F);
		walkAnimator.move(this.leftFoot, 1.0F, 0.0F, 2.0F);
		walkAnimator.rotate(this.leftFoot, 12.5F, 32.5F, 0.0F);
		walkAnimator.apply();
		
		walkAnimator.setupKeyframe(0.25F);
		walkAnimator.rotate(this.body, 0.0F, 0.0F, -17.5F);
		walkAnimator.rotate(this.rightFoot, 0.0F, -11.25F, 17.5F);
		walkAnimator.rotate(this.leftFoot, -20.0F, 11.25F, 0.0F);
		walkAnimator.apply();
		
		walkAnimator.setupKeyframe(0.25F);
		walkAnimator.rotate(this.body, 0.0F, 22.5F, 0.0F);
		walkAnimator.move(this.rightFoot, -1.0F, 0.0F, 2.0F);
		walkAnimator.rotate(this.rightFoot, 12.5F, -32.5F, 0.0F);
		walkAnimator.move(this.leftFoot, 1.0F, 0.0F, -2.0F);
		walkAnimator.rotate(this.leftFoot, 0.0F, -10.0F, 0.0F);
		walkAnimator.apply();
		
		walkAnimator.reset();
		
		walkAnimator.setupKeyframe(0.0F);
		walkAnimator.move(this.model, 0.0F, 0.0F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, -0.7F, -0.65F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, -1.0F, -1.3F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, -0.8F, -0.65F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, 0.0F, 0.0F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, 0.7F, -0.65F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, 1.0F, -1.3F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, 0.8F, -0.65F, 0.0F);
		walkAnimator.apply();
		walkAnimator.setupKeyframe(0.125F);
		walkAnimator.move(this.model, 0.0F, 0.0F, 0.0F);
		walkAnimator.apply();
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoul;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;

public class PetrifiedGhoulModel extends BCEntityModel<PetrifiedGhoul> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel rightArm;
    private final BCVoxel leftArm;
    private final BCVoxel rightLeg;
    private final BCVoxel leftLeg;

    public PetrifiedGhoulModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -14.0F, 0.0F);
        this.model.addChild(this.body);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.0436F, 0.0F, 0.0F);
        this.hip.texOffs(0, 30).addBox(-4.0F, -7.0F, -2.0F, 8.0F, 7.0F, 4.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -7.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0436F, 0.0F, 0.0F);
        this.chest.texOffs(0, 17).addBox(-6.0F, -7.0F, -2.5F, 12.0F, 7.0F, 6.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -7.0F, 0.5F);
        this.chest.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-4.0F, -9.0F, -4.5F, 8.0F, 9.0F, 8.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-6.0F, -5.0F, 0.5F);
        this.chest.addChild(this.rightArm);
        this.rightArm.texOffs(32, 0).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        this.rightArm.texOffs(0, 41).addBox(-8.0F, -1.0F, 0.0F, 5.0F, 15.0F, 0.0F, 0.0F, false);
        this.rightArm.texOffs(10, 36).addBox(-1.5F, -1.0F, 1.5F, 0.0F, 15.0F, 5.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(6.0F, -5.0F, 0.5F);
        this.chest.addChild(this.leftArm);
        this.leftArm.texOffs(24, 30).addBox(0.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        this.leftArm.texOffs(0, 41).addBox(3.0F, -1.0F, 0.0F, 5.0F, 15.0F, 0.0F, 0.0F, true);
        this.leftArm.texOffs(10, 36).addBox(1.5F, -1.0F, 1.5F, 0.0F, 15.0F, 5.0F, 0.0F, true);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.4F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(36, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.4F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(36, 18).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(PetrifiedGhoul entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.chest.xRot += RAD * 3.0F * idle1;
        this.rightArm.xRot += RAD * -4.0F * idle1;
        this.leftArm.xRot += RAD * -4.0F * idle1;
        this.rightArm.zRot += RAD * 2.0F * idle2;
        this.leftArm.zRot += RAD * -2.0F * idle2;

        this.head.yRot += headYaw * RAD * 0.8F;
        this.head.xRot += headPitch * RAD;
        this.chest.yRot += headYaw * RAD * 0.2F;
    }

    @Override
    public void handleKeyframedAnimations(PetrifiedGhoul entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.mainHandler.isPlaying(PetrifiedGhoul.SIDE_ATTACK)) {
            EntityModelAnimator attackAnimation = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

            attackAnimation.setupKeyframe(8.0F);
            attackAnimation.rotate(this.hip, -22.5F, 45.0F, 0.0F);
            attackAnimation.rotate(this.chest, 17.5F, 40.0F, 0.0F);
            attackAnimation.rotate(this.head, 0.0F, -27.5F, 0.0F);
            //attackAnimation.rotate(rightArm, 0.0F, 0.0F, 0.0F);
            attackAnimation.rotate(this.leftArm, -87.5F, 72.5F, 15.0F);
            attackAnimation.apply();

            attackAnimation.setupKeyframe(3.2F);
            attackAnimation.rotate(this.hip, 27.5F, -12.5F, 0.0F);
            attackAnimation.rotate(this.chest, -17.5F, -22.5F, 0.0F);
            attackAnimation.rotate(this.head, 0.0F, 27.5F, 0.0F);
            attackAnimation.rotate(this.rightArm, -7.5F, 0.0F, 10.0F);
            attackAnimation.rotate(this.leftArm, -87.5F, -80.0F, 0.0F);
            attackAnimation.apply();

            attackAnimation.staticKeyframe(4.8F);

            attackAnimation.emptyKeyframe(6.4F);

        } else if (entity.mainHandler.isPlaying(PetrifiedGhoul.STRONG_ATTACK)) {
            EntityModelAnimator attackAnimation = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

            attackAnimation.setupKeyframe(8.0F);
            attackAnimation.rotate(this.hip, -15.0F, 0.0F, 0.0F);
            attackAnimation.rotate(this.chest, -25.0F, 0.0F, 0.0F);
            attackAnimation.apply();

            attackAnimation.staticKeyframe(3.2F);

            attackAnimation.setupKeyframe(4.0F);
            attackAnimation.rotate(this.hip, 17.5F, 0.0F, 0.0F);
            attackAnimation.rotate(this.chest, 52.5F, 0.0F, 0.0F);
            attackAnimation.rotate(this.head, -30.0F, 0.0F, 0.0F);
            attackAnimation.apply();

            attackAnimation.staticKeyframe(4.0F);

            attackAnimation.emptyKeyframe(12.8F);

            attackAnimation.reset();

            attackAnimation.setupKeyframe(4.0F);
            attackAnimation.rotate(this.rightArm, -77.5F, 25.0F, 0.0F);
            attackAnimation.rotate(this.leftArm, -77.5F, -25.0F, 0.0F);
            attackAnimation.apply();

            attackAnimation.setupKeyframe(4.0F);
            attackAnimation.rotate(this.rightArm, -167.5F, -17.5F, 0.0F);
            attackAnimation.rotate(this.leftArm, -167.5F, 17.5F, 0.0F);
            attackAnimation.apply();

            attackAnimation.staticKeyframe(3.2F);

            attackAnimation.setupKeyframe(4.0F);
            attackAnimation.rotate(this.rightArm, -100.0F, -30.0F, 0.0F);
            attackAnimation.rotate(this.leftArm, -100.0F, 30.0F, 0.0F);
            attackAnimation.apply();

            attackAnimation.staticKeyframe(4.0F);

            attackAnimation.emptyKeyframe(12.8F);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount * 12.0F, 0.0F, 1.0F);
        float easedLimbSwing = Mth.clamp(caculateLimbSwingEasing(entity), 0.0F, 0.999F);
        if (easedLimbSwing > 0.0F) {
            EntityModelAnimator walkAnimation = new EntityModelAnimator(this, easedLimbSwing).multiplier(f);

            walkAnimation.setupKeyframe(0.0F);
            walkAnimation.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.chest, 20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.head, -10.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.rightArm, -20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.leftArm, -20.0F, 0.0F, 0.0F);
            //walkAnimation.move(rightLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.rightLeg, -32.5F, 0.0F, 0.0F);
            walkAnimation.move(this.leftLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.leftLeg, 35.0F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.25F);
            walkAnimation.rotate(this.hip, -7.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.chest, 5.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.head, 2.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.rightArm, -2.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.leftArm, -2.5F, 0.0F, 0.0F);
            walkAnimation.move(this.rightLeg, 0.0F, 0.0F, 1.0F);
            //walkAnimation.rotate(rightLeg, -32.5F, 0.0F, 0.0F);
            walkAnimation.move(this.leftLeg, 0.0F, -2.0F, -1.0F);
            walkAnimation.rotate(this.leftLeg, -10.0F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.25F);
            walkAnimation.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.chest, 20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.head, -10.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.rightArm, -20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.leftArm, -20.0F, 0.0F, 0.0F);
            walkAnimation.move(this.rightLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.rightLeg, 35.0F, 0.0F, 0.0F);
            //walkAnimation.move(leftLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.leftLeg, -32.5F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.25F);
            walkAnimation.rotate(this.hip, -7.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.chest, 5.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.head, 2.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.rightArm, -2.5F, 0.0F, 0.0F);
            walkAnimation.rotate(this.leftArm, -2.5F, 0.0F, 0.0F);
            walkAnimation.move(this.rightLeg, 0.0F, -2.0F, -1.0F);
            walkAnimation.rotate(this.rightLeg, -10.0F, 0.0F, 0.0F);
            walkAnimation.move(this.leftLeg, 0.0F, 0.0F, 1.0F);
            //walkAnimation.rotate(leftLeg, -32.5F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.25F);
            walkAnimation.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.chest, 20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.head, -10.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.rightArm, -20.0F, 0.0F, 0.0F);
            walkAnimation.rotate(this.leftArm, -20.0F, 0.0F, 0.0F);
            //walkAnimation.move(rightLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.rightLeg, -32.5F, 0.0F, 0.0F);
            walkAnimation.move(this.leftLeg, 0.0F, 0.0F, 1.0F);
            walkAnimation.rotate(this.leftLeg, 35.0F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.reset();

            walkAnimation.setupKeyframe(0.0F);
            walkAnimation.move(this.model, 0.0F, 2.2F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 1.15F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.5F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.15F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.25F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.7F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 1.75F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 2.2F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 1.15F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.5F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.15F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.0F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.25F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 0.7F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 1.75F, 0.0F);
            walkAnimation.apply();

            walkAnimation.setupKeyframe(0.0625F);
            walkAnimation.move(this.model, 0.0F, 2.2F, 0.0F);
            walkAnimation.apply();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

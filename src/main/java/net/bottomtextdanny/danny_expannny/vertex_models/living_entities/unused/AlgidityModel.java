package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.unused;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.AlgidityEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;

public class AlgidityModel extends BCEntityModel<AlgidityEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel rightLeg;
    private final BCVoxel rightCalf;
    private final BCVoxel leftLeg;
    private final BCVoxel leftCalf;

    public AlgidityModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -17.0F, 0.0F);
        this.model.addChild(this.body);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        this.hip.texOffs(0, 0).addBox(-6.5F, -6.0F, -3.5F, 13.0F, 6.0F, 7.0F, 0.0F, false);
        this.hip.texOffs(0, 13).addBox(-6.0F, -12.0F, -3.0F, 12.0F, 6.0F, 6.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -12.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0436F, 0.0F, 0.0F);
        this.chest.texOffs(0, 25).addBox(-7.5F, -9.0F, -4.0F, 15.0F, 9.0F, 8.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -9.0F, -0.5F);
        this.chest.addChild(this.head);
        setRotationAngle(this.head, 0.0436F, 0.0F, 0.0F);
        this.head.texOffs(0, 42).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 9.0F, 10.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-7.5F, -6.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.0436F, 0.0F, 0.0F);
        this.rightArm.texOffs(40, 0).addBox(-5.0F, -5.0F, -2.5F, 5.0F, 13.0F, 5.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-2.5F, 8.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        this.rightForearm.texOffs(60, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 13.0F, 5.0F, 0.25F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(7.5F, -6.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.0436F, 0.0F, 0.0F);
        this.leftArm.texOffs(40, 0).addBox(0.0F, -5.0F, -2.5F, 5.0F, 13.0F, 5.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(2.5F, 8.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        this.leftForearm.texOffs(60, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 13.0F, 5.0F, 0.25F, true);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-3.5F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(40, 18).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 8.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(60, 18).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 9.0F, 5.0F, 0.25F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(3.5F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(40, 18).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 8.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(60, 18).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 9.0F, 5.0F, 0.25F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(AlgidityEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.chest.xRot += RAD * 3 * idle1;
        this.rightArm.xRot += RAD * -4 * idle1;
        this.leftArm.xRot += RAD * -4 * idle1;
        this.rightArm.zRot += RAD * 2 * idle2;
        this.leftArm.zRot += RAD * -2 * idle2;

        this.head.yRot += headYaw * RAD * 150;
        this.head.xRot += headPitch * RAD * 180;
        this.chest.yRot += headYaw * RAD * 30;
    }

    @Override
    public void handleKeyframedAnimations(AlgidityEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
//        float easedlimbSwingAmount = Mth.lerp(getPartialTick(), entity.prevRenderLimbSwingAmount, entity.renderLimbSwingAmount);
//        float f = Mth.clamp(easedlimbSwingAmount * 8F, 0, 1);
//        float easedLimbSwing = caculateLimbSwingEasing(entity);
//
//        SEAnimator walk = new SEAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);
//        SEAnimator walk2 = new SEAnimator(this, Mth.clamp(MathUtil.loop(easedLimbSwing, 1, 0.15F), 0, 0.999F)).multiplier(f);
//
//        walk.setupKeyframe(0.0F);
//
//        walk.rotate(chest, 5.0F, 10.0F, 0.0F);
//        walk.rotate(head, 0.0F, -5.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.5F);
//        walk.rotate(chest, 5.0F, -10.0F, 0.0F);
//        walk.rotate(head, 0.0F, 5.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.5F);
//        walk.rotate(chest, 5.0F, 10.0F, 0.0F);
//        walk.rotate(head, 0.0F, -5.0F, 0.0F);
//        walk.apply();
//
//        walk.reset();
//        //
//        //
//
//        walk.setupKeyframe(0.0F);
//        walk.rotate(hip, 7.5F, 0.0F, 0.0F);
//        walk.rotate(rightLeg, -52.5F, 5.0F, 0.0F);
//        walk.rotate(rightCalf, 40.0F, 0.0F, 0.0F);
//        walk.rotate(leftLeg, 30.0F, 5.0F, 0.0F);
//        walk.rotate(leftCalf, 25.0F, 0.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.25F);
//        walk.rotate(hip, 5.0F, 0.0F, 0.0F);
//        walk.rotate(leftLeg, -2.5F, 0.0F, 0.0F);
//        walk.rotate(leftCalf, 50.0F, 0.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.25F);
//        walk.rotate(hip, 7.5F, 0.0F, 0.0F);
//        walk.rotate(rightLeg, 30.0F, -5.0F, 0.0F);
//        walk.rotate(rightCalf, 25.0F, 0.0F, 0.0F);
//        walk.rotate(leftLeg, -52.5F, -5.0F, 0.0F);
//        walk.rotate(leftCalf, 40.0F, 0.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.25F);
//        walk.rotate(hip, 5.0F, 0.0F, 0.0F);
//        walk.rotate(rightLeg, -2.5F, 0.0F, 0.0F);
//        walk.rotate(rightCalf, 50.0F, 0.0F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.25F);
//        walk.rotate(hip, 7.5F, 0.0F, 0.0F);
//        walk.rotate(rightLeg, -52.5F, 5.0F, 0.0F);
//        walk.rotate(rightCalf, 40.0F, 0.0F, 0.0F);
//        walk.rotate(leftLeg, 30.0F, 5.0F, 0.0F);
//        walk.rotate(leftCalf, 25.0F, 0.0F, 0.0F);
//        walk.apply();
//
//        walk.reset();
//        //
//        //
//
//        walk.setupKeyframe(0.0F);
//        walk.move(model, 0.0F, 3.1F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 1.8F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.7F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.1F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, -0.2F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, -0.3F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.2F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 1.4F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 3.1F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 1.8F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.7F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.1F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, -0.2F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, -0.3F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 0.2F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 1.4F, 0.0F);
//        walk.apply();
//
//        walk.setupKeyframe(0.0625F);
//        walk.move(model, 0.0F, 3.1F, 0.0F);
//        walk.apply();
//
//        //
//        //
//
//        walk2.setupKeyframe(0.0F);
//        walk.rotate(body, 0.0F, -5.0F, 0.0F);
//        walk2.rotate(rightArm, 22.5F, -10.0F, 0.0F);
//        walk2.rotate(rightForearm, -15.0F, 0.0F, 0.0F);
//        walk2.rotate(leftArm, -2.5F, 0.0F, 0.0F);
//        walk2.rotate(leftForearm, -20.0F, 0.0F, 0.0F);
//        walk2.apply();
//
//        walk2.setupKeyframe(0.5F);
//        walk.rotate(body, 0.0F, 5.0F, 0.0F);
//        walk2.rotate(rightArm, -2.5F, 0.0F, 0.0F);
//        walk2.rotate(rightForearm, -20.0F, 0.0F, 0.0F);
//        walk2.rotate(leftArm, 22.5F, 10.0F, 0.0F);
//        walk2.rotate(leftForearm, -15.0F, 0.0F, 0.0F);
//        walk2.apply();
//
//        walk2.setupKeyframe(0.5F);
//        walk.rotate(body, 0.0F, -5.0F, 0.0F);
//        walk2.rotate(rightArm, 22.5F, -10.0F, 0.0F);
//        walk2.rotate(rightForearm, -15.0F, 0.0F, 0.0F);
//        walk2.rotate(leftArm, -2.5F, 0.0F, 0.0F);
//        walk2.rotate(leftForearm, -20.0F, 0.0F, 0.0F);
//        walk2.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

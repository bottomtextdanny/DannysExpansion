package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FrozenGhoulEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class FrozenGhoulModel extends BCEntityModel<FrozenGhoulEntity> {
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

    public FrozenGhoulModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -16.0F, 0.0F);
        this.model.addChild(this.body);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.0873F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-4.0F, -4.0F, -2.5F, 8.0F, 4.0F, 5.0F, 0.25F, false);
        this.hip.texOffs(0, 9).addBox(-4.0F, -8.0F, -2.5F, 8.0F, 4.0F, 5.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -8.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.2182F, 0.0F, 0.0F);
        this.chest.texOffs(0, 18).addBox(-6.0F, -8.0F, -3.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
        this.chest.texOffs(0, 42).addBox(0.0F, -12.0F, 3.0F, 0.0F, 14.0F, 8.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -8.0F, -1.0F);
        this.chest.addChild(this.head);
        setRotationAngle(this.head, -0.0873F, 0.0F, 0.0F);
        this.head.texOffs(0, 34).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-6.0F, -5.0F, 1.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.1309F, 0.0F, 0.0F);
        this.rightArm.texOffs(26, 0).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.5F, 8.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        setRotationAngle(this.rightForearm, -0.0436F, 0.0F, 0.0F);
        this.rightForearm.texOffs(38, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(6.0F, -5.0F, 1.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.1309F, 0.0F, 0.0F);
        this.leftArm.texOffs(26, 0).addBox(0.0F, -2.0F, -1.5F, 3.0F, 10.0F, 3.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(1.5F, 8.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        setRotationAngle(this.leftForearm, -0.0436F, 0.0F, 0.0F);
        this.leftForearm.texOffs(38, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, 0.0F, true);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.5F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(50, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 8.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(62, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.05F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.5F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(50, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 8.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(62, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.05F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(FrozenGhoulEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.chest.xRot += RAD * 3 * idle1;
        this.rightArm.xRot += RAD * -4 * idle1;
        this.leftArm.xRot += RAD * -4 * idle1;
        this.rightArm.zRot += RAD * 2 * idle2;
        this.leftArm.zRot += RAD * -2 * idle2;

        this.head.yRot += headYaw * RAD * 0.8;
        this.head.xRot += headPitch;
        this.chest.yRot += headYaw * RAD * 0.2;
    }

    @Override
    public void handleKeyframedAnimations(FrozenGhoulEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.melee0)) {
            animator.disableAtomic(this.walkMult, 4, 6.5F, 4);

            animator.setupKeyframe(6);
            animator.move(this.model, 0.0F, 0.5F, -4.1F);
            animator.rotate(this.rightLeg, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 15.0F, 0.0F, 0.0F);
            animator.move(this.leftArm, 0.0F, 0.0F, -2.0F);
            animator.apply();

            animator.setupKeyframe(3.5F);
            animator.move(this.model, 0.0F, 0.5F, 2.8F);
            animator.rotate(this.rightLeg, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -10.0F, 0.0F, 0.0F);
            animator.move(this.leftArm, 0.0F, 0.0F, -2.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);

            animator.reset();

            animator.setupKeyframe(6);
            animator.rotate(this.hip, -7.5F, 5.0F, 0.0F);
            animator.rotate(this.chest, -15.0F, 30.0F, 0.0F);
            animator.rotate(this.head, 15.0F, -25.0F, 0.0F);
            animator.rotate(this.rightArm, -175.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -160.0F, 30.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(2.0F);
            animator.rotate(this.hip, 22.5F, 2.5F, 0.0F);
            animator.rotate(this.chest, -10.0F, -5.0F, 0.0F);
            animator.rotate(this.head, -5.0F, 5.0F, 0.0F);
            animator.rotate(this.rightArm, -107.5F, 17.5F, 0.0F);
            animator.rotate(this.leftArm, -95.0F, 50.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.hip, 25.0F, -2.5F, 0.0F);
            animator.rotate(this.chest, 25.0F, -42.5F, 0.0F);
            animator.rotate(this.head, -20.0F, 40.0F, 0.0F);
            animator.rotate(this.rightArm, -80.0F, -22.5F, 0.0F);
            animator.rotate(this.leftArm, -75.0F, 5.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);
            
        } else if (entity.mainAnimationHandler.isPlaying(entity.melee1)) {

            animator.setupKeyframe(6);
            animator.rotate(this.hip, -5.0F, 17.5F, 0.0F);
            animator.rotate(this.leftForearm, -12.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.hip, 2.5F, -2.5F, 0.0F);
            animator.rotate(this.leftForearm, -5.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);

            animator.reset();
            //
            //

            animator.setupKeyframe(7);
            animator.rotate(this.chest, 0.0F, 47.5F, 0.0F);
            animator.rotate(this.head, 0.0F, -37.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.rotate(this.chest, 10.0F, -55.0F, 0.0F);
            animator.rotate(this.head, -7.5F, 50.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);

            animator.reset();
            //
            //

            animator.setupKeyframe(8);
            animator.rotate(this.rightForearm, -30.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.rightForearm, -62.5F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(3, Easing.LINEAR);

            animator.reset();
            //
            //

            animator.setupKeyframe(8);
            animator.rotate(this.rightArm, 65.0F, -2.5F, 92.5F);
            animator.apply();

            animator.setupKeyframe(2);
            animator.rotate(this.rightArm, -85.0F, -22.5F, 92.5F);
            animator.apply();

            animator.setupKeyframe(1);
            animator.rotate(this.rightArm, -107.5F, -15.0F, 92.5F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);

            animator.reset();
            //
            //

            animator.setupKeyframe(6);
            animator.rotate(this.leftArm, 27.5F, 10.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(6);
            animator.rotate(this.leftArm, 30.0F, 2.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(3, Easing.LINEAR);
        } else if (entity.mainAnimationHandler.isPlaying(entity.melee2)) {

            animator.setupKeyframe(7);
            animator.rotate(this.hip, -7.5F, 10.0F, 0.0F);
            animator.rotate(this.chest, 5.0F, 40.0F, 0.0F);
            animator.rotate(this.head, 0.0F, -40.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.hip, 12.5F, -2.5F, 0.0F);
            animator.rotate(this.chest, -10.0F, -2.5F, 0.0F);
            animator.rotate(this.head, -5.0F, 15.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.hip, 0.0F, -17.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, -47.5F, 0.0F);
            animator.rotate(this.head, 0.0F, 72.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);

            animator.reset();
            //
            //

            animator.setupKeyframe(8);
            animator.rotate(this.rightArm, -87.5F, 37.5F, 0.0F);
            animator.rotate(this.leftArm, -82.5F, 50.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.rightArm, -87.5F, -52.5F, 0.0F);
            animator.rotate(this.leftArm, -82.5F, -45.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount * 8F, 0, 1) * this.walkMult.floatValue();
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walk = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);

        walk.setupKeyframe(0.0F);
        walk.rotate(this.body, 0.0F, 5.0F, 0.0F);
        walk.rotate(this.head, 0.0F, -2.5F, 0.0F);
        walk.rotate(this.rightArm, -35.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, 20.0F, 0.0F, 0.0F);
        walk.rotate(this.leftForearm, -7.5F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.body, 0.0F, -5.0F, 0.0F);
        walk.rotate(this.head, 0.0F, 2.5F, 0.0F);
        walk.rotate(this.rightArm, 20.0F, 0.0F, 0.0F);
        walk.rotate(this.rightForearm, -7.5F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, -35.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.body, 0.0F, 5.0F, 0.0F);
        walk.rotate(this.head, 0.0F, -2.5F, 0.0F);
        walk.rotate(this.rightArm, -35.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, 20.0F, 0.0F, 0.0F);
        walk.rotate(this.leftForearm, -7.5F, 0.0F, 0.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.rotate(this.hip, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.chest, 5.0F, -2.5F, 0.0F);
        walk.rotate(this.rightLeg, 20.0F, -5.0F, 0.0F);
        walk.rotate(this.rightCalf, 25.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, -40.0F, -5.0F, 0.0F);
        walk.rotate(this.leftCalf, 30.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 2.5F, 0.0F, 0.0F);
        walk.rotate(this.chest, 2.5F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, -7.5F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 40.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.chest, 5.0F, 2.5F, 0.0F);
        walk.rotate(this.rightLeg, -40.0F, 5.0F, 0.0F);
        walk.rotate(this.rightCalf, 30.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, 20.0F, 5.0F, 0.0F);
        walk.rotate(this.leftCalf, 25.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 2.5F, 0.0F, 0.0F);
        walk.rotate(this.chest, 2.5F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 40.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.chest, 5.0F, -2.5F, 0.0F);
        walk.rotate(this.rightLeg, 20.0F, -5.0F, 0.0F);
        walk.rotate(this.rightCalf, 25.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, -40.0F, -5.0F, 0.0F);
        walk.rotate(this.leftCalf, 30.0F, 0.0F, 0.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.move(this.model, 0.0F, 1.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.5F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.3F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.3F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.5F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.9F, 0.0F);
        walk.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

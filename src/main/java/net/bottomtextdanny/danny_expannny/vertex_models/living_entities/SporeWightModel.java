package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporeWightEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class SporeWightModel extends BCEntityModel<SporeWightEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel head;
    private final BCVoxel cap;
    private final BCVoxel leftArm;
    private final BCVoxel rightArm;
    private final BCVoxel rightLeg;
    private final BCVoxel leftLeg;

    public SporeWightModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -11.0F, 0.0F);
        this.model.addChild(this.body);
        setRotationAngle(this.body, 0.0436F, 0.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-4.0F, -11.0F, -2.0F, 8.0F, 11.0F, 4.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -11.0F, 0.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 15).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);

        this.cap = new BCVoxel(this);
        this.cap.setPos(0.0F, -3.0F, 0.0F);
        this.head.addChild(this.cap);
        setRotationAngle(this.cap, 0.0436F, 0.0F, 0.0F);
        this.cap.texOffs(24, 0).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 8.0F, 12.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(4.0F, -8.0F, 0.0F);
        this.body.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.0436F, 0.0F, 0.0F);
        this.leftArm.texOffs(14, 25).addBox(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, true);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-4.0F, -8.0F, 0.0F);
        this.body.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.0436F, 0.0F, 0.0F);
        this.rightArm.texOffs(0, 25).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.0F, -11.0F, 0.0F);
        this.model.addChild(this.rightLeg);
        this.rightLeg.texOffs(0, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.0F, -11.0F, 0.0F);
        this.model.addChild(this.leftLeg);
        this.leftLeg.texOffs(16, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(SporeWightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.xRot = Mth.clamp(headPitch, -70, 70) * RAD;
        this.head.yRot = headYaw * RAD;
    }

    @Override
    public void handleKeyframedAnimations(SporeWightEntity entity, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount / 0.125F, 0, 1);
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walkAnimator = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.attack)) {

            animator.setupKeyframe(6);
            animator.rotate(this.body, -32.5F, 0F, 0F);
            animator.rotate(this.head, 10.0F, 0F, 0F);
            animator.rotate(this.rightArm, -220.0F, 0F, -35.0F);
            animator.rotate(this.leftArm, -220.0F, 0F, 35.0F);
            animator.apply();

            animator.staticKeyframe(2);

            animator.setupKeyframe(4);
            animator.rotate(this.body, 47.5F, 0F, 0F);
            animator.rotate(this.head, 25.0F, 0F, 0F);
            animator.rotate(this.rightArm, -55.0F, -27.5F, 0F);
            animator.rotate(this.leftArm, -55.0F, 27.5F, 0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);
        }

        if (entity.tickAnimation.isWoke()) {
            EntityModelAnimator tickAnimator = new EntityModelAnimator(this, entity.livingModule.getTick() + getPartialTick());

            tickAnimator.setupKeyframe(2);
            tickAnimator.rotate(this.head, 0F, 50.0F, 0F);
            tickAnimator.apply();

            tickAnimator.setupKeyframe(2);
            tickAnimator.rotate(this.head, 0F, -60.0F, 0F);
            tickAnimator.apply();

            tickAnimator.setupKeyframe(2);
            tickAnimator.rotate(this.head, 0F, 70.0F, 0F);
            tickAnimator.apply();

            tickAnimator.setupKeyframe(2);
            tickAnimator.rotate(this.head, 0F, -30.0F, 0F);
            tickAnimator.apply();

            tickAnimator.emptyKeyframe(2, Easing.LINEAR);
        }


        walkAnimator.setupKeyframe(0F);
        walkAnimator.rotate(this.head, 5.0F, 0F, 0F);
        walkAnimator.move(this.rightLeg, 0F, 0F, 0F);
        walkAnimator.rotate(this.rightLeg, -37.5F, 0F, 0F);
        walkAnimator.move(this.leftLeg, 0F, 0F, 1F);
        walkAnimator.rotate(this.leftLeg, 37.5F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.head, -7.5F, 0F, 0F);
        walkAnimator.move(this.rightLeg, 0F, 0F, 0F);
        walkAnimator.rotate(this.rightLeg, 0F, 0F, 0F);
        walkAnimator.move(this.leftLeg, 0F, -1F, 0F);
        walkAnimator.rotate(this.leftLeg, 0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.head, 5.0F, 0F, 0F);
        walkAnimator.move(this.rightLeg, 0F, 0F, 1F);
        walkAnimator.rotate(this.rightLeg, 37.5F, 0F, 0F);
        walkAnimator.move(this.leftLeg, 0F, 0F, 0F);
        walkAnimator.rotate(this.leftLeg, -37.5F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.head, -7.5F, 0F, 0F);
        walkAnimator.move(this.rightLeg, 0F, -1F, 0F);
        walkAnimator.rotate(this.rightLeg, 0F, 0F, 0F);
        walkAnimator.move(this.leftLeg, 0F, 0F, 0F);
        walkAnimator.rotate(this.leftLeg, 0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.head, 5.0F, 0F, 0F);
        walkAnimator.move(this.rightLeg, 0F, 0F, 0F);
        walkAnimator.rotate(this.rightLeg, -37.5F, 0F, 0F);
        walkAnimator.move(this.leftLeg, 0F, 0F, 1F);
        walkAnimator.rotate(this.leftLeg, 37.5F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.reset();
        //

        walkAnimator.setupKeyframe(0F);
        walkAnimator.move(this.model, 0F, 2.2F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.5F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.1F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.5F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 2.2F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.5F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.1F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 0.5F, 2.5F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.125F);
        walkAnimator.move(this.model, 0F, 2.2F, 2.5F);
        walkAnimator.apply();

        walkAnimator.reset();
        walkAnimator.setTimer(DEMath.loop(easedLimbSwing, 1, 0.15F));
        //

        walkAnimator.setupKeyframe(0F);
        walkAnimator.rotate(this.body, -10.0F, 0F, 2.5F);
        walkAnimator.rotate(this.rightArm, 15.0F, 0F, 0F);
        walkAnimator.rotate(this.leftArm, 15.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.body, 25.0F, 0F, 0F);
        walkAnimator.rotate(this.rightArm, -25.0F, 0F, 0F);
        walkAnimator.rotate(this.leftArm, -25.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.body, -10.0F, 0F, -2.5F);
        walkAnimator.rotate(this.rightArm, 15.0F, 0F, 0F);
        walkAnimator.rotate(this.leftArm, 15.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.body, 25.0F, 0F, 0F);
        walkAnimator.rotate(this.rightArm, -25.0F, 0F, 0F);
        walkAnimator.rotate(this.leftArm, -25.0F, 0F, 0F);

        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.body, -10.0F, 0F, 2.5F);
        walkAnimator.rotate(this.rightArm, 15.0F, 0F, 0F);
        walkAnimator.rotate(this.leftArm, 15.0F, 0F, 0F);
        walkAnimator.apply();

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }

}

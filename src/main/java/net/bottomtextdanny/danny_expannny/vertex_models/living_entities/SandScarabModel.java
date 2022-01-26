package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SandScarabEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class SandScarabModel extends BCEntityModel<SandScarabEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel wings;
    private final BCVoxel head;
    private final BCVoxel rightAntenna;
    private final BCVoxel leftAntenna;
    private final BCVoxel rightFrontLeg;
    private final BCVoxel rightMediumLeg;
    private final BCVoxel rightBackLeg;
    private final BCVoxel leftFrontLeg;
    private final BCVoxel leftMiddleLeg;
    private final BCVoxel leftBackLeg;
    private final AtomicDouble livingRotation = new AtomicDouble(1);

    public SandScarabModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -2.5F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 11.0F, 0.0F, false);

        this.wings = new BCVoxel(this);
        this.wings.setPos(0.0F, -1.0F, 0.0F);
        this.body.addChild(this.wings);
        setRotationAngle(this.wings, -0.0436F, 0.0F, 0.0F);
        this.wings.texOffs(0, 15).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 4.0F, 14.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -2.0F, -4.0F);
        this.body.addChild(this.head);
        setRotationAngle(this.head, 0.1309F, 0.0F, 0.0F);
        this.head.texOffs(0, 33).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);

        this.rightAntenna = new BCVoxel(this);
        this.rightAntenna.setPos(-2.0F, -2.0F, -3.0F);
        this.head.addChild(this.rightAntenna);
        setRotationAngle(this.rightAntenna, 0.0F, 0.2618F, 0.0F);
        this.rightAntenna.texOffs(0, 36).addBox(0.0F, -8.0F, -3.0F, 0.0F, 8.0F, 5.0F, 0.0F, false);

        this.leftAntenna = new BCVoxel(this);
        this.leftAntenna.setPos(2.0F, -2.0F, -3.0F);
        this.head.addChild(this.leftAntenna);
        setRotationAngle(this.leftAntenna, 0.0F, -0.2618F, 0.0F);
        this.leftAntenna.texOffs(0, 36).addBox(0.0F, -8.0F, -3.0F, 0.0F, 8.0F, 5.0F, 0.0F, true);

        this.rightFrontLeg = new BCVoxel(this);
        this.rightFrontLeg.setPos(-4.0F, -1.0F, -3.0F);
        this.body.addChild(this.rightFrontLeg);
        setRotationAngle(this.rightFrontLeg, -0.7418F, 1.309F, 0.0F);
        this.rightFrontLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        this.rightMediumLeg = new BCVoxel(this);
        this.rightMediumLeg.setPos(-4.0F, -1.0F, 0.0F);
        this.body.addChild(this.rightMediumLeg);
        setRotationAngle(this.rightMediumLeg, -0.7854F, 1.5708F, 0.0F);
        this.rightMediumLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        this.rightBackLeg = new BCVoxel(this);
        this.rightBackLeg.setPos(-4.0F, -1.0F, 3.0F);
        this.body.addChild(this.rightBackLeg);
        setRotationAngle(this.rightBackLeg, -0.7854F, 2.0508F, 0.0F);
        this.rightBackLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.05F, false);

        this.leftFrontLeg = new BCVoxel(this);
        this.leftFrontLeg.setPos(4.0F, -1.0F, -3.0F);
        this.body.addChild(this.leftFrontLeg);
        setRotationAngle(this.leftFrontLeg, -0.7418F, -1.309F, 0.0F);
        this.leftFrontLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.05F, true);

        this.leftMiddleLeg = new BCVoxel(this);
        this.leftMiddleLeg.setPos(4.0F, -1.0F, 0.0F);
        this.body.addChild(this.leftMiddleLeg);
        setRotationAngle(this.leftMiddleLeg, -0.7854F, -1.5708F, 0.0F);
        this.leftMiddleLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.05F, true);

        this.leftBackLeg = new BCVoxel(this);
        this.leftBackLeg.setPos(4.0F, -1.0F, 3.0F);
        this.body.addChild(this.leftBackLeg);
        setRotationAngle(this.leftBackLeg, -0.7854F, -2.0508F, 0.0F);
        this.leftBackLeg.texOffs(0, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.05F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(SandScarabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.livingRotation.set(1.0);
        float idle0 = DEMath.sin(ageInTicks * 0.05F);
        float idle1 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.rightAntenna.xRot += idle0 * RAD * 3;
        this.leftAntenna.xRot += -idle0 * RAD * 3;
        this.rightAntenna.zRot += idle1 * RAD * 3;
        this.leftAntenna.zRot += -idle1 * RAD * 3;

        this.head.xRot = Mth.clamp(headPitch, -30, 30) * RAD * (float) this.livingRotation.get();
        this.head.yRot = Mth.clamp(headYaw, -30, 30) * RAD * (float) this.livingRotation.get();
    }

    @Override
    public void handleKeyframedAnimations(SandScarabEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.death)) {

            animator.disableAtomic(this.livingRotation, 3, 12, 0);

            animator.setupKeyframe(5);
            animator.move(this.model, 0F, -0.5F, 0F);
            animator.rotate(this.rightFrontLeg, 17.5F, 0F, 0F);
            animator.rotate(this.rightMediumLeg, 17.5F, 0F, 0F);
            animator.rotate(this.rightBackLeg, 17.5F, 0F, 0F);
            animator.rotate(this.leftFrontLeg, 17.5F, 0F, 0F);
            animator.rotate(this.leftMiddleLeg, 17.5F, 0F, 0F);
            animator.rotate(this.leftBackLeg, 17.5F, 0F, 0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.setupKeyframe(6);
            animator.move(this.model, 0F, 1.8F, 0F);
            animator.rotate(this.rightFrontLeg, -30.0F, 0F, 0F);
            animator.rotate(this.rightMediumLeg, -30.0F, 0F, 0F);
            animator.rotate(this.rightBackLeg, -30.0F, 0F, 0F);
            animator.rotate(this.leftFrontLeg, -40.0F, 0F, 0F);
            animator.rotate(this.leftMiddleLeg, -40.0F, 0F, 0F);
            animator.rotate(this.leftBackLeg, -40.0F, 0F, 0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.staticKeyframe(4);

            //
            animator.reset();

            animator.setupKeyframe(8);
            animator.rotate(this.head, -20.0F, 0F, 0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.setupKeyframe(3);
            animator.rotate(this.head, 0F, 0F, -10.0F);
            animator.apply();

            animator.staticKeyframe(4);

            //
            animator.reset();

            animator.setupKeyframe(11);
            animator.rotate(this.body, 0F, 0F, 2.5F);
            animator.rotate(this.rightAntenna, 32.5F, 0F, 0F);
            animator.rotate(this.leftAntenna, 0F, 0F, 17.5F);
            animator.apply();

            animator.staticKeyframe(4);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount / 0.125F, 0, 1);
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walk = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f * (float) this.livingRotation.get());

        walk.setupKeyframe(0F);
        walk.rotate(this.rightFrontLeg, 0F, -30.0F, 0F);
        walk.rotate(this.leftMiddleLeg, 0F, 30.0F, 0F);
        walk.rotate(this.rightBackLeg, 0F, -30.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.rightFrontLeg, 0F, 30.0F, 0F);
        walk.rotate(this.leftMiddleLeg, 0F, -30.0F, 0F);
        walk.rotate(this.rightBackLeg, 0F, 30.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightFrontLeg, -40.0F, 0F, 0F);
        walk.rotate(this.leftMiddleLeg, -40.0F, 0F, 0F);
        walk.rotate(this.rightBackLeg, -40.0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightFrontLeg, 0F, -30.0F, 0F);
        walk.rotate(this.leftMiddleLeg, 0F, 30.0F, 0F);
        walk.rotate(this.rightBackLeg, 0F, -30.0F, 0F);
        walk.apply();

        //
        walk.reset();
        walk.setTimer(DEMath.loop(easedLimbSwing, 1, 0.5F));

        walk.setupKeyframe(0F);
        walk.rotate(this.rightMediumLeg, 0F, -30.0F, 0F);
        walk.rotate(this.leftFrontLeg, 0F, 30.0F, 0F);
        walk.rotate(this.leftBackLeg, 0F, 30.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.rightMediumLeg, 0F, 30.0F, 0F);
        walk.rotate(this.leftFrontLeg, 0F, -30.0F, 0F);
        walk.rotate(this.leftBackLeg, 0F, -30.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightMediumLeg, -40.0F, 0F, 0F);
        walk.rotate(this.leftFrontLeg, -40.0F, 0F, 0F);
        walk.rotate(this.leftBackLeg, -40.0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightMediumLeg, 0F, -30.0F, 0F);
        walk.rotate(this.leftFrontLeg, 0F, 30.0F, 0F);
        walk.rotate(this.leftBackLeg, 0F, 30.0F, 0F);
        walk.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

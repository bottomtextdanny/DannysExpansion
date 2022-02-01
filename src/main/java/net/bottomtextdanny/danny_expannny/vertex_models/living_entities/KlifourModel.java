package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour.KlifourEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class KlifourModel extends BCEntityModel<KlifourEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel headHelper;
    private final BCVoxel head;
    private final BCVoxel flowerHatBase;
    private final BCVoxel leftPetals;
    private final BCVoxel rightPetals;
    private final BCVoxel backPetals;
    private final BCVoxel frontPetals;

    public KlifourModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, -4.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 4.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 32).addBox(-8.0F, -0.2F, -8.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);

        this.headHelper = new BCVoxel(this);
        this.headHelper.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.headHelper);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.headHelper.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.flowerHatBase = new BCVoxel(this);
        this.flowerHatBase.setPos(0.0F, -8.0F, 0.0F);
        this.head.addChild(this.flowerHatBase);
        this.flowerHatBase.texOffs(0, 16).addBox(-6.0F, -0.1F, -6.0F, 12.0F, 0.0F, 12.0F, 0.0F, false);

        this.leftPetals = new BCVoxel(this);
        this.leftPetals.setPos(-4.0F, 0.0F, 0.0F);
        this.flowerHatBase.addChild(this.leftPetals);
        setRotationAngle(this.leftPetals, 0.0F, 1.5708F, -0.5236F);
        this.leftPetals.texOffs(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.rightPetals = new BCVoxel(this);
        this.rightPetals.setPos(4.0F, 0.0F, 0.0F);
        this.flowerHatBase.addChild(this.rightPetals);
        setRotationAngle(this.rightPetals, 0.0F, -1.5708F, 0.5236F);
        this.rightPetals.texOffs(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.backPetals = new BCVoxel(this);
        this.backPetals.setPos(0.0F, 0.0F, -4.0F);
        this.flowerHatBase.addChild(this.backPetals);
        setRotationAngle(this.backPetals, 0.5236F, 0.0F, 0.0F);
        this.backPetals.texOffs(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.frontPetals = new BCVoxel(this);
        this.frontPetals.setPos(0.0F, 0.0F, 4.0F);
        this.flowerHatBase.addChild(this.frontPetals);
        setRotationAngle(this.frontPetals, 0.5236F, 3.1416F, 0.0F);
        this.frontPetals.texOffs(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(KlifourEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle0 = DEMath.sin(ageInTicks * 0.25) - 0.5F;
        float idle1 = DEMath.cos(ageInTicks * 0.25) - 0.5F;

        this.head.xRot += idle0 * RAD * 2.0F;
        this.head.y += idle1 * 0.1F;

        if (entity.isHidden()) {
            this.head.setScale(0.0F, 0.0F, 0.0F);
        }

        this.head.yRot += RAD * headYaw;
        this.head.xRot += RAD * Mth.clamp(headPitch, -30.0F, 30.0F);
    }


    @Override
    public void handleKeyframedAnimations(KlifourEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator0 = new EntityModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

        if (entity.mainHandler.isPlaying(KlifourEntity.SPIT)) {

            animator0.setupKeyframe(4);
            animator0.rotate(this.head, -40.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.5F);
            animator0.rotate(this.head, 32.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(2.5F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5F);
            animator0.rotate(this.frontPetals, 22.5F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, -25.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2F);
            animator0.rotate(this.frontPetals, -60.0F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, 35.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(3.0F, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(KlifourEntity.NAUSEA)) {

            animator0.setupKeyframe(12.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 50.0F);
            animator0.apply(Easing.EASE_OUT_BACK);

            animator0.setupKeyframe(12.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, -50.0F);
            animator0.apply(Easing.EASE_OUT_BACK);

            animator0.setupKeyframe(12.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 50.0F);
            animator0.apply(Easing.EASE_OUT_BACK);

            animator0.setupKeyframe(12.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, -50.0F);
            animator0.apply(Easing.EASE_OUT_BACK);

            animator0.emptyKeyframe(6.0F, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(KlifourEntity.SCRUB)) {

            animator0.setupKeyframe(4.0F);
            animator0.rotate(this.headHelper, 0.0F, 60.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.headHelper, 0.0F, -90.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.headHelper, 0.0F, 120.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.headHelper, 0.0F, -120.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.headHelper, 0.0F, 60.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.headHelper, 0.0F, 10.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(2.0F, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(KlifourEntity.HIDE)) {

            animator0.setupKeyframe(7.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 55.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, -40.0F);
            animator0.apply();

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 40.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(15.0F);
            animator0.scale(this.head, -1.0F, -1.0F, -1.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(KlifourEntity.SHOW_UP)) {

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 40.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, -40.0F);
            animator0.apply();

            animator0.setupKeyframe(7.0F);
            animator0.rotate(this.head, 0.0F, 0.0F, 55.0F);
            animator0.apply();

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(0.0F);
            animator0.scale(this.head, -1.0F, -1.0F, -1.0F);
            animator0.apply();

            animator0.emptyKeyframe(15.0F, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(KlifourEntity.DEATH)) {

            animator0.setupKeyframe(20.0F);
            animator0.rotate(this.head, 60.0F, 0.0F, 0.0F);
            animator0.apply(Easing.BOUNCE_OUT);



            animator0.emptyKeyframe(5.0F, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }




}

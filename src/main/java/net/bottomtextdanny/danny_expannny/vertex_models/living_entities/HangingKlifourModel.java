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

public class HangingKlifourModel extends BCEntityModel<KlifourEntity> {
    private final BCVoxel model;
    private final BCVoxel plantBase;
    private final BCVoxel fisrtStem;
    private final BCVoxel secondStem;
    private final BCVoxel thirdStem;
    private final BCVoxel body;
    private final BCVoxel weedBase;
    private final BCVoxel frontWeed;
    private final BCVoxel rightWeed;
    private final BCVoxel backWeed;
    private final BCVoxel leftWeed;
    private final BCVoxel head;
    private final BCVoxel flowerHatBase;
    private final BCVoxel leftPetals;
    private final BCVoxel rightPetals;
    private final BCVoxel backPetals;
    private final BCVoxel frontPetals;

    public HangingKlifourModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.plantBase = new BCVoxel(this);
        this.plantBase.setPos(0.0F, 0.0F, -8.0F);
        this.model.addChild(this.plantBase);
        this.plantBase.texOffs(0, 33).addBox(-3.0F, -3.0F, 0.1F, 6.0F, 6.0F, 0.0F, 0.0F, false);

        this.fisrtStem = new BCVoxel(this);
        this.fisrtStem.setPos(0.0F, 0.0F, 0.0F);
        this.plantBase.addChild(this.fisrtStem);
        setRotationAngle(this.fisrtStem, -0.3881F, 0.0F, 0.0F);
        this.fisrtStem.texOffs(40, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 11.0F, 0.05F, false);

        this.secondStem = new BCVoxel(this);
        this.secondStem.setPos(0.0F, 0.0F, 10.0F);
        this.fisrtStem.addChild(this.secondStem);
        setRotationAngle(this.secondStem, 2.2702F, 0.0F, 0.0F);
        this.secondStem.texOffs(53, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        this.thirdStem = new BCVoxel(this);
        this.thirdStem.setPos(0.0F, 0.0F, 4.0F);
        this.secondStem.addChild(this.thirdStem);
        setRotationAngle(this.thirdStem, -1.885F, 0.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 0.1182F, 0.1255F);
        this.thirdStem.addChild(this.body);


        this.weedBase = new BCVoxel(this);
        this.weedBase.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.weedBase);


        this.frontWeed = new BCVoxel(this);
        this.frontWeed.setPos(0.0F, 0.0F, -2.0F);
        this.weedBase.addChild(this.frontWeed);
        setRotationAngle(this.frontWeed, 0.3491F, 0.0F, 0.0F);
        this.frontWeed.texOffs(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.rightWeed = new BCVoxel(this);
        this.rightWeed.setPos(2.0F, 0.0F, 0.0F);
        this.weedBase.addChild(this.rightWeed);
        setRotationAngle(this.rightWeed, 0.3491F, -1.5708F, 0.0F);
        this.rightWeed.texOffs(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.backWeed = new BCVoxel(this);
        this.backWeed.setPos(0.0F, 0.0F, 2.0F);
        this.weedBase.addChild(this.backWeed);
        setRotationAngle(this.backWeed, 0.3491F, 3.1416F, 0.0F);
        this.backWeed.texOffs(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.leftWeed = new BCVoxel(this);
        this.leftWeed.setPos(-2.0F, 0.0F, 0.0F);
        this.weedBase.addChild(this.leftWeed);
        setRotationAngle(this.leftWeed, 0.3491F, 1.5708F, 0.0F);
        this.leftWeed.texOffs(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -0.0183F, -0.1002F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-4.0F, -7.9817F, -4.0498F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.flowerHatBase = new BCVoxel(this);
        this.flowerHatBase.setPos(0.0F, -7.9817F, -0.0498F);
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
        super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        float idle0 = DEMath.sin(ageInTicks * 0.25) - 0.5F;
        float idle1 = DEMath.cos(ageInTicks * 0.25) - 0.5F;
        float yawOffset = 0;

        this.fisrtStem.xRot += idle0 * RAD;
        this.secondStem.xRot += idle1 * RAD;

        if (entity.isHidden()) {
            this.fisrtStem.setScale(0.0F, 0.0F, 0.0F);
        }

        switch (entity.getAttachingDirection().get3DDataValue()) {
            case 3: yawOffset = -180; break;
            case 4: yawOffset = 90; break;
            case 5: yawOffset = -90; break;
            default: break;
        }

        this.model.yRot -= RAD * yawOffset;
        this.body.yRot += RAD * yawOffset;

        this.body.yRot += RAD * headYaw;
        this.body.xRot += RAD * Mth.clamp(headPitch, -30, 30);
    }

    @Override
    public void setupAnim(KlifourEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);


    }

    @Override
    public void handleKeyframedAnimations(KlifourEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator0 = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.spit)) {

            animator0.setupKeyframe(4);
            animator0.rotate(this.body, -40.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.5F);
            animator0.rotate(this.body, 32.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(2.5F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5F);
            animator0.rotate(this.frontWeed, -22.5F, 0.0F, 0.0F);
            animator0.rotate(this.backWeed, 27.5F, 0.0F, 0.0F);
            animator0.rotate(this.frontPetals, 22.5F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, -25.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2F);
            animator0.rotate(this.frontWeed, 22.5F, 0.0F, 0.0F);
            animator0.rotate(this.backWeed, -35.0F, 0.0F, 0.0F);
            animator0.rotate(this.frontPetals, -60.0F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, 35.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(3.0F, Easing.LINEAR);

        } else if (entity.mainAnimationHandler.isPlaying(entity.nausea)) {

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

        } else if (entity.mainAnimationHandler.isPlaying(entity.scrub)) {

            animator0.setupKeyframe(4.0F);
            animator0.rotate(this.body, 0.0F, 60.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.body, 0.0F, -90.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.body, 0.0F, 120.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.body, 0.0F, -120.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.body, 0.0F, 60.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.body, 0.0F, 10.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(2.0F, Easing.LINEAR);

        } else if (entity.mainAnimationHandler.isPlaying(entity.hide)) {

            animator0.setupKeyframe(7.0F);
            animator0.rotate(this.fisrtStem, 55.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.fisrtStem, -5.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.fisrtStem, 85.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -167.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -50.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -245.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(4.0F);
            animator0.rotate(this.thirdStem, -22.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(6.0F);
            animator0.rotate(this.thirdStem, 55.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.thirdStem, 72.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(15.0F);
            animator0.scale(this.fisrtStem, -1.0F, -1.0F, -1.0F);
            animator0.apply();

            animator0.emptyKeyframe(0, Easing.LINEAR);

        } else if (entity.mainAnimationHandler.isPlaying(entity.showUp)) {

            animator0.setupKeyframe(3.0F);
            animator0.rotate(this.fisrtStem, 85.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.fisrtStem, -5.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(7.0F);
            animator0.rotate(this.fisrtStem, 55.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -245.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -50.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.secondStem, -167.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.thirdStem, 72.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(6.0F);
            animator0.rotate(this.thirdStem, 55.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(4.0F);
            animator0.rotate(this.thirdStem, -22.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(0.0F);
            animator0.scale(this.fisrtStem, -1.0F, -1.0F, -1.0F);
            animator0.apply();

            animator0.emptyKeyframe(15.0F, Easing.LINEAR);
        }else if (entity.mainAnimationHandler.isPlaying(entity.death)) {

            animator0.setupKeyframe(20.0F);
            animator0.rotate(this.head, 60.0F, 0.0F, 0.0F);
            animator0.apply(Easing.BOUNCE_OUT);



            animator0.emptyKeyframe(5.0F, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

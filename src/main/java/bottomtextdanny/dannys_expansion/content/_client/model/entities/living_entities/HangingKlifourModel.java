package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion.content.entities.mob.klifour.Klifour;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.util.Mth;

public class HangingKlifourModel extends BCEntityModel<Klifour> {
    private final BCJoint model;
    private final BCJoint plantBase;
    private final BCJoint fisrtStem;
    private final BCJoint secondStem;
    private final BCJoint thirdStem;
    private final BCJoint body;
    private final BCJoint weedBase;
    private final BCJoint frontWeed;
    private final BCJoint rightWeed;
    private final BCJoint backWeed;
    private final BCJoint leftWeed;
    private final BCJoint head;
    private final BCJoint flowerHatBase;
    private final BCJoint leftPetals;
    private final BCJoint rightPetals;
    private final BCJoint backPetals;
    private final BCJoint frontPetals;

    public HangingKlifourModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 0.0F, 0.0F);


        this.plantBase = new BCJoint(this);
        this.plantBase.setPosCore(0.0F, 0.0F, -8.0F);
        this.model.addChild(this.plantBase);
        this.plantBase.uvOffset(0, 33).addBox(-3.0F, -3.0F, 0.1F, 6.0F, 6.0F, 0.0F, 0.0F, false);

        this.fisrtStem = new BCJoint(this);
        this.fisrtStem.setPosCore(0.0F, 0.0F, 0.0F);
        this.plantBase.addChild(this.fisrtStem);
        setRotationAngle(this.fisrtStem, -0.3881F, 0.0F, 0.0F);
        this.fisrtStem.uvOffset(40, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 11.0F, 0.05F, false);

        this.secondStem = new BCJoint(this);
        this.secondStem.setPosCore(0.0F, 0.0F, 10.0F);
        this.fisrtStem.addChild(this.secondStem);
        setRotationAngle(this.secondStem, 2.2702F, 0.0F, 0.0F);
        this.secondStem.uvOffset(53, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        this.thirdStem = new BCJoint(this);
        this.thirdStem.setPosCore(0.0F, 0.0F, 4.0F);
        this.secondStem.addChild(this.thirdStem);
        setRotationAngle(this.thirdStem, -1.885F, 0.0F, 0.0F);


        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, 0.1182F, 0.1255F);
        this.thirdStem.addChild(this.body);


        this.weedBase = new BCJoint(this);
        this.weedBase.setPosCore(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.weedBase);


        this.frontWeed = new BCJoint(this);
        this.frontWeed.setPosCore(0.0F, 0.0F, -2.0F);
        this.weedBase.addChild(this.frontWeed);
        setRotationAngle(this.frontWeed, 0.3491F, 0.0F, 0.0F);
        this.frontWeed.uvOffset(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.rightWeed = new BCJoint(this);
        this.rightWeed.setPosCore(2.0F, 0.0F, 0.0F);
        this.weedBase.addChild(this.rightWeed);
        setRotationAngle(this.rightWeed, 0.3491F, -1.5708F, 0.0F);
        this.rightWeed.uvOffset(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.backWeed = new BCJoint(this);
        this.backWeed.setPosCore(0.0F, 0.0F, 2.0F);
        this.weedBase.addChild(this.backWeed);
        setRotationAngle(this.backWeed, 0.3491F, 3.1416F, 0.0F);
        this.backWeed.uvOffset(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.leftWeed = new BCJoint(this);
        this.leftWeed.setPosCore(-2.0F, 0.0F, 0.0F);
        this.weedBase.addChild(this.leftWeed);
        setRotationAngle(this.leftWeed, 0.3491F, 1.5708F, 0.0F);
        this.leftWeed.uvOffset(24, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 5.0F, 0.0F, false);

        this.head = new BCJoint(this);
        this.head.setPosCore(0.0F, -0.0183F, -0.1002F);
        this.body.addChild(this.head);
        this.head.uvOffset(0, 0).addBox(-4.0F, -7.9817F, -4.0498F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.flowerHatBase = new BCJoint(this);
        this.flowerHatBase.setPosCore(0.0F, -7.9817F, -0.0498F);
        this.head.addChild(this.flowerHatBase);
        this.flowerHatBase.uvOffset(0, 16).addBox(-6.0F, -0.1F, -6.0F, 12.0F, 0.0F, 12.0F, 0.0F, false);

        this.leftPetals = new BCJoint(this);
        this.leftPetals.setPosCore(-4.0F, 0.0F, 0.0F);
        this.flowerHatBase.addChild(this.leftPetals);
        setRotationAngle(this.leftPetals, 0.0F, 1.5708F, -0.5236F);
        this.leftPetals.uvOffset(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.rightPetals = new BCJoint(this);
        this.rightPetals.setPosCore(4.0F, 0.0F, 0.0F);
        this.flowerHatBase.addChild(this.rightPetals);
        setRotationAngle(this.rightPetals, 0.0F, -1.5708F, 0.5236F);
        this.rightPetals.uvOffset(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.backPetals = new BCJoint(this);
        this.backPetals.setPosCore(0.0F, 0.0F, -4.0F);
        this.flowerHatBase.addChild(this.backPetals);
        setRotationAngle(this.backPetals, 0.5236F, 0.0F, 0.0F);
        this.backPetals.uvOffset(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        this.frontPetals = new BCJoint(this);
        this.frontPetals.setPosCore(0.0F, 0.0F, 4.0F);
        this.flowerHatBase.addChild(this.frontPetals);
        setRotationAngle(this.frontPetals, 0.5236F, 3.1416F, 0.0F);
        this.frontPetals.uvOffset(0, 28).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 4.0F, 0.0F, false);

        //modelInitEnd
    }

    @Override
    public void handleRotations(Klifour entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle0 = DEMath.sin(ageInTicks * 0.25) - 0.5F;
        float idle1 = DEMath.cos(ageInTicks * 0.25) - 0.5F;
        float yawOffset = 0;

        this.fisrtStem.xRot += idle0 * RAD;
        this.secondStem.xRot += idle1 * RAD;

        if (entity.isHidden()) {
            this.fisrtStem.scaleX = 0.0F;
            this.fisrtStem.scaleY = 0.0F;
            this.fisrtStem.scaleZ = 0.0F;
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
    public void handleKeyframedAnimations(Klifour entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        ModelAnimator animator0 = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

        if (entity.mainHandler.isPlaying(Klifour.SPIT)) {

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
        } else if (entity.mainHandler.isPlaying(Klifour.NAUSEA)) {

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
        } else if (entity.mainHandler.isPlaying(Klifour.SCRUB)) {

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

        } else if (entity.mainHandler.isPlaying(Klifour.DEATH)) {

            animator0.setupKeyframe(20.0F);
            animator0.rotate(this.head, 60.0F, 0.0F, 0.0F);
            animator0.apply(Easing.BOUNCE_OUT);

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);
        }

        animator0.setTimer(entity.hideHandler.linearProgress());

        if (entity.hideHandler.isPlaying(Klifour.HIDE)) {

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
        } else if (entity.hideHandler.isPlaying(Klifour.SHOW_UP)) {

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
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

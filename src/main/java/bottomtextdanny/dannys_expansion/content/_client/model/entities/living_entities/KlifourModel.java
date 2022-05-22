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

public class KlifourModel extends BCEntityModel<Klifour> {
    private final BCJoint model;
    private final BCJoint body;
    private final BCJoint headHelper;
    private final BCJoint head;
    private final BCJoint flowerHatBase;
    private final BCJoint leftPetals;
    private final BCJoint rightPetals;
    private final BCJoint backPetals;
    private final BCJoint frontPetals;

    public KlifourModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, -4.0F, 0.0F);


        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, 4.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.uvOffset(0, 32).addBox(-8.0F, -0.2F, -8.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);

        this.headHelper = new BCJoint(this);
        this.headHelper.setPosCore(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.headHelper);

        this.head = new BCJoint(this);
        this.head.setPosCore(0.0F, 0.0F, 0.0F);
        this.headHelper.addChild(this.head);
        this.head.uvOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.flowerHatBase = new BCJoint(this);
        this.flowerHatBase.setPosCore(0.0F, -8.0F, 0.0F);
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

        this.head.xRot += idle0 * RAD * 2.0F;
        this.head.y += idle1 * 0.1F;

        if (entity.isHidden()) {
            this.head.scaleX = 0.0F;
            this.head.scaleY = 0.0F;
            this.head.scaleZ = 0.0F;
        }

        this.head.yRot += RAD * headYaw;
        this.head.xRot += RAD * Mth.clamp(headPitch, -30.0F, 30.0F);
    }


    @Override
    public void handleKeyframedAnimations(Klifour entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        ModelAnimator animator0 = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

        if (entity.mainHandler.isPlaying(Klifour.SPIT)) {

            animator0.setupKeyframe(4.0F);
            animator0.rotate(this.head, -40.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.5F);
            animator0.rotate(this.head, 32.5F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(2.5F, Easing.LINEAR);

            animator0.reset();

            animator0.setupKeyframe(5.0F);
            animator0.rotate(this.frontPetals, 22.5F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, -25.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.setupKeyframe(2.0F);
            animator0.rotate(this.frontPetals, -60.0F, 0.0F, 0.0F);
            animator0.rotate(this.backPetals, 35.0F, 0.0F, 0.0F);
            animator0.apply();

            animator0.emptyKeyframe(3.0F, Easing.LINEAR);

        } else if (entity.mainHandler.isPlaying(Klifour.SCRUB)) {

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

        } else if (entity.mainHandler.isPlaying(Klifour.DEATH)) {

            animator0.setupKeyframe(20.0F);
            animator0.rotate(this.head, 60.0F, 0.0F, 0.0F);
            animator0.apply(Easing.BOUNCE_OUT);

            animator0.emptyKeyframe(5.0F, Easing.LINEAR);
        }

        animator0.setTimer(entity.hideHandler.linearProgress());

        if (entity.hideHandler.isPlaying(Klifour.HIDE)) {
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

        } else if (entity.hideHandler.isPlaying(Klifour.SHOW_UP)) {
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

        } else if (entity.hideHandler.isPlaying(Klifour.NAUSEA)) {
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

        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }




}

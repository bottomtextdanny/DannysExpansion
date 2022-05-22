package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.ender_beasts;

import bottomtextdanny.dannys_expansion.content.entities.mob._pending.ender_beast.EnderBeastArcherEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.util.Mth;

public class EnderBeastArcherModel extends BCEntityModel<EnderBeastArcherEntity> {
    private final BCJoint model;
    private final BCJoint rightLeg;
    private final BCJoint rightCalf;
    private final BCJoint leftLeg;
    private final BCJoint leftCalf;
    private final BCJoint hip;
    private final BCJoint chest;
    private final BCJoint head;
    private final BCJoint jaw;
    private final BCJoint rightArm;
    private final BCJoint rightShoulderPad;
    private final BCJoint rightForearm;
    private final BCJoint bow;
    private final BCJoint lowerFirst;
    private final BCJoint lowerSecond;
    private final BCJoint lowerThird;
    private final BCJoint lowerFourth;
    private final BCJoint lowerString;
    private final BCJoint higherFirst;
    private final BCJoint higherSecond;
    private final BCJoint higherThird;
    private final BCJoint higherFourth;
    private final BCJoint higherString;
    private final BCJoint leftArm;
    private final BCJoint leftForearm;
    private final BCJoint handArrow;
    private final BCJoint quiver;
    private final BCJoint quiverArrow1;
    private final BCJoint quiverArrow2;
    private final BCJoint quiverArrow3;

    public EnderBeastArcherModel() {
        this.texWidth = 256;
        this.texHeight = 256;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 22.2F, 0.7F);


        this.rightLeg = new BCJoint(this);
        this.rightLeg.setPosCore(-7.5F, -22.0F, 3.5F);
        this.model.addChild(this.rightLeg);
        setRotationAngle(this.rightLeg, -0.3491F, 0.0F, 0.0F);
        this.rightLeg.uvOffset(102, 68).addBox(-3.5F, 0.0F, -3.5F, 8.0F, 13.0F, 8.0F, 0.0F, false);

        this.rightCalf = new BCJoint(this);
        this.rightCalf.setPosCore(0.0F, 12.8F, 0.3F);
        this.rightLeg.addChild(this.rightCalf);
        setRotationAngle(this.rightCalf, 0.3491F, 0.0F, 0.0F);
        this.rightCalf.uvOffset(102, 89).addBox(-3.5F, -0.5F, -3.5F, 8.0F, 12.0F, 8.0F, 0.1F, false);

        this.leftLeg = new BCJoint(this);
        this.leftLeg.setPosCore(7.5F, -22.0F, 3.5F);
        this.model.addChild(this.leftLeg);
        setRotationAngle(this.leftLeg, -0.3491F, 0.0F, 0.0F);
        this.leftLeg.uvOffset(102, 68).addBox(-4.5F, 0.0F, -3.5F, 8.0F, 13.0F, 8.0F, 0.0F, true);

        this.leftCalf = new BCJoint(this);
        this.leftCalf.setPosCore(0.0F, 12.8F, 0.3F);
        this.leftLeg.addChild(this.leftCalf);
        setRotationAngle(this.leftCalf, 0.3491F, 0.0F, 0.0F);
        this.leftCalf.uvOffset(102, 89).addBox(-4.5F, -0.5F, -3.5F, 8.0F, 12.0F, 8.0F, 0.1F, true);

        this.hip = new BCJoint(this);
        this.hip.setPosCore(0.0F, -19.0F, 3.8F);
        this.model.addChild(this.hip);
        setRotationAngle(this.hip, 0.0873F, 0.0F, 0.0F);
        this.hip.uvOffset(0, 0).addBox(-10.0F, -20.0F, -5.5F, 20.0F, 20.0F, 11.0F, 0.0F, false);

        this.chest = new BCJoint(this);
        this.chest.setPosCore(0.0F, -17.0F, 1.5F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.1745F, 0.0F, 0.0F);
        this.chest.uvOffset(62, 0).addBox(-14.0F, -16.0F, -9.0F, 28.0F, 15.0F, 18.0F, 0.0F, false);
        this.chest.uvOffset(62, 33).addBox(-14.5F, -16.5F, -9.5F, 29.0F, 16.0F, 19.0F, 0.0F, false);

        this.head = new BCJoint(this);
        this.head.setPosCore(0.0F, -16.2F, -5.7F);
        this.chest.addChild(this.head);
        this.head.uvOffset(0, 31).addBox(-6.0F, -9.0F, -7.0F, 12.0F, 9.0F, 11.0F, 0.0F, false);

        this.jaw = new BCJoint(this);
        this.jaw.setPosCore(0.0F, -3.0F, 3.0F);
        this.head.addChild(this.jaw);
        this.jaw.uvOffset(0, 51).addBox(-7.0F, -1.0F, -11.0F, 14.0F, 6.0F, 11.0F, 0.0F, false);

        this.rightArm = new BCJoint(this);
        this.rightArm.setPosCore(-14.0F, -8.7F, 0.3F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, 0.0873F, -0.1745F, 0.1309F);
        this.rightArm.uvOffset(0, 83).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 17.0F, 8.0F, 0.0F, false);

        this.rightShoulderPad = new BCJoint(this);
        this.rightShoulderPad.setPosCore(-3.5F, -3.3F, 0.2F);
        this.rightArm.addChild(this.rightShoulderPad);
        setRotationAngle(this.rightShoulderPad, 0.0F, 0.0F, -0.1745F);
        this.rightShoulderPad.uvOffset(47, 102).addBox(-5.5F, -6.0F, -5.0F, 14.0F, 6.0F, 10.0F, 0.0F, false);

        this.rightForearm = new BCJoint(this);
        this.rightForearm.setPosCore(-4.0F, 12.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        setRotationAngle(this.rightForearm, -0.6981F, 0.0F, 0.0F);
        this.rightForearm.uvOffset(0, 108).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 18.0F, 8.0F, 0.1F, false);

        this.bow = new BCJoint(this);
        this.bow.setPosCore(4.5F, 17.5F, 0.0F);
        this.rightForearm.addChild(this.bow);
        this.bow.uvOffset(32, 118).addBox(-1.0F, -0.5F, -3.5F, 2.0F, 1.0F, 7.0F, 0.0F, false);

        this.lowerFirst = new BCJoint(this);
        this.lowerFirst.setPosCore(0.0F, 0.0F, 3.5F);
        this.bow.addChild(this.lowerFirst);
        setRotationAngle(this.lowerFirst, 0.1745F, 0.0F, 0.0F);
        this.lowerFirst.uvOffset(50, 118).addBox(-2.5F, -1.0F, -0.5F, 5.0F, 3.0F, 5.0F, 0.0F, false);

        this.lowerSecond = new BCJoint(this);
        this.lowerSecond.setPosCore(0.0F, 0.5F, 4.5F);
        this.lowerFirst.addChild(this.lowerSecond);
        setRotationAngle(this.lowerSecond, 0.2182F, 0.0F, 0.0F);
        this.lowerSecond.uvOffset(70, 118).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 9.0F, 0.0F, false);

        this.lowerThird = new BCJoint(this);
        this.lowerThird.setPosCore(0.0F, 0.0F, 8.0F);
        this.lowerSecond.addChild(this.lowerThird);
        setRotationAngle(this.lowerThird, 0.2618F, 0.0F, 0.0F);
        this.lowerThird.uvOffset(72, 136).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 8.0F, 0.0F, false);

        this.lowerFourth = new BCJoint(this);
        this.lowerFourth.setPosCore(0.0F, 0.0F, 7.0F);
        this.lowerThird.addChild(this.lowerFourth);
        setRotationAngle(this.lowerFourth, 0.3491F, 0.0F, 0.0F);
        this.lowerFourth.uvOffset(92, 136).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);

        this.lowerString = new BCJoint(this);
        this.lowerString.setPosCore(0.0F, -1.5F, 2.5F);
        this.lowerFourth.addChild(this.lowerString);
        setRotationAngle(this.lowerString, 0.5672F, 0.0F, 0.0F);
        this.lowerString.uvOffset(24, 159).addBox(-0.5F, -21.25F, -0.5F, 1.0F, 22.0F, 1.0F, 0.0F, false);

        this.higherFirst = new BCJoint(this);
        this.higherFirst.setPosCore(0.0F, 0.0F, -3.5F);
        this.bow.addChild(this.higherFirst);
        setRotationAngle(this.higherFirst, -0.1745F, 0.0F, 0.0F);
        this.higherFirst.uvOffset(28, 137).addBox(-2.5F, -1.0F, -4.5F, 5.0F, 3.0F, 5.0F, 0.0F, false);

        this.higherSecond = new BCJoint(this);
        this.higherSecond.setPosCore(0.0F, 0.5F, -4.5F);
        this.higherFirst.addChild(this.higherSecond);
        setRotationAngle(this.higherSecond, -0.2182F, 0.0F, 0.0F);
        this.higherSecond.uvOffset(48, 137).addBox(-1.5F, -1.0F, -8.0F, 3.0F, 2.0F, 9.0F, 0.0F, false);

        this.higherThird = new BCJoint(this);
        this.higherThird.setPosCore(0.0F, 0.0F, -8.0F);
        this.higherSecond.addChild(this.higherThird);
        setRotationAngle(this.higherThird, -0.2618F, 0.0F, 0.0F);
        this.higherThird.uvOffset(32, 127).addBox(-1.0F, -0.5F, -7.0F, 2.0F, 1.0F, 8.0F, 0.0F, false);

        this.higherFourth = new BCJoint(this);
        this.higherFourth.setPosCore(0.0F, 0.0F, -7.0F);
        this.higherThird.addChild(this.higherFourth);
        setRotationAngle(this.higherFourth, -0.3491F, 0.0F, 0.0F);
        this.higherFourth.uvOffset(53, 127).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);

        this.higherString = new BCJoint(this);
        this.higherString.setPosCore(0.0F, -1.5F, -2.5F);
        this.higherFourth.addChild(this.higherString);
        setRotationAngle(this.higherString, -0.5672F, 0.0F, 0.0F);
        this.higherString.uvOffset(24, 136).addBox(-0.5F, -21.25F, -0.5F, 1.0F, 22.0F, 1.0F, 0.0F, false);

        this.leftArm = new BCJoint(this);
        this.leftArm.setPosCore(14.0F, -8.7F, 0.3F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, 0.0873F, 0.1745F, -0.1309F);
        this.leftArm.uvOffset(0, 83).addBox(0.0F, -4.0F, -4.0F, 8.0F, 17.0F, 8.0F, 0.0F, true);

        this.leftForearm = new BCJoint(this);
        this.leftForearm.setPosCore(4.0F, 12.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        setRotationAngle(this.leftForearm, -0.6981F, 0.0F, 0.0F);
        this.leftForearm.uvOffset(0, 108).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 18.0F, 8.0F, 0.1F, true);

        this.handArrow = new BCJoint(this);
        this.handArrow.setPosCore(0.0F, 17.5F, 0.0F);
        this.leftForearm.addChild(this.handArrow);
        this.handArrow.uvOffset(0, 137).addBox(-2.5F, 1.5F, -0.5F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        this.handArrow.uvOffset(4, 132).addBox(-2.0F, -2.5F, -2.5F, 0.0F, 26.0F, 5.0F, 0.0F, false);
        this.handArrow.uvOffset(9, 137).addBox(-4.5F, -2.5F, 0.0F, 5.0F, 26.0F, 0.0F, 0.0F, false);

        this.quiver = new BCJoint(this);
        this.quiver.setPosCore(2.0F, -6.0F, 9.0F);
        this.chest.addChild(this.quiver);
        setRotationAngle(this.quiver, 0.0F, 0.0F, 0.6109F);
        this.quiver.uvOffset(47, 69).addBox(-5.0F, -14.0F, 0.25F, 10.0F, 26.0F, 7.0F, 0.0F, false);
        this.quiver.uvOffset(75, 68).addBox(-5.0F, -5.0F, 0.25F, 10.0F, 0.0F, 7.0F, 0.0F, false);

        this.quiverArrow1 = new BCJoint(this);
        this.quiverArrow1.setPosCore(0.0F, 13.0F, 4.0F);
        this.quiver.addChild(this.quiverArrow1);
        setRotationAngle(this.quiverArrow1, 0.0F, 0.5236F, 0.0873F);
        this.quiverArrow1.uvOffset(0, 137).addBox(0.5F, -31.0F, -1.5F, 1.0F, 20.0F, 1.0F, 0.0F, false);
        this.quiverArrow1.uvOffset(4, 132).addBox(1.0F, -35.0F, -3.5F, 0.0F, 27.0F, 5.0F, 0.0F, false);
        this.quiverArrow1.uvOffset(9, 137).addBox(-1.5F, -35.0F, -1.0F, 5.0F, 27.0F, 0.0F, 0.0F, false);

        this.quiverArrow2 = new BCJoint(this);
        this.quiverArrow2.setPosCore(0.0F, 13.0F, 4.0F);
        this.quiver.addChild(this.quiverArrow2);
        setRotationAngle(this.quiverArrow2, -0.0873F, 1.0472F, 0.0873F);
        this.quiverArrow2.uvOffset(0, 137).addBox(-2.5F, -31.0F, -3.5F, 1.0F, 20.0F, 1.0F, 0.0F, false);
        this.quiverArrow2.uvOffset(4, 132).addBox(-2.0F, -35.0F, -5.5F, 0.0F, 27.0F, 5.0F, 0.0F, false);
        this.quiverArrow2.uvOffset(9, 137).addBox(-4.5F, -35.0F, -3.0F, 5.0F, 27.0F, 0.0F, 0.0F, false);

        this.quiverArrow3 = new BCJoint(this);
        this.quiverArrow3.setPosCore(-2.0F, 13.0F, 4.0F);
        this.quiver.addChild(this.quiverArrow3);
        setRotationAngle(this.quiverArrow3, -0.0873F, 1.4835F, 0.0F);
        this.quiverArrow3.uvOffset(0, 137).addBox(-0.5F, -31.0F, -1.5F, 1.0F, 20.0F, 1.0F, 0.0F, false);
        this.quiverArrow3.uvOffset(4, 132).addBox(0.0F, -35.0F, -3.5F, 0.0F, 27.0F, 5.0F, 0.0F, false);
        this.quiverArrow3.uvOffset(9, 137).addBox(-2.5F, -35.0F, -1.0F, 5.0F, 27.0F, 0.0F, 0.0F, false);

        //modelInitEnd
    }

    @Override
    public void setupAnim(EnderBeastArcherEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.handArrow.visible = false;
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.higherString.setScale(0.5F, 1.0F, 0.5F);
        this.lowerString.setScale(0.5F, 1.0F, 0.5F);

        this.head.yRot += netHeadYaw * (RAD / 5 * 4);
        this.head.xRot += headPitch * RAD;
        this.chest.yRot += netHeadYaw * (RAD / 5);

//        walkOffsetY(this.model, this.globalSpeed, 2.0F, 0, 4.75F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.hip, this.globalSpeed, 1.5F, 0, 1.5F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.chest, this.globalSpeed, 2.5F, 0.1F, 2.5F, limbSwing, limbSwingAmount, false);
//        walkRotateY(this.chest, this.globalSpeed / 2, -2.5F, 0.0F, 0F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.head, this.globalSpeed, -4.0F, 0, -4.0F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.rightArm, this.globalSpeed / 2, -22.5F, 0, 0.0F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.leftArm, this.globalSpeed / 2, -22.5F, 0, 0.0F, limbSwing, limbSwingAmount, true);
//        walkRotateX(this.rightLeg, this.globalSpeed / 2, 30.0F, 0, 2.5F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.leftLeg, this.globalSpeed / 2, 30.0F, 0, 2.5F, limbSwing, limbSwingAmount, true);
//        walkRotateX(this.rightCalf, this.globalSpeed / 2, 8.75F, 0.0F, 39.25F, limbSwing, limbSwingAmount, false);
//        walkRotateX(this.leftCalf, this.globalSpeed / 2, 8.75F, 0.0F, 39.25F, limbSwing, limbSwingAmount, true);

    }

    @Override
    public void handleKeyframedAnimations(EnderBeastArcherEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        if (!entity.jawModule.isPlayingNull()) {
            float jawTime = (float)entity.jawModule.getAnimation().getDuration() / 2.0F;
            ModelAnimator ambientAnimator = new ModelAnimator(this, entity.jawModule.linearProgress());

            ambientAnimator.setupKeyframe(jawTime);
            ambientAnimator.rotate(this.jaw, 10.0F, 0.0F, 0.0F);
            ambientAnimator.apply();

            ambientAnimator.emptyKeyframe(jawTime, Easing.LINEAR);
        }

        if (entity.mainHandler.isPlaying(EnderBeastArcherEntity.SHOT)) {
            ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            ModelAnimator bowAnimator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

           // animator.disableAtomic(this.walkMult, 5, 28, 5, entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(10);
            animator.rotate(this.chest, -17.5F, -7.5F, -7.5F);
            animator.rotate(this.head, 7.5F, 7.5F, 7.5F);
            animator.rotate(this.rightArm, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -165.0F, -42.5F, 5.0F);
            animator.rotate(this.leftForearm, -62.5F, 0.0F, 0.0F);
            animator.rotate(this.handArrow, -82.5F, 17.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(8);
            animator.rotate(this.model, 0.0F, -20.0F, 0.0F);
            animator.rotate(this.hip, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -17.5F + headPitch, -7.5F + headYaw / 5 * 4, 0.0F);
            animator.rotate(this.head, 7.5F - headPitch, 25.0F - headYaw / 5 * 4, 5.0F);
            animator.rotate(this.rightArm, -87.5F, 7.5F, 0.0F);
            animator.rotate(this.rightForearm, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.bow, 0.0F, 0.0F, 27.5F);
            animator.rotate(this.leftArm, -95.0F, 42.5F, 0.0F);
            animator.rotate(this.leftForearm, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.handArrow, 2.5F, 0.0F, -37.5F);
            animator.rotate(this.rightLeg, 0.0F, 20.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(10);
            animator.rotate(this.model, 0.0F, -57.5F, 0.0F);
            animator.rotate(this.chest, -22.5F, -12.5F + headYaw / 5 * 4, 0.0F - headPitch);
            animator.rotate(this.head, 7.5F - headPitch, 60.0F - headYaw / 5 * 4, 2.5F);
            animator.rotate(this.rightArm, -87.5F, 60.0F, -2.5F);
            animator.rotate(this.rightForearm, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.bow, 0.0F, 0.0F, 22.5F);
            animator.move(this.leftArm, -2.0F, 0.0F, -7.0F);
            animator.rotate(this.leftArm, -92.5F, 70.0F, 7.5F);
            animator.rotate(this.leftForearm, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.handArrow, 0.0F, 0.0F, -10.0F);
            animator.rotate(this.rightLeg, 0.0F, 57.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);

            //


            bowAnimator.staticKeyframe(18);

            bowAnimator.setupKeyframe(10);
            bowAnimator.rotate(this.higherFirst, -5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.higherSecond, -5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.higherThird, -5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.higherString, 40.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.lowerFirst, 5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.lowerSecond, 5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.lowerThird, 5.0F, 0.0F, 0.0F);
            bowAnimator.rotate(this.lowerString, -40.0F, 0.0F, 0.0F);
            bowAnimator.apply();

            bowAnimator.emptyKeyframe(6, Easing.BOUNCE_OUT);

            //
            animator.reset();

            animator.staticKeyframe(10);

            animator.setupKeyframe(4);
            animator.move(this.model, 0.5F, 0.0F, 1.4F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.move(this.model, 0.8F, 0.0F, 2.8F);
            animator.apply();

            animator.setupKeyframe(5);
            animator.move(this.model, 0.5F, 0.0F, 5.6F);
            animator.apply();

            animator.setupKeyframe(5);
            animator.move(this.model, -0.6F, 0.0F, 8.0F);
            animator.apply();

            animator.setupKeyframe(5);
            animator.move(this.model, 0.6F, 0.0F, 4.0F);
            animator.apply();

            animator.emptyKeyframe(5, Easing.LINEAR);

            if (entity.mainHandler.getTick() >= 10 && entity.mainHandler.getTick() < 28) {
                this.handArrow.visible = true;
            }
        }

        if (entity.mainHandler.isPlaying(EnderBeastArcherEntity.PUNCH)) {
            ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            float headRotX = headPitch;
            float clampedHeadRotX = Mth.clamp(headPitch, -90, 50);

            animator.setupKeyframe(8);
            animator.rotate(this.model, 0.0F, -22.5F, 0.0F);
            animator.rotate(this.chest, 0.0F + clampedHeadRotX, -32.5F, 0.0F);
            animator.rotate(this.head, 0.0F - headRotX, 55F, 0.0F);
            animator.rotate(this.leftArm, 57.5F, 0.0F, -75.0F);
            animator.rotate(this.leftForearm, -75.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, 22.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(2);
            animator.rotate(this.model, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 2.5F + clampedHeadRotX, 37.5F, 0.0F);
            animator.rotate(this.head, -7.5F - headRotX, -52.5F, 0.0F);
            animator.rotate(this.rightArm, 30.0F, 0.0F, 0.0F);
            animator.move(this.leftArm, 3.0F, 0.0F, -5.0F);
            animator.rotate(this.leftArm, -117.5F, 0.0F, -75.0F);
            animator.rotate(this.leftForearm, -12.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 12.5F, -25.0F, 0.0F);
            animator.rotate(this.rightCalf, -12.5F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);

            ///////////////////////////////////////////////////////////
            animator.reset();

            animator.setupKeyframe(4);
            animator.move(this.model, 0.35F, 0.0F, 1.6F);
            animator.rotate(this.leftForearm, -37.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, 40.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.move(this.model, 0.7F, 0.0F, 3.2F);
            animator.rotate(this.leftForearm, -75.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 27.5F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, -2.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1);
            animator.move(this.model, -0.1F, -0.2F, -1.1F);
            animator.rotate(this.leftLeg, 35.0F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, 7.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1);
            animator.move(this.model, -2.1F, -0.4F, -5.5F);
            animator.rotate(this.leftLeg, 25.0F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, -10.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}


package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion.content.entities.mob.ice_elemental.IceElemental;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class IceElementalModel extends BCEntityModel<IceElemental> {
    private final BCJoint model;
    private final BCJoint body;
    private final BCJoint bodyZRotator;
    private final BCJoint wingOne;
    private final BCJoint wingOneRotator;
    private final BCJoint wingTwo;
    private final BCJoint wingTwoRotator;
    private final BCJoint wingThree;
    private final BCJoint wingThreeRotator;

    public IceElementalModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 24.0F, 0.0F);


        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, -5.0F, 0.0F);
        this.model.addChild(this.body);


        this.bodyZRotator = new BCJoint(this);
        this.bodyZRotator.setPosCore(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.bodyZRotator);
        this.bodyZRotator.uvOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.wingOne = new BCJoint(this);
        this.wingOne.setPosCore(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingOne);
        setRotationAngle(this.wingOne, 0.0F, 0.0F, -2.0944F);


        this.wingOneRotator = new BCJoint(this);
        this.wingOneRotator.setPosCore(0.0F, -7.0F, 0.0F);
        this.wingOne.addChild(this.wingOneRotator);
        this.wingOneRotator.uvOffset(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingOneRotator.uvOffset(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);

        this.wingTwo = new BCJoint(this);
        this.wingTwo.setPosCore(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingTwo);


        this.wingTwoRotator = new BCJoint(this);
        this.wingTwoRotator.setPosCore(0.0F, -7.0F, 0.0F);
        this.wingTwo.addChild(this.wingTwoRotator);
        this.wingTwoRotator.uvOffset(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingTwoRotator.uvOffset(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);

        this.wingThree = new BCJoint(this);
        this.wingThree.setPosCore(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingThree);
        setRotationAngle(this.wingThree, 0.0F, 0.0F, 2.0944F);


        this.wingThreeRotator = new BCJoint(this);
        this.wingThreeRotator.setPosCore(0.0F, -7.0F, 0.0F);
        this.wingThree.addChild(this.wingThreeRotator);
        this.wingThreeRotator.uvOffset(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingThreeRotator.uvOffset(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);
        //modelInitEnd
    }

    @Override
    public void handleRotations(IceElemental entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float wingRotationCycle = BCMath.sin(ageInTicks * 0.025F);
        float bodyRotationSin = BCMath.sin(ageInTicks * 0.1F);
        float bodyRotationCos = BCMath.cos(ageInTicks * 0.1F);

        this.body.yRot = headYaw * RAD;
        this.body.xRot += headPitch * RAD;
        this.body.xRot += bodyRotationSin * 5.0F * RAD;
        this.body.zRot += bodyRotationCos * 5.0F * RAD;
        this.wingOne.zRot += BCMath.FPI_BY_TWO * wingRotationCycle;
        this.wingTwo.zRot += BCMath.FPI_BY_TWO * wingRotationCycle;
        this.wingThree.zRot += BCMath.FPI_BY_TWO * wingRotationCycle;
    }

    @Override
    public void handleKeyframedAnimations(IceElemental entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.mainHandler.isPlaying(IceElemental.ICE_SPIKE_ANIMATION)) {
            ModelAnimator attackAnimator = new ModelAnimator(this, entity.mainHandler.linearProgress());

            float wingRot = 45.0F * entity.spikeAnimationRotationMultiplier;
            attackAnimator.setupKeyframe(10.0F);
            attackAnimator.rotate(this.wingOne, 0.0F, 0.0F, wingRot);
            attackAnimator.rotate(this.wingTwo, 0.0F, 0.0F, wingRot);
            attackAnimator.rotate(this.wingThree, 0.0F, 0.0F, wingRot);
            attackAnimator.move(this.wingOneRotator, 0.0F, 3.0F, 0.0F);
            attackAnimator.rotate(this.wingOneRotator, -25.0F, 0.0F, 0.0F);
            attackAnimator.move(this.wingTwoRotator, 0.0F, 3.0F, 0.0F);
            attackAnimator.rotate(this.wingTwoRotator, -25.0F, 0.0F, 0.0F);
            attackAnimator.move(this.wingThreeRotator, 0.0F, 3.0F, 0.0F);
            attackAnimator.rotate(this.wingThreeRotator, -25.0F, 0.0F, 0.0F);
            attackAnimator.apply();

            attackAnimator.staticKeyframe(0.4F);

            wingRot = -30.0F * entity.spikeAnimationRotationMultiplier;
            attackAnimator.setupKeyframe(1.2F);
            attackAnimator.rotate(this.wingOne, 0.0F, 0.0F, wingRot);
            attackAnimator.rotate(this.wingTwo, 0.0F, 0.0F, wingRot);
            attackAnimator.rotate(this.wingThree, 0.0F, 0.0F, wingRot);
            attackAnimator.move(this.wingOneRotator, 0.0F, -4.0F, 0.0F);
            attackAnimator.rotate(this.wingOneRotator, 30.0F, 0.0F, 0.0F);
            attackAnimator.move(this.wingTwoRotator, 0.0F, -4.0F, 0.0F);
            attackAnimator.rotate(this.wingTwoRotator, 30.0F, 0.0F, 0.0F);
            attackAnimator.move(this.wingThreeRotator, 0.0F, -4.0F, 0.0F);
            attackAnimator.rotate(this.wingThreeRotator, 30.0F, 0.0F, 0.0F);
            attackAnimator.apply();

            attackAnimator.staticKeyframe(0.8F);

            attackAnimator.emptyKeyframe(8.0F);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

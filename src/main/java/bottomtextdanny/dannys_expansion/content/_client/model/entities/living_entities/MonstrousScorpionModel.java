package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion.MonstrousScorpion;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.util.Mth;

public class MonstrousScorpionModel extends BCEntityModel<MonstrousScorpion> {
	private final BCJoint model;
	private final BCJoint body;
	private final BCJoint leftLegBack;
	private final BCJoint rightLegBack;
	private final BCJoint leftLegMid;
	private final BCJoint rightLegMid;
	private final BCJoint leftLegFront;
	private final BCJoint rightLegFront;
	private final BCJoint leftLegVeryFront;
	private final BCJoint rightLegVeryFront;
	private final BCJoint leftArm;
	private final BCJoint leftPincerTop;
	private final BCJoint leftPincerBottom;
	private final BCJoint rightArm;
	private final BCJoint rightPincerTop;
	private final BCJoint rightPincerBottom;
	private final BCJoint leftFang;
	private final BCJoint rightFang;
	private final BCJoint tail1;
	private final BCJoint tail2;
	private final BCJoint stinger;
	
	public MonstrousScorpionModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 24.0F, 0.0F);


        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, -8.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.uvOffset(0, 0).addBox(-8.0F, -5.0F, -15.0F, 16.0F, 11.0F, 27.0F, 0.0F, false);

        this.leftLegBack = new BCJoint(this);
        this.leftLegBack.setPosCore(8.0F, 4.0F, 6.0F);
        this.body.addChild(this.leftLegBack);
		setRotationAngle(this.leftLegBack, 0.0F, -0.829F, 0.0F);
        this.leftLegBack.uvOffset(65, 39).addBox(0.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, false);

        this.rightLegBack = new BCJoint(this);
        this.rightLegBack.setPosCore(-8.0F, 4.0F, 6.0F);
        this.body.addChild(this.rightLegBack);
		setRotationAngle(this.rightLegBack, 0.0F, 0.829F, 0.0F);
        this.rightLegBack.uvOffset(65, 39).addBox(-16.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, true);

        this.leftLegMid = new BCJoint(this);
        this.leftLegMid.setPosCore(8.0F, 4.0F, 2.0F);
        this.body.addChild(this.leftLegMid);
		setRotationAngle(this.leftLegMid, -0.3491F, -0.6109F, 0.0F);
        this.leftLegMid.uvOffset(65, 39).addBox(0.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, false);

        this.rightLegMid = new BCJoint(this);
        this.rightLegMid.setPosCore(-8.0F, 4.0F, 2.0F);
        this.body.addChild(this.rightLegMid);
		setRotationAngle(this.rightLegMid, -0.3491F, 0.6109F, 0.0F);
        this.rightLegMid.uvOffset(65, 39).addBox(-16.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, true);

        this.leftLegFront = new BCJoint(this);
        this.leftLegFront.setPosCore(8.0F, 4.0F, -3.0F);
        this.body.addChild(this.leftLegFront);
		setRotationAngle(this.leftLegFront, -0.3491F, -0.2618F, 0.0F);
        this.leftLegFront.uvOffset(65, 39).addBox(0.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, false);

        this.rightLegFront = new BCJoint(this);
        this.rightLegFront.setPosCore(-8.0F, 4.0F, -3.0F);
        this.body.addChild(this.rightLegFront);
		setRotationAngle(this.rightLegFront, -0.3491F, 0.2618F, 0.0F);
        this.rightLegFront.uvOffset(65, 39).addBox(-16.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, true);

        this.leftLegVeryFront = new BCJoint(this);
        this.leftLegVeryFront.setPosCore(7.0F, 4.0F, -6.0F);
        this.body.addChild(this.leftLegVeryFront);
		setRotationAngle(this.leftLegVeryFront, -1.117F, 0.4712F, 0.2269F);
        this.leftLegVeryFront.uvOffset(67, 59).addBox(0.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, false);

        this.rightLegVeryFront = new BCJoint(this);
        this.rightLegVeryFront.setPosCore(-7.0F, 4.0F, -6.0F);
        this.body.addChild(this.rightLegVeryFront);
		setRotationAngle(this.rightLegVeryFront, -1.117F, -0.4712F, -0.2269F);
        this.rightLegVeryFront.uvOffset(67, 59).addBox(-16.0F, -9.0F, 0.0F, 16.0F, 13.0F, 0.0F, 0.0F, true);

        this.leftArm = new BCJoint(this);
        this.leftArm.setPosCore(10.0F, 4.0F, -11.0F);
        this.body.addChild(this.leftArm);
		setRotationAngle(this.leftArm, 0.0F, -0.5236F, 0.0F);
        this.leftArm.uvOffset(32, 59).addBox(-4.0F, -5.0F, -8.0F, 6.0F, 8.0F, 11.0F, 0.0F, false);

        this.leftPincerTop = new BCJoint(this);
        this.leftPincerTop.setPosCore(-1.0F, 0.0F, -8.0F);
        this.leftArm.addChild(this.leftPincerTop);
		setRotationAngle(this.leftPincerTop, 0.0F, 1.1345F, 0.0F);
        this.leftPincerTop.uvOffset(43, 39).addBox(-1.5F, -4.0F, -13.0F, 3.0F, 4.0F, 15.0F, 0.0F, false);

        this.leftPincerBottom = new BCJoint(this);
        this.leftPincerBottom.setPosCore(0.0F, -1.0F, 0.3F);
        this.leftPincerTop.addChild(this.leftPincerBottom);
		setRotationAngle(this.leftPincerBottom, -0.0873F, 0.0F, 0.0F);
        this.leftPincerBottom.uvOffset(60, 0).addBox(-1.0F, 0.0F, -13.0F, 2.0F, 4.0F, 15.0F, 0.0F, false);

        this.rightArm = new BCJoint(this);
        this.rightArm.setPosCore(-10.0F, 4.0F, -11.0F);
        this.body.addChild(this.rightArm);
		setRotationAngle(this.rightArm, 0.0F, 0.5236F, 0.0F);
        this.rightArm.uvOffset(32, 59).addBox(-2.0F, -5.0F, -8.0F, 6.0F, 8.0F, 11.0F, 0.0F, true);

        this.rightPincerTop = new BCJoint(this);
        this.rightPincerTop.setPosCore(1.0F, 0.0F, -8.0F);
        this.rightArm.addChild(this.rightPincerTop);
		setRotationAngle(this.rightPincerTop, 0.0F, -1.1345F, 0.0F);
        this.rightPincerTop.uvOffset(43, 39).addBox(-1.5F, -4.0F, -13.0F, 3.0F, 4.0F, 15.0F, 0.0F, true);

        this.rightPincerBottom = new BCJoint(this);
        this.rightPincerBottom.setPosCore(0.0F, -1.0F, 0.3F);
        this.rightPincerTop.addChild(this.rightPincerBottom);
		setRotationAngle(this.rightPincerBottom, -0.0873F, 0.0F, 0.0F);
        this.rightPincerBottom.uvOffset(60, 0).addBox(-1.0F, 0.0F, -13.0F, 2.0F, 4.0F, 15.0F, 0.0F, true);

        this.leftFang = new BCJoint(this);
        this.leftFang.setPosCore(4.0F, 3.0F, -15.0F);
        this.body.addChild(this.leftFang);
		setRotationAngle(this.leftFang, 0.0F, 0.0F, 0.9599F);
        this.leftFang.uvOffset(0, 39).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 6.0F, 2.0F, 0.0F, false);

        this.rightFang = new BCJoint(this);
        this.rightFang.setPosCore(-4.0F, 3.0F, -15.0F);
        this.body.addChild(this.rightFang);
		setRotationAngle(this.rightFang, 0.0F, 0.0F, -0.9599F);
        this.rightFang.uvOffset(0, 39).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 6.0F, 2.0F, 0.0F, true);

        this.tail1 = new BCJoint(this);
        this.tail1.setPosCore(0.0F, -1.0F, 13.0F);
        this.body.addChild(this.tail1);
		setRotationAngle(this.tail1, -0.0436F, 0.0F, 0.0F);
        this.tail1.uvOffset(0, 0).addBox(-3.0F, -18.0F, -1.0F, 6.0F, 20.0F, 6.0F, 0.0F, false);

        this.tail2 = new BCJoint(this);
        this.tail2.setPosCore(0.0F, -18.0F, 2.0F);
        this.tail1.addChild(this.tail2);
        this.tail2.uvOffset(0, 39).addBox(-2.0F, -4.0F, -14.0F, 4.0F, 4.0F, 17.0F, 0.0F, false);

        this.stinger = new BCJoint(this);
        this.stinger.setPosCore(0.0F, -2.0F, -14.0F);
        this.tail2.addChild(this.stinger);
        this.stinger.uvOffset(26, 39).addBox(-3.0F, -1.0F, -5.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
        this.stinger.uvOffset(11, 39).addBox(0.0F, 6.0F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, false);
		
		//modelInitEnd
    }

    @Override
    public void handleRotations(MonstrousScorpion entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle = DEMath.sin(ageInTicks * 0.04F);
        float idle1 = DEMath.sin(ageInTicks * 0.04F + 1.0F);
	    float idle2 = DEMath.sin(ageInTicks * 0.07F + 1.4F);

        this.tail1.xRot += 3.0F * RAD * idle;
	    this.tail2.xRot += 3.0F * RAD * idle1;
	
	    this.rightArm.yRot += 4.0F * RAD * idle2;
	    this.leftArm.yRot -= 4.0F * RAD * idle2;
    }

    @Override
    public void setupAnim(MonstrousScorpion entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void handleKeyframedAnimations(MonstrousScorpion entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

		if (entity.mainHandler.isPlaying(MonstrousScorpion.STING)) {
			ModelAnimator attackAnimator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.move(this.tail1, 0.0F, 2.0F, 0.0F);
			attackAnimator.rotate(this.tail1, -10.0F, headYaw * 0.5F, 0.0F);
			attackAnimator.rotate(this.tail2, 42.5F, 0.0F, 0.0F);
			attackAnimator.rotate(this.stinger, 12.5F, 0.0F, 0.0F);
			attackAnimator.apply(Easing.LINEAR);

			attackAnimator.setupKeyframe(3.0F);
			attackAnimator.move(this.tail1, 0.0F, 2.0F, -5.0F);
			attackAnimator.rotate(this.tail1, 67.5F, headYaw * 0.5F, 0.0F);
			attackAnimator.rotate(this.tail2, -42.5F, 0.0F, 0.0F);
			attackAnimator.rotate(this.stinger, -105.0F, 0.0F, 0.0F);
			attackAnimator.apply(Easing.LINEAR);

			attackAnimator.emptyKeyframe(6.0F, Easing.LINEAR);
		} else if (entity.mainHandler.isPlaying(MonstrousScorpion.RIGHT_CLAW_ATTACk)) {
			ModelAnimator attackAnimator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

			attackAnimator.setupKeyframe(3.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 80.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -70.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 82.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 60.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -50.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -50.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, -22.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, 5.0F, -25.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, -5.0F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.emptyKeyframe(7.0F, Easing.EASE_OUT_SQUARE);

		} else if (entity.mainHandler.isPlaying(MonstrousScorpion.LEFT_CLAW_ATTACk)) {
			ModelAnimator attackAnimator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

			attackAnimator.setupKeyframe(3.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -80.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -70.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 82.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -60.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -50.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -50.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, 22.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, 5.0F, 25.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, -5.0F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.emptyKeyframe(7.0F, Easing.EASE_OUT_SQUARE);

		} else if (entity.mainHandler.isPlaying(MonstrousScorpion.BOTH_CLAWS_ATTACK)) {
			ModelAnimator attackAnimator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

			attackAnimator.setupKeyframe(3.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 80.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -70.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 82.5F, 0.0F, 0.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -80.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -70.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 82.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 60.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -50.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -60.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -50.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, -50.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, -17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, -50.0F, 17.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, 52.5F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.setupKeyframe(2.0F);
			attackAnimator.rotate(this.rightArm, 0.0F, -22.5F, 0.0F);
			attackAnimator.rotate(this.rightPincerTop, 5.0F, -25.0F, 0.0F);
			attackAnimator.rotate(this.rightPincerBottom, -5.0F, 0.0F, 0.0F);
			attackAnimator.rotate(this.leftArm, 0.0F, 22.5F, 0.0F);
			attackAnimator.rotate(this.leftPincerTop, 5.0F, 25.0F, 0.0F);
			attackAnimator.rotate(this.leftPincerBottom, -5.0F, 0.0F, 0.0F);
			attackAnimator.apply();

			attackAnimator.emptyKeyframe(7.0F, Easing.EASE_OUT_SQUARE);
		}

		float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
		float f = Mth.clamp(easedlimbSwingAmount * 12.0F, 0.0F, 1.0F);
		float easedLimbSwing = Mth.clamp(caculateLimbSwingEasing(entity), 0.0F, 0.999F);

		if (easedLimbSwing > 0.0F) {
			ModelAnimator walkAnimator = new ModelAnimator(this, easedLimbSwing).multiplier(f);

			walkAnimator.setupKeyframe(0.0F);
			walkAnimator.move(this.body, 0.0F, 0.3F, 0.0F);
			walkAnimator.rotate(this.leftLegBack, 0.0F, 25.0F, 0.0F);
			walkAnimator.rotate(this.rightLegBack, 0.0F, 25.0F, 0.0F);
			walkAnimator.rotate(this.leftLegMid, 20.0F, 0.0F, 0.0F);
			walkAnimator.rotate(this.rightLegMid, 20.0F, -50.0F, 0.0F);
			walkAnimator.rotate(this.leftLegFront, 20.0F, 55.0F, 0.0F);
			walkAnimator.rotate(this.rightLegFront, 20.0F, -5.0F, 0.0F);
			walkAnimator.rotate(this.leftLegVeryFront, 65.0F, -17.5F, -20.0F);
			walkAnimator.rotate(this.rightLegVeryFront, 65.0F, -22.5F, 20.0F);
			walkAnimator.rotate(this.leftArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.rightArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.tail1, -2.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.rightLegBack, 0.0F, 0.0F, 37.5F);
			walkAnimator.rotate(this.leftLegMid, 20.0F, 25.0F, -37.5F);
			walkAnimator.rotate(this.rightLegMid, 20.0F, -25.0F, 0.0F);
			walkAnimator.rotate(this.leftLegFront, 20.0F, 30.0F, 0.0F);
			walkAnimator.rotate(this.rightLegFront, 20.0F, -30.0F, 37.5F);
			walkAnimator.rotate(this.leftLegVeryFront, 65.0F, 2.5F, -37.5F);
			walkAnimator.rotate(this.rightLegVeryFront, 65.0F, -2.5F, 20.0F);
			walkAnimator.rotate(this.tail1, 5.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.move(this.body, 0.0F, 0.3F, 0.0F);
			walkAnimator.rotate(this.leftLegBack, 0.0F, -25.0F, 0.0F);
			walkAnimator.rotate(this.rightLegBack, 0.0F, -25.0F, 0.0F);
			walkAnimator.rotate(this.leftLegMid, 20.0F, 50.0F, 0.0F);
			walkAnimator.rotate(this.rightLegMid, 20.0F, 0.0F, 0.0F);
			walkAnimator.rotate(this.leftLegFront, 20.0F, 5.0F, 0.0F);
			walkAnimator.rotate(this.rightLegFront, 20.0F, -55.0F, 0.0F);
			walkAnimator.rotate(this.leftLegVeryFront, 65.0F, 22.5F, -20.0F);
			walkAnimator.rotate(this.rightLegVeryFront, 65.0F, 17.5F, 20.0F);
			walkAnimator.rotate(this.leftArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.rightArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.tail1, -2.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.leftLegBack, 0.0F, 0.0F, -37.5F);
			walkAnimator.rotate(this.leftLegMid, 20.0F, 25.0F, 0.0F);
			walkAnimator.rotate(this.rightLegMid, 20.0F, -25.0F, 37.5F);
			walkAnimator.rotate(this.leftLegFront, 20.0F, 30.0F, -37.5F);
			walkAnimator.rotate(this.rightLegFront, 20.0F, -30.0F, 0.0F);
			walkAnimator.rotate(this.leftLegVeryFront, 65.0F, 2.5F, -20.0F);
			walkAnimator.rotate(this.rightLegVeryFront, 65.0F, -2.5F, 37.5F);
			walkAnimator.rotate(this.tail1, 5.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.move(this.body, 0.0F, 0.3F, 0.0F);
			walkAnimator.rotate(this.leftLegBack, 0.0F, 25.0F, 0.0F);
			walkAnimator.rotate(this.rightLegBack, 0.0F, 25.0F, 0.0F);
			walkAnimator.rotate(this.leftLegMid, 20.0F, 0.0F, 0.0F);
			walkAnimator.rotate(this.rightLegMid, 20.0F, -50.0F, 0.0F);
			walkAnimator.rotate(this.leftLegFront, 20.0F, 55.0F, 0.0F);
			walkAnimator.rotate(this.rightLegFront, 20.0F, -5.0F, 0.0F);
			walkAnimator.rotate(this.leftLegVeryFront, 65.0F, -17.5F, -20.0F);
			walkAnimator.rotate(this.rightLegVeryFront, 65.0F, -22.5F, 20.0F);
			walkAnimator.rotate(this.leftArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.rightArm, 2.5F, 0.0F, 0.0F);
			walkAnimator.rotate(this.tail1, -2.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			///////////////////////////////////
			walkAnimator.reset();
			walkAnimator.setTimer(DEMath.loop(easedLimbSwing, 1.0F, 0.15F));

			walkAnimator.setupKeyframe(0.0F);
			walkAnimator.rotate(this.tail2, 5.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.tail2, -2.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.tail2, 5.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.tail2, -2.0F, 0.0F, 0.0F);
			walkAnimator.apply();

			walkAnimator.setupKeyframe(0.25F);
			walkAnimator.rotate(this.tail2, 5.0F, 0.0F, 0.0F);
			walkAnimator.apply();
		}
	}

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(matrixStackIn, bufferIn, packedLight, packedOverlay);
    }
}

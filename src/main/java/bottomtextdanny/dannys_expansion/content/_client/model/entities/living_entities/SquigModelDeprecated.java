package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion.content.entities.mob._pending.squig.SquigDeprecated;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.util.Mth;

public class SquigModelDeprecated extends BCEntityModel<SquigDeprecated> {
	private final BCJoint model;
	private final BCJoint body;
	private final BCJoint yAux;
	private final BCJoint tentBackLeft;
	private final BCJoint nentBackLeft;
	private final BCJoint tentBackRight;
	private final BCJoint nentBackRight;
	private final BCJoint tentFrontLeft;
	private final BCJoint nentFrontLeft;
	private final BCJoint tentFrontRight;
	private final BCJoint nentFrontRight;
	
	public SquigModelDeprecated() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 24.0F, 0.0F);


        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, -5.0F, 0.0F);
        this.model.addChild(this.body);


        this.yAux = new BCJoint(this);
        this.yAux.setPosCore(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.yAux);
        this.yAux.uvOffset(0, 0).addBox(-5.0F, -11.0F, -3.5F, 11.0F, 8.0F, 10.0F, 0.0F, false);
        this.yAux.uvOffset(0, 18).addBox(-3.0F, -3.0F, -4.5F, 7.0F, 3.0F, 8.0F, 0.0F, false);
        this.yAux.uvOffset(0, 0).addBox(-4.0F, -5.0F, -4.5F, 3.0F, 3.0F, 2.0F, 0.025F, false);
        this.yAux.uvOffset(0, 0).addBox(2.0F, -5.0F, -4.5F, 3.0F, 3.0F, 2.0F, 0.025F, false);
        this.yAux.uvOffset(0, 29).addBox(-2.5F, -2.0F, -3.5F, 6.0F, 7.0F, 7.0F, -0.25F, false);
        this.yAux.uvOffset(0, 21).addBox(3.0F, -1.0F, -4.501F, 2.0F, 4.0F, 0.0F, 0.0F, false);
        this.yAux.uvOffset(4, 21).addBox(-4.0F, -1.0F, -4.501F, 2.0F, 4.0F, 0.0F, 0.0F, false);
        this.yAux.uvOffset(0, 18).addBox(-1.0F, 0.0F, -4.501F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        this.tentBackLeft = new BCJoint(this);
        this.tentBackLeft.setPosCore(2.5F, 3.25F, 2.0F);
        this.yAux.addChild(this.tentBackLeft);
		setRotationAngle(this.tentBackLeft, 0.0873F, 0.7854F, 0.0F);
        this.tentBackLeft.uvOffset(42, 0).addBox(-1.5F, -0.25F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentBackLeft = new BCJoint(this);
        this.nentBackLeft.setPosCore(0.0F, 7.75F, 0.0F);
        this.tentBackLeft.addChild(this.nentBackLeft);
        this.nentBackLeft.uvOffset(54, 0).addBox(-1.0F, 0.0F, 0.55F, 2.0F, 9.0F, 0.0F, 0.0F, false);
        this.nentBackLeft.uvOffset(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentBackRight = new BCJoint(this);
        this.tentBackRight.setPosCore(-1.5F, 3.25F, 2.0F);
        this.yAux.addChild(this.tentBackRight);
		setRotationAngle(this.tentBackRight, 0.0873F, -0.7854F, 0.0F);
        this.tentBackRight.uvOffset(42, 0).addBox(-1.5F, -0.25F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentBackRight = new BCJoint(this);
        this.nentBackRight.setPosCore(0.0F, 7.75F, 0.0F);
        this.tentBackRight.addChild(this.nentBackRight);
        this.nentBackRight.uvOffset(54, 0).addBox(-1.0F, 0.0F, 0.55F, 2.0F, 9.0F, 0.0F, 0.0F, true);
        this.nentBackRight.uvOffset(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentFrontLeft = new BCJoint(this);
        this.tentFrontLeft.setPosCore(2.5F, 4.0F, -2.0F);
        this.yAux.addChild(this.tentFrontLeft);
		setRotationAngle(this.tentFrontLeft, -0.0873F, -0.7854F, 0.0F);
        this.tentFrontLeft.uvOffset(42, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentFrontLeft = new BCJoint(this);
        this.nentFrontLeft.setPosCore(0.0F, 8.0F, 0.0F);
        this.tentFrontLeft.addChild(this.nentFrontLeft);
        this.nentFrontLeft.uvOffset(54, 0).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 9.0F, 0.0F, 0.0F, false);
        this.nentFrontLeft.uvOffset(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        this.tentFrontRight = new BCJoint(this);
        this.tentFrontRight.setPosCore(-1.5F, 4.0F, -2.0F);
        this.yAux.addChild(this.tentFrontRight);
		setRotationAngle(this.tentFrontRight, -0.0873F, 0.7854F, 0.0F);
        this.tentFrontRight.uvOffset(42, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.nentFrontRight = new BCJoint(this);
        this.nentFrontRight.setPosCore(0.0F, 8.0F, 0.0F);
        this.tentFrontRight.addChild(this.nentFrontRight);
        this.nentFrontRight.uvOffset(54, 0).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 9.0F, 0.0F, 0.0F, true);
        this.nentFrontRight.uvOffset(42, 11).addBox(-1.0F, 0.0F, -0.475F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		
		//modelInitEnd
	}
	
	@Override
	public void handleRotations(SquigDeprecated entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
		float yaw = Mth.rotLerp(getPartialTick(), entity.prevRenderSquigYaw + 180.0F, entity.renderSquigYaw + 180.0F) ;
		
		float off = Mth.rotlerp(entity.prevYRotOff, entity.yRotOff, getPartialTick());


        this.body.xRot += (headPitch + 90.0F) * RAD;
        this.body.yRot += yaw * RAD;

        this.yAux.yRot += off * RAD;
	}
	
	@Override
	public void handleKeyframedAnimations(SquigDeprecated entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
		super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
		
		if (entity.mainHandler.isPlaying(SquigDeprecated.MOVE)) {
			ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.linearProgress());
			
			animator.setupKeyframe(7.0F);
			animator.rotate(this.tentBackLeft, 60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentBackRight, 60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontLeft, -60.0F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontRight, -60.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.setupKeyframe(4.0F);
			animator.rotate(this.tentBackLeft, -12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentBackRight, -12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontLeft, 12.5F, 0.0F, 0.0F);
			animator.rotate(this.tentFrontRight, 12.5F, 0.0F, 0.0F);
			animator.apply();
			
			animator.emptyKeyframe(8.0F, Easing.LINEAR);
			animator.reset();
			
			animator.staticKeyframe(2.5F);
			
			animator.setupKeyframe(7.0F);
			animator.rotate(this.nentBackLeft, 75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentBackRight, 75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontLeft, -75.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontRight, -75.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.setupKeyframe(4.0F);
			animator.rotate(this.nentBackLeft, -15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentBackRight, -15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontLeft, 15.0F, 0.0F, 0.0F);
			animator.rotate(this.nentFrontRight, 15.0F, 0.0F, 0.0F);
			animator.apply();
			
			animator.emptyKeyframe(5.5F, Easing.LINEAR);
		}
		
		if (entity.hurtModule.isPlaying(SquigDeprecated.HURT)) {
			ModelAnimator animator = new ModelAnimator(this, entity.hurtModule.linearProgress());
			animator.setupKeyframe(2.0F);
			animator.scale(this.body, -0.2F, -0.2F, -0.2F);
			animator.apply();
			animator.setupKeyframe(4.0F);
			animator.scale(this.body, 0.1F, 0.1F, 0.1F);
			animator.apply(Easing.BOUNCE_OUT);
			
			animator.emptyKeyframe(3.0F, Easing.LINEAR);
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.ender_beasts;

import bottomtextdanny.dannys_expansion.content.entities.mob._pending.ender_beast.EnderBeastLancerEntity;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.mutable.MutableFloat;

public class EnderBeastLancerModel extends BCEntityModel<EnderBeastLancerEntity> {
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
    private final BCJoint rightForearm;
    public final BCJoint spear;
    private final BCJoint leftArm;
    private final BCJoint leftForearm;
    private final MutableFloat walkMultiplier = new MutableFloat(1.0F);

    public EnderBeastLancerModel() {
        this.texWidth = 256;
        this.texHeight = 256;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 24.0F, 0.0F);


        this.rightLeg = new BCJoint(this);
        this.rightLeg.setPosCore(-7.5F, -23.8F, 4.1F);
        this.model.addChild(this.rightLeg);
	    setRotationAngle(this.rightLeg, -0.3491F, 0.0F, 0.0F);
        this.rightLeg.uvOffset(50, 33).addBox(-3.5F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, false);

        this.rightCalf = new BCJoint(this);
        this.rightCalf.setPosCore(0.0F, 12.8F, 0.5F);
        this.rightLeg.addChild(this.rightCalf);
	    setRotationAngle(this.rightCalf, 0.3491F, 0.0F, 0.0F);
        this.rightCalf.uvOffset(50, 54).addBox(-3.5F, -0.5F, -4.2F, 8.0F, 12.0F, 8.0F, 0.1F, false);

        this.leftLeg = new BCJoint(this);
        this.leftLeg.setPosCore(7.5F, -23.8F, 4.1F);
        this.model.addChild(this.leftLeg);
	    setRotationAngle(this.leftLeg, -0.3491F, 0.0F, 0.0F);
        this.leftLeg.uvOffset(50, 33).addBox(-4.5F, 0.0F, -4.0F, 8.0F, 13.0F, 8.0F, 0.0F, true);

        this.leftCalf = new BCJoint(this);
        this.leftCalf.setPosCore(0.0F, 12.8F, 0.5F);
        this.leftLeg.addChild(this.leftCalf);
	    setRotationAngle(this.leftCalf, 0.3491F, 0.0F, 0.0F);
        this.leftCalf.uvOffset(50, 54).addBox(-4.5F, -0.5F, -4.2F, 8.0F, 12.0F, 8.0F, 0.1F, true);

        this.hip = new BCJoint(this);
        this.hip.setPosCore(0.0F, -20.8F, 3.6F);
        this.model.addChild(this.hip);
	    setRotationAngle(this.hip, 0.0873F, 0.0F, 0.0F);
        this.hip.uvOffset(0, 0).addBox(-10.0F, -18.0F, -5.5F, 20.0F, 18.0F, 11.0F, 0.0F, false);
        this.hip.uvOffset(82, 56).addBox(-10.0F, 0.0F, -5.5F, 20.0F, 5.0F, 11.0F, 0.0F, false);

        this.chest = new BCJoint(this);
        this.chest.setPosCore(0.0F, -15.0F, 1.5F);
        this.hip.addChild(this.chest);
	    setRotationAngle(this.chest, 0.1745F, 0.0F, 0.0F);
        this.chest.uvOffset(62, 0).addBox(-14.0F, -16.0F, -9.0F, 28.0F, 15.0F, 18.0F, 0.0F, false);
        this.chest.uvOffset(82, 33).addBox(-14.0F, -1.0F, -9.0F, 28.0F, 5.0F, 18.0F, 0.0F, false);

        this.head = new BCJoint(this);
        this.head.setPosCore(0.0F, -16.2F, -3.7F);
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
        this.rightArm.uvOffset(0, 68).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 17.0F, 8.0F, 0.0F, false);

        this.rightForearm = new BCJoint(this);
        this.rightForearm.setPosCore(-4.0F, 12.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
	    setRotationAngle(this.rightForearm, -0.6981F, 0.0F, 0.0F);
        this.rightForearm.uvOffset(0, 93).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 18.0F, 8.0F, 0.1F, false);

        this.spear = new BCJoint(this);
        this.spear.setPosCore(0.0F, 14.6F, 0.0F);
        this.rightForearm.addChild(this.spear);
        this.spear.uvOffset(0, 119).addBox(-1.0F, -1.0F, -16.0F, 2.0F, 2.0F, 46.0F, 0.0F, false);
        this.spear.uvOffset(0, 171).addBox(0.0F, -6.0F, -46.0F, 0.0F, 10.0F, 20.0F, 0.0F, false);
        this.spear.uvOffset(40, 167).addBox(-1.5F, -1.5F, -26.0F, 3.0F, 3.0F, 12.0F, 0.0F, false);

        this.leftArm = new BCJoint(this);
        this.leftArm.setPosCore(14.0F, -8.7F, 0.3F);
        this.chest.addChild(this.leftArm);
	    setRotationAngle(this.leftArm, 0.0873F, 0.1745F, -0.1309F);
        this.leftArm.uvOffset(0, 68).addBox(0.0F, -4.0F, -4.0F, 8.0F, 17.0F, 8.0F, 0.0F, true);

        this.leftForearm = new BCJoint(this);
        this.leftForearm.setPosCore(4.0F, 12.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
	    setRotationAngle(this.leftForearm, -0.6981F, 0.0F, 0.0F);
        this.leftForearm.uvOffset(0, 93).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 18.0F, 8.0F, 0.1F, true);

        this.addReseter(m -> this.walkMultiplier.setValue(1.0F));
        //modelInitEnd
    }

    @Override
    public void setupAnim(EnderBeastLancerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        
        netHeadYaw = Mth.wrapDegrees(netHeadYaw);
        //ClientInstance.chatMsg(netHeadYaw);
        netHeadYaw = Mth.clamp(netHeadYaw, -90.0F, 89.0F);

        this.head.yRot += netHeadYaw * (RAD / 5 * 4);
        this.head.xRot += Mth.clamp(headPitch, -70.0F, 40.0F) * RAD;
        this.chest.yRot += netHeadYaw * (RAD / 5);
    }

    @Override
    public void handleKeyframedAnimations(EnderBeastLancerEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
	    headYaw = DEMath.moduloFromBase(-180.0F, headYaw, 360.0F);
	    headYaw = Mth.clamp(headYaw, -90.0F, 90.0F);

        if (!entity.jawModule.isPlayingNull()) {
        	float jawTime = (float)entity.jawModule.getAnimation().getDuration() / 2.0F;
            ModelAnimator ambientAnimator = new ModelAnimator(this, entity.jawModule.linearProgress());

            ambientAnimator.setupKeyframe(jawTime);
            ambientAnimator.rotate(this.jaw, 10.0F, 0.0F, 0.0F);
            ambientAnimator.apply();

            ambientAnimator.emptyKeyframe(jawTime, Easing.LINEAR);
        }

        if (entity.mainHandler.isPlaying(EnderBeastLancerEntity.IMPALE)) {
            ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            ModelAnimator animator1 = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            ModelAnimator posFixer = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            float headRotY = headYaw;
            
            animator.disableFloat(this.walkMultiplier, 4, 12, 4);

            animator.setupKeyframe(8.0F);
            animator.rotate(this.model, 0.0F, 17.5F, 0.0F);
            animator.rotate(this.hip, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -17.5F, 25.0F  + headRotY, 10.0F);
            animator.rotate(this.head, 10.0F , -45.0F- headRotY, 0.0F);
            animator.rotate(this.rightArm, 47.5F, 0.0F, 107.5F);
            animator.rotate(this.rightForearm, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 47.5F, 0.0F, -15.0F);
            animator.rotate(this.leftForearm, -35.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 20.0F, -17.5F, 0.0F);
            animator.rotate(this.leftCalf, -20.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.rotate(this.model, 10.0F, -2.5F, 0.0F);
            animator.rotate(this.chest, 5.0F, -45.0F + headRotY, -5.0F);
            animator.rotate(this.head, -25.0F, 42.5F - headRotY, 0.0F);
            animator.rotate(this.rightArm, -60.0F, 0.0F, 117.5F);
            animator.rotate(this.rightForearm, 40.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, 7.0F, 0.0F);
            animator.move(this.leftLeg, 0.0F, -2.0F, 0.0F);
            animator.rotate(this.leftLeg, 32.5F, 2.5F, 0.0F);
            animator.rotate(this.leftCalf, 30.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);

            //

            animator1.setupKeyframe(8);
            animator1.rotate(this.rightLeg, 25.0F, 0.0F, 0.0F);
            animator1.rotate(this.rightCalf, 7.5F, 0.0F, 0.0F);
            animator1.apply();

            animator1.setupKeyframe(2);
            animator1.move(this.rightArm, 0.0F, 0.0F, -5.0F);
            animator1.rotate(this.spear, 32.5F, 0.0F, 0.0F);
            animator1.rotate(this.rightLeg, -47.5F, 0.0F, 0.0F);
            animator1.rotate(this.rightCalf, 27.5F, 0.0F, 0.0F);
            animator1.apply();

            animator1.setupKeyframe(2);
            animator1.move(this.rightArm, 0.0F, 0.0F, -5.0F);
            animator1.rotate(this.spear, 90.0F, 0.0F, 0.0F);
            animator1.rotate(this.rightLeg, -77.5F, 0.0F, 0.0F);
            animator1.rotate(this.rightCalf, 67.5F, 0.0F, 0.0F);
            animator1.apply();

            animator1.setupKeyframe(4);
            animator1.move(this.rightArm, 0.0F, 0.0F, -2.5F);
            animator1.rotate(this.spear, 45.0F, 0.0F, 0.0F);
            animator1.rotate(this.rightLeg, -52.5F, 0.0F, 0.0F);
            animator1.rotate(this.rightCalf, 50.0F, 0.0F, 0.0F);
            animator1.apply();

            animator1.emptyKeyframe(4, Easing.LINEAR);

            //

            posFixer.setupKeyframe(8);
            posFixer.move(this.model, -0.6F, -0.5F, -2.0F);
            posFixer.apply();

            posFixer.setupKeyframe(1F);
            posFixer.move(this.model, -0.7F, -0.5F, -6.1F);
            posFixer.apply();

            posFixer.setupKeyframe(1F);
            posFixer.move(this.model, -0.4F, 1.3F, -11.8F);
            posFixer.apply();

            posFixer.setupKeyframe(1F);
            posFixer.move(this.model, -0.1F, 3.9F, -15.7F);
            posFixer.apply();

            posFixer.setupKeyframe(1F);
            posFixer.move(this.model, -0.5F, 7.6F, -18.5F);
            posFixer.apply();

            posFixer.setupKeyframe(2F);
            posFixer.move(this.model, 0.2F, 3.7F, -15.2F);
            posFixer.apply();

            posFixer.setupKeyframe(2F);
            posFixer.move(this.model, 0.1F, 0.7F, -9.9F);
            posFixer.apply();

            posFixer.emptyKeyframe(4, Easing.LINEAR);
        }

        if (entity.mainHandler.isPlaying(EnderBeastLancerEntity.SWING)) {
            ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());
            ModelAnimator posFixer = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(7);
            animator.rotate(this.model, 0.0F, 15.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, 12.5F, 12.5F);
            animator.rotate(this.head, 0.0F, -27.5F, 0.0F);
            animator.move(this.rightArm, 0.0F, 0.0F, 8.0F);
            animator.rotate(this.rightArm, 52.5F, 0.0F, 87.5F);
            animator.rotate(this.rightForearm, -12.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 30.0F, 0.0F, -17.5F);
            animator.rotate(this.leftForearm, 2.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -15.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.model, 0.0F, -22.5F, 0.0F);
            animator.rotate(this.hip, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, -32.5F, -12.5F);
            animator.rotate(this.head, 0.0F, 37.5F, 0.0F);
            animator.move(this.rightArm, 0.0F, 0.0F, -3.0F);
            animator.rotate(this.rightArm, -75.0F, -5.0F, 110.0F);
            animator.rotate(this.rightForearm, -12.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 32.5F, 0.0F, -32.5F);
            animator.rotate(this.leftForearm, 2.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, 22.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);

            //

            posFixer.setupKeyframe(7.0F);
            posFixer.move(this.model, -0.6F, 0.0F, 2.1F);
            posFixer.rotate(this.spear, 10.0F, 0.0F, 0.0F);
            posFixer.apply();

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, -0.3F, 0.0F, -0.5F);
            posFixer.rotate(this.spear, 57.5F, 0.0F, 0.0F);
            posFixer.rotate(this.rightLeg, -15.0F, 17.5F, 0.0F);
            posFixer.rotate(this.rightCalf, 12.5F, 0.0F, 0.0F);
            posFixer.apply();

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, 1.9F, 0.0F, -2.5F);
            posFixer.rotate(this.spear, 35.0F, 0.0F, 0.0F);
            posFixer.rotate(this.rightLeg, 0.0F, 22.5F, 0.0F);
            posFixer.apply();

            posFixer.emptyKeyframe(6, Easing.LINEAR);
        }
	
	    float easedlimbSwingAmount = Mth.lerp(getPartialTick(), entity.loopedWalkModule().prevRenderLimbSwingAmount, entity.loopedWalkModule().renderLimbSwingAmount);
	    float f = Mth.clamp(easedlimbSwingAmount * 8.0F, 0.0F, 1.0F);
	    float easedLimbSwing = caculateLimbSwingEasing(entity);
	
	    ModelAnimator walk = new ModelAnimator(this, Mth.clamp(easedLimbSwing, 0.0F, 0.999F)).multiplier(f);
	    
	    
	    walk.setupKeyframe(0.0F);
	    walk.rotate(this.chest, 5.0F, -5.0F, 0.0F);
	    walk.rotate(this.head, -5.0F, 7.5F, 0.0F);
	    walk.rotate(this.rightArm, 20.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightForearm, 7.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftArm, -32.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftForearm, 0.0F, 0.0F, 0.0F);
	    walk.move(this.rightLeg, 0.0F, 0.0F, 0.0F);
	    walk.move(this.leftLeg, 0.0F, 1.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.5F);
	    walk.rotate(this.chest, 5.0F, 5.0F, 0.0F);
	    walk.rotate(this.head, -5.0F, -7.5F, 0.0F);
	    walk.rotate(this.rightArm, -32.5F, 0.0F, 0.0F);
	    walk.rotate(this.rightForearm, 0.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftArm, 20.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftForearm, 7.5F, 0.0F, 0.0F);
	    walk.move(this.rightLeg, 0.0F, 1.0F, 0.0F);
	    walk.move(this.leftLeg, 0.0F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.5F);
	    walk.rotate(this.chest, 5.0F, -5.0F, 0.0F);
	    walk.rotate(this.head, -5.0F, 7.5F, 0.0F);
	    walk.rotate(this.rightArm, 20.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightForearm, 7.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftArm, -32.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftForearm, 0.0F, 0.0F, 0.0F);
	    walk.move(this.rightLeg, 0.0F, 0.0F, 0.0F);
	    walk.move(this.leftLeg, 0.0F, 1.0F, 0.0F);
	    walk.apply();
	   
	    walk.reset();
	
	    walk.setupKeyframe(0.0F);
	    walk.rotate(this.hip, 12.5F, -5.0F, 0.0F);
	    walk.rotate(this.rightLeg, -40.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightCalf, 32.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftLeg, 25.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftCalf, 47.5F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.25F);
	    walk.rotate(this.hip, 2.5F, 0.0F, 0.0F);
	    walk.rotate(this.rightLeg, 20.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightCalf, -20.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftLeg, -15.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftCalf, 65.0F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.25F);
	    walk.rotate(this.hip, 12.5F, 5.0F, 0.0F);
	    walk.rotate(this.rightLeg, 25.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightCalf, 47.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftLeg, -40.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftCalf, 32.5F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.25F);
	    walk.rotate(this.hip, 2.5F, 0.0F, 0.0F);
	    walk.rotate(this.rightLeg, -15.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightCalf, 65.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftLeg, 20.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftCalf, -20.0F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.25F);
	    walk.rotate(this.hip, 12.5F, -5.0F, 0.0F);
	    walk.rotate(this.rightLeg, -40.0F, 0.0F, 0.0F);
	    walk.rotate(this.rightCalf, 32.5F, 0.0F, 0.0F);
	    walk.rotate(this.leftLeg, 25.0F, 0.0F, 0.0F);
	    walk.rotate(this.leftCalf, 47.5F, 0.0F, 0.0F);
	    walk.apply();
	    
	    walk.reset();
	
	    walk.setupKeyframe(0.0F);
	    walk.move(this.model, 0.0F, 5.8F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 3.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 1.4F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, -0.2F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.6F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 2.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 5.8F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 3.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 1.4F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.0F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, -0.2F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 0.6F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 2.3F, 0.0F);
	    walk.apply();
	
	    walk.setupKeyframe(0.0625F);
	    walk.move(this.model, 0.0F, 5.8F, 0.0F);
	    walk.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
    	float prog = 1.0F - ((float)getHost().hurtColorTimer.current() / getHost().hurtColorTimer.bound());


        this.model.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, red, (int)(prog * 128) + 1.0F, (int)(prog * 128) + 1.0F, alpha);
    }
}

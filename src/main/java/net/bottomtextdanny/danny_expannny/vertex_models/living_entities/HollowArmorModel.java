package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class HollowArmorModel extends BCEntityModel<HollowArmor> {
    private final BCVoxel model;
    private final BCVoxel hip;
    private final BCVoxel abdomen;
    private final BCVoxel chest;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel rightHand;
    public final BCVoxel sword;
    private final BCVoxel rightShoulderPad;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    public final BCVoxel leftHand;
    private final BCVoxel head;
    private final BCVoxel rightLeg;
    private final BCVoxel rightLegLower;
    private final BCVoxel rightFoot;
    private final BCVoxel leftLeg;
    private final BCVoxel leftLegLower;
    private final BCVoxel leftFoot;

    public HollowArmorModel() {
        this.texWidth = 96;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, -15.0F, 0.0F);
        this.model.addChild(this.hip);
        this.hip.texOffs(0, 0).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.25F, false);

        this.abdomen = new BCVoxel(this);
        this.abdomen.setPos(0.0F, -3.0F, 0.0F);
        this.hip.addChild(this.abdomen);
        this.abdomen.texOffs(0, 8).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -5.0F, 0.0F);
        this.abdomen.addChild(this.chest);
        setRotationAngle(this.chest, 0.0873F, 0.0F, 0.0F);
        this.chest.texOffs(24, 0).addBox(-5.0F, -6.0F, -3.0F, 10.0F, 6.0F, 6.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-5.0F, -4.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, 0.0873F, -0.0873F, 0.0873F);
        this.rightArm.texOffs(12, 16).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.1F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.5F, 5.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        setRotationAngle(this.rightForearm, -0.3491F, 0.0F, 0.0F);
        this.rightForearm.texOffs(0, 16).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);

        this.rightHand = new BCVoxel(this);
        this.rightHand.setPos(0.0F, 4.0F, 0.0F);
        this.rightForearm.addChild(this.rightHand);
        this.rightHand.texOffs(0, 23).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        this.sword = new BCVoxel(this);
        this.sword.setPos(0.0F, 2.0F, 0.5F);
        this.rightHand.addChild(this.sword);
        this.sword.texOffs(24, 12).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        this.sword.texOffs(14, 40).addBox(-1.5F, -1.5F, 2.5F, 3.0F, 3.0F, 2.0F, 0.0F, false);
        this.sword.texOffs(34, 12).addBox(-1.5F, -1.5F, -4.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        this.rightShoulderPad = new BCVoxel(this);
        this.rightShoulderPad.setPos(0.0F, 0.0F, 0.0F);
        this.rightArm.addChild(this.rightShoulderPad);
        setRotationAngle(this.rightShoulderPad, 0.0F, 0.0F, 0.0436F);
        this.rightShoulderPad.texOffs(19, 20).addBox(-4.5F, -3.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(5.0F, -4.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, 0.0873F, 0.0873F, -0.0873F);
        this.leftArm.texOffs(12, 16).addBox(0.0F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.1F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(1.5F, 5.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        setRotationAngle(this.leftForearm, -0.3491F, 0.0F, 0.0F);
        this.leftForearm.texOffs(0, 16).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, true);

        this.leftHand = new BCVoxel(this);
        this.leftHand.setPos(0.0F, 4.0F, 0.0F);
        this.leftForearm.addChild(this.leftHand);
        this.leftHand.texOffs(0, 23).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, true);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -6.0F, 0.0F);
        this.chest.addChild(this.head);
        this.head.texOffs(39, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        this.head.texOffs(28, 30).addBox(-1.0F, -10.0F, -2.0F, 2.0F, 8.0F, 8.0F, 0.0F, false);
        this.head.texOffs(48, 28).addBox(-1.0F, -12.0F, -3.0F, 2.0F, 14.0F, 12.0F, 0.0F, false);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.5F, -15.0F, 0.0F);
        this.model.addChild(this.rightLeg);
        setRotationAngle(this.rightLeg, -0.0873F, 0.0873F, 0.0873F);
        this.rightLeg.texOffs(0, 31).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

        this.rightLegLower = new BCVoxel(this);
        this.rightLegLower.setPos(0.0F, 7.0F, 0.0F);
        this.rightLeg.addChild(this.rightLegLower);
        setRotationAngle(this.rightLegLower, 0.1745F, -0.0873F, 0.0F);
        this.rightLegLower.texOffs(16, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.1F, false);

        this.rightFoot = new BCVoxel(this);
        this.rightFoot.setPos(0.0F, 7.0F, 0.0F);
        this.rightLegLower.addChild(this.rightFoot);
        setRotationAngle(this.rightFoot, -0.0873F, 0.0F, -0.0873F);
        this.rightFoot.texOffs(0, 42).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.5F, -15.0F, 0.0F);
        this.model.addChild(this.leftLeg);
        setRotationAngle(this.leftLeg, -0.0873F, -0.0873F, -0.0873F);
        this.leftLeg.texOffs(0, 31).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, true);

        this.leftLegLower = new BCVoxel(this);
        this.leftLegLower.setPos(0.0F, 7.0F, 0.0F);
        this.leftLeg.addChild(this.leftLegLower);
        setRotationAngle(this.leftLegLower, 0.1745F, 0.0873F, 0.0F);
        this.leftLegLower.texOffs(16, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.1F, true);

        this.leftFoot = new BCVoxel(this);
        this.leftFoot.setPos(0.0F, 7.0F, 0.0F);
        this.leftLegLower.addChild(this.leftFoot);
        setRotationAngle(this.leftFoot, -0.0873F, 0.0F, 0.0873F);
        this.leftFoot.texOffs(0, 42).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 6.0F, 0.0F, true);
        setupDefaultState();
    }

    @Override
    public void handleRotations(HollowArmor entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = DEMath.sin(ageInTicks * 0.03F);

        this.chest.xRot += Mth.abs(RAD) * 2.0F * idle1;
        this.rightArm.xRot += Mth.abs(RAD) * -4.0F * idle1;
        this.leftArm.xRot += Mth.abs(RAD) * -4.0F * idle1;
        this.rightArm.zRot += Mth.abs(RAD) * -2.0F * idle2;
        this.leftArm.zRot -= Mth.abs(RAD) * -2.0F * idle2;

        this.head.yRot += headYaw * RAD;
        this.head.xRot += headPitch * (RAD / 5.0F * 4.0F);
        this.chest.yRot += headYaw * (RAD / 5.0F);
    }

    @Override
    public void handleKeyframedAnimations(HollowArmor entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());
        float walkMult = 1.0F;
        
        if(entity.mainHandler.isPlaying(HollowArmor.BLUNT)) {
            animator.setupKeyframe(8.0F);
            animator.move(this.hip, 0.0F, 0.0F, 1.0F);
            animator.rotate(this.hip, 0.0F, 35.0F, 0.0F);
            animator.rotate(this.abdomen, -5.0F, 5.0F, 0.0F);
            animator.rotate(this.chest, -10.0F, 20.0F, 0.0F);
            animator.rotate(this.head, 0.0F, -50.0F, 0.0F);
            animator.rotate(this.rightArm, -65.0F, -5.0F, 55.0F);
            animator.rotate(this.rightForearm, -75.0F, 15.0F, -10.0F);
            animator.rotate(this.rightHand, 0.0F, 45.0F, 30.0F);
            animator.rotate(this.sword, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -95.0F, 20.0F, 0.0F);
            animator.rotate(this.leftForearm, 0.0F, 0.0F, 45.0F);
            animator.move(this.rightLeg, 0.0F, 0.0F, 2.0F);
            animator.rotate(this.rightLeg, 15.0F, 20.0F, 0.0F);
            animator.rotate(this.rightLegLower, 30.0F, 20.0F, 0.0F);
            animator.rotate(this.rightFoot, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, 15.0F, 0.0F);
            animator.rotate(this.leftFoot, 0.0F, -15.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.setupKeyframe(1.0F);
            animator.move(this.model, 0.0F, 2.0F, 0.0F);
            animator.rotate(this.model, 2.5F, 0.0F, 0.0F);
            animator.move(this.hip, 0.0F, -1.0F, -0.5F);
            animator.rotate(this.hip, 2.0F, -5.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, 20.0F, 0.0F);
            animator.rotate(this.head, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -80.0F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, 0.0F, 45.0F, 0.0F);
            animator.rotate(this.rightHand, 70.0F, 45.0F, 0.0F);
            animator.rotate(this.leftArm, -80.0F, 20.0F, 0.0F);
            animator.rotate(this.leftForearm, 10.0F, 0.0F, 30.0F);
            animator.move(this.rightLeg, 0.0F, -1.0F, -1.0F);
            animator.rotate(this.rightLeg, 18.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLegLower, 35.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 30.0F, 0.0F, 0.0F);
            animator.move(this.leftLeg, 0.0F, -1.0F, 0.0F);
            animator.rotate(this.leftLeg, -15.0F, -5.0F, 0.0F);
            animator.rotate(this.leftLegLower, 25.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -8.0F, 5.0F, -3.0F);
            animator.apply();

            animator.setupKeyframe(1.0F);
            animator.move(this.model, 0.0F, 4.0F, 0.0F);
            animator.rotate(this.model, 5.0F, 0.0F, 0.0F);
            animator.move(this.hip, 0.0F, -2.0F, -2.0F);
            animator.rotate(this.hip, 5.0F, -45.0F, 0.0F);
            animator.rotate(this.abdomen, 5.0F, -5.0F, 0.0F);
            animator.rotate(this.chest, 5.0F, -30.0F, 0.0F);
            animator.rotate(this.head, 0.0F, 70.0F, 0.0F);
            animator.rotate(this.rightArm, -80.0F, -10.0F, 0.0F);
            animator.rotate(this.rightForearm, 0.0F, 45.0F, -30.0F);
            animator.rotate(this.rightHand, 70.0F, 45.0F, 0.0F);
            animator.rotate(this.sword, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -80.0F, -5.0F, 0.0F);
            animator.rotate(this.leftForearm, 10.0F, 0.0F, 55.0F);
            animator.move(this.rightLeg, 0.0F, -2.0F, -4.0F);
            animator.rotate(this.rightLeg, 20.0F, -20.0F, 0.0F);
            animator.rotate(this.rightLegLower, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 45.0F, 0.0F, 0.0F);
            animator.move(this.leftLeg, 0.0F, -2.0F, 0.0F);
            animator.rotate(this.leftLeg, -30.0F, -25.0F, 0.0F);
            animator.rotate(this.leftLegLower, 50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -25.0F, 20.0F, -5.0F);
            animator.apply();

            animator.emptyKeyframe(6.0F, Easing.LINEAR);
        }

        else if(entity.mainHandler.isPlaying(HollowArmor.SLASH)) {

            animator.setupKeyframe(8.0F);
            animator.move(this.model, 0.7F, 0.3F, 2.0F);
            animator.rotate(this.model, 0.0F, 32.5F, 0.0F);
            animator.rotate(this.hip, 0.0F, 0.0F, -7.5F);
            animator.rotate(this.chest, 0.0F, 35.0F, 0.0F);
            animator.rotate(this.head, 0.0F, -52.5F, 0.0F);
            animator.rotate(this.rightArm, 47.5F, 0.0F, 77.5F);
            animator.rotate(this.rightForearm, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 0.0F, 25.0F, -22.5F);
            animator.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -0.4F, 0.0F);
            animator.rotate(this.rightLeg, -10.0F, 45.0F, 2.5F);
            animator.rotate(this.rightLegLower, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -32.5F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.setupKeyframe(2.0F);
            animator.move(this.model, 0.2F, 0.3F, 0.1F);
            animator.rotate(this.hip, 0.0F, 0.0F, -3.75F);
            animator.rotate(this.chest, 0.0F, -6.25F, 0.0F);
            animator.rotate(this.head, 0.0F, 13.75F, 0.0F);
            animator.rotate(this.rightArm, -16.25F, 12.5F, 77.5F);
            animator.rotate(this.rightForearm, -8.75F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 0.0F, 25.0F, -22.5F);
            animator.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -0.2F, 0.0F);
            animator.rotate(this.rightLeg, -5.0F, 32.5F, 12.5F);
            animator.rotate(this.rightLegLower, 55.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 1.25F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.setupKeyframe(2.0F);
            animator.move(this.model, 0.7F, 0.0F, -1.8F);
            animator.rotate(this.model, 0.0F, -32.5F, 0.0F);
            animator.rotate(this.chest, 0.0F, -50.0F, 0.0F);
            animator.rotate(this.head, 0.0F, 80.0F, 0.0F);
            animator.rotate(this.rightArm, -80.0F, 25.0F, 77.5F);
            animator.rotate(this.rightForearm, -30.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 0.0F, 25.0F, -22.5F);
            animator.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 25.0F, 20.0F, 7.5F);
            animator.rotate(this.rightLegLower, 17.5F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 2.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, 32.5F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.emptyKeyframe(7.0F, Easing.LINEAR);
        }

        else if(entity.mainHandler.isPlaying(HollowArmor.SLASH)) {

            walkMult = animator.disable(8.0F, 4.0F, 3.0F, (float) entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(1.5F);
            animator.move(this.model, 0.05F, 0.2F, 1.15F);
            animator.rotate(this.rightLeg, -37.5F, 0.2F, 10.0F);
            animator.rotate(this.rightLegLower, 27.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.move(this.model, 0.3F, 0.4F, 2.3F);
            animator.rotate(this.rightLeg, -12.5F, 27.5F, 0.0F);
            animator.rotate(this.rightLegLower, 15.0F, 0.0F, 0.0F);
            animator.apply();

            animator.staticKeyframe(4.0F);

            animator.setupKeyframe(1.0F);
            animator.move(this.model, -0.1F, 1.0F, -0.3F);
            animator.rotate(this.rightLeg, -7.5F, 15.0F, 0.0F);
            animator.rotate(this.rightLegLower, 85.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.0F);
            animator.move(this.model, -0.2F, 1.9F, -2.2F);
            animator.rotate(this.rightLeg, 25.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLegLower, 15.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3.0F);
            animator.move(this.model, -0.1F, 0.7F, -1.2F);
            animator.rotate(this.rightLeg, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLegLower, 15.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(3.0F, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(3.0F);
            animator.rotate(this.model, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.rightArm, -162.5F, -22.5F, -32.5F);
            animator.rotate(this.rightForearm, -35.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 35.0F, 5.0F, -10.0F);
            animator.rotate(this.leftForearm, -25.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -10.0F, -22.5F, 0.0F);
            animator.rotate(this.leftLegLower, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -2.5F, 0.0F, 0.0F);
            animator.apply();

            animator.staticKeyframe(4.0F);

            animator.setupKeyframe(2.0F);
            animator.rotate(this.rightArm, -40.0F, 12.5F, -32.5F);
            animator.rotate(this.rightForearm, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -25.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 35.0F, 5.0F, -10.0F);
            animator.rotate(this.leftForearm, -25.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 92.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -12.5F, 2.5F, 0.0F);
            animator.rotate(this.leftLegLower, 45.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -32.5F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6.0F, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(3.0F);
            animator.rotate(this.hip, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.abdomen, 27.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, -20.0F, 37.5F, 7.5F);
            animator.rotate(this.head, 10.0F, -50.0F, -12.5F);
            animator.apply();

            animator.setupKeyframe(6.0F);
            animator.rotate(this.hip, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.abdomen, 2.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, -37.5F, 0.0F);
            animator.rotate(this.head, -17.5F, 40.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(6.0F, Easing.LINEAR);
        }

        else if(entity.mainHandler.isPlaying(HollowArmor.IMPALE)) {

            walkMult = animator.disable(7.0F, 14.0F, 5.0F, (float) entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(7.0F);
            animator.move(this.model, -0.1F, 0.9F, 3.4F);
            animator.rotate(this.model, 0.0F, 21.25F, 0.0F);
            animator.rotate(this.hip, 0.0F, 0.0F, 6.25F);
            animator.rotate(this.chest, 5.0F, 11.25F, -3.25F);
            animator.rotate(this.head, 0.0F, -31.25F, 0.0F);
            animator.rotate(this.rightArm, 12.5F, 0.0F, 15.0F);
            animator.rotate(this.leftArm, 10.0F, 16.25F, -11.25F);
            animator.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -1.0F, 0.0F);
            animator.rotate(this.rightLeg, -6.25F, -1.25F, 0.0F);
            animator.rotate(this.rightLegLower, 18.75F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -3.25F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -18.75F, -20.0F, 0.0F);
            animator.rotate(this.leftLegLower, 20.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(7.0F);
            animator.move(this.model, 0.6F, 2.2F, 6.2F);
            animator.rotate(this.model, 0.0F, 42.5F, 0.0F);
            animator.rotate(this.hip, 0.0F, 0.0F, 12.5F);
            animator.rotate(this.chest, 10.0F, 22.5F, -7.5F);
            animator.rotate(this.head, 0.0F, -62.5F, 0.0F);
            animator.rotate(this.rightArm, 25.0F, 0.0F, 30.0F);
            animator.rotate(this.leftArm, 20.0F, 32.5F, -22.5F);
            animator.rotate(this.leftForearm, -20.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -2.0F, 0.0F);
            animator.rotate(this.rightLeg, -12.5F, -2.5F, 0.0F);
            animator.rotate(this.rightLegLower, 37.5F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -37.5F, -40.0F, 0.0F);
            animator.rotate(this.leftLegLower, 40.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.setupKeyframe(1.0F);
            animator.move(this.model, -0.1F, 0.6F, -0.6F);
            animator.rotate(this.model, 0.0F, 8.75F, 0.0F);
            animator.rotate(this.hip, 11.25F, 0.0F, 11.25F);
            animator.rotate(this.chest, 1.25F, -10.0F, -8.75F);
            animator.rotate(this.head, 0.0F, 5.0F, 12.5F);
            animator.rotate(this.rightArm, -18.75F, 31.25F, 41.25F);
            animator.rotate(this.rightHand, 48.75F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 30.0F, 25.0F, -22.5F);
            animator.rotate(this.leftForearm, -20.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -1.0F, 0.0F);
            animator.rotate(this.rightLeg, 17.5F, 11.25F, 0.0F);
            animator.rotate(this.rightLegLower, 21.25F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -5.0F, -7.5F, 0.0F);
            animator.rotate(this.leftLegLower, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -12.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.0F);
            animator.move(this.model, 0.9F, 1.8F, -7.8F);
            animator.rotate(this.model, 0.0F, -25.0F, 0.0F);
            animator.rotate(this.hip, 22.5F, 0.0F, 10.0F);
            animator.rotate(this.chest, -7.5F, -42.5F, -10.0F);
            animator.rotate(this.head, 0.0F, 72.5F, 25.0F);
            animator.rotate(this.rightArm, -62.5F, 50.0F, 52.5F);
            animator.rotate(this.rightHand, 97.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 40.0F, 32.5F, -22.5F);
            animator.rotate(this.leftForearm, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 47.5F, 25.0F, 0.0F);
            animator.rotate(this.rightLegLower, 5.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 27.5F, 25.0F, 0.0F);
            animator.rotate(this.leftFoot, -25.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.move(this.model, 0.3F, 0.6F, -3.6F);
            animator.rotate(this.model, 0.0F, -12.5F, 0.0F);
            animator.rotate(this.hip, 11.25F, 0.0F, 5.0F);
            animator.rotate(this.chest, -3.5F, -21.25F, -5.0F);
            animator.rotate(this.head, 0.0F, 36.25F, 11.25F);
            animator.rotate(this.rightArm, -31.25F, 31.25F, 25.0F);
            animator.rotate(this.rightHand, 46.25F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 20.0F, 16.25F, -11.25F);
            animator.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 23.75F, 12.5F, 0.0F);
            animator.rotate(this.rightLegLower, 2.5F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, 11.25F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 13.75F, 12.5F, 0.0F);
            animator.rotate(this.leftFoot, -12.5F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(4.0F, Easing.LINEAR);
        }

        else if(entity.mainHandler.isPlaying(HollowArmor.DOUBLE_SWING)) {

            animator.setupKeyframe(6.0F);
            animator.move(this.model, -0.5F, 1.0F, 3.5F);
            animator.rotate(this.model, 0.0F, 25.0F, 0.0F);
            animator.move(this.hip, 0.0F, 0.5F, 0.0F);
            animator.rotate(this.hip, 0.0F, 0.0F, -10.0F);
            animator.rotate(this.chest, -10.0F, 20.0F, 5.0F);
            animator.rotate(this.head, 15.0F, -30.0F, 0.0F);
            animator.rotate(this.rightArm, 35.0F, -20.0F, 90.0F);
            animator.rotate(this.rightForearm, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 55.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -20.0F, -25.0F, 0.0F);
            animator.rotate(this.leftLegLower, 25.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, -15.0F, -20.0F, 0.0F);
            animator.rotate(this.rightLegLower, -10.0F, 5.0F, 0.0F);
            animator.rotate(this.rightFoot, 25.0F, -5.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(2.0F);
            animator.move(this.model, 0.25F, 0.0F, 0.5F);
            animator.rotate(this.model, 0.0F, 10.0F, 0.0F);
            animator.move(this.hip, 0.0F, 0.25F, 0.0F);
            animator.rotate(this.hip, 20.0F, 0.0F, -5.0F);
            animator.rotate(this.chest, 5.0F, -5.0F, 5.0F);
            animator.rotate(this.head, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -40.0F, 20.0F, 90.0F);
            animator.rotate(this.rightForearm, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 55.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -5.0F, -10.0F, 0.0F);
            animator.rotate(this.leftLegLower, 0.0F, 10.0F, 0.0F);
            animator.rotate(this.leftFoot, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, -10.0F, 0.0F);
            animator.rotate(this.rightLegLower, -5.0F, 2.5F, 0.0F);
            animator.rotate(this.rightFoot, 7.5F, -2.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(1.0F);
            animator.move(this.model, 0.0F, 0.3F, -2.5F);
            animator.rotate(this.model, 0.0F, 10.0F, 0.0F);
            animator.rotate(this.hip, 10.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -5.0F, -60.0F, 5.0F);
            animator.rotate(this.head, -5.0F, 30.0F, 0.0F);
            animator.rotate(this.rightArm, -110.0F, 5.0F, 50.0F);
            animator.rotate(this.rightForearm, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 5.0F, -50.0F, 0.0F);
            animator.rotate(this.leftArm, 55.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 10.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -10.0F, 0.0F, 0.0F);
            animator.apply();

            animator.staticKeyframe(3.0F);

            animator.setupKeyframe(4.0F);
            animator.move(this.model, -0.4F, 0.8F, -3.3F);
            animator.rotate(this.model, 0.0F, 20.0F, 0.0F);
            animator.rotate(this.hip, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -5.0F, 40.0F, 5.0F);
            animator.rotate(this.head, -5.0F, -50.0F, 0.0F);
            animator.rotate(this.rightArm, 40.0F, 30.0F, 45.0F);
            animator.rotate(this.rightForearm, -20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 55.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -50.0F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 20.0F, -20.0F, 0.0F);
            animator.rotate(this.leftFoot, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, -20.0F, 0.0F);
            animator.rotate(this.rightLegLower, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -20.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.emptyKeyframe(5.0F, Easing.LINEAR);
        }

        else if(entity.mainHandler.isPlaying(HollowArmor.DASH)) {

            walkMult = animator.disable(3.0F, 34.0F, 3.0F, (float) entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(20.0F);
            animator.rotate(this.model, 0.0F, -27.5F, 0.0F);
            animator.rotate(this.hip, -35.0F, -17.5F, -7.5F);
            animator.rotate(this.abdomen, 30.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 15.0F, -50.0F, -12.5F);
            animator.rotate(this.head, 20.0F, 80.0F, 0.0F);
            animator.rotate(this.rightForearm, -55.0F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 67.5F, -87.5F, 0.0F);
            animator.rotate(this.leftArm, 25.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -30.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, -0.2F, 0.0F);
            animator.rotate(this.rightLeg, -22.5F, 37.5F, 0.0F);
            animator.rotate(this.rightLegLower, 27.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -22.5F, 27.5F, 0.0F);
            animator.rotate(this.leftLegLower, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -2.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(6.0F);
            animator.rotate(this.model, 0.0F, 10.0F, 0.0F);
            animator.rotate(this.hip, 20.0F, 12.5F, 0.0F);
            animator.rotate(this.abdomen, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -2.5F, 10.0F, -12.5F);
            animator.rotate(this.head, 0.0F, -20.0F, 0.0F);
            animator.rotate(this.rightForearm, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.rightHand, 80.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 17.5F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -25.0F, 0.0F, 0.0F);
            animator.move(this.rightLeg, 0.0F, 1.1F, 0.0F);
            animator.rotate(this.rightLeg, -27.5F, -10.0F, 0.0F);
            animator.rotate(this.rightLegLower, 35.0F, 0.0F, 0.0F);
            animator.rotate(this.rightFoot, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 10.0F, -10.0F, 0.0F);
            animator.rotate(this.leftLegLower, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.leftFoot, -5.0F, 0.0F, 0.0F);
            animator.apply();

            animator.staticKeyframe(4.0F);

            animator.emptyKeyframe(10.0F, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(20.0F);
            animator.rotate(this.rightArm, -20.0F, -35.0F, 37.5F);
            animator.apply();

            animator.setupKeyframe(3.5F);
            animator.rotate(this.rightArm, 20.0F, 45.0F, 95.0F);
            animator.apply();

            animator.setupKeyframe(2.5F);
            animator.rotate(this.rightArm, 15.0F, -25.0F, 117.5F);
            animator.apply();

            animator.staticKeyframe(4.0F);

            animator.emptyKeyframe(10.0F, Easing.LINEAR);


            //
            animator.reset();

            animator.setupKeyframe(10.0F);
            animator.move(this.model, -0.1F, 0.3F, 0.4F);
            animator.apply();

            animator.setupKeyframe(10.0F);
            animator.move(this.model, 0.0F, 0.8F, 0.9F);
            animator.apply();

            animator.setupKeyframe(3.5F);
            animator.move(this.model, -0.1F, 0.25F, -0.15F);
            animator.apply();

            animator.setupKeyframe(2.5F);
            animator.move(this.model, 0.3F, 0.1F, -1.2F);
            animator.apply();

            animator.staticKeyframe(4.0F);

            animator.emptyKeyframe(10.0F, Easing.LINEAR);
        }

        else if(entity.getLocalAnimationHandler().isPlaying(entity.getDeathAnimation())) {
            EntityModelAnimator deathAnimator = new EntityModelAnimator(this, (float) entity.getLocalAnimationHandler().getTick() + getPartialTick());

            deathAnimator.setupKeyframe(4.0F);
            deathAnimator.move(this.model, 0.0F, 1.0F, -6.0F);
            deathAnimator.rotate(this.chest, 10.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.head, 20.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightArm, 10.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftArm, 10.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLeg, -15.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLegLower, 65.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightFoot, 20.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLeg, -15.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLegLower, 65.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftFoot, 20.0F, 0.0F, 0.0F);
            deathAnimator.apply();

            deathAnimator.setupKeyframe(4.0F);
            deathAnimator.move(this.model, 0.0F, 8.0F, -11.2F);
            deathAnimator.rotate(this.chest, -20.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.head, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightArm, -10.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftArm, -10.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.apply();

            deathAnimator.setupKeyframe(8.0F);
            deathAnimator.move(this.model, 0.0F, 8.0F, -11.2F);
            deathAnimator.rotate(this.hip, 60.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.chest, 15.0F, -10.0F, 0.0F);
            deathAnimator.rotate(this.head, 45.0F, 10.0F, 15.0F);
            deathAnimator.rotate(this.rightArm, -85.0F, 20.0F, 0.0F);
            deathAnimator.rotate(this.rightForearm, 20.0F, 0.0F, -10.0F);
            deathAnimator.rotate(this.rightHand, 0.0F, 0.0F, 80.0F);
            deathAnimator.rotate(this.leftArm, -90.0F, -5.0F, 0.0F);
            deathAnimator.rotate(this.leftForearm, 20.0F, 0.0F, 15.0F);
            deathAnimator.rotate(this.rightLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.apply();

            deathAnimator.setupKeyframe(8.0F);
            deathAnimator.move(this.model, 0.0F, 8.0F, -11.2F);
            deathAnimator.rotate(this.hip, 65.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.chest, 15.0F, -10.0F, 0.0F);
            deathAnimator.rotate(this.head, 55.0F, 10.0F, 15.0F);
            deathAnimator.rotate(this.rightArm, -90.0F, 20.0F, 0.0F);
            deathAnimator.rotate(this.rightForearm, 20.0F, 0.0F, -5.0F);
            deathAnimator.rotate(this.rightHand, 0.0F, 0.0F, 80.0F);
            deathAnimator.rotate(this.leftArm, -95.0F, -10.0F, 0.0F);
            deathAnimator.rotate(this.leftForearm, 20.0F, 0.0F, 20.0F);
            deathAnimator.rotate(this.rightLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.rightFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLeg, -30.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftLegLower, 130.0F, 0.0F, 0.0F);
            deathAnimator.rotate(this.leftFoot, 40.0F, 0.0F, 0.0F);
            deathAnimator.apply();
        }

        else if (entity.mainHandler.isPlaying(HollowArmor.HEAL)) {
            EntityModelAnimator animator1 = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());
            EntityModelAnimator posFixer = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());
            float headRotX = headPitch;
            float headRotY = headYaw;

            walkMult = animator.disable(3.0F, 19.0F, 4.0F, (float) entity.mainHandler.getTick() + getPartialTick());

            animator.setupKeyframe(6.0F);
            animator.rotate(this.model, 0.0F, -52.5F, 0.0F);
            animator.rotate(this.chest, 0.0F, -25.0F, 0.0F);
            animator.rotate(this.head, 50.0F - headRotX, 0.0F - headRotY, 0.0F);
            animator.rotate(this.rightArm, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, -27.5F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -25.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, 52.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(12.0F);
            animator.rotate(this.model, 0.0F, -52.5F, 0.0F);
            animator.rotate(this.abdomen, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -25.0F, 15.0F, 0.0F);
            animator.rotate(this.head, 2.5F - headRotX, -5.0F - headRotY, 0.0F);
            animator.rotate(this.rightArm, 72.5F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -32.5F, 0.0F, 0.0F);
            animator.rotate(this.leftHand, -22.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, 52.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.rotate(this.model, 0.0F, -52.5F, 0.0F);
            animator.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            animator.rotate(this.abdomen, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, 27.5F, 0.0F);
            animator.rotate(this.head, 5.0F - headRotX, 22.5F - headRotY, 0.0F);
            animator.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -32.5F, 0.0F, 0.0F);
            animator.rotate(this.leftHand, -22.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, 0.0F, 52.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(4.0F, Easing.LINEAR);

            //

            animator1.setupKeyframe(3.0F);
            animator1.rotate(this.leftArm, 27.5F, 15.0F, -15.0F);
            animator1.rotate(this.leftLeg, -45.0F, 0.0F, 0.0F);
            animator1.rotate(this.leftLegLower, 52.5F, 0.0F, 0.0F);
            animator1.apply();

            animator1.setupKeyframe(3.0F);
            animator1.rotate(this.leftArm, 57.5F, 30.0F, 47.5F);
            animator1.apply();

            animator1.setupKeyframe(6.0F);
            animator1.rotate(this.leftArm, -92.5F, 15.0F, -57.5F);
            animator1.apply();

            animator1.setupKeyframe(6.0F);
            animator1.rotate(this.leftArm, -72.5F, 10.0F, -60.0F);
            animator1.apply();

            animator1.setupKeyframe(2.0F);
            animator1.rotate(this.leftArm, 10.0F, 0.0F, -35.0F);
            animator1.apply();

            animator1.setupKeyframe(2.0F);
            animator1.rotate(this.leftArm, 77.5F, 0.0F, 37.5F);
            animator1.apply();

            animator1.setupKeyframe(2.0F);
            animator1.rotate(this.leftArm, 37.5F, 0.0F, -10.0F);
            animator1.rotate(this.leftLeg, -27.5F, 0.0F, 0.0F);
            animator1.rotate(this.leftLegLower, 32.5F, 0.0F, 0.0F);
            animator1.apply();

            animator1.emptyKeyframe(2.0F, Easing.LINEAR);

            //

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, -0.1F, 0.1F, 0.8F);
            posFixer.apply();

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, -0.4F, 0.1F, 1.5F);
            posFixer.apply();

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, -0.9F, 0.2F, 2.2F);
            posFixer.apply();

            posFixer.setupKeyframe(1.5F);
            posFixer.move(this.model, -1.5F, 0.2F, 2.9F);
            posFixer.apply();

            posFixer.staticKeyframe(16.0F);

            posFixer.setupKeyframe(1.0F);
            posFixer.move(this.model, -1.1F, 0.1F, 2.3F);
            posFixer.apply();

            posFixer.setupKeyframe(1.0F);
            posFixer.move(this.model, -0.5F, 0.1F, 1.6F);
            posFixer.apply();

            posFixer.setupKeyframe(1.0F);
            posFixer.move(this.model, -0.2F, 0.0F, 0.7F);
            posFixer.apply();

            posFixer.emptyKeyframe(1.0F, Easing.LINEAR);
        }

        //*/

        float easedlimbSwingAmount = Mth.lerp(getPartialTick(), entity.loopedWalkModule().prevRenderLimbSwingAmount, entity.loopedWalkModule().renderLimbSwingAmount);
        float f = Mth.clamp(easedlimbSwingAmount * 8.0F, (float) 0, 1.0F) * walkMult;
        float easedLimbSwing = caculateLimbSwingEasing(entity);

        EntityModelAnimator walk = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, (float) 0, 0.999F)).multiplier(f);

        walk.setupKeyframe(0.0F);
        walk.rotate(this.model, 0.0F, -5.0F, 0.0F);
        walk.rotate(this.chest, 0.0F, 10.0F, 0.0F);
        walk.rotate(this.rightArm, 30.0F, 0.0F, 0.0F);
        walk.rotate(this.rightForearm, -10.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, -17.5F, 0.0F, 0.0F);
        walk.rotate(this.leftForearm, 0.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.model, 0.0F, 5.0F, 0.0F);
        walk.rotate(this.chest, 0.0F, -10.0F, 0.0F);
        walk.rotate(this.rightArm, -17.5F, 0.0F, 0.0F);
        walk.rotate(this.rightForearm, 0.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, 30.0F, 0.0F, 0.0F);
        walk.rotate(this.leftForearm, -10.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.model, 0.0F, -5.0F, 0.0F);
        walk.rotate(this.chest, 0.0F, 10.0F, 0.0F);
        walk.rotate(this.rightArm, 30.0F, 0.0F, 0.0F);
        walk.rotate(this.rightForearm, -10.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, -17.5F, 0.0F, 0.0F);
        walk.rotate(this.leftForearm, 0.0F, 0.0F, 0.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.rotate(this.hip, 17.5F, 0.0F, 0.0F);
        walk.rotate(this.head, -7.5F, -5.0F, 0.0F);
        walk.rotate(this.rightLeg, -47.5F, 0.0F, -5.0F);
        walk.rotate(this.rightLegLower, 30.0F, 5.0F, 0.0F);
        walk.rotate(this.rightFoot, 17.5F, 5.0F, 0.0F);
        walk.rotate(this.leftLeg, 22.5F, 10.0F, 5.0F);
        walk.rotate(this.leftLegLower, 60.0F, -5.0F, 0.0F);
        walk.rotate(this.leftFoot, 27.5F, 0.0F, -5.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.head, -2.5F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, 0.0F, -5.0F, -5.0F);
        walk.rotate(this.rightLegLower, 7.5F, 5.0F, 0.0F);
        walk.rotate(this.rightFoot, -7.5F, 0.0F, 5.0F);
        walk.rotate(this.leftLeg, -22.5F, 5.0F, 5.0F);
        walk.rotate(this.leftLegLower, 85.0F, -5.0F, 0.0F);
        walk.rotate(this.leftFoot, 5.0F, 0.0F, -5.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 17.5F, 0.0F, 0.0F);
        walk.rotate(this.head, -7.5F, 5.0F, 0.0F);
        walk.rotate(this.rightLeg, 15.0F, -10.0F, -5.0F);
        walk.rotate(this.rightLegLower, 60.0F, 5.0F, 0.0F);
        walk.rotate(this.rightFoot, 27.5F, 00.0F, 5.0F);
        walk.rotate(this.leftLeg, -47.5F, 0.0F, 5.0F);
        walk.rotate(this.leftLegLower, 30.0F, -5.0F, 0.0F);
        walk.rotate(this.leftFoot, 17.5F, 0.0F, -5.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0.0F, 0.0F);
        walk.rotate(this.head, -2.5F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, -22.5F, -5.0F, -5.0F);
        walk.rotate(this.rightLegLower, 85.0F, 5.0F, 0.0F);
        walk.rotate(this.rightFoot, 5.0F, 0.0F, 5.0F);
        walk.rotate(this.leftLeg, 0.0F, 5.0F, 5.0F);
        walk.rotate(this.leftLegLower, 7.5F, -5.0F, 0.0F);
        walk.rotate(this.leftFoot, -7.5F, 0.0F, -5.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 17.5F, 0.0F, 0.0F);
        walk.rotate(this.head, -7.5F, -5.0F, 0.0F);
        walk.rotate(this.rightLeg, -47.5F, 0.0F, -5.0F);
        walk.rotate(this.rightLegLower, 30.0F, 5.0F, 0.0F);
        walk.rotate(this.rightFoot, 17.5F, 5.0F, 0.0F);
        walk.rotate(this.leftLeg, 22.5F, 10.0F, 5.0F);
        walk.rotate(this.leftLegLower, 60.0F, -5.0F, 0.0F);
        walk.rotate(this.leftFoot, 27.5F, 0.0F, -5.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.move(this.model, 0.0F, 2.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.75F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.35F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, -0.4F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, -0.325F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.905F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 2.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.75F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.9F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.35F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, -0.4F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, -0.325F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.905F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 2.9F, 0.0F);
        walk.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

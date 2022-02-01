package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MummyEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MummyModel extends BCEntityModel<MummyEntity> {
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    public final BCVoxel head;
    private final BCVoxel bone;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel rightHand;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel leftHand;
    private final BCVoxel rightLeg;
    private final BCVoxel rightCalf;
    private final BCVoxel leftLeg;
    private final BCVoxel leftCalf;
    private final AtomicDouble headYawMod = new AtomicDouble(1.0);

    public MummyModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 10.0F, 0.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        this.hip.texOffs(0, 0).addBox(-4.0F, -9.0F, -1.5F, 8.0F, 9.0F, 3.0F, 0.0F, false);
        this.hip.texOffs(0, 12).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 4.0F, 3.0F, 0.25F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -9.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0873F, 0.0F, 0.0F);
        this.chest.texOffs(22, 0).addBox(-5.0F, -5.0F, -2.0F, 10.0F, 5.0F, 4.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -5.0F, 0.0F);
        this.chest.addChild(this.head);
        this.head.texOffs(22, 26).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);
        this.head.texOffs(22, 9).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        this.head.texOffs(0, 51).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 4.0F, 9.0F, 0.0F, false);
        this.head.texOffs(27, 48).addBox(-3.5F, -8.0F, 3.5F, 7.0F, 10.0F, 2.0F, 0.0F, false);

        this.bone = new BCVoxel(this);
        this.bone.setPos(0.0F, -6.5672F, 1.6026F);
        this.head.addChild(this.bone);
        setRotationAngle(this.bone, -0.1745F, 0.0F, 0.0F);
        this.bone.texOffs(46, 48).addBox(3.5F, -1.0F, -2.5F, 3.0F, 10.0F, 6.0F, 0.0F, false);
        this.bone.texOffs(46, 48).addBox(-6.5F, -1.0F, -2.5F, 3.0F, 10.0F, 6.0F, 0.0F, true);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-5.0F, -3.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.0873F, 0.0F, 0.0F);
        this.rightArm.texOffs(8, 19).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
        this.rightArm.texOffs(8, 28).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.3F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.0F, 6.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        this.rightForearm.texOffs(8, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, false);

        this.rightHand = new BCVoxel(this);
        this.rightHand.setPos(0.0F, 6.0F, 0.0F);
        this.rightForearm.addChild(this.rightHand);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(5.0F, -3.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.0873F, 0.0F, 0.0F);
        this.leftArm.texOffs(8, 19).addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, true);
        this.leftArm.texOffs(8, 28).addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.3F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(1.0F, 6.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        this.leftForearm.texOffs(8, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, true);

        this.leftHand = new BCVoxel(this);
        this.leftHand.setPos(0.0F, 6.0F, 0.0F);
        this.leftForearm.addChild(this.leftHand);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-3.0F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(0, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
        this.rightLeg.texOffs(0, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.3F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 7.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(3.0F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(0, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, true);
        this.leftLeg.texOffs(0, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.3F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 7.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, true);
        
        setupDefaultState();
    }

    @Override
    public void handleRotations(MummyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.headYawMod.set(1.0);
        float idle1 = DEMath.sin(ageInTicks * 0.015F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.01F));
        this.chest.xRot += RAD * 2.0F * idle1;
        this.rightArm.xRot = (float) ((double) this.rightArm.xRot + (double) RAD * -2.5 * (double) idle1);
        this.leftArm.xRot = (float) ((double) this.leftArm.xRot + (double) RAD * -2.5 * (double) idle1);
        this.rightArm.zRot = (float) ((double) this.rightArm.zRot + (double) RAD * 1.5 * (double) idle2);
        this.leftArm.zRot = (float) ((double) this.leftArm.zRot + (double) RAD * -1.5 * (double) idle2);
        this.head.yRot = (float) ((double) this.head.yRot + (double) this.headYawMod.floatValue() * (double) headYaw * (double) RAD * 0.85);
        this.head.xRot += headPitch * RAD;
        this.chest.yRot = (float) ((double) this.chest.yRot + (double) this.headYawMod.floatValue() * (double) headYaw * (double) RAD * 0.15);

        walkOffsetY(this.body, 1.0F, 1.5F, (float) 0, 2.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.hip, 1.0F, 2.5F, 0.0F, 7.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.chest, 1.0F, 2.5F, 0.05F, 2.5F, limbSwing, limbSwingAmount, false);
        walkRotateY(this.chest, 0.5F, 5.0F, -0.05F, (float) 0, limbSwing, limbSwingAmount, false);
        walkRotateX(this.head, 1.0F, -7.5F, 0.15F, 0.0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightArm, 0.5F, 31.25F, 0.0F, 11.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightForearm, 0.5F, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftArm, 0.5F, 31.25F, 0.0F, 11.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftForearm, 0.5F, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightLeg, 0.5F, 35.0F, 0.0F, -12.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightCalf, 0.5F, -2.5F, 0.0F, 27.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftLeg, 0.5F, 35.0F, 0.0F, -12.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftCalf, 0.5F, -2.5F, 0.0F, 27.5F, limbSwing, limbSwingAmount, false);

        if (entity.mainHandler.isPlaying(MummyEntity.SUMMON_ABOMINATION)) {
            entity.rightHandVec = this.rightHand.getAbsolutePosition(Vec3.ZERO, getPartialTick(), entity);
            entity.leftHandVec = this.leftHand.getAbsolutePosition(Vec3.ZERO, getPartialTick(), entity);
        }
    }

    @Override
    public void handleKeyframedAnimations(MummyEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());

        if(entity.mainHandler.isPlaying(MummyEntity.RISE_SPIKE)) {
            animator.setupKeyframe(10.0F);
            animator.move(this.body, 0.0F, 0.0F, 0.65F);
            animator.rotate(this.body, 0.0F, 12.5F, 0.0F);
            animator.rotate(this.hip, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 10.0F, 15.0F, 0.0F);
            animator.rotate(this.head, -12.5F, -25.0F, 0.0F);
            animator.rotate(this.rightArm, 52.5F, 20.0F, 0.0F);
            animator.rotate(this.rightForearm, -77.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -22.5F, -15.0F, 0.0F);
            animator.rotate(this.rightLeg, -5.5F, -12.5F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -12.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(7.0F);
            animator.move(this.body, 0.0F, 0.0F, -0.8F);
            animator.rotate(this.body, 0.0F, -15.0F, 0.0F);
            animator.rotate(this.hip, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -12.5F, -12.5F, 0.0F);
            animator.rotate(this.head, 10.0F, 25.0F, 0.0F);
            animator.rotate(this.rightArm, -150.0F, 32.5F, 0.0F);
            animator.rotate(this.rightForearm, -27.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 35.0F, 5.0F, 0.0F);
            animator.rotate(this.rightLeg, 6.5F, 15.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, 15.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.staticKeyframe(3.0F);

            animator.emptyKeyframe(6.0F, Easing.EASE_IN_SQUARE);
        }

        else if(entity.mainHandler.isPlaying(MummyEntity.THROW_ORB)) {

            animator.setupKeyframe(13.0F);
            animator.rotate(this.hip, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, -17.5F, 0.0F, 0.0F);
            animator.rotate(this.head, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -207.5F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, -42.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -207.5F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -42.5F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_CUBIC);

            animator.setupKeyframe(7.0F);
            animator.rotate(this.hip, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 0.0F, 0.0F, 0.0F);
            animator.rotate(this.head, 12.5F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -117.5F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, -22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -117.5F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -22.5F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.emptyKeyframe(6.0F, Easing.EASE_IN_SQUARE);
        }

        else if(entity.mainHandler.isPlaying(MummyEntity.THROW_EGG)) {

            animator.disableAtomic(this.headYawMod, 4.0F, 18.0F, 4.0F);

            animator.setupKeyframe(6.0F);
            animator.move(this.body, 0.0F, 0.1F, 0.9F);
            animator.rotate(this.hip, -10.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 15.0F, headYaw * BCMath.FRAD * 90.0F, 0.0F);
            animator.rotate(this.head, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, 92.5F, 0.0F, 65.0F);
            animator.rotate(this.rightForearm, -110.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 92.5F, 0.0F, -65.0F);
            animator.rotate(this.leftForearm, -110.0F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.rightCalf, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, 7.5F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.staticKeyframe(2.0F);

            animator.setupKeyframe(3.0F);
            animator.move(this.body, 0.0F, 0.1F, 0.9F);
            animator.rotate(this.hip, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.chest, 32.5F, headYaw * BCMath.FRAD * 90.0F, 0.0F);
            animator.rotate(this.head, -52.5F, 0.0F, 0.0F);
            animator.rotate(this.rightArm, -97.5F, 42.5F, 70.0F);
            animator.rotate(this.leftArm, -97.5F, -42.5F, -70.0F);
            animator.rotate(this.rightLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.rightCalf, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, 7.5F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(8.0F, Easing.EASE_IN_SQUARE);
        } else if (entity.mainHandler.isPlaying(MummyEntity.SUMMON_ABOMINATION)) {

            animator.setupKeyframe(8.0F);
            animator.rotate(this.hip, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, 45.0F, 0.0F, 0.0F);
            animator.rotate(this.head, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, -80.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -80.0F, 0.0F, 0.0F);
            animator.apply();

            animator.staticKeyframe(3.0F);

            animator.setupKeyframe(4.0F);
            animator.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -35.0F, 0.0F, 0.0F);
            animator.rotate(this.head, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.rightForearm, -55.0F, 0.0F, 0.0F);
            animator.rotate(this.leftForearm, -55.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.emptyKeyframe(8.0F, Easing.EASE_IN_SQUARE);

            //
            animator.reset();

            animator.setupKeyframe(4.0F);
            animator.rotate(this.rightArm, 10.0F, 0.0F, 12.5F);
            animator.rotate(this.leftArm, 10.0F, 0.0F, -12.5F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.rotate(this.rightArm, 35.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 35.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3.0F);
            animator.rotate(this.rightArm, 20.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, 20.0F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.rotate(this.rightArm, -145.0F, 0.0F, 0.0F);
            animator.rotate(this.leftArm, -145.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(8.0F, Easing.EASE_IN_SQUARE);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

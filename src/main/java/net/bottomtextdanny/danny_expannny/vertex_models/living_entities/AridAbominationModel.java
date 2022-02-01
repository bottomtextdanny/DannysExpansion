package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.braincell.underlying.util.BCMath;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.AridAbominationEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class AridAbominationModel extends BCEntityModel<AridAbominationEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel rightHand;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel rightLeg;
    private final BCVoxel rightCalf;
    private final BCVoxel leftLeg;
    private final BCVoxel leftCalf;

    public AridAbominationModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -12.0F, 0.0F);
        this.model.addChild(this.body);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.0436F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-4.0F, -9.0F, -1.5F, 8.0F, 9.0F, 3.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -9.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.2618F, 0.0F, 0.0F);
        this.chest.texOffs(0, 12).addBox(-5.5F, -6.0F, -2.0F, 11.0F, 6.0F, 4.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -6.0F, 0.0F);
        this.chest.addChild(this.head);
        setRotationAngle(this.head, -0.0436F, 0.0F, 0.0F);
        this.head.texOffs(0, 22).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-5.5F, -4.5F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.2182F, 0.0F, 0.0F);
        this.rightArm.texOffs(22, 0).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.0F, 8.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        this.rightForearm.texOffs(30, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        this.rightHand = new BCVoxel(this);
        this.rightHand.setPos(0.0F, 8.0F, 0.0F);
        this.rightForearm.addChild(this.rightHand);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(5.5F, -4.5F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.2182F, 0.0F, 0.0F);
        this.leftArm.texOffs(22, 0).addBox(0.0F, -2.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(1.0F, 8.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        this.leftForearm.texOffs(30, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, true);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-2.0F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(38, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 6.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(38, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(2.0F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(38, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 6.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(38, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(AridAbominationEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        this.chest.xRot += BCMath.FRAD * 3.0F * idle1;
        this.rightArm.xRot += BCMath.FRAD * -4.0F * idle1;
        this.leftArm.xRot += BCMath.FRAD * -4.0F * idle1;
        this.rightArm.zRot += BCMath.FRAD * 2.0F * idle2;
        this.leftArm.zRot += BCMath.FRAD * -2.0F * idle2;

        this.head.yRot += headYaw * BCMath.FRAD * 0.8F;
        this.head.xRot += Mth.clamp(headPitch * BCMath.FRAD, -40.0F, 40.0F);
        this.chest.yRot += headYaw * BCMath.FRAD * 0.2F;
    }

    @Override
    public void handleKeyframedAnimations(AridAbominationEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());
        float walkMult = 1.0F;
	    
        if (entity.mainHandler.isPlaying(AridAbominationEntity.CLAP)) {
            EntityModelAnimator ambientAnimator = new EntityModelAnimator(this, (float) entity.mainHandler.getTick() + getPartialTick());

            ambientAnimator.setupKeyframe(3.0F);
            ambientAnimator.rotate(this.hip, -17.5F, 0.0F, 0.0F);
            ambientAnimator.rotate(this.head, 25.0F, 0.0F, 0.0F);
            ambientAnimator.apply();

            ambientAnimator.setupKeyframe(4.0F);
            ambientAnimator.rotate(this.hip, 5.0F, 0.0F, 0.0F);
            ambientAnimator.rotate(this.head, -25.0F, 0.0F, 0.0F);
            ambientAnimator.apply(Easing.EASE_OUT_SQUARE);

            ambientAnimator.emptyKeyframe(3.0F, Easing.LINEAR);
        }

        if (entity.mainHandler.isPlaying(AridAbominationEntity.CLAP)) {
            walkMult = animator.disable(4.0F, 10.0F, 4.0F);

            animator.setupKeyframe(7.0F);
            animator.move(this.model, 0.0F, -0.8F, 0.0F);
            animator.rotate(this.hip, -30.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -2.5F, 0.0F, 0.0F);
            animator.rotate(this.rightLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.rightCalf, 7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, -7.5F, 0.0F, 0.0F);
            animator.rotate(this.leftCalf, 7.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3.0F);
            animator.rotate(this.hip, 40.0F, 0.0F, 0.0F);
            animator.rotate(this.chest, -20.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(8.0F, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(7.0F);
            animator.rotate(this.rightArm, -105.0F, 82.5F, 0.0F);
            animator.rotate(this.leftArm, -105.0F, -82.5F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3.0F);
            animator.rotate(this.rightArm, -105.0F, -22.5F, 0.0F);
            animator.rotate(this.leftArm, -105.0F, 22.5F, 0.0F);
            animator.apply();

            animator.staticKeyframe(2.0F);

            animator.setupKeyframe(3.0F);
            animator.rotate(this.rightArm, -25.0F, -2.5F, 0.0F);
            animator.rotate(this.leftArm, -25.0F, 2.5F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(3.0F, Easing.LINEAR);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount * 8.0F, 0.0F, 1.0F) * walkMult;
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walk = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0.0F, 0.999F)).multiplier(f);
        EntityModelAnimator walkOffset = new EntityModelAnimator(this, Mth.clamp(DEMath.loop(easedLimbSwing, 1.0F, -0.15F), 0.0F, 0.999F)).multiplier(f);

        walk.setupKeyframe(0.0F);
        walk.rotate(this.chest, 7.5F, 2.5F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.chest, 7.5F, -2.5F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.chest, 7.5F, 2.5F, 0.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.rotate(this.hip, 15.0F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, -45.0F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 20.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, 32.5F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 15.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 10.0F, 0.0F, 0.0F);
        walk.rotate(this.rightArm, -5.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, -5.0F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, 0.0F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 0.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, -13.75F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 55.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 15.0F, 0.0F, 0.0F);
        walk.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, 5.0F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, 32.5F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 15.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, -45.0F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 20.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 10.0F, 0.0F, 0.0F);
        walk.rotate(this.rightArm, -5.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, -5.0F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, -13.75F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 55.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, 0.0F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 0.0F, 0.0F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 15.0F, 0.0F, 0.0F);
        walk.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
        walk.rotate(this.leftArm, 5.0F, 0.0F, 0.0F);
        walk.rotate(this.rightLeg, -45.0F, 0.0F, 0.0F);
        walk.rotate(this.rightCalf, 20.0F, 0.0F, 0.0F);
        walk.rotate(this.leftLeg, 32.5F, 0.0F, 0.0F);
        walk.rotate(this.leftCalf, 15.0F, 0.0F, 0.0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.move(this.model, 0.0F, 2.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.18F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.55F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.22F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.13F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.35F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 2.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.18F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.55F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.22F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.1F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.13F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 0.35F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 1.2F, 0.0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.model, 0.0F, 2.2F, 0.0F);
        walk.apply();

        //

        walkOffset.setupKeyframe(0.0F);
        walkOffset.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
        walkOffset.rotate(this.leftArm, 5.0F, 0.0F, 0.0F);
        walkOffset.apply();

        walkOffset.setupKeyframe(0.25F);
        walkOffset.rotate(this.rightArm, -5.0F, 0.0F, 0.0F);
        walkOffset.rotate(this.leftArm, -5.0F, 0.0F, 0.0F);
        walkOffset.apply();

        walkOffset.setupKeyframe(0.25F);
        walkOffset.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
        walkOffset.rotate(this.leftArm, 5.0F, 0.0F, 0.0F);
        walkOffset.apply();

        walkOffset.setupKeyframe(0.25F);
        walkOffset.rotate(this.rightArm, -5.0F, 0.0F, 0.0F);
        walkOffset.rotate(this.leftArm, -5.0F, 0.0F, 0.0F);
        walkOffset.apply();

        walkOffset.setupKeyframe(0.25F);
        walkOffset.rotate(this.rightArm, 5.0F, 0.0F, 0.0F);
        walkOffset.rotate(this.leftArm, 5.0F, 0.0F, 0.0F);
        walkOffset.apply();
        
        if (entity.mainHandler.isPlaying(AridAbominationEntity.CLAP)) {
        	entity.rightHandPosition = this.rightHand.getAbsolutePosition(Vec3.ZERO, getPartialTick(), entity);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

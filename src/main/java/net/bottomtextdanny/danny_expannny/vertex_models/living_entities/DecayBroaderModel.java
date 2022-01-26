package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DecayBroaderEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class DecayBroaderModel extends BCEntityModel<DecayBroaderEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel headWhole;
    private final BCVoxel dontUseT;
    private final BCVoxel cap;
    private final BCVoxel rightFrontLeg;
    private final BCVoxel rightFrontCalf;
    private final BCVoxel leftFrontLeg;
    private final BCVoxel leftFrontCalf;
    private final BCVoxel leftBackLeg;
    private final BCVoxel leftBackCalf;
    private final BCVoxel rightBackLeg;
    private final BCVoxel rightBackCalf;

    public DecayBroaderModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -4.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);

        this.headWhole = new BCVoxel(this);
        this.headWhole.setPos(0.0F, -4.0F, 0.0F);
        this.body.addChild(this.headWhole);


        this.dontUseT = new BCVoxel(this);
        this.dontUseT.setPos(0.0F, 0.0F, 0.0F);
        this.headWhole.addChild(this.dontUseT);
        setRotationAngle(this.dontUseT, 0.0F, 0.7854F, 0.0F);
        this.dontUseT.texOffs(0, 7).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);

        this.cap = new BCVoxel(this);
        this.cap.setPos(0.0F, -9.0F, 0.0F);
        this.dontUseT.addChild(this.cap);
        setRotationAngle(this.cap, 0.0F, -0.7854F, 0.0F);
        this.cap.texOffs(32, 0).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 10.0F, 16.0F, 0.0F, false);

        this.rightFrontLeg = new BCVoxel(this);
        this.rightFrontLeg.setPos(0.0F, -1.5F, 0.0F);
        this.body.addChild(this.rightFrontLeg);
        setRotationAngle(this.rightFrontLeg, -0.4363F, 0.7854F, 0.0F);
        this.rightFrontLeg.texOffs(0, 26).addBox(-1.5F, -1.5F, -15.0F, 3.0F, 3.0F, 15.0F, 0.0F, false);

        this.rightFrontCalf = new BCVoxel(this);
        this.rightFrontCalf.setPos(0.0F, 0.0F, -13.5F);
        this.rightFrontLeg.addChild(this.rightFrontCalf);
        setRotationAngle(this.rightFrontCalf, 0.0873F, 0.0F, 0.0F);
        this.rightFrontCalf.texOffs(0, 44).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        this.leftFrontLeg = new BCVoxel(this);
        this.leftFrontLeg.setPos(0.0F, -1.5F, 0.0F);
        this.body.addChild(this.leftFrontLeg);
        setRotationAngle(this.leftFrontLeg, -0.4363F, -0.7854F, 0.0F);
        this.leftFrontLeg.texOffs(0, 26).addBox(-1.5F, -1.5F, -15.0F, 3.0F, 3.0F, 15.0F, 0.0F, false);

        this.leftFrontCalf = new BCVoxel(this);
        this.leftFrontCalf.setPos(0.0F, 0.0F, -13.5F);
        this.leftFrontLeg.addChild(this.leftFrontCalf);
        setRotationAngle(this.leftFrontCalf, 0.0873F, 0.0F, 0.0F);
        this.leftFrontCalf.texOffs(0, 44).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        this.leftBackLeg = new BCVoxel(this);
        this.leftBackLeg.setPos(0.0F, -1.5F, 0.0F);
        this.body.addChild(this.leftBackLeg);
        setRotationAngle(this.leftBackLeg, -0.4363F, -2.3562F, 0.0F);
        this.leftBackLeg.texOffs(0, 26).addBox(-1.5F, -1.5F, -15.0F, 3.0F, 3.0F, 15.0F, 0.0F, false);

        this.leftBackCalf = new BCVoxel(this);
        this.leftBackCalf.setPos(0.0F, 0.0F, -13.5F);
        this.leftBackLeg.addChild(this.leftBackCalf);
        setRotationAngle(this.leftBackCalf, 0.0873F, 0.0F, 0.0F);
        this.leftBackCalf.texOffs(0, 44).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        this.rightBackLeg = new BCVoxel(this);
        this.rightBackLeg.setPos(0.0F, -1.5F, 0.0F);
        this.body.addChild(this.rightBackLeg);
        setRotationAngle(this.rightBackLeg, -0.4363F, 2.3562F, 0.0F);
        this.rightBackLeg.texOffs(0, 26).addBox(-1.5F, -1.5F, -15.0F, 3.0F, 3.0F, 15.0F, 0.0F, false);

        this.rightBackCalf = new BCVoxel(this);
        this.rightBackCalf.setPos(0.0F, 0.0F, -13.5F);
        this.rightBackLeg.addChild(this.rightBackCalf);
        setRotationAngle(this.rightBackCalf, 0.0873F, 0.0F, 0.0F);
        this.rightBackCalf.texOffs(0, 44).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(DecayBroaderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        float capIdleSin = DEMath.sin(ageInTicks * 0.2F) * 0.05F;
        float capIdleCos = DEMath.cos(ageInTicks * 0.2F) * 0.05F;

        setSize(this.cap, 1.05F + capIdleSin, 1.05F + capIdleCos, 1.05F + capIdleSin);
    }

    @Override
    public void handleKeyframedAnimations(DecayBroaderEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.throwProjectile)) {

            animator.setupKeyframe(7F);
            animator.move(this.body, 0F, 2.0F, 0F);
            animator.scale(this.cap, 0.3F, -0.3F, 0.3F);
            animator.rotate(this.rightFrontLeg, -8.0F, 0F, 0F);
            animator.rotate(this.rightFrontCalf, 3.0F, 0F, 0F);
            animator.rotate(this.rightBackLeg, -8.0F, 0F, 0F);
            animator.rotate(this.rightBackCalf, 3.0F, 0F, 0F);
            animator.rotate(this.leftFrontLeg, -8.0F, 0F, 0F);
            animator.rotate(this.leftFrontCalf, 3.0F, 0F, 0F);
            animator.rotate(this.leftBackLeg, -8.0F, 0F, 0F);
            animator.rotate(this.leftBackCalf, 3.0F, 0F, 0F);
            animator.apply();

            animator.setupKeyframe(3F);
            animator.move(this.body, 0F, -4.0F, 0F);
            animator.scale(this.cap, -0.3F, 0.5F, -0.3F);
            animator.rotate(this.rightFrontLeg, 16.0F, 0F, 0F);
            animator.rotate(this.rightFrontCalf, -10.0F, 0F, 0F);
            animator.rotate(this.rightBackLeg, 16.0F, 0F, 0F);
            animator.rotate(this.rightBackCalf, -10.0F, 0F, 0F);
            animator.rotate(this.leftFrontLeg, 16.0F, 0F, 0F);
            animator.rotate(this.leftFrontCalf, -10.0F, 0F, 0F);
            animator.rotate(this.leftBackLeg, 16.0F, 0F, 0F);
            animator.rotate(this.leftBackCalf, -10.0F, 0F, 0F);
            animator.apply();


            animator.emptyKeyframe(7F, Easing.LINEAR);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount / 0.125F, 0, 1);
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walkAnimator = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);
        
        walkAnimator.setupKeyframe(0F);
        walkAnimator.move(this.model, 0F, 0F, 0F);
        walkAnimator.rotate(this.headWhole, 0F, 7.5F, 0F);
        walkAnimator.rotate(this.rightFrontLeg, 5.0F, -12.5F, 0F);
        walkAnimator.rotate(this.rightFrontCalf, -20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftFrontLeg, -2.5F, -30.0F, 0F);
        walkAnimator.rotate(this.leftFrontCalf, 20.0F, 0F, 0F);
        walkAnimator.rotate(this.rightBackLeg, 5.0F, 12.5F, 0F);
        walkAnimator.rotate(this.rightBackCalf, -20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftBackLeg, -2.5F, 30.0F, 0F);
        walkAnimator.rotate(this.leftBackCalf, 20.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.move(this.model, 0F, -2.0F, 0F);
        walkAnimator.rotate(this.headWhole, 7.5F, 0F, 0F);
        walkAnimator.rotate(this.rightFrontLeg, 7.5F, 10.0F, 0F);
        walkAnimator.rotate(this.rightFrontCalf, 0F, 0F, 0F);
        walkAnimator.rotate(this.leftFrontLeg, -20.0F, -7.5F, 0F);
        walkAnimator.rotate(this.leftFrontCalf, 22.5F, 0F, 0F);
        walkAnimator.rotate(this.rightBackLeg, -20.0F, -7.5F, 0F);
        walkAnimator.rotate(this.rightBackCalf, 22.5F, 0F, 0F);
        walkAnimator.rotate(this.leftBackLeg, 7.5F, 10.0F, 0F);
        walkAnimator.rotate(this.leftBackCalf, 0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.move(this.model, 0F, 0F, 0F);
        walkAnimator.rotate(this.headWhole, 0F, -7.5F, 0F);
        walkAnimator.rotate(this.rightFrontLeg, -2.5F, 30.0F, 0F);
        walkAnimator.rotate(this.rightFrontCalf, 20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftFrontLeg, 5.0F, 12.5F, 0F);
        walkAnimator.rotate(this.leftFrontCalf, -20.0F, 0F, 0F);
        walkAnimator.rotate(this.rightBackLeg, -2.5F, -30.0F, 0F);
        walkAnimator.rotate(this.rightBackCalf, 20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftBackLeg, 5.0F, -12.5F, 0F);
        walkAnimator.rotate(this.leftBackCalf, -20.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.move(this.model, 0F, -2.0F, 0F);
        walkAnimator.rotate(this.headWhole, 7.5F, 0F, 0F);
        walkAnimator.rotate(this.rightFrontLeg, -20.0F, -7.5F, 0F);
        walkAnimator.rotate(this.rightFrontCalf, 22.5F, 0F, 0F);
        walkAnimator.rotate(this.leftFrontLeg, 7.5F, -10.0F, 0F);
        walkAnimator.rotate(this.leftFrontCalf, 0F, 0F, 0F);
        walkAnimator.rotate(this.rightBackLeg, 7.5F, -10.0F, 0F);
        walkAnimator.rotate(this.rightBackCalf, 0F, 0F, 0F);
        walkAnimator.rotate(this.leftBackLeg, -20.0F, -7.5F, 0F);
        walkAnimator.rotate(this.leftBackCalf, 22.5F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.move(this.model, 0F, 0F, 0F);
        walkAnimator.rotate(this.headWhole, 0F, 7.5F, 0F);
        walkAnimator.rotate(this.rightFrontLeg, 5.0F, -12.5F, 0F);
        walkAnimator.rotate(this.rightFrontCalf, -20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftFrontLeg, -2.5F, -30.0F, 0F);
        walkAnimator.rotate(this.leftFrontCalf, 20.0F, 0F, 0F);
        walkAnimator.rotate(this.rightBackLeg, 5.0F, 12.5F, 0F);
        walkAnimator.rotate(this.rightBackCalf, -20.0F, 0F, 0F);
        walkAnimator.rotate(this.leftBackLeg, -2.5F, 30.0F, 0F);
        walkAnimator.rotate(this.leftBackCalf, 20.0F, 0F, 0F);
        walkAnimator.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

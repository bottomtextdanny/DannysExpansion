package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental.IceElemental;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;

public class IceElementalModel extends BCEntityModel<IceElemental> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel bodyZRotator;
    private final BCVoxel wingOne;
    private final BCVoxel wingOneRotator;
    private final BCVoxel wingTwo;
    private final BCVoxel wingTwoRotator;
    private final BCVoxel wingThree;
    private final BCVoxel wingThreeRotator;

    public IceElementalModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -5.0F, 0.0F);
        this.model.addChild(this.body);


        this.bodyZRotator = new BCVoxel(this);
        this.bodyZRotator.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.bodyZRotator);
        this.bodyZRotator.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.wingOne = new BCVoxel(this);
        this.wingOne.setPos(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingOne);
        setRotationAngle(this.wingOne, 0.0F, 0.0F, -2.0944F);


        this.wingOneRotator = new BCVoxel(this);
        this.wingOneRotator.setPos(0.0F, -7.0F, 0.0F);
        this.wingOne.addChild(this.wingOneRotator);
        this.wingOneRotator.texOffs(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingOneRotator.texOffs(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);

        this.wingTwo = new BCVoxel(this);
        this.wingTwo.setPos(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingTwo);


        this.wingTwoRotator = new BCVoxel(this);
        this.wingTwoRotator.setPos(0.0F, -7.0F, 0.0F);
        this.wingTwo.addChild(this.wingTwoRotator);
        this.wingTwoRotator.texOffs(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingTwoRotator.texOffs(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);

        this.wingThree = new BCVoxel(this);
        this.wingThree.setPos(0.0F, 0.0F, 0.0F);
        this.bodyZRotator.addChild(this.wingThree);
        setRotationAngle(this.wingThree, 0.0F, 0.0F, 2.0944F);


        this.wingThreeRotator = new BCVoxel(this);
        this.wingThreeRotator.setPos(0.0F, -7.0F, 0.0F);
        this.wingThree.addChild(this.wingThreeRotator);
        this.wingThreeRotator.texOffs(0, 16).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        this.wingThreeRotator.texOffs(6, 16).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 11.0F, 0.0F, 0.0F, false);
        setupDefaultState();
    }

    @Override
    public void handleRotations(IceElemental entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float wingRotationCycle = DEMath.sin(ageInTicks * 0.025F);
        float bodyRotationSin = DEMath.sin(ageInTicks * 0.1F);
        float bodyRotationCos = DEMath.cos(ageInTicks * 0.1F);

        this.body.yRot = headYaw * RAD;
        this.body.xRot += headPitch * RAD;
        this.body.xRot += bodyRotationSin * 5.0F * RAD;
        this.body.zRot += bodyRotationCos * 5.0F * RAD;
        this.wingOne.zRot += DEMath.FPI_BY_TWO * wingRotationCycle;
        this.wingTwo.zRot += DEMath.FPI_BY_TWO * wingRotationCycle;
        this.wingThree.zRot += DEMath.FPI_BY_TWO * wingRotationCycle;
    }

    @Override
    public void handleKeyframedAnimations(IceElemental entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.mainHandler.isPlaying(IceElemental.ICE_SPIKE_ANIMATION)) {
            EntityModelAnimator attackAnimator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

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

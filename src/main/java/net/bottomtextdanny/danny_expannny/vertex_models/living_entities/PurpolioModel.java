package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.PurpolioEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class PurpolioModel extends BCEntityModel<PurpolioEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel head;
    private final BCVoxel rightFirstLeg;
    private final BCVoxel rightSecondLeg;
    private final BCVoxel rightThirdLeg;
    private final BCVoxel leftFirstLeg;
    private final BCVoxel leftSecondLeg;
    private final BCVoxel leftThirdLeg;

    public PurpolioModel() {

        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -3.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-8.0F, -10.0F, -9.0F, 16.0F, 7.0F, 18.0F, 0.0F, false);
        this.body.texOffs(0, 25).addBox(-6.0F, -3.0F, -8.0F, 12.0F, 3.0F, 15.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -4.0F, -8.0F);
        this.body.addChild(this.head);
        setRotationAngle(this.head, 0.2182F, 0.0F, 0.0F);
        this.head.texOffs(0, 43).addBox(-5.0F, -3.0F, -7.0F, 10.0F, 7.0F, 7.0F, 0.0F, false);

        this.rightFirstLeg = new BCVoxel(this);
        this.rightFirstLeg.setPos(-6.0F, -1.0F, -5.0F);
        this.body.addChild(this.rightFirstLeg);
        setRotationAngle(this.rightFirstLeg, -0.9599F, 0.7854F, 0.0F);
        this.rightFirstLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.rightSecondLeg = new BCVoxel(this);
        this.rightSecondLeg.setPos(-6.0F, -1.0F, 0.0F);
        this.body.addChild(this.rightSecondLeg);
        setRotationAngle(this.rightSecondLeg, -0.9599F, 1.5708F, 0.0F);
        this.rightSecondLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.rightThirdLeg = new BCVoxel(this);
        this.rightThirdLeg.setPos(-6.0F, -1.0F, 5.0F);
        this.body.addChild(this.rightThirdLeg);
        setRotationAngle(this.rightThirdLeg, -0.9599F, 2.1817F, 0.0F);
        this.rightThirdLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        this.leftFirstLeg = new BCVoxel(this);
        this.leftFirstLeg.setPos(6.0F, -1.0F, -5.0F);
        this.body.addChild(this.leftFirstLeg);
        setRotationAngle(this.leftFirstLeg, -0.9599F, -0.7854F, 0.0F);
        this.leftFirstLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        this.leftSecondLeg = new BCVoxel(this);
        this.leftSecondLeg.setPos(6.0F, -1.0F, 0.0F);
        this.body.addChild(this.leftSecondLeg);
        setRotationAngle(this.leftSecondLeg, -0.9599F, -1.5708F, 0.0F);
        this.leftSecondLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        this.leftThirdLeg = new BCVoxel(this);
        this.leftThirdLeg.setPos(6.0F, -1.0F, 5.0F);
        this.body.addChild(this.leftThirdLeg);
        setRotationAngle(this.leftThirdLeg, -0.9599F, -2.1817F, 0.0F);
        this.leftThirdLeg.texOffs(0, 57).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(PurpolioEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.xRot = Mth.clamp(headPitch, -30, 30) * RAD;
        this.head.yRot = Mth.clamp(headYaw, -30, 30) * RAD;
    }

    @Override
    public void handleKeyframedAnimations(PurpolioEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.hurtAnimation.isWoke()) {
            EntityModelAnimator animator = new EntityModelAnimator(this, entity.hurtModule.getTick() + net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK);

            animator.setupKeyframe(2);
            animator.move(this.body, 0F, 2F, 0F);
            animator.rotate(this.body, 0.0F, 0F, -2.5F);
            animator.rotate(this.rightFirstLeg, -10.0F, 0F, 0F);
            animator.rotate(this.rightSecondLeg, -10.0F, 0F, 0F);
            animator.rotate(this.rightThirdLeg, -10.0F, 0F, 0F);
            animator.rotate(this.leftFirstLeg, -10.0F, 0F, 0F);
            animator.rotate(this.leftSecondLeg, -10.0F, 0F, 0F);
            animator.rotate(this.leftThirdLeg, -10.0F, 0F, 0F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);
        }

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount / 0.125F, 0, 1);
        float easedLimbSwing = caculateLimbSwingEasing(entity);
        EntityModelAnimator walkAnimator = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);

        walkAnimator.setupKeyframe(0F);
        walkAnimator.rotate(this.rightFirstLeg, 0F, -41.0F, 0F);
        walkAnimator.rotate(this.leftSecondLeg, 0F, 41.0F, 0F);
        walkAnimator.rotate(this.rightThirdLeg, 0F, -41.0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.5F);
        walkAnimator.rotate(this.rightFirstLeg, 0F, 41.0F, 0F);
        walkAnimator.rotate(this.leftSecondLeg, 0F, -41.0F, 0F);
        walkAnimator.rotate(this.rightThirdLeg, 0F, 41.0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.rightFirstLeg, -35.0F, 0F, 0F);
        walkAnimator.rotate(this.leftSecondLeg, -35.0F, 0F, 0F);
        walkAnimator.rotate(this.rightThirdLeg, -35.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.rightFirstLeg, 0F, -41.0F, 0F);
        walkAnimator.rotate(this.leftSecondLeg, 0F, 41.0F, 0F);
        walkAnimator.rotate(this.rightThirdLeg, 0F, -41.0F, 0F);
        walkAnimator.apply();

        //

        walkAnimator.reset();
        walkAnimator.setTimer(DEMath.loop(easedLimbSwing, 1, 0.5F));

        walkAnimator.setupKeyframe(0F);
        walkAnimator.rotate(this.rightSecondLeg, 0F, -41.0F, 0F);
        walkAnimator.rotate(this.leftFirstLeg, 0F, 41.0F, 0F);
        walkAnimator.rotate(this.leftThirdLeg, 0F, 41.0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.5F);
        walkAnimator.rotate(this.rightSecondLeg, 0F, 41.0F, 0F);
        walkAnimator.rotate(this.leftFirstLeg,  0F, -41.0F, 0F);
        walkAnimator.rotate(this.leftThirdLeg, 0F, -41.0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.rightSecondLeg, -35.0F, 0F, 0F);
        walkAnimator.rotate(this.leftFirstLeg,  -35.0F, 0F, 0F);
        walkAnimator.rotate(this.leftThirdLeg, -35.0F, 0F, 0F);
        walkAnimator.apply();

        walkAnimator.setupKeyframe(0.25F);
        walkAnimator.rotate(this.rightSecondLeg, 0F, -41.0F, 0F);
        walkAnimator.rotate(this.leftFirstLeg,  0F, 41.0F, 0F);
        walkAnimator.rotate(this.leftThirdLeg, 0F, 41.0F, 0F);
        walkAnimator.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

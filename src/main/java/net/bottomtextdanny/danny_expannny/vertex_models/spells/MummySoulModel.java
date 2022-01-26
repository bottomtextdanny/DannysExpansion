package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.MummySoulEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class MummySoulModel extends BCEntityModel<MummySoulEntity> {
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel head;
    private final BCVoxel jaw;

    public MummySoulModel() {
        this.texWidth = 52;
        this.texHeight = 58;

        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-5.0F, -6.0F, -3.5F, 10.0F, 6.0F, 7.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -6.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0873F, 0.0F, 0.0F);
        this.chest.texOffs(0, 13).addBox(-7.0F, -8.0F, -5.5F, 14.0F, 8.0F, 10.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-7.0F, -5.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, 0.2182F, -0.2618F, 0.0873F);
        this.rightArm.texOffs(0, 31).addBox(-5.0F, -2.0F, -2.5F, 5.0F, 9.0F, 5.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-2.5F, 7.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        setRotationAngle(this.rightForearm, -0.6981F, 0.0F, 0.0F);
        this.rightForearm.texOffs(0, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(7.0F, -5.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, 0.2182F, 0.2618F, -0.0873F);
        this.leftArm.texOffs(0, 31).addBox(0.0F, -2.0F, -2.5F, 5.0F, 9.0F, 5.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(2.5F, 7.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        setRotationAngle(this.leftForearm, -0.6981F, 0.0F, 0.0F);
        this.leftForearm.texOffs(0, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, true);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -8.0F, -3.0F);
        this.chest.addChild(this.head);
        this.head.texOffs(20, 31).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, false);

        this.jaw = new BCVoxel(this);
        this.jaw.setPos(0.0F, -3.0F, 4.0F);
        this.head.addChild(this.jaw);
        this.jaw.texOffs(16, 45).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 3.0F, 7.0F, 0.4F, false);

        setupDefaultState();
    }

    @Override
    public void handleKeyframedAnimations(MummySoulEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0);
        animator.scale(this.hip, -1.0F, -1.0F, -1.0F);
        animator.apply();

        animator.setupKeyframe(7);
        animator.scale(this.hip, 0.5F, 0.5F, 0.5F);
        animator.rotate(this.hip, -12.5F, 22.5F, 0F);
        animator.rotate(this.chest, 5.0F, 30.0F, 0F);
        animator.rotate(this.head, 0F, -50.0F, 0F);
        animator.rotate(this.rightArm, 42.5F, 0F, 90.0F);
        animator.rotate(this.rightForearm, -50.0F, 0F, 0F);
        animator.rotate(this.leftArm, 37.5F, 0F, 0F);
        animator.rotate(this.leftForearm, -52.5F, 0F, 0F);
        animator.apply(Easing.EASE_OUT_SQUARE);

        animator.setupKeyframe(7);
        animator.scale(this.hip, 0.5F, 0.5F, 0.5F);
        animator.rotate(this.hip, 17.5F, -35.0F, 0F);
        animator.rotate(this.chest, 5.0F, -30.0F, 0F);
        animator.rotate(this.head, 0F, 65.0F, 0F);
        animator.rotate(this.rightArm, -50.0F, 0F, 115.0F);
        animator.rotate(this.rightForearm, 17.5F, 0F, 0F);
        animator.rotate(this.leftArm, 37.5F, 0F, -32.5F);
        animator.rotate(this.leftForearm, -25.0F, 0F, 0F);
        animator.apply(Easing.EASE_IN_CUBIC);

        animator.setupKeyframe(7);
        animator.scale(this.hip, -1.0F, -1.0F, -1.0F);
        animator.apply(Easing.EASE_OUT_CUBIC);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.hip.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

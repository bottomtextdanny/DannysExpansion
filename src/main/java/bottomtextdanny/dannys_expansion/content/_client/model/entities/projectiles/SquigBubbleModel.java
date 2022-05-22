package bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles;

import bottomtextdanny.dannys_expansion.content.entities.projectile.SquigBubbleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;

public class SquigBubbleModel extends BCEntityModel<SquigBubbleEntity> {
    private final BCJoint model;
    private final BCJoint rotator;
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);

    public SquigBubbleModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCJoint(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.rotator = new BCJoint(this);
        this.rotator.setPosCore(0.0F, -4.0F, 0.0F);
        this.model.addChild(this.rotator);
        this.rotator.uvOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void handleRotations(SquigBubbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        this.rotator.yRot += BCMath.sin(ageInTicks * 0.025F + 10.0F) * Math.PI;
        this.rotator.zRot += BCMath.sin(ageInTicks * 0.015F + 20.0F) * Math.PI;
    }

    @Override
    public void handleKeyframedAnimations(SquigBubbleEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        animator.setTimer(entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0.0F);
        animator.scale(this.rotator, -1.0F, -1.0F, -1.0F);
        animator.apply();

        animator.emptyKeyframe(23.0F, Easing.BOUNCE_OUT);

        animator.reset();

        if (entity.mainHandler.isPlaying(SquigBubbleEntity.POP)) {
            animator.setTimer(entity.mainHandler.linearProgress());

            animator.setupKeyframe(2.0F);
            animator.scale(this.rotator, -0.3F, -0.3F, -0.3F);
            animator.apply();
            animator.setupKeyframe(5.0F);
            animator.scale(this.rotator, 0.8F, 0.8F, 0.8F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.reset();
        }

        if (entity.hurtModule.isPlaying(SquigBubbleEntity.HURT)) {
            animator.setTimer(entity.hurtModule.linearProgress());

            animator.setupKeyframe(2.0F);
            animator.scale(this.rotator, -0.3F, -0.3F, -0.3F);
            animator.apply();
            animator.setupKeyframe(5.0F);
            animator.scale(this.rotator, 0.8F, 0.8F, 0.8F);
            animator.apply(Easing.EASE_IN_CUBIC);
            animator.emptyKeyframe(3.0F, Easing.LINEAR);

            animator.reset();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

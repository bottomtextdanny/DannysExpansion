package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SandScarabEggEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class SandScarabEggModel extends BCEntityModel<SandScarabEggEntity> {
    private final BCVoxel egg;

    public SandScarabEggModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.egg = new BCVoxel(this);
        this.egg.setPos(0.0F, -6.0F, 0.0F);
        this.egg.texOffs(0, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(SandScarabEggEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.egg.xRot += Mth.lerp(getPartialTick(), entity.prevMotionToRot, entity.motionToRot) + ageInTicks * 0.5;
    }

    @Override
    public void handleKeyframedAnimations(SandScarabEggEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0);
        animator.scale(this.egg, -1.0F, -1.0F, -1.0F);
        animator.apply();

        animator.emptyKeyframe(5, Easing.EASE_OUT_CUBIC);

        if (entity.breakAnimation.isWoke()) {
            EntityModelAnimator ambientAnimator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

            ambientAnimator.setupKeyframe(5);
            ambientAnimator.scale(this.egg, 0.5F, 0.3F, 0.5F);
            ambientAnimator.apply(Easing.EASE_IN_CUBIC);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.egg.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.BarrenOrbEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class BarrenOrbModel extends BCEntityModel<BarrenOrbEntity> {
    private final BCVoxel orb;

    public BarrenOrbModel() {
        this.texWidth = 40;
        this.texHeight = 40;

        this.orb = new BCVoxel(this);
        this.orb.setPos(0.0F, -6.0F, 0.0F);
        this.orb.texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
        this.orb.texOffs(0, 20).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 1.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(BarrenOrbEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
    }

    @Override
    public void handleKeyframedAnimations(BarrenOrbEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0);
        animator.rotate(this.orb, 0.0F, -900.0F, 0.0F);
        animator.apply();

        animator.emptyKeyframe(14, Easing.EASE_OUT_CUBIC);

        animator.reset();

        animator.setupKeyframe(0);
        animator.scale(this.orb, -1.0F, -1.0F, -1.0F);
        animator.apply();

        animator.setupKeyframe(5);
        animator.scale(this.orb, 0.5F, 0.5F, 0.5F);
        animator.apply();
        animator.emptyKeyframe(5, Easing.BOUNCE_OUT);

        if (entity.getLifeTick() <= 14) {
            entity.spillYaw = (float) Math.toDegrees(this.orb.yRot);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.orb.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

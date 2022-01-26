package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.DeserticFangEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class DeserticFangModel extends BCEntityModel<DeserticFangEntity> {
    private final BCVoxel bottom;
    private final BCVoxel middle;
    private final BCVoxel upper;

    public DeserticFangModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.bottom = new BCVoxel(this);
        this.bottom.setPos(0.0F, 0.0F, 0.0F);
        this.bottom.texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

        this.middle = new BCVoxel(this);
        this.middle.setPos(0.0F, -8.0F, 0.0F);
        this.bottom.addChild(this.middle);
        this.middle.texOffs(0, 14).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        this.upper = new BCVoxel(this);
        this.upper.setPos(0.0F, -9.0F, 0.0F);
        this.middle.addChild(this.upper);
        this.upper.texOffs(16, 14).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleKeyframedAnimations(DeserticFangEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0);
        animator.scale(this.bottom, -1.0F, -1.0F, -1.0F);
        animator.apply();

        animator.setupKeyframe(15);
        animator.scale(this.bottom, 0.4F, 0.4F, 0.4F);
        animator.apply(Easing.EASE_OUT_CUBIC);

        animator.setupKeyframe(16);
        animator.scale(this.bottom, -1.0F, -1.0F, -1.0F);
        animator.apply(Easing.EASE_IN_SQUARE);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.bottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

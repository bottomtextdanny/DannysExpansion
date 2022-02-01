package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.CursedSkullEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class CursedSkullModel extends BCEntityModel<CursedSkullEntity> {
    private final BCVoxel body;
    private final BCVoxel jaw;

    public CursedSkullModel() {
        this.texWidth = 48;
        this.texHeight = 33;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 21.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 9.0F, 12.0F, 0.0F, false);

        this.jaw = new BCVoxel(this);
        this.jaw.setPos(0.0F, 0.0F, 3.0F);
        this.body.addChild(this.jaw);
        this.jaw.texOffs(0, 21).addBox(-5.0F, 0.0F, -8.5F, 10.0F, 3.0F, 9.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(CursedSkullEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = Mth.abs(DEMath.sin(ageInTicks * 0.02F));

        this.body.xRot += headPitch * RAD;
        this.jaw.xRot += 2 * RAD * idle1;
    }

    @Override
    public void handleKeyframedAnimations(CursedSkullEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);
        
        if (entity.mainHandler.isPlaying(CursedSkullEntity.SPIT_FIRE)) {
	        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.getTick()  + getPartialTick());

            animator.setupKeyframe(16);
            animator.rotate(this.body, -37.5F, 0.0F, 0.0F);
            animator.rotate(this.jaw, 52.5F, 0.0F, 0.0F);
            animator.apply(Easing.BOUNCE_OUT);

            animator.staticKeyframe(4);

            animator.emptyKeyframe(8, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.AbstractSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class DannySlimeModel<E extends AbstractSlimeEntity> extends BCEntityModel<E> {
    protected final BCVoxel body;

    public DannySlimeModel(float width, float height) {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 24.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-width / 2, -height, -width / 2, width, height, width, -0.2F, false);
        this.body.texOffs(0, (int)width + (int)height).addBox(-width / 2, -height, -width / 2, width, height, width, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle = DEMath.sin(ageInTicks * 0.4F);

        addSize(this.body, 0.1111F * idle, -0.1F * idle, 0.1111F * idle);
    }

    @Override
    public void handleKeyframedAnimations(E entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());

        if(entity.mainAnimationHandler.isPlaying(entity.jump)) {

            animator.setupKeyframe(4);
            animator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.scale(this.body, -0.3F, 0.7692F, -0.3F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);
        }

        if(entity.getLocalAnimationHandler().isPlaying(entity.death)) {
            EntityModelAnimator deathAnimator = new EntityModelAnimator(this, entity.getLocalAnimationHandler().getTick() + getPartialTick());

            animator.setupKeyframe(10);
            animator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
            animator.apply();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

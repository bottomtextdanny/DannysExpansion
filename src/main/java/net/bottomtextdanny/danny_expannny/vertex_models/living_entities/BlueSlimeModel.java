package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.BlueSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.client.CameraType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlueSlimeModel extends BCEntityModel<BlueSlimeEntity> {
    private final BCVoxel model;
    private final BCVoxel slime;
    private final BCVoxel saddle;

    public BlueSlimeModel() {
        this.texWidth = 128;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);

        this.saddle = new BCVoxel(this);
        this.saddle.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.saddle);
        this.saddle.texOffs(0, 32).addBox(-9.0F, -14.0F, -9.0F, 18.0F, 14.0F, 18.0F, 0.5F, false);

        this.slime = new BCVoxel(this);
        this.slime.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.slime);
        this.slime.texOffs(0, 0).addBox(-9.0F, -14.0F, -9.0F, 18.0F, 14.0F, 18.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(BlueSlimeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle = DEMath.sin(ageInTicks * 0.1F);
        this.slime.visible = true;
        this.globalSpeed = 0.4F;
        walkScaleY(this.model, this.globalSpeed, 0.01F, 0, 0.01F, limbSwing, limbSwingAmount, false);
        walkScaleX(this.model, this.globalSpeed, -0.01F, 0, -0.01F, limbSwing, limbSwingAmount, false);
        walkScaleZ(this.model, this.globalSpeed, -0.01F, 0, -0.01F, limbSwing, limbSwingAmount, false);



        addSize(this.model, 0.1111F / 5 * idle, -0.1F / 5 * idle, 0.1111F / 5 * idle);

        if (entity.getControllingPassenger() instanceof AbstractClientPlayer cPlayer) {
            Vec3 vec = cPlayer.position().add(0, cPlayer.getEyeHeight(), 0);

            if (entity.getBoundingBox().intersects(new AABB(vec, vec)) && ClientInstance.gs().getCameraType() == CameraType.FIRST_PERSON) {
                this.slime.visible = false;
            }
        }
    }

    @Override
    public void handleKeyframedAnimations(BlueSlimeEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        float dif = entity.getProgress01() - entity.getPrevProgress01();
        float prog = entity.getPrevProgress01() + dif * getPartialTick();

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick() + getPartialTick());
        EntityModelAnimator mountProgAnimator = new EntityModelAnimator(this, prog);

        if (entity.getProgress01() > 0) {
            mountProgAnimator.setupKeyframe(1);
            mountProgAnimator.scale(this.model, 0.2F, -0.3F, 0.2F);
            mountProgAnimator.apply();

            mountProgAnimator.staticKeyframe(1);
        }

        if (entity.mainAnimationHandler.isPlaying(entity.jump)) {
            animator.setupKeyframe(3);
            animator.scale(this.model, -0.25F, 0.4F, -0.25F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);

        } else if (entity.mainAnimationHandler.isPlaying(entity.backToItem)) {
            animator.setupKeyframe(4);
            animator.scale(this.model, -0.15F, -0.15F, -0.15F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.3F, -0.3F, -0.3F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.45F, -0.45F, -0.45F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.6F, -0.6F, -0.6F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.75F, -0.75F, -0.75F);
            animator.apply(Easing.EASE_OUT_BACK);

        } else if (entity.mainAnimationHandler.isPlaying(entity.fromItem)) {
            animator.setupKeyframe(0);
            animator.scale(this.model, -0.75F, -0.75F, -0.75F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.6F, -0.6F, -0.6F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.45F, -0.45F, -0.45F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.3F, -0.3F, -0.3F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.setupKeyframe(4);
            animator.scale(this.model, -0.15F, -0.15F, -0.15F);
            animator.apply(Easing.EASE_OUT_BACK);

            animator.emptyKeyframe(4, Easing.EASE_OUT_BACK);
        }

        if (entity.getProtectiveTicks() > -1) {
            EntityModelAnimator protectiveAnimator = new EntityModelAnimator(this, entity.getProtectiveTicks() + getPartialTick());

            protectiveAnimator.setupKeyframe(10);
            protectiveAnimator.scale(this.model, 0.7F, 0.7F, 0.7F);
            protectiveAnimator.apply(Easing.EASE_OUT_BACK);

            protectiveAnimator.staticKeyframe(280);

            protectiveAnimator.emptyKeyframe(10, Easing.EASE_OUT_BACK);
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(matrixStackIn, bufferIn, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.GolemDroneEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class GolemDroneModel extends BCEntityModel<GolemDroneEntity> {
    private final BCVoxel model;
    private final BCVoxel drone;
    private final BCVoxel helix1;
    private final BCVoxel helix2;
    private final BCVoxel helix3;
    private final BCVoxel helix4;
    float helixMover;

    public GolemDroneModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, -2.5F, 0.0F);

        this.drone = new BCVoxel(this);
        this.model.addChild(this.drone);
        this.drone.setPos(0.0F, 0.0F, 0.0F);
        this.drone.texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        this.drone.texOffs(0, 14).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        this.drone.texOffs(0, 8).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        this.drone.texOffs(0, 23).addBox(-2.5F, -7.0F, -3.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
        this.drone.texOffs(0, 23).addBox(2.5F, -7.0F, -3.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
        this.drone.texOffs(0, 29).addBox(-3.0F, -7.0F, -2.5F, 6.0F, 2.0F, 0.0F, 0.0F, false);
        this.drone.texOffs(0, 29).addBox(-3.0F, -7.0F, 2.5F, 6.0F, 2.0F, 0.0F, 0.0F, false);

        this.helix1 = new BCVoxel(this);
        this.helix1.setPos(2.5F, -6.0F, -2.5F);
        this.drone.addChild(this.helix1);
        this.helix1.texOffs(12, 0).addBox(-2.0F, 0.0F, -0.5F, 4.0F, 0.0F, 1.0F, 0.0F, false);

        this.helix2 = new BCVoxel(this);
        this.helix2.setPos(-2.5F, -6.0F, -2.5F);
        this.drone.addChild(this.helix2);
        this.helix2.texOffs(12, 0).addBox(-2.0F, 0.0F, -0.5F, 4.0F, 0.0F, 1.0F, 0.0F, false);

        this.helix3 = new BCVoxel(this);
        this.helix3.setPos(-2.5F, -6.0F, 2.5F);
        this.drone.addChild(this.helix3);
        this.helix3.texOffs(12, 0).addBox(-2.0F, 0.0F, -0.5F, 4.0F, 0.0F, 1.0F, 0.0F, false);

        this.helix4 = new BCVoxel(this);
        this.helix4.setPos(2.5F, -6.0F, 2.5F);
        this.drone.addChild(this.helix4);
        this.helix4.texOffs(12, 0).addBox(-2.0F, 0.0F, -0.5F, 4.0F, 0.0F, 1.0F, 0.0F, false);
        
        setupDefaultState();
    }

    @Override
    public void handleRotations(GolemDroneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin((entity.getLifeTick() + net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK) * 0.5F);
        float idle2 = DEMath.sin((entity.getLifeTick() + net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK) * 2.47F);

        float movement = (float) (Math.PI * 2 * (ageInTicks % 1));

        this.drone.y += 1.0F * idle1 + 0.5 * idle2;
        this.helix1.yRot += movement;
        this.helix2.yRot += movement;
        this.helix3.yRot += movement;
        this.helix4.yRot += movement;
    }

    @Override
    public void handleKeyframedAnimations(GolemDroneEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + getPartialTick());

        animator.setupKeyframe(0);
        animator.scale(this.drone, -1.0F, -1.0F, -1.0F);
        animator.move(this.drone, 0.0F, 1.25F, 0.0F);
        animator.apply();

        animator.setupKeyframe(4);
        animator.apply(Easing.EASE_OUT_CUBIC);

        if (entity.mainHandler.isPlaying(entity.hurtAnimation)) {
            EntityModelAnimator ambientAnimator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

            ambientAnimator.setupKeyframe(1.5F);
            ambientAnimator.move(this.drone, 0.0F, 2.3F, 0.0F);
            ambientAnimator.apply(Easing.EASE_IN_CUBIC);

            ambientAnimator.setupKeyframe(1.0F);
            ambientAnimator.move(this.drone, 0.0F, -1.7F, 0.0F);
            ambientAnimator.apply(Easing.EASE_IN_CUBIC);

            ambientAnimator.setupKeyframe(1.5F);
            ambientAnimator.move(this.drone, 0.0F, 1.3F, 0.0F);
            ambientAnimator.apply(Easing.EASE_IN_SQUARE);

            ambientAnimator.setupKeyframe(1.5F);
            ambientAnimator.move(this.drone, 0.0F, -0.7F, 0.0F);
            ambientAnimator.apply(Easing.EASE_IN_SQUARE);

            ambientAnimator.emptyKeyframe(2.5F, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.drone.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

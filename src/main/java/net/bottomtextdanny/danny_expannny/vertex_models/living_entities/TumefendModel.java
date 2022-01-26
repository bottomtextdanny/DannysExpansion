package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.TumefendEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class TumefendModel extends BCEntityModel<TumefendEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel head;
    private final BCVoxel lightPos;
    private final BCVoxel inferior;
    private final BCVoxel frontTent;
    private final BCVoxel backTent;
    private final BCVoxel leftTent;
    private final BCVoxel rightTent;

    public TumefendModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -8.0F, 0.0F);
        this.model.addChild(this.body);


        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -1.0F, 0.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 12.0F, 16.0F, 0.0F, false);
        this.head.texOffs(0, 28).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 5.0F, 14.0F, 0.0F, false);

        this.lightPos = new BCVoxel(this);
        this.lightPos.setPos(0.0F, -6.0F, 0.0F);
        this.head.addChild(this.lightPos);


        this.inferior = new BCVoxel(this);
        this.inferior.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.inferior);
        this.inferior.texOffs(0, 47).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 9.0F, 9.0F, 0.0F, false);

        this.frontTent = new BCVoxel(this);
        this.frontTent.setPos(0.0F, 8.0F, -4.0F);
        this.inferior.addChild(this.frontTent);
        this.frontTent.texOffs(48, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 8.0F, 0.0F, 0.0F, false);

        this.backTent = new BCVoxel(this);
        this.backTent.setPos(0.0F, 8.0F, 4.0F);
        this.inferior.addChild(this.backTent);
        setRotationAngle(this.backTent, 0.0F, 3.1416F, 0.0F);
        this.backTent.texOffs(48, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 8.0F, 0.0F, 0.0F, false);

        this.leftTent = new BCVoxel(this);
        this.leftTent.setPos(4.0F, 8.0F, 0.0F);
        this.inferior.addChild(this.leftTent);
        setRotationAngle(this.leftTent, 0.0F, -1.5708F, 0.0F);
        this.leftTent.texOffs(48, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 8.0F, 0.0F, 0.0F, false);

        this.rightTent = new BCVoxel(this);
        this.rightTent.setPos(-4.0F, 8.0F, 0.0F);
        this.inferior.addChild(this.rightTent);
        setRotationAngle(this.rightTent, 0.0F, 1.5708F, 0.0F);
        this.rightTent.texOffs(48, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 8.0F, 0.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(TumefendEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        final float easedRotYaw = Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.yRotO, entity.getYRot());
        final float easedRenderDif = Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.prevRenderYawRot, entity.renderYawRot);
        final float easedMovement = (float) Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.prevRenderMovement, entity.renderMovement);
        final float fromPast = Mth.degreesDifference(easedRenderDif, easedRotYaw) - 90.0F;
        final float fPX = DEMath.sin(fromPast * RAD);
        final float fPZ = DEMath.cos(fromPast * RAD);
        final float offsetFactor = DEMath.sin(ageInTicks * 0.5F) * 0.25F;
        final float tentacleMovementFactor = DEMath.sin(ageInTicks * 0.15F) * 0.18F - 0.25F;

        this.model.yRot = headYaw * RAD;
        this.model.y += offsetFactor;
        this.frontTent.xRot += tentacleMovementFactor;
        this.backTent.xRot += tentacleMovementFactor;
        this.rightTent.xRot += tentacleMovementFactor;
        this.leftTent.xRot += tentacleMovementFactor;
        setRotationAngleDegrees(this.body, easedMovement * 10 * fPX, 0, easedMovement * 10 * fPZ);
    }

    @Override
    public void handleKeyframedAnimations(TumefendEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick()  + getPartialTick());

        if(entity.mainAnimationHandler.isPlaying(entity.goUp)) {

            animator.setupKeyframe(5);
            animator.scale(this.head, 0.6F, -0.4F, 0.6F);
            animator.rotate(this.frontTent, 30.0F, 0F, 0F);
            animator.rotate(this.backTent, 30.0F, 0F, 0F);
            animator.rotate(this.rightTent, 30.0F, 0F, 0F);
            animator.rotate(this.leftTent, 30.0F, 0F, 0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.setupKeyframe(5);
            animator.scale(this.head, -0.4F, 0.4F, -0.4F);
            animator.rotate(this.frontTent, -10.0F, 0F, 0F);
            animator.rotate(this.backTent, -10.0F, 0F, 0F);
            animator.rotate(this.rightTent, -10.0F, 0F, 0F);
            animator.rotate(this.leftTent, -10.0F, 0F, 0F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.EASE_IN_OUT_BACK);
        }

        if (entity.attack.isWoke()) {
            EntityModelAnimator ambAnimator = new EntityModelAnimator(this, entity.attackModule.getTick() + getPartialTick());

            ambAnimator.setupKeyframe(4);
            ambAnimator.scale(this.head, 0.3F, 0.3F, 0.3F);
            ambAnimator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }

    public BCVoxel getLightPos() {
        return this.lightPos;
    }
}

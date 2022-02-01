package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.MagmaGulperEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class MagmaGulperModel extends BCEntityModel<MagmaGulperEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel head;
    private final BCVoxel jaw;
    private final BCVoxel rightWing;
    private final BCVoxel rightWingNext;
    private final BCVoxel leftWing;
    private final BCVoxel leftWingNext;

    public MagmaGulperModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -6.0F, 0.0F);
        this.model.addChild(this.body);
        setRotationAngle(this.body, 0.1745F, 0.0F, 0.0F);


        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -1.0F, 4.5F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 16).addBox(-4.0F, -5.0F, -8.5F, 8.0F, 5.0F, 8.0F, 0.0F, false);
        this.head.texOffs(32, 19).addBox(-1.5F, -7.5F, -10.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
        this.head.texOffs(32, 8).addBox(-4.0F, -8.0F, -8.5F, 8.0F, 3.0F, 8.0F, 0.0F, false);
        this.head.texOffs(0, 29).addBox(-4.5F, -5.5F, -9.0F, 9.0F, 6.0F, 9.0F, -0.2F, false);

        this.jaw = new BCVoxel(this);
        this.jaw.setPos(0.0F, 0.0F, -0.5F);
        this.head.addChild(this.jaw);
        setRotationAngle(this.jaw, 0.0F, 0.0F, 0.0F);
        this.jaw.texOffs(0, 0).addBox(-4.5F, 0.0F, -8.5F, 9.0F, 7.0F, 9.0F, 0.0F, false);

        this.rightWing = new BCVoxel(this);
        this.rightWing.setPos(-1.0F, 0.0F, -0.5F);
        this.head.addChild(this.rightWing);
        setRotationAngle(this.rightWing, 0.5236F, -1.3963F, 0.0F);
        this.rightWing.texOffs(0, 44).addBox(-4.0F, 0.0F, 0.0F, 10.0F, 0.0F, 10.0F, 0.0F, false);

        this.rightWingNext = new BCVoxel(this);
        this.rightWingNext.setPos(1.0F, 0.0F, 10.0F);
        this.rightWing.addChild(this.rightWingNext);
        setRotationAngle(this.rightWingNext, -0.7854F, 0.0F, 0.0F);
        this.rightWingNext.texOffs(0, 54).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 10.0F, 0.0F, false);

        this.leftWing = new BCVoxel(this);
        this.leftWing.setPos(1.0F, 0.0F, -0.5F);
        this.head.addChild(this.leftWing);
        setRotationAngle(this.leftWing, 0.5236F, 1.3963F, 0.0F);
        this.leftWing.texOffs(0, 44).addBox(-6.0F, 0.0F, 0.0F, 10.0F, 0.0F, 10.0F, 0.0F, true);

        this.leftWingNext = new BCVoxel(this);
        this.leftWingNext.setPos(-1.0F, 0.0F, 10.0F);
        this.leftWing.addChild(this.leftWingNext);
        setRotationAngle(this.leftWingNext, -0.7854F, 0.0F, 0.0F);
        this.leftWingNext.texOffs(0, 54).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 10.0F, 0.0F, true);
    
        setupDefaultState();
    }

    @Override
    public void handleRotations(MagmaGulperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.2F);
        float idle2 = DEMath.sin(ageInTicks * 0.175F);
        float idle3 = Mth.abs(DEMath.sin(ageInTicks * 0.03F));

        this.body.yRot += headYaw * RAD;

        this.body.xRot += 10 * RAD;

        this.body.zRot += Math.min(80.0F, -Mth.lerp(getPartialTick(), entity.prevRenderYawRot, entity.renderYawRot)) * RAD * 5;

        this.leftWing.xRot += 2 * RAD * idle1;
        this.rightWing.xRot += -2 * RAD * idle1;
        this.leftWing.yRot += 2 * RAD * idle2;
        this.rightWing.yRot += -2 * RAD * idle2;
        this.head.xRot += -26 * RAD * idle3;
        this.jaw.xRot += 22 * RAD * idle3;
    }

    @Override
    public void handleKeyframedAnimations(MagmaGulperEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator limbSwingReactor = new EntityModelAnimator(this, limbSwingAmount);

        limbSwingReactor.setupKeyframe(0.9F);
        limbSwingReactor.rotate(this.body, 10.0F, 0.0F, 0.0F);
        limbSwingReactor.rotate(this.jaw, 10.0F, 0.0F, 0.0F);
        limbSwingReactor.rotate(this.rightWing, -2.5F, 0.0F, -30.0F);
        limbSwingReactor.rotate(this.leftWing, -2.5F, 0.0F, 30.0F);
        limbSwingReactor.rotate(this.rightWingNext, 42.5F, 0.0F, 0.0F);
        limbSwingReactor.rotate(this.leftWingNext, 42.5F, 0.0F, 0.0F);
        limbSwingReactor.apply();
        limbSwingReactor.staticKeyframe(Float.MAX_VALUE);

        if (entity.attackHandler.isPlaying(MagmaGulperEntity.RAM)) {
            EntityModelAnimator attackAnimator = new EntityModelAnimator(this, entity.attackHandler.dynamicProgress());
	
	        attackAnimator.setupKeyframe(10);
            attackAnimator.rotate(this.body, headPitch + 10.0F, 0.0F, 0.0F);
            attackAnimator.apply();

            attackAnimator.staticKeyframe(2.0F);

            attackAnimator.emptyKeyframe(10, Easing.LINEAR);
        }

        if (entity.beatHandler.isPlaying(MagmaGulperEntity.BEAT)) {
            EntityModelAnimator ambientAnimator = new EntityModelAnimator(this, entity.beatHandler.linearProgress());

            ambientAnimator.setupKeyframe(10);
            ambientAnimator.scale(this.jaw, 0.45F, 0.45F, 0.45F);
            ambientAnimator.apply(Easing.BOUNCE_OUT);

            ambientAnimator.staticKeyframe(1.0F);

            ambientAnimator.emptyKeyframe(6, Easing.LINEAR);
        }

        if (entity.mainHandler.isPlaying(MagmaGulperEntity.FLAP)) {
            EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());
            
            animator.setupKeyframe(3);
            animator.rotate(this.rightWing, 0.0F, 0.0F, 30.0F);
            animator.rotate(this.leftWing, 0.0F, 0.0F, -30.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.jaw, 15.0F, 0.0F, 0.0F);
            animator.rotate(this.rightWing, 0.0F, 0.0F, -70.0F);
            animator.rotate(this.leftWing, 0.0F, 0.0F, 70.0F);
            animator.apply();

            animator.emptyKeyframe(14, Easing.LINEAR);
            
            animator.reset();

            animator.setupKeyframe(5);
            animator.rotate(this.rightWingNext, 42.5F, 0.0F, 0.0F);
            animator.rotate(this.leftWingNext, 42.5F, 0.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.rightWingNext, -15.0F, 0.0F, 0.0F);
            animator.rotate(this.leftWingNext, -15.0F, 0.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(12, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

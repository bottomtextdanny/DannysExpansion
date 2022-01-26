package net.bottomtextdanny.danny_expannny.vertex_models.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.IceSpike;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;

public class IceSpikeModel extends BCEntityModel<IceSpike> {
    private final BCVoxel model;
    private final BCVoxel zRotation;

    public IceSpikeModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);
        this.model.texOffs(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);

        this.zRotation = new BCVoxel(this);
        this.zRotation.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.zRotation);
        setRotationAngle(this.zRotation, 0.0F, 0.0F, -1.5708F);
        this.zRotation.texOffs(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(IceSpike entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.model.xRot += headPitch * RAD;
    }

    @Override
    public void handleKeyframedAnimations(IceSpike entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.getLifeTick() < 5) {
            EntityModelAnimator animator = new EntityModelAnimator(this, entity.getLifeTick() + DEUtil.PARTIAL_TICK);

            animator.setupKeyframe(0.0F);
            animator.scale(this.model, -1.0F, -1.0F, -1.0F);
            animator.apply();

            animator.setupKeyframe(4.0F);
            animator.scale(this.model, 0.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_IN_CUBIC);
        }
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        this.model.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
    }
}

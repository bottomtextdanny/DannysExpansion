package bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles;

import bottomtextdanny.dannys_expansion.content.entities.projectile.IceSpike;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod._base.BCStaticData;

public class IceSpikeModel extends BCEntityModel<IceSpike> {
    private final BCJoint model;
    private final BCJoint zRotation;

    public IceSpikeModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 0.0F, 0.0F);
        this.model.uvOffset(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);

        this.zRotation = new BCJoint(this);
        this.zRotation.setPosCore(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.zRotation);
        setRotationAngle(this.zRotation, 0.0F, 0.0F, -1.5708F);
        this.zRotation.uvOffset(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);

        //modelInitEnd
    }

    @Override
    public void handleRotations(IceSpike entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.model.xRot += headPitch * RAD;
    }

    @Override
    public void handleKeyframedAnimations(IceSpike entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.getLifeTick() < 5) {
            ModelAnimator animator = new ModelAnimator(this, entity.getLifeTick() + BCStaticData.partialTick());

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

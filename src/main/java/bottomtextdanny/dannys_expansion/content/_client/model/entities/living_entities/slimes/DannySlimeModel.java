package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes;

import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.AbstractSlime;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class DannySlimeModel<E extends AbstractSlime> extends BCEntityModel<E> {
    protected final BCJoint body;

    public DannySlimeModel(float width, float height, int imageWidth, int imageHeight) {
        this.texWidth = imageWidth;
        this.texHeight = imageHeight;

        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, 24.0F, 0.0F);
        this.body.uvOffset(0, 0).addBox(-width / 2, -height, -width / 2, width, height, width, -0.2F, false);
        this.body.uvOffset(0, (int)width + (int)height).addBox(-width / 2, -height, -width / 2, width, height, width, 0.0F, false);

        //modelInitEnd
    }

    public DannySlimeModel(float width, float height) {
        this(width, height, 128, 128);
    }

    @Override
    public void handleRotations(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle = DEMath.sin(ageInTicks * 0.4F);

        this.body.addToScale(0.1111F * idle, -0.1F * idle, 0.1111F * idle);
    }

    @Override
    public void handleKeyframedAnimations(E entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        ModelAnimator animator = new ModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());

        if(entity.mainHandler.isPlaying(entity.JUMP)) {

            animator.setupKeyframe(4);
            animator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.scale(this.body, -0.3F, 0.7692F, -0.3F);
            animator.apply();

            animator.emptyKeyframe(10, Easing.LINEAR);
        }

        if(entity.getLocalAnimationHandler().isPlaying(entity.DEATH)) {
            ModelAnimator deathAnimator = new ModelAnimator(this, entity.getLocalAnimationHandler().getTick() + getPartialTick());

            deathAnimator.setupKeyframe(10);
            deathAnimator.scale(this.body, 0.7692F, -0.3F, 0.7692F);
            deathAnimator.apply();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

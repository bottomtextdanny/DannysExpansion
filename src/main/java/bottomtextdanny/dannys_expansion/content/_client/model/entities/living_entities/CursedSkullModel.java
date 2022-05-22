package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.entities.mob.cursed_skull.CursedSkull;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.animation.interpreter.AnimationInterpreter;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CursedSkullModel extends BCEntityModel<CursedSkull> {
    private final BCJoint root;
    private final BCJoint head;
    private final BCJoint jaw;
    private final AnimationInterpreter spit = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "cursed_skull/spit"), this);
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);

    public CursedSkullModel() {
        texWidth = 64;
        texHeight = 64;

        root = new BCJoint(this, "root");
        root.setPosCore(0.0F, 24.0F, 0.0F);

        head = new BCJoint(this, "head");
        head.setPosCore(0.0F, -6.0F, 0.0F);
        root.addChild(head);
        head.uvOffset(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 8.0F, 12.0F, 0.0F, false);
        head.uvOffset(0, 31).addBox(-5.0F, 2.7F, -5.0F, 10.0F, 3.0F, 8.0F, -0.3F, false);

        jaw = new BCJoint(this, "jaw");
        jaw.setPosCore(0.0F, 3.0F, 2.0F);
        head.addChild(jaw);
        jaw.uvOffset(0, 20).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 3.0F, 8.0F, 0.0F, false);
        jaw.uvOffset(0, 42).addBox(-5.0F, -2.6F, -7.0F, 10.0F, 3.0F, 8.0F, -0.4F, false);
    }

    @Override
    public void handleRotations(CursedSkull entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.yRot = headYaw * RAD;
        this.head.xRot += headPitch * RAD;

        jaw.xRot += Mth.lerp(BCStaticData.partialTick(), entity.jawAnimationO, entity.jawAnimation);

        head.xRot -= 10.0 * BCMath.FRAD;
        jaw.xRot += 15.0 * BCMath.FRAD;
        jaw.xRot += Mth.sin(ageInTicks * 0.08F) * 5.0F * BCMath.FRAD;
    }

    @Override
    public void handleKeyframedAnimations(CursedSkull entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        if (entity.mainHandler.isPlaying(CursedSkull.SPIT)) {
            animator.setTimer(entity.mainHandler.linearProgress());

            spit.run(animator);
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

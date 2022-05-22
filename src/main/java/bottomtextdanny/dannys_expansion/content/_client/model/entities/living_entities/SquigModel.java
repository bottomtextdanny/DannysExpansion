package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.entities.mob.squig.Squig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.animation.interpreter.AnimationInterpreter;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SquigModel extends BCEntityModel<Squig> {
    private final BCJoint root;
    private final BCJoint headXY;
    private final BCJoint head;
    private final BCJoint body;
    private final BCJoint leg;
    private final BCJoint leg2;
    private final BCJoint leg3;
    private final BCJoint leg4;
    private final BCJoint leg5;
    private final BCJoint leg6;
    private final BCJoint leg7;
    private final BCJoint leg8;
    private final AnimationInterpreter impulse = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "squig/impulse"), this);
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);
    public SquigModel() {
        texWidth = 128;
        texHeight = 128;

        root = new BCJoint(this, "root");
        root.setPosCore(0.0F, 24.0F, 0.0F);

        headXY = new BCJoint(this, "headXY");
        headXY.setPosCore(0.0F, -7.0F, 0.5F);
        root.addChild(headXY);

        head = new BCJoint(this, "head");
        head.setPosCore(0.0F, 0.0F, 0.0F);
        headXY.addChild(head);
        head.uvOffset(0, 4).addBox(-5.0F, -3.1F, -7.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        head.uvOffset(0, 0).addBox(2.0F, -3.1F, -7.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        head.uvOffset(0, 0).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 9.0F, 12.0F, 0.0F, false);
        head.uvOffset(32, 28).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 4.0F, 10.0F, 0.0F, false);

        body = new BCJoint(this, "body");
        body.setPosCore(0.0F, 3.0F, -0.5F);
        head.addChild(body);
        body.uvOffset(0, 21).addBox(-5.0F, -3.0F, -5.5F, 10.0F, 6.0F, 11.0F, 0.0F, false);

        leg = new BCJoint(this, "leg");
        leg.setPosCore(-4.5F, 2.5F, -5.0F);
        body.addChild(leg);
        setRotationAngle(leg, 0.0F, 0.7854F, 0.0F);
        leg.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg2 = new BCJoint(this, "leg2");
        leg2.setPosCore(0.0F, 2.5F, -5.5F);
        body.addChild(leg2);
        leg2.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg3 = new BCJoint(this, "leg3");
        leg3.setPosCore(4.5F, 2.5F, -5.0F);
        body.addChild(leg3);
        setRotationAngle(leg3, 0.0F, -0.7854F, 0.0F);
        leg3.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg4 = new BCJoint(this, "leg4");
        leg4.setPosCore(-5.5F, 2.5F, 0.0F);
        body.addChild(leg4);
        setRotationAngle(leg4, 0.0F, 1.5708F, 0.0F);
        leg4.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg5 = new BCJoint(this, "leg5");
        leg5.setPosCore(5.5F, 2.5F, 0.0F);
        body.addChild(leg5);
        setRotationAngle(leg5, 0.0F, -1.5708F, 0.0F);
        leg5.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg6 = new BCJoint(this, "leg6");
        leg6.setPosCore(-4.5F, 2.5F, 5.0F);
        body.addChild(leg6);
        setRotationAngle(leg6, 0.0F, 2.3562F, 0.0F);
        leg6.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg7 = new BCJoint(this, "leg7");
        leg7.setPosCore(0.0F, 2.5F, 5.5F);
        body.addChild(leg7);
        setRotationAngle(leg7, 0.0F, 3.1416F, 0.0F);
        leg7.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 17.0F, 2.0F, 0.0F, false);

        leg8 = new BCJoint(this, "leg8");
        leg8.setPosCore(4.5F, 2.5F, 5.0F);
        body.addChild(leg8);
        setRotationAngle(leg8, 0.0F, -2.3562F, 0.0F);
        leg8.uvOffset(48, 0).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 17.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void handleRotations(Squig entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float yawOffset = Mth.rotLerp(BCStaticData.partialTick(), entity.getOffsetYawO(), entity.getOffsetYaw()) + 180.0F;
      //  float off = Mth.rotlerp(entity.prevYRotOff, entity.yRotOff, getPartialTick());

        this.headXY.xRot += (headPitch + 90.0F) * RAD;
        this.headXY.yRot += headYaw * RAD;

        this.head.yRot += yawOffset * RAD;
    }

    @Override
    public void handleKeyframedAnimations(Squig entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        if (entity.mainHandler.isPlaying(Squig.IMPULSE)) {
            animator.setTimer(entity.mainHandler.linearProgress());

            impulse.run(animator);
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.ghoul;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._util.DEMath;
import bottomtextdanny.dannys_expansion.content._client.model.entities.DEBipedModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.ghoul.Ghoul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.animation.interpreter.AnimationInterpreter;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GhoulModel extends DEBipedModel<Ghoul> {
    private final BCJoint headSpike;
    private final AnimationInterpreter walk = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "ghoul/walk"), this);
    private final AnimationInterpreter punch = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "ghoul/punch"), this);
    private final AnimationInterpreter upPunch = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "ghoul/up_punch"), this);
    private final AnimationInterpreter trap = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "ghoul/trap"), this);
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);

    public GhoulModel() {
        super(64, 64);

        root.setPosCore(0.0F, 24.0F, 0.0F);

        body.setPosCore(0.0F, -13.0F, 0.0F);
        root.addChild(body);
        body.uvOffset(33, 10).addBox(0.0F, -5.0F, 2.5F, 0.0F, 4.0F, 5.0F, 0.0F, false);
        body.uvOffset(0, 0).addBox(-4.5F, -10.0F, -3.0F, 9.0F, 10.0F, 6.0F, 0.0F, false);
        body.uvOffset(28, 14).addBox(0.0F, -10.0F, 2.5F, 0.0F, 4.0F, 6.0F, 0.0F, false);

        head.setPosCore(0.0F, -10.0F, 0.0F);
        body.addChild(head);
        head.uvOffset(0, 16).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, 0.0F, false);

        headSpike = new BCJoint(this, "headSpike");
        headSpike.setPosCore(-0.5F, -6.0F, 2.5F);
        head.addChild(headSpike);
        setRotationAngle(headSpike, 0.3927F, 0.0F, 0.0F);
        headSpike.uvOffset(21, 10).addBox(0.5F, -2.0F, -1.0F, 0.0F, 4.0F, 6.0F, 0.0F, false);
        
        rightArm.setPosCore(4.5F, -8.0F, 0.0F);
        body.addChild(rightArm);
        rightArm.uvOffset(12, 30).addBox(0.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, true);
        
        leftArm.setPosCore(-4.5F, -8.0F, 0.0F);
        body.addChild(leftArm);
        leftArm.uvOffset(30, 0).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        
        leftLeg.setPosCore(-2.0F, -13.0F, 0.0F);
        root.addChild(leftLeg);
        leftLeg.uvOffset(0, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, 0.0F, false);
        
        rightLeg.setPosCore(2.0F, -13.0F, 0.0F);
        root.addChild(rightLeg);
        rightLeg.uvOffset(25, 27).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, 0.0F, true);
    }

    @Override
    public void handleRotations(Ghoul entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle1 = DEMath.sin(ageInTicks * 0.05F);
        float idle2 = DEMath.sin(ageInTicks * 0.03F);

        this.head.xRot += Mth.clamp(headPitch, -70, 70) * RAD;
        this.head.yRot += Mth.clamp(headYaw, -90, 60) * RAD;

        this.body.xRot += RAD * 2.0F * idle1;
        this.rightArm.xRot += RAD * -4.0F * idle1;
        this.leftArm.xRot += RAD * 4.0F * idle1;
        this.rightArm.zRot += RAD * (-4.0F * idle2 - 4.0F);
        this.leftArm.zRot += RAD * (4.0F * idle2 + 4.0F);
    }

    @Override
    public void handleKeyframedAnimations(Ghoul entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        float easedLimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float tickOffset = BCStaticData.partialTick();

        if (easedLimbSwingAmount > 0.0F) {
            float easedLimbSwing = Mth.clamp(caculateLimbSwingEasing(entity), 0.0F, 0.999F);
            float walkMult = Mth.clamp(easedLimbSwingAmount * 12.0F, 0.0F, 1.0F);

            animator.setTimer(easedLimbSwing * 20.0F);
            animator.multiplier(walkMult);
            walk.run(animator);

            animator.multiplier(1.0F);
        }

        animator.setTimer(entity.mainHandler.linearProgress());

        boolean lowerArms = false;

        if (entity.isUsingLeftHand())
            animator.mirror(true);

        if (entity.mainHandler.isPlaying(Ghoul.UP_PUNCH)) {

            upPunch.run(animator);
            lowerArms = true;
        } else if (entity.mainHandler.isPlaying(Ghoul.PUNCH)) {
            punch.run(animator);
            lowerArms = true;
        } else if (entity.mainHandler.isPlaying(Ghoul.TRAP)) {
            trap.run(animator);
            lowerArms = true;
        }
        animator.mirror(false);

        float handRaise = Mth.lerp(tickOffset, entity.getHandRaiseO(), entity.getHandRaise());
        handRaise = Easing.EASE_OUT_GAMMA.progression(handRaise) * RAD * -90.0F;

        if (lowerArms)
            handRaise *= animator.disable(9, 4, 9);

        this.rightArm.xRot += handRaise;
        this.leftArm.xRot += handRaise;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

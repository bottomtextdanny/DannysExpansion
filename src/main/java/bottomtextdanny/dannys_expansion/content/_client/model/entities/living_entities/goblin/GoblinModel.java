package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.goblin;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.entities.mob.goblin.Goblin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.BCMath;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod._base.animation.interpreter.AnimationInterpreter;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GoblinModel extends BCEntityModel<Goblin> {
    private final BCJoint root;
    private final BCJoint body;
    private final BCJoint head;
    private final BCJoint rightEar;
    private final BCJoint leftEar;
    private final BCJoint rightarm;
    private final BCJoint rightHandling;
    public final BCJoint rightHandlingRot;
    private final BCJoint leftarm;
    public final BCJoint leftHandling;
    private final BCJoint leftleg;
    private final BCJoint rightleg;
    private final ModelAnimator animator = new ModelAnimator(this, 0.0F);
    private final AnimationInterpreter bumpAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/bump"), this);
    private final AnimationInterpreter doubleSlashAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/double_slash"), this);
    private final AnimationInterpreter throwLeftAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/throw_left"), this);
    private final AnimationInterpreter throwRightAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/throw_right"), this);
    private final AnimationInterpreter slashLeftAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/slash_left"), this);
    private final AnimationInterpreter slashRightAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/slash_right"), this);
    private final AnimationInterpreter stabLeftAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/stab_left"), this);
    private final AnimationInterpreter stabRightAttack = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/stab_right"), this);
    private final AnimationInterpreter walk = Braincell.client().getAnimationManager()
            .makeInterpreter(new ResourceLocation(DannysExpansion.ID, "goblin/walk"), this);

    public GoblinModel() {
        texWidth = 32;
        texHeight = 32;

        root = new BCJoint(this, "root");
        root.setPosCore(0.0F, 24.0F, 0.0F);

        body = new BCJoint(this, "body");
        body.setPosCore(0.0F, -4.0F, 0.0F);
        root.addChild(body);
        body.uvOffset(12, 15).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        body.uvOffset(0, 11).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.025F, false);

        head = new BCJoint(this, "head");
        head.setPosCore(0.0F, -3.0F, 0.0F);
        body.addChild(head);
        head.uvOffset(0, 0).addBox(-4.0F, -6.0F, -2.5F, 8.0F, 6.0F, 5.0F, 0.0F, false);

        rightEar = new BCJoint(this, "rightEar");
        rightEar.setPosCore(4.0F, -3.5F, 0.0F);
        head.addChild(rightEar);
        rightEar.uvOffset(0, 19).addBox(0.0F, -1.5F, 0.0F, 6.0F, 3.0F, 0.0F, 0.0F, false);

        leftEar = new BCJoint(this, "leftEar");
        leftEar.setPosCore(-4.0F, -3.5F, 0.0F);
        head.addChild(leftEar);
        leftEar.uvOffset(12, 11).addBox(-6.0F, -1.5F, 0.0F, 6.0F, 3.0F, 0.0F, 0.0F, false);

        rightarm = new BCJoint(this, "rightarm");
        rightarm.setPosCore(2.0F, -2.5F, 0.0F);
        body.addChild(rightarm);
        setRotationAngle(rightarm, 0.0F, 0.0F, -2.3562F);
        rightarm.uvOffset(12, 22).addBox(-4.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, false);

        rightHandling = new BCJoint(this, "rightHandling");
        rightHandling.setPosCore(-3.5F, 0.0F, 0.0F);
        rightarm.addChild(rightHandling);

        rightHandlingRot = new BCJoint(this, "rightHandlingRot");
        rightHandlingRot.setPosCore(0.0F, 0.0F, 0.0F);
        rightHandling.addChild(rightHandlingRot);
        setRotationAngle(rightHandlingRot, 0.0F, 0.0F, -3.1416F);

        leftarm = new BCJoint(this, "leftarm");
        leftarm.setPosCore(-2.0F, -2.5F, 0.0F);
        body.addChild(leftarm);
        setRotationAngle(leftarm, 0.0F, 0.0F, -0.7854F);
        leftarm.uvOffset(12, 22).addBox(-4.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, 0.0F, false);

        leftHandling = new BCJoint(this, "leftHandling");
        leftHandling.setPosCore(-3.5F, 0.0F, 0.0F);
        leftarm.addChild(leftHandling);

        leftleg = new BCJoint(this, "leftleg");
        leftleg.setPosCore(-1.5F, -4.0F, 0.0F);
        root.addChild(leftleg);
        leftleg.uvOffset(0, 22).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);

        rightleg = new BCJoint(this, "rightleg");
        rightleg.setPosCore(1.5F, -4.0F, 0.0F);
        root.addChild(rightleg);
        rightleg.uvOffset(0, 22).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void handleRotations(Goblin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.xRot = Mth.clamp(headPitch, -50, 50) * RAD;
        this.head.yRot = Mth.clamp(headYaw, -70, 50) * RAD;
    }

    @Override
    public void handleKeyframedAnimations(Goblin entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);

        if (!entity.isPassenger()) {
            if (easedlimbSwingAmount > 0.0F) {
                float easedLimbSwing = Mth.clamp(caculateLimbSwingEasing(entity), 0.0F, 0.999F);
                float walkMult = Mth.clamp(easedlimbSwingAmount * 12.0F, 0.0F, 1.0F);

                animator.setTimer(easedLimbSwing * 20.0F);
                animator.multiplier(walkMult);
                walk.run(animator);

                animator.multiplier(1.0F);
            }
        } else {
            animator.reset();
            setSittingPose();
        }

        if (entity.mainHandler.isPlayingNull()) return;

        animator.setTimer(entity.mainHandler.dynamicProgress());

        if (entity.mainHandler.isPlaying(Goblin.BUMP)) {
            bumpAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.DOUBLE_SLASH)) {
            doubleSlashAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.SLASH_LEFT)) {
            slashLeftAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.SLASH_RIGHT)) {
            slashRightAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.STAB_LEFT)) {
            stabLeftAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.STAB_RIGHT)) {
            stabRightAttack.run(animator);
        }

        else if (entity.mainHandler.isPlaying(Goblin.THROW)) {
            if (!entity.hasLeftWeapon()) throwLeftAttack.run(animator);
            else throwRightAttack.run(animator);
        }
    }

    private void setSittingPose() {
        float sittingAngleOffset = -70.0F * BCMath.FRAD;

        this.leftleg.xRot = sittingAngleOffset;
        this.rightleg.xRot = sittingAngleOffset;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}

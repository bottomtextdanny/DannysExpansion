package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.RammerEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class RammerModel extends BCEntityModel<RammerEntity> {
    public final BCVoxel model;
    public final BCVoxel body;
    public final BCVoxel head;
    private final BCVoxel shell;
    private final BCVoxel lastshell;
    private final BCVoxel bodyfirst;
    private final BCVoxel bodysecond;
    private final BCVoxel bodythird;
    private final BCVoxel rightfrontleg;
    private final BCVoxel rightbackleg;
    private final BCVoxel leftbackleg;
    private final BCVoxel leftfrontleg;

    public RammerModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 20.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -6.0F, -1.0F);
        this.model.addChild(this.body);


        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-7.0F, -6.0F, -10.0F, 14.0F, 11.0F, 10.0F, 0.0F, false);
        this.head.texOffs(73, 0).addBox(0.0F, -8.0F, -7.0F, 0.0F, 2.0F, 7.0F, 0.0F, false);

        this.shell = new BCVoxel(this);
        this.shell.setPos(0.0F, -5.0F, 1.0F);
        this.head.addChild(this.shell);
        this.shell.texOffs(0, 21).addBox(-7.5F, -1.5F, -11.5F, 15.0F, 12.0F, 3.0F, 0.0F, false);

        this.lastshell = new BCVoxel(this);
        this.lastshell.setPos(0.0F, 10.5F, -11.5F);
        this.shell.addChild(this.lastshell);
        this.lastshell.texOffs(0, 70).addBox(-7.5F, -4.0F, -3.0F, 15.0F, 4.0F, 3.0F, 0.0F, false);
        this.lastshell.texOffs(15, 70).addBox(-7.5F, -1.0F, -3.0F, 15.0F, 0.0F, 3.0F, 0.0F, false);

        this.bodyfirst = new BCVoxel(this);
        this.bodyfirst.setPos(0.0F, 1.0F, 0.0F);
        this.body.addChild(this.bodyfirst);
        this.bodyfirst.texOffs(0, 36).addBox(-6.0F, -5.0F, 0.0F, 12.0F, 9.0F, 7.0F, 0.0F, false);
        this.bodyfirst.texOffs(73, 0).addBox(0.0F, -7.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, false);

        this.bodysecond = new BCVoxel(this);
        this.bodysecond.setPos(0.0F, 1.0F, 7.0F);
        this.bodyfirst.addChild(this.bodysecond);
        this.bodysecond.texOffs(0, 53).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 5.0F, 0.0F, false);
        this.bodysecond.texOffs(76, 2).addBox(0.0F, -5.0F, 0.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);

        this.bodythird = new BCVoxel(this);
        this.bodythird.setPos(0.0F, 0.5F, 5.0F);
        this.bodysecond.addChild(this.bodythird);
        this.bodythird.texOffs(0, 64).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
        this.bodythird.texOffs(92, 0).addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, false);

        this.rightfrontleg = new BCVoxel(this);
        this.rightfrontleg.setPos(-6.0F, -1.0F, -5.0F);
        this.model.addChild(this.rightfrontleg);
        this.rightfrontleg.texOffs(52, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.rightfrontleg.texOffs(52, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.15F, false);

        this.rightbackleg = new BCVoxel(this);
        this.rightbackleg.setPos(-5.0F, -1.0F, 5.0F);
        this.model.addChild(this.rightbackleg);
        this.rightbackleg.texOffs(52, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        this.rightbackleg.texOffs(52, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.15F, false);

        this.leftbackleg = new BCVoxel(this);
        this.leftbackleg.setPos(5.0F, -1.0F, 5.0F);
        this.model.addChild(this.leftbackleg);
        this.leftbackleg.texOffs(52, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        this.leftbackleg.texOffs(52, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.15F, true);

        this.leftfrontleg = new BCVoxel(this);
        this.leftfrontleg.setPos(6.0F, -1.0F, -5.0F);
        this.model.addChild(this.leftfrontleg);
        this.leftfrontleg.texOffs(52, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        this.leftfrontleg.texOffs(52, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.15F, true);

        setupDefaultState();
    }

    @Override
    public void setupAnim(RammerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float idle1 = DEMath.sin(ageInTicks * 0.15F);
        float idle2 = DEMath.sin(ageInTicks * 0.225F);
        float idle3 = Mth.abs(DEMath.sin(ageInTicks * 0.025F));

        addOffset(this.body, 0, 0.2F * idle3, 0);
        addRotation(this.bodyfirst, -1.0F * idle1, -1.0F * idle2, 0);
        addRotation(this.bodysecond, -2.0F * idle1, -2.0F * idle2, 0);
        addRotation(this.bodythird, -2.0F * idle1, -2.0F * idle2, 0);

        this.model.y += -(DEMath.sin(limbSwing * 1.3324F + (float)Math.PI) * limbSwingAmount * 0.5F);
        this.rightbackleg.xRot += DEMath.cos(limbSwing * 0.6662F) * 1.25F * limbSwingAmount;
        this.leftbackleg.xRot += DEMath.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.25F * limbSwingAmount;
        this.rightfrontleg.xRot += DEMath.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.25F * limbSwingAmount;
        this.leftfrontleg.xRot += DEMath.cos(limbSwing * 0.6662F) * 1.25F * limbSwingAmount;
    }

    @Override
    public void handleKeyframedAnimations(RammerEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.getTick() + getPartialTick());


        if(entity.mainHandler.isPlaying(RammerEntity.RAM)) {

            animator.setupKeyframe(4);
            animator.move(this.model, 0.0F, -0.2F, 3.0F);
            animator.move(this.body, 0.0F, 0.0F, 1.0F);
            animator.rotate(this.body, 10.0F, 0.0F, 0.0F);
            animator.rotate(this.bodysecond, 10.0F, 0.0F, 0.0F);
            animator.rotate(this.rightfrontleg, -30.0F, 0.0F, 0.0F);
            animator.rotate(this.rightbackleg, -30.0F, 0.0F, 0.0F);
            animator.rotate(this.leftfrontleg, -30.0F, 0.0F, 0.0F);
            animator.rotate(this.leftbackleg, -30.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.setupKeyframe(4);
            animator.move(this.model, 0.0F, -0.2F, -4.0F);
            animator.rotate(this.body, -5.0F, 0.0F, 0.0F);
            animator.rotate(this.head, -5.0F, 0.0F, 0.0F);
            animator.scale(this.lastshell, 0.0F, 0.5F, 0.5F);
            animator.rotate(this.rightfrontleg, 45.0F, 0.0F, 0.0F);
            animator.rotate(this.rightbackleg, 45.0F, 0.0F, 0.0F);
            animator.rotate(this.leftfrontleg, 45.0F, 0.0F, 0.0F);
            animator.rotate(this.leftbackleg, 45.0F, 0.0F, 0.0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.emptyKeyframe(3, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

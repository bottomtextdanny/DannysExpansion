package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.GrandRammerEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;

public class
GrandRammerModel extends BCEntityModel<GrandRammerEntity> {
    private final BCVoxel body;
    private final BCVoxel torso;
    private final BCVoxel head;
    private final BCVoxel shell;
    private final BCVoxel bodyfirst;
    private final BCVoxel bodyfirstSpine;
    private final BCVoxel bodysecond;
    private final BCVoxel bodythird;
    private final BCVoxel bodyfourth;
    private final BCVoxel frontrightleg;
    private final BCVoxel frontleftleg;
    private final BCVoxel backleftleg;
    private final BCVoxel backrightleg;

    public GrandRammerModel(float delta) {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 16.0F, -8.0F);

        this.torso = new BCVoxel(this);
        this.torso.setPos(0.0F, 0.0F, 8.0F);
        this.body.addChild(this.torso);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -6.0F, -4.0F);
        this.torso.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-9.0F, -8.0F, -13.0F, 18.0F, 14.0F, 13.0F, delta, false);
        this.head.texOffs(43, 51).addBox(0.0F, -13.0F, -10.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);

        this.shell = new BCVoxel(this);
        this.shell.setPos(0.0F, -1.0F, -10.0F);
        this.head.addChild(this.shell);
        this.shell.texOffs(62, 0).addBox(-9.5F, -7.5F, -3.5F, 19.0F, 15.0F, 3.0F, -0.25F, false);
        this.shell.texOffs(62, 18).addBox(-9.5F, -7.5F, -3.5F, 19.0F, 15.0F, 3.0F, 0.0F, false);
        this.shell.texOffs(34, 52).addBox(-9.5F, 5.5F, -7.5F, 19.0F, 2.0F, 7.0F, 0.0F, false);
        this.shell.texOffs(54, 71).addBox(-9.5F, 1.5F, -7.5F, 19.0F, 4.0F, 4.0F, 0.0F, false);

        this.bodyfirst = new BCVoxel(this);
        this.bodyfirst.setPos(0.0F, -6.5F, -4.0F);
        this.torso.addChild(this.bodyfirst);
        this.bodyfirst.texOffs(0, 27).addBox(-7.0F, -6.5F, 0.0F, 14.0F, 13.0F, 10.0F, delta, false);

        this.bodyfirstSpine = new BCVoxel(this);
        this.bodyfirst.setPos(0.0F, -6.5F, -4.0F);
        this.bodyfirst.addChild(this.bodyfirstSpine);
        this.bodyfirstSpine.texOffs(43, 51).addBox(0.0F, -11.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);


        this.bodysecond = new BCVoxel(this);
        this.bodysecond.setPos(0.0F, 1.5F, 10.0F);
        this.bodyfirst.addChild(this.bodysecond);
        this.bodysecond.texOffs(0, 50).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 7.0F, delta, false);
        this.bodysecond.texOffs(43, 56).addBox(0.0F, -10.0F, -1.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);

        this.bodythird = new BCVoxel(this);
        this.bodythird.setPos(0.0F, 1.0F, 7.0F);
        this.bodysecond.addChild(this.bodythird);
        this.bodythird.texOffs(0, 67).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 4.0F, delta, false);

        this.bodyfourth = new BCVoxel(this);
        this.bodyfourth.setPos(0.0F, 0.0F, 4.0F);
        this.bodythird.addChild(this.bodyfourth);
        this.bodyfourth.texOffs(0, 77).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 6.0F, delta, false);
        this.bodyfourth.texOffs(63, 51).addBox(0.0F, -9.0F, -1.0F, 0.0F, 5.0F, 10.0F, 0.0F, false);

        this.frontrightleg = new BCVoxel(this);
        this.frontrightleg.setPos(-8.0F, 0.0F, 0.0F);
        this.body.addChild(this.frontrightleg);
        this.frontrightleg.texOffs(48, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
        this.frontrightleg.texOffs(72, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.25F, false);

        this.frontleftleg = new BCVoxel(this);
        this.frontleftleg.setPos(8.0F, 0.0F, 0.0F);
        this.body.addChild(this.frontleftleg);
        this.frontleftleg.texOffs(48, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, true);
        this.frontleftleg.texOffs(72, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.25F, true);

        this.backleftleg = new BCVoxel(this);
        this.backleftleg.setPos(6.0F, 0.0F, 15.0F);
        this.body.addChild(this.backleftleg);
        this.backleftleg.texOffs(48, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, true);
        this.backleftleg.texOffs(72, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.25F, true);

        this.backrightleg = new BCVoxel(this);
        this.backrightleg.setPos(-6.0F, 0.0F, 15.0F);
        this.body.addChild(this.backrightleg);
        this.backrightleg.texOffs(48, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
        this.backrightleg.texOffs(72, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.25F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(GrandRammerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float idle = DEMath.sin(ageInTicks * 0.15F);
        float idle1 = DEMath.sin(ageInTicks * 0.225F);
        float idle2 = Mth.abs(DEMath.sin(ageInTicks * 0.03F));

        this.bodyfirstSpine.visible = !entity.isSaddled();

        if (entity.getPassengers().isEmpty()) {
            this.bodyfirst.y += 0.3F * idle2;
            this.head.y += 0.3F * idle2;
        }

        this.bodyfirst.xRot -= RAD * 0.5 * idle;
        this.bodysecond.xRot -= RAD * idle;
        this.bodythird.xRot -= RAD * idle;
        this.bodyfirst.yRot -= RAD * 0.5 * idle1;
        this.bodysecond.yRot -= RAD * idle1;
        this.bodythird.yRot -= RAD * idle1;

        this.body.y += -(DEMath.sin(limbSwing * 1.3324F + (float)Math.PI) * limbSwingAmount * 0.5F);
        this.backrightleg.xRot += DEMath.cos(limbSwing * 0.6662F) * 1.25F * limbSwingAmount;
        this.backleftleg.xRot += DEMath.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.25F * limbSwingAmount;
        this.frontrightleg.xRot += DEMath.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.25F * limbSwingAmount;
        this.frontleftleg.xRot += DEMath.cos(limbSwing * 0.6662F) * 1.25F * limbSwingAmount;

    }

    @Override
    public void handleKeyframedAnimations(GrandRammerEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());

        if (entity.mainHandler.isPlaying(GrandRammerEntity.RAM)) {

            animator.setupKeyframe(6);
            animator.move(this.body, 0F, 0.4F, 6.9F);
            animator.move(this.torso, 0F, 0F, 2.0F);
            animator.rotate(this.torso, 2.5F, 0F, 0F);
            animator.rotate(this.head, 12.5F, 0F, 0F);
            animator.rotate(this.bodysecond, 7.5F, 0F, 0F);
            animator.rotate(this.bodythird, 10.0F, 0F, 0F);
            animator.rotate(this.frontrightleg, -47.5F, 0F, 0F);
            animator.rotate(this.frontleftleg, -47.5F, 0F, 0F);
            animator.rotate(this.backrightleg, -47.5F, 0F, 0F);
            animator.rotate(this.backleftleg, -47.5F, 0F, 0F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.move(this.body, 0F, 0F, -5.8F);
            animator.move(this.torso, 0F, 0F, -2.0F);
            animator.rotate(this.torso, -2.5F, 0F, 0F);
            animator.rotate(this.head, -7.5F, 0F, 0F);
            animator.scale(this.shell, 0F, 0F, 0.3F);
            animator.rotate(this.bodysecond, 7.5F, 0F, 0F);
            animator.rotate(this.bodythird, 5.0F, 0F, 0F);
            animator.rotate(this.frontrightleg, 40.0F, 0F, 0F);
            animator.rotate(this.frontleftleg, 40.0F, 0F, 0F);
            animator.rotate(this.backrightleg, 40.0F, 0F, 0F);
            animator.rotate(this.backleftleg, 40.0F, 0F, 0F);
            animator.apply();

            animator.emptyKeyframe(6, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);

    }
}

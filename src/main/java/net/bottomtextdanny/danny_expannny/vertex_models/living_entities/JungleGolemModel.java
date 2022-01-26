package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.JungleGolemEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class JungleGolemModel extends BCEntityModel<JungleGolemEntity> {
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel rightArm;
    private final BCVoxel leftArm;
    private final BCVoxel head;
    private final BCVoxel leftLeg;
    private final BCVoxel rightLeg;

    public JungleGolemModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 14.0F, 0.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        this.hip.texOffs(26, 31).addBox(-5.0F, -7.0F, -3.0F, 10.0F, 7.0F, 6.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -7.0F, 0.0F);
        this.hip.addChild(this.chest);
	    setRotationAngle(this.chest, 0.0436F, 0.0F, -0.0873F);
        this.chest.texOffs(0, 0).addBox(-8.0F, -9.0F, -5.0F, 16.0F, 9.0F, 10.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-8.0F, -6.0F, 0.0F);
        this.chest.addChild(this.rightArm);
	    setRotationAngle(this.rightArm, -0.0436F, 0.0F, 0.0873F);
        this.rightArm.texOffs(0, 37).addBox(-5.0F, -2.0F, -2.5F, 5.0F, 18.0F, 5.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(8.0F, -6.0F, 0.0F);
        this.chest.addChild(this.leftArm);
	    setRotationAngle(this.leftArm, -0.0436F, 0.0F, 0.0F);
        this.leftArm.texOffs(20, 44).addBox(0.0F, -2.0F, -2.5F, 5.0F, 14.0F, 5.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -9.0F, 0.0F);
        this.chest.addChild(this.head);
	    setRotationAngle(this.head, 0.0F, 0.0F, 0.0872F);
        this.head.texOffs(0, 19).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(3.0F, 0.0F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(40, 44).addBox(-1.0F, 0.0F, -3.0F, 5.0F, 10.0F, 6.0F, 0.1F, true);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-3.0F, 0.0F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(40, 44).addBox(-4.0F, 0.0F, -3.0F, 5.0F, 10.0F, 6.0F, 0.1F, false);
	
	    setupDefaultState();
    }

    @Override
    public void handleRotations(JungleGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.yRot += headYaw * RAD;
        this.head.xRot += headPitch * (RAD / 5 * 4);
        this.chest.yRot += headYaw * (RAD / 5);
        this.chest.xRot += Math.abs(headYaw) * RAD / 180 * 20;
        this.rightArm.xRot += -Math.abs(headYaw) * RAD / 180 * 20;
        this.leftArm.xRot += -Math.abs(headYaw) * RAD / 180 * 20;

        walkOffsetY(this.body, 1, 0.75F, 0, 1.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.hip, 1, 2.5F, 0.15F, 12.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.chest, 1, 2.5F, 0.2F, 2.5F, limbSwing, limbSwingAmount, false);
        walkRotateY(this.chest, 0.5F, 2.5F, 0.2F, 0F, limbSwing, limbSwingAmount, false);
        walkRotateZ(this.chest, 0.5F, 1F, 0F, 2.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.head, 0.5F, -4F, 0.25F, 0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightArm, 0.5F, 15F, 0.05F, 0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftArm, 0.5F, 35F, 0, 0F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightLeg, 0.5F, -35F, 0, 0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftLeg, 0.5F, -35F, 0, 0F, limbSwing, limbSwingAmount, true);
    }

    @Override
    public void handleKeyframedAnimations(JungleGolemEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick()  + getPartialTick());

        if (entity.mainAnimationHandler.isPlaying(entity.slam)) {

            animator.setupKeyframe(4);
            animator.rotate(this.hip, -10F, 0F, 0F);
            animator.rotate(this.chest, -7.5F, 30.0F, 10.0F);
            animator.rotate(this.head, -10F, -17.5F, 0F);
            animator.rotate(this.rightArm, 22.5F, 0F, 127.5F);
            animator.rotate(this.leftArm, -37.5F, 0F, -72.5F);
            animator.apply(Easing.EASE_IN_SQUARE);

            animator.setupKeyframe(5);
            animator.move(this.body, 0F, 0.3F, -3.0F);
            animator.rotate(this.body, 0F, -10.0F, 0F);
            animator.rotate(this.hip, 35.0F, 0F, 0F);
            animator.rotate(this.chest, 5.0F, -32.5F, 2.5F);
            animator.rotate(this.head, -42.5F, 40.0F, 0F);
            animator.rotate(this.rightArm, -62.5F, 0F, 65.0F);
            animator.rotate(this.leftArm, 2.5F, 0F, -32.5F);
            animator.rotate(this.rightLeg, -15.0F, 10.0F, 0F);
            animator.rotate(this.leftLeg, 15.0F, 10.0F, 0F);
            animator.apply(Easing.EASE_IN_CUBIC);

            animator.emptyKeyframe(6, Easing.LINEAR);
        }

        else if (entity.mainAnimationHandler.isPlaying(entity.punch)) {

            animator.setupKeyframe(5);
            animator.rotate(this.hip, 0F, 10.0F, 0F);
            animator.rotate(this.head, 0F, -42.5F, 0F);
            animator.apply();

            animator.setupKeyframe(3);
            animator.rotate(this.hip, 0F, -2.5F, 0F);
            animator.rotate(this.head, 0F, 47.5F, 0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(5);
            animator.rotate(this.chest, 0F, 42.5F, -10.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.chest, 20.0F, -5.0F, -10.0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.chest, 0F, -65.0F, -10.0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(5);
            animator.rotate(this.rightArm, -52.5F, 85.0F, 0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.rightArm, -100.0F, 30.0F, 0F);
            animator.apply();

            animator.setupKeyframe(3.5F);
            animator.rotate(this.rightArm, -95.0F, -52.5F, 0F);
            animator.apply();

            animator.setupKeyframe(1.5F);
            animator.rotate(this.rightArm, -47.5F, 2.5F, 0F);
            animator.apply();

            animator.emptyKeyframe(6.5F, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(5);
            animator.rotate(this.leftArm, 22.5F, 0F, -10.0F);
            animator.apply();

            animator.setupKeyframe(5);
            animator.rotate(this.leftArm, 42.5F, 0F, -10.0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);
        }

        else if (entity.mainAnimationHandler.isPlaying(entity.heavyPunch)) {

            animator.setupKeyframe(7);
            animator.rotate(this.body, 0F, 35F, 0F);
            animator.rotate(this.head, 7.5F, -45.0F, 10.0F);
            animator.rotate(this.rightArm, -50F, -22.5F, 0F);
            animator.rotate(this.leftArm, 30.0F, 0F, 0F);
            animator.rotate(this.leftLeg, 0F, -35.0F, 0F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.rotate(this.body, 0F, 35F, 0F);
            animator.rotate(this.head, 7.5F, -45.0F, 10.0F);
            animator.rotate(this.rightArm, -100.0F, -50.0F, 0F);
            animator.rotate(this.leftArm, 30.0F, 0F, 0F);
            animator.rotate(this.leftLeg, 0F, -35.0F, 0F);
            animator.apply();

            animator.setupKeyframe(2);
            animator.rotate(this.body, 0F, -35F, 0F);
            animator.rotate(this.head, 7.5F, 62.5F, 0.0F);
            animator.rotate(this.rightArm, -100.0F, -20.0F, 0F);
            animator.rotate(this.leftArm, 30.0F, 0F, 0F);
            animator.rotate(this.leftLeg, 0F, 35.0F, 0F);
            animator.apply();

            animator.staticKeyframe(2);

            animator.emptyKeyframe(8, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(11);
            animator.rotate(this.hip, -25.0F, 7.5F, -12.5F);
            animator.rotate(this.chest, 15.0F, 25.0F, 0F);
            animator.scale(this.rightArm, 0F, -0.1F, 0F);
            animator.apply();

            animator.setupKeyframe(2);
            animator.rotate(this.hip, 20.0F, 2.5F, 0F);
            animator.rotate(this.chest, 5.0F, -35.0F, 7.5F);
            animator.apply();

            animator.staticKeyframe(2);

            animator.emptyKeyframe(8, Easing.LINEAR);

            //
            animator.reset();

            animator.setupKeyframe(3.5F);
            animator.move(this.body, 0.2F, 0F, 0.95F);
            animator.rotate(this.rightLeg, -20.0F, 0F, 0F);
            animator.apply();

            animator.setupKeyframe(3.5F);
            animator.move(this.body, 0.6F, 0F, 1.7F);
            animator.apply();

            animator.staticKeyframe(4F);

            animator.setupKeyframe(1F);
            animator.rotate(this.rightLeg, -32.5F, 17.5F, 0F);
            animator.apply();

            animator.setupKeyframe(1F);
            animator.move(this.body, 0.6F, 0F, -1.7F);
            animator.rotate(this.rightLeg, 0F, 35.0F, 0F);
            animator.apply();

            animator.staticKeyframe(2F);

            animator.setupKeyframe(4F);
            animator.move(this.body, 0.2F, 0F, -0.9F);
            animator.rotate(this.rightLeg, 10.0F, 17.5F, 0F);
            animator.apply();

            animator.emptyKeyframe(4, Easing.LINEAR);
        }

        else if (entity.mainAnimationHandler.isPlaying(entity.droneFront)) {

            animator.setupKeyframe(10);
            animator.move(this.body, 0.2F, 0.0F, 1.5F);
            animator.rotate(this.body, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.hip, 0.0F, 22.5F, -10.0F);
            animator.rotate(this.chest, 0.0F, 37.5F, 0.0F);
            animator.rotate(this.head, 0.0F, -75.0F, 0.0F);
            animator.rotate(this.leftArm, -95.0F, -70.0F, 12.5F);
            animator.rotate(this.rightLeg, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -25.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(2);
            animator.move(this.body, 0.2F, 0.0F, 1.5F);
            animator.rotate(this.body, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.hip, -32.5F, 22.5F, -10.0F);
            animator.rotate(this.chest, 12.5F, 37.5F, 0.0F);
            animator.rotate(this.head, 0.0F, -75.0F, 15.0F);
            animator.scale(this.leftArm, 0.2F, -0.2F, 0.2F);
            animator.rotate(this.leftArm, -95.0F, -70.0F, 35.0F);
            animator.rotate(this.rightLeg, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -25.0F, 0.0F);
            animator.apply();

            animator.setupKeyframe(8);
            animator.move(this.body, 0.2F, 0.0F, 1.5F);
            animator.rotate(this.body, 0.0F, 25.0F, 0.0F);
            animator.rotate(this.hip, 0.0F, 22.5F, -10.0F);
            animator.rotate(this.chest, 0.0F, 37.5F, 0.0F);
            animator.rotate(this.head, 0.0F, -75.0F, 0.0F);
            animator.rotate(this.leftArm, -95.0F, -70.0F, 12.5F);
            animator.rotate(this.rightLeg, 22.5F, 0.0F, 0.0F);
            animator.rotate(this.leftLeg, 0.0F, -25.0F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(8, Easing.LINEAR);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

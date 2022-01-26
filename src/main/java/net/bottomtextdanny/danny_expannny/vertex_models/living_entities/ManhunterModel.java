package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.manhunter.ManhunterEntity;
import net.minecraft.util.Mth;

public class ManhunterModel extends BCEntityModel<ManhunterEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel rightHand;
    private final BCVoxel weapon;
    private final BCVoxel blade;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel leftHand;
    private final BCVoxel cape;
    private final BCVoxel rightLeg;
    private final BCVoxel rightCalf;
    private final BCVoxel rightFoot;
    private final BCVoxel leftLeg;
    private final BCVoxel leftCalf;
    private final BCVoxel leftFoot;

    public ManhunterModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 5.0F, 0.0F);
        this.model.addChild(this.body);

        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.1745F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-9.0F, -9.0F, -5.0F, 18.0F, 10.0F, 11.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, -9.0F, 1.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.48F, 0.0F, 0.0F);
        this.chest.texOffs(0, 21).addBox(-12.0F, -14.0F, -8.0F, 24.0F, 14.0F, 16.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -14.0F, -5.0F);
        this.chest.addChild(this.head);
        setRotationAngle(this.head, -0.1745F, 0.0F, 0.0F);
        this.head.texOffs(0, 51).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-12.0F, -9.0F, 0.0F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, 0.0436F, 0.0F, 0.0436F);
        this.rightArm.texOffs(0, 71).addBox(-7.0F, -4.0F, -4.5F, 7.0F, 12.0F, 8.0F, 0.0F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-3.5F, 8.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        setRotationAngle(this.rightForearm, -0.5672F, 0.0F, 0.0F);
        this.rightForearm.texOffs(30, 71).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.1F, false);

        this.rightHand = new BCVoxel(this);
        this.rightHand.setPos(0.0F, 9.0F, 0.0F);
        this.rightForearm.addChild(this.rightHand);
        this.rightHand.texOffs(54, 71).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 7.0F, 7.0F, 0.0F, false);

        this.weapon = new BCVoxel(this);
        this.weapon.setPos(0.0F, 4.0F, 1.0F);
        this.rightHand.addChild(this.weapon);
        this.weapon.texOffs(62, 95).addBox(-1.0F, -1.0F, -20.0F, 2.0F, 2.0F, 31.0F, 0.0F, false);
        this.weapon.texOffs(34, 114).addBox(-2.0F, -2.0F, -30.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

        this.blade = new BCVoxel(this);
        this.blade.setPos(0.0F, 0.0F, -25.0F);
        this.weapon.addChild(this.blade);
        setRotationAngle(this.blade, -0.7854F, 0.0F, 0.0F);
        this.blade.texOffs(106, 94).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 10.0F, 10.0F, 0.0F, false);
        this.blade.texOffs(114, 114).addBox(-0.5F, -5.5F, -5.5F, 1.0F, 6.0F, 6.0F, 0.2F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(12.0F, -9.0F, 0.0F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, 0.0436F, 0.0F, -0.0436F);
        this.leftArm.texOffs(0, 71).addBox(0.0F, -4.0F, -4.5F, 7.0F, 12.0F, 8.0F, 0.0F, true);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(3.5F, 8.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        setRotationAngle(this.leftForearm, -0.5672F, 0.0F, 0.0F);
        this.leftForearm.texOffs(30, 71).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.1F, true);

        this.leftHand = new BCVoxel(this);
        this.leftHand.setPos(0.0F, 9.0F, 0.0F);
        this.leftForearm.addChild(this.leftHand);
        this.leftHand.texOffs(54, 71).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 7.0F, 7.0F, 0.0F, true);

        this.cape = new BCVoxel(this);
        this.cape.setPos(0.0F, -13.0F, 8.0F);
        this.chest.addChild(this.cape);
        this.cape.texOffs(64, 0).addBox(-10.0F, 0.0F, 0.0F, 20.0F, 29.0F, 1.0F, 0.0F, false);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-5.5F, 0.0F, 1.5F);
        this.body.addChild(this.rightLeg);
        setRotationAngle(this.rightLeg, -0.48F, 0.3491F, 0.0F);
        this.rightLeg.texOffs(0, 91).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, 0.1F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 9.0F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        setRotationAngle(this.rightCalf, 0.6981F, 0.0F, 0.0F);
        this.rightCalf.texOffs(28, 91).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

        this.rightFoot = new BCVoxel(this);
        this.rightFoot.setPos(0.0F, 9.0F, 0.0F);
        this.rightCalf.addChild(this.rightFoot);
        setRotationAngle(this.rightFoot, -0.2182F, 0.0F, 0.0F);
        this.rightFoot.texOffs(52, 91).addBox(-3.5F, -1.0F, -5.5F, 7.0F, 3.0F, 9.0F, 0.0F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(5.5F, 0.0F, 1.5F);
        this.body.addChild(this.leftLeg);
        setRotationAngle(this.leftLeg, -0.48F, -0.3491F, 0.0F);
        this.leftLeg.texOffs(0, 91).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, 0.1F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 9.0F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        setRotationAngle(this.leftCalf, 0.6981F, 0.0F, 0.0F);
        this.leftCalf.texOffs(28, 91).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, true);

        this.leftFoot = new BCVoxel(this);
        this.leftFoot.setPos(0.0F, 9.0F, 0.0F);
        this.leftCalf.addChild(this.leftFoot);
        setRotationAngle(this.leftFoot, -0.2182F, 0.0F, 0.0F);
        this.leftFoot.texOffs(52, 91).addBox(-3.5F, -1.0F, -5.5F, 7.0F, 3.0F, 9.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(ManhunterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.yRot += headYaw * RAD * 0.8;
        this.head.xRot += headPitch * RAD;
        this.chest.yRot += headYaw * RAD * 0.2;
    }

    public void handleKeyframedAnimations(ManhunterEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationHandler.getTick()  + getPartialTick());

        float easedlimbSwingAmount = caculateLimbSwingAmountEasing(entity);
        float f = Mth.clamp(easedlimbSwingAmount * 8F, 0, 1);
        float easedLimbSwing = caculateLimbSwingEasing(entity);

        EntityModelAnimator walk = new EntityModelAnimator(this, Mth.clamp(easedLimbSwing, 0, 0.999F)).multiplier(f);

        walk.setupKeyframe(0.0F);
        walk.rotate(this.hip, 12.5F, 0F, 0F);
        walk.rotate(this.chest, -5.0F, 5.0F, -2.5F);
        walk.rotate(this.head, -10.0F, -5.0F, 0F);
        walk.rotate(this.rightLeg, -27.5F, -20.0F, 0F);
        walk.rotate(this.rightFoot, 27.5F, 0F, 0F);
        walk.rotate(this.leftLeg, 27.5F, 20.0F, 0F);
        walk.rotate(this.leftFoot, 17.5F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0F, 0F);
        walk.rotate(this.chest, -5.0F, 0F, 0F);
        walk.rotate(this.head, -5.0F, 0F, 0F);
        walk.rotate(this.rightLeg, 18.25F, -20.0F, 0F);
        walk.rotate(this.rightFoot, 22.5F, 0F, 0F);
        walk.rotate(this.leftLeg, 16.25F, 20.0F, 0F);
        walk.rotate(this.leftFoot, 2.5F, 0F, 0F);
        walk.apply();
        
        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 12.5F, 0F, 0F);
        walk.rotate(this.chest, -5.0F, -5.0F, 2.5F);
        walk.rotate(this.head, -10.0F, 5.0F, 0F);
        walk.rotate(this.rightLeg, 27.5F, -20.0F, 0F);
        walk.rotate(this.rightFoot, 17.5F, 0F, 0F);
        walk.rotate(this.leftLeg, -27.5F, 20.0F, 0F);
        walk.rotate(this.leftFoot, 27.5F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 7.5F, 0F, 0F);
        walk.rotate(this.chest, -5.0F, 0F, 0F);
        walk.rotate(this.head, -5.0F, 0F, 0F);
        walk.rotate(this.rightLeg, 16.25F, -20.0F, 0F);
        walk.rotate(this.rightFoot, 2.5F, 0F, 0F);
        walk.rotate(this.leftLeg, 18.25F, 20.0F, 0F);
        walk.rotate(this.leftFoot, 22.5F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.hip, 12.5F, 0F, 0F);
        walk.rotate(this.chest, -5.0F, 5.0F, -2.5F);
        walk.rotate(this.head, -10.0F, -5.0F, 0F);
        walk.rotate(this.rightLeg, -27.5F, -20.0F, 0F);
        walk.rotate(this.rightFoot, 27.5F, 0F, 0F);
        walk.rotate(this.leftLeg, 27.5F, 20.0F, 0F);
        walk.rotate(this.leftFoot, 17.5F, 0F, 0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0.0F);
        walk.rotate(this.rightCalf, 0F, 0F, 0F);
        walk.rotate(this.leftCalf, 40.0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.rotate(this.rightCalf, -15.0F, 0F, 0F);
        walk.rotate(this.leftCalf, 35.0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.rotate(this.rightCalf, -31.25F, 0F, 0F);
        walk.rotate(this.leftCalf, 33.75F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightCalf, 40.0F, 0F, 0F);
        walk.rotate(this.leftCalf, 0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.rotate(this.rightCalf, 35.0F, 0F, 0F);
        walk.rotate(this.leftCalf, -15.0F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.125F);
        walk.rotate(this.rightCalf, 33.75F, 0F, 0F);
        walk.rotate(this.leftCalf, -31.25F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.25F);
        walk.rotate(this.rightCalf, 0F, 0F, 0F);
        walk.rotate(this.leftCalf, 40.0F, 0F, 0F);
        walk.apply();


        //
        walk.reset();

        walk.setupKeyframe(0F);
        walk.move(this.body, 0F, 3.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 1.4F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 0.2F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -1.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -1.6F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -2.4F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -2.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -0.3F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 3.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 1.4F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 0.2F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -1.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -1.6F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -2.4F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -2.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, -0.3F, 0F);
        walk.apply();

        walk.setupKeyframe(0.0625F);
        walk.move(this.body, 0F, 3.0F, 0F);
        walk.apply();

        //
        walk.reset();

        walk.setupKeyframe(0F);
        walk.rotate(this.rightArm, 27.5F, 0F, 0F);
        walk.rotate(this.rightForearm, -15.0F, 0F, 0F);
        walk.rotate(this.leftArm, -10.0F, 5.0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.rightArm, -10.0F, -5.0F, 0F);
        walk.rotate(this.rightForearm, 0F, 0F, 0F);
        walk.rotate(this.leftArm, 27.5F, 0F, 0F);
        walk.apply();

        walk.setupKeyframe(0.5F);
        walk.rotate(this.rightArm, 27.5F, 0F, 0F);
        walk.rotate(this.rightForearm, -15.0F, 0F, 0F);
        walk.rotate(this.leftArm, -10.0F, 5.0F, 0F);
        walk.apply();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

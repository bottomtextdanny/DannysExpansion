package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DesolatedEntity;

public class DesolatedModel extends BCEntityModel<DesolatedEntity> {
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel rightArm;
    private final BCVoxel rightForearm;
    private final BCVoxel leftArm;
    private final BCVoxel leftForearm;
    private final BCVoxel rightLeg;
    private final BCVoxel rightCalf;
    private final BCVoxel leftLeg;
    private final BCVoxel leftCalf;

    public DesolatedModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 10.0F, 0.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, 1.5F, 0.0F);
        this.body.addChild(this.hip);
        this.hip.texOffs(0, 0).addBox(-4.0F, -8.0F, -1.5F, 8.0F, 8.0F, 3.0F, 0.1F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.5F, -8.0F, 0.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.0873F, 0.0F, 0.0F);
        this.chest.texOffs(22, 0).addBox(-6.0F, -5.0F, -2.0F, 11.0F, 5.0F, 4.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -5.0F, 0.0F);
        this.chest.addChild(this.head);
        this.head.texOffs(22, 9).addBox(-4.5F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        this.head.texOffs(22, 25).addBox(-4.5F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);

        this.rightArm = new BCVoxel(this);
        this.rightArm.setPos(-6.0F, -3.0F, -0.5F);
        this.chest.addChild(this.rightArm);
        setRotationAngle(this.rightArm, -0.0873F, 0.0F, 0.0F);
        this.rightArm.texOffs(0, 11).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.1F, false);

        this.rightForearm = new BCVoxel(this);
        this.rightForearm.setPos(-1.0F, 6.0F, 0.0F);
        this.rightArm.addChild(this.rightForearm);
        this.rightForearm.texOffs(8, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

        this.leftArm = new BCVoxel(this);
        this.leftArm.setPos(5.0F, -3.0F, -0.5F);
        this.chest.addChild(this.leftArm);
        setRotationAngle(this.leftArm, -0.0873F, 0.0F, 0.0F);
        this.leftArm.texOffs(0, 11).addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.1F, false);

        this.leftForearm = new BCVoxel(this);
        this.leftForearm.setPos(1.0F, 6.0F, 0.0F);
        this.leftArm.addChild(this.leftForearm);
        this.leftForearm.texOffs(8, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

        this.rightLeg = new BCVoxel(this);
        this.rightLeg.setPos(-3.0F, 1.5F, 0.0F);
        this.body.addChild(this.rightLeg);
        this.rightLeg.texOffs(0, 20).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        this.rightCalf = new BCVoxel(this);
        this.rightCalf.setPos(0.0F, 5.5F, 0.0F);
        this.rightLeg.addChild(this.rightCalf);
        this.rightCalf.texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, false);

        this.leftLeg = new BCVoxel(this);
        this.leftLeg.setPos(3.0F, 1.5F, 0.0F);
        this.body.addChild(this.leftLeg);
        this.leftLeg.texOffs(0, 20).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, true);

        this.leftCalf = new BCVoxel(this);
        this.leftCalf.setPos(0.0F, 5.5F, 0.0F);
        this.leftLeg.addChild(this.leftCalf);
        this.leftCalf.texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.05F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(DesolatedEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        walkOffsetY(this.body, this.globalSpeed, 1.5F, 0, 2.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.hip, this.globalSpeed, 2.5F, 0.0F, 7.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.chest, this.globalSpeed, 2.5F, 0.05F, 2.5F, limbSwing, limbSwingAmount, false);
        walkRotateY(this.chest, this.globalSpeed / 2, 5, -0.05F, 0, limbSwing, limbSwingAmount, false);
        walkRotateX(this.head, this.globalSpeed, -7.5F, 0.15F, 0.0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightArm, this.globalSpeed / 2, 31.25F, 0F, 11.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightForearm, this.globalSpeed / 2, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftArm, this.globalSpeed / 2, 31.25F, 0F, 11.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftForearm, this.globalSpeed / 2, -6.25F, 0.2F, -16.25F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightLeg, this.globalSpeed / 2, 35.0F, 0F, -12.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightCalf, this.globalSpeed / 2, -2.5F, 0F, 27.5F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftLeg, this.globalSpeed / 2, 35.0F, 0F, -12.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftCalf, this.globalSpeed / 2, -2.5F, 0F, 27.5F, limbSwing, limbSwingAmount, false);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

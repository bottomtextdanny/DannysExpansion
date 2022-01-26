package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.NyctoidEntity;

public class NyctoidModel extends BCEntityModel<NyctoidEntity> {
    private final BCVoxel body;
    private final BCVoxel hip;
    private final BCVoxel chest;
    private final BCVoxel head;
    private final BCVoxel jaw;
    private final BCVoxel rightFrontLeg;
    private final BCVoxel leftFrontLeg;
    private final BCVoxel rightBackLeg;
    private final BCVoxel leftBackLeg;

    public NyctoidModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 24.0F, 0.0F);


        this.hip = new BCVoxel(this);
        this.hip.setPos(0.0F, -9.8F, 9.5F);
        this.body.addChild(this.hip);
        setRotationAngle(this.hip, -0.0873F, 0.0F, 0.0F);
        this.hip.texOffs(0, 0).addBox(-8.0F, -4.0F, -13.0F, 16.0F, 8.0F, 18.0F, 0.0F, false);

        this.chest = new BCVoxel(this);
        this.chest.setPos(0.0F, 0.0F, -10.0F);
        this.hip.addChild(this.chest);
        setRotationAngle(this.chest, 0.1309F, 0.0F, 0.0F);
        this.chest.texOffs(0, 26).addBox(-10.0F, -6.0F, -14.0F, 20.0F, 11.0F, 14.0F, 0.0F, false);
        this.chest.texOffs(0, 88).addBox(-10.0F, -6.0F, -14.0F, 20.0F, 11.0F, 14.0F, 0.25F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, 1.0F, -13.5F);
        this.chest.addChild(this.head);
        this.head.texOffs(0, 51).addBox(-7.5F, -4.0F, -15.0F, 15.0F, 7.0F, 15.0F, 0.0F, false);

        this.jaw = new BCVoxel(this);
        this.jaw.setPos(0.0F, 3.0F, -0.5F);
        this.head.addChild(this.jaw);
        this.jaw.texOffs(0, 73).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 1.0F, 14.0F, 0.0F, true);

        this.rightFrontLeg = new BCVoxel(this);
        this.rightFrontLeg.setPos(-10.0F, -4.0F, -9.0F);
        this.chest.addChild(this.rightFrontLeg);
        setRotationAngle(this.rightFrontLeg, -0.0436F, 0.0F, 0.0F);
        this.rightFrontLeg.texOffs(68, 24).addBox(-6.0F, -7.5F, -3.0F, 6.0F, 22.0F, 6.0F, 0.0F, false);

        this.leftFrontLeg = new BCVoxel(this);
        this.leftFrontLeg.setPos(10.0F, -4.0F, -9.0F);
        this.chest.addChild(this.leftFrontLeg);
        setRotationAngle(this.leftFrontLeg, -0.0436F, 0.0F, 0.0F);
        this.leftFrontLeg.texOffs(68, 24).addBox(0.0F, -7.5F, -3.0F, 6.0F, 22.0F, 6.0F, 0.0F, true);

        this.rightBackLeg = new BCVoxel(this);
        this.rightBackLeg.setPos(-8.0F, -2.0F, 0.0F);
        this.hip.addChild(this.rightBackLeg);
        setRotationAngle(this.rightBackLeg, 0.0873F, 0.0F, 0.0F);
        this.rightBackLeg.texOffs(68, 0).addBox(-6.0F, -6.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);

        this.leftBackLeg = new BCVoxel(this);
        this.leftBackLeg.setPos(8.0F, -2.0F, 0.0F);
        this.hip.addChild(this.leftBackLeg);
        setRotationAngle(this.leftBackLeg, 0.0873F, 0.0F, 0.0F);
        this.leftBackLeg.texOffs(68, 0).addBox(0.0F, -6.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void handleRotations(NyctoidEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.head.xRot += headPitch * RAD / 1.2;
        this.head.yRot += headYaw * RAD / 2;

        walkOffsetY(this.hip, 1, -0.25F, 0, 0.25F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.hip, 1, 3.5F, 0, -3.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.head, 1, -1.5F, 0.15F, 1.5F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.rightFrontLeg, 0.5F, 25.0F, 0F, 5.0F, limbSwing, limbSwingAmount, false);
        walkRotateX(this.leftFrontLeg, 0.5F, 25.0F, 0.05F, 5.0F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.rightBackLeg, 0.5F, 25.0F, 0.075F, 0F, limbSwing, limbSwingAmount, true);
        walkRotateX(this.leftBackLeg, 0.5F, 25.0F, 0.025F, 0F, limbSwing, limbSwingAmount, false);
    }

    @Override
    public void handleKeyframedAnimations(NyctoidEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DarialynEntity;

public class DarialynModel extends BCEntityModel<DarialynEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel finPart0;
    private final BCVoxel finPart1;
    private final BCVoxel finPart2;
    private final BCVoxel finPart3;
    private final BCVoxel bodyRight;
    private final BCVoxel chargerRight;
    private final BCVoxel bodyLeft;
    private final BCVoxel chargerLeft;

    public DarialynModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -4.25F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-9.0F, -2.0F, -5.0F, 18.0F, 4.0F, 10.0F, 0.0F, false);

        this.finPart0 = new BCVoxel(this);
        this.finPart0.setPos(0.0F, 0.0F, 5.0F);
        this.body.addChild(this.finPart0);
        this.finPart0.texOffs(0, 33).addBox(-3.0F, -1.5F, 0.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);

        this.finPart1 = new BCVoxel(this);
        this.finPart1.setPos(0.0F, 0.0F, 6.0F);
        this.finPart0.addChild(this.finPart1);
        this.finPart1.texOffs(0, 42).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);

        this.finPart2 = new BCVoxel(this);
        this.finPart2.setPos(0.0F, 0.0F, 6.0F);
        this.finPart1.addChild(this.finPart2);
        this.finPart2.texOffs(0, 50).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 7.0F, 0.1F, false);

        this.finPart3 = new BCVoxel(this);
        this.finPart3.setPos(0.0F, 0.0F, 7.0F);
        this.finPart2.addChild(this.finPart3);
        this.finPart3.texOffs(24, 33).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
        this.finPart3.texOffs(20, 42).addBox(-6.0F, 0.0F, 3.0F, 12.0F, 0.0F, 8.0F, 0.0F, false);

        this.bodyRight = new BCVoxel(this);
        this.bodyRight.setPos(-9.0F, 0.0F, 1.0F);
        this.body.addChild(this.bodyRight);
        setRotationAngle(this.bodyRight, 0.0F, 0.5672F, 0.0F);
        this.bodyRight.texOffs(0, 14).addBox(-9.0F, -1.5F, -4.0F, 13.0F, 3.0F, 8.0F, 0.0F, false);

        this.chargerRight = new BCVoxel(this);
        this.chargerRight.setPos(-9.0F, 0.0F, 0.0F);
        this.bodyRight.addChild(this.chargerRight);
        setRotationAngle(this.chargerRight, 0.0F, 0.3054F, 0.0F);
        this.chargerRight.texOffs(0, 25).addBox(-21.0F, 0.0F, -4.0F, 23.0F, 0.0F, 8.0F, 0.0F, false);

        this.bodyLeft = new BCVoxel(this);
        this.bodyLeft.setPos(9.0F, 0.0F, 1.0F);
        this.body.addChild(this.bodyLeft);
        setRotationAngle(this.bodyLeft, 0.0F, -0.5672F, 0.0F);
        this.bodyLeft.texOffs(0, 14).addBox(-4.0F, -1.5F, -4.0F, 13.0F, 3.0F, 8.0F, 0.0F, true);

        this.chargerLeft = new BCVoxel(this);
        this.chargerLeft.setPos(9.0F, 0.0F, 0.0F);
        this.bodyLeft.addChild(this.chargerLeft);
        setRotationAngle(this.chargerLeft, 0.0F, -0.3054F, 0.0F);
        this.chargerLeft.texOffs(0, 25).addBox(-2.0F, 0.0F, -4.0F, 23.0F, 0.0F, 8.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void setupAnim(DarialynEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.body.xRot = headPitch * ((float)Math.PI / 180F);
        this.body.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

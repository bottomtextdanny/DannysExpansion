package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.EnderArrowEntity;

public class EnderArrowModel extends BCEntityModel<EnderArrowEntity> {
    private final BCVoxel arrow;
    private final BCVoxel texture2;

    public EnderArrowModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.arrow = new BCVoxel(this);
        this.arrow.setPos(0.0F, -3.5F, 2.0F);
        this.arrow.texOffs(0, 0).addBox(-0.5F, -0.5F, -11.0F, 1.0F, 1.0F, 18.0F, 0.0F, false);
        this.arrow.texOffs(0, 38).addBox(-2.5F, 0.0F, -15.0F, 5.0F, 0.0F, 26.0F, 0.0F, false);

        this.texture2 = new BCVoxel(this);
        this.texture2.setPos(0.0F, 0.0F, -2.0F);
        this.arrow.addChild(this.texture2);
        setRotationAngle(this.texture2, 0.0F, 0.0F, 1.5708F);
        this.texture2.texOffs(0, 38).addBox(-2.5F, 0.0F, -13.0F, 5.0F, 0.0F, 26.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(EnderArrowEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        this.arrow.xRot = headPitch * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.arrow.render(poseStack, buffer, packedLight, packedOverlay);
    }

}

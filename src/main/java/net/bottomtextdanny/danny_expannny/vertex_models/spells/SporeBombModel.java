package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SporeBombEntity;

public class SporeBombModel extends BCEntityModel<SporeBombEntity> {
    public final BCVoxel model;

    public SporeBombModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, -5.0F, 0.0F);

        setupDefaultState();
    }

    @Override
    public void handleRotations(SporeBombEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.model.yRot = headYaw * RAD;
        this.model.xRot = 360 * RAD * (ageInTicks * 0.02F % 1);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

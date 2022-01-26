package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.chest;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCBlockEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;

public abstract class BCChestModel extends BCBlockEntityModel<BCChestBlockEntity> {
    protected BCVoxel chest;
    protected BCVoxel lid;

    public void setRotationWLid(BCChestBlockEntity chest, float partialTicks, float ligAngle) {
        this.setRotationAngles(chest, partialTicks);
        float lidAngle = 1 - ligAngle;
        float lidDeg = (-90.0F + lidAngle * lidAngle * lidAngle * 90.0F) * RAD;
        this.lid.xRot = lidDeg;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

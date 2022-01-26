package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BCBlockEntityModel<T extends BlockEntity> extends Model implements BCModel {
    public int texWidth, texHeight;

    public BCBlockEntityModel() {
        super(RenderType::entityCutoutNoCull);
    }

    public void setRotationAngles(T blockEntity, float partialTicks) {
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    }

    public int getTexWidth() {
        return this.texWidth;
    }

    public int getTexHeight() {
        return this.texHeight;
    }
}

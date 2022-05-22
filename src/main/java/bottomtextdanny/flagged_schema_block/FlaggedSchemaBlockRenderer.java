package bottomtextdanny.flagged_schema_block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class FlaggedSchemaBlockRenderer implements BlockEntityRenderer<FlaggedSchemaBlockEntity> {

    public FlaggedSchemaBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FlaggedSchemaBlockEntity blockEntity, float tickOffset, PoseStack pose, MultiBufferSource buffer, int p_112587_, int p_112588_) {
        if (Minecraft.getInstance().player.canUseGameMasterBlocks() || Minecraft.getInstance().player.isSpectator()) {
            int offX = blockEntity.getBoxPosX(), offY = blockEntity.getBoxPosY(), offZ = blockEntity.getBoxPosZ(),
            sizeX = blockEntity.getBoxSizeX(), sizeY = blockEntity.getBoxSizeY(), sizeZ = blockEntity.getBoxSizeZ();

            VertexConsumer vertBuf = buffer.getBuffer(RenderType.lines());
            LevelRenderer.renderLineBox(pose, vertBuf,
                    offX, offY, offZ,
                    offX + sizeX, offY + sizeY, offZ + sizeZ,
                    1.0F, 0.5F, 0.0F, 1.0F,
                    1.0F, 0.5F, 0.0F);

            Matrix4f matrix4f = pose.last().pose();
            Matrix3f matrix3f = pose.last().normal();

            float x = Mth.floor(((double)blockEntity.getBoxSizeX() - 1.0) / 2.0) + blockEntity.getBoxOffsetX() + blockEntity.getBoxPosX() + 0.5F,
                    y = blockEntity.getBoxOffsetY() + blockEntity.getBoxPosY() + 0.5F,
                    z = Mth.floor(((double)blockEntity.getBoxSizeZ() - 1.0) / 2.0) + blockEntity.getBoxOffsetZ() + blockEntity.getBoxPosZ() + 0.5F;

            float r = 0.0F, g = 0.0F, b = 1.0F, a = 1.0F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
            vertBuf.vertex(matrix4f, x, y, z + 1).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();

            b = 0.0F; r = 1.0F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
            vertBuf.vertex(matrix4f, x + 1, y, z).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();

            r = 0.0F; g = 1.0F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertBuf.vertex(matrix4f, x, y + 1, z).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            r = 1.0F; g = 1.0F; a = 0.25F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
            vertBuf.vertex(matrix4f, x, y, z - 1).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();

            r = 0.0F; g = 1.0F; b = 1.0F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
            vertBuf.vertex(matrix4f, x - 1, y, z).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();

            r = 1.0F; g = 0.0F; b = 1.0F;
            vertBuf.vertex(matrix4f, x, y, z).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertBuf.vertex(matrix4f, x, y - 1, z).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(FlaggedSchemaBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(FlaggedSchemaBlockEntity blockEntity, Vec3 pos) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 128;
    }
}

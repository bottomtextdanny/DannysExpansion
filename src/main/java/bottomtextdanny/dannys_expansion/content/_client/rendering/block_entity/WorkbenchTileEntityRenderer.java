package bottomtextdanny.dannys_expansion.content._client.rendering.block_entity;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.block_entities.WorkbenchMainModel;
import bottomtextdanny.dannys_expansion.content._client.model.block_entities.WorkbenchOffModel;
import bottomtextdanny.dannys_expansion.content.block_entities.WorkbenchBlockEntity;
import bottomtextdanny.dannys_expansion.content.block_properties.WorkbenchPart;
import bottomtextdanny.dannys_expansion.content.blocks.WorkbenchBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class WorkbenchTileEntityRenderer implements BlockEntityRenderer<WorkbenchBlockEntity> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/models/block/workbench.png");
    private final WorkbenchMainModel mainModel = new WorkbenchMainModel();
    private final WorkbenchOffModel offModel = new WorkbenchOffModel();

    public WorkbenchTileEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(WorkbenchBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.0, 0.5F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(blockstate.getValue(WorkbenchBlock.FACING).getOpposite().toYRot()));


        if (blockstate.getValue(WorkbenchBlock.PART).name().equals(WorkbenchPart.MAIN.name())) {
            this.mainModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(TEXTURES)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.offModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(TEXTURES)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStackIn.popPose();
    }
}

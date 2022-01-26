package net.bottomtextdanny.danny_expannny.rendering.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.block_entities.WorkbenchMainModel;
import net.bottomtextdanny.danny_expannny.vertex_models.block_entities.WorkbenchOffModel;
import net.bottomtextdanny.danny_expannny.objects.blocks.WorkbenchBlock;
import net.bottomtextdanny.danny_expannny.objects.block_properties.WorkbenchPart;
import net.bottomtextdanny.danny_expannny.objects.block_entities.WorkbenchBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class WorkbenchTileEntityRenderer implements BlockEntityRenderer<WorkbenchBlockEntity> {
    private final WorkbenchMainModel mainModel = new WorkbenchMainModel();
    private final WorkbenchOffModel offModel = new WorkbenchOffModel();

    public WorkbenchTileEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(WorkbenchBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.0, 0.5F);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(blockstate.getValue(WorkbenchBlock.FACING).getOpposite().toYRot()));


        if (blockstate.getValue(WorkbenchBlock.PART).name().equals(WorkbenchPart.MAIN.name())) {
            this.mainModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(this.mainModel.renderType(new ResourceLocation(DannysExpansion.ID, "textures/models/block/workbench_main.png"))), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.offModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(this.offModel.renderType(new ResourceLocation(DannysExpansion.ID, "textures/models/block/workbench_off.png"))), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStackIn.popPose();
    }
}

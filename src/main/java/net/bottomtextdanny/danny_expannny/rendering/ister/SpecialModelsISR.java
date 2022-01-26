package net.bottomtextdanny.danny_expannny.rendering.ister;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IItemModel;
import net.bottomtextdanny.dannys_expansion.core.interfaces.ISpecialModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpecialModelsISR extends BlockEntityWithoutLevelRenderer {

    public SpecialModelsISR() {
        super(null, null);
    }

    //items should implement ISpecialModel
    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();
        matrixStack.pushPose();

        Model model = ((ISpecialModel)item).model();
        ResourceLocation texture = ((ISpecialModel)item).modelTexture();
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, model.renderType(texture), false, stack.hasFoil());
        ((IItemModel)model).pre(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
        model.renderToBuffer(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        ((IItemModel)model).post(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
        matrixStack.popPose();
    }
}

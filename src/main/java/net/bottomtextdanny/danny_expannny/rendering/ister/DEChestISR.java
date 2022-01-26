package net.bottomtextdanny.danny_expannny.rendering.ister;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.objects.blocks.DEChestBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class DEChestISR extends BlockEntityWithoutLevelRenderer {
    public static final IItemRenderProperties AS_PROPERTY = new IItemRenderProperties() {
        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            return new DEChestISR();
        }
    };

    public DEChestISR() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (((BlockItem)stack.getItem()).getBlock() instanceof DEChestBlock block) {
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()), matrixStack, buffer, combinedLight, combinedOverlay);
        }
    }
}

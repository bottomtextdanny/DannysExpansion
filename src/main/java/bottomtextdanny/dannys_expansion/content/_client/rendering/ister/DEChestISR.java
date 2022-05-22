package bottomtextdanny.dannys_expansion.content._client.rendering.ister;

import com.mojang.blaze3d.vertex.PoseStack;
import bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
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
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BCChestBlock block = null;

        if (((BlockItem)stack.getItem()).getBlock() instanceof BCChestBlock chest) {
            block = chest;
        }

        if (block != null)
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()), matrixStack, buffer, combinedLight, combinedOverlay);
    }
}

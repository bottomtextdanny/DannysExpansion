package net.bottomtextdanny.braincell.mod.minecraft_front_rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

@OnlyIn(Dist.CLIENT)
public final class BigItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final IItemRenderProperties AS_PROPERTY = new IItemRenderProperties() {
        @Override
        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
            return new BigItemRenderer();
        }
    };

    public BigItemRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(
                new ModelResourceLocation(DannysExpansion.ID + ":" + stack.getItem().getRegistryName().getPath() + "_handheld", "inventory"));
        boolean flag =
                transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND ||
                transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;

        poseStack.translate(0.5F, 0.5F, 0.5F);

        if (transformType == ItemTransforms.TransformType.GUI) {
            ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(
                    new ModelResourceLocation(DannysExpansion.ID + ":" + stack.getItem().getRegistryName().getPath() + "_gui", "inventory")
            );
            Minecraft.getInstance().getItemRenderer().render(stack, transformType, false, poseStack, buffer, combinedLight, combinedOverlay, ibakedmodel);
            RenderUtils.renderItemModelIntoGUI(stack, combinedLight, ibakedmodel);
        } else {
            Minecraft.getInstance().getItemRenderer().render(stack, transformType, flag, poseStack, buffer, combinedLight, combinedOverlay, ibakedmodel);
        }

        poseStack.popPose();
    }
}

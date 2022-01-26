package net.bottomtextdanny.danny_expannny.rendering.ister;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.bottomtextdanny.danny_expannny.rendering.RenderUtils;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.GunModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;

public class GunISR extends BlockEntityWithoutLevelRenderer  {

    public GunISR() {
        super(null, null);
    }
    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {



        if (transformType != ItemTransforms.TransformType.GUI) {
            if (stack.getItem() instanceof GunItem<?> gunItem) {
                GunModelRenderer renderer = DannysExpansion.client().getGunRenderData().getRenderer(gunItem);

                renderer.render(
                        ClientInstance.player(),
                        gunItem,
                        stack,
                        transformType,
                        matrixStack,
                        buffer,
                        combinedLight,
                        combinedOverlay);
            }
        } else {
            BakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(DannysExpansion.ID, stack.getItem().getRegistryName().getPath() + "_gui", "inventory"));

            RenderUtils.renderItemModelIntoGUI(stack, combinedLight, ibakedmodel);
        }
    }
}

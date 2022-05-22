package bottomtextdanny.dannys_expansion.content._client.rendering.ister;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.GunModelRenderer;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.dannys_expansion._util._client.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import bottomtextdanny.braincell.mixin_support.ItemStackClientExtensor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunISR extends BlockEntityWithoutLevelRenderer  {

    public GunISR() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();

        if (transformType != ItemTransforms.TransformType.GUI) {
            if (item instanceof GunItem<?> gunItem) {
                GunModelRenderer renderer = DannysExpansion.client().getGunRenderData().getRenderer(gunItem);

                LivingEntity holder = ((ItemStackClientExtensor)(Object)stack).getCachedHolder();

                AbstractClientPlayer player = holder instanceof AbstractClientPlayer pl ? pl : null;

                matrixStack.pushPose();
                renderer.render(
                        player,
                        gunItem,
                        stack,
                        transformType,
                        matrixStack,
                        buffer,
                        combinedLight,
                        combinedOverlay);
                matrixStack.popPose();
            }
        } else {
            BakedModel ibakedmodel = Minecraft.getInstance()
                    .getItemRenderer()
                    .getItemModelShaper()
                    .getModelManager()
                    .getModel(new ModelResourceLocation(DannysExpansion.ID, item.getRegistryName().getPath() + "_gui", "inventory"));

            RenderUtils.renderItemModelIntoGUI(stack, combinedLight, ibakedmodel);
        }
    }
}

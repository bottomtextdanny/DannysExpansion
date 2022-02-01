package net.bottomtextdanny.dannys_expansion.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IItemEntityExt;
import net.bottomtextdanny.dannys_expansion.core.base.item.ExtraModelProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    ItemEntity de_currentEntity;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"), method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", remap = false)
    private void renderItem(ItemRenderer instance, ItemStack stack, ItemTransforms.TransformType transform, boolean left, PoseStack pose, MultiBufferSource vertexconsumer, int light, int overlay, BakedModel model) {
        label: {
            if (this.de_currentEntity instanceof IItemEntityExt) {
                int customModelKey = ((IItemEntityExt) this.de_currentEntity).de_getShowingModel();
                if (customModelKey != -1 && stack.getItem() instanceof ExtraModelProvider provider) {
                    BakedModel customModel = provider.fetchModel(instance, customModelKey);

                    if (customModel != null) {
                        instance.render(stack, transform, left, pose, vertexconsumer, light, overlay, customModel);
                        break label;
                    }
                }
            }
            instance.render(stack, transform, left, pose, vertexconsumer, light, overlay, model);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", remap = false)
    private void renderHead(ItemEntity f8, float f9, float f11, PoseStack f13, MultiBufferSource f10, int f12, CallbackInfo ci) {
        this.de_currentEntity = f8;
    }
}

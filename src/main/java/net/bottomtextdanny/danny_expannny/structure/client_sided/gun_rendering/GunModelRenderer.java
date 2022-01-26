package net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface GunModelRenderer {

    void render(AbstractClientPlayer player, GunItem<?> gun, ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay);
}

package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Field;

public class SWBoltRenderer extends DannyArrowRenderer<AbstractArrow> {

    public SWBoltRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        String name = null;

        try {
            Field boltentity$arrowStack = entity.getClass().getDeclaredField("boltStack");
            boltentity$arrowStack.setAccessible(true);
            ResourceLocation texturePath = ((ItemStack) boltentity$arrowStack.get(entity)).getItem().getRegistryName();

            if (texturePath != null) {
                name = decode(texturePath.getPath());
            }
        } catch (Exception ignored) {
        }

        if (name == null) {
            name = "sw/bolt.png";
        }

        return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/" + name);
    }

    public static String decode(String spTexturePath) {
        int tippedPos = spTexturePath.indexOf("_tipped");
        if (tippedPos != -1) {
            spTexturePath = spTexturePath.substring(0, tippedPos) + spTexturePath.substring(tippedPos + 7);
        }

        return "sw/" + spTexturePath + ".png";
    }
}

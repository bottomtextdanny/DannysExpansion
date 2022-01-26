package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.sw;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Field;

public class SWArrowRenderer extends DannyArrowRenderer<AbstractArrow> {

    public SWArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        String name = null;

        try {
            Field arrowbaseentity$arrowStack = entity.getClass().getDeclaredField("arrowStack");
            arrowbaseentity$arrowStack.setAccessible(true);
            ResourceLocation texturePath = ((ItemStack) arrowbaseentity$arrowStack.get(entity)).getItem().getRegistryName();

            if (texturePath != null) {
                name = decode(texturePath.getPath());
            }
        } catch (Exception ignored) {
        }

        if (name == null) {
            name = "arrow.png";
        }

        return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/" + name);

    }

    public static String decode(String spTexturePath) {
        int tippedPos = spTexturePath.indexOf("_tipped");
        if (tippedPos != -1) {
            return "sw/" + spTexturePath.substring(0, tippedPos) + ".png";
        }

        return "sw/" + spTexturePath + ".png";
    }
}

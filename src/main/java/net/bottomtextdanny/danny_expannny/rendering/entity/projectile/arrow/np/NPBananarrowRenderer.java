package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.np;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.vanilla.VanillaArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class NPBananarrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/np/bananarrow.png");

    public NPBananarrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        try {
            boolean bananarrow$impacted = entity.getClass().getDeclaredField("impacted").getBoolean(entity);
            return bananarrow$impacted ? VanillaArrowRenderer.TEXTURES : TEXTURES;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TEXTURES;
    }
}

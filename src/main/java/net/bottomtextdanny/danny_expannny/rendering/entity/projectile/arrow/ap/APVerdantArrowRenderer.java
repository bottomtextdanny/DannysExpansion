package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.ap;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class APVerdantArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/ap/verdant_arrow.png");

    public APVerdantArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        return TEXTURES;
    }
}
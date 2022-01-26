package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.vanilla;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class VanillaSpectralArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/nothing.png");
    private static final ResourceLocation FULLBRIGHT_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/spectral_arrow_fullbright.png");

    public VanillaSpectralArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        return TEXTURES;
    }

    @Override
    public ResourceLocation getFullbrightTexture(AbstractArrow entity) {
        return FULLBRIGHT_TEXTURES;
    }
}

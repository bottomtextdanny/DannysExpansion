package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.iaf;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class IAFDragonArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/iaf/dragon_arrow.png");

    public IAFDragonArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        return TEXTURES;
    }

    @Override
    public float rescale() {
        return 1.2F;
    }
}

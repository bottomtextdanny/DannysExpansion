package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.iaf;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class IAFHydraArrowRenderer extends DannyArrowRenderer<AbstractArrow> {

    public IAFHydraArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/iaf/hydra_arrow.png");
    }
}

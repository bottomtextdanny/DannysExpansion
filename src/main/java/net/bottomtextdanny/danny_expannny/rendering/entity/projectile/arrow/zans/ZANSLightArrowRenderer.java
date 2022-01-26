package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.zans;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow.DannyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class ZANSLightArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/nothing.png");
    private static final ResourceLocation FULLBRIGHT_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/zans/light_arrow_fullbright.png");


    public ZANSLightArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
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

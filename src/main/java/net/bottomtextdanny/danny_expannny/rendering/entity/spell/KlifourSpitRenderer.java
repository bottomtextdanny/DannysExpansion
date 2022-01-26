package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.rendering.entity.ImageRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.KlifourSpitEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KlifourSpitRenderer extends ImageRenderer<KlifourSpitEntity> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/klifour_spit.png");

    public KlifourSpitRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public KlifourSpitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        setDimensions(8, 8);
    }

    @Override
    public ResourceLocation getTextureLocation(KlifourSpitEntity entity) {
        return TEXTURES;
    }
}

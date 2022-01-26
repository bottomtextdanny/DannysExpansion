package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.SnaithModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SnaithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnaithRenderer extends LivingEntityRenderer<SnaithEntity, SnaithModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/snode/snaith.png");

    public SnaithRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SnaithRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SnaithModel(), 0.5F);
    }

    public ResourceLocation getTextureLocation(SnaithEntity entity) {
        return TEXTURES;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.DarialynModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DarialynEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DarialynRenderer extends MobRenderer<DarialynEntity, DarialynModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/darialyn.png");

    public DarialynRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public DarialynRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DarialynModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(DarialynEntity entity) {
        return TEXTURES;
    }
}

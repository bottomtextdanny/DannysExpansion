package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.FrozenGhoulModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FrozenGhoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FrozenGhoulRenderer extends MobRenderer<FrozenGhoulEntity, FrozenGhoulModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/frozen_ghoul.png");

    public FrozenGhoulRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public FrozenGhoulRenderer(EntityRendererProvider.Context manager) {
        super(manager, new FrozenGhoulModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(FrozenGhoulEntity entity) {
        return TEXTURES;
    }
}

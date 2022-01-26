package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.SporeWightModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporeWightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SporeWightRenderer extends MobRenderer<SporeWightEntity, SporeWightModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/spore_wight.png");

    public SporeWightRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SporeWightRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SporeWightModel(), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(SporeWightEntity entity) {
        return TEXTURES;
    }
}

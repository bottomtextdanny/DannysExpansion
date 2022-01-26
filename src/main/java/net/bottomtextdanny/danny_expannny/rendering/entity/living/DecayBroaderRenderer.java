package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.DecayBroaderModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DecayBroaderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DecayBroaderRenderer extends MobRenderer<DecayBroaderEntity, DecayBroaderModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/decay_broader.png");

    public DecayBroaderRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public DecayBroaderRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DecayBroaderModel(), 0.5F);
    }

    public ResourceLocation getTextureLocation(DecayBroaderEntity entity) {
        return TEXTURES;
    }
}

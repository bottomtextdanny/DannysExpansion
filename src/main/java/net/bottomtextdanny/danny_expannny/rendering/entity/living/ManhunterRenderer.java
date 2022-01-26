package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ManhunterModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.manhunter.ManhunterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ManhunterRenderer extends MobRenderer<ManhunterEntity, ManhunterModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/manhunter/manhunter.png");

    public ManhunterRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public ManhunterRenderer(EntityRendererProvider.Context manager) {
        super(manager, new ManhunterModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(ManhunterEntity entity) {
        return TEXTURES;
    }
}

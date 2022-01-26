package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.PurpolioModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicMobLayerRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.PurpolioEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PurpolioRenderer extends MobRenderer<PurpolioEntity, PurpolioModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/purpolio/purpolio.png");
    public static final ResourceLocation TEXTURE_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/purpolio/purpolio_eyes.png");

    public PurpolioRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public PurpolioRenderer(EntityRendererProvider.Context manager) {
        super(manager, new PurpolioModel(), 0.5F);
        addLayer(new BasicMobLayerRenderer<>(this, e -> RenderType.eyes(TEXTURE_EYES)));
    }

    public ResourceLocation getTextureLocation(PurpolioEntity entity) {
        return TEXTURES;
    }
}

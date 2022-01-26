package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.SporerModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicMobLayerRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SporerRenderer extends MobRenderer<SporerEntity, SporerModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/sporer/sporer.png");
    public static final ResourceLocation TEXTURES_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/sporer/sporer_bright_layer.png");

    public SporerRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SporerRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SporerModel(), 0.4F);
        addLayer(new BasicMobLayerRenderer<>(this, e -> RenderType.eyes(TEXTURES_EYES)).bright());
    }

    public ResourceLocation getTextureLocation(SporerEntity entity) {
        return TEXTURES;
    }
}

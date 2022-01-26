package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.NyctoidModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicMobLayerRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.NyctoidEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NyctoidRenderer extends MobRenderer<NyctoidEntity, NyctoidModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/nyctoid/nyctoid.png");
    public static final ResourceLocation TEXTURES_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/nyctoid/nyctoid_eyes.png");

    public NyctoidRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public NyctoidRenderer(EntityRendererProvider.Context manager) {
        super(manager, new NyctoidModel(), 0.4F);
        addLayer(new BasicMobLayerRenderer<>(this, e -> RenderType.eyes(TEXTURES_EYES)).bright());
    }

    public ResourceLocation getTextureLocation(NyctoidEntity entity) {
        return TEXTURES;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.MummyModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicMobLayerRenderer;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.MummyForeheadItemLayer;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MummyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MummyRenderer extends MobRenderer<MummyEntity, MummyModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/mummy/mummy.png");
    public static final ResourceLocation TEXTURES_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/mummy/mummy_eyes.png");

    public MummyRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public MummyRenderer(EntityRendererProvider.Context manager) {
        super(manager, new MummyModel(), 0.4F);
        addLayer(new BasicMobLayerRenderer<>(this, e -> RenderType.eyes(TEXTURES_EYES)).bright());

        addLayer(new MummyForeheadItemLayer(this));
    }

    public ResourceLocation getTextureLocation(MummyEntity entity) {
        return TEXTURES;
    }

}
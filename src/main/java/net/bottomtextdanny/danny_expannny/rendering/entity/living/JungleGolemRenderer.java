package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.JungleGolemModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicMobLayerRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.JungleGolemEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JungleGolemRenderer extends MobRenderer<JungleGolemEntity, JungleGolemModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/jungle_golem/jungle_golem.png");
    public static final ResourceLocation TEXTURES_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/jungle_golem/jungle_golem_eyes.png");

    public JungleGolemRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public JungleGolemRenderer(EntityRendererProvider.Context manager) {
        super(manager, new JungleGolemModel(), 0.4F);
        addLayer(new BasicMobLayerRenderer<>(this, e -> RenderType.eyes(TEXTURES_EYES)).bright());
    }

    public ResourceLocation getTextureLocation(JungleGolemEntity entity) {
        return TEXTURES;
    }
}

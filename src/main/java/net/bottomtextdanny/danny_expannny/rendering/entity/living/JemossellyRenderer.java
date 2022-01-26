package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.JemossellyModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.JemossellyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JemossellyRenderer extends MobRenderer<JemossellyEntity, JemossellyModel> {
    public static final String TEXTURES = "textures/entity/jemosselly/jemosselly_";

    public JemossellyRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public JemossellyRenderer(EntityRendererProvider.Context manager) {
        super(manager, new JemossellyModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(JemossellyEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, TEXTURES + entity.tickCount / 2 % 15 + ".png");
    }
}

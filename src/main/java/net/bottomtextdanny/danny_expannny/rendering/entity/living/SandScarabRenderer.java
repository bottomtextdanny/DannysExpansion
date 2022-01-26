package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.SandScarabModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SandScarabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SandScarabRenderer extends MobRenderer<SandScarabEntity, SandScarabModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/sand_scarab.png");

    public SandScarabRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SandScarabRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SandScarabModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(SandScarabEntity entity) {
        return TEXTURES;
    }

    @Override
    protected float getFlipDegrees(SandScarabEntity entityLivingBaseIn) {
        return 0;
    }


}

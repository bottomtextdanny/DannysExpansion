package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.AridAbominationModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.AridAbominationEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AridAbominationRenderer extends MobRenderer<AridAbominationEntity, AridAbominationModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arid_abomination.png");

    public AridAbominationRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public AridAbominationRenderer(EntityRendererProvider.Context manager) {
        super(manager, new AridAbominationModel(), 0.4F);
    }
	
	public ResourceLocation getTextureLocation(AridAbominationEntity entity) {
        return TEXTURES;
    }

    @Override
    protected float getFlipDegrees(AridAbominationEntity entityLivingBaseIn) {
        return 0;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.FoamieModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.FoamieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FoamieRenderer extends MobRenderer<FoamieEntity, FoamieModel> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/foamie.png");

	public FoamieRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public FoamieRenderer(EntityRendererProvider.Context manager) {
		super(manager, new FoamieModel(), 0.5F);
	}
	
	public ResourceLocation getTextureLocation(FoamieEntity entity) {
		return TEXTURES;
	}
}

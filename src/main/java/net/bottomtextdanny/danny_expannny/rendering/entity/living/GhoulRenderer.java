package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls.GhoulModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.GhoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GhoulRenderer extends VariantRenderer<GhoulEntity, GhoulModel> {
    public static final ResourceLocation DEFAULT_TEXTURE_PATH = new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/ghoul.png");
	public static GhoulModel COMMON_MODEL = new GhoulModel();

	public GhoulRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

    public GhoulRenderer(EntityRendererProvider.Context manager) {
        super(manager, COMMON_MODEL, 0.4F);
    }
	
	@Override
	public ResourceLocation getDefaultEntityTexture(GhoulEntity entity) {
		return DEFAULT_TEXTURE_PATH;
	}
}

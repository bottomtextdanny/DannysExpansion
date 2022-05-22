package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.SquigModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.squig.Squig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SquigRenderer extends MobVariantRenderer<Squig, SquigModel> {
    private static final ResourceLocation DEFAULT_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/freedom_squig.png");
    public static final SquigModel MODEL = new SquigModel();

    public SquigRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SquigRenderer(EntityRendererProvider.Context manager) {
        super(manager, new SquigModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(Squig entity) {
        return super.getTextureLocation(entity);
    }

    @Override
    public ResourceLocation getDefaultEntityTexture(Squig entity) {
        return DEFAULT_TEXTURES;
    }
}

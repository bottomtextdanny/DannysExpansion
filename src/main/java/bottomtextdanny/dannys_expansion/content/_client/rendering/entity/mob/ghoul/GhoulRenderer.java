package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.ghoul;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.ghoul.GhoulModel;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.MobVariantRenderer;
import bottomtextdanny.dannys_expansion.content.entities.mob.ghoul.Ghoul;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GhoulRenderer extends MobVariantRenderer<Ghoul, GhoulModel> {
    public static final ResourceLocation GHOUL_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/ghoul.png");
    public static final ResourceLocation FROZEN_GHOUL_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/frozen_ghoul.png");
    public static final GhoulModel MODEL = new GhoulModel();

    public GhoulRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public GhoulRenderer(EntityRendererProvider.Context manager) {
        super(manager, new GhoulModel(), 0.25F);
    }

    @Override
    public ResourceLocation getDefaultEntityTexture(Ghoul entity) {
        return FROZEN_GHOUL_TEXTURES;
    }
}

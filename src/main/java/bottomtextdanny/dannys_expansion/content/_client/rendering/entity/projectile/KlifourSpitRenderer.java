package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.ImageRenderer;
import bottomtextdanny.dannys_expansion.content.entities.projectile.KlifourSpit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KlifourSpitRenderer extends ImageRenderer<KlifourSpit> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/klifour_spit.png");

    public KlifourSpitRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public KlifourSpitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        setDimensions(8, 8);
    }

    @Override
    public ResourceLocation getTextureLocation(KlifourSpit entity) {
        return TEXTURES;
    }
}

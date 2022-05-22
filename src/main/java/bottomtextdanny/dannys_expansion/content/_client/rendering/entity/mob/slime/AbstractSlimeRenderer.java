package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.DannySlimeModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.AbstractSlime;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSlimeRenderer <E extends AbstractSlime> extends MobRenderer<E, DannySlimeModel<E>> {
    public static final ResourceLocation TEXTURES =
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/slime.png");

    public AbstractSlimeRenderer(EntityRendererProvider.Context renderManagerIn, DannySlimeModel<E> entityModelIn, float shadowSizeIn) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);
    }

    @Override
    protected float getFlipDegrees(E entityLivingBaseIn) {
        return 0;
    }

    public ResourceLocation getTextureLocation(AbstractSlime entity) {
        return TEXTURES;
    }
}

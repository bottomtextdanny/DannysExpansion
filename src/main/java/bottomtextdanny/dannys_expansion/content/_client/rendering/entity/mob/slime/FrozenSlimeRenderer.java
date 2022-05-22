package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.DannySlimeModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.FrozenSlime;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class FrozenSlimeRenderer extends AbstractSlimeRenderer<FrozenSlime> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/frozen_slime.png");

    public FrozenSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public FrozenSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(12, 9), 0.4F);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(FrozenSlime entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(TEXTURES);
    }

    public ResourceLocation getTextureLocation(FrozenSlime entity) {
        return TEXTURES;
    }

}

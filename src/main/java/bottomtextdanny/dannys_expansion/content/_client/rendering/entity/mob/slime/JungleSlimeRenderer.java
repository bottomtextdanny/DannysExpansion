package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.DannySlimeModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.JungleSlime;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class JungleSlimeRenderer extends AbstractSlimeRenderer<JungleSlime> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/jungle_slime.png");

    public JungleSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public JungleSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(15.0F, 13.0F), 0.4F);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(JungleSlime entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(TEXTURES);
    }

    public ResourceLocation getTextureLocation(JungleSlime entity) {
        return TEXTURES;
    }


}

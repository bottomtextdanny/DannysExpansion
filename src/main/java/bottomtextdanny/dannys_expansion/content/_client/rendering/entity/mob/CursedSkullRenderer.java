package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.CursedSkullModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.cursed_skull.CursedSkull;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CursedSkullRenderer extends MobRenderer<CursedSkull, CursedSkullModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/cursed_skull/cursed_skull.png");

    public CursedSkullRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public CursedSkullRenderer(EntityRendererProvider.Context manager) {
        super(manager, new CursedSkullModel(), 0.0F);
    }

    public ResourceLocation getTextureLocation(CursedSkull entity) {
        return TEXTURES;
    }
}

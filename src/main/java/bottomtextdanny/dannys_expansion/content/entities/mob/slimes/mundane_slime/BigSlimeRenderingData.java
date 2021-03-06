package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime.MundaneSlimeRenderer;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BigSlimeRenderingData implements VariantRenderingData<MundaneSlime> {
    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/big_green_slime.png"),
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/big_red_slime.png"),
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/big_blue_slime.png"),
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/big_yellow_slime.png")
    };

    @Override
    public EntityModel<MundaneSlime> getModel(MundaneSlime entity) {
        return MundaneSlimeRenderer.BIG_MODEL;
    }

    @Override
    public ResourceLocation getTexture(MundaneSlime entity) {
        return TEXTURES[entity.colorVariant.get()];
    }
}

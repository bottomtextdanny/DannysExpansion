package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime.MundaneSlimeRenderer;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MundaneSlimeOldForm extends MundaneSlimeForm {

    public MundaneSlimeOldForm() {
        super();
    }

    @Override
    public GelItem.Model gelModel(MundaneSlime entity) {
        return GelItem.Model.GREEN;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MundaneSlime> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/old_slime.png"),
                MundaneSlimeRenderer.COMMON_MODEL
        );
    }
}

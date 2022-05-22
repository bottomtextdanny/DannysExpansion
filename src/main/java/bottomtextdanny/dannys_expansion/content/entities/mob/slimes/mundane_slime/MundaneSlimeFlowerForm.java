package bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.FlowerSlimeModel;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MundaneSlimeFlowerForm extends MundaneSlimeForm {

    public MundaneSlimeFlowerForm() {
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
                new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/pink_flower_slime.png"),
                new FlowerSlimeModel()
        );
    }
}

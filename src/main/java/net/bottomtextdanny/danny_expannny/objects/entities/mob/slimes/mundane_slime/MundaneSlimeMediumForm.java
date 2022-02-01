package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime;

import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MundaneSlimeMediumForm extends MundaneSlimeForm {

    public MundaneSlimeMediumForm() {
        super();
    }

    @Override
    public GelItem.Model gelModel(MundaneSlime entity) {
        return MundaneSlime.COLORED_GEL_MODELS.get(entity.colorVariant.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MundaneSlime> createRenderingHandler() {
        return new MediumSlimeRenderingData();
    }
}

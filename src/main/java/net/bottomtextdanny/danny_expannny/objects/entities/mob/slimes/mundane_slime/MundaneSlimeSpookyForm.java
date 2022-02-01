package net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.SpookySlimeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MundaneSlimeSpookyForm extends MundaneSlimeForm {

    public MundaneSlimeSpookyForm() {
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
                new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/spooky_slime.png"),
                new SpookySlimeModel()
        );
    }
}

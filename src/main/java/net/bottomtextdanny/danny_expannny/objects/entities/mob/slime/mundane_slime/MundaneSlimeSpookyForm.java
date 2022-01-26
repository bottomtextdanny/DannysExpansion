package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.mundane_slime;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.SpookySlimeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MundaneSlimeSpookyForm extends Form<MundaneSlimeEntity> {

    public MundaneSlimeSpookyForm() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MundaneSlimeEntity> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/spooky_slime.png"),
                new SpookySlimeModel()
        );
    }
}

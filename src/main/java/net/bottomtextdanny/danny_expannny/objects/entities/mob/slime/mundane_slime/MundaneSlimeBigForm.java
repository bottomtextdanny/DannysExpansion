package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.mundane_slime;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MundaneSlimeBigForm extends Form<MundaneSlimeEntity> {

    public MundaneSlimeBigForm() {
        super();
    }

    @Nullable
    @Override
    public EntityDimensions boxSize() {
        return EntityDimensions.fixed(1.0F, 0.75F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MundaneSlimeEntity> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/big_slime.png"),
                new DannySlimeModel<>(16.0F, 13.0F)
        );
    }
}

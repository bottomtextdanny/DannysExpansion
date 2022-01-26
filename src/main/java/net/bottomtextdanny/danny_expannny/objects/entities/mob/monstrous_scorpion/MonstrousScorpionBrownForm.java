package net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.MonstrousScorpionRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonstrousScorpionBrownForm extends MonstrousScorpionForm {

    public MonstrousScorpionBrownForm() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MonstrousScorpion> createRenderingHandler() {
        return new MonstrousScorpionVariantRendering(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/monstrous_scorpion/brown_scorpion.png"),
                null,
                MonstrousScorpionRenderer.COMMON_MODEL
        );
    }
}

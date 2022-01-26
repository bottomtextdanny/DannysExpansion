package net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.MonstrousScorpionRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MonstrousScorpionBlackForm extends MonstrousScorpionForm {

    public MonstrousScorpionBlackForm() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MonstrousScorpion> createRenderingHandler() {
        return new MonstrousScorpionVariantRendering(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/monstrous_scorpion/black_scorpion.png"),
                new ResourceLocation(DannysExpansion.ID, "textures/entity/monstrous_scorpion/black_scorpion_eyes.png"),
                MonstrousScorpionRenderer.COMMON_MODEL
        );
    }
}

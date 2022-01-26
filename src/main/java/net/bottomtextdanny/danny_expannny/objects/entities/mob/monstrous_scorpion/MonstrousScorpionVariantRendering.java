package net.bottomtextdanny.danny_expannny.objects.entities.mob.monstrous_scorpion;

import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class MonstrousScorpionVariantRendering extends SimpleVariantRenderingData<MonstrousScorpion> {
    private final ResourceLocation eyeTexturePath;

    public MonstrousScorpionVariantRendering(ResourceLocation texturePath,
                                             ResourceLocation eyeTexturePath,
                                             EntityModel<MonstrousScorpion> model) {
        super(texturePath, model);
        this.eyeTexturePath = eyeTexturePath;
    }

    @Nullable
    public ResourceLocation getEyeTexturePath() {
        return this.eyeTexturePath;
    }
}

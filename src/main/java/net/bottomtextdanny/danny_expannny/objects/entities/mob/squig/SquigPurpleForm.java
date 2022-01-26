package net.bottomtextdanny.danny_expannny.objects.entities.mob.squig;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.particles.SquigCrossParticle;
import net.bottomtextdanny.danny_expannny.objects.particles.SquigRingParticle;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.SquigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SquigPurpleForm extends SquigForm {

    public SquigPurpleForm() {
        super();
    }

    @Override
    public int getCrossSprite() {
        return SquigCrossParticle.PURPLE_IDX;
    }

    @Override
    public int getRingSprite() {
        return SquigRingParticle.PURPLE_IDX;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<SquigEntity> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/luxury_squig.png"),
                SquigRenderer.COMMON_MODEL
        );
    }
}

package bottomtextdanny.dannys_expansion.content.entities.mob._pending.squig;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigCrossParticle;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigRingParticle;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.SquigRendererDeprecated;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SquigGreenForm extends SquigForm {

    public SquigGreenForm() {
        super();
    }

    @Override
    public int getCrossSprite() {
        return SquigCrossParticle.GREEN_IDX;
    }

    @Override
    public int getRingSprite() {
        return SquigRingParticle.GREEN_IDX;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<SquigDeprecated> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/harmony_squig.png"),
                SquigRendererDeprecated.COMMON_MODEL
        );
    }
}
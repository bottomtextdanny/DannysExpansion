package bottomtextdanny.dannys_expansion.content.entities.mob._pending.squig;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigCrossParticle;
import bottomtextdanny.dannys_expansion.content._client.particles.SquigRingParticle;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.SquigRendererDeprecated;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.SimpleVariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SquigMelanisticForm extends SquigForm {

    public SquigMelanisticForm() {
        super();
    }

    @Override
    public int getCrossSprite() {
        return SquigCrossParticle.BLACK_IDX;
    }

    @Override
    public int getRingSprite() {
        return SquigRingParticle.BLACK_IDX;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<SquigDeprecated> createRenderingHandler() {
        return new SimpleVariantRenderingData<>(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/squig/authority_squig.png"),
                SquigRendererDeprecated.COMMON_MODEL
        );
    }
}

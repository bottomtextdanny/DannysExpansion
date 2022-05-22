package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.MonstrousScorpionRenderer;
import bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonstrousScorpionBrownForm extends MonstrousScorpionForm {

    public MonstrousScorpionBrownForm() {
        super();
    }

    @Override
    public ScorpionGlandItem.Model gland() {
        return ScorpionGlandItem.Model.BROWN;
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

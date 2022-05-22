package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.MonstrousScorpionRenderer;
import bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MonstrousScorpionBlackForm extends MonstrousScorpionForm {

    public MonstrousScorpionBlackForm() {
        super();
    }

    @Override
    public ScorpionGlandItem.Model gland() {
        return ScorpionGlandItem.Model.BLACK;
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

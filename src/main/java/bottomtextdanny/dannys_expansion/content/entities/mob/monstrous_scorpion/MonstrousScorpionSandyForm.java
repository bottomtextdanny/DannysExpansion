package bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.MonstrousScorpionRenderer;
import bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem;
import bottomtextdanny.braincell.mod._mod.client_sided.variant_data.VariantRenderingData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonstrousScorpionSandyForm extends MonstrousScorpionForm {

    public MonstrousScorpionSandyForm() {
        super();
    }

    @Override
    public ScorpionGlandItem.Model gland() {
        return ScorpionGlandItem.Model.SAND;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected VariantRenderingData<MonstrousScorpion> createRenderingHandler() {
        return new MonstrousScorpionVariantRendering(
                new ResourceLocation(DannysExpansion.ID, "textures/entity/monstrous_scorpion/sand_scorpion.png"),
                null,
                MonstrousScorpionRenderer.COMMON_MODEL
        );
    }
}

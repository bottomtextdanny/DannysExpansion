package bottomtextdanny.dannys_expansion.content.items.material;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.items.MaterialItem;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraModelData;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraModelProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;

import static bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem.Model.*;

public class ScorpionGlandItem extends MaterialItem implements ExtraModelProvider {

    public ScorpionGlandItem(Properties properties) {
        super(properties);
        fetchFrom(
                ExtraModelData.of(BROWN, new ModelResourceLocation(DannysExpansion.ID, "brown_scorpion_gland", "inventory")),
                ExtraModelData.of(BLACK, new ModelResourceLocation(DannysExpansion.ID, "black_scorpion_gland", "inventory")),
                ExtraModelData.of(SAND, new ModelResourceLocation(DannysExpansion.ID, "sand_scorpion_gland", "inventory"))
        );
    }

    public enum Model {
        BROWN,
        BLACK,
        SAND
    }
}

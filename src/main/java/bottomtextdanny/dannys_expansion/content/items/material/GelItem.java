package bottomtextdanny.dannys_expansion.content.items.material;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.items.MaterialItem;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraModelData;
import bottomtextdanny.braincell.mod._base.registry.item_extensions.ExtraModelProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;

import static bottomtextdanny.dannys_expansion.content.items.material.GelItem.Model.*;

public class GelItem extends MaterialItem implements ExtraModelProvider {

    public GelItem(Properties properties) {
        super(properties);
        fetchFrom(
                ExtraModelData.of(GREEN, new ModelResourceLocation(DannysExpansion.ID, "green_gel", "inventory")),
                ExtraModelData.of(BLUE, new ModelResourceLocation(DannysExpansion.ID, "blue_gel", "inventory")),
                ExtraModelData.of(YELLOW, new ModelResourceLocation(DannysExpansion.ID, "yellow_gel", "inventory")),
                ExtraModelData.of(RED, new ModelResourceLocation(DannysExpansion.ID, "red_gel", "inventory")),
                ExtraModelData.of(MAGMA, new ModelResourceLocation(DannysExpansion.ID, "magma_gel", "inventory")),
                ExtraModelData.of(FROZEN, new ModelResourceLocation(DannysExpansion.ID, "ice_gel", "inventory")),
	            ExtraModelData.of(DESERTIC, new ModelResourceLocation(DannysExpansion.ID, "desertic_gel", "inventory")),
	            ExtraModelData.of(MUMMY, new ModelResourceLocation(DannysExpansion.ID, "mummy_gel", "inventory"))
        );
    }

    public enum Model {
        GREEN,
        YELLOW,
        BLUE,
        RED,
        MAGMA,
        FROZEN,
        DESERTIC,
        MUMMY,
    }
}

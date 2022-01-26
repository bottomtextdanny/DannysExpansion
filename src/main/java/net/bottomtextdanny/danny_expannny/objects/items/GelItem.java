package net.bottomtextdanny.danny_expannny.objects.items;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.core.base.item.IExtraModelProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

public class GelItem extends MaterialItem implements IExtraModelProvider {
    public static int MAGMA_MODEL = 0, ICE_MODEL = 1, DESERTIC_MODEL = 2, MUMMY_MODEL = 3;

    public GelItem(Properties properties) {
        super(properties);
        fetchFrom(
                Pair.of(MAGMA_MODEL, new ModelResourceLocation(DannysExpansion.ID, "magma_gel", "inventory")),
                Pair.of(ICE_MODEL, new ModelResourceLocation(DannysExpansion.ID, "ice_gel", "inventory")),
	            Pair.of(DESERTIC_MODEL, new ModelResourceLocation(DannysExpansion.ID, "desertic_gel", "inventory")),
	            Pair.of(MUMMY_MODEL, new ModelResourceLocation(DannysExpansion.ID, "mummy_gel", "inventory"))
        );
    }
}

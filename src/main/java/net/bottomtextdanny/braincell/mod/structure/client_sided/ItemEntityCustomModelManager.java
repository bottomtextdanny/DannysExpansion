package net.bottomtextdanny.braincell.mod.structure.client_sided;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemEntityCustomModelManager {
    private final Map<Item, Int2ObjectArrayMap<ModelResourceLocation>> modelMap = Maps.newIdentityHashMap();

}

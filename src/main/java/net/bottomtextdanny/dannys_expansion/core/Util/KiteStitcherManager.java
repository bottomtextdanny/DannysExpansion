package net.bottomtextdanny.dannys_expansion.core.Util;

import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.dannys_expansion.core.Util.mixin.IEntityTypeExt;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KiteStitcherManager {
    private static final Set<SpecialKiteItem> DEFERRED_STITCHING = new HashSet<>();
    private static final Map<ResourceLocation, SpecialKiteItem> KITE_MAP_BY_STRING = new HashMap<>();

    public static Map<ResourceLocation, SpecialKiteItem> getKiteMapByString() {
        return KITE_MAP_BY_STRING;
    }

    public static Set<SpecialKiteItem> getDeferredStitching() {
        return DEFERRED_STITCHING;
    }

    public static void stitch() {
        DEFERRED_STITCHING.forEach(k -> {
            if (k.getCachedEntity() != null) ((IEntityTypeExt)k.getCachedEntity().get()).DE_setFixedKite(k);
        });
        KITE_MAP_BY_STRING.forEach((s, k) -> {
            EntityType<?> type = Registry.ENTITY_TYPE.get(s);
            if (type != null) {
                ((IEntityTypeExt) type).DE_setFixedKite(k);
            }
        });

        DEFERRED_STITCHING.clear();
        KITE_MAP_BY_STRING.clear();
    }
}

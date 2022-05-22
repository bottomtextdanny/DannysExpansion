package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.accessories.*;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryFactory;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.player.accessory.IAccessory;
import net.minecraft.resources.ResourceLocation;

public final class DEAccessoryKeys {
    public static final AccessoryKey<CoreAccessory> CORE_EMPTY = create("core_empty", (player, factory) -> CoreAccessory.EMPTY);
    public static final AccessoryKey<BurbleAccessory> BURBLE = create("burble", BurbleAccessory::new);
    public static final AccessoryKey<EbbewelAccessory> EBBEWEL = create("ebbewel", EbbewelAccessory::new);
    public static final AccessoryKey<EclipseAccessory> ECLIPSE = create("eclipse", EclipseAccessory::new);
    public static final AccessoryKey<GelatinousDiplomacyAccessory> GELATINOUS_DIPLOMACY = create("gelatinous_diplomacy", GelatinousDiplomacyAccessory::new);
    public static final AccessoryKey<KlifourTalismanAccessory> KLIFOUR_TALISMAN = create("klifour_talisman", KlifourTalismanAccessory::new);
    public static final AccessoryKey<PiglinMedalAccessory> PIGLIN_MEDAL = create("piglin_medal", PiglinMedalAccessory::new);
    public static final AccessoryKey<SandyClothesAccessory> SANDY_CLOTHES = create("sandy_clothes", SandyClothesAccessory::new);
    public static final AccessoryKey<PiercedDollAccessory> PIERCED_DOLL = create("pierced_doll", PiercedDollAccessory::new);
    public static final AccessoryKey<BrieyeAccessory> BRIEYE = create("brieye", BrieyeAccessory::new);
    public static final AccessoryKey<ReefCollarAccessory> REEF_COLLAR = create("reef_collar", ReefCollarAccessory::new);

    public static <E extends IAccessory> AccessoryKey<E> create(String name, AccessoryFactory<E> factory) {
        return AccessoryKey.createKey(new ResourceLocation(DannysExpansion.ID, name), factory);
    }

    public static void loadClass() {}
}

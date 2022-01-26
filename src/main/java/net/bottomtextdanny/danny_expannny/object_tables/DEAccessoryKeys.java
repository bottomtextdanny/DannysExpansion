package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.accessory.*;
import net.bottomtextdanny.danny_expannny.objects.accessories.*;
import net.minecraft.resources.ResourceLocation;

public final class DEAccessoryKeys {
    public static final AccessoryKey<IAccessory.EmptyAccessory> EMPTY = create("empty", (player, factory) -> new IAccessory.EmptyAccessory());
    public static final AccessoryKey<CoreAccessory> CORE_EMPTY = create("core_empty", (player, factory) -> CoreAccessory.EMPTY);
    public static final AccessoryKey<StackAccessory> STACK_EMPTY = create("core_empty", (player, factory) -> StackAccessory.EMPTY);
    public static final AccessoryKey<AntitoxinAccessory> ANTITOXIN = create("antitoxin", AntitoxinAccessory::new);
    public static final AccessoryKey<AyerstoneAccessory> AYERSTONE = create("ayerstone", AyerstoneAccessory::new);
    public static final AccessoryKey<BurbleAccessory> BURBLE = create("burble", BurbleAccessory::new);
    public static final AccessoryKey<EbbewelAccessory> EBBEWEL = create("ebbewel", EbbewelAccessory::new);
    public static final AccessoryKey<EclipseAccessory> ECLIPSE = create("eclipse", EclipseAccessory::new);
    public static final AccessoryKey<EndBalloonAccessory> END_BALLOON = create("end_balloon", EndBalloonAccessory::new);
    public static final AccessoryKey<GelatinousDiplomacyAccessory> GELATINOUS_DIPLOMACY = create("gelatinous_diplomacy", GelatinousDiplomacyAccessory::new);
    public static final AccessoryKey<KlifourTalismanAccessory> KLIFOUR_TALISMAN = create("klifour_talisman", KlifourTalismanAccessory::new);
    public static final AccessoryKey<PiglinMedalAccessory> PIGLIN_MEDAL = create("piglin_medal", PiglinMedalAccessory::new);
    public static final AccessoryKey<SandNecklaceAccessory> SAND_NECKLACE = create("sand_necklace", SandNecklaceAccessory::new);

    public static <E extends IAccessory> AccessoryKey<E> create(String name, AccessoryFactory<E> factory) {
        return AccessoryKey.createKey(new ResourceLocation(DannysExpansion.ID, name), factory);
    }

    public static void loadClass() {}
}

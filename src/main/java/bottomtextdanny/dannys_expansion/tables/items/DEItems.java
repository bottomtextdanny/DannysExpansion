package bottomtextdanny.dannys_expansion.tables.items;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.items.arrow.VenomArrowItem;
import bottomtextdanny.dannys_expansion.content.items.arrow.WoodenArrowItem;
import bottomtextdanny.dannys_expansion.content.items.gun.*;
import bottomtextdanny.dannys_expansion.content.items.material.ScorpionGlandItem;
import bottomtextdanny.dannys_expansion.tables.DEAccessoryKeys;
import bottomtextdanny.dannys_expansion.content.items.AccessoryItem;
import bottomtextdanny.dannys_expansion.content.items.material.GelItem;
import bottomtextdanny.dannys_expansion.content.items.MaterialItem;
import bottomtextdanny.dannys_expansion.content.items.arrow.IceArrowItem;
import bottomtextdanny.dannys_expansion.content.items.bow.FrozenBowItem;
import bottomtextdanny.dannys_expansion.content.items.bullet.AquaticBulletItem;
import bottomtextdanny.dannys_expansion.content.items.bullet.BulletItem;
import bottomtextdanny.dannys_expansion.content.items.bullet.HighVelocityBulletItem;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public final class DEItems {
    public static final BCRegistry<Item> ENTRIES = new BCRegistry<>(true);
    public static final RegistryHelper<Item> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Wrap<Item> ICON = defer(DEItemCategory.NO, "icon", () -> new Item(new Item.Properties()));

    // TODO: 2/3/2021 REMOVE THIS!
    public static final Wrap<Item> HOLLOW_ARMOR_BLADE = defer(DEItemCategory.NO, "possessed_armor_blade", () ->
            new Item(new Item.Properties()));

    //materials
    public static final Wrap<MaterialItem> SCORPION_GLAND = defer(DEItemCategory.MATERIALS_N_FOOD, "scorpion_gland", () ->
            new ScorpionGlandItem(new Item.Properties()));
    public static final Wrap<MaterialItem> ICE_SHARD = defer(DEItemCategory.MATERIALS_N_FOOD, "ice_shard", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> STRANGE_ALLOY = defer(DEItemCategory.MATERIALS_N_FOOD, "strange_alloy", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> GOLEM_SCRAP = defer(DEItemCategory.MATERIALS_N_FOOD, "golem_scrap", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> GEL = defer(DEItemCategory.MATERIALS_N_FOOD, "gel", () ->
            new GelItem(new Item.Properties()));
    public static final Wrap<MaterialItem> LIFE_ESSENCE = defer(DEItemCategory.MATERIALS_N_FOOD, "life_essence", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> IMPULSOR = defer(DEItemCategory.MATERIALS_N_FOOD, "impulsor", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> VENOM_VIAL = defer(DEItemCategory.MATERIALS_N_FOOD, "venom_vial", () ->
            new GelItem(new Item.Properties()));

    //sword
//    public static final Wrap<EclipseItem> ECLIPSE = defer(DEItemCategory.SWORDS, "eclipse", () ->
//            new EclipseItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //bow
    public static final Wrap<FrozenBowItem> FROZEN_BOW = defer(DEItemCategory.BOWS, "frozen_bow", () ->
            new FrozenBowItem(new Item.Properties().durability(564).tab(DannysExpansion.TAB)));

	//gun
    public static final Wrap<HandgunItem> HANDGUN = defer(DEItemCategory.GUNS, "handgun", () ->
            new HandgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<MusketItem> MUSKET = defer(DEItemCategory.GUNS, "musket", () ->
            new MusketItem(new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<GolemHandgunItem> HEAVY_HANDGUN = defer(DEItemCategory.GUNS, "heavy_handgun", () ->
            new GolemHandgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<ShotgunItem> SHOTGUN = defer(DEItemCategory.GUNS, "shotgun", () ->
            new ShotgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<ShootingStarItem> SHOOTING_STAR = defer(DEItemCategory.GUNS, "shooting_star", () ->
            new ShootingStarItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //accessory
    public static final Wrap<AccessoryItem> GELATINOUS_DIPLOMACY = defer(DEItemCategory.ACCESSORIES, "gelatinous_diplomacy", () ->
            new AccessoryItem(DEAccessoryKeys.GELATINOUS_DIPLOMACY, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> KLIFOUR_TALISMAN = defer(DEItemCategory.ACCESSORIES, "klifour_talisman", () ->
            new AccessoryItem(DEAccessoryKeys.KLIFOUR_TALISMAN, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> SANDY_CLOTHES = defer(DEItemCategory.ACCESSORIES, "sandy_clothes", () ->
            new AccessoryItem(DEAccessoryKeys.SANDY_CLOTHES, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> PIERCED_DOLL = defer(DEItemCategory.ACCESSORIES, "pierced_doll", () ->
            new AccessoryItem(DEAccessoryKeys.PIERCED_DOLL, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> EBBEWEL = defer(DEItemCategory.ACCESSORIES, "ebbewel", () ->
            new AccessoryItem(DEAccessoryKeys.EBBEWEL, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> PIGLIN_MEDAL = defer(DEItemCategory.ACCESSORIES, "piglin_medal", () ->
            new AccessoryItem(DEAccessoryKeys.PIGLIN_MEDAL, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> BURBLE = defer(DEItemCategory.ACCESSORIES, "burble", () ->
            new AccessoryItem(DEAccessoryKeys.BURBLE, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> BRIEYE = defer(DEItemCategory.ACCESSORIES, "brieye", () ->
            new AccessoryItem(DEAccessoryKeys.BRIEYE, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> REEF_COLLAR = defer(DEItemCategory.ACCESSORIES, "reef_collar", () ->
            new AccessoryItem(DEAccessoryKeys.REEF_COLLAR, new Item.Properties().tab(DannysExpansion.TAB)));

    //arrow
    public static final Wrap<WoodenArrowItem> WOODEN_ARROW = defer(DEItemCategory.ARROWS, "wooden_arrow", () ->
            new WoodenArrowItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<IceArrowItem> ICE_ARROW = defer(DEItemCategory.ARROWS, "ice_arrow", () ->
            new IceArrowItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<VenomArrowItem> VENOM_ARROW = defer(DEItemCategory.ARROWS, "venom_arrow", () ->
            new VenomArrowItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //bullet
    public static final Wrap<BulletItem> BULLET = defer(DEItemCategory.AMMO, "bullet", () ->
            new BulletItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<HighVelocityBulletItem> HIGH_VELOCITY_BULLET = defer(DEItemCategory.AMMO, "high_velocity_bullet", () ->
            new HighVelocityBulletItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AquaticBulletItem> AQUATIC_BULLET = defer(DEItemCategory.AMMO, "aquatic_bullet", () ->
            new AquaticBulletItem(new Item.Properties().tab(DannysExpansion.TAB)));
	
    static {
        DEBuildingItems.call();
    }

    public static <U extends Item> Wrap<U> defer(DEItemCategory category, String name, Supplier<U> sup) {
        Wrap<U> wrapped = HELPER.defer(name, sup);
        Braincell.common().getItemSortData().setSortValue(wrapped.getKey(), (short)category.ordinal());
        return wrapped;
    }

    private static TagKey<Item> bindTag(String location) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(DannysExpansion.ID, location));
    }
}

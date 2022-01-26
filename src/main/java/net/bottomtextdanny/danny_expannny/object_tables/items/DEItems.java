package net.bottomtextdanny.danny_expannny.object_tables.items;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEAccessoryKeys;
import net.bottomtextdanny.danny_expannny.objects.items.BlueSlimeItem;
import net.bottomtextdanny.danny_expannny.objects.items.GelItem;
import net.bottomtextdanny.danny_expannny.objects.items.MaterialItem;
import net.bottomtextdanny.danny_expannny.objects.items.SporeBombItem;
import net.bottomtextdanny.danny_expannny.objects.items.gun.*;
import net.bottomtextdanny.danny_expannny.objects.items.AccessoryItem;
import net.bottomtextdanny.danny_expannny.objects.items.armor.AntiqueArmorItem;
import net.bottomtextdanny.danny_expannny.objects.items.arrow.IceArrowItem;
import net.bottomtextdanny.danny_expannny.objects.items.bow.EquinoxItem;
import net.bottomtextdanny.danny_expannny.objects.items.bow.FrozenBowItem;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.AquaticBullet;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.BulletItem;
import net.bottomtextdanny.danny_expannny.objects.items.bullet.HighVelocityBullet;
import net.bottomtextdanny.danny_expannny.objects.items.sword.EclipseItem;
import net.bottomtextdanny.danny_expannny.objects.items.sword.ScorpionSwordItem;
import net.bottomtextdanny.dannys_expansion.core.data.ModFoods;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static net.bottomtextdanny.danny_expannny.object_tables.items.DEItemCategory.*;

public final class DEItems {
    public static final BCRegistry<Item> ENTRIES = new BCRegistry<>(true);
    public static final RegistryHelper<Item> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Wrap<Item> ICON = defer(NO, "icon", () -> new Item(new Item.Properties()));

    // TODO: 2/3/2021 REMOVE THIS!
    public static final Wrap<Item> HOLLOW_ARMOR_BLADE = defer(NO, "possessed_armor_blade", () ->
            new Item(new Item.Properties()));
    public static final Wrap<Item> ENDER_BEAST_SPEAR_TIP = defer(NO, "ender_beast_spear_tip", () ->
            new Item(new Item.Properties()));

    //materials
    public static final Wrap<MaterialItem> SCORPION_GLAND = defer(MATERIALS_N_FOOD, "scorpion_gland", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> ICE_SHARD = defer(MATERIALS_N_FOOD, "ice_shard", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> SPORES = defer(MATERIALS_N_FOOD, "spores", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> VISCERAL_ESSENCE = defer(MATERIALS_N_FOOD, "visceral_essence", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> STRANGE_ALLOY = defer(MATERIALS_N_FOOD, "strange_alloy", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> GOLEM_SCRAP = defer(MATERIALS_N_FOOD, "golem_scrap", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> GEL = defer(MATERIALS_N_FOOD, "gel", () ->
            new GelItem(new Item.Properties()));
    public static final Wrap<MaterialItem> ARID_WRAPPING = defer(MATERIALS_N_FOOD, "arid_wrapping", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> LIFE_ESSENCE = defer(MATERIALS_N_FOOD, "life_essence", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> SLIME = defer(MATERIALS_N_FOOD, "slime", () ->
            new MaterialItem(new Item.Properties()));
    public static final Wrap<MaterialItem> IMPULSOR = defer(MATERIALS_N_FOOD, "impulsor", () ->
            new MaterialItem(new Item.Properties()));
	public static final Wrap<MaterialItem> EMOSSENCE_EXTRACT = defer(MATERIALS_N_FOOD, "emossence_extract", () ->
            new MaterialItem(new Item.Properties()));
	public static final Wrap<MaterialItem> HARDENED_FOAMSHROOM = defer(MATERIALS_N_FOOD, "hardened_foamshroom", () ->
            new MaterialItem(new Item.Properties()));
	public static final Wrap<MaterialItem> ENDER_SCALES = defer(MATERIALS_N_FOOD, "ender_scales", () ->
            new MaterialItem(new Item.Properties()));

    //food
    public static final Wrap<Item> RAMMER_MEAT = defer(MATERIALS_N_FOOD, "rammer_meat", () ->
            new Item(new Item.Properties().tab(DannysExpansion.TAB).food(ModFoods.RAMMER_MEAT)));
    public static final Wrap<Item> COOKED_RAMMER_MEAT = defer(MATERIALS_N_FOOD, "cooked_rammer_meat", () ->
            new Item(new Item.Properties().tab(DannysExpansion.TAB).food(ModFoods.COOKED_RAMMER_MEAT)));

    //armor
    public static final Wrap<AntiqueArmorItem> ANTIQUE_ARMOR_HELMET = defer(ARMOR, "antique_helmet", () ->
            new AntiqueArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AntiqueArmorItem> ANTIQUE_ARMOR_CHESTPLATE = defer(ARMOR, "antique_chestplate", () ->
            new AntiqueArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AntiqueArmorItem> ANTIQUE_ARMOR_LEGGINGS = defer(ARMOR, "antique_leggings", () ->
            new AntiqueArmorItem(ArmorMaterials.IRON, EquipmentSlot.LEGS, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AntiqueArmorItem> ANTIQUE_ARMOR_BOOTS = defer(ARMOR, "antique_boots", () ->
            new AntiqueArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET, new Item.Properties().tab(DannysExpansion.TAB)));

    //sword
    public static final Wrap<EclipseItem> ECLIPSE = defer(SWORDS, "eclipse", () ->
            new EclipseItem(new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<ScorpionSwordItem> SCORPION_SWORD = defer(SWORDS, "scorpion_sword", () ->
            new ScorpionSwordItem(new Item.Properties().tab(DannysExpansion.TAB)));
   
    //bow
    public static final Wrap<FrozenBowItem> FROZEN_BOW = defer(BOWS, "frozen_bow", () ->
            new FrozenBowItem(new Item.Properties().durability(564).tab(DannysExpansion.TAB)));
	public static final Wrap<EquinoxItem> EQUINOX = defer(BOWS, "equinox", () ->
            new EquinoxItem(new Item.Properties().durability(564).tab(DannysExpansion.TAB)));
	
	//gun
    public static final Wrap<HandgunItem> HANDGUN = defer(GUNS, "handgun", () ->
            new HandgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<MusketItem> MUSKET = defer(GUNS, "musket", () ->
            new MusketItem(new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<GolemHandgunItem> HEAVY_HANDGUN = defer(GUNS, "heavy_handgun", () ->
            new GolemHandgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<ShotgunItem> SHOTGUN = defer(GUNS, "shotgun", () ->
            new ShotgunItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<ShootingStarItem> SHOOTING_STAR = defer(GUNS, "shooting_star", () ->
            new ShootingStarItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //bomb
    public static final Wrap<SporeBombItem> SPORE_BOMB = defer(GUNS, "spore_bomb", () ->
            new SporeBombItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //summon
    public static final Wrap<BlueSlimeItem> BLUE_SLIME = defer(GUNS, "blue_slime", () ->
            new BlueSlimeItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //accessory
    public static final Wrap<AccessoryItem> GELATINOUS_DIPLOMACY = defer(ACCESSORIES, "gelatinous_diplomacy", () ->
            new AccessoryItem(DEAccessoryKeys.GELATINOUS_DIPLOMACY, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> KLIFOUR_TALISMAN = defer(ACCESSORIES, "klifour_talisman", () ->
            new AccessoryItem(DEAccessoryKeys.KLIFOUR_TALISMAN, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> AYERSTONE = defer(ACCESSORIES, "ayerstone", () ->
            new AccessoryItem(DEAccessoryKeys.AYERSTONE, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AccessoryItem> SAND_NECKLACE = defer(ACCESSORIES, "sand_necklace", () ->
            new AccessoryItem(DEAccessoryKeys.SAND_NECKLACE, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> ANTITOXIN = defer(ACCESSORIES, "antitoxin", () ->
            new AccessoryItem(DEAccessoryKeys.ANTITOXIN, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> EBBEWEL = defer(ACCESSORIES, "ebbewel", () ->
            new AccessoryItem(DEAccessoryKeys.EBBEWEL, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> PIGLIN_MEDAL = defer(ACCESSORIES, "piglin_medal", () ->
            new AccessoryItem(DEAccessoryKeys.PIGLIN_MEDAL, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> END_BALLOON = defer(ACCESSORIES, "end_balloon", () ->
            new AccessoryItem(DEAccessoryKeys.END_BALLOON, new Item.Properties().tab(DannysExpansion.TAB)));
	public static final Wrap<AccessoryItem> BURBLE = defer(ACCESSORIES, "burble", () ->
            new AccessoryItem(DEAccessoryKeys.BURBLE, new Item.Properties().tab(DannysExpansion.TAB)));

    //arrow
    public static final Wrap<IceArrowItem> ICE_ARROW = defer(ARROWS, "ice_arrow", () ->
            new IceArrowItem(new Item.Properties().tab(DannysExpansion.TAB)));

    //bullet
    public static final Wrap<BulletItem> BULLET = defer(AMMO, "bullet", () ->
            new BulletItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<HighVelocityBullet> HIGH_VELOCITY_BULLET = defer(AMMO, "high_velocity_bullet", () ->
            new HighVelocityBullet(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<AquaticBullet> AQUATIC_BULLET = defer(AMMO, "aquatic_bullet", () ->
            new AquaticBullet(new Item.Properties().tab(DannysExpansion.TAB)));
	
    static {
        DEBuildingItems.call();
        DEKiteItems.call();
    }

    public static <U extends Item> Wrap<U> defer(DEItemCategory category, String name, Supplier<U> sup) {
        Wrap<U> wrapped = HELPER.defer(name, sup);
        Braincell.common().getItemSortData().setSortValue(wrapped.getKey(), (short)category.ordinal());
        return wrapped;
    }
}

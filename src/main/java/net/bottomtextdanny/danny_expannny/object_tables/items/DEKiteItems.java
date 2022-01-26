package net.bottomtextdanny.danny_expannny.object_tables.items;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DEConstants;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.items.KiteItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import static net.bottomtextdanny.danny_expannny.object_tables.items.DEItemCategory.*;
import static net.bottomtextdanny.danny_expannny.object_tables.items.DEItems.defer;

public final class DEKiteItems {
    public static final Wrap<KiteItem> WHITE_KITE = defer(BASIC_KITES, "white_kite", () ->
            new KiteItem(0, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> ORANGE_KITE = defer(BASIC_KITES, "orange_kite", () ->
            new KiteItem(1, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> MAGENTA_KITE = defer(BASIC_KITES, "magenta_kite", () ->
            new KiteItem(2, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> LIGHT_BLUE_KITE = defer(BASIC_KITES, "light_blue_kite", () ->
            new KiteItem(3, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> YELLOW_KITE = defer(BASIC_KITES, "yellow_kite", () ->
            new KiteItem(4, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> LIME_KITE = defer(BASIC_KITES, "lime_kite", () ->
            new KiteItem(5, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> PINK_KITE = defer(BASIC_KITES, "pink_kite", () ->
            new KiteItem(6, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> GRAY_KITE = defer(BASIC_KITES, "gray_kite", () ->
            new KiteItem(7, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> LIGHT_GRAY_KITE = defer(BASIC_KITES, "light_gray_kite", () ->
            new KiteItem(8, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> CYAN_KITE = defer(BASIC_KITES, "cyan_kite", () ->
            new KiteItem(9, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> PURPLE_KITE = defer(BASIC_KITES, "purple_kite", () ->
            new KiteItem(10, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> BLUE_KITE = defer(BASIC_KITES, "blue_kite", () ->
            new KiteItem(11, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> BROWN_KITE = defer(BASIC_KITES, "brown_kite", () ->
            new KiteItem(12, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> GREEN_KITE = defer(BASIC_KITES, "green_kite", () ->
            new KiteItem(13, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> RED_KITE = defer(BASIC_KITES, "red_kite", () ->
            new KiteItem(14, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<KiteItem> BLACK_KITE = defer(BASIC_KITES, "black_kite", () ->
            new KiteItem(15, new Item.Properties().tab(DannysExpansion.TAB)));

    //vanilla special kite
    public static final Wrap<SpecialKiteItem> BLAZE_KITE = defer(VANILLA_MOB_KITES, "blaze_kite", () -> new SpecialKiteItem(
            EntityType.BLAZE,
            50,
            "blaze",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> CAVE_SPIDER_KITE = defer(VANILLA_MOB_KITES, "cave_spider_kite", () -> new SpecialKiteItem(
            EntityType.CAVE_SPIDER,
            50,
            "cave_spider",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> CREEPER_KITE = defer(VANILLA_MOB_KITES, "creeper_kite", () -> new SpecialKiteItem(
            EntityType.CREEPER,
            50,
            "creeper",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> DROWNED_KITE = defer(VANILLA_MOB_KITES, "drowned_kite", () -> new SpecialKiteItem(
            EntityType.DROWNED,
            50,
            "drowned",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ELDER_GUARDIAN_KITE = defer(VANILLA_MOB_KITES, "elder_guardian_kite", () -> new SpecialKiteItem(
            EntityType.ELDER_GUARDIAN,
            10,
            "elder_guardian",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ENDERMAN_KITE = defer(VANILLA_MOB_KITES, "enderman_kite", () -> new SpecialKiteItem(
            EntityType.ENDERMAN,
            50,
            "enderman",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ENDERMITE_KITE = defer(VANILLA_MOB_KITES, "endermite_kite", () -> new SpecialKiteItem(
            EntityType.ENDERMITE,
            50,
            "endermite",
            (byte)0,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> EVOKER_KITE = defer(VANILLA_MOB_KITES, "evoker_kite", () -> new SpecialKiteItem(
            EntityType.EVOKER,
            10,
            "evoker",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> GHAST_KITE = defer(VANILLA_MOB_KITES, "ghast_kite", () -> new SpecialKiteItem(
            EntityType.GHAST,
            50,
            "ghast",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> GUARDIAN_KITE = defer(VANILLA_MOB_KITES, "guardian_kite", () -> new SpecialKiteItem(
            EntityType.GUARDIAN,
            50,
            "guardian",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> MAGMACUBE_KITE = defer(VANILLA_MOB_KITES, "magma_cube_kite", () -> new SpecialKiteItem(
            EntityType.MAGMA_CUBE,
            50,
            "magmacube",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> HUSK_KITE = defer(VANILLA_MOB_KITES, "husk_kite", () -> new SpecialKiteItem(
            EntityType.HUSK,
            50,
            "husk",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ILLUSIONER_KITE = defer(VANILLA_MOB_KITES, "illusioner_kite", () -> new SpecialKiteItem(
            EntityType.ILLUSIONER,
            50,
            "illusioner",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> PILLAGER_KITE = defer(VANILLA_MOB_KITES, "pillager_kite", () -> new SpecialKiteItem(
            EntityType.PILLAGER,
            50,
            "pillager",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> RAVAGER_KITE = defer(VANILLA_MOB_KITES, "ravager_kite", () -> new SpecialKiteItem(
            EntityType.RAVAGER,
            10,
            "ravager",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SHULKER_KITE = defer(VANILLA_MOB_KITES, "shulker_kite", () -> new SpecialKiteItem(
            EntityType.SHULKER,
            50,
            "shulker",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SILVERFISH_KITE = defer(VANILLA_MOB_KITES, "silverfish_kite", () -> new SpecialKiteItem(
            EntityType.SILVERFISH,
            50,
            "silverfish",
            (byte)0,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SKELETON_KITE = defer(VANILLA_MOB_KITES, "skeleton_kite", () -> new SpecialKiteItem(
            EntityType.SKELETON,
            50,
            "skeleton",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SLIME_KITE = defer(VANILLA_MOB_KITES, "slime_kite", () -> new SpecialKiteItem(
            EntityType.SLIME,
            50,
            "slime",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SPIDER_KITE = defer(VANILLA_MOB_KITES, "spider_kite", () -> new SpecialKiteItem(
            EntityType.SLIME,
            50,
            "spider",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> STRAY_KITE = defer(VANILLA_MOB_KITES, "stray_kite", () -> new SpecialKiteItem(
            EntityType.STRAY,
            50,
            "stray",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> STRIDER_KITE = defer(VANILLA_MOB_KITES, "strider_kite", () -> new SpecialKiteItem(
            EntityType.STRIDER,
            50,
            "strider",
            (byte)3,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> PHANTOM_KITE = defer(VANILLA_MOB_KITES, "phantom_kite", () -> new SpecialKiteItem(
            EntityType.PHANTOM,
            50,
            "phantom",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> PIGLIN_KITE = defer(VANILLA_MOB_KITES, "piglin_kite", () -> new SpecialKiteItem(
            EntityType.PIGLIN,
            50,
            "piglin",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> PIGLIN_BRUTE_KITE = defer(VANILLA_MOB_KITES, "piglin_brute_kite", () -> new SpecialKiteItem(
            EntityType.PIGLIN_BRUTE,
            50,
            "piglin_brute",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> VEX_KITE = defer(VANILLA_MOB_KITES, "vex_kite", () -> new SpecialKiteItem(
            EntityType.VEX,
            50, "vex", (byte)1, true, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> VINDICATOR_KITE = defer(VANILLA_MOB_KITES, "vindicator_kite", () -> new SpecialKiteItem(
            EntityType.VINDICATOR,
            50,
            "vindicator",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> WITCH_KITE = defer(VANILLA_MOB_KITES, "witch_kite", () -> new SpecialKiteItem(
            EntityType.WITCH,
            50,
            "witch",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> WITHER_KITE = defer(VANILLA_MOB_KITES, "wither_kite", () -> new SpecialKiteItem(
            EntityType.WITHER,
            10,
            "wither",
            (byte)4,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> WITHER_SKELETON_KITE = defer(VANILLA_MOB_KITES, "wither_skeleton_kite", () -> new SpecialKiteItem(
            EntityType.WITHER_SKELETON,
            50,
            "wither_skeleton",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ZOGLIN_KITE = defer(VANILLA_MOB_KITES, "zoglin_kite", () -> new SpecialKiteItem(
            EntityType.ZOGLIN,
            50,
            "zoglin",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ZOMBIE_KITE = defer(VANILLA_MOB_KITES, "zombie_kite", () -> new SpecialKiteItem(
            EntityType.ZOMBIE,
            50,
            "zombie",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ZOMBIE_VILLAGER_KITE = defer(VANILLA_MOB_KITES, "zombie_villager_kite", () -> new SpecialKiteItem(
            EntityType.ZOMBIE_VILLAGER,
            50,
            "zombie_villager",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ZOMBIFIED_PIGLIN_KITE = defer(VANILLA_MOB_KITES, "zombified_piglin_kite", () -> new SpecialKiteItem(
            EntityType.ZOMBIFIED_PIGLIN,
            50,
            "zombified_piglin",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));

    //mowzies mobs special kite
    public static final Wrap<SpecialKiteItem> BARAKO_KITE = defer(COMPAT_MOB_KITES, "barako_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "barako"),
            10,
            "mowziesmobs/mm_barako",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> FERROUS_WROUGHTNAUT_KITE = defer(COMPAT_MOB_KITES, "ferrous_wroughtnaut_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "ferrous_wroughtnaut"),
            10,
            "mowziesmobs/mm_ferrous_wroughtnaut",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> FOLIAATH_KITE = defer(COMPAT_MOB_KITES, "foliaath_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "foliaath"),
            50,
            "mowziesmobs/mm_foliaath",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> FROSTMAW_KITE = defer(COMPAT_MOB_KITES, "frostmaw_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "frostmaw"),
            10,
            "mowziesmobs/mm_frostmaw",
            (byte)4,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> LANTERN_KITE = defer(COMPAT_MOB_KITES, "lantern_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "lantern"),
            50,
            "mowziesmobs/mm_lantern",
            (byte)4,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)) );
    public static final Wrap<SpecialKiteItem> NAGA_KITE = defer(COMPAT_MOB_KITES, "naga_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.MOWZIES_MOBS_ID, "naga"),
            50,
            "mowziesmobs/mm_naga",
            (byte)3,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));

    //alexs mobs special
    public static final Wrap<SpecialKiteItem> BONE_SERPENT_KITE = defer(COMPAT_MOB_KITES, "bone_serpent_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "bone_serpent"),
            50,
            "alexsmobs/am_bone_serpent",
            (byte)3,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> CAVE_CENTIPEDE_KITE = defer(COMPAT_MOB_KITES, "cave_centipede_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "cave_centipede"),
            50,
            "alexsmobs/am_cave_centipede",
            (byte)3,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> CRIMSON_MOSQUITO_KITE = defer(COMPAT_MOB_KITES, "crimson_mosquito_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "crimson_mosquito"),
            50,
            "alexsmobs/am_crimson_mosquito",
            (byte)0,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> DROPBEAR_KITE = defer(COMPAT_MOB_KITES, "dropbear_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "dropbear"),
            50,
            "alexsmobs/am_dropbear",
            (byte)1,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> ENDERGRADE_KITE = defer(COMPAT_MOB_KITES, "endergrade_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "endergrade"),
            50,
            "alexsmobs/am_endergrade",
            (byte)1,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> FLY_KITE = defer(COMPAT_MOB_KITES, "fly_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "fly"),
            50,
            "alexsmobs/am_fly",
            (byte)0,
            false,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> SUNBIRD_KITE = defer(COMPAT_MOB_KITES, "sunbird_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "sunbird"),
            10,
            "alexsmobs/am_sunbird",
            (byte)4,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<SpecialKiteItem> WARPED_TOAD_KITE = defer(COMPAT_MOB_KITES, "warped_toad_kite", () -> new SpecialKiteItem(
            new ResourceLocation(DEConstants.ALEXS_MOBS_ID, "warped_toad"),
            50,
            "alexsmobs/am_warped_toad",
            (byte)3,
            true,
            new Item.Properties().tab(DannysExpansion.TAB)));

    public static void call() {}
}

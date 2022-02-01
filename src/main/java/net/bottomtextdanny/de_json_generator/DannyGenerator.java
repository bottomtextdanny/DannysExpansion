package net.bottomtextdanny.de_json_generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.bottomtextdanny.de_json_generator.types.*;
import net.bottomtextdanny.de_json_generator.types.base.Generator;
import net.bottomtextdanny.de_json_generator.types.base.GeneratorUtils;
import net.bottomtextdanny.de_json_generator.types.blockmodel.GrassLikeModel;
import net.bottomtextdanny.de_json_generator.types.blockmodel.SimpleBlockModel;
import net.bottomtextdanny.de_json_generator.types.extension.*;
import net.bottomtextdanny.de_json_generator.types.item.ArmorItemGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DannyGenerator extends GenUtils implements GeneratorUtils {
	public static Set<TagBuffer> TAGS = new HashSet<>();

	public static final TagBuffer EMOSSENCE_PLACEABLE_ON = new TagBuffer(MOD_ID, "blocks", "emossence_placeable_on");

	public static final TagBuffer PLANKS = new TagBuffer(MC_ID, "items&blocks", "planks");
	public static final TagBuffer FENCES = new TagBuffer(MC_ID, "items&blocks", "fences");
	public static final TagBuffer WALLS = new TagBuffer(MC_ID, "items&blocks", "walls");
	public static final TagBuffer WOODEN_FENCES = new TagBuffer(MC_ID, "items&blocks", "wooden_fences");
	public static final TagBuffer FENCE_GATES = new TagBuffer(MC_ID, "items&blocks", "fence_gates");
	public static final TagBuffer SLABS = new TagBuffer(MC_ID, "items&blocks", "slabs");
	public static final TagBuffer WOODEN_SLABS = new TagBuffer(MC_ID, "items&blocks", "wooden_slabs");
	public static final TagBuffer STAIRS = new TagBuffer(MC_ID, "items&blocks", "stairs");
	public static final TagBuffer WOODEN_STAIRS = new TagBuffer(MC_ID, "items&blocks", "wooden_stairs");
	public static final TagBuffer DOORS = new TagBuffer(MC_ID, "items&blocks", "doors");
	public static final TagBuffer WOODEN_DOORS = new TagBuffer(MC_ID, "items&blocks", "wooden_doors");
	public static final TagBuffer BUTTONS = new TagBuffer(MC_ID, "items&blocks", "buttons");
	public static final TagBuffer WOODEN_BUTTONS = new TagBuffer(MC_ID, "items&blocks", "wooden_buttons");
	public static final TagBuffer PRESSURE_PLATES = new TagBuffer(MC_ID, "items&blocks", "pressure_plates");
	public static final TagBuffer WOODEN_PRESSURE_PLATES = new TagBuffer(MC_ID, "items&blocks", "wooden_pressure_plates");
	public static final TagBuffer TRAPDOORS = new TagBuffer(MC_ID, "items&blocks", "trapdoors");
	public static final TagBuffer WOODEN_TRAPDOORS = new TagBuffer(MC_ID, "items&blocks", "wooden_trapdoors");
	public static final TagBuffer STANDING_SIGNS = new TagBuffer(MC_ID, "blocks", "standing_signs");
	public static final TagBuffer WALL_SIGNS = new TagBuffer(MC_ID, "blocks", "wall_signs");
	public static final TagBuffer SIGNS = new TagBuffer(MC_ID, "items", "signs");
	public static final TagBuffer GUARDED_BY_PIGLINS = new TagBuffer(MC_ID, "blocks", "guarded_by_piglins");
	public static final TagBuffer DRAGON_IMMUNE = new TagBuffer(MC_ID, "blocks", "dragon_immune");
	public static final TagBuffer BOATS = new TagBuffer(MC_ID, "items", "boats");

	public static final TagBuffer FORGE_FENCES = new TagBuffer("forge", "items&blocks", "fences");
	public static final TagBuffer FORGE_WOODEN_FENCES = new TagBuffer("forge", "items&blocks/fences", "wooden");
	public static final TagBuffer FORGE_FENCE_GATES = new TagBuffer("forge", "items&blocks", "fence_gates");
	public static final TagBuffer FORGE_WOODEN_FENCE_GATES = new TagBuffer("forge", "items&blocks/fence_gates", "wooden");
	public static final TagBuffer FORGE_CHESTS = new TagBuffer("forge", "items&blocks", "chests");
	public static final TagBuffer FORGE_WOODEN_CHESTS = new TagBuffer("forge", "items&blocks/chests", "wooden");
	public static final TagBuffer FORGE_TRAPPED_CHESTS = new TagBuffer("forge", "items&blocks/chests", "trapped");
	public static final TagBuffer FORGE_MUSHROOMS = new TagBuffer("forge", "items", "mushrooms");

	public static final TagBuffer NEEDS_ENDER_TOOL = new TagBuffer("dannys_expansion", "blocks", "needs_ender_tool");

	public static final TagBuffer ENDERGETIC_ENDER_FIRE_BASES = new TagBuffer("endergetic", "blocks", "ender_fire_base_blocks");

	public static final TagBuffer CURIOS_ACCESSORIES = new TagBuffer("curios", "items", "danny_accessory");

	public static final BlockDropGenExt BLOCK_DROP = new BlockDropGenExt();
	public static final SilkOrElseDropGenExt SILK_OR_ELSE = new SilkOrElseDropGenExt();
	public static final GrassLikeDropGenExt GRASS_LIKE_DROP = new GrassLikeDropGenExt();
	public static final WoodGenExt WOODEN_FURNITURE = new WoodGenExt();

	public static void main(String[] args) throws IOException {

		new FullblockGenerator("ice_bricks")
				.with(BLOCK_DROP).generate();
		new FullblockGenerator("weak_ice")
				.with(BLOCK_DROP)
				.with(ext_loottable("blocks", lt_silktouch_only())).generate();
		new SlabGenerator("ice_brick_slab", "ice_bricks")
				.with(ext_recipe(rs_slab("dannys_expansion:ice_bricks"))).with(BLOCK_DROP).generate();
		new StairsGenerator("ice_brick_stairs", "ice_bricks")
				.with(ext_recipe(rs_stairs("dannys_expansion:ice_bricks"))).with(BLOCK_DROP).generate();
		new DoorGenerator("ice_door", "ice_bricks")
				.with(ext_loottable("blocks", lt_double()))
				.with(ext_recipe(rs_door("dannys_expansion:ice_bricks"))).generate();
		new TrapdoorGenerator("ice_trapdoor", "ice_bricks")
				.with(BLOCK_DROP)
				.with(ext_recipe(rs_trapdoor("dannys_expansion:ice_bricks"))).generate();
		new ChestGenerator("ice_chest", "ice_bricks").materialID("dannys_expansion:foamshroom_fancy_planks")
				.with(BLOCK_DROP).generate();
		new ChestGenerator("trapped_ice_chest", "ice_bricks").materialID("dannys_expansion:ice_chest")
				.trapped()
				.with(BLOCK_DROP).generate();
		new ButtonGenerator("ice_button", "ice_bricks")
				.with(BLOCK_DROP).generate();
		new PressurePlateGenerator("ice_pressure_plate", "ice_bricks")
				.with(BLOCK_DROP).generate();

		new FullblockGenerator("cobbled_briostone")
				.with(BLOCK_DROP).generate();
		new FullblockGenerator("briostone_crags")
				.with(ext_loottable("blocks", lt_classic("dannys_expansion:cobbled_briostone"))).generate();
		new FullblockGenerator("briostone_bricks")
				.with(BLOCK_DROP)
				.with(ext_recipe(rs_square(danny("cobbled_briostone")).result(result(4))))
				.generate();
		new SlabGenerator("briostone_brick_slab", "briostone_bricks")
				.with(ext_recipe(rs_slab("dannys_expansion:briostone_bricks"))).with(BLOCK_DROP).generate();
		new StairsGenerator("briostone_brick_stairs", "briostone_bricks")
				.with(ext_recipe(rs_stairs("dannys_expansion:briostone_bricks"))).with(BLOCK_DROP).generate();

		new FullblockGenerator("chiseled_briostone_bricks").with(BLOCK_DROP)
				.with(ext_recipe(rs_chiseled("dannys_expansion:briostone_bricks")))
				.generate();

		g_column_assets("briostone_pillar", danny("block/briostone_pillar_side"), danny("block/briostone_pillar_end")).with(BLOCK_DROP).generate();

		new ChestGenerator("briostone_chest", "briostone_bricks").materialID("dannys_expansion:briostone_bricks").with(ext_recipe(rs_chest("dannys_expansion:briostone_bricks"))).with(ext_loottable("blocks", lt_chest())).generate();

		new FullblockGenerator("foamshroom_block").with(SILK_OR_ELSE.aux("dannys_expansion:foamshroom").bounds(-4, 1)).generate();
		ColumnGenerator.oneFaced("foamshroom_stem").with(SILK_OR_ELSE.aux("dannys_expansion:foamshroom").bounds(-4, 1)).generate();

		new FullblockGenerator("foamshroom_planks").with(TagGenExt.of(PLANKS)).with(BLOCK_DROP).with(ext_recipe(rs_shapeless().ingredients(MOD_ID + ":hardened_foamshroom").result(result(MOD_ID + ":foamshroom_planks").count(4)))).generate();
		new DoorGenerator("foamshroom_door", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new SlabGenerator("foamshroom_slab", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new StairsGenerator("foamshroom_stairs", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new FenceGenerator("foamshroom_fence", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new FenceGateGenerator("foamshroom_fence_gate", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ChestGenerator("foamshroom_chest", "foamshroom_planks").materialID("dannys_expansion:foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ChestGenerator("trapped_foamshroom_chest", "foamshroom_planks").materialID("dannys_expansion:foamshroom_chest").trapped().with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ButtonGenerator("foamshroom_button", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new PressurePlateGenerator("foamshroom_pressure_plate", "foamshroom_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new FullblockGenerator("foamshroom_fancy_planks").with(TagGenExt.of(PLANKS)).with(BLOCK_DROP).with(ext_recipe(rs_square(MOD_ID + ":hardened_foamshroom").result(result(MOD_ID + ":foamshroom_fancy_planks").count(4)))).generate();
		new SlabGenerator("foamshroom_fancy_slab", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new StairsGenerator("foamshroom_fancy_stairs", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ChestGenerator("foamshroom_fancy_chest", "foamshroom_fancy_planks").materialID("dannys_expansion:foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ChestGenerator("trapped_foamshroom_fancy_chest", "foamshroom_fancy_planks").materialID("dannys_expansion:foamshroom_fancy_chest").trapped().with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new DoorGenerator("foamshroom_fancy_door", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new FenceGenerator("foamshroom_fancy_fence", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new FenceGateGenerator("foamshroom_fancy_fence_gate", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new ButtonGenerator("foamshroom_fancy_button", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();
		new PressurePlateGenerator("foamshroom_fancy_pressure_plate", "foamshroom_fancy_planks").with(WOODEN_FURNITURE).with(BLOCK_DROP).generate();

		new ComplexGenerator("emossence", new GrassLikeModel("minecraft:end_stone")).blockstate(SimpleBlockstate.HORIZONTAL_ROTATIVE)
				.with(TagGenExt.of(DRAGON_IMMUNE, EMOSSENCE_PLACEABLE_ON, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_loottable("blocks", lt_emossence_like("minecraft:end_stone", "dannys_expansion:emossence_extract", 0.05F)))
				.generate();

		g_grass_like_block_assets("emossence_rubbold", danny("block/emossence_top"), danny("block/emossence_rubbold_side"), danny("block/rubbold"))
				.with(TagGenExt.of(DRAGON_IMMUNE, EMOSSENCE_PLACEABLE_ON, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_loottable("blocks", lt_emossence_like("dannys_expansion:rubbold", "dannys_expansion:emossence_extract", 0.05F)))
				.generate();

		g_potted_plant("potted_emossence_plant", danny("block/emossence_plant_0")).with(ext_loottable("blocks", lt_potted(danny("emossence_plant")))).generate();
		g_potted_plant("potted_foamshroom", danny("block/foamshroom")).with(ext_loottable("blocks", lt_potted(danny("foamshroom")))).generate();

		new FullblockGenerator("rubbold").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES)).generate();
		new SlabGenerator("rubbold_slab", "rubbold").with(ext_recipe(rs_slab("dannys_expansion:rubbold"))).with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES)).generate();
		new StairsGenerator("rubbold_stairs", "rubbold").with(ext_recipe(rs_slab("dannys_expansion:rubbold"))).with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES)).generate();

		new FullblockGenerator("rubbold_bricks").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_square(danny("rubbold")).result(result(1))))
				.generate();

		new FullblockGenerator("cracked_rubbold_bricks").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_smelting().result(danny(CURRENT_NAME)).ingredient(danny("rubbold_bricks")).cookingTime(200).experience(0.2F)))
				.generate();

		new FullblockGenerator("chiseled_rubbold_bricks").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_chiseled("dannys_expansion:rubbold_bricks")))
				.generate();

		new SlabGenerator("rubbold_brick_slab", "rubbold_bricks") .with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_slab("dannys_expansion:rubbold_bricks")))
				.generate();

		new StairsGenerator("rubbold_brick_stairs", "rubbold_bricks").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_slab("dannys_expansion:rubbold_bricks")))
				.generate();


		g_wall_assets("rubbold_brick_wall", "dannys_expansion:block/rubbold_brick_wall", "dannys_expansion:block/rubbold_bricks").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES))
				.with(ext_recipe(rs_wall("dannys_expansion:rubbold_bricks_slab")))
				.generate();

		g_column_assets("rubbold_pillar", danny("block/rubbold_pillar_side"), danny("block/rubbold_pillar_end")).with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE, ENDERGETIC_ENDER_FIRE_BASES)).generate();

		new ChestGenerator("rubbold_lost_chest", "rubbold_bricks").with(BLOCK_DROP).generate();
		new ChestGenerator("trapped_rubbold_lost_chest", "rubbold_bricks").trapped().with(BLOCK_DROP).generate();

		new ComplexGenerator("emossence_sward", new SimpleBlockModel("cross")).itemModel("block_item_baked").with(ext_loottable("blocks", lt_grass_like("dannys_expansion:foamshroom", 0.05F))).generate();

		new TallPlantGenerator("nightshade_plant").with(ext_loottable("blocks", lt_special_double_like("minecraft:diamond", 0.05F))).generate();

		g_plant_variant_assets("emossence_plant", 6).with(ext_loottable("blocks", lt_grass_like("dannys_expansion:foamshroom", 0.02F))).generate();

		new FullblockGenerator("ectoshroom_block").with(SILK_OR_ELSE.aux("dannys_expansion:foamshroom").bounds(-4, 1)).generate();

		ColumnGenerator.oneFaced("ectoshroom_stem").with(SILK_OR_ELSE.aux("dannys_expansion:foamshroom").bounds(-4, 1)).generate();

		new ComplexGenerator("neithine", new GrassLikeModel("minecraft:end_stone")).blockstate(SimpleBlockstate.NORMAL)
				.with(TagGenExt.of(DRAGON_IMMUNE))
				.with(SILK_OR_ELSE.aux("minecraft:end_stone"))
				.generate();

		new AnchorGenerator("emossence_anchor").with(BLOCK_DROP).with(TagGenExt.of(DRAGON_IMMUNE)).generate();

		new FullblockGenerator("plant_matter").generate();

		new ChestGenerator("sea_chest", "orange_terracotta").mcId().with(BLOCK_DROP).generate();
		new ChestGenerator("trapped_sea_chest", "orange_terracotta").trapped().mcId().with(BLOCK_DROP).with(ext_recipe(rs_trapped_chest(MOD_ID + ":sea_chest"))).generate();

		new BuiltinGenerator("workbench", "oak_planks").itemModelTemplateNamedSame().particleMCID().with(BLOCK_DROP).with(ext_recipe(
				recipe(" B ", "XXX", "X X")
						.keys(
								keyItem('B', "minecraft:book"),
								keyTag('X', "minecraft:planks")
						)
		)).with(ext_loottable("blocks", lt_by_blockstate(map().put("workbench_part", "main")))).generate();

		//g_sign("foamshroom_sign", "dannys_expansion:foamshroom_planks").generate();
		// g_sign("foamshroom_fancy_sign", "dannys_expansion:foamshroom_fancy_planks").generate();



		doLootTable("gameplay", "ender_dragon_reward", lt_ender_dragon_reward());
		automateBasicItems();
		generateWBRecipes();
		TAGS.forEach(TagBuffer::bake);
		ParticleMetadataGenerator.generate();
		SoundMetadataGenerator.generate();
	}


	public static void generateWBRecipes() throws IOException {
		int counter = 0;

		try {
			Generator.trashAgentDir(str -> str.contains("workbench_recipes"), GeneratorUtils.PATH + "\\image\\dannys_expansion\\workbench_recipes");
		} catch(Exception e) {
			e.printStackTrace();
		}

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_tag("minecraft:planks", 8))
						.add(wbi_item("minecraft:hay_block", 5))
						.result(wbi_item("dannys_expansion:test_dummy", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:iron_ingot", 3))
						.add(wbi_item("minecraft:gold_ingot", 3))
						.add(wbi_item("minecraft:redstone", 4))
						.add(wbi_item("minecraft:gunpowder", 4))
						.result(wbi_item("dannys_expansion:impulsor", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:totem_of_undying", 1))
						.result(wbi_item("dannys_expansion:life_essence", 5))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:strange_alloy", 16))
						.result(wbi_item("dannys_expansion:antique_chestplate", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:strange_alloy", 12))
						.result(wbi_item("dannys_expansion:antique_leggings", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:strange_alloy", 8))
						.result(wbi_item("dannys_expansion:antique_boots", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:strange_alloy", 8))
						.add(wbi_item("minecraft:gold_ingot", 3))
						.result(wbi_item("dannys_expansion:antique_helmet", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:netherite_sword", 1))
						.add(wbi_item("dannys_expansion:scorpion_gland", 5))
						.result(wbi_item("dannys_expansion:scorpion_sword", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:blue_ice", 128))
						.add(wbi_item("dannys_expansion:ice_shard", 16))
						.result(wbi_item("dannys_expansion:frozen_bow", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:glass", 5))
						.add(wbi_item("minecraft:gunpowder", 5))
						.add(wbi_item("dannys_expansion:visceral_essence", 3))
						.add(wbi_item("dannys_expansion:spores", 3))
						.result(wbi_item("dannys_expansion:spore_bomb", 1))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:blue_ice", 5))
						.add(wbi_item("dannys_expansion:ice_shard", 1))
						.add(wbi_item("minecraft:arrow", 12))
						.result(wbi_item("dannys_expansion:ice_arrow", 12))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:iron_ingot", 8))
						.add(wbi_item("minecraft:gunpowder", 8))
						.result(wbi_item("dannys_expansion:bullet", 12))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:bullet", 16))
						.add(wbi_item("dannys_expansion:impulsor", 1))
						.result(wbi_item("dannys_expansion:high_velocity_bullet", 16))
		);


		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("dannys_expansion:bullet", 12))
						.add(wbi_item("dannys_expansion:prismarine_shard", 20))
						.result(wbi_item("dannys_expansion:aquatic_bullet", 12))
		);

		doWBRecipe(
				wb_recipe()
						.index(counter++)
						.add(wbi_item("minecraft:saddle", 1))
						.add(wbi_item("dannys_expansion:gel", 72))
						.add(wbi_item("dannys_expansion:life_essence", 5))
						.result(wbi_item("dannys_expansion:blue_slime", 1))
		);

	}

	public static boolean objStringEquals(JsonObject obj, String key, String compareTo) {
		if (obj.has(key)) {
			return obj.get(key).getAsString().equals(compareTo);
		}
		return false;
	}

	public static String objGetStr(JsonObject obj, String key) {
		if (obj.has(key)) {
			return obj.get(key).getAsString();
		}
		return null;
	}

	public static boolean objStringEquals(JsonObject obj, String key, String... compareTo) {
		for (String equalKey : compareTo) {
			if (obj.has(key)) {
				return obj.get(key).getAsString().equals(compareTo);
			}
		}
		return false;
	}

	public static void automateBasicItems() throws IOException {

		g_item("_t_handheld_18", m_handheld18(im_template("item/generated")).bake()).generate();

		// g_item("_t_handheld_32", m_handheld18(im_template("item/generated")).bake()).generate();

		g_sword("eclipse", danny("item/_t_handheld_32"));

		g_big_bow("equinox", danny("item/_t_bow_32"));

		g_item(im_simple_item("emossence_extract")).generate();
		g_item(im_simple_item("hardened_foamshroom")).with(ext_recipe(rs_smelting().ingredient("dannys_expansion:foamshroom").result(deWrap(CURRENT_NAME)).experience(0.01F).cookingTime(120))).generate();


		new ArmorItemGenerator("antique").generate();

//	    g_item("_test_sword",
//		    FreeItemModel.deTex("item", "test_sword")
//		    .setParent("dannys_expansion:item/_t_handheld_18").bake()
//	    ).generate();


		//////////////////////////////////////////////FROM ITEMLIST
		File namesFile = new File(PATH + "/assets/item_list.json");
		JsonParser parser = new JsonParser();
		JsonReader jsonReader = new JsonReader(new FileReader(namesFile));
		jsonReader.setLenient(true);

		JsonObject namesObject = (JsonObject) parser.parse(jsonReader);

		JsonArray namesArray = namesObject.getAsJsonArray("objects");

		namesArray.forEach(asElement -> {
			JsonObject object = asElement.getAsJsonObject();
			String itemName = object.get("name").getAsString();
			try {

				if (objStringEquals(object, "type", "accessory")) {
					CURIOS_ACCESSORIES.add(itemName);
					g_item(im_simple_item(itemName)).generate();
				} else if (objStringEquals(object, "type", "spawn_egg")) {
					g_item(itemName, im_free_dir().setParent("item/template_spawn_egg").bake()).generate();
				} else if (objStringEquals(object, "type", "kite")) {
					g_item(itemName, im_free_dir().setParent("builtin/entity").bake()).generate();
				} else if (objStringEquals(object, "type", "bullet") || objStringEquals(object, "type", "material")) {
					g_item(im_simple_item(itemName)).generate();
				} else if (objStringEquals(object, "type", "boat")) {
					g_item(im_simple_item(itemName))
							.with(TagGenExt.of(BOATS))
							.with(ext_recipe(recipe("X X", "XXX")
									.keys(keyItem('X', objGetStr(object, "recipe_material_id")))
									.result(result())
							))
							.generate();
				}

//		        else if (
//		        	itemName.contains("large_bullet") ||
//				        itemName.contains("short_bullet") ||
//				        itemName.contains("shotgun_shell")
//		        ) {
//			        g_item(im_simple_item(itemName)).generate();
//		        }
//
//		        if (itemName.contains("_boat")) {
//			        g_item(im_simple_item(itemName)).with(TagGenExt.of(BOATS)).with(ext_recipe(recipe("X X", "XXX"))).generate();
//		        }


			}



			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static String deWrap(String path) {
		return MOD_ID + ":" + path;
	}
}

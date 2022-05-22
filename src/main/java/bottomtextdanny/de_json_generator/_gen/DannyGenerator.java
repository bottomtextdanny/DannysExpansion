package bottomtextdanny.de_json_generator._gen;

import bottomtextdanny.de_json_generator.*;
import bottomtextdanny.de_json_generator.types.*;
import bottomtextdanny.de_json_generator.types.base.GeneratorUtils;
import bottomtextdanny.de_json_generator.types.extension.*;
import bottomtextdanny.de_json_generator.types.item.ArmorItemGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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
	public static final TagBuffer ARROWS = new TagBuffer(MC_ID, "items", "arrows");

	public static final TagBuffer FORGE_FENCES = new TagBuffer("forge", "items&blocks", "fences");
	public static final TagBuffer FORGE_WOODEN_FENCES = new TagBuffer("forge", "items&blocks/fences", "wooden");
	public static final TagBuffer FORGE_FENCE_GATES = new TagBuffer("forge", "items&blocks", "fence_gates");
	public static final TagBuffer FORGE_WOODEN_FENCE_GATES = new TagBuffer("forge", "items&blocks/fence_gates", "wooden");
	public static final TagBuffer FORGE_CHESTS = new TagBuffer("forge", "items&blocks", "chests");
	public static final TagBuffer FORGE_WOODEN_CHESTS = new TagBuffer("forge", "items&blocks/chests", "wooden");
	public static final TagBuffer FORGE_TRAPPED_CHESTS = new TagBuffer("forge", "items&blocks/chests", "trapped");
	public static final TagBuffer FORGE_MUSHROOMS = new TagBuffer("forge", "items", "mushrooms");

	//public static final TagBuffer NEEDS_ENDER_TOOL = new TagBuffer("dannys_expansion", "blocks", "needs_ender_tool");
	//public static final TagBuffer BEACH_SPAWNS = new TagBuffer("dannys_expansion", "blocks", "beach_spawns");

	public static final TagBuffer ENDERGETIC_ENDER_FIRE_BASES = new TagBuffer("endergetic", "blocks", "ender_fire_base_blocks");

	public static final BlockDropGenExt BLOCK_DROP = new BlockDropGenExt();
	public static final SilkOrElseDropGenExt SILK_OR_ELSE = new SilkOrElseDropGenExt();
	public static final GrassLikeDropGenExt GRASS_LIKE_DROP = new GrassLikeDropGenExt();
	public static final WoodGenExt WOODEN_FURNITURE = new WoodGenExt();

	public static void main(String[] args) throws IOException {
		BlockGenerator.gen();
		LazyRecipesGenerator.gen();
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

		automateBasicItems();
		TAGS.forEach(TagBuffer::bake);
		ParticleMetadataGenerator.generate();
		SoundMetadataGenerator.generate();
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
					g_item(im_simple_item(itemName)).generate();
				} else if (objStringEquals(object, "type", "spawn_egg")) {
					g_item(itemName, im_free_dir().setParent("item/template_spawn_egg").bake()).generate();
				} else if (objStringEquals(object, "type", "kite")) {
					g_item(itemName, im_free_dir().setParent("builtin/entity").bake()).generate();
				} else if (objStringEquals(object, "type", "bullet") || objStringEquals(object, "type", "material")) {
					g_item(im_simple_item(itemName)).generate();
				} else if (objStringEquals(object, "type", "arrow")) {
					g_item(im_simple_item(itemName))
							.with(TagGenExt.of(ARROWS)).generate();
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

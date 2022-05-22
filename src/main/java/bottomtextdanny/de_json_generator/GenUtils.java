package bottomtextdanny.de_json_generator;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.inner.TextureProvider;
import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsFrontend;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.jsonBakers._blockstate.*;
import bottomtextdanny.de_json_generator.jsonBakers._model.JsonModel;
import bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeMaster;
import bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeShapedMaster;
import bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeShapelessMaster;
import bottomtextdanny.de_json_generator.jsonBakers._recipehelper.RecipeSmeltingMaster;
import bottomtextdanny.de_json_generator.jsonBakers._wb_recipe.WBObjectBaker;
import bottomtextdanny.de_json_generator.jsonBakers._wb_recipe.WBRecipeBaker;
import bottomtextdanny.de_json_generator.jsonBakers.itemmodel.FreeItemModel;
import bottomtextdanny.de_json_generator.jsonBakers.itemmodel.ItemModel;
import bottomtextdanny.de_json_generator.jsonBakers.itemmodel.SimpleItemModel;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.*;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.ApplyBonusFunction;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.extension.LootTableGenExt;
import bottomtextdanny.de_json_generator.types.extension.RecipeGenExt;
import bottomtextdanny.de_json_generator.types.item.ItemGenerator;
import bottomtextdanny.de_json_generator.types.item.MatrixTemplateModel;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class GenUtils extends JsonUtilsFrontend {
	public static String CURRENT_NAME = "";
	
	public static String danny(String name) {
		return "dannys_expansion:" + name;
	}
	
	public static WBRecipeBaker wb_recipe() {
		return new WBRecipeBaker();
	}
	
	public static WBObjectBaker lezy_item(String itemID, int count, char mod) {
		return new WBObjectBaker(mod + "item", itemID, map().put("count", count));
	}

	public static WBObjectBaker lezy_item(String itemID, int count) {
		return new WBObjectBaker("item", itemID, map().put("count", count));
	}

	public static WBObjectBaker wbi_tool(String itemID, int damage) {
		return new WBObjectBaker("tool", itemID, map().put("damage", damage));
	}

	public static JsonModel model(String parent, JsonMap map) {
		return  new JsonModel(parent, map);
	}
	
	public static VariantStateMaster var_state() {
		return new VariantStateMaster();
	}
	
	public static MultipartStateMaster multi_state() {
		return new MultipartStateMaster();
	}
	
	//*********GENERATOR SHORTCUTS MODULE STARTS
	
	public static Generator<?> g_plant_variant_assets(String name, int variants) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				StateModel[] models =  new StateModel[variants];
				
				doItemModel(this.name, new JsonModel("item/generated", map().put("layer0", "dannys_expansion:block/" + this.name + "_0")).bake());
				
				for (int i = 0; i < variants; i++) {
					String iterationName = this.name + "_" + i;
					models[i] = new StateModel().path("dannys_expansion:block/" +iterationName);
					
					doModel(iterationName, model("minecraft:block/cross", map().put("cross", "dannys_expansion:block/" + iterationName)).bake());
				}
				
				doBlockState(this.name, GenUtils.var_state().addVariant(
					new BlockStateVariant()
					.addModels(
						models
					)
				).bake());
			}
		};
	}
	
	public static Generator<?> g_potted_plant(String name, String plantTexturePath) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				doItemModel(this.name, new JsonModel(MOD_ID + ':' + "block/" + this.name, map()).bake());
				
				doModel(this.name, ExtraModelGen.m_potted_plant(plantTexturePath));
				
				doBlockState(this.name, GenUtils.var_state().addVariant(
					new BlockStateVariant()
						.path(MOD_ID + ":block/" + this.name)
				).bake());
			}
		};
	}
	
	public static Generator<?> g_grass_like_block_assets(String name, String top, String side, String bottom) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				doItemModel(this.name, new JsonModel(MOD_ID + ':' + "block/" + this.name, map()).bake());
				
				doModel(this.name, ExtraModelGen.m_bottom_side_top(top, side, bottom));
				
				doBlockState(this.name, GenUtils.var_state().addVariant(
					new BlockStateVariant()
						.addModels(
							new StateModel().path(MOD_ID + ":block/" + this.name),
							new StateModel().path(MOD_ID + ":block/" + this.name).yRot(90),
							new StateModel().path(MOD_ID + ":block/" + this.name).yRot(180),
							new StateModel().path(MOD_ID + ":block/" + this.name).yRot(270)
						)
				).bake());
			}
		};
	}
	
	public static Generator<?> g_column_assets(String name, String side, String end) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				doItemModel(this.name, new JsonModel(MOD_ID + ':' + "block/" + this.name, map()).bake());
				
				doModel(this.name, ExtraModelGen.m_column(side, end));
				
				doBlockState(this.name, GenUtils.var_state().addVariants(
					new BlockStateVariant(
						map()
							.put("axis", "x")
					)
						.path(MOD_ID + ":block/" + this.name)
						.xRot(90)
						.yRot(90)
					,
					new BlockStateVariant(
						map()
							.put("axis", "y")
					)
						.path(MOD_ID + ":block/" + this.name)
					,
					new BlockStateVariant(
						map()
							.put("axis", "z")
					)
						.path(MOD_ID + ":block/" + this.name)
						.xRot(90)
				).bake());
			}
		};
	}
	
	
	public static Generator<?> g_sign(String name, String material) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				String wall_name = this.name.replace("_sign", "_wall_sign");
				doItemModel(this.name, new JsonModel("item/generated", map().put("layer0", danny("item/" + this.name))).bake());
				
				ResourceLocation nameRef = new ResourceLocation(material);
				String materialPath = nameRef.getNamespace() + ":block/" + nameRef.getPath();
				
				doRecipe(recipe("XXX", "XXX", " S ").group("sign").keys(keyItem('X', material), keyItem('S', "minecraft:stick")).result(result(3)).bake());
				
				DannyGenerator.STANDING_SIGNS.add(this.name);
				DannyGenerator.SIGNS.add(this.name);
				DannyGenerator.WALL_SIGNS.add(wall_name);
				
				doModel(this.name, ExtraModelGen.m_particle(materialPath));
				doModel(wall_name, ExtraModelGen.m_particle(materialPath));
				
				doBlockState(this.name, GenUtils.var_state().addVariant(
					new BlockStateVariant(map())
						.path(MOD_ID + ":block/" + this.name)
				).bake());
				
				doBlockState(wall_name, GenUtils.var_state().addVariant(
					new BlockStateVariant(map())
						.path(MOD_ID + ":block/" + wall_name)
				).bake());
			}
		};
	}

	public static Generator<?> g_simple_block(String name, JsonModel model) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				String blockModelPath = MOD_ID + ':' + "block/" + this.name;
				doItemModel(this.name, new JsonModel(blockModelPath, map()).bake());

				doModel(this.name, model);

				doBlockState(this.name, GenUtils.var_state()
						.addVariant(
								new BlockStateVariant().path(blockModelPath)
						)
						.bake()
				);
			}
		};
	}

	public static Generator<?> g_simple_block_h_rotable(String name, JsonModel model) {
		return new Generator(name) {
			@Override
			public void generate() {
				String blockModelPath = MOD_ID + ':' + "block/" + this.name;
				doItemModel(this.name, new JsonModel(blockModelPath, map()).bake());

				doModel(this.name, model);

				doBlockState(this.name, GenUtils.var_state()
						.addVariants(
								new BlockStateVariant(map().put("facing", "east"))
										.path(blockModelPath)
										.yRot(90),
								new BlockStateVariant(map().put("facing", "north"))
										.path(blockModelPath),
								new BlockStateVariant(map().put("facing", "south"))
										.path(blockModelPath)
										.yRot(180),
								new BlockStateVariant(map().put("facing", "west"))
										.path(blockModelPath)
										.yRot(270)
						)
						.bake()
				);
			}
		};
	}

	public static Generator<?> g_stairs(String name, TextureProvider provider) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				String blockModelPath = MOD_ID + ':' + "block/" + this.name;
				String blockModelPathOuter = blockModelPath + "_outer";
				String blockModelPathInner = blockModelPath + "_inner";
				DannyGenerator.STAIRS.add(this.name);
				doItemModel(this.name, new JsonModel(blockModelPath, map()).bake());

				doModel(this.name, ExtraModelGen.m_stairs(provider.down(), provider.up(), provider.north()));
				doModel(this.name + "_outer", ExtraModelGen.m_stairs_outer(provider.down(), provider.up(), provider.north()));
				doModel(this.name + "_inner", ExtraModelGen.m_stairs_inner(provider.down(), provider.up(), provider.north()));

				doBlockState(this.name, GenUtils.var_state()
						.addVariants(
								new BlockStateVariant(map().put("facing", "east").put("half", "bottom").put("shape", "inner_left"))
										.path(blockModelPathInner).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "bottom").put("shape", "inner_right"))
										.path(blockModelPathInner),
								new BlockStateVariant(map().put("facing", "east").put("half", "bottom").put("shape", "outer_left"))
										.path(blockModelPathOuter).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "bottom").put("shape", "outer_right"))
										.path(blockModelPathOuter),
								new BlockStateVariant(map().put("facing", "east").put("half", "bottom").put("shape", "straight"))
										.path(blockModelPath),
								new BlockStateVariant(map().put("facing", "east").put("half", "top").put("shape", "inner_left"))
										.path(blockModelPathInner).xRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "top").put("shape", "inner_right"))
										.path(blockModelPathInner).xRot(180).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "top").put("shape", "outer_left"))
										.path(blockModelPathOuter).xRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "top").put("shape", "outer_right"))
										.path(blockModelPathOuter).xRot(180).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "east").put("half", "top").put("shape", "straight"))
										.path(blockModelPath).xRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "bottom").put("shape", "inner_left"))
										.path(blockModelPathInner).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "bottom").put("shape", "inner_right"))
										.path(blockModelPathInner).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "bottom").put("shape", "outer_left"))
										.path(blockModelPathOuter).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "bottom").put("shape", "outer_right"))
										.path(blockModelPathOuter).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "bottom").put("shape", "straight"))
										.path(blockModelPath).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "top").put("shape", "inner_left"))
										.path(blockModelPathInner).xRot(180).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "top").put("shape", "inner_right"))
										.path(blockModelPathInner).xRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "top").put("shape", "outer_left"))
										.path(blockModelPathOuter).xRot(180).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "top").put("shape", "outer_right"))
										.path(blockModelPathOuter).xRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "north").put("half", "top").put("shape", "straight"))
										.path(blockModelPath).xRot(180).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "bottom").put("shape", "inner_left"))
										.path(blockModelPathInner),
								new BlockStateVariant(map().put("facing", "south").put("half", "bottom").put("shape", "inner_right"))
										.path(blockModelPathInner).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "bottom").put("shape", "outer_left"))
										.path(blockModelPathOuter),
								new BlockStateVariant(map().put("facing", "south").put("half", "bottom").put("shape", "outer_right"))
										.path(blockModelPathOuter).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "bottom").put("shape", "straight"))
										.path(blockModelPath).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "top").put("shape", "inner_left"))
										.path(blockModelPathInner).xRot(180).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "top").put("shape", "inner_right"))
										.path(blockModelPathInner).xRot(180).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "top").put("shape", "outer_left"))
										.path(blockModelPathOuter).xRot(180).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "top").put("shape", "outer_right"))
										.path(blockModelPathOuter).xRot(180).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "south").put("half", "top").put("shape", "straight"))
										.path(blockModelPath).xRot(180).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "bottom").put("shape", "inner_left"))
										.path(blockModelPathInner).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "bottom").put("shape", "inner_right"))
										.path(blockModelPathInner).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "bottom").put("shape", "outer_left"))
										.path(blockModelPathOuter).yRot(90).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "bottom").put("shape", "outer_right"))
										.path(blockModelPathOuter).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "bottom").put("shape", "straight"))
										.path(blockModelPath).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "top").put("shape", "inner_left"))
										.path(blockModelPathInner).xRot(180).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "top").put("shape", "inner_right"))
										.path(blockModelPathInner).xRot(180).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "top").put("shape", "outer_left"))
										.path(blockModelPathOuter).xRot(180).yRot(180).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "top").put("shape", "outer_right"))
										.path(blockModelPathOuter).xRot(180).yRot(270).uvLock(),
								new BlockStateVariant(map().put("facing", "west").put("half", "top").put("shape", "straight"))
										.path(blockModelPath).xRot(180).yRot(180).uvLock()
						)
						.bake()
				);
			}
		};
	}

	public static Generator<?> g_slab(String name, String doubleName, TextureProvider provider) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				String blockModelPath = MOD_ID + ':' + "block/" + this.name;
				String doubleBlockModelPath = MOD_ID + ':' + "block/" + doubleName;
				String blockModelPathTop = blockModelPath + "_top";
				DannyGenerator.SLABS.add(this.name);
				doItemModel(this.name, new JsonModel(blockModelPath, map()).bake());

				doModel(this.name, ExtraModelGen.m_slab(provider.down(), provider.up(), provider.north()));
				doModel(this.name + "_top", ExtraModelGen.m_slab_top(provider.down(), provider.up(), provider.north()));

				doBlockState(this.name, GenUtils.var_state()
						.addVariants(
								new BlockStateVariant(map().put("type", "bottom"))
										.path(blockModelPath),
								new BlockStateVariant(map().put("type", "double"))
										.path(doubleBlockModelPath),
								new BlockStateVariant(map().put("type", "top"))
										.path(blockModelPathTop)
						)
						.bake()
				);
			}
		};
	}

	public static Generator<?> g_wall_assets(String name, String wall, String texturePath) {
		return new Generator(name) {
			@Override
			public void generate() throws IOException {
				DannyGenerator.WALLS.add(this.name);
				doItemModel(this.name, new JsonModel(MOD_ID + ':' + "block/" + this.name + "_inventory", map()).bake());
				
				doModel(this.name + "_inventory", ExtraModelGen.m_wall_inventory(texturePath));
				doModel(this.name + "_post", ExtraModelGen.m_wall_post(texturePath));
				doModel(this.name + "_side", ExtraModelGen.m_wall_side(texturePath));
				doModel(this.name + "_side_tall", ExtraModelGen.m_wall_side_tall(texturePath));
				
				doBlockState(this.name, GenUtils.multi_state()
					.addParts(
						new BlockStateMultipart().when(map().put("up", "true"))
							.path(wall + "_post")
						,
						new BlockStateMultipart().when(map().put("north", "low"))
							.path(wall + "_side")
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("east", "low"))
							.path(wall + "_side")
							.yRot(90)
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("south", "low"))
							.path(wall + "_side")
							.yRot(180)
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("west", "low"))
							.path(wall + "_side")
							.yRot(270)
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("north", "tall"))
							.path(wall + "_side_tall")
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("east", "tall"))
							.path(wall + "_side_tall")
							.yRot(90)
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("south", "tall"))
							.path(wall + "_side_tall")
							.yRot(180)
							.uvLock()
						,
						new BlockStateMultipart().when(map().put("west", "tall"))
							.path(wall + "_side_tall")
							.yRot(270)
							.uvLock()
					)
					.bake()
				);
			}
		};
	}
	
	public static ItemModel m_handheld18(ItemModel model) {
		model.thirdPersonRightHand
			.rotation(0.0F, -90.0F, 55.0F)
			.translation(0.0F, 5.25F, 0.5F)
			.scale(0.95625F, 0.95625F, 0.85F);
		
		model.firstPersonRightHand
			.rotation(0.0F, -90.0F, 25.0F)
			.translation(1.13F, 3.2F, 1.13F)
			.scale(0.765F, 0.765F, 0.68F);
		
		model.ground
			.rotation(0.0F, 0.0F, 0.0F)
			.translation(0.0F, 2.0F, 0.0F)
			.scale(0.56125F, 0.56125F, 0.5F);
		
		model.fixed
			.rotation(0.0F, 0.0F, 0.0F)
			.translation(0.0F, 0.0F, 0.0F)
			.scale(1.125F, 1.125F, 1.125F);
		
		model.gui
			.rotation(0.0F, 0.0F, 0.0F)
			.translation(0.0F, 0.0F, 0.0F)
			.scale(1.0F, 1.0F, 1.0F);
		
		return model.bake();
	}
	
	public static MatrixTemplateModel im_template(String parent) {
		return new MatrixTemplateModel(parent);
	}
	
	public static SimpleItemModel im_simple_item_oriented(String object, String... name) {
		return SimpleItemModel.deTex(object, name);
	}
	
	public static SimpleItemModel im_simple_item(String... name) {
		if (name.length < 1)
			return SimpleItemModel.deTex("item", CURRENT_NAME);
		else
			return SimpleItemModel.deTex("item", name);
	}
	
	public static FreeItemModel im_free_dir() {
		return new FreeItemModel();
	}
	
	
	public static SimpleItemModel im_free_model(String... directory) {
		return SimpleItemModel.deFreeDir(directory);
	}
	
	public static ItemGenerator g_item(String name, ItemModel model) {
		return new ItemGenerator(name, model);
	}
	
	public static ItemGenerator g_item(ItemModel model) {
		return ItemGenerator.create(model);
	}
	
	public static void g_sword(String name, String handheldPath) {
		String nameHandheld = name + "_handheld";
		String nameGui = name + "_gui";
		ItemModel handheld = new ItemModel(handheldPath).layers("dannys_expansion:item/" + name).bake();
		ItemModel gui = new ItemModel("item/generated").layers("dannys_expansion:item/" + nameGui).bake();
		ItemModel base = new ItemModel("builtin/entity").bake();
		
		
		doItemModel(nameHandheld, handheld);
		doItemModel(nameGui, gui);
		doItemModel(name, base);
	}
	
	public static void g_big_bow(String name, String handheldPath) {
		String nameHandheld = name + "_handheld";
		String nameGui = name + "_gui";
		ItemModel handheld = new ItemModel(handheldPath).layers("dannys_expansion:item/" + name).bake();
		ItemModel gui = new ItemModel("item/generated").layers("dannys_expansion:item/" + nameGui).bake();
		
		
		for (int i = 0; i < 3; i++) {
			String guiIter = name + "_gui_pulling_" + i;
			String handheldIter = name + "_handheld_pulling_" + i;
			doItemModel(guiIter, new ItemModel("minecraft:item/bow").layers("dannys_expansion:item/" + guiIter).bake());
			doItemModel(handheldIter, new ItemModel(handheldPath).layers("dannys_expansion:item/" + handheldIter).bake());
		}
		
		ItemModel base = new ItemModel("builtin/entity").bake();
		
		
		doItemModel(nameHandheld, handheld);
		doItemModel(nameGui, gui);
		doItemModel(name, base);
	}

	//*********RECIPE HELPER STRATS

	public static RecipeShapedMaster rs_door(String materialID) {
		return new RecipeShapedMaster("XX", "XX", "XX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
				.count(3)
			).bake();
	}

	public static RecipeShapedMaster rs_chest(String materialID) {
		return new RecipeShapedMaster("XXX", "X X", "XXX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
			).bake();
	}

	public static RecipeShapelessMaster rs_trapped_chest(String chestID) {
		return new RecipeShapelessMaster()
			.ingredients(
				chestID,
				"minecraft:tripwire_hook"
			)
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
			)
			.bake();
	}
	
	public static RecipeShapedMaster rs_square(String materialID) {
		return new RecipeShapedMaster("XX", "XX")
			.keys(keyItem('X', materialID));
	}
	
	public static RecipeShapelessMaster rs_shapeless() {
		return new RecipeShapelessMaster();
	}
	
	public static RecipeSmeltingMaster rs_smelting() {
		return new RecipeSmeltingMaster();
	}
	
	public static RecipeShapedMaster rs_pressure_plate(String materialID) {
		return new RecipeShapedMaster("XX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
			).bake();
	}
	
	public static RecipeShapelessMaster rs_buttonlike(String materialID) {
		return new RecipeShapelessMaster()
			.ingredients(materialID)
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
			)
			.bake();
	}

	public static RecipeShapedMaster rs_slab(String materialID) {
		return new RecipeShapedMaster("XXX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
				.count(2)
			).bake();
	}
	
	public static RecipeShapedMaster rs_wall(String materialID) {
		return new RecipeShapedMaster("XXX", "XXX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
					.count(6)
			).bake();
	}
	
	public static RecipeShapedMaster rs_chiseled(String materialID) {
		return new RecipeShapedMaster("X", "X")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
					.count(1)
			).bake();
	}

	public static RecipeShapedMaster rs_stairs(String materialID) {
		return new RecipeShapedMaster("X  ", "XX ", "XXX")
			.keys(keyItem('X', materialID))
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
				.count(4)
			).bake();
	}

	public static RecipeShapedMaster rs_fence(String materialID, String transitorID) {
		return new RecipeShapedMaster("XCX", "XCX")
			.keys(
				keyItem('X', materialID),
				keyItem('C', transitorID)
			)
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
				.count(4)
			).bake();
	}

	public static RecipeShapedMaster rs_wooden_fence(String materialID) {
		return rs_fence(materialID, "minecraft:stick");
	}

	public static RecipeShapedMaster rs_wooden_fence_gate(String materialID) {
		return new RecipeShapedMaster("CXC", "CXC")
			.keys(
				keyItem('X', materialID),
				keyItem('C', "minecraft:stick")
			)
			.result(result("dannys_expansion:" + CURRENT_NAME))
			.bake();
	}

	public static RecipeShapedMaster rs_trapdoor(String materialID) {
		return new RecipeShapedMaster("XXX", "XXX")
			.keys(
				keyItem('X', materialID)
			)
			.result(
				result("dannys_expansion:" + CURRENT_NAME)
					.count(2)
			).bake();
	}
	
	//*********LOOT TABLE HELPER STARTS
	
	public static LootTableMaster lt_ender_dragon_reward() {
		return new LootTableMaster(LootTableType.GIFT)
		.pools(
			new LTPool(JsonUtilsMiddleEnd.uniform(8, 9))
			.entries(
				new LTEntry()
				.itemID("dannys_expansion:ender_scales")
				.functions(
					f_set_count(JsonUtilsMiddleEnd.uniform(4.0F, 7.0F))
				)
			)
		).bake();
	}

	public static LootTableMaster lt_classic() {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(JsonUtilsMiddleEnd.constant(1))
				.entries(
					new LTEntry()
					.itemID("dannys_expansion:" + CURRENT_NAME)
				)
				.conditions(
					JsonUtilsFrontend.c_survives_explosion()
				)
			);
	}

	public static LootTableMaster lt_silktouch_only() {
		return new LootTableMaster(LootTableType.BLOCK)
				.pools(
						new LTPool(JsonUtilsMiddleEnd.constant(1.0F))
								.bonusRolls(JsonUtilsMiddleEnd.constant(0.0F))
								.entries(
										new LTEntry()
												.itemID("dannys_expansion:" + CURRENT_NAME)
												.conditions(c_match_tool(item_pred()
														.enchantments(
																enchantment("minecraft:silk_touch").min(1))))
								)
				);
	}
	
	public static LootTableMaster lt_classic(String itemID) {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(JsonUtilsMiddleEnd.constant(1))
					.entries(
						new LTEntry()
							.itemID(itemID)
					)
					.conditions(
						JsonUtilsFrontend.c_survives_explosion()
					)
			);
	}

	public static LootTableMaster lt_slab() {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(JsonUtilsMiddleEnd.constant(1))
				.entries(
					new LTEntry()
					.itemID("dannys_expansion:" + CURRENT_NAME)
					.functions(
						f_set_count(value(2))
						.conditions(
							c_blockstate(map()
							.put("type", "double")
							)
						)
					)
				)
				.conditions(
					JsonUtilsFrontend.c_survives_explosion()
				)
			);
	}

	public static LootTableMaster lt_chest() {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(value(1))
					.entries(
						new LTEntry()
						.itemID("dannys_expansion:" + CURRENT_NAME)
						.functions(
							f_b_copy_name()
						)
					)
					.conditions(
						c_survives_explosion()
					)
			);
	}

	public static LootTableMaster lt_double() {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(value(1))
				.entries(
					new LTEntry()
					.itemID("dannys_expansion:" + CURRENT_NAME)
					.conditions(
						c_blockstate(map()
							.put("half", "lower")
						)
					)
				)
				.conditions(
					c_survives_explosion()
				)
			);
	}
	
	public static LootTableMaster lt_special_double_like(String specialID, float chance) {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(value(1))
				.entries(
					new LTEntry()
					.type(EntryType.ALTERNATIVE)
					.children(
						new LTEntry()
						.itemID("dannys_expansion:" + CURRENT_NAME)
						.conditions(
						    c_match_tool(
						        item_pred()
						            .item("minecraft:shears")
						            .bake()
						            )
						),
						new LTEntry()
						.itemID(specialID)
						.conditions(
							c_random_chance(chance)
						)
					)
					.conditions(
						c_blockstate(map()
						.put("half", "lower")
						)
					)
				)
				.conditions(
					c_survives_explosion()
				)
			);
	}
	
	public static LootTableMaster lt_by_blockstate(JsonMap stateMap) {
		return new LootTableMaster(LootTableType.BLOCK)
		.pools(
			new LTPool(value(1))
			.entries(
				new LTEntry()
				.itemID("dannys_expansion:" + CURRENT_NAME)
				.conditions(
					c_blockstate(
						stateMap
					)
				)
			)
			.conditions(
				c_survives_explosion()
			)
		);
	}
	
	public static LootTableMaster lt_potted(String plantID) {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(value(1))
					.entries(
						new LTEntry().itemID("minecraft:flower_pot")
					)
					.conditions(
						c_survives_explosion()
				    )
				,
				new LTPool(value(1))
					.entries(
						new LTEntry().itemID(plantID)
					)
					.conditions(
						c_survives_explosion()
					)
			
			);
	}
	
	public static LootTableMaster lt_grass_like(String auxID, float elseChance) {
		return new LootTableMaster(LootTableType.BLOCK)
		.pools(
			new LTPool(value(1))
			.entries(
				new LTEntry()
				.type(EntryType.ALTERNATIVE)
				.children(
					new LTEntry()
					.itemID("dannys_expansion:" + CURRENT_NAME)
					.conditions(
						c_match_tool(
							item_pred()
							.item("minecraft:shears")
						)
					)
					,
					new LTEntry()
					.itemID(auxID)
					.conditions(
						c_random_chance(elseChance)
					)
					.functions(
						f_apply_bonus_unbaked("minecraft:fortune")
						.formula(ApplyBonusFunction.Formula.UNIFORM)
						.bonusMultiplier(2.0F)
						.bake()
						,
						f_b_explosion_decay()
					)
				)
			)
		);
	}
	
	public static LootTableMaster lt_emossence_like(String imperfectID, String auxID, float elseChance, LTFunction<?>... functions) {
		return new LootTableMaster(LootTableType.BLOCK)
			.pools(new LTPool(value(1))
				.entries(
					new LTEntry()
					.type(EntryType.ALTERNATIVE)
					.children(
						new LTEntry()
						.itemID("dannys_expansion:" + CURRENT_NAME)
						.conditions(
							c_match_tool(
								item_pred()
								.enchantments(
									enchantment("minecraft:silk_touch")
								)
							)
						),
						new LTEntry()
						.type(EntryType.SEQUENCE)
						.children(
							new LTEntry()
							.itemID(imperfectID)
							,
							new LTEntry()
							.itemID(auxID)
							.conditions(
								c_random_chance(elseChance)
							)
							.functions(
									Stream.concat(
											Arrays.stream(functions),
											Stream.of(f_apply_bonus_unbaked("minecraft:fortune")
											.formula(ApplyBonusFunction.Formula.UNIFORM)
											.bonusMultiplier(2.0F)
											.bake())).toArray(LTFunction[]::new)
							)
						)
					)
					.functions(
						f_b_explosion_decay()
					)
				)
			);
	}

	public static RecipeGenExt ext_recipe(RecipeMaster lootTable) {
		return new RecipeGenExt(lootTable);
	}

	public static LootTableGenExt ext_loottable(String object, LootTableMaster lootTable) {
		return new LootTableGenExt(object, lootTable);
	}
}

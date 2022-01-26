package net.bottomtextdanny.de_json_generator.jsonBakers;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.GenUtils;
import net.bottomtextdanny.de_json_generator.jsonBakers._model.JsonModel;
import net.bottomtextdanny.de_json_generator.jsonBakers._recipehelper.*;
import net.bottomtextdanny.de_json_generator.jsonBakers._wb_recipe.WBRecipeBaker;
import net.bottomtextdanny.de_json_generator.jsonBakers.itemmodel.ItemModel;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.EnchantJson;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.LootTableMaster;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.RandomChanceCondition;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.RandomChanceWithLootingCondition;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.SimpleCondition;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.block.BlockstateCondition;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item.MatchToolCondition;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item.TLItemPred;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.*;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block.CopyNameFunction;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block.CopyStateFunction;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block.SetContentsFunction;
import net.bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;
import net.bottomtextdanny.de_json_generator.types.base.Generator;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtilsFrontend {

	public static SRKey keyItem(char key, String itemID) {
		return new SRKey(key, "item", itemID).bake();
	}

	public static SRKey keyTag(char key, String tagID) {
		return new SRKey(key, "tag", tagID).bake();
	}

	public static SRResult result(String itemID) {
		return new SRResult(itemID).bake();
	}

	public static SRResult result(int count) {
		return new SRResult("dannys_expansion:" + GenUtils.CURRENT_NAME).count(count).bake();
	}

	public static SRResult result() {
		return new SRResult("dannys_expansion:" + GenUtils.CURRENT_NAME).bake();
	}

	public static SRResult resultUnbaked(String itemID) {
		return new SRResult(itemID);
	}

	public static RecipeShapedMaster recipe(String... craft) {
		return new RecipeShapedMaster(craft);
	}

	public static QuarkFlag rs_flag(String typeID, String flag) {
		return new QuarkFlag(typeID, flag);
	}

    public static BlockstateCondition c_blockstate(JsonMap properties) {
        return new BlockstateCondition("dannys_expansion:" + DannyGenerator.CURRENT_NAME, properties).bake();
    }

   public static MatchToolCondition c_match_tool(TLItemPred pred) {
        return new MatchToolCondition(pred).bake();
    }

   public static JsonMap map() {
	   return JsonMap.create();
   }
    
    public static EnchantJson enchantment(String id) {
		return new EnchantJson(id);
	}

   public static TLItemPred item_pred() {
        return new TLItemPred();
    }

   public static SimpleCondition c_survives_explosion() {
        return new SimpleCondition("survives_explosion").bake();
    }

   public static SimpleCondition c_killed_by_player() {
        return new SimpleCondition("killed_by_player").bake();
    }

   public static RandomChanceCondition c_random_chance(float chance) {
	   return new RandomChanceCondition(chance).bake();
   }

   public static RandomChanceWithLootingCondition c_s_random_chance_with_looting(float chance, float lootingMultiplier) {
	   return new RandomChanceWithLootingCondition(chance, lootingMultiplier).bake();
   }

   public static CopyNameFunction f_b_copy_name() {
        return new CopyNameFunction().bake();
    }

   public static CopyStateFunction f_b_copy_state(String... properties) {
	   return new CopyStateFunction("dannys_expansion:" + DannyGenerator.CURRENT_NAME).properties(properties).bake();
   }

   public static SetContentsFunction f_b_set_contents() {
        return new SetContentsFunction().bake();
    }

   public static SimpleFunction f_b_explosion_decay() {
        return new SimpleFunction("explosion_decay").bake();
    }


   public static ApplyBonusFunction f_apply_bonus_unbaked(String enchantmentID) {
	return new ApplyBonusFunction(enchantmentID);
   }

   public static SimpleFunction f_furnace_smelt() {
        return new SimpleFunction("furnace_smelt").bake();
    }

   public static SetCountFunction f_set_count(MojValue value) {
        return new SetCountFunction(value).bake();
    }

   public static SetDamageFunction f_damage(MojValue value) {
        return new SetDamageFunction(value).bake();
    }

   public static SetEnchantmentsFunction f_enchantments(EnchantJson... value) {
        return new SetEnchantmentsFunction(value).bake();
	}

   public static EnchantRandomlyFunction f_enchantment_pool(String... value) {
       return new EnchantRandomlyFunction(value).bake();
   }

   public static EnchantWithLevelsFunction f_random_enchantment_with(MojValue value) {
		return new EnchantWithLevelsFunction(value).bake();
	}

   public static MojValue value(float constant) {
        return new MojValue(new JsonPrimitive(constant));
    }

   public static MojValue uniform(float min, float max) {
        JsonObject object = new JsonObject();
        object.add("min", new JsonPrimitive(min));
        object.add("max", new JsonPrimitive(max));
        object.add("type", JsonUtilsMiddleEnd.cString("minecraft:uniform"));
        return new MojValue(object);
	}

    public static void doRecipe(RecipeMaster<?> recipe) {
		File file = Generator.createRecipe(new ResourceLocation(recipe.getResult().getItemID()).getPath());

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doWBRecipe(String name, WBRecipeBaker recipe) {
		File file = Generator.createWBRecipe(name);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.bake().jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doWBRecipe(WBRecipeBaker recipe) {
		File file = Generator.createWBRecipe(new ResourceLocation(recipe.getResult().getItemID()).getPath());
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.bake().jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doBlockState(String name, JsonBaker recipe) {
		File file = Generator.createBlockstate(name);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}

	public static void doLootTable(String object, String fileName, LootTableMaster recipe) {
		File file = Generator.createLootTable(object, new ResourceLocation(fileName).getPath());

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doItemModel(String fileName, ItemModel recipe) {
		File file = Generator.createItemModel(new ResourceLocation(fileName).getPath());
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doItemModel(String fileName, JsonModel recipe) {
		File file = Generator.createItemModel(new ResourceLocation(fileName).getPath());
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
	
	public static void doModel(String fileName, JsonModel recipe) {
		File file = Generator.createBlockModel(new ResourceLocation(fileName).getPath());
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(recipe.bake().jsonString());
			writer.close();
		} catch (IOException ignored) {}
	}
}

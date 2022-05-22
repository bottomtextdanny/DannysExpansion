package bottomtextdanny.dannys_expansion._base.lazy_recipe;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyIngredient;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyIngredientDeserializer;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyIngredientDeserializers;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyRecipe;
import bottomtextdanny.dannys_expansion._util.JsonReadUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.function.Clearable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;

public class LazyRecipeManager extends SimpleJsonResourceReloadListener {
    private static final String INGREDIENTS_TAG = "ingredients";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final BiMap<Integer, LazyRecipeType> transientIdToRecipe = HashBiMap.create();
    private final Map<ResourceLocation, LazyRecipeType> keyToRecipe = Maps.newHashMap();
    private final Map<LazyRecipeType, List<LazyRecipe>> recipes = new IdentityHashMap<>();
    private final Clearable<Map<LazyRecipeType, List<LazyRecipe>>> unmodifiableRecipes = Clearable.of(() -> {
        Map<LazyRecipeType, List<LazyRecipe>> map = new IdentityHashMap<>();
        this.recipes.forEach((type, typeRecipes) -> map.put(type, Collections.unmodifiableList(typeRecipes)));
        return map;
    });

    public LazyRecipeManager(String directory) {
        super(GSON, directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        resetRecipeCache();
        MutableBoolean wrongedTypes = new MutableBoolean(false);
        objectIn.entrySet().stream().sorted(Comparator.comparingInt((entry) -> JsonReadUtil.getInt(entry.getValue().getAsJsonObject(), "index", Integer.MAX_VALUE))).forEach((e) -> {
            ResourceLocation id = e.getKey();
            JsonElement element = e.getValue();

            ResourceLocation typeRl = new ResourceLocation(JsonReadUtil.getString(element.getAsJsonObject(), "type"));
            LazyRecipeType type = this.keyToRecipe.get(typeRl);

            if (type != null) {
                List<LazyIngredient> ingredients = Lists.newLinkedList();
                JsonArray jsonIngredients = JsonReadUtil.getJsonArray(element.getAsJsonObject(), INGREDIENTS_TAG);

                jsonIngredients.forEach(jsonElement -> readIngredient(jsonElement.getAsJsonObject(), ingredients));

                JsonObject jsonResult = JsonReadUtil.getJsonObject(element.getAsJsonObject(), "result");
                ItemStack itemStack = new ItemStack(JsonReadUtil.getItem(jsonResult, "item"));

                itemStack.setCount(JsonReadUtil.getInt(jsonResult, "count"));

                if (!this.recipes.containsKey(type)) this.recipes.put(type, Lists.newArrayList());

                LazyRecipe recipe = new LazyRecipe(this.recipes.get(type).size(), ingredients, itemStack, id);

                this.recipes.get(type).add(recipe);
            } else {
                wrongedTypes.setTrue();
                DannysExpansion.common().logger.warn("Couldn't match a lazy recipe type with string \"{}\".", typeRl);
            }
        });

        if (wrongedTypes.booleanValue()) {
            DannysExpansion.common().logger.info("\nHere is a list of all existing lazy recipe types:");
            keyToRecipe.forEach((rl, type) -> {
                DannysExpansion.common().logger.info("{} with transient id {}.", rl, type);
            });
        }
    }

    public static void readIngredient(JsonObject object, Collection<? super LazyIngredient> collection) {
        @SuppressWarnings("unchecked")
    	Map.Entry<String, JsonElement>[] elementArray = object.entrySet().toArray(Map.Entry[]::new);
    	if (elementArray.length > 0) {
		    LazyIngredientDeserializer deserializerFound = LazyIngredientDeserializers.DESERIALIZATION_BY_STR_IDENTIFIER.get(elementArray[0].getKey());
            if (deserializerFound == null) {
                deserializerFound = LazyIngredientDeserializers.DESERIALIZATION_BY_STR_IDENTIFIER.get(elementArray[0].getKey().substring(1));
            }
		    LazyIngredient ingredientType = deserializerFound.fromJSON.apply(object);
		    
		    if (ingredientType != null) {
			    collection.add(ingredientType);
		    }
	    }
    }

    public Map<LazyRecipeType, List<LazyRecipe>> getAllRecipes() {
        return this.recipes;
    }

    public void addRecipe(LazyRecipeType type, LazyRecipe recipe) {
        this.recipes.get(type).add(recipe);
    }
    
    public LazyRecipe getRecipe(LazyRecipeType type, int index) {
        if (index == -1) return LazyRecipe.EMPTY;
        return this.recipes.get(type).get(index);
    }

    public List<LazyRecipe> getRecipes(LazyRecipeType type) {
        return this.recipes.get(type);
    }

    public void resetRecipeCache() {
        for (Map.Entry<LazyRecipeType, List<LazyRecipe>> entry : this.recipes.entrySet()) {
            LazyRecipeType type = entry.getKey();
            List<LazyRecipe> list = entry.getValue();
            list.clear();
        }
        //  this.unmodifiableRecipes.assertClear();
    }

    public void tryAddType(LazyRecipeType type) {
        if (Braincell.common().hasPassedInitialization())
            throw new UnsupportedOperationException("Cannot register new lazy recipe types after mod loading phase.");
        this.keyToRecipe.putIfAbsent(type.getKey(), type);
        this.transientIdToRecipe.put(this.transientIdToRecipe.size(), type);;
        this.recipes.put(type, Lists.newArrayList());
    }

    public int getTypeId(LazyRecipeType type) {
        return this.transientIdToRecipe.inverse().get(type);
    }

    public LazyRecipeType getTypeById(int type) {
        return this.transientIdToRecipe.get(type);
    }

    public int recipeTypeSize() {
        return this.keyToRecipe.size();
    }

    public int size() {
        MutableInt size = new MutableInt(0);
        this.recipes.forEach((type, typeRecipes) -> size.addAndGet(typeRecipes.size()));
        return this.recipes.size();
    }
}

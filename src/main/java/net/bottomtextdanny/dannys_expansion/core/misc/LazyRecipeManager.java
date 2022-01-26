package net.bottomtextdanny.dannys_expansion.core.misc;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.ItemIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyDeserializers;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.function.Function;

public class LazyRecipeManager extends SimpleJsonResourceReloadListener {
    private static final String INGREDIENTS_TAG = "ingredients";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final List<LazyRecipe> recipes = Lists.newArrayList();

    public LazyRecipeManager(String directory) {
        super(GSON, directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        resetRecipeCache();
        HashMap<LazyRecipe, Integer> sortingValues = new HashMap<>(objectIn.size() / 4);

        objectIn.forEach((id, element) -> {
            List<LazyIngredient> ingredients = Lists.newLinkedList();
            JsonArray jsonIngredients = JsonReadUtil.getJsonArray(element.getAsJsonObject(), INGREDIENTS_TAG);
            jsonIngredients.forEach(jsonElement -> readIngredient(jsonElement.getAsJsonObject(), ingredients));

            JsonObject jsonResult = JsonReadUtil.getJsonObject(element.getAsJsonObject(), "result");

            ItemStack itemStack = new ItemStack(JsonReadUtil.getItem(jsonResult, "item"));
            itemStack.setCount(JsonReadUtil.getInt(jsonResult, "count"));

            LazyRecipe recipe = new LazyRecipe(this.recipes.size(), ingredients, itemStack, id);
            this.recipes.add(recipe);
            sortingValues.put(recipe,  JsonReadUtil.getInt(element.getAsJsonObject(), "index"));
        });
        List<LazyIngredient> lazyIngredientList = Lists.newLinkedList();
        for (int i = 0; i < 20; i++) {
            Item randomItem = Registry.ITEM.getRandom(new Random());
            int randomCount = DEUtil.S_RANDOM.nextInt(666);
            lazyIngredientList.add(new ItemIngredient(randomItem, randomCount));
        }
        this.recipes.sort(Comparator.comparingInt(recipe -> sortingValues.getOrDefault(recipe, Integer.MAX_VALUE)));
        addRecipe(new LazyRecipe(this.recipes.size(), lazyIngredientList, new ItemStack(Items.APPLE), new ResourceLocation(DannysExpansion.ID, "test1")));
    }

    public static void readIngredient(JsonObject object, Collection<? super LazyIngredient> collection) {
    	Map.Entry<String, JsonElement>[] elementArray = DEUtil.collectionPopulateArray(Map.Entry.class, object.entrySet(), r -> r);
    	if (elementArray.length > 0 && LazyDeserializers.DESERIALIZATION_BY_STR_IDENTIFIER.containsKey(elementArray[0].getKey())) {
		    Function<JsonObject, ? extends LazyIngredient> deserializerFound = LazyDeserializers.DESERIALIZATION_BY_STR_IDENTIFIER.get(elementArray[0].getKey()).fromJSON;
		    LazyIngredient ingredientType = deserializerFound.apply(object);
		    
		    if (ingredientType != null) {
			    collection.add(ingredientType);
		    }
	    }
    }

    public Collection<LazyRecipe> getRecipes() {
        return Collections.unmodifiableCollection(this.recipes);
    }

    public void addRecipe(LazyRecipe recipe) {
        this.recipes.add(recipe);
    }

    public void resetRecipeCache() {
        this.recipes.clear();
    }
    
    public LazyRecipe getRecipe(int index) {
        return this.recipes.get(index);
    }

    public int size() {
        return this.recipes.size();
    }
}

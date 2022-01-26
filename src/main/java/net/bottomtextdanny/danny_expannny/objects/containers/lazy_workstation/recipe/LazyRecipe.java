package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LazyRecipe {
    public static LazyRecipe EMPTY = new LazyRecipe(-1, null, ItemStack.EMPTY, null);
    private ResourceLocation resourceLocation;
    private List<LazyIngredient> ingredients;
    private ItemStack result;
    private final int index;

    public LazyRecipe(int index, List<LazyIngredient> ingredients, ItemStack result, ResourceLocation resourceLocation) {
        this.ingredients = ingredients;
        this.result = result;
        this.resourceLocation = resourceLocation;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public List<LazyIngredient> getIngredients() {
        return this.ingredients;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();
        compound.putString("data", this.resourceLocation.toString());
        compound.put("result_stack", this.result.serializeNBT());
        int[] aInt = {0};
        this.ingredients.forEach(ingredient -> {
        	CompoundTag ingredientCompound = ingredient.serialize();
        	ingredientCompound.putInt("index", ingredient.getDeserializationIdentifier().numberIdentifier);
            compound.put("ingredient_" + aInt[0], ingredientCompound);
            aInt[0]++;
        });

        return compound;
    }

    public static LazyRecipe deserialize(int index, CompoundTag compound) {
        ResourceLocation desLocation;
        List<LazyIngredient> desIngredients = Lists.newLinkedList();
        ItemStack desResult;

        desLocation = new ResourceLocation(compound.getString("data"));

        int iteration = 0;
	    while (compound.contains("ingredient_" + iteration)) {
	    	CompoundTag ingredientCompound = (CompoundTag) compound.get("ingredient_" + iteration);
	    	
	    	if (ingredientCompound.contains("index")) {
			    LazyIngredientDeserializer decodeFunc = LazyDeserializers.DESERIALIZATION_BY_INT_IDENTIFIER.get(ingredientCompound.getInt("index"));
			    desIngredients.add(decodeFunc.fromNBT.apply(ingredientCompound));
		    }
		    iteration++;
	    }

        desResult = ItemStack.of((CompoundTag) compound.get("result_stack"));

        LazyRecipe recipe = new LazyRecipe(index, desIngredients, desResult, desLocation);
        
        return recipe;
    }

//    public WorkbenchCraft copy() {
//        Set<IWBRIngredient<?>> ingredientsCopy = new LinkedHashSet<>(0);
//        for (IWBRIngredient<?> ingredient : getIngredients()) {
//            ingredientsCopy.add(ingredient.copy());
//        }
//
//        return new WorkbenchCraft(ingredientsCopy, result.copy(), resourceLocation);
//    }
}

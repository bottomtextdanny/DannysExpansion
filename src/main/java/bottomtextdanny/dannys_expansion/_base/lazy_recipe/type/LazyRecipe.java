package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import bottomtextdanny.braincell.Braincell;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class LazyRecipe {
    public static LazyRecipe EMPTY = new LazyRecipe(-1, null, ItemStack.EMPTY, null);
    private final ResourceLocation resourceLocation;
    private final List<LazyIngredient> ingredients;
    private final ItemStack result;
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
        try {
            compound.putString("data", this.resourceLocation.toString());
            compound.put("result_stack", this.result.serializeNBT());
            ListTag ingList = new ListTag();

            this.ingredients.forEach(ingredient -> {
                    CompoundTag ingredientCompound = ingredient.serialize();
                    ingredientCompound.putInt("index", ingredient.getDeserializationIdentifier().numberIdentifier);

                    ingList.add(ingredientCompound);
            });
            compound.put("ingredients", ingList);
        }
        catch (Exception ex) {
                ex.printStackTrace();
                Braincell.common().logger.info(resourceLocation);
        }

        return compound;
    }

    public static LazyRecipe deserialize(int index, CompoundTag compound) {
        ResourceLocation desLocation;
        List<LazyIngredient> desIngredients = Lists.newLinkedList();
        ItemStack desResult;

        desLocation = new ResourceLocation(compound.getString("data"));

        compound.getList("ingredients", 10).stream().map(tg -> (CompoundTag)tg).forEach((comp) -> {
            if (comp.contains("index")) {
                LazyIngredientDeserializer decodeFunc = LazyIngredientDeserializers.DESERIALIZATION_BY_INT_IDENTIFIER.get(comp.getInt("index"));
                LazyIngredient ing = decodeFunc.fromNBT.apply(comp);
                if (ing != null) {
                    desIngredients.add(ing);
                }
            }
        });

        desResult = ItemStack.of((CompoundTag) Objects.requireNonNull(compound.get("result_stack"), "lazy ingredient result couldn't be read. " + desLocation));

        return new LazyRecipe(index, desIngredients, desResult, desLocation);
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

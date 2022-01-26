package net.bottomtextdanny.danny_expannny.compatibility;

import mezz.jei.api.ingredients.IIngredientType;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.minecraft.world.item.ItemStack;

public class DEJEITypes {
    public static final IIngredientType<LazyIngredient> LAZY_INGREDIENT = () -> LazyIngredient.class;
}

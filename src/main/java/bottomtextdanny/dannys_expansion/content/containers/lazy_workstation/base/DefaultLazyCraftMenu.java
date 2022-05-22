package bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.*;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DefaultLazyCraftMenu extends LazyCraftMenu {
    private final Container resultInventory = new SimpleContainer(1);
    private final MenuType<? extends DefaultLazyCraftMenu> menu;
    private final LazyRecipeType recipeType;
    private final Slot resultInventorySlot;

    public DefaultLazyCraftMenu(int id, MenuType<? extends DefaultLazyCraftMenu> menu, LazyRecipeType type, Inventory playerInventoryIn) {
        super(menu, playerInventoryIn, id);
        this.menu = menu;
        this.recipeType = type;
        this.resultInventorySlot = this.addSlot(new Slot(this.resultInventory, 0,  30, 29) {
            @Override
            public boolean isActive() {
                return hasItem();
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player thePlayer, ItemStack stack) {
                if (DefaultLazyCraftMenu.this.resultRecipe != null) {
                    consumeIngredients(DefaultLazyCraftMenu.this.resultRecipe);
                }
                onOutputDrawn(stack);
                broadcastChanges();
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, 40 + j * 18, 156 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(this.playerInventory, k, 40 + k * 18, 214));
        }
    }

    public ItemStack getItemStackResult() {
        return this.resultInventorySlot.getItem().copy();
    }


    @Override
    public void onInventoryChange() {
        this.craftableRecipes.clear();
        this.relevantRecipes.clear();
        this.irrelevantRecipes.clear();
        List<LazyRecipe> wbRecipes = DannysExpansion.common().getLazyRecipeManager().getRecipes(this.recipeType);
        for (LazyRecipe recipe : wbRecipes) {
            boolean recipeIsCraftable = true;
            boolean atLeastOneItemMatches = false;
            for (ItemStack iterationItem : this.playerInventory.items) {
                for (LazyIngredient ingredient : recipe.getIngredients()) {
                    ingredient.collectResources(iterationItem);
                }
            }
            for (LazyIngredient ingredient : recipe.getIngredients()) {
                LazyIngredientState state = ingredient.resetCollectionData();
                if (state == LazyIngredientState.NO_MATCH) {
                    recipeIsCraftable = false;
                } else if (state == LazyIngredientState.UNFULFILLED_MATCH) {
                    recipeIsCraftable = false;
                    atLeastOneItemMatches = true;
                } else {
                    atLeastOneItemMatches = true;
                }
            }

            if (recipeIsCraftable) this.craftableRecipes.add(SolvedLazyRecipe.of(recipe, LazyCraftState.CRAFTABLE));
            else if (atLeastOneItemMatches) this.relevantRecipes.add(SolvedLazyRecipe.of(recipe, LazyCraftState.RELEVANT));
            else this.irrelevantRecipes.add(SolvedLazyRecipe.of(recipe, LazyCraftState.IRRELEVANT));
        }
    }

    public MenuType<?> getType() {
        return this.menu;
    }

    public LazyRecipeType getRecipeType() {
        return recipeType;
    }

    @Override
    public Slot resultSlot() {
        return this.resultInventorySlot;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

}

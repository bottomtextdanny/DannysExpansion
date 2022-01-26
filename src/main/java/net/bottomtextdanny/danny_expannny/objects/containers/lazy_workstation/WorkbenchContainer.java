package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation;

import net.bottomtextdanny.danny_expannny.object_tables.DEMenuTypes;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.*;
import net.bottomtextdanny.dannys_expansion.core.events.ResourceReloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WorkbenchContainer extends LazyCraftContainer {
    Slot resultInventorySlot;
    private final Container resultInventory = new SimpleContainer(1);

    public WorkbenchContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory);
    }

    public WorkbenchContainer(int id, Inventory playerInventoryIn) {
        super(DEMenuTypes.WORKBENCH.get(), playerInventoryIn, id);

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
            	if (WorkbenchContainer.this.resultRecipe != null) {
                    consumeIngredients(WorkbenchContainer.this.resultRecipe);
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

        for (LazyRecipe recipe : ResourceReloadHandler.recipeManager.getRecipes()) {
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
        return DEMenuTypes.WORKBENCH.get();
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

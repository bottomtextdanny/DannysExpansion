package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.SolvedLazyRecipe;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public abstract class LazyCraftContainer extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected LazyRecipe resultRecipe;
    protected final List<SolvedLazyRecipe>
            relevantRecipes = Lists.newLinkedList(),
            craftableRecipes = Lists.newLinkedList(),
            irrelevantRecipes = Lists.newLinkedList();

    protected LazyCraftContainer(@Nullable MenuType<? extends LazyCraftContainer> menu, Inventory playerInventory, int id) {
        super(menu, id);
        this.playerInventory = playerInventory;

    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
        return slotIn != resultSlot() && super.canTakeItemForPickAll(stack, slotIn);
    }

    public void makeResultItemstack() {
        ItemStack stack = this.resultRecipe.getResult().copy();
        resultSlot().set(stack);
        this.broadcastChanges();
    }

    public void consumeIngredients(LazyRecipe recipe) {
        final List<LazyIngredient> ingredients = recipe.getIngredients();
        final HashSet<LazyIngredient> doneMap = Sets.newHashSet();
        for (ItemStack invStack : this.playerInventory.items) {
            for (LazyIngredient ingredient : ingredients) {
                if (!doneMap.contains(ingredient)) {
                    if (ingredient.iterationOnTake(this.playerInventory, invStack)) {
                        doneMap.add(ingredient);
                    }
                }
            }
        }
        for (LazyIngredient ingredient : ingredients) {
            ingredient.resetConsumeData();
        }
    }


    public void onInventoryChange() {}

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.drop(itemstack1, false);
            }
        }
        return itemstack;
    }

    protected void onOutputDrawn(ItemStack stack) {
        this.playerInventory.setChanged();
        Connection.doClientSide(() -> {
            if (Minecraft.getInstance().screen instanceof LazyCraftScreen<?> lazyScreen) {
                lazyScreen.onOutputDrawn(stack);
            }
        });
    }

    public void setResultRecipe(LazyRecipe resultRecipe) {
        this.resultRecipe = resultRecipe;
    }

    public LazyRecipe getResultRecipe() {
        return this.resultRecipe;
    }

    public ItemStack bakeResult() {
        return this.resultRecipe.getResult().copy();
    }

    public abstract Slot resultSlot();

    public List<SolvedLazyRecipe> getCraftableRecipes() {
        return this.craftableRecipes;
    }

    public List<SolvedLazyRecipe> getRelevantRecipes() {
        return this.relevantRecipes;
    }

    public List<SolvedLazyRecipe> getIrrelevantRecipes() {
        return this.irrelevantRecipes;
    }
}

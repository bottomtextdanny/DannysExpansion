package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemStackPredicate;
import bottomtextdanny.braincell.base.BCLerp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.stream.Collectors;

public class ItemIngredient extends LazyRegistryIngredient {
    private final ItemStackPredicate itemPredicate;
    private final int count;
	private int leftCountPointer;
	private int inventoryCountPointer;
	private int screenItemPointer;
	private int countColor;

    public ItemIngredient(ItemStackPredicate item, int count) {
    	super(LazyIngredientDeserializers.ITEM_DESERIALIZER);
		this.itemPredicate = item;
        this.count = count;

    }
	
	@Override
	public void collectResources(ItemStack inventoryStack) {
		if (this.itemPredicate.test(inventoryStack) && !inventoryStack.isDamaged()) this.inventoryCountPointer += inventoryStack.getCount();
	}

	@Override
	public boolean isItemStackRelevant(ItemStack stack) {
		return this.itemPredicate.test(stack);
	}

	@Override
	public List<ItemStack> getRelevantIemStacks() {
		return this.itemPredicate.getDisplayableItems().stream().map((item) -> new ItemStack(item, this.count)).collect(Collectors.toList());
	}

	@Override
	public boolean iterationOnTake(Inventory inventory, ItemStack iteration) {
		if (this.itemPredicate.test(iteration)) {
			if (iteration.getCount() <= this.leftCountPointer) {
				inventory.removeItem(iteration);
				this.leftCountPointer -= iteration.getCount();
			} else {
				iteration.setCount(iteration.getCount() - this.leftCountPointer);
				this.leftCountPointer = 0;
			}
		}
		return this.leftCountPointer <= 0;
	}

	@Override
	public LazyIngredientState resetCollectionData() {
		final int oldCountPointer = this.inventoryCountPointer;
		this.inventoryCountPointer = 0;
		if (oldCountPointer >= this.count) {
			return LazyIngredientState.MATCH;
		}  else if (oldCountPointer > 0) {
			return LazyIngredientState.UNFULFILLED_MATCH;
		} else {
			return LazyIngredientState.NO_MATCH;
		}
	}

	@Override
	public void resetConsumeData() {
		this.inventoryCountPointer = 0;
		this.leftCountPointer = this.count;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getCountDisplayColor() {
		return this.countColor;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onScreenModification(Inventory inventory) {
		int countInInventory = 0;

		for (ItemStack stack : inventory.items) {
			if (this.itemPredicate.test(stack) && !stack.isDamaged()) {
				countInInventory += stack.getCount();
			}
		}

		if (countInInventory >= this.count) {
			this.countColor = 0x00FF00;
		} else if (countInInventory > 0) {
			double step = (double)countInInventory / (double)this.count;
			this.countColor = (int) BCLerp.get(step, 256.0, 196.0) << 16;
			this.countColor += (int) BCLerp.get(step, 64.0, 256.0) << 8;
		} else {
			this.countColor = 0xDD0000;
		}

	}
	
	@Override
	public boolean filter(Item filterBy) {
		return this.itemPredicate.test(new ItemStack(filterBy));
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
		List<Item> list = this.itemPredicate.getDisplayableItems();
		if (list.isEmpty()) {
			return Items.BARRIER;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			this.screenItemPointer = (this.screenItemPointer + 1) % (15 * list.size() - 1);
			return list.get(this.screenItemPointer / 15);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		return this.itemPredicate.tooltip(flag);
	}

    public int getCount() {
        return this.count;
    }

    public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();
		CompoundTag predCompound = new CompoundTag();
		this.itemPredicate.serialize(predCompound);
        compound.putInt("pred", this.itemPredicate.getType().getId());
		compound.put("pred_e", predCompound);
        compound.putInt("count", this.count);
        return compound;
    }

    public ItemIngredient copy() {
        return  new ItemIngredient(this.itemPredicate, getCount());
    }
}

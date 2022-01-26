package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
import net.bottomtextdanny.braincell.underlying.util.BCLerp;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.LazyRegistryIngredient;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemIngredient extends LazyRegistryIngredient {
    private final Item item;
    private final int count;
	private int leftCountPointer;
	private int inventoryCountPointer;
	@OnlyHandledOnClient
	private int countColor;

    public ItemIngredient(Item item, int count) {
    	super(LazyDeserializers.ITEM_DESERIALIZER);
        this.item = item;
        this.count = count;
    }
	
	@Override
	public void collectResources(ItemStack inventoryStack) {
		if (inventoryStack.getItem() == this.item && !inventoryStack.isDamaged()) this.inventoryCountPointer += inventoryStack.getCount();
	}

	@Override
	public boolean isItemStackRelevant(ItemStack stack) {
		return stack.getItem() == this.item;
	}

	@Override
	public List<ItemStack> getRelevantIemStacks() {
		return List.of(new ItemStack(this.item, this.count));
	}

	@Override
	public boolean iterationOnTake(Inventory inventory, ItemStack iteration) {
		if (iteration.getItem() == this.item) {
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
			if (stack.getItem() == this.item && !stack.isDamaged()) {
				countInInventory += stack.getCount();
			}
		}

		if (countInInventory >= this.count) {
			this.countColor = 0x00FF00;
		} else if (countInInventory > 0) {
			double step = (double)countInInventory / (double)this.count;
			this.countColor = (int)BCLerp.get(step, 256.0, 196.0) << 16;
			this.countColor += (int)BCLerp.get(step, 64.0, 256.0) << 8;
		} else {
			this.countColor = 0xDD0000;
		}

	}
	
	@Override
	public boolean filter(Item filterBy) {
		return this.item == filterBy;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
		return this.item;
	}

	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		return new ItemStack(this.item, this.count).getTooltipLines(ClientInstance.player(), flag);
	}

	public Item getItem() {
        return this.item;
    }

    public int getCount() {
        return this.count;
    }

    public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("item", Registry.ITEM.getId(this.item));
        compound.putInt("count", this.count);
        return compound;
    }
	
	public ItemIngredient deserialize(CompoundTag compound) {
        Item desItem;
        int desCount;

        desItem = Item.byId(compound.getInt("item"));
        desCount = compound.getInt("count");

        return new ItemIngredient(desItem, desCount);
    }

    public ItemIngredient copy() {
        return  new ItemIngredient(getItem(), getCount());
    }
}

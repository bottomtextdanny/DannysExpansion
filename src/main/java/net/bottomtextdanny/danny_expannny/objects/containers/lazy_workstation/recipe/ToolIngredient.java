package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
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

public class ToolIngredient extends LazyRegistryIngredient {
	private static final String COUNT_DISPLAY = "(tool)";
    private final Item tool;
	private final int damage;
	private int leftDamagePointer;
	private int inventoryDamagePointer;
	private @OnlyHandledOnClient int countColor;

    public ToolIngredient(Item item, int damage) {
    	super(LazyDeserializers.TOOL_DESERIALIZER);
        this.tool = item;
		this.damage = damage;
    }
	
	@Override
	public void collectResources(ItemStack inventoryStack) {
		if (inventoryStack.getItem() == this.tool) {
			int stackDurability = inventoryStack.getMaxDamage() - inventoryStack.getDamageValue();
			this.inventoryDamagePointer += stackDurability;
		}
	}

	@Override
	public boolean isItemStackRelevant(ItemStack stack) {
		return stack.getItem() == this.tool;
	}

	@Override
	public List<ItemStack> getRelevantIemStacks() {
		return List.of(new ItemStack(this.tool));
	}

	@Override
	public boolean iterationOnTake(Inventory inventory, ItemStack iteration) {
		if (iteration.getItem() == this.tool) {
			int stackDurability = iteration.getMaxDamage() - iteration.getDamageValue();
			if (stackDurability <= this.leftDamagePointer) {
				inventory.removeItem(iteration);
				this.leftDamagePointer -= stackDurability;
			} else {
				iteration.setDamageValue(iteration.getDamageValue() + this.leftDamagePointer);
				this.leftDamagePointer = 0;
			}
		}
		return this.leftDamagePointer <= 0;
	}

	@Override
	public LazyIngredientState resetCollectionData() {
		final int oldDamagePointer = this.inventoryDamagePointer;
		this.inventoryDamagePointer = 0;
		if (oldDamagePointer >= this.damage) {
			return LazyIngredientState.MATCH;
		}  else if (oldDamagePointer > 0) {
			return LazyIngredientState.UNFULFILLED_MATCH;
		} else {
			return LazyIngredientState.NO_MATCH;
		}
	}

	@Override
	public void resetConsumeData() {
		this.inventoryDamagePointer = 0;
		this.leftDamagePointer = this.damage;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onScreenModification(Inventory inventory) {
		int totalDurability = 0;

		for (ItemStack stack : inventory.items) {
			if (stack.getItem() == this.tool) {
				int stackDurability = stack.getMaxDamage() - stack.getDamageValue();
				totalDurability += stackDurability;
				if (totalDurability >= this.damage) {
					this.countColor = 0x00FF00;
					return;
				}
			}
		}
		this.countColor = 0xFFFFFF;
	}

	@Override
	public boolean filter(Item filterBy) {
		return this.tool == filterBy;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getCountDisplay() {
		return COUNT_DISPLAY;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getCountDisplayColor() {
		return this.countColor;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
		return this.tool;
	}

	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		return new ItemStack(this.tool).getTooltipLines(ClientInstance.player(), flag);
	}

	public Item getTool() {
        return this.tool;
    }

    public int getCount() {
        return 1;
    }

    public CompoundTag serialize() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("tool", Registry.ITEM.getId(this.tool));
		compound.putInt("damage", this.damage);
        return compound;
    }
	
	public ToolIngredient deserialize(CompoundTag compound) {
        Item desItem;
        int desDamage;

        desItem = Item.byId(compound.getInt("tool"));
        desDamage = compound.getInt("damage");

        return new ToolIngredient(desItem, desDamage);
    }

    public ToolIngredient copy() {
        return  new ToolIngredient(getTool(), getCount());
    }
}

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
import java.util.function.Function;
import java.util.stream.Collectors;

public class ToolIngredient extends LazyRegistryIngredient {
	private static final Function<String, String> COUNT_DISPLAY = s -> " (" + s + " uses) ";
    private final ItemStackPredicate toolPredicate;
	private final int damage;
	private int leftDamagePointer;
	private int inventoryDamagePointer;
	private int screenItemPointer;
	private int countColor;

    public ToolIngredient(ItemStackPredicate item, int damage) {
    	super(LazyIngredientDeserializers.TOOL_DESERIALIZER);
        this.toolPredicate = item;
		this.damage = damage;
    }
	
	@Override
	public void collectResources(ItemStack inventoryStack) {
		if (this.toolPredicate.test(inventoryStack)) {
			int stackDurability = inventoryStack.getMaxDamage() - inventoryStack.getDamageValue();
			this.inventoryDamagePointer += stackDurability;
		}
	}

	@Override
	public boolean isItemStackRelevant(ItemStack stack) {
		return this.toolPredicate.test(stack);
	}

	@Override
	public List<ItemStack> getRelevantIemStacks() {
		return this.toolPredicate.getDisplayableItems().stream().map(ItemStack::new).collect(Collectors.toList());
	}

	@Override
	public boolean iterationOnTake(Inventory inventory, ItemStack iteration) {
		if (this.toolPredicate.test(iteration)) {
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
		int usesInInventory = 0;

		for (ItemStack stack : inventory.items) {
			if (this.toolPredicate.test(stack)) {
				usesInInventory += stack.getMaxDamage() - stack.getDamageValue();
			}
		}

		if (usesInInventory >= this.damage) {
			this.countColor = 0x00FF00;
		} else if (usesInInventory > 0) {
			double step = (double)usesInInventory / (double)this.damage;
			this.countColor = (int) BCLerp.get(step, 256.0, 196.0) << 16;
			this.countColor += (int)BCLerp.get(step, 64.0, 256.0) << 8;
		} else {
			this.countColor = 0xDD0000;
		}
	}

	@Override
	public boolean filter(Item filterBy) {
		return this.toolPredicate.test(new ItemStack(filterBy));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getCountDisplay() {
		return COUNT_DISPLAY.apply(String.valueOf(this.damage));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getCountDisplayColor() {
		return this.countColor;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public Item screenItem() {
		List<Item> list = this.toolPredicate.getDisplayableItems();
		if (list.isEmpty()) {
			return Items.BARRIER;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			this.screenItemPointer = (this.screenItemPointer + 1) % (15 * list.size() - 1);
			return list.get(this.screenItemPointer / 15);
		}
	}

	@Override
	public List<Component> tooltip(TooltipFlag flag) {
		return this.toolPredicate.tooltip(flag);
	}

	@Override
	public int getCount() {
		return 1;
	}

	public CompoundTag serialize() {
		CompoundTag compound = new CompoundTag();
		CompoundTag predCompound = new CompoundTag();
		this.toolPredicate.serialize(predCompound);
		compound.putInt("pred", this.toolPredicate.getType().getId());
		compound.put("pred_e", predCompound);
		compound.putInt("damage", this.damage);
		return compound;
    }

    public ToolIngredient copy() {
        return new ToolIngredient(this.toolPredicate, this.damage);
    }
}

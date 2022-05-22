package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public interface LazyIngredient {

	void collectResources(ItemStack inventoryStack);

	boolean isItemStackRelevant(ItemStack stack);

	List<ItemStack> getRelevantIemStacks();
	
	boolean iterationOnTake(Inventory inventory, ItemStack iteration);

	LazyIngredientState resetCollectionData();

	void resetConsumeData();

	boolean filter(Item filterBy);
	
	@OnlyIn(Dist.CLIENT)
	Item screenItem();
	
	@OnlyIn(Dist.CLIENT)
	List<Component> tooltip(TooltipFlag flag);

	//@OnlyIn(Dist.CLIENT)
	//	default List<Component> tooltip(ItemStack screenItemStack, Inventory inventory, TooltipFlag entry) {
	//		return screenItemStack.getTooltipLines(inventory.player, entry);
	//	}
	int getCount();

	@OnlyIn(Dist.CLIENT)
	default String getCountDisplay() {
		return "("+getCount()+")";
	}

	@OnlyIn(Dist.CLIENT)
	default List<Component> getCountTooltip() {
		return new ArrayList<>(0);
	}

	@OnlyIn(Dist.CLIENT)
	int getCountDisplayColor();

	@OnlyIn(Dist.CLIENT)
	default void onScreenModification(Inventory inventory) {}
	
	CompoundTag serialize();
	
	LazyIngredientDeserializer getDeserializationIdentifier();
}

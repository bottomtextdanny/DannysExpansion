package bottomtextdanny.dannys_expansion.content.containers;

import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.accessory.IAccessory;
import bottomtextdanny.braincell.mod.capability.player.accessory.IJumpQueuerAccessory;
import bottomtextdanny.braincell.mod.capability.player.accessory.IQueuedJump;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IAccessoryInventory extends Container, StackedContentsCompatible {
	
	default void removeAccessoryUnsafe(int index) {
		CoreAccessory accessory = this.getCapability().getCoreAccessoryList().get(index);
		if (accessory instanceof IJumpQueuerAccessory) {
			IJumpQueuerAccessory jumpProvider = (IJumpQueuerAccessory) accessory;
			for (IQueuedJump iQueuedJump : jumpProvider.provideJumps()) {
				getBraincellCapability().getJumpSet().remove(iQueuedJump);
			}
			if (getBraincellCapability().isAccessoryTypeCurrent(accessory.getClass())) {
				getBraincellCapability().deleteFromCurrentAccessorySet(accessory.getClass());
			}
		}
		this.getCapability().getCoreAccessoryList().set(index, CoreAccessory.EMPTY);
	}
	
	default void checkToAddJumpInterface(IAccessory accessory) {
		if (accessory instanceof IJumpQueuerAccessory) {
			IJumpQueuerAccessory jumpProvider = (IJumpQueuerAccessory) accessory;
			for (IQueuedJump iQueuedJump : jumpProvider.provideJumps()) {
				getBraincellCapability().getJumpSet().add(iQueuedJump);
			}
		}
	}
	
	@Override
	default void fillStackedContents(StackedContents helper) {
		for(ItemStack itemstack : getStackContents()) {
			helper.accountStack(itemstack);
		}
	}

	DEAccessoryModule getCapability();

	BCAccessoryModule getBraincellCapability();
	
	List<ItemStack> getStackContents();
}

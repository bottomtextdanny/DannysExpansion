package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation;

import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IJumpQueuerAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IQueuedJump;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
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
				getCapability().getJumpSet().remove(iQueuedJump);
			}
		}
		this.getCapability().getCoreAccessoryList().set(index, CoreAccessory.EMPTY);
	}
	
	default void checkToAddJumpInterface(IAccessory accessory) {
		if (accessory instanceof IJumpQueuerAccessory) {
			IJumpQueuerAccessory jumpProvider = (IJumpQueuerAccessory) accessory;
			for (IQueuedJump iQueuedJump : jumpProvider.provideJumps()) {
				getCapability().getJumpSet().add(iQueuedJump);
			}
		}
	}
	
	@Override
	default void fillStackedContents(StackedContents helper) {
		for(ItemStack itemstack : getStackContents()) {
			helper.accountStack(itemstack);
		}
	}

	PlayerAccessoryModule getCapability();
	
	List<ItemStack> getStackContents();
}

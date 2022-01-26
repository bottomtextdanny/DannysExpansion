package net.bottomtextdanny.danny_expannny.objects.containers;

import net.bottomtextdanny.danny_expannny.accessory.*;
import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.IAccessoryInventory;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.ItemUtil;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class AccessoryInventory implements IAccessoryInventory {
    private final PlayerAccessoryModule capability;
    private final int slotsCount;
    private final NonNullList<ItemStack> contentList = NonNullList.withSize(5, ItemStack.EMPTY);

    public AccessoryInventory(PlayerAccessoryModule cap, int space) {
        this.capability = cap;
        this.slotsCount = space;
    }

    public ItemStack getItem(int index) {
        return index >= 0 && index < this.contentList.size() ? this.contentList.get(index): ItemStack.EMPTY;
    }

    public ItemStack removeItem(int index, int count) {
        ItemStack itemstack = ContainerHelper.removeItem(this.contentList, index, count);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        } else {
            removeAccessoryUnsafe(index);
        }

        return itemstack;
    }

    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemstack = this.contentList.get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            removeAccessoryUnsafe(index);
            this.contentList.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    public void setItem(int index, ItemStack stack) {
        if (getItem(index).getItem() != stack.getItem()) {
            CoreAccessory overwrite = ItemUtil.getAccessory(this.capability.getHolder(), stack.getItem());
            this.capability.getCoreAccessoryList().set(index, overwrite);
            this.capability.addToCurrentAccessorySet(overwrite);
            checkToAddJumpInterface(overwrite);
	
	        List<ModifierType> attributes = AccessoryKey.getAttributeData().get(overwrite.getKey()).modifierIterable;

            overwrite.prepare(index);
            for (ModifierType attribute : attributes) {
                this.capability.addAccessoryAttributeModifier(attribute, overwrite);
            }
        }

        this.contentList.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (stack.isEmpty()) removeAccessoryUnsafe(index);

        this.setChanged();
    }

    public int getContainerSize() {
        return this.slotsCount;
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.contentList) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void setChanged() {
    }

    public boolean stillValid(Player player) {
        return true;
    }

    public void clearContent() {
        this.contentList.clear();
        this.capability.getCoreAccessoryList().clear();
        this.setChanged();
    }

    public String toString() {
        return this.contentList.stream().filter(p_223371_0_ -> {
            return !p_223371_0_.isEmpty();
        }).collect(Collectors.toList()).toString();
    }
    
    public void removeAccessoryUnsafe(int index) {
        CoreAccessory accessory = this.capability.getCoreAccessoryList().get(index);

        if (accessory instanceof IJumpQueuerAccessory) {
            IJumpQueuerAccessory jumpProvider = (IJumpQueuerAccessory) accessory;
            for (IQueuedJump iQueuedJump : jumpProvider.provideJumps()) {
                this.capability.getJumpSet().remove(iQueuedJump);
            }
        }

        if (!AccessoryKey.isEmpty(accessory.getKey())) {
            this.capability.deleteFromCurrentAccessorySet(accessory.getClass());

            List<ModifierType> attributes = AccessoryKey.getAttributeData().get(accessory.getKey()).modifierIterable;

            for (ModifierType attribute : attributes) {
                this.capability.deleteAccessoryAttributeModifier(attribute, accessory);
            }

            this.capability.getCoreAccessoryList().set(index, CoreAccessory.EMPTY);
        }
    }

    public void checkToAddJumpInterface(CoreAccessory accessory) {
        if (accessory instanceof IJumpQueuerAccessory jumpProvider) {
            for (IQueuedJump iQueuedJump : jumpProvider.provideJumps()) {
                this.capability.getJumpSet().add(iQueuedJump);
            }
        }
    }
	
	public PlayerAccessoryModule getCapability() {
		return this.capability;
	}

	public List<ItemStack> getStackContents() {
		return this.contentList;
	}
}

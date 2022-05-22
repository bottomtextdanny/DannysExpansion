package bottomtextdanny.dannys_expansion.content.containers;

import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import bottomtextdanny.dannys_expansion.content.items.AccessoryItem;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.BCPlayerCapability;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class AccessoryInventory implements IAccessoryInventory {
    private final DEAccessoryModule capability;
    private final int slotsCount;
    private final NonNullList<ItemStack> contentList = NonNullList.withSize(10, ItemStack.EMPTY);

    public AccessoryInventory(DEAccessoryModule capability, int space) {
        this.capability = capability;
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
            final BCAccessoryModule bcAccessoryModule = getBraincellCapability();
            Item item = stack.getItem();
            CoreAccessory accessory;

            if (item instanceof AccessoryItem) {
                accessory = ((AccessoryItem)item).accessoryKey.create(this.capability.getHolder());
            } else {
                accessory = CoreAccessory.EMPTY;
            }

            CoreAccessory overwrite = accessory;
            bcAccessoryModule.onAccessoryAddition(overwrite);
            this.capability.getCoreAccessoryList().set(index, overwrite);
            overwrite.prepare(index);
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
        final BCAccessoryModule bcAccessoryModule = getBraincellCapability();
        CoreAccessory accessory = this.capability.getCoreAccessoryList().get(index);

        bcAccessoryModule.onAccessoryRemoval(accessory);

        if (!AccessoryKey.isEmpty(accessory.getKey())) {
            this.capability.getCoreAccessoryList().set(index, CoreAccessory.EMPTY);
        }
    }
	
	public DEAccessoryModule getCapability() {
		return this.capability;
	}

    @Override
    public BCAccessoryModule getBraincellCapability() {
        return CapabilityHelper.get(this.capability.getHolder(), BCPlayerCapability.TOKEN).getAccessories();
    }

    public List<ItemStack> getStackContents() {
		return this.contentList;
	}
}

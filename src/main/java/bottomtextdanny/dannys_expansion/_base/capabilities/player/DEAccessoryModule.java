package bottomtextdanny.dannys_expansion._base.capabilities.player;

import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import bottomtextdanny.dannys_expansion.content.containers.AccessoryInventory;
import bottomtextdanny.dannys_expansion.content.containers.IAccessoryInventory;
import bottomtextdanny.braincell.mod.capability.CapabilityModule;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DEAccessoryModule extends CapabilityModule<Player, PlayerCapability> {
    public static final int CORE_ACCESSORIES_SIZE = 10;
    public static final String ACCESSORY_TAG = "acc";
    private final List<CoreAccessory> coreAccessoryList;
    private final IAccessoryInventory inventory;

    public DEAccessoryModule(PlayerCapability capability) {
        super("accessories", capability);
        this.coreAccessoryList = NonNullList.withSize(CORE_ACCESSORIES_SIZE, CoreAccessory.EMPTY);
        this.inventory = new AccessoryInventory(this, CORE_ACCESSORIES_SIZE);
    }

    public void setAccessoryStack(int index, ItemStack itemStack) {
        this.inventory.setItem(index, itemStack);
    }
    
    public IAccessoryInventory getAccessories() {
        return this.inventory;
    }

    public List<CoreAccessory> getCoreAccessoryList() {
        return this.coreAccessoryList;
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {
        int containerSize = this.inventory.getContainerSize();
        ListTag list = new ListTag();
        for (int i = 0; i < containerSize; i++) {
            CompoundTag itemNBT = this.inventory.getStackContents().get(i).serializeNBT();
            CompoundTag accessoryNBT = this.coreAccessoryList.get(i).write();

            list.add(itemNBT);
            list.add(accessoryNBT);
        }
        nbt.put(ACCESSORY_TAG, list);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        int containerSize = this.inventory.getContainerSize();
        ListTag list = nbt.getList(ACCESSORY_TAG, 10);
        for (int i = 0; i < containerSize; i++) {
            CompoundTag itemNBT = list.getCompound(i * 2);
            ItemStack stack = ItemStack.of(itemNBT);

            this.inventory.setItem(i, stack);
            this.coreAccessoryList.get(i).read(list.getCompound(i * 2 + 1));
        }
    }
}

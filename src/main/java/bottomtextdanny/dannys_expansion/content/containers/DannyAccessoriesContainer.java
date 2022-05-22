package bottomtextdanny.dannys_expansion.content.containers;

import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.tables.DEMenuTypes;
import bottomtextdanny.dannys_expansion.content.items.AccessoryItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DannyAccessoriesContainer extends AbstractContainerMenu {
    private final Container accessories;
    private final Inventory playerInventory;

    public DannyAccessoriesContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, playerInventory.player);
    }

    public DannyAccessoriesContainer(int id, Inventory playerInventory, Player player) {
        super(DEMenuTypes.DANNY_ACCESSORIES.get(), id);
        DEAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);

        this.playerInventory = playerInventory;

        this.accessories = accessoryModule.getAccessories();

        for (int i = 0; i < 5; i++) {
            this.addSlot(new Slot(this.accessories, i, 16 + i * 32, 36) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    if (!(stack.getItem() instanceof AccessoryItem)) return false;

                    for (int j = 0; j < 5; j++) {
                        if (DannyAccessoriesContainer.this.accessories.getItem(j).getItem() == stack.getItem()) {
                            return false;
                        }
                    }

                    return super.mayPlace(stack);
                }
            });
        }

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 30 + 36 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 88 + 36));
        }
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 5) {
                if (!this.moveItemStackTo(itemstack1, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 5, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    public MenuType<?> getType() {
        return DEMenuTypes.DANNY_ACCESSORIES.get();
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}

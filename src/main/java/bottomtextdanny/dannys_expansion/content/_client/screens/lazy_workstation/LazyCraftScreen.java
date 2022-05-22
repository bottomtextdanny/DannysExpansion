package bottomtextdanny.dannys_expansion.content._client.screens.lazy_workstation;

import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.LazyCraftMenu;
import bottomtextdanny.braincell.mod.gui.BCContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LazyCraftScreen<T extends LazyCraftMenu> extends BCContainerScreen<T> {
    protected int timesInventoryChanged;
    protected Inventory inventory;

    public LazyCraftScreen(T container, Inventory inventory, Component nameComponent) {
        super(container, inventory, nameComponent);
        this.timesInventoryChanged = inventory.getTimesChanged();
        this.inventory = inventory;
    }

    public void onOutputDrawn(ItemStack drawnItemStack) {}
}

package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LazyCraftScreen<T extends LazyCraftContainer> extends AbstractContainerScreen<T> {
    protected int timesInventoryChanged;
    protected Inventory inventory;

    public LazyCraftScreen(T container, Inventory inventory, Component nameComponent) {
        super(container, inventory, nameComponent);
        this.timesInventoryChanged = inventory.getTimesChanged();
        this.inventory = inventory;
    }

    public void onOutputDrawn(ItemStack drawnItemStack) {}
}

package net.bottomtextdanny.dannys_expansion.common;

import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class DECreativeTab extends CreativeModeTab {

    public DECreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(DEItems.ICON.get());
    }
}

package net.bottomtextdanny.dannys_expansion.core;

import net.bottomtextdanny.dannys_expansion.common.DECreativeTab;
import net.minecraft.world.item.CreativeModeTab;

public class ModManager extends AbstractModManager {
    private final CreativeModeTab tab;

    public ModManager(String id) {
        super(id);
        this.tab = new DECreativeTab(id);
    }

    public CreativeModeTab getTab() {
        return this.tab;
    }
}

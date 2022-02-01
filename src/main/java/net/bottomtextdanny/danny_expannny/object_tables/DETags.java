package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class DETags {

    public static final Tag.Named<Block> NEEDS_ENDER_TOOL = createBlock("emossence_placeable_on");

    public static final Tag.Named<Block> EMOSSENCE_PLACEABLE_ON = createBlock("emossence_placeable_on");

    public static final Tag.Named<Block> MAGMA_GULPER_DANGER_BLOCKS = createBlock("magma_gulper_danger_blocks");

    private static Tag.Named<Block> createBlock(String name) {
        return BlockTags.bind(DannysExpansion.ID + ":" + name);
    }

    private static Tag.Named<Item> createItem(String name) {
        return ItemTags.bind(DannysExpansion.ID + ":" + name);
    }

    public static void loadClass() {
    }
}

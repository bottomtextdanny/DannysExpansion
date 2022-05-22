package bottomtextdanny.dannys_expansion.tables.items;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.tables.DEBlocks;
import bottomtextdanny.dannys_expansion.content.items.BCChestItem;
import bottomtextdanny.dannys_expansion.content.items.TestDummyItem;
import bottomtextdanny.dannys_expansion.content.items.WorkbenchItem;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static bottomtextdanny.dannys_expansion.tables.items.DEItems.defer;

public final class DEBuildingItems {

    //*\\*//*\\*//*\\WORK STATIONS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> WORKBENCH = defer(DEItemCategory.WORK_STATIONS, "workbench", () ->
            new WorkbenchItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<BlockItem> ENGINEERING_STATION = defer(DEItemCategory.WORK_STATIONS, "engineering_station", () ->
            new BlockItem(DEBlocks.ENGINEERING_STATION.get(), new Item.Properties().tab(DannysExpansion.TAB)));
    //*\\*//*\\*//*\\WORK STATIONS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\MISC\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
//    public static final Wrap<BCChestItem> SEA_CHEST = deferChestItem(DEItemCategory.PRIMARY_MISC, "sea_chest", DEBlocks.SEA_CHEST::get);
//    public static final Wrap<BCChestItem> TRAPPED_SEA_CHEST = deferChestItem(DEItemCategory.PRIMARY_MISC, "trapped_sea_chest", () -> DEBlocks.SEA_CHEST.get().trapped());
    public static final Wrap<TestDummyItem> TEST_DUMMY = defer(DEItemCategory.PRIMARY_MISC, "test_dummy", () ->
            new TestDummyItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<BlockItem> PLANT_MATTER = deferBlockItem(DEItemCategory.PRIMARY_MISC, "plant_matter", DEBlocks.PLANT_MATTER::get);
    //*\\*//*\\*//*\\MISC\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\THATCH*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> THATCH = deferBlockItem(DEItemCategory.THATCH_BUILDING, "thatch", DEBlocks.THATCH::get);
    public static final Wrap<BlockItem> THATCH_SLAB = deferBlockItem(DEItemCategory.THATCH_BUILDING, "thatch_slab", () -> DEBlocks.THATCH.get().slab());
    public static final Wrap<BlockItem> THATCH_STAIRS = deferBlockItem(DEItemCategory.THATCH_BUILDING, "thatch_stairs", () -> DEBlocks.THATCH.get().stairs());
    //*\\*//*\\*//*\\THATCH*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\ICE BUILDING*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> ICE_BRICKS = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_bricks", DEBlocks.ICE_BRICKS::get);
    public static final Wrap<BlockItem> ICE_BRICK_SLAB = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_brick_slab", () -> DEBlocks.ICE_BRICKS.get().slab());
    public static final Wrap<BlockItem> ICE_BRICK_STAIRS = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_brick_stairs", () -> DEBlocks.ICE_BRICKS.get().stairs());
    public static final Wrap<BlockItem> ICE_DOOR = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_door", DEBlocks.ICE_DOOR::get);
    public static final Wrap<BlockItem> ICE_TRAPDOOR = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_trapdoor", DEBlocks.ICE_TRAPDOOR::get);
    public static final Wrap<BCChestItem> ICE_CHEST = deferChestItem(DEItemCategory.ICE_BUILDING, "ice_chest", DEBlocks.ICE_CHEST::get);
    public static final Wrap<BCChestItem> TRAPPED_ICE_CHEST = deferChestItem(DEItemCategory.ICE_BUILDING, "trapped_ice_chest", () -> DEBlocks.ICE_CHEST.get().trapped());
    public static final Wrap<BlockItem> ICE_BUTTON = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_button", DEBlocks.ICE_BUTTON::get);
    public static final Wrap<BlockItem> ICE_PRESSURE_PLATE = deferBlockItem(DEItemCategory.ICE_BUILDING, "ice_pressure_plate", DEBlocks.ICE_PRESSURE_PLATE::get);
    //*\\*//*\\*//*\\ICE BUILDING*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    public static Wrap<BlockItem> deferBlockItem(DEItemCategory category, String name, Supplier<? extends Block> block) {
        return defer(category, name, () -> new BlockItem(block.get(), new Item.Properties().tab(DannysExpansion.TAB)));
    }

    public static Wrap<BCChestItem> deferChestItem(DEItemCategory category, String name, Supplier<? extends BCChestBlock> block) {
        return defer(category, name, () -> new BCChestItem(block.get(), new Item.Properties().tab(DannysExpansion.TAB)));
    }

    public static void call() {}
}

package net.bottomtextdanny.danny_expannny.object_tables.items;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.object_tables.DEBoatTypes;
import net.bottomtextdanny.danny_expannny.objects.items.TestDummyItem;
import net.bottomtextdanny.danny_expannny.objects.items.WorkbenchItem;
import net.bottomtextdanny.braincell.mod.world.builtin_items.BCBoatItem;
import net.bottomtextdanny.danny_expannny.objects.items.BCChestItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static net.bottomtextdanny.danny_expannny.object_tables.items.DEItemCategory.*;
import static net.bottomtextdanny.danny_expannny.object_tables.items.DEItems.defer;

public final class DEBuildingItems {
    //*\\*//*\\*//*\\WORK STATIONS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> WORKBENCH = defer(WORK_STATIONS, "workbench", () ->
            new WorkbenchItem(new Item.Properties().tab(DannysExpansion.TAB)));
    //*\\*//*\\*//*\\WORK STATIONS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\MISC\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BCChestItem> SEA_CHEST = deferChestItem(PRIMARY_MISC, "sea_chest", () -> DEBlocks.SEA_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_SEA_CHEST = deferChestItem(PRIMARY_MISC, "trapped_sea_chest", () -> DEBlocks.SEA_CHEST.get().trapped());
    public static final Wrap<TestDummyItem> TEST_DUMMY = defer(PRIMARY_MISC, "test_dummy", () ->
            new TestDummyItem(new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<BlockItem> PLANT_MATTER = deferBlockItem(PRIMARY_MISC, "plant_matter", () -> DEBlocks.PLANT_MATTER.get());
    //*\\*//*\\*//*\\MISC\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\ICE BUILDING*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> ICE_BRICKS = deferBlockItem(ICE_BUILDING, "ice_bricks", () -> DEBlocks.ICE_BRICKS.get());
    public static final Wrap<BlockItem> ICE_BRICK_SLAB = deferBlockItem(ICE_BUILDING, "ice_brick_slab", () -> DEBlocks.ICE_BRICKS.get().slab());
    public static final Wrap<BlockItem> ICE_BRICK_STAIRS = deferBlockItem(ICE_BUILDING, "ice_brick_stairs", () -> DEBlocks.ICE_BRICKS.get().stairs());
    public static final Wrap<BlockItem> ICE_DOOR = deferBlockItem(ICE_BUILDING, "ice_door", () -> DEBlocks.ICE_DOOR.get());
    public static final Wrap<BlockItem> ICE_TRAPDOOR = deferBlockItem(ICE_BUILDING, "ice_trapdoor", () -> DEBlocks.ICE_TRAPDOOR.get());
    public static final Wrap<BCChestItem> ICE_CHEST = deferChestItem(ICE_BUILDING, "ice_chest", () -> DEBlocks.ICE_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_ICE_CHEST = deferChestItem(ICE_BUILDING, "trapped_ice_chest", () -> DEBlocks.ICE_CHEST.get().trapped());
    public static final Wrap<BlockItem> ICE_BUTTON = deferBlockItem(ICE_BUILDING, "ice_button", () -> DEBlocks.ICE_BUTTON.get());
    public static final Wrap<BlockItem> ICE_PRESSURE_PLATE = deferBlockItem(ICE_BUILDING, "ice_pressure_plate", () -> DEBlocks.ICE_PRESSURE_PLATE.get());
    //*\\*//*\\*//*\\ICE BUILDING*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\BRIOSTONE*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> COBBLED_BRIOSTONE = deferBlockItem(BRIOSTONE_BUILDING, "cobbled_briostone", () -> DEBlocks.COBBLED_BRIOSTONE.get());
    public static final Wrap<BlockItem> BRIOSTONE_CRAGS = deferBlockItem(BRIOSTONE_BUILDING, "briostone_crags", () -> DEBlocks.BRIOSTONE_CRAGS.get());
    public static final Wrap<BlockItem> BRIOSTONE_BRICKS = deferBlockItem(BRIOSTONE_BUILDING, "briostone_bricks", () -> DEBlocks.BRIOSTONE_BRICKS.get());
    public static final Wrap<BlockItem> BRIOSTONE_BRICK_STAIRS = deferBlockItem(BRIOSTONE_BUILDING, "briostone_brick_stairs", () -> DEBlocks.BRIOSTONE_BRICKS.get().stairs());
    public static final Wrap<BlockItem> BRIOSTONE_BRICK_SLAB = deferBlockItem(BRIOSTONE_BUILDING, "briostone_brick_slab", () -> DEBlocks.BRIOSTONE_BRICKS.get().slab());
    public static final Wrap<BlockItem> CHISELED_BRIOSTONE_BRICKS = deferBlockItem(BRIOSTONE_BUILDING, "chiseled_briostone_bricks", () -> DEBlocks.CHISELED_BRIOSTONE_BRICKS.get());
    public static final Wrap<BlockItem> BRIOSTONE_PILLAR = deferBlockItem(BRIOSTONE_BUILDING, "briostone_pillar", () -> DEBlocks.BRIOSTONE_PILLAR.get());
    public static final Wrap<BCChestItem> BRIOSTONE_CHEST = deferChestItem(BRIOSTONE_BUILDING, "briostone_chest", () -> DEBlocks.BRIOSTONE_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_BRIOSTONE_CHEST = deferChestItem(BRIOSTONE_BUILDING, "trapped_briostone_chest", () -> DEBlocks.BRIOSTONE_CHEST.get().trapped());
    //*\\*//*\\*//*\\BRIOSTONE*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\EMOSSENCE NATURE\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> EMOSSENCE = deferBlockItem(EMOSSENCE_NATURE, "emossence", () -> DEBlocks.EMOSSENCE.get());
    public static final Wrap<BlockItem> EMOSSENCE_RUBBOLD = deferBlockItem(EMOSSENCE_NATURE, "emossence_rubbold", () -> DEBlocks.EMOSSENCE_RUBBOLD.get());
    public static final Wrap<BlockItem> RUBBOLD = deferBlockItem(EMOSSENCE_NATURE, "rubbold", () -> DEBlocks.RUBBOLD.get());
    public static final Wrap<BlockItem> RUBBOLD_STAIRS = deferBlockItem(EMOSSENCE_NATURE, "rubbold_stairs", () -> DEBlocks.RUBBOLD.get().stairs());
    public static final Wrap<BlockItem> RUBBOLD_SLAB = deferBlockItem(EMOSSENCE_NATURE, "rubbold_slab", () -> DEBlocks.RUBBOLD.get().slab());
    public static final Wrap<BlockItem> EMOSSENCE_SWARD = deferBlockItem(EMOSSENCE_NATURE, "emossence_sward", () -> DEBlocks.EMOSSENCE_SWARD.get());
    public static final Wrap<BlockItem> TALL_EMOSSENCE_SWARD = deferBlockItem(EMOSSENCE_NATURE, "tall_emossence_sward", () -> DEBlocks.TALL_EMOSSENCE_SWARD.get());
    public static final Wrap<BlockItem> EMOSSENCE_PLANT = deferBlockItem(EMOSSENCE_NATURE, "emossence_plant", () -> DEBlocks.EMOSSENCE_PLANT.get());
    public static final Wrap<BlockItem> ECTOSHROOM_BLOCK = deferBlockItem(EMOSSENCE_NATURE, "ectoshroom_block", () -> DEBlocks.ECTOSHROOM_BLOCK.get());
    public static final Wrap<BlockItem> ECTOSHROOM_STEM = deferBlockItem(EMOSSENCE_NATURE, "ectoshroom_stem", () -> DEBlocks.ECTOSHROOM_STEM.get());
    public static final Wrap<BlockItem> NIGHTSHADE_PLANT = deferBlockItem(EMOSSENCE_NATURE, "nightshade_plant", () -> DEBlocks.NIGHTSHADE_PLANT.get());
    //*\\*//*\\*//*\\EMOSSENCE NATURE\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\RUBBOLD//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> RUBBOLD_BRICKS = deferBlockItem(RUBBOLD_BUILDING, "rubbold_bricks", () -> DEBlocks.RUBBOLD_BRICKS.get());
    public static final Wrap<BlockItem> RUBBOLD_BRICKS_STAIRS = deferBlockItem(RUBBOLD_BUILDING, "rubbold_brick_stairs", () -> DEBlocks.RUBBOLD_BRICKS.get().stairs());
    public static final Wrap<BlockItem> RUBBOLD_BRICKS_SLAB = deferBlockItem(RUBBOLD_BUILDING, "rubbold_brick_slab", () -> DEBlocks.RUBBOLD_BRICKS.get().slab());
    public static final Wrap<BlockItem> CRACKED_RUBBOLD_BRICKS = deferBlockItem(RUBBOLD_BUILDING, "cracked_rubbold_bricks", () -> DEBlocks.CRACKED_RUBBOLD_BRICKS.get());
    public static final Wrap<BlockItem> CHISELED_RUBBOLD_BRICKS = deferBlockItem(RUBBOLD_BUILDING, "chiseled_rubbold_bricks", () -> DEBlocks.CHISELED_RUBBOLD_BRICKS.get());
    public static final Wrap<BlockItem> RUBBOLD_PILLAR = deferBlockItem(RUBBOLD_BUILDING, "rubbold_pillar", () -> DEBlocks.RUBBOLD_PILLAR.get());
    public static final Wrap<BlockItem> RUBBOLD_BRICK_WALL = deferBlockItem(RUBBOLD_BUILDING, "rubbold_brick_wall", () -> DEBlocks.RUBBOLD_BRICK_WALL.get());
    public static final Wrap<BCChestItem> RUBBOLD_LOST_CHEST = deferChestItem(RUBBOLD_BUILDING, "rubbold_lost_chest", () -> DEBlocks.RUBBOLD_LOST_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_RUBBOLD_LOST_CHEST = deferChestItem(RUBBOLD_BUILDING, "trapped_rubbold_lost_chest", () -> DEBlocks.RUBBOLD_LOST_CHEST.get().trapped());
    //*\\*//*\\*//*\\RUBBOLD//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\FOAMSHROOM BUILDING//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BlockItem> FOAMSHROOM = deferBlockItem(EMOSSENCE_NATURE, "foamshroom", () -> DEBlocks.FOAMSHROOM.get());
    public static final Wrap<BlockItem> FOAMSHROOM_BLOCK = deferBlockItem(EMOSSENCE_NATURE, "foamshroom_block", () -> DEBlocks.FOAMSHROOM_BLOCK.get());
    public static final Wrap<BlockItem> FOAMSHROOM_STEM = deferBlockItem(EMOSSENCE_NATURE, "foamshroom_stem", () -> DEBlocks.FOAMSHROOM_STEM.get());
    public static final Wrap<BCChestItem> FOAMSHROOM_CHEST = deferChestItem(FOAMSHROOM_BUILDING, "foamshroom_chest", () -> DEBlocks.FOAMSHROOM_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_FOAMSHROOM_CHEST = deferChestItem(FOAMSHROOM_BUILDING, "trapped_foamshroom_chest", () -> DEBlocks.FOAMSHROOM_CHEST.get().trapped());
    public static final Wrap<BlockItem> FOAMSHROOM_PLANKS = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_planks", () -> DEBlocks.FOAMSHROOM_PLANKS.get());
    public static final Wrap<BlockItem> FOAMSHROOM_SLAB = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_slab", () -> DEBlocks.FOAMSHROOM_PLANKS.get().slab());
    public static final Wrap<BlockItem> FOAMSHROOM_STAIRS = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_stairs", () -> DEBlocks.FOAMSHROOM_PLANKS.get().stairs());
    public static final Wrap<BlockItem> FOAMSHROOM_FENCE = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_fence", () -> DEBlocks.FOAMSHROOM_FENCE.get());
    public static final Wrap<BlockItem> FOAMSHROOM_FENCE_GATE = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_fence_gate", () -> DEBlocks.FOAMSHROOM_FENCE.get().gate());
    public static final Wrap<BlockItem> FOAMSHROOM_DOOR = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_door", () -> DEBlocks.FOAMSHROOM_DOOR.get());
    public static final Wrap<BlockItem> FOAMSHROOM_BUTTON = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_button", () -> DEBlocks.FOAMSHROOM_BUTTON.get());
    public static final Wrap<BlockItem> FOAMSHROOM_PRESSURE_PLATE = deferBlockItem(FOAMSHROOM_BUILDING, "foamshroom_pressure_plate", () -> DEBlocks.FOAMSHROOM_PRESSURE_PLATE.get());
    //public static final SignItem FOAMSHROOM_SIGN = defer(BUILDING, "foamshroom_sign", new SignItem(new Item.Properties().group(DannysExpansion.MAIN_GROUP), DannyBlocks.FOAMSHROOM_SIGN, DannyBlocks.FOAMSHROOM_SIGN.wall()));
    public static final Wrap<BCChestItem> FOAMSHROOM_FANCY_CHEST = deferChestItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_chest", () -> DEBlocks.FOAMSHROOM_FANCY_CHEST.get());
    public static final Wrap<BCChestItem> TRAPPED_FOAMSHROOM_FANCY_CHEST = deferChestItem(FANCY_FOAMSHROOM_BUILDING, "trapped_foamshroom_fancy_chest", () -> DEBlocks.FOAMSHROOM_FANCY_CHEST.get().trapped());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_PLANKS = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_planks", () -> DEBlocks.FOAMSHROOM_FANCY_PLANKS.get());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_SLAB = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_slab", () -> DEBlocks.FOAMSHROOM_FANCY_PLANKS.get().slab());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_STAIRS = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_stairs", () -> DEBlocks.FOAMSHROOM_FANCY_PLANKS.get().stairs());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_FENCE = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_fence", () -> DEBlocks.FOAMSHROOM_FANCY_FENCE.get());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_FENCE_GATE = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_fence_gate", () -> DEBlocks.FOAMSHROOM_FANCY_FENCE.get().gate());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_DOOR = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_door", () -> DEBlocks.FOAMSHROOM_FANCY_DOOR.get());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_BUTTON = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_button", () -> DEBlocks.FOAMSHROOM_FANCY_BUTTON.get());
    public static final Wrap<BlockItem> FOAMSHROOM_FANCY_PRESSURE_PLATE = deferBlockItem(FANCY_FOAMSHROOM_BUILDING, "foamshroom_fancy_pressure_plate", () -> DEBlocks.FOAMSHROOM_FANCY_PRESSURE_PLATE.get());
    //*\\*//*\\*//*\\FOAMSHROOM BUILDING//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    //*\\*//*\\*//*\\PRIMARY BOATS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//
    public static final Wrap<BCBoatItem> FOAMSHROOM_BOAT = defer(TRANSPORT, "foamshroom_boat", () ->
            new BCBoatItem(DEBoatTypes.FOAMSHROOM, new Item.Properties().tab(DannysExpansion.TAB)));
    public static final Wrap<BCBoatItem> FOAMSHROOM_FANCY_BOAT = defer(TRANSPORT, "foamshroom_fancy_boat", () ->
            new BCBoatItem(DEBoatTypes.FANCY_FOAMSHROOM, new Item.Properties().tab(DannysExpansion.TAB)));
    //*\\*//*\\*//*\\PRIMARY BOATS//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//*\\*//

    public static Wrap<BlockItem> deferBlockItem(DEItemCategory category, String name, Supplier<? extends Block> block) {
        return defer(category, name, () -> new BlockItem(block.get(), new Item.Properties().tab(DannysExpansion.TAB)));
    }

    public static Wrap<BCChestItem> deferChestItem(DEItemCategory category, String name, Supplier<? extends BCChestBlock> block) {
        return defer(category, name, () -> new BCChestItem(block.get(), new Item.Properties().tab(DannysExpansion.TAB)));
    }

    public static void call() {}
}

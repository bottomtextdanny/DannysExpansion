package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCCuttedBlock;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.DEFenceBlock;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.DEPlanksBlock;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.danny_expannny.objects.blocks.*;
import net.bottomtextdanny.dannys_expansion.core.Registries.DESoundTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public final class DEBlocks {
    public static final BCRegistry<Block> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<Block> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);
	public static final Supplier<BlockBehaviour.Properties> ICE_BRICK_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.ICE_SOLID, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().strength(1.5F, 5.0F).sound(DESoundTypes.COMPACT_ICE);
	public static final Supplier<BlockBehaviour.Properties> BRIOSTIONE_BRICK_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.STONE, DyeColor.GREEN).requiresCorrectToolForDrops().strength(1.75F, 8.0F);
	public static final Supplier<BlockBehaviour.Properties> RUBBOLD_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.STONE, DyeColor.PURPLE).requiresCorrectToolForDrops().strength(2.0F, 8.0F).sound(SoundType.BASALT);
	public static final Supplier<BlockBehaviour.Properties> RUBBOLD_BRICKS_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.STONE, DyeColor.PURPLE).requiresCorrectToolForDrops().strength(2.0F, 8.0F).sound(SoundType.BASALT);
	public static final Supplier<BlockBehaviour.Properties> FOAMSHROOM_PLANKS_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.WOOD, DyeColor.MAGENTA).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final Supplier<BlockBehaviour.Properties> FANCY_FOAMSHROOM_PLANKS_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.WOOD, DyeColor.PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	
	public static final Wrap<Block> COBBLED_BRIOSTONE = HELPER.defer("cobbled_briostone", () -> new Block(BRIOSTIONE_BRICK_PROPERTIES.get()));
	public static final Wrap<Block> BRIOSTONE_CRAGS = HELPER.defer("briostone_crags", () -> new Block(BRIOSTIONE_BRICK_PROPERTIES.get()));
	public static final Wrap<BCCuttedBlock> BRIOSTONE_BRICKS = HELPER.defer("briostone_bricks", () -> new BCCuttedBlock(BRIOSTIONE_BRICK_PROPERTIES.get(), "briostone_brick"));
	public static final Wrap<Block> CHISELED_BRIOSTONE_BRICKS = HELPER.defer("chiseled_briostone_bricks", () -> new Block(BRIOSTIONE_BRICK_PROPERTIES.get()));
	public static final Wrap<RotatedPillarBlock> BRIOSTONE_PILLAR = HELPER.defer("briostone_pillar", () -> new RotatedPillarBlock(BRIOSTIONE_BRICK_PROPERTIES.get()));
	public static final Wrap<DEChestBlock> BRIOSTONE_CHEST = HELPER.defer("briostone_chest", () -> new DEChestBlock(BRIOSTIONE_BRICK_PROPERTIES.get(), () -> DEBlockEntities.CHEST.get(), DESounds.ES_GENERIC_CHEST_OPEN.get(), DESounds.ES_GENERIC_CHEST_CLOSE.get()));
	public static final Wrap<BCCuttedBlock> ICE_BRICKS = HELPER.defer("ice_bricks", () -> new BCCuttedBlock(ICE_BRICK_PROPERTIES.get(), "ice_brick"));
	public static final Wrap<DoorBlock> ICE_DOOR = HELPER.defer("ice_door", () -> new DoorBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<TrapDoorBlock> ICE_TRAPDOOR = HELPER.defer("ice_trapdoor", () -> new TrapDoorBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<DEChestBlock> ICE_CHEST = HELPER.defer( "ice_chest", () -> new DEChestBlock(ICE_BRICK_PROPERTIES.get(), DEBlockEntities.CHEST::get, DESounds.ES_GENERIC_CHEST_OPEN.get(), DESounds.ES_GENERIC_CHEST_CLOSE.get()));
	public static final Wrap<ButtonBlock> ICE_BUTTON = HELPER.defer( "ice_button", () -> new StoneButtonBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<PressurePlateBlock> ICE_PRESSURE_PLATE = HELPER.defer( "ice_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<BCCuttedBlock> RUBBOLD = HELPER.defer( "rubbold", () -> new BCCuttedBlock(RUBBOLD_PROPERTIES.get()));
	public static final Wrap<BCCuttedBlock> RUBBOLD_BRICKS = HELPER.defer( "rubbold_bricks", () -> new BCCuttedBlock(BlockBehaviour.Properties.of(Material.STONE, DyeColor.PURPLE).requiresCorrectToolForDrops().strength(2.25F, 8.0F), "rubbold_brick"));
	public static final Wrap<Block> CRACKED_RUBBOLD_BRICKS = HELPER.defer( "cracked_rubbold_bricks", () -> new Block(RUBBOLD_BRICKS_PROPERTIES.get()));
	public static final Wrap<Block> CHISELED_RUBBOLD_BRICKS = HELPER.defer( "chiseled_rubbold_bricks", () -> new Block(RUBBOLD_BRICKS_PROPERTIES.get()));
	public static final Wrap<RotatedPillarBlock> RUBBOLD_PILLAR = HELPER.defer( "rubbold_pillar", () -> new RotatedPillarBlock(RUBBOLD_BRICKS_PROPERTIES.get()));
	public static final Wrap<WallBlock> RUBBOLD_BRICK_WALL = HELPER.defer( "rubbold_brick_wall", () -> new WallBlock(RUBBOLD_BRICKS_PROPERTIES.get()));
	public static final Wrap<DEChestBlock> RUBBOLD_LOST_CHEST = HELPER.defer( "rubbold_lost_chest", () -> new DEChestBlock(RUBBOLD_BRICKS_PROPERTIES.get(), () -> DEBlockEntities.CHEST.get(), DESounds.ES_GENERIC_CHEST_OPEN.get(), DESounds.ES_GENERIC_CHEST_CLOSE.get()));
	public static final Wrap<EmossenceBlock> EMOSSENCE = HELPER.defer("emossence", () -> new EmossenceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)));
	public static final Wrap<EmossenceBlock> EMOSSENCE_RUBBOLD = HELPER.defer("emossence_rubbold", () -> new EmossenceBlock(RUBBOLD_PROPERTIES.get()));
	public static final Wrap<EmossenceSwardBlock> EMOSSENCE_SWARD = HELPER.defer("emossence_sward", () -> new EmossenceSwardBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, DyeColor.PURPLE).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final Wrap<EmossencePlantBlock> EMOSSENCE_PLANT = HELPER.defer("emossence_plant", () -> new EmossencePlantBlock(BlockBehaviour.Properties.of(Material.PLANT, DyeColor.PURPLE).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final Wrap<EmossenceDoublePlantBlock> TALL_EMOSSENCE_SWARD = HELPER.defer("tall_emossence_sward", () -> new EmossenceDoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, DyeColor.PURPLE).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final Wrap<FoamshroomBlock> FOAMSHROOM = HELPER.defer("foamshroom", () -> new FoamshroomBlock(BlockBehaviour.Properties.of(Material.PLANT, DyeColor.MAGENTA).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final Wrap<Block> FOAMSHROOM_BLOCK = HELPER.defer("foamshroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, DyeColor.MAGENTA).strength(2.0F).sound(SoundType.FUNGUS)));
	public static final Wrap<RotatedPillarBlock> FOAMSHROOM_STEM = HELPER.defer("foamshroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, DyeColor.MAGENTA).strength(2.0F).sound(SoundType.FUNGUS)));
	public static final Wrap<DEPlanksBlock> FOAMSHROOM_PLANKS = HELPER.defer("foamshroom_planks", () -> new DEPlanksBlock(FOAMSHROOM_PLANKS_PROPERTIES.get(), DEBuildingItems.FOAMSHROOM_CHEST));
    public static final Wrap<DEFenceBlock> FOAMSHROOM_FENCE = HELPER.defer("foamshroom_fence", () -> new DEFenceBlock(FOAMSHROOM_PLANKS_PROPERTIES.get()));
    public static final Wrap<DoorBlock> FOAMSHROOM_DOOR = HELPER.defer("foamshroom_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, FOAMSHROOM_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final Wrap<DEChestBlock> FOAMSHROOM_CHEST = HELPER.defer("foamshroom_chest", () -> new DEChestBlock(FOAMSHROOM_PLANKS_PROPERTIES.get(), () -> DEBlockEntities.CHEST.get()).overrideRecipe(FOAMSHROOM_PLANKS.getKey()));
    public static final Wrap<WoodButtonBlock> FOAMSHROOM_BUTTON = HELPER.defer("foamshroom_button", () -> new WoodButtonBlock(FOAMSHROOM_PLANKS_PROPERTIES.get().noCollission().strength(0.5F)));
	//public static final Wrap<DEStandingSignBlockOld FOAMSHROOM_SIGN = HELPER.defer("foamshroom_sign", () -> new DEStandingSignBlockOld(BlockBehaviour.Properties.create(Material.WOOD, DyeColor.PURPLE).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), DannyBlocks.WT_FOAMSHROOM));
	//public static final Wrap<DEStandingSignBlockOld FOAMSHROOM_FANCY_SIGN = HELPER.defer("foamshroom_fancy_sign", () -> new DEStandingSignBlockOld(BlockBehaviour.Properties.create(Material.WOOD, DyeColor.PURPLE).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), DannyBlocks.WT_FANCY_FOAMSHROOM));
	public static final Wrap<PressurePlateBlock> FOAMSHROOM_PRESSURE_PLATE = HELPER.defer("foamshroom_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FOAMSHROOM_PLANKS_PROPERTIES.get().noCollission().strength(0.5F)));
    public static final Wrap<DEPlanksBlock> FOAMSHROOM_FANCY_PLANKS = HELPER.defer("foamshroom_fancy_planks", () -> new DEPlanksBlock(FANCY_FOAMSHROOM_PLANKS_PROPERTIES.get(), DEBuildingItems.FOAMSHROOM_FANCY_CHEST));
	public static final Wrap<DEFenceBlock> FOAMSHROOM_FANCY_FENCE = HELPER.defer("foamshroom_fancy_fence", () -> new DEFenceBlock(FANCY_FOAMSHROOM_PLANKS_PROPERTIES.get()));
	public static final Wrap<DoorBlock> FOAMSHROOM_FANCY_DOOR = HELPER.defer("foamshroom_fancy_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, FOAMSHROOM_FANCY_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final Wrap<DEChestBlock> FOAMSHROOM_FANCY_CHEST = HELPER.defer("foamshroom_fancy_chest", () -> new DEChestBlock(FANCY_FOAMSHROOM_PLANKS_PROPERTIES.get(), () -> DEBlockEntities.CHEST.get()).overrideRecipe(FOAMSHROOM_FANCY_PLANKS.getKey()));
	public static final Wrap<WoodButtonBlock> FOAMSHROOM_FANCY_BUTTON = HELPER.defer("foamshroom_fancy_button", () -> new WoodButtonBlock(FANCY_FOAMSHROOM_PLANKS_PROPERTIES.get().noCollission().strength(0.5F)));
	public static final Wrap<PressurePlateBlock> FOAMSHROOM_FANCY_PRESSURE_PLATE = HELPER.defer("foamshroom_fancy_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FANCY_FOAMSHROOM_PLANKS_PROPERTIES.get().noCollission().strength(0.5F)));
	public static final Wrap<EmossenceDoublePlantBlock> NIGHTSHADE_PLANT = HELPER.defer("nightshade_plant", () -> new EmossenceDoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT, DyeColor.PURPLE).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final Wrap<Block> ECTOSHROOM_BLOCK = HELPER.defer("ectoshroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, DyeColor.BLUE).strength(2.0F).sound(DESoundTypes.ECTOSHROOM)));
	public static final Wrap<RotatedPillarBlock> ECTOSHROOM_STEM = HELPER.defer("ectoshroom_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, DyeColor.BLUE).strength(2.0F).sound(DESoundTypes.ECTOSHROOM)));
	public static final Wrap<Block> NEITHINE = HELPER.defer("neithine", () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE)));
	public static final Wrap<AnchorBlock> EMOSSENCE_ANCHOR = HELPER.defer("emossence_anchor", () -> new AnchorBlock(BlockBehaviour.Properties.of(Material.STONE, DyeColor.GRAY).requiresCorrectToolForDrops().strength(3.5F, 6.0F), () -> DEAmbiences.EMOSSENCE));
	public static final Wrap<Block> PLANT_MATTER = HELPER.defer("plant_matter", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
   // public static final Wrap<JoldBlock> JOLD = HELPER.defer(B"jold", () -> new JoldBlock(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.GRASS).strength(0.1F)));
    public static final Wrap<WorkbenchBlock> WORKBENCH = HELPER.defer("workbench", () -> new WorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final Wrap<DEChestBlock> SEA_CHEST = HELPER.defer("sea_chest", () -> new DEChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST), () -> DEBlockEntities.CHEST.get(), DESounds.BS_SEA_CHEST_OPEN.get(), DESounds.BS_SEA_CHEST_CLOSE.get()) {
        @Override
        public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, @Nullable Entity entity) {
            return DESoundTypes.HOLLOW_METAL;
        }
    });

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockRenders() {
		ItemBlockRenderTypes.setRenderLayer(DEBlocks.WORKBENCH.get(), RenderType.entityCutoutNoCull(new ResourceLocation(DannysExpansion.ID, "textures/models/block/workbench.png")));
		ItemBlockRenderTypes.setRenderLayer(DEBlocks.EMOSSENCE_SWARD.get(), RenderType.cutout());
	    ItemBlockRenderTypes.setRenderLayer(DEBlocks.EMOSSENCE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DEBlocks.TALL_EMOSSENCE_SWARD.get(), RenderType.cutout());
	    ItemBlockRenderTypes.setRenderLayer(DEBlocks.NIGHTSHADE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DEBlocks.FOAMSHROOM.get(), RenderType.cutout());
	    ItemBlockRenderTypes.setRenderLayer(DEBlocks.FOAMSHROOM.get().potted(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DEBlocks.FOAMSHROOM_DOOR.get(), RenderType.cutout());
    }
}

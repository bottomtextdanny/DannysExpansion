package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.blocks.DEChestBlock;
import bottomtextdanny.dannys_expansion.content.blocks.EngineeringStationBlock;
import bottomtextdanny.dannys_expansion.content.blocks.ThatchBlock;
import bottomtextdanny.dannys_expansion.content.blocks.WorkbenchBlock;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import bottomtextdanny.braincell.mod.world.builtin_blocks.BCCuttedBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public final class DEBlocks {
    public static final BCRegistry<Block> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<Block> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);
	public static final Supplier<BlockBehaviour.Properties> ICE_BRICK_PROPERTIES =
			() -> BlockBehaviour.Properties.of(Material.ICE_SOLID, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().strength(1.5F, 5.0F).sound(DESoundTypes.COMPACT_ICE);

	public static final Wrap<BCCuttedBlock> ICE_BRICKS = HELPER.defer("ice_bricks", () -> new BCCuttedBlock(ICE_BRICK_PROPERTIES.get(), "ice_brick"));
	public static final Wrap<DoorBlock> ICE_DOOR = HELPER.defer("ice_door", () -> new DoorBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<TrapDoorBlock> ICE_TRAPDOOR = HELPER.defer("ice_trapdoor", () -> new TrapDoorBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<DEChestBlock> ICE_CHEST = HELPER.defer( "ice_chest", () -> new DEChestBlock(ICE_BRICK_PROPERTIES.get(), DEBlockEntities.CHEST::get, DESounds.ES_GENERIC_CHEST_OPEN.get(), DESounds.ES_GENERIC_CHEST_CLOSE.get()));
	public static final Wrap<ButtonBlock> ICE_BUTTON = HELPER.defer( "ice_button", () -> new StoneButtonBlock(ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<PressurePlateBlock> ICE_PRESSURE_PLATE = HELPER.defer( "ice_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, ICE_BRICK_PROPERTIES.get()));
	public static final Wrap<Block> PLANT_MATTER = HELPER.defer("plant_matter", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
	public static final Wrap<WorkbenchBlock> WORKBENCH = HELPER.defer("workbench", () -> new WorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
//	  public static final Wrap<DEChestBlock> SEA_CHEST = HELPER.defer("sea_chest", () -> new DEChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST), () -> DEBlockEntities.CHEST.get(), DESounds.BS_SEA_CHEST_OPEN.get(), DESounds.BS_SEA_CHEST_CLOSE.get()) {
//        @Override
//        public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, @Nullable Entity entity) {
//            return DESoundTypes.HOLLOW_METAL;
//        }
//    });
	public static final Wrap<EngineeringStationBlock> ENGINEERING_STATION = HELPER.defer("engineering_station", () -> new EngineeringStationBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));
	public static final Wrap<ThatchBlock> THATCH = HELPER.defer("thatch", () -> new ThatchBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));

	@OnlyIn(Dist.CLIENT)
    public static void registerBlockRenders() {
		ItemBlockRenderTypes.setRenderLayer(DEBlocks.WORKBENCH.get(), RenderType.entityCutoutNoCull(new ResourceLocation(DannysExpansion.ID, "textures/models/block/workbench.png")));
		ItemBlockRenderTypes.setRenderLayer(DEBlocks.ICE_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DEBlocks.ICE_DOOR.get(), RenderType.cutout());
    }
}

package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.block_entities.DEChestBlockEntity;
import bottomtextdanny.dannys_expansion.content.block_entities.DETrappedChestBlockEntity;
import bottomtextdanny.dannys_expansion.content.block_entities.WorkbenchBlockEntity;
import bottomtextdanny.dannys_expansion.content.blocks.DEChestBlock;
import bottomtextdanny.dannys_expansion.content.blocks.DETrappedChestBlock;
import bottomtextdanny.dannys_expansion.content._client.rendering.block_entity.WorkbenchTileEntityRenderer;
import bottomtextdanny.flagged_schema_block.FlaggedSchemaBlock;
import bottomtextdanny.flagged_schema_block.FlaggedSchemaBlockEntity;
import bottomtextdanny.flagged_schema_block.FlaggedSchemaBlockRenderer;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import bottomtextdanny.braincell.mod.rendering.BCSimpleChestRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public final class DEBlockEntities {
    public static final BCRegistry<BlockEntityType<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<BlockEntityType<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    public static final Wrap<BlockEntityType<WorkbenchBlockEntity>> WORKBENCH =
            HELPER.defer("workbench", () -> BlockEntityType.Builder.of(
                    WorkbenchBlockEntity::new, DEBlocks.WORKBENCH.get())
                    .build(null));
    public static final Wrap<BlockEntityType<DEChestBlockEntity>> CHEST =
            HELPER.defer("danny_chest", () -> BlockEntityType.Builder.of(
                    DEChestBlockEntity::new, getAllBlocksFromClass(DEChestBlock.class))
                    .build(null));
    public static final Wrap<BlockEntityType<DETrappedChestBlockEntity>> TRAPPED_CHEST =
            HELPER.defer("trapped_danny_chest", () -> BlockEntityType.Builder.of(
                    DETrappedChestBlockEntity::new, getAllBlocksFromClass(DETrappedChestBlock.class))
                    .build(null));
    public static final Wrap<BlockEntityType<FlaggedSchemaBlockEntity>> FLAGGED_SCHEMA_BLOCK =
            HELPER.defer("flagged_schema_block", () -> BlockEntityType.Builder.of(
                    FlaggedSchemaBlockEntity::new, getAllBlocksFromClass(FlaggedSchemaBlock.class))
                    .build(null));

    private static <E extends Block> Block[] getAllBlocksFromClass(Class<E> classRef) {
        return DEBlocks.ENTRIES.getRegistryEntries().stream()
                .map(Supplier::get)
                .filter(block -> classRef.isAssignableFrom(block.getClass()))
                .toArray(Block[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(WORKBENCH.get(), WorkbenchTileEntityRenderer::new);
        BlockEntityRenderers.register(CHEST.get(), BCSimpleChestRenderer::new);
        BlockEntityRenderers.register(TRAPPED_CHEST.get(), BCSimpleChestRenderer::new);
        BlockEntityRenderers.register(FLAGGED_SCHEMA_BLOCK.get(), FlaggedSchemaBlockRenderer::new);
    }
}

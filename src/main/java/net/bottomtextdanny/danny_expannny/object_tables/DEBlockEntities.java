package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.block_entities.DEChestBlockEntity;
import net.bottomtextdanny.danny_expannny.objects.block_entities.DETrappedChestBlockEntity;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BCSimpleChestRenderer;
import net.bottomtextdanny.danny_expannny.objects.blocks.DEChestBlock;
import net.bottomtextdanny.danny_expannny.objects.blocks.DETrappedChestBlock;
import net.bottomtextdanny.danny_expannny.rendering.block_entity.WorkbenchTileEntityRenderer;
import net.bottomtextdanny.danny_expannny.objects.block_entities.WorkbenchBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public final class DEBlockEntities {
    public static final BCRegistry<BlockEntityType<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<BlockEntityType<?>> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

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

    private static <E extends Block> Block[] getAllBlocksFromClass(Class<E> classRef) {
        return DEBlocks.ENTRIES.getRegistryEntries().stream()
                .map(Supplier::get)
                .filter(block -> classRef.isAssignableFrom(block.getClass()))
                .toArray(Block[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenderers() {
        BlockEntityRenderers.register(DEBlockEntities.WORKBENCH.get(), WorkbenchTileEntityRenderer::new);
        BlockEntityRenderers.register(DEBlockEntities.CHEST.get(), BCSimpleChestRenderer::new);
        BlockEntityRenderers.register(DEBlockEntities.TRAPPED_CHEST.get(), BCSimpleChestRenderer::new);
    }
}

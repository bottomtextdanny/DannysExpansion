package net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.bottomtextdanny.danny_expannny.objects.blocks.DETrappedChestBlock;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public interface ExtraChestRegisterer<T extends BCChestBlock, U extends DETrappedChestBlock> extends ExtraBlockRegisterer<T> {

    @Override
    default void registerExtra(T object, ModDeferringManager solving) {
        Objects.requireNonNull(object);
        Optional<BCRegistry<Block>> registryOp = solving.getRegistryDeferror(DeferrorType.BLOCK);
        registryOp.ifPresent(registry -> {
            U newTrappedChestBlock = trappedFactory(object).get();
            newTrappedChestBlock.setRegistryName(solving.getModID(), "trapped_" + object.getRegistryName().getPath());
            solving.doHooksForObject(DeferrorType.BLOCK, newTrappedChestBlock);
            registry.addDeferredRegistry(() -> newTrappedChestBlock);
            setTrapped(newTrappedChestBlock);
        });
    }

    void setTrapped(U declaration);

    Supplier<U> trappedFactory(BCChestBlock base);

    U trapped();
}

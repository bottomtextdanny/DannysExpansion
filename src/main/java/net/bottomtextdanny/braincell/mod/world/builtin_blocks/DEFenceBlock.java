package net.bottomtextdanny.braincell.mod.world.builtin_blocks;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions.ExtraBlockRegisterer;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DEFenceBlock extends FenceBlock implements ExtraBlockRegisterer<DEFenceBlock> {
    private FenceGateBlock gate;

    public DEFenceBlock(Properties properties) {
        super(properties);
    }

    public FenceGateBlock gate() {
        return this.gate;
    }

    @Override
    public void registerExtra(DEFenceBlock base, ModDeferringManager solving) {
        solving.getRegistryDeferror(DeferrorType.BLOCK).ifPresent(registry -> {
            this.gate = new FenceGateBlock(BlockBehaviour.Properties.copy(this));
            this.gate.setRegistryName(getRegistryName().getPath() + "_gate");
            solving.doHooksForObject(DeferrorType.BLOCK, this.gate);
            registry.addDeferredRegistry(() -> this.gate);
        });
    }
}

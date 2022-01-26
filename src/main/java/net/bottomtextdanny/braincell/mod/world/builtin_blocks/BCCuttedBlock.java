package net.bottomtextdanny.braincell.mod.world.builtin_blocks;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions.ExtraBlockRegisterer;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

import javax.annotation.Nullable;

public class BCCuttedBlock extends Block implements ExtraBlockRegisterer<Block> {
    @Nullable
	private String mask;
    private StairBlock stairs;
    private SlabBlock slab;

    public BCCuttedBlock(Block.Properties properties, String mask) {
        super(properties);
        this.mask = mask;
    }
	
	public BCCuttedBlock(Block.Properties properties) {
		super(properties);
		this.mask = null;
	}

    public SlabBlock slab() {
        return this.slab;
    }

    public StairBlock stairs() {
        return this.stairs;
    }

    @Override
    public void registerExtra(Block object, ModDeferringManager solving) {
        solving.getRegistryDeferror(DeferrorType.BLOCK).ifPresent(registry -> {
            if (this.mask == null) this.mask = object.getRegistryName().getPath();
            this.stairs = new StairBlock(object.defaultBlockState(), Properties.copy(object));
            this.stairs.setRegistryName(this.mask + "_stairs");
            solving.doHooksForObject(DeferrorType.BLOCK, this.stairs);
            registry.addDeferredRegistry(() -> this.stairs);
            this.slab = new SlabBlock(Properties.copy(object));
            this.slab.setRegistryName(this.mask + "_slab");
            solving.doHooksForObject(DeferrorType.BLOCK, this.slab);
            registry.addDeferredRegistry(() -> this.slab);
            this.mask = null;
        });
    }
}

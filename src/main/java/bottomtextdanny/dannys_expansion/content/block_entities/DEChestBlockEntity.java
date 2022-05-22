package bottomtextdanny.dannys_expansion.content.block_entities;

import bottomtextdanny.dannys_expansion.tables.DEBlockEntities;
import bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DEChestBlockEntity extends BCChestBlockEntity {

    public DEChestBlockEntity(BlockPos pos, BlockState state) {
        super(DEBlockEntities.CHEST.get(), pos, state);
    }
}

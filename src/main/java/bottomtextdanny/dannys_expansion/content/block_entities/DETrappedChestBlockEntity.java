package bottomtextdanny.dannys_expansion.content.block_entities;

import bottomtextdanny.dannys_expansion.tables.DEBlockEntities;
import bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DETrappedChestBlockEntity extends BCChestBlockEntity {

    public DETrappedChestBlockEntity(BlockPos pos, BlockState state) {
        super(DEBlockEntities.TRAPPED_CHEST.get(), pos, state);
    }

    @Override
    protected void signalOpenCount(Level p_155333_, BlockPos p_155334_, BlockState p_155335_, int p_155336_, int p_155337_) {
        super.signalOpenCount(p_155333_, p_155334_, p_155335_, p_155336_, p_155337_);
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }
}

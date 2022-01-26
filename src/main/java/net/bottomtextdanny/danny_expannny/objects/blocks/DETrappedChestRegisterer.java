package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions.ExtraChestRegisterer;
import net.bottomtextdanny.braincell.mod.world.builtin_blocks.BCChestBlock;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface DETrappedChestRegisterer extends ExtraChestRegisterer<BCChestBlock, DETrappedChestBlock> {

    default Supplier<DETrappedChestBlock> trappedFactory(BCChestBlock base) {
        return () -> new DETrappedChestBlock(BlockBehaviour.Properties.copy(base), DEBlockEntities.TRAPPED_CHEST::get, base.getOpenSound(), base.getCloseSound()) {

            @Override
            public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, @Nullable Entity entity) {
                return base.getSoundType(state, world, pos, entity);
            }
        };
    }
}

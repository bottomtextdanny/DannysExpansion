package net.bottomtextdanny.danny_expannny.objects.block_entities;

import net.bottomtextdanny.braincell.mod.world.builtin_block_entities.BCChestBlockEntity;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DEChestBlockEntity extends BCChestBlockEntity {

    public DEChestBlockEntity(BlockPos pos, BlockState state) {
        super(DEBlockEntities.CHEST.get(), pos, state);
    }
}

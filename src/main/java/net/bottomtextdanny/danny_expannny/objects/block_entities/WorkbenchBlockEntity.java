package net.bottomtextdanny.danny_expannny.objects.block_entities;

import net.bottomtextdanny.danny_expannny.object_tables.DEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WorkbenchBlockEntity extends BlockEntity {

    public WorkbenchBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(DEBlockEntities.WORKBENCH.get(), p_155229_, p_155230_);
    }
}

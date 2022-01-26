package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class EmossenceDoublePlantBlock extends DoublePlantBlock {

    public EmossenceDoublePlantBlock(Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(DETags.EMOSSENCE_PLACEABLE_ON);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return blockstate.is(DETags.EMOSSENCE_PLACEABLE_ON);
        } else {

            if (state.getBlock() != this) {
                return state.is(DETags.EMOSSENCE_PLACEABLE_ON); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            }
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }
}

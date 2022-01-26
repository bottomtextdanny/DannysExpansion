package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.objects.blocks.EmossenceDoublePlantBlock;
import net.bottomtextdanny.danny_expannny.objects.blocks.EmossencePlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class EmossenceSwardBlock extends EmossencePlantBlock implements BonemealableBlock {

    public EmossenceSwardBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        EmossenceDoublePlantBlock grownPlant = DEBlocks.TALL_EMOSSENCE_SWARD.get();
        if (grownPlant.defaultBlockState().canSurvive(worldIn, pos) && worldIn.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(worldIn, state, pos, 2);
        }
    }
}

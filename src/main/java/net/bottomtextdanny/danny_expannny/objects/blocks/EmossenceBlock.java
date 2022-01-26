package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.braincell.mod.world.block_utilities.AmbientWeightProvider;
import net.bottomtextdanny.danny_expannny.object_tables.DEAmbiences;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class EmossenceBlock extends Block implements BonemealableBlock, AmbientWeightProvider {

    public EmossenceBlock(Properties properties) {
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
        BlockPos blockpos = pos.above();
        BlockState blockstate = DEBlocks.EMOSSENCE_SWARD.get().defaultBlockState();

        label:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for(int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!worldIn.getBlockState(blockpos1.below()).is(this) || worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) {
                    continue label;
                }
            }

            BlockState blockstate2 = worldIn.getBlockState(blockpos1);
            if (blockstate2.is(blockstate.getBlock()) && rand.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(worldIn, rand, blockpos1, blockstate2);
            }

            if (blockstate2.isAir()) {
                BlockState blockstate1;
                if (rand.nextInt(8) == 0) {
                    float rng = rand.nextFloat();
                    if (rng < 0.6) {
                        blockstate1 = DEBlocks.TALL_EMOSSENCE_SWARD.get().defaultBlockState();
                    } else {
                        blockstate1 = DEBlocks.FOAMSHROOM.get().defaultBlockState();
                    }

                } else {
                    blockstate1 = blockstate;
                }

                if (blockstate1.canSurvive(worldIn, blockpos1)) {
                    if (blockstate1.getBlock() instanceof DoublePlantBlock) {
                        DoublePlantBlock.placeAt(worldIn, blockstate1, blockpos1, 3);
                    } else {
                        worldIn.setBlock(blockpos1, blockstate1, 3);
                    }
                }
            }
        }
    }
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public DEAmbiance ambiance() {
		return DEAmbiences.EMOSSENCE;
	}

    @OnlyIn(Dist.CLIENT)
	@Override
	public int weightOnAmbiance(BlockState state, BlockPos pos, Level world) {
		return 5;
	}
}

package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosArrayDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.Random;

public class CTPillarsDecorator extends PosArrayDecorator {
    int height;
    int distance;
    Direction.Axis axis;

    public CTPillarsDecorator(WorldGenLevel world, Random rand, Direction.Axis axis, int height, int distance) {
        super(world, rand);
        this.height = height;
        this.axis = axis;
        this.distance = distance;
    }

    //position[0] should be min from axis, position[1] should be max from axis
    @Override
    public void generate(BlockPos... positions) {
        for (BlockPos pos : positions) {
            int sign = pos == positions[0] ? 1 : -1;

            for (int i = 0; PieceUtil.isSpace(getWorld(), pos.below(i)); i++) {
                getWorld().setBlock(pos.below(i), Blocks.OAK_LOG.defaultBlockState(), 0);
            }

            for (int i = 0; i <= this.height; i++) {
                getWorld().setBlock(pos.above(i), Blocks.OAK_LOG.defaultBlockState(), 0);


            }

            if (this.random.nextFloat() < 0.55F) {
                getWorld().setBlock(pos.above().relative(this.axis, sign), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, this.axis == Direction.Axis.X ? sign == 1 ? Direction.EAST : Direction.WEST : sign == 1 ? Direction.SOUTH : Direction.NORTH), 0);
            }

            BlockPos supportPos = pos.above(this.height).relative(this.axis, sign);

            for (int i = 0; i < this.distance / 2 + 1; i++) {
                BlockPos iterationPos = supportPos.relative(this.axis, i * sign);
                if (i == 0) {
                    getWorld().setBlock(iterationPos, Blocks.OAK_PLANKS.defaultBlockState(), 0);
                    getWorld().setBlock(iterationPos.below(), Blocks.OAK_PLANKS.defaultBlockState(), 0);
                    continue;
                }

                if (i == 1) {
                    getWorld().setBlock(iterationPos, Blocks.OAK_PLANKS.defaultBlockState(), 0);
                    continue;
                }

                getWorld().setBlock(iterationPos, Blocks.OAK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 0);
            }
        }
    }
}

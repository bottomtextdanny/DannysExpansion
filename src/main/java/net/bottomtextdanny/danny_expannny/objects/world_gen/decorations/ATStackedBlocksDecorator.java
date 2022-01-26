package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.FlagMatrix;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * makes a stack of a random block in #blocks
 */
public class ATStackedBlocksDecorator extends PosDecorator {
    public ImmutableList<FlagMatrix> shapeMatrices;
    public final BlockState[] blocks;

    public ATStackedBlocksDecorator(WorldGenLevel world, BlockPos pos, Random rand, BlockState... block) {
        super(world, rand);
        this.blocks = block;
    }

    @Override
    public void generate(BlockPos position) {
        int height = this.random.nextInt(2) + 2;
        FlagMatrix matrix0 = new FlagMatrix(position, this.random).rotative();
        matrix0.addRegion(position, position.offset(1, height, 1));
        matrix0.addRegionWithIntegrity(position.above(height + 1), position.offset(1, height + 1, 1), 0.8F);
        matrix0.mayAddPosition(Pair.bp(position.offset(-1, 0, 0)), 0.7F);
        matrix0.mayAddPosition(Pair.bp(position.offset(0, 0, -1)), 0.7F);
        matrix0.mayAddPosition(Pair.bp(position.offset(-1, 0, -1)), 0.7F);

        FlagMatrix matrix1 = new FlagMatrix(position, this.random).rotative();
        matrix1.addRegion(position, position.offset(1, height, 1));
        matrix1.addRegionWithIntegrity(position.above(height + 1), position.offset(1, height + 1, 1), 0.8F);
        matrix1.addRegionWithIntegrity(position.offset(-1, 0, -1), position.offset(0, 2, 0), 0.5F);

        BlockState block = DEUtil.getRandomObject(this.blocks);

        this.shapeMatrices = ImmutableList.of(matrix0, matrix1);
        DEUtil.getRandomObject(this.shapeMatrices).getFinal().forEach(pbp -> {
            BlockPos bp = pbp.getKey();
            PieceUtil.setBlockOnSpace(getWorld(), bp, block);
        });
    }
}

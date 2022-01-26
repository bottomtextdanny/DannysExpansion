package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.FlagMatrix;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.SplittableRandom;

public class SmallFoamshroomDeco extends PosDecorator {

    public SmallFoamshroomDeco(WorldGenLevel world, Random rand) {
        super(world, rand);
    }

    public SmallFoamshroomDeco(WorldGenLevel world, SplittableRandom rand) {
        super(world, rand);
    }

    @Override
    public void generate(BlockPos position) {
        position = position.above();
        FlagMatrix matrix0 = new FlagMatrix(position, this.random);

        int m0HeightFactor = 1 + this.random.nextInt(3);
        matrix0.addRegion(position, position.above(m0HeightFactor), 0);
        matrix0.addPosition(Pair.bp(position.offset(0, m0HeightFactor + 1, 0), 1));
        matrix0.addPosition(Pair.bp(position.offset(1, m0HeightFactor + 1, 0), 1));
        matrix0.addPosition(Pair.bp(position.offset(0, m0HeightFactor + 1, 1), 1));
        matrix0.addPosition(Pair.bp(position.offset(-1, m0HeightFactor + 1, 0), 1));
        matrix0.addPosition(Pair.bp(position.offset(0, m0HeightFactor + 1, -1), 1));

        matrix0.mayAddPosition(Pair.bp(position.offset(1, m0HeightFactor + 1, 1), 1), 0.6F);
        matrix0.mayAddPosition(Pair.bp(position.offset(1, m0HeightFactor + 1, -1), 1), 0.6F);
        matrix0.mayAddPosition(Pair.bp(position.offset(-1, m0HeightFactor + 1, 1), 1), 0.6F);
        matrix0.mayAddPosition(Pair.bp(position.offset(-1, m0HeightFactor + 1, -1), 1), 0.6F);

        FlagMatrix matrix1 = new FlagMatrix(position, this.random);
        matrix1.addRegion(position, position.above(), 0);
        matrix1.addPosition(Pair.bp(position.offset(1, 1, 0), 1));
        matrix1.addPosition(Pair.bp(position.offset(0, 1, 1), 1));
        matrix1.addPosition(Pair.bp(position.offset(-1, 1, 0), 1));
        matrix1.addPosition(Pair.bp(position.offset(0, 1, -1), 1));
        matrix1.addPosition(Pair.bp(position.offset(0, 2, 0), 1));

        DEUtil.getRandomObject(ImmutableList.of(matrix0, matrix1)).getMatrixList().forEach(pbp -> {
            BlockPos bp = pbp.getKey();
            byte flag = pbp.getValue();
            BlockState state = flag == 0 ? DEBlocks.FOAMSHROOM_STEM.get().defaultBlockState() : DEBlocks.FOAMSHROOM_BLOCK.get().defaultBlockState();

            if (!PieceUtil.fullCollision(this.world, bp)) {
                PieceUtil.setBlock(this.world, bp, state, 2);
            }
        });
    }
}

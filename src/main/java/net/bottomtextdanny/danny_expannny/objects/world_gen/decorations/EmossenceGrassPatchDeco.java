package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;

import java.util.SplittableRandom;

public class EmossenceGrassPatchDeco extends PosDecorator {

    public EmossenceGrassPatchDeco(WorldGenLevel world, SplittableRandom split) {
        super(world, split);
    }

    @Override
    public void generate(BlockPos position) {
        final int radX = 8;
        final int radZ = 8;

        for (int x = -radX; x < radX; x++) {
            for (int z = -radZ; z < radZ; z++) {
                BlockPos pos = position.offset(x, 0, z);

                pos = PieceUtil.vBlocksNoCollision(this.world, pos, 4);

                if (pos != null) {
                    double distance = DEMath.getHorizontalDistance(pos.getX(), pos.getZ(), position.getX(), position.getZ());
                    double chanceValue = distance / radX * (0.3 + this.split.nextDouble() * 0.7);

                    if (chanceValue < 0.3 && PieceUtil.isAir(this.world, pos.above()) && this.world.getBlockState(pos).is(DETags.EMOSSENCE_PLACEABLE_ON)) {
                        double blockRandomFactor = this.split.nextDouble();

                        if (blockRandomFactor < 0.8) {
                            this.world.setBlock(pos.above(), DEBlocks.EMOSSENCE_SWARD.get().defaultBlockState(), 0);
                        } else if (blockRandomFactor < 0.97 && PieceUtil.noCollision(this.world, pos.above(2))) {

                            DoublePlantBlock.placeAt(this.world, DEBlocks.TALL_EMOSSENCE_SWARD.get().defaultBlockState(), pos.above(), 2);
                        } else {
                            this.world.setBlock(pos.above(), DEBlocks.FOAMSHROOM.get().defaultBlockState(), 0);
                        }
                    }
                }
            }
        }
    }
}

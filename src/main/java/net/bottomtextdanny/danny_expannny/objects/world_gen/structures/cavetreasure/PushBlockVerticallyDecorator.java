package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.cavetreasure;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.FlagMatrix;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PushBlockVerticallyDecorator extends PosDecorator {
    public ImmutableList<FlagMatrix> shapeMatrices;
    public final Direction direction;

    public PushBlockVerticallyDecorator(WorldGenLevel world, Random rand, Direction type, Block mask) {
        super(world, rand);
        this.direction = type;
        this.mask = Collections.singletonList(mask);
    }

    @Override
    public void generate(BlockPos position) {

        this.shapeMatrices = ImmutableList.of(new FlagMatrix(position, this.random, Arrays.asList(
                Pair.bp(position),
                Pair.bp(position.west()),
                Pair.bp(position.east()),
                Pair.bp(position.north()),
                Pair.bp(position.south())
        )).rotative(), new FlagMatrix(position, this.random, Arrays.asList(
                Pair.bp(position),
                Pair.bp(position.west()),
                Pair.bp(position.east()),
                Pair.bp(position.north()),
                Pair.bp(position.north(2)),
                Pair.bp(position.south()),
                Pair.bp(position.south(2))
        )).rotative());

        DEUtil.getRandomObject(this.shapeMatrices).getFinal().forEach(pbp -> {
            BlockPos bp = pbp.getKey();
            if (getWorld().getBlockState(bp).getBlock() == this.mask.get(0) && !PieceUtil.isSpace(getWorld(), bp.relative(this.direction, 1)) && PieceUtil.isSpace(getWorld(), bp.relative(this.direction, -1))) {
                getWorld().setBlock(bp.relative(this.direction, 1), getWorld().getBlockState(bp), 0);

                getWorld().setBlock(bp, Blocks.AIR.defaultBlockState(), 0);
            }
        });
    }
}

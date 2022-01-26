package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.FlagMatrix;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
should be placed on a surface
 */
public class ATSlabStripesDecorator extends PosDecorator {
    public final BlockState slab;

    public ATSlabStripesDecorator(WorldGenLevel world, Random rand, BlockState slab, Block mask) {
        super(world, rand);
        this.slab = slab;
        this.mask = Collections.singletonList(mask);
    }

    @Override
    public void generate(BlockPos position) {
        ImmutableList<FlagMatrix> shapeMatrices = ImmutableList.of(new FlagMatrix(position, this.random, Arrays.asList(
                Pair.bp(position),
                Pair.bp(position.west()),
                Pair.bp(position.east()),
                Pair.bp(position.north()),
                Pair.bp(position.south())
        )), new FlagMatrix(position, this.random, Arrays.asList(
                Pair.bp(position),
                Pair.bp(position.west()),
                Pair.bp(position.east()),
                Pair.bp(position.north()),
                Pair.bp(position.north(2)),
                Pair.bp(position.south()),
                Pair.bp(position.south(2))
        )).rotative());

        DEUtil.getRandomObject(shapeMatrices).getFinal().forEach(pbp -> {
            BlockPos bp = pbp.getKey();
            if (getWorld().getBlockState(bp).getBlock() == this.mask.get(0) && !getWorld().getBlockState(bp.above()).isRedstoneConductor(getWorld(), bp))
                PieceUtil.setWLBlockSolid(getWorld(), bp, this.slab);
        });
    }
}

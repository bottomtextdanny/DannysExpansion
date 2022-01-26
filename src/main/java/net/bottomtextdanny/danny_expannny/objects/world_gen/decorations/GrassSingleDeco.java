package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import net.bottomtextdanny.braincell.underlying.misc.WeightArray;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;

import java.util.HashSet;
import java.util.List;
import java.util.SplittableRandom;

public class GrassSingleDeco extends PosDecorator {
	private final HashSet<Block> placeableOn = new HashSet<>(0);
	private final int radius;
	private final WeightArray<Block> placeables;

    public GrassSingleDeco(WorldGenLevel world, SplittableRandom split, int radius, List<Block> placeableOn, WeightArray<Block> placeables) {
        super(world, split);
	    this.placeableOn.addAll(placeableOn);
	    this.radius = radius;
	    this.placeables = placeables;
    }

    @Override
    public void generate(BlockPos position) {
    	if (this.placeableOn.contains(this.world.getBlockState(position).getBlock())) {
		    Block blockKey = this.placeables.map(this.split.nextInt(this.placeables.weightArraySizeSize));
		
		    if (PieceUtil.noCollision(this.world, position.above(1))) {
			
			    if (blockKey instanceof DoublePlantBlock && PieceUtil.noCollision(this.world, position.above(2))) {
				    DoublePlantBlock.placeAt(this.world, blockKey.defaultBlockState(), position.above(), 0);
			    } else {
                    this.world.setBlock(position.above(), blockKey.defaultBlockState(), 0);
			    }
		    }
	    }
    }
}

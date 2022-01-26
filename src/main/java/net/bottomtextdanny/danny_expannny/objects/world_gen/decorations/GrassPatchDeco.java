package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import net.bottomtextdanny.braincell.underlying.misc.WeightArray;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;

import java.util.HashSet;
import java.util.List;
import java.util.SplittableRandom;

public class GrassPatchDeco extends PosDecorator {
	private final HashSet<Block> placeableOn = new HashSet<>(0);
	private final int radius;
	private final WeightArray<Block> placeables;

    public GrassPatchDeco(WorldGenLevel world, SplittableRandom split, int radius, List<Block> placeableOn, WeightArray<Block> placeables) {
        super(world, split);
	    this.placeableOn.addAll(placeableOn);
	    this.radius = radius;
	    this.placeables = placeables;
    }

    @Override
    public void generate(BlockPos position) {
    	
        for (int x = -this.radius; x < this.radius; x++) {
            for (int z = -this.radius; z < this.radius; z++) {
                BlockPos pos = position.offset(x, 0, z);
                pos = PieceUtil.vBlocksNoCollision(this.world, pos, 16);

                if (pos != null) {
                    double distance = DEMath.getHorizontalDistance(pos.getX(), pos.getZ(), position.getX(), position.getZ());
                    double chanceValue = distance / this.radius * (0.3 + this.split.nextDouble() * 0.7);
                    if (chanceValue < 0.3 && PieceUtil.isAir(this.world, pos.above()) && this.placeableOn.contains(this.world.getBlockState(pos).getBlock())) {
                        Block blockKey = this.placeables.map(this.split.nextInt(this.placeables.weightArraySizeSize));
	                  
                        if (PieceUtil.noCollision(this.world, pos.above(1))) {
	
                        	if (blockKey instanceof DoublePlantBlock && PieceUtil.noCollision(this.world, pos.above(2))) {
		                        DoublePlantBlock.placeAt(this.world, blockKey.defaultBlockState(), pos.above(), 0);
	                        } else {
                                this.world.setBlock(pos.above(), blockKey.defaultBlockState(), 0);
	                        }
                        }
                    }
                }
            }
        }
    }
}

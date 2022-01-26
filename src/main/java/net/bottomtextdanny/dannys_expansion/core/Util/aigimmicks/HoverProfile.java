package net.bottomtextdanny.dannys_expansion.core.Util.aigimmicks;

import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class HoverProfile {
    private final Mob entity;
    private final boolean overWater;
    public final int blockHeap;
    public int blocksUp;
    public int blocksDown;
    private int aboveVoid = 10;

    public HoverProfile(Mob entity, int blockHeap, boolean overWater) {
        this.entity = entity;
        this.blockHeap = blockHeap;
        this.overWater = overWater;
    }
    
    public HoverProfile aboveVoid(int aboveVoid) {
    	this.aboveVoid = aboveVoid;
    	return this;
    }

    public void update(Vec3 pos) {
        BlockPos blockPos = new BlockPos(pos);

        if (this.overWater) {
            this.blocksDown = vBlocksNoCollisionNoWater(this.entity.level, blockPos, -1);
            this.blocksUp = vBlocksNoCollisionNoWater(this.entity.level, blockPos, 1);
        } else {
            this.blocksDown = vBlocksNoCollision(this.entity.level, blockPos, -1);
            this.blocksUp = vBlocksNoCollision(this.entity.level, blockPos, 1);
        }
    }

    public float get01Scale() {
        return (float)(this.blocksDown + 1) / Math.max((float)(this.blocksDown + 1 + this.blocksUp), 1);
    }

    public float get01ScaleTrim(int down, int up) {
        int trimmedDown = Math.min(this.blocksDown, down);
        int trimmedUp = Math.min(this.blocksUp, up);
        return (float)(trimmedDown + 1) / (float)(trimmedDown + 1 + trimmedUp);
    }

    public boolean isGroundLower(int down) {
        return down < this.blocksDown;
    }

    public boolean isCeilingHigher(int up) {
        return up < this.blocksUp;
    }

    public int equilibriumOffset() {
        return this.blocksUp - (this.blocksDown + 1);
    }

    public int vBlocksNoCollision(LevelAccessor world, BlockPos pos, int sum) {
        int j = 0;

        if (PieceUtil.noCollision(world, pos)) {
            int k = 0;
            BlockPos iter;
            
            if (sum < 0) {
	            for(int i = 0; PieceUtil.noCollision(world, iter = pos.offset(0F, k += sum, 0F)); ++i) {
		            if (i < this.blockHeap && iter.getY() > this.aboveVoid) {
			            j++;
		            } else {
			            break;
		            }
	            }
            } else {
	            for(int i = 0; PieceUtil.noCollision(world, pos.offset(0F, k += sum, 0F)); ++i) {
		            if (i < this.blockHeap) {
			            j++;
		            } else {
			            break;
		            }
	            }
            }
           
        }

        return j;
    }

    public int vBlocksNoCollisionNoWater(LevelAccessor world, BlockPos pos, int sum) {
        int j = 0;

        if (PieceUtil.noCollision(world, pos)) {
            int k = 0;
	        BlockPos iter;
         
	        if (sum < 0) {
		        for(int i = 0; !PieceUtil.water(world, iter = pos.offset(0F, k += sum, 0F)) && PieceUtil.noCollision(world, iter); ++i) {
			        if (i < this.blockHeap && iter.getY() > this.aboveVoid) {
				        j++;
			        } else {
				        break;
			        }
		        }
	        } else {
		        for(int i = 0; !PieceUtil.water(world, iter = pos.offset(0F, k += sum, 0F)) && PieceUtil.noCollision(world, iter); ++i) {
			        if (i < this.blockHeap) {
				        j++;
			        } else {
				        break;
			        }
		        }
	        }
        }

        return j;
    }
}

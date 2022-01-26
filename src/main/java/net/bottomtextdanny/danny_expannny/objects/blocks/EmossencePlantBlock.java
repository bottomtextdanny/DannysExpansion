package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

public class EmossencePlantBlock extends BushBlock implements IForgeShearable {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public EmossencePlantBlock(Properties properties) {
        super(properties);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return worldIn.getBlockState(blockpos).is(DETags.EMOSSENCE_PLACEABLE_ON);
        return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
	    return state.is(DETags.EMOSSENCE_PLACEABLE_ON);
    }
}

package net.bottomtextdanny.danny_expannny.objects.blocks;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.block_extensions.ExtraPottedPlantRegisterer;
import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.GiantFoamshroomDeco;
import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.SmallFoamshroomDeco;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class FoamshroomBlock extends EmossencePlantBlock implements BonemealableBlock, ExtraPottedPlantRegisterer<EmossencePlantBlock, FlowerPotBlock> {
	private FlowerPotBlock potted;

    public FoamshroomBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double) worldIn.random.nextFloat() < 0.25D;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        if (!ForgeEventFactory.saplingGrowTree(worldIn, rand, pos)) {
            return;
        }

        if (rand.nextFloat() > 0.2F) {
            new SmallFoamshroomDeco(worldIn, rand).generate(pos.below());
        } else {
            new GiantFoamshroomDeco(worldIn, rand).generate(pos.below());
        }
    }
	
	@Override
	public void decPotted(FlowerPotBlock value) {
        this.potted = value;
	}
	
	@Override
	public FlowerPotBlock potted() {
		return this.potted;
	}
}

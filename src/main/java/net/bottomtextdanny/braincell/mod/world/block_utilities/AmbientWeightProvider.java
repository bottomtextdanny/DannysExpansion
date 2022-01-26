package net.bottomtextdanny.braincell.mod.world.block_utilities;

import net.bottomtextdanny.danny_expannny.rendering.ambiances.DEAmbiance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface AmbientWeightProvider {

	@OnlyIn(Dist.CLIENT)
	DEAmbiance ambiance();

	@OnlyIn(Dist.CLIENT)
	int weightOnAmbiance(BlockState state, BlockPos pos, Level world);
}

package bottomtextdanny.dannys_expansion._base.ambiance;

import bottomtextdanny.dannys_expansion.content._client.ambiances.Ambiance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface AmbientWeightProvider {

	@OnlyIn(Dist.CLIENT)
	Ambiance ambiance();

	@OnlyIn(Dist.CLIENT)
	int weightOnAmbiance(BlockState state, BlockPos pos, Level world);
}

package net.bottomtextdanny.dannys_expansion.common.Level.end_biome_generation;

import net.bottomtextdanny.danny_expannny.object_tables.DEBiomes;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.objects.world_gen.DESurfaceTools;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class EndBiomeSurfaceRules {
	
	private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
		return SurfaceRules.state(p_194811_.defaultBlockState());
	}
	
	public static SurfaceRules.RuleSource makeEmossenceGeneration() {
		return
			DESurfaceTools.wrap(() -> SurfaceRules.sequence(
					
					SurfaceRules.ifTrue(SurfaceRules.isBiome(DEBiomes.EMOSSENCE.getBiomeKey()),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
								SurfaceRules.ifTrue(DESurfaceTools.freeCheck(EndBiomeSurfaceRules::calculateEmossence),
									emossenceState()
								)
							),
							rubboldState()
						)
					)
				)
			);
	}
	
	public static boolean calculateEmossence(BlockPos pos) {
		double x = (double) pos.getX() / 4.0;
		double z = (double) pos.getZ() / 4.0;
		double mainThreshold = 0.78;
		double mainFactor = EndBiomeChooser.noise1.noise(x * 0.004, z * 0.004);
		
		double rustyFactor = EndBiomeChooser.noise3.noise(x * 0.3, z * 0.3) * 0.15;
		
		double finalNoise = mainFactor + rustyFactor * mainFactor * mainFactor *  mainFactor * mainFactor;
		
		return finalNoise > mainThreshold;
	}
	
	public static SurfaceRules.RuleSource emossenceState() {
		return makeStateRule(DEBlocks.EMOSSENCE_RUBBOLD.get());
	}
	
	public static SurfaceRules.RuleSource rubboldState() {
		return makeStateRule(DEBlocks.RUBBOLD.get());
	}
}

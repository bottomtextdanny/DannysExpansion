package net.bottomtextdanny.dannys_expansion.common.Level.end_biome_generation;

import net.bottomtextdanny.danny_expannny.object_tables.DEBiomes;
import net.bottomtextdanny.dannys_expansion.core.Util.random.SimplexNoiseMapper;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

import javax.annotation.Nullable;
import java.util.SplittableRandom;

public class EndBiomeChooser {
	public static final SplittableRandom SRANDOM = new SplittableRandom();
	public static SimplexNoiseMapper noise1 = new SimplexNoiseMapper(SRANDOM);// main
	public static SimplexNoiseMapper noise2 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise3 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise4 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise5 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise6 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise7 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise8 = new SimplexNoiseMapper(SRANDOM);
	public static SimplexNoiseMapper noise9 = new SimplexNoiseMapper(SRANDOM);
	
	@Nullable
	public static Biome getBiomeForCoord(int x, int y, int z, Registry<Biome> biomeRegistry) {
	
		double mainThreshold = 0.75;
		double mainFactor = EndBiomeChooser.noise1.noise((double) x * 0.004, (double) z * 0.004);
		
		double rustyFactor = EndBiomeChooser.noise3.noise((double) x * 0.3, (double) z * 0.3) * 0.20;
		
		double finalNoise = mainFactor + rustyFactor * mainFactor * mainFactor * mainFactor * mainFactor;
		
		if (finalNoise > mainThreshold) {
			return biomeRegistry.get(DEBiomes.EMOSSENCE.getBiomeKey());
		}
		
		return null;
	}
	
	public static float chooseHeightForBlock(SimplexNoise noise, int x, int z) {
		float i = x / 2.0F;
		float j = z / 2.0F;
		return getHeightValue(noise, i + 1, j + 1) + 19.0F;
	}
	
	public static float getHeightValue(SimplexNoise p_48646_, float p_48647_, float p_48648_) {
		float i = p_48647_ / 2.0F;
		float j = p_48648_ / 2.0F;
		float k = p_48647_ % 2.0F;
		float l = p_48648_ % 2.0F;
		float f = 100.0F - Mth.sqrt(p_48647_ * p_48647_ + p_48648_ * p_48648_) * 8.0F;
		f = Mth.clamp(f, -100.0F, 80.0F);
		
		for(int i1 = -12; i1 <= 12; ++i1) {
			for(int j1 = -12; j1 <= 12; ++j1) {
				long k1 = (long)(i + i1);
				long l1 = (long)(j + j1);
				if (k1 * k1 + l1 * l1 > 4096L && p_48646_.getValue((double)k1, (double)l1) < (double)-0.9F) {
					float f1 = (Mth.abs((float)k1) * 3439.0F + Mth.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
					float f2 = k - i1 * 2;
					float f3 = l - j1 * 2;
					float f4 = 100.0F - Mth.sqrt(f2 * f2 + f3 * f3) * f1;
					f4 = Mth.clamp(f4, -100.0F, 80.0F);
					f = Math.max(f, f4);
				}
			}
		}
		
		return f;
	}
}

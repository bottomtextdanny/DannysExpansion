package net.bottomtextdanny.danny_expannny.objects.world_gen.presets;

import net.bottomtextdanny.danny_expannny.object_tables.DEFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class EndBiomesPresets {
	
	public static Biome makeEmossenceBiome() {
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder()
			.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN)
			.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, DEFeatures.GIANT_FOAMSHROOM.getUniversalPlacement())
			;
		return makeEndBiome(biomegenerationsettings$builder);
	}
	
	private static Biome makeEndBiome(BiomeGenerationSettings.Builder generationSettingsBuilder) {
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.endSpawns(mobspawninfo$builder);
		return new Biome.BiomeBuilder()
			.precipitation(Biome.Precipitation.NONE)
			.biomeCategory(Biome.BiomeCategory.THEEND)
			.temperature(0.5F)
			.downfall(0.5F)
			.specialEffects(new BiomeSpecialEffects.Builder()
				.waterColor(4159204)
				.waterFogColor(329011)
				.fogColor(10518688)
				.skyColor(0)
				.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
				.build())
			.mobSpawnSettings(mobspawninfo$builder.build())
			.generationSettings(generationSettingsBuilder.build())
			.build();
	}
}

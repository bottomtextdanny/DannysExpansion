package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.world_gen.presets.EndBiomesPresets;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.DEBiomeRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public final class DEBiomes {
	public static final BCRegistry<Biome> ENTRIES = new BCRegistry<>(true);
	public static final RegistryHelper<Biome> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

	public static final DEBiomeRegistry EMOSSENCE = HELPER.deferWrap(DEBiomeRegistry.Builder.start("emossence", () -> EndBiomesPresets.makeEmossenceBiome())
			.putTypes(BiomeDictionary.Type.END, BiomeDictionary.Type.COLD)
			.build()
	);
}

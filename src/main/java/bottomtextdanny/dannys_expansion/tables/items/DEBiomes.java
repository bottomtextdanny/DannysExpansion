package bottomtextdanny.dannys_expansion.tables.items;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import net.minecraft.world.level.biome.Biome;

public final class DEBiomes {
	public static final BCRegistry<Biome> ENTRIES = new BCRegistry<>(true);
	public static final RegistryHelper<Biome> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

}

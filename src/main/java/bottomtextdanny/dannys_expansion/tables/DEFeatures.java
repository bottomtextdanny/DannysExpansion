package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class DEFeatures {
	public static final BCRegistry<Feature<?>> ENTRIES = new BCRegistry<>(true);
	public static final RegistryHelper<Feature<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);
}

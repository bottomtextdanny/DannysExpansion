package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;

public final class DEAttributes {
	public static final BCRegistry<Attribute> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<Attribute> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

}

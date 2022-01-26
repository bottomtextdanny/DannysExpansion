package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.world.entity.ai.attributes.Attribute;

public final class DEAttributes {
	public static final BCRegistry<Attribute> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<Attribute> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

}

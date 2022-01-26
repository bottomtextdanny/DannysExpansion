package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DERecipes {
	public static final BCRegistry<RecipeSerializer<?>> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<RecipeSerializer<?>> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

}

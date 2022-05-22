package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DERecipes {
	public static final BCRegistry<RecipeSerializer<?>> ENTRIES = new BCRegistry<>(false);
	public static final RegistryHelper<RecipeSerializer<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

}

package bottomtextdanny.dannys_expansion.compatibility;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeManager;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion.tables.items.DEBuildingItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

@JeiPlugin
public class JEIAddon implements IModPlugin {
    private static final List<LazyStationData> STATIONS = List.of(
            new LazyStationData(
                    () -> new ItemStack(DEBuildingItems.WORKBENCH.get()),
                    new ResourceLocation(DannysExpansion.ID, "workbench"),
                    LazyRecipeType.WORKBENCH),
            new LazyStationData(
                    () -> new ItemStack(DEBuildingItems.ENGINEERING_STATION.get()),
                    new ResourceLocation(DannysExpansion.ID, "engineering_station"),
                    LazyRecipeType.ENGINEERING_STATION)
    );

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DannysExpansion.ID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        for (LazyStationData data : STATIONS) {
            registration.addRecipeCategories(new LazyJEICategory(
                    guiHelper,
                    data.id,
                    data.itemIcon.get(),
                    DannysExpansion.common().getLazyRecipeManager().getRecipes(data.type).size()));
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        LazyRecipeManager manager = DannysExpansion.common().getLazyRecipeManager();
        for (LazyStationData data : STATIONS) {
            registration.addRecipes(manager.getRecipes(data.type), data.id);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      //  registration.addRecipeClickArea(WorkbenchScreen.class, 78, 32, 28, 23, WorkbenchJEICategory.ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        LazyRecipeManager manager = DannysExpansion.common().getLazyRecipeManager();
        for (LazyStationData data : STATIONS) {
            registration.addRecipeCatalyst(data.itemIcon.get(), data.id);
        }
    }

    public record LazyStationData(Supplier<ItemStack> itemIcon, ResourceLocation id, LazyRecipeType type) {
        public LazyStationData(Supplier<ItemStack> itemIcon, ResourceLocation id, LazyRecipeType type) {
            this.itemIcon = itemIcon;
            this.id = id;
            this.type = type;
        }
    }
}

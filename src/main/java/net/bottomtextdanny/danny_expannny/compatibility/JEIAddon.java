package net.bottomtextdanny.danny_expannny.compatibility;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEBuildingItems;
import net.bottomtextdanny.dannys_expansion.core.events.ResourceReloadHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEIAddon implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DannysExpansion.ID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(new LazyJEICategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addRecipes(ResourceReloadHandler.recipeManager.getRecipes(), LazyJEICategory.ID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      //  registration.addRecipeClickArea(WorkbenchScreen.class, 78, 32, 28, 23, WorkbenchJEICategory.ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(DEBuildingItems.WORKBENCH.get()), LazyJEICategory.ID);
    }
}

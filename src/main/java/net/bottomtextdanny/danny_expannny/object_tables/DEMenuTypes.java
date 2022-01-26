package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.screens.AccessoriesScreen;
import net.bottomtextdanny.danny_expannny.objects.screens.WorkbenchScreen;
import net.bottomtextdanny.danny_expannny.objects.containers.DannyAccessoriesContainer;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.WorkbenchContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;

public final class DEMenuTypes {
    public static final BCRegistry<MenuType<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<MenuType<?>> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

    public static final Wrap<MenuType<WorkbenchContainer>> WORKBENCH = defer("workbench", WorkbenchContainer::new);
    public static final Wrap<MenuType<DannyAccessoriesContainer>> DANNY_ACCESSORIES = defer("danny_accessories", DannyAccessoriesContainer::new);

    public static <T extends AbstractContainerMenu> Wrap<MenuType<T>> defer(String name, IContainerFactory<T> subject) {
        return HELPER.defer(name, () -> IForgeMenuType.create(subject));
    }

    public static void registerScreens() {
        MenuScreens.register(WORKBENCH.get(), WorkbenchScreen::new);
        MenuScreens.register(DANNY_ACCESSORIES.get(), AccessoriesScreen::new);
    }
}

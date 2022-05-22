package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.containers.DannyAccessoriesContainer;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.EngineeringStationMenu;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.WorkbenchMenu;
import bottomtextdanny.dannys_expansion.content._client.screens.AccessoriesScreen;
import bottomtextdanny.dannys_expansion.content._client.screens.lazy_workstation.EngineeringStationScreen;
import bottomtextdanny.dannys_expansion.content._client.screens.lazy_workstation.WorkbenchScreen;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;

public final class DEMenuTypes {
    public static final BCRegistry<MenuType<?>> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<MenuType<?>> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    public static final Wrap<MenuType<WorkbenchMenu>> WORKBENCH = defer("workbench", WorkbenchMenu::new);
    public static final Wrap<MenuType<EngineeringStationMenu>> ENGINEERING_STATION = defer("engineering_station", EngineeringStationMenu::new);
    public static final Wrap<MenuType<DannyAccessoriesContainer>> DANNY_ACCESSORIES = defer("danny_accessories", DannyAccessoriesContainer::new);

    public static <T extends AbstractContainerMenu> Wrap<MenuType<T>> defer(String name, IContainerFactory<T> subject) {
        return HELPER.defer(name, () -> IForgeMenuType.create(subject));
    }

    public static void registerScreens() {
        MenuScreens.register(WORKBENCH.get(), WorkbenchScreen::new);
        MenuScreens.register(ENGINEERING_STATION.get(), EngineeringStationScreen::new);
        MenuScreens.register(DANNY_ACCESSORIES.get(), AccessoriesScreen::new);
    }
}

package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.tables.DEAccessoryKeys;
import bottomtextdanny.dannys_expansion.content.accessories.CoreAccessory;
import bottomtextdanny.braincell.mod.capability.player.accessory.AllAccessoryCollectorEvent;
import bottomtextdanny.braincell.mod.capability.player.accessory.EmptyAccessoryKeyCollectorEvent;
import bottomtextdanny.braincell.mod.capability.player.accessory.IAccessory;

public class AccessoryHooks {

    public static void onAllAccessoriesCollection(AllAccessoryCollectorEvent event) {
        DEAccessoryModule module = PlayerHelper.accessoryModule(event.player);
        for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
            IAccessory accessory = module.getCoreAccessoryList().get(i);
            if (accessory != CoreAccessory.EMPTY) {
                event.addAccessory(accessory);
            }
        }
    }

    public static void onEmptyKeysCollection(EmptyAccessoryKeyCollectorEvent event) {
        event.addKey(DEAccessoryKeys.CORE_EMPTY);
    }
}

package bottomtextdanny.dannys_expansion._base.capabilities.player;

import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.BCPlayerCapability;
import bottomtextdanny.braincell.mod.capability.player.accessory.IAccessory;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class PlayerHelper {

    public static PlayerBowModule bowModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).bowModule();
    }

    public static PlayerGunModule gunModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).gunModule();
    }

    public static DEAccessoryModule accessoryModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).accessoryModule();
    }

    public static BCAccessoryModule braincellAccessoryModule(Player player) {
        return player.getCapability(BCPlayerCapability.TOKEN).orElse(null).getAccessories();
    }

    public static boolean hasAccessory(Player player, Class<? extends IAccessory> clazz) {
      return braincellAccessoryModule(player).isAccessoryTypeCurrent(clazz);
    }

    @Nullable
    public static <A extends IAccessory> A getAccessory(Player player, Class<A> clazz) {
        return braincellAccessoryModule(player).getAccessoryByType(clazz);
    }
}

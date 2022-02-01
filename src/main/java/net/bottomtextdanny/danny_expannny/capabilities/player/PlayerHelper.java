package net.bottomtextdanny.danny_expannny.capabilities.player;

import net.bottomtextdanny.danny_expannny.objects.accessories.CoreAccessory;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class PlayerHelper {

    public static PlayerBowModule bowModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).bowModule();
    }

    public static PlayerGunModule gunModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).gunModule();
    }

    public static PlayerAccessoryModule accessoryModule(Player player) {
        return player.getCapability(PlayerCapability.TOKEN).orElse(null).accessoryModule();
    }

    public static boolean hasAccessory(Player player, Class<? extends CoreAccessory> clazz) {
      return accessoryModule(player).isAccessoryTypeCurrent(clazz);
    }

    @Nullable
    public static <A extends IAccessory> A getAccessory(Player player, Class<A> clazz) {
        return accessoryModule(player).getAccessoryByType(clazz);
    }
}

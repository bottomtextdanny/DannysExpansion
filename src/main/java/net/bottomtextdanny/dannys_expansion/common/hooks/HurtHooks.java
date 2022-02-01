package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.BlueSlimeEntity;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnHurt;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.apache.commons.lang3.mutable.MutableObject;

public class HurtHooks {

    public static void livingHurtHook(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            handlePlayerHurt(player, event);
        }

        if (event.getEntity().getVehicle() instanceof BlueSlimeEntity mount) {
           handleBlueSlimeCase(mount, event);
        }
    }

    private static void handleBlueSlimeCase(BlueSlimeEntity mount, LivingHurtEvent event) {
        if (mount.getProtectiveTicks() > -1) {
            event.setCanceled(true);
        }
    }

    private static void handlePlayerHurt(Player player, LivingHurtEvent event) {
        PlayerAccessoryModule accessoryModule = CapabilityHelper.get(player, PlayerCapability.TOKEN).accessoryModule();
        MutableObject<Float> damageTransformer = new MutableObject<>(event.getAmount());
        accessoryModule.getAllAccessoryList().forEach(acc -> {
            if (acc instanceof FinnHurt hurtModule) {
                hurtModule.onHurt(event.getSource(), damageTransformer);
            }
        });
    }
}

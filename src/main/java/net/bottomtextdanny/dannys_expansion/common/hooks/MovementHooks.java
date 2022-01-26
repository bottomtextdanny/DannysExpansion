package net.bottomtextdanny.dannys_expansion.common.hooks;

import com.google.common.collect.Lists;
import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnFall;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.List;

public class MovementHooks {

    public static void livingFallHook(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            playerFallHook(player, event);
        }
    }

    private static void playerFallHook(Player player, LivingFallEvent event) {
        PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
        List<FinnFall> savedList = Lists.newLinkedList();

        accessoryModule.getCoreAccessoryList().forEach(a -> {
            if (a instanceof FinnFall fallModule) {
                savedList.add(fallModule);
                event.setDistance(fallModule.fallDistanceModifier(event.getDistance(), event.getDamageMultiplier()));

                event.setDamageMultiplier(fallModule.fallDamageMultModifier(event.getDistance(), event.getDamageMultiplier()));
            }
        });

        savedList.forEach(fallModule -> {
            if (fallModule.cancelFall(event.getDistance(), event.getDamageMultiplier())) {
                event.setCanceled(true);
            }
        });
    }

    public static void livingJumpHook(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            playerJumpHook(player, event);
        }
    }

    private static void playerJumpHook(Player player, LivingEvent.LivingJumpEvent event) {
        float f = player.getYRot() * DEMath.FRAD;
        float deltaFactor = Evaluations.EXTRA_JUMP_FORWARD.test(player) / 10.0F;

        if (deltaFactor > 0.0F) {
            player.setDeltaMovement(player.getDeltaMovement().add(-Mth.sin(f) * deltaFactor, 0.0D, Mth.cos(f) * deltaFactor));
        }
    }
}

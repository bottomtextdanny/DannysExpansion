package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCrumbsModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelScheduleModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelWindModule;
import net.minecraftforge.event.TickEvent;

public class TickHooks {

    public static void serverLevelTickHook(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            LevelCapability capability = CapabilityHelper.get(event.world, LevelCapability.CAPABILITY);
            LevelScheduleModule schedulesModule = capability.getScheduleModule();
            LevelWindModule windModule = capability.getWindModule();
            LevelCrumbsModule crumbsModule = capability.getCrumbsModule();

            schedulesModule.tick();
            windModule.tick();
            crumbsModule.tick();
        }
    }

    public static void playerTickHook(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null && event.player.isAlive()) {
            PlayerHelper.accessoryModule(event.player).tick();
            PlayerHelper.bowModule(event.player).tick();
            PlayerHelper.gunModule(event.player).tick();
        }
    }
}

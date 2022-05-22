package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelWindModule;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import net.minecraftforge.event.TickEvent;

public class TickHooks {

    public static void serverLevelTickHook(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            LevelCapability capability = CapabilityHelper.get(event.world, LevelCapability.TOKEN);
            LevelWindModule windModule = capability.getWindModule();

            windModule.tick();
        }
    }

    public static void playerTickHook(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.isAlive()) {
            if (event.phase == TickEvent.Phase.START) PlayerHelper.gunModule(event.player).preTick();
            else {
                PlayerHelper.bowModule(event.player).tick();
                PlayerHelper.gunModule(event.player).tick();
            }
        }
    }
}

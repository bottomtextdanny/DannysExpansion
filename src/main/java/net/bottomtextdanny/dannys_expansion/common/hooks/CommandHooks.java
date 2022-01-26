package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.danny_expannny.objects.commands.force_phase.PhaseCommands;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CommandHooks {

    public static void commandRegistryHook(RegisterCommandsEvent event) {
        PhaseCommands.register(event.getDispatcher());
    }
}

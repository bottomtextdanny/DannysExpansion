package bottomtextdanny.dannys_expansion._base.sensitive_hooks;

import bottomtextdanny.dannys_expansion.content.commands.force_phase.PhaseCommands;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CommandHooks {

    public static void commandRegistryHook(RegisterCommandsEvent event) {
        PhaseCommands.register(event.getDispatcher());
    }
}

package bottomtextdanny.dannys_expansion.content.commands;

import bottomtextdanny.dannys_expansion.content.commands.force_phase.PhaseArgument;
import bottomtextdanny.dannys_expansion.content.commands.force_phase.PhaseOrdinalArgument;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;

import static net.minecraft.commands.synchronization.ArgumentTypes.register;

public final class DECommandManager {

    public static void setup() {
        argumentTypes();
    }

    private static void argumentTypes() {
        register("phase", PhaseArgument.class, new EmptyArgumentSerializer<>(PhaseArgument::new));
        register("phase_ordinal", PhaseOrdinalArgument.class, new EmptyArgumentSerializer<>(PhaseOrdinalArgument::new));
    }

    private DECommandManager() {}
}

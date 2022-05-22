package bottomtextdanny.dannys_expansion.content.commands.force_phase;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.network.chat.TranslatableComponent;

public class PhaseOrdinalArgument implements ArgumentType<Integer> {
    private static final DynamicCommandExceptionType ERROR_NEGATIVE_ORDINAL = new DynamicCommandExceptionType(p_113041_ -> {
        return new TranslatableComponent("argument.dannys_expansion.phase_ordinal.negative_ordinal", p_113041_);
    });
    private static final DynamicCommandExceptionType ERROR_OUT_OF_BOUNDS_ORDINAL = new DynamicCommandExceptionType(p_113041_ -> {
        return new TranslatableComponent("argument.dannys_expansion.phase_ordinal.out_of_bounds_ordinal", p_113041_);
    });

    public static PhaseOrdinalArgument create() {
        return new PhaseOrdinalArgument();
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        int parsedPhaseOrdinal = reader.readInt();

        if (parsedPhaseOrdinal < 0) throw ERROR_NEGATIVE_ORDINAL.create(parsedPhaseOrdinal);
        else if (parsedPhaseOrdinal >= LevelPhaseModule.Phase.values().length) throw ERROR_OUT_OF_BOUNDS_ORDINAL.create(parsedPhaseOrdinal);

        return parsedPhaseOrdinal;
    }
}

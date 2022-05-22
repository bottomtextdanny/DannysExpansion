package bottomtextdanny.dannys_expansion.content.commands.force_phase;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.network.chat.TranslatableComponent;

public class PhaseArgument implements ArgumentType<String> {
    private static final DynamicCommandExceptionType ERROR_INVALID_REFERENCE = new DynamicCommandExceptionType(p_113041_ -> {
        return new TranslatableComponent("argument.dannys_expansion.phase.invalid_reference", p_113041_);
    });

    public static PhaseArgument create() {
        return new PhaseArgument();
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String extracted = reader.readUnquotedString();

        if (LevelPhaseModule.Phase.STRING_TO_ORDINAL.containsKey(extracted)) {
            return extracted;
        } else throw ERROR_INVALID_REFERENCE.create(extracted);
    }
}

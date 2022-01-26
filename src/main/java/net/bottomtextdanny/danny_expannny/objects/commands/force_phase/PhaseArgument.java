package net.bottomtextdanny.danny_expannny.objects.commands.force_phase;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
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

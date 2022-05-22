package bottomtextdanny.dannys_expansion.content.commands.force_phase;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.dannys_expansion.tables.DESuggestionProviders;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class PhaseCommands {

    public static void register(CommandDispatcher<CommandSourceStack> commandStack) {
        RequiredArgumentBuilder<CommandSourceStack, String> phaseStringArgument = Commands.argument("phase", PhaseArgument.create()).suggests(DESuggestionProviders.ALL_PHASES).executes(sourceStack -> {
            return changePhase(sourceStack.getSource(), LevelPhaseModule.Phase.STRING_TO_ORDINAL.get(StringArgumentType.getString(sourceStack, "phase")));
        });

        RequiredArgumentBuilder<CommandSourceStack, Integer> phaseOrdinalArgument = Commands.argument("phase_ordinal", PhaseOrdinalArgument.create()).executes(sourceStack -> {
            return changePhase(sourceStack.getSource(), IntegerArgumentType.getInteger(sourceStack, "phase_ordinal"));
        });

        commandStack.register(Commands.literal("force_phase").requires(p_138159_ -> {
            return p_138159_.hasPermission(Commands.LEVEL_GAMEMASTERS);
        }).then(phaseStringArgument).then(phaseOrdinalArgument));
    }

    private static int changePhase(CommandSourceStack sourceStack, int ordinal) {
        LevelPhaseModule module = CapabilityHelper.get(sourceStack.getLevel(), LevelCapability.TOKEN).getPhaseModule();
        LevelPhaseModule.Phase previousPhase = module.getPhase();
        LevelPhaseModule.Phase newPhase = LevelPhaseModule.Phase.values()[ordinal];
        sourceStack.sendSuccess(new TranslatableComponent("danny_commands.force_phase.success", previousPhase.name, newPhase.name), true);
        module.forcePhase(newPhase);
        return ordinal;
    }
}

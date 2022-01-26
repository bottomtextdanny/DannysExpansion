package net.bottomtextdanny.danny_expannny.object_tables;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.resources.ResourceLocation;

public final class DESuggestionProviders extends SuggestionProviders {

    public static final SuggestionProvider<CommandSourceStack> ALL_PHASES = register(new ResourceLocation("all_phases"), (p_121670_, p_121671_) -> {
        return SharedSuggestionProvider.suggest(LevelPhaseModule.Phase.names, p_121671_);
    });

    public static final SuggestionProvider<CommandSourceStack> ALL_PHASES_ORDINAL = register(new ResourceLocation("all_phases_ordinal"), (p_121670_, p_121671_) -> {
        return SharedSuggestionProvider.suggest(LevelPhaseModule.Phase.ordinals, p_121671_);
    });
}

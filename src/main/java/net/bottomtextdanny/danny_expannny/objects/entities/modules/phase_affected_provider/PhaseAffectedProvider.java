package net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider;

import net.bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;

public interface PhaseAffectedProvider extends ModuleProvider {

    PhaseAffectedModule phaseAffectedModule();

    default boolean operatingPhaseAffectedModule() {
        return phaseAffectedModule() != null;
    }

    default LevelPhaseModule.Phase getPhaseWhenSpawned() {
        return phaseAffectedModule().getPhaseSpawned();
    }
}

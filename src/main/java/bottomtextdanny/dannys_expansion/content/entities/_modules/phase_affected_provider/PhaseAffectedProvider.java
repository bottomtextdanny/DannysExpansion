package bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import bottomtextdanny.braincell.mod.entity.modules.ModuleProvider;

public interface PhaseAffectedProvider extends ModuleProvider {

    PhaseAffectedModule phaseAffectedModule();

    default boolean operatingPhaseAffectedModule() {
        return phaseAffectedModule() != null;
    }

    default LevelPhaseModule.Phase getPhaseWhenSpawned() {
        return phaseAffectedModule().getPhaseSpawned();
    }
}

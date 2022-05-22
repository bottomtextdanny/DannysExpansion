package bottomtextdanny.dannys_expansion.content.entities._modules.phase_affected_provider;

import bottomtextdanny.dannys_expansion._base.capabilities.world.LevelPhaseModule;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface AttributeFunction<E extends LivingEntity> {

    double extractor(LevelPhaseModule.Phase phase, E entity, double baseValue);

    @SuppressWarnings("unchecked")
    default double calculate(LevelPhaseModule.Phase phase, LivingEntity entity, double baseValue) {
        return extractor(phase, (E)entity, baseValue);
    }
}

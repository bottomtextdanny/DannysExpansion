package net.bottomtextdanny.danny_expannny.objects.entities.modules.phase_affected_provider;

import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface AttributeFunction<E extends LivingEntity> {

    double extractor(LevelPhaseModule.Phase phase, E entity, double baseValue);

    @SuppressWarnings("unchecked")
    default double calculate(LevelPhaseModule.Phase phase, LivingEntity entity, double baseValue) {
        return extractor(phase, (E)entity, baseValue);
    }
}

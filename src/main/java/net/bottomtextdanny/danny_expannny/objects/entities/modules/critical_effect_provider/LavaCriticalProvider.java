package net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.objects.particles.CustomCriticalParticle;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;

public interface LavaCriticalProvider extends CriticalEffectProvider {

    @Override
    default DannyParticleData criticalParticle() {
        return new DannyParticleData(DEParticles.CRITICAL_HIT, CustomCriticalParticle.LAVA_IDX);
    }
}

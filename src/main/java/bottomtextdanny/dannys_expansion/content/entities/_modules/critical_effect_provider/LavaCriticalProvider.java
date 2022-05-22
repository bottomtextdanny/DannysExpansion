package bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider;

import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.content._client.particles.CustomCriticalParticle;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;

public interface LavaCriticalProvider extends CriticalEffectProvider {

    @Override
    default DannyParticleData criticalParticle() {
        return new DannyParticleData(DEParticles.CRITICAL_HIT, CustomCriticalParticle.LAVA_IDX);
    }
}

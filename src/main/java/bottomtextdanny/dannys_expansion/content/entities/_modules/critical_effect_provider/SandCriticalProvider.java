package bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider;

import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.content._client.particles.CustomCriticalParticle;
import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface SandCriticalProvider extends CriticalEffectProvider {

    @OnlyIn(Dist.CLIENT)
    @Override
    default DannyParticleData criticalParticle() {
        return new DannyParticleData(DEParticles.CRITICAL_HIT, CustomCriticalParticle.SAND_IDX);
    }
}

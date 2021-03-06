package bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public interface CriticalEffectProvider {

    @OnlyIn(Dist.CLIENT)
    ParticleOptions criticalParticle();
}

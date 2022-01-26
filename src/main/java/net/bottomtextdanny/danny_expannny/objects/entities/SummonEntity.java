package net.bottomtextdanny.danny_expannny.objects.entities;

import net.bottomtextdanny.braincell.mod.world.builtin_entities.ModuledMob;
import net.minecraft.core.particles.ParticleOptions;

import javax.annotation.Nullable;

public interface SummonEntity {

    ParticleOptions getDeathParticle();

    void setSummoner(ModuledMob livingEntity);

    void setTamed(boolean v);

    @Nullable
    ModuledMob getSummoner();

    boolean isTamed();
    
}

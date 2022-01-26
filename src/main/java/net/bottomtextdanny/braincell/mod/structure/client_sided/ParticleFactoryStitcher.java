package net.bottomtextdanny.braincell.mod.structure.client_sided;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class ParticleFactoryStitcher<D extends ParticleOptions> {
    private final Supplier<? extends ParticleType<D>> type;
    private final ParticleEngine.SpriteParticleRegistration<D> spriteFactory;

    public ParticleFactoryStitcher(Supplier<? extends ParticleType<D>> type, ParticleEngine.SpriteParticleRegistration<D> data) {
        this.type = type;
        this.spriteFactory = data;
    }

    @OnlyIn(Dist.CLIENT)
    public void stitch() {
        Minecraft.getInstance().particleEngine.register(this.type.get(), this.spriteFactory);
    }
}

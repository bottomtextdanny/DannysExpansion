package net.bottomtextdanny.braincell.mod.structure.client_sided;


import com.google.common.collect.Lists;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.function.Supplier;


public final class ParticleFactoryDeferror {
    public final List<ParticleFactoryStitcher<?>> stitchers = Lists.newLinkedList();

    public ParticleFactoryDeferror() {
        super();

    }

    public void sendListeners() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::putFactories);
    }

    public <D extends ParticleOptions, T extends ParticleType<D>> void addFactoryStitch(
            Supplier<T> particleType,
            ParticleEngine.SpriteParticleRegistration<D> factory) {
        this.stitchers.add(new ParticleFactoryStitcher<>(particleType, factory));
    }

    @OnlyIn(Dist.CLIENT)
    private void putFactories(ParticleFactoryRegisterEvent event) {
        this.stitchers.forEach(ParticleFactoryStitcher::stitch);
    }
}

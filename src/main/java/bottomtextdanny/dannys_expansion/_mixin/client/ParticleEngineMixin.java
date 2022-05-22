package bottomtextdanny.dannys_expansion._mixin.client;

import bottomtextdanny.dannys_expansion.content.entities._modules.critical_effect_provider.CriticalEffectProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    protected ClientLevel level;
    @Shadow
    @Final
    private Queue<TrackingEmitter> trackingEmitters;

    public ParticleEngineMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;)V"},
            remap = true,
            cancellable = true
    )
    public void createTrackingEmitterHook(Entity hitEntity, ParticleOptions particle, CallbackInfo ci) {
        if (hitEntity instanceof CriticalEffectProvider) {
            CriticalEffectProvider provider = (CriticalEffectProvider)hitEntity;
            this.trackingEmitters.add(new TrackingEmitter(this.level, hitEntity, provider.criticalParticle()));
            ci.cancel();
        }
    }
}

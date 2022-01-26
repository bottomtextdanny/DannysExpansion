package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.danny_expannny.objects.entities.modules.critical_effect_provider.CriticalEffectProvider;
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

    @Shadow protected ClientLevel level;

    @Shadow @Final private Queue<TrackingEmitter> trackingEmitters;

    @Inject(at = @At("HEAD"), method = "createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;)V", cancellable = true)
    public void createTrackingEmitterHook(Entity hitEntity, ParticleOptions particle, CallbackInfo ci) {
        if (hitEntity instanceof CriticalEffectProvider provider) {
            this.trackingEmitters.add(new TrackingEmitter(this.level, hitEntity, provider.criticalParticle()));
            ci.cancel();
        }
    }
}

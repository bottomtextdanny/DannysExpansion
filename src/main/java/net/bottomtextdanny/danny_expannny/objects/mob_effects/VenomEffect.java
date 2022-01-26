package net.bottomtextdanny.danny_expannny.objects.mob_effects;

import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.bottomtextdanny.dannys_expansion.core.data.DEDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VenomEffect extends MobEffect {
    private final Timer hurtDelay = new Timer(40);
    int duration;

    public VenomEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        super.applyEffectTick(entityLivingBaseIn, amplifier);

        this.hurtDelay.tryUp();

        if (this.hurtDelay.hasEnded()) {
            entityLivingBaseIn.hurt(DEDamageSources.VENOM, 0.7F + 0.2F * (amplifier + 1));
            this.hurtDelay.reset();
        }

        if (this.duration == 1) {
            entityLivingBaseIn.removeEffect(this);
        }
    }


    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }
}

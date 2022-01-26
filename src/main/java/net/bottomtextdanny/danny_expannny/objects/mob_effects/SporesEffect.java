package net.bottomtextdanny.danny_expannny.objects.mob_effects;

import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporerEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.SporeSlimeEntity;
import net.bottomtextdanny.dannys_expansion.core.data.DEDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SporesEffect extends MobEffect {
    int hurtDelay = 30;
    int duration;

    public SporesEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        super.applyEffectTick(entityLivingBaseIn, amplifier);

        if (this.hurtDelay > 0) {
            --this.hurtDelay;
        } else if (!(entityLivingBaseIn instanceof SporerEntity) && !(entityLivingBaseIn instanceof SporeSlimeEntity)) {
            entityLivingBaseIn.hurt(DEDamageSources.SPORES, 0.7F * (amplifier + 1));
            entityLivingBaseIn.invulnerableTime = 1;
            this.hurtDelay = 30;
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

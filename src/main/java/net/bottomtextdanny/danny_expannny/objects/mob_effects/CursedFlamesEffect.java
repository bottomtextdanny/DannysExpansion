package net.bottomtextdanny.danny_expannny.objects.mob_effects;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.dannys_expansion.core.data.DEDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;


public class CursedFlamesEffect extends MobEffect {
    int burnDelay = 20;
    int duration;

    public CursedFlamesEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        super.applyEffectTick(entityLivingBaseIn, amplifier);
        Random rand = new Random();
        double d0 = rand.nextGaussian() * (entityLivingBaseIn.getBoundingBox().getXsize() + 0.15) / 2 ;
        double d1 = rand.nextFloat() * entityLivingBaseIn.getBoundingBox().getYsize() + 0.1;
        double d2 = rand.nextGaussian() * (entityLivingBaseIn.getBoundingBox().getXsize() + 0.15) / 2 ;

        if (this.burnDelay > 0) {
            --this.burnDelay;
        } else {
            entityLivingBaseIn.hurt(DEDamageSources.CURSED_FLAMES, 1.0F * (amplifier + 1));
            entityLivingBaseIn.invulnerableTime = 1;
            this.burnDelay = 20;
        }

        entityLivingBaseIn.level.addParticle(DEParticles.CURSED_FLAME.get(), entityLivingBaseIn.getX() + d0, entityLivingBaseIn.getY() + d1, entityLivingBaseIn.getZ() + d2, 0.0F, 0.0F, 0.0F);

        if (this.duration == 1) {
            entityLivingBaseIn.removeEffect(this);
        }
    }


    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }
}

package bottomtextdanny.dannys_expansion.content.mob_effects;

import bottomtextdanny.dannys_expansion.tables.DEDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VenomEffect extends MobEffect {

    public VenomEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living.tickCount % 30 == 0) {
            living.hurt(DEDamageSources.VENOM, 0.7F + 0.2F * (amplifier + 1));
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

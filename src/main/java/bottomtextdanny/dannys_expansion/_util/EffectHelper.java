package bottomtextdanny.dannys_expansion._util;

import bottomtextdanny.dannys_expansion.tables.DEEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public final class EffectHelper {

    public static boolean isToxin(MobEffect effect) {
        return is(effect, DEEffects.TOXIN);
    }

    public static boolean isBadAndOnlyAppliesToLiving(MobEffect effect) {
        return is(effect, DEEffects.DEGENERATIVE);
    }

    private static boolean is(MobEffect effect, TagKey<MobEffect> tag) {
        try {
            Registry<MobEffect> registry = Registry.MOB_EFFECT;

            Holder<MobEffect> holder = registry.getHolder(registry.getId(effect)).get();

            return holder.is(tag);
        } catch (Exception ex) {
            return false;
        }
    }

    private EffectHelper() {}
}

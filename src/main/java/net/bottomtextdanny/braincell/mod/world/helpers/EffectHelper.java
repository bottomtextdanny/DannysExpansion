package net.bottomtextdanny.braincell.mod.world.helpers;

import com.google.common.collect.Sets;
import net.bottomtextdanny.danny_expannny.object_tables.DEEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.Set;

public final class EffectHelper {
    private static final Set<MobEffect> BAD_EFFECTS_ONLY_APPLYING_TO_BONE_AND_SKIN = Sets.newIdentityHashSet();
    private static final Set<MobEffect> TOXIN_EFFECTS = Sets.newIdentityHashSet();

    public static boolean isToxin(MobEffect effect) {
        return TOXIN_EFFECTS.contains(effect);
    }

    public static boolean isBadAndOnlyAppliesToLiving(MobEffect effect) {
        return BAD_EFFECTS_ONLY_APPLYING_TO_BONE_AND_SKIN.contains(effect);
    }

    public static void init() {
        Set<MobEffect> set = BAD_EFFECTS_ONLY_APPLYING_TO_BONE_AND_SKIN;

        set.add(MobEffects.POISON);
        set.add(MobEffects.HUNGER);
        set.add(MobEffects.CONFUSION);
        set.add(DEEffects.VENOM.get());

        set = TOXIN_EFFECTS;

        set.add(MobEffects.POISON);
        set.add(DEEffects.VENOM.get());
    }
}

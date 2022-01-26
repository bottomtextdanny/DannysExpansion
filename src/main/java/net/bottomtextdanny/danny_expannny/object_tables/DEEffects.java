package net.bottomtextdanny.danny_expannny.object_tables;

import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.BCRegistry;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.RegistryHelper;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.Wrap;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.mob_effects.CursedFlamesEffect;
import net.bottomtextdanny.danny_expannny.objects.mob_effects.SporesEffect;
import net.bottomtextdanny.danny_expannny.objects.mob_effects.VenomEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class DEEffects {
    public static final BCRegistry<MobEffect> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<MobEffect> HELPER = new RegistryHelper<>(DannysExpansion.solvingState, ENTRIES);

    public static final Wrap<MobEffect> CURSED_FLAMES = HELPER.defer("cursed_flames", () -> new CursedFlamesEffect(MobEffectCategory.HARMFUL, 6610724));
	public static final Wrap<MobEffect> SPORES = HELPER.defer("spores", () -> new SporesEffect(MobEffectCategory.HARMFUL, 6435235));
    public static final Wrap<MobEffect> VENOM = HELPER.defer("venom", () -> new VenomEffect(MobEffectCategory.HARMFUL, 4593008));
}

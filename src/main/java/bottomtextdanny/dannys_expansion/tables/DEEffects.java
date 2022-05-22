package bottomtextdanny.dannys_expansion.tables;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.mob_effects.VenomEffect;
import bottomtextdanny.braincell.mod._base.registry.managing.BCRegistry;
import bottomtextdanny.braincell.mod._base.registry.managing.RegistryHelper;
import bottomtextdanny.braincell.mod._base.registry.managing.Wrap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class DEEffects {
    public static final BCRegistry<MobEffect> ENTRIES = new BCRegistry<>(false);
    public static final RegistryHelper<MobEffect> HELPER = new RegistryHelper<>(DannysExpansion.DE_REGISTRY_MANAGER, ENTRIES);

    public static final TagKey<MobEffect> DEGENERATIVE = bindTag("bad_to_living_only");
    public static final TagKey<MobEffect> TOXIN = bindTag("toxin");

    public static final Wrap<MobEffect> VENOM = HELPER.defer("venom", () -> new VenomEffect(MobEffectCategory.HARMFUL, 4593008));

    private static TagKey<MobEffect> bindTag(String location) {
        return TagKey.create(Registry.MOB_EFFECT_REGISTRY, new ResourceLocation(DannysExpansion.ID, location));
    }
}

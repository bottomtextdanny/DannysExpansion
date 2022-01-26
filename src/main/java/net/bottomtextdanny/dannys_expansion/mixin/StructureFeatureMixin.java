package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.dannys_expansion.core.Registries.DannyStructures;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.function.BiConsumer;

@Mixin(StructureFeatures.class)
public abstract class StructureFeatureMixin {

    @Shadow
    private static void register(BiConsumer<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> p_194764_, ConfiguredStructureFeature<?, ?> p_194765_, ResourceKey<Biome> p_194766_) {
    }

    @Shadow
    private static void register(BiConsumer<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> p_194760_, ConfiguredStructureFeature<?, ?> p_194761_, Set<ResourceKey<Biome>> p_194762_) {
    }

    @Inject(at = @At("TAIL"), method = "registerStructures", remap = false)
    private static void registerStructures(BiConsumer<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> p_194758_, CallbackInfo ci) {
        ConfiguredStructureFeature<NoneFeatureConfiguration, ?> anglersTreasure = DannyStructures.ANGLERS_TREASURE.config(NoneFeatureConfiguration.NONE);

        register(p_194758_, anglersTreasure, Biomes.OCEAN);
        register(p_194758_, anglersTreasure, Biomes.COLD_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.WARM_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.DEEP_COLD_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.DEEP_LUKEWARM_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.DEEP_FROZEN_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.LUKEWARM_OCEAN);
        register(p_194758_, anglersTreasure, Biomes.FROZEN_OCEAN);
    }
}

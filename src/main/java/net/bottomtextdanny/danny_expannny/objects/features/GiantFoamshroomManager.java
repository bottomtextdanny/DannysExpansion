package net.bottomtextdanny.danny_expannny.objects.features;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.world_gen.features.end.GiantFoamshroomFeature;
import net.bottomtextdanny.dannys_expansion.core.Util.setup.FeatureManager;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GiantFoamshroomManager extends FeatureManager<NoneFeatureConfiguration> {
    public static final ResourceLocation UNIVERSAL_CONFIGURATION_KEY =
            new ResourceLocation(DannysExpansion.ID, "giant_foamshroom");
    public static final ResourceLocation UNIVERSAL_PLACEMENT_KEY =
            new ResourceLocation(DannysExpansion.ID, "giant_foamshroom");
    private PlacedFeature universalPlacement;

    public GiantFoamshroomManager() {
        super(new ResourceLocation(DannysExpansion.ID, "giant_foamshroom"), GiantFoamshroomFeature::new);
    }

    @Override
    public void solve() {
        super.solve();
        ConfiguredFeature<NoneFeatureConfiguration, ?> configuredFeature =
                makeConfiguration(UNIVERSAL_CONFIGURATION_KEY, FeatureConfiguration.NONE);
        this.universalPlacement =
                makePlacement(
                        UNIVERSAL_PLACEMENT_KEY,
                        configuredFeature,
                        NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE
                );
    }

    public PlacedFeature getUniversalPlacement() {
        checkSolvingState();
        return this.universalPlacement;
    }
}

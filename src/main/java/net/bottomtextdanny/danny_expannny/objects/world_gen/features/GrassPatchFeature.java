package net.bottomtextdanny.danny_expannny.objects.world_gen.features;

import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.GrassPatchDeco;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Function;

public class GrassPatchFeature extends Feature<NoneFeatureConfiguration> {
	public final Function<WorldGenLevel, GrassPatchDeco> deco;
	
    public GrassPatchFeature(Function<WorldGenLevel, GrassPatchDeco> deco) {
        super(NoneFeatureConfiguration.CODEC);
        this.deco = deco;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        this.deco.apply(ctx.level()).generate(ctx.origin().below());
        return true;
    }
}

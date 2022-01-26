package net.bottomtextdanny.danny_expannny.objects.world_gen.features;

import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.GrassSingleDeco;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Function;

public class SingleGrassFeature extends Feature<NoneFeatureConfiguration> {
	public final Function<WorldGenLevel, GrassSingleDeco> deco;
	
    public SingleGrassFeature(Function<WorldGenLevel, GrassSingleDeco> deco) {
        super(NoneFeatureConfiguration.CODEC);
        this.deco = deco;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        this.deco.apply(ctx.level()).generate(ctx.origin().below());
        return true;
    }
}

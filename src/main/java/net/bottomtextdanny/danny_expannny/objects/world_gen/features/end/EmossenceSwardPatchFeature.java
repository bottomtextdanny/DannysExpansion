package net.bottomtextdanny.danny_expannny.objects.world_gen.features.end;

import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.EmossenceGrassPatchDeco;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EmossenceSwardPatchFeature extends Feature<NoneFeatureConfiguration> {

    public EmossenceSwardPatchFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        new EmossenceGrassPatchDeco(ctx.level(), DEUtil.S_RANDOM.split()).generate(ctx.origin().below());
        return true;
    }
}

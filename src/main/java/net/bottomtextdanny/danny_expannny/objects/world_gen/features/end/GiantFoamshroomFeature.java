package net.bottomtextdanny.danny_expannny.objects.world_gen.features.end;

import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.GiantFoamshroomDeco;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GiantFoamshroomFeature extends Feature<NoneFeatureConfiguration> {


    public GiantFoamshroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        new GiantFoamshroomDeco(ctx.level(), ctx.random()).generate(new BlockPos(ctx.origin().getX(), 70, ctx.origin().getZ()));
        return true;
    }
}

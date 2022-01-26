package net.bottomtextdanny.danny_expannny.objects.world_gen.features.end;

import com.mojang.serialization.Codec;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.object_tables.DETags;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.random.SimplexNoiseMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class EmossencePatchLarge extends Feature<NoneFeatureConfiguration> {


    public EmossencePatchLarge(Codec<NoneFeatureConfiguration> codec) {

        super(codec);

    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        BlockPos pos = ctx.origin();
        WorldGenLevel reader = ctx.level();
        ChunkGenerator generator = ctx.chunkGenerator();
        pos = new BlockPos(pos.getX(), 0, pos.getZ());
        SimplexNoiseMapper noise = new SimplexNoiseMapper(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.S_RANDOM.split());
        float radius = 9.4F;
        float noiseRange = 5.5F;
        Set<BlockPos> avPositions = new HashSet<>();
        float noiseRadius = radius + noiseRange * 3;

        for (int x = -Mth.ceil(noiseRadius); x <= noiseRadius + 1; x++) {
            for (int z = -Mth.ceil(noiseRadius); z <= noiseRadius + 1; z++) {
                BlockPos iterationPos = pos.offset(x, generator.getBaseHeight(pos.getX() + x, pos.getZ() + z, Heightmap.Types.WORLD_SURFACE_WG, reader) - 1, z);
                iterationPos = PieceUtil.vBlocksNoCollision(reader, iterationPos, 8);
                if (iterationPos != null) {
                    if (reader.getBlockState(iterationPos).is(DETags.EMOSSENCE_PLACEABLE_ON) && !PieceUtil.fullCollision(reader, iterationPos.above())) {
                        double dist = DEMath.getHorizontalDistance(pos.getX(), pos.getZ(), iterationPos.getX(), iterationPos.getZ());
                        float yawToIter = DEMath.getTargetYaw(pos.getX(), pos.getZ(), pos.getX() + x, pos.getZ() + z);
                        float radianYaw = (float)Math.toRadians(yawToIter);

                        if (dist < radius + noiseRange * (noise.noise(66.753, DEMath.sin(radianYaw) * 1.1, DEMath.cos(radianYaw) * 1.1) + noise.noise(66.753, Math.sin(radianYaw) * 2.1, Math.cos(radianYaw) * 2.1)) * 0.5) {
                            reader.setBlock(iterationPos, DEBlocks.NEITHINE.get().defaultBlockState(), 1);
                            avPositions.add(iterationPos);
                        }
                    }

                }
            }
        }

        return true;
    }



    @Nullable
    public static BlockPos verticalNonSolid(LevelAccessor world, BlockPos pos, int max) {
        int i;
        int j = 0;
        
        if (PieceUtil.noCollision(world, pos)) {
            for(i = 0; PieceUtil.noCollision(world, pos.offset(0F, j - 1, 0F)); ++i) {
                if (i < max) {
                    j--;
                } else {
                    return null;
                }
            }
        }
    
        return pos.offset(0, j, 0);
    }
}

package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.bottomtextdanny.dannys_expansion.core.Util.random.Noise;
import net.bottomtextdanny.dannys_expansion.core.Util.random.SimplexNoiseMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class GiantFoamshroomDeco extends PosDecorator {

    public GiantFoamshroomDeco(WorldGenLevel world, Random rand) {
        super(world, rand);
    }

    @Override
    public void generate(BlockPos position) {
        SplittableRandom localRand = net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.S_RANDOM.split();
        final Noise noise1 = new Noise(localRand);
        final Noise noise2 = new Noise(localRand);

        final float baseYaw = this.random.nextFloat() * 360;
        final float basePitch = -85;
        final int blocks = 25 + Mth.floor(Easing.EASE_IN_CUBIC.progression(this.random.nextFloat())) * 12;
        int stemNumber;

        float stemRand = this.random.nextFloat();

        if (stemRand < 0.09) {
            stemNumber = 0;
        } else if (stemRand < 0.25) {
            stemNumber = 1;
        } else if (stemRand < 0.90) {
            stemNumber = 2;
        } else {
            stemNumber = 3;
        }

        int[] stemArray = new int[stemNumber];
        Map<BlockPos, Float> stemMap = new HashMap<>();
        final int stemHeightBase = Mth.floor((float)blocks / (stemNumber + 1));

        Vec3 iterationPos = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);

        if (stemNumber > 0) {
            for (int j = 0; j < stemNumber; j++) {
                int iterationStemHeight = stemHeightBase * (j + 1) - 5 + this.random.nextInt(3);

                stemArray[j] = iterationStemHeight;
            }
        }

        for (int i = 0; i < blocks; i++) {
            float multiplier;

            if (i <= 5) {
                multiplier = Easing.EASE_IN_CUBIC.progression((float)i / 5);
            } else {
                multiplier = 1F;
            }

            double yaw = baseYaw + noise1.getNext(0.08F) * 360 * multiplier;
            double pitch = Mth.wrapDegrees(basePitch + noise2.getNext(0.1F) * 10) + multiplier * 10;
            Vec3 rotVector = DEMath.fromPitchYaw((float)pitch, (float)yaw);

            float stemYawOffset = this.random.nextInt(360);

            for (int stemPos : stemArray) {
                if (i == stemPos) {
                    final double randomGaussian = this.random.nextGaussian();

                    stemMap.put(new BlockPos(iterationPos), stemYawOffset);
                    stemYawOffset += Mth.sign(randomGaussian) * Mth.abs((float) (randomGaussian * 40 + 80));
                }
            }

            iterationPos = iterationPos.add(rotVector.x, rotVector.y, rotVector.z);

            BlockPos finalPos = new BlockPos(iterationPos);
            if (PieceUtil.fullCollision(this.world, finalPos.above())) break;
            PieceUtil.setBlock(this.world, finalPos, DEBlocks.FOAMSHROOM_STEM.get().defaultBlockState(), 2);
        }

        stemMap.forEach((bp, f) -> {
            createStem(bp, 8 + this.random.nextInt(4), f, localRand);
        });

        createCap(new BlockPos(iterationPos).above(), 4F, 1.9F, 2, localRand);
    }

    public BlockPos createStem(BlockPos pos, int blocks, double avoidYaw, SplittableRandom localRand) {
        final Noise noise1 = new Noise(localRand);
        final Noise noise2 = new Noise(localRand);
        Vec3 iterationPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

        for (int i = 0; i < blocks; i++) {
            float multiplier = Easing.EASE_IN_SQUARE.progression((float)i / blocks);

            double yaw = Mth.wrapDegrees(avoidYaw + 180) + noise1.getNext(0.08F) * 180 + noise1.getNext(0.05F) * multiplier;
            double pitch = -10 + Mth.wrapDegrees(noise2.getNext(0.1F) * 10) + multiplier * -90;
            Vec3 rotVector = DEMath.fromPitchYaw((float)pitch, (float)yaw);

            iterationPos = iterationPos.add(rotVector.x, rotVector.y, rotVector.z);

            PieceUtil.setBlock(this.world, new BlockPos(iterationPos), DEBlocks.FOAMSHROOM_STEM.get().defaultBlockState(), 2);
        }

        createCap(new BlockPos(iterationPos).above(), 2.6F, 0.9F, 2, localRand);

        return new BlockPos(iterationPos);
    }

    public void createCap(BlockPos pos, float baseRadius, float noiseMag, int layers, SplittableRandom localRand) {
        final SimplexNoiseMapper circleMut = new SimplexNoiseMapper(this.random);
        final Set<BlockPos> deletionQueue = new HashSet<>();

        for (int i = 0; i < layers; i++) {
            float radius = baseRadius + noiseMag;
            float radiusMult = 1 + Easing.EASE_OUT_SQUARE.progression((float)i / layers);

            int bound = Mth.floor(radius * radiusMult);
            for (int x = -bound; x <= bound; x++) {
                for (int z = -bound; z <= bound; z++) {
                    BlockPos iterationPos = new BlockPos(pos.getX() + x, pos.getY() - i, pos.getZ() + z);
                    double dist = DEMath.getHorizontalDistance(pos.getX(), pos.getZ(), pos.getX() + x, pos.getZ() + z);
                    double yawToIter = DEMath.getTargetYaw(pos.getX(), pos.getZ(), pos.getX() + x, pos.getZ() + z);
                    float radYaw = (float)Math.toRadians(yawToIter);
                    double mutatedRadius = (baseRadius + circleMut.noise(66.753 + i * 0.06, DEMath.sin(radYaw) * 0.4, DEMath.cos(radYaw) * 0.4) * noiseMag) * radiusMult;

                    if (dist < mutatedRadius && !PieceUtil.fullCollision(this.world, iterationPos)) {
                        if (this.world.getBlockState(iterationPos.above()).getBlock() == DEBlocks.FOAMSHROOM_BLOCK.get()) {
                            deletionQueue.add(iterationPos);
                        }

                        PieceUtil.setBlock(this.world, iterationPos, DEBlocks.FOAMSHROOM_BLOCK.get().defaultBlockState(), 2);
                    }
                }
            }
        }

        deletionQueue.forEach(bp -> PieceUtil.setBlock(this.world, bp, Blocks.AIR.defaultBlockState(), 2));
    }


}

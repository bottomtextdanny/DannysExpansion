package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.enderbeastregion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;

import java.util.Random;

public class EnderBeastRegionPiece extends ScatteredFeaturePiece {
	protected EnderBeastRegionPiece(StructurePieceType p_163188_, int p_163189_, int p_163190_, int p_163191_, int p_163192_, int p_163193_, int p_163194_, Direction p_163195_) {
		super(p_163188_, p_163189_, p_163190_, p_163191_, p_163192_, p_163193_, p_163194_, p_163195_);
	}
	
	@Override
	public void postProcess(WorldGenLevel p_192637_, StructureFeatureManager p_192638_, ChunkGenerator p_192639_, Random p_192640_, BoundingBox p_192641_, ChunkPos p_192642_, BlockPos p_192643_) {
	
	}

//    public EnderBeastRegionPiece(Random random, int x, int z) {
//        super(DannyStructures.ENDER_BEAST_REGION.piece, x, 64, z, 7, 7, 9, Direction.NORTH);
//    }
//
//    public EnderBeastRegionPiece(ServerLevel templateManager, CompoundTag compoundNBT) {
//        super(DannyStructures.ENDER_BEAST_REGION.piece, compoundNBT);
//    }
//
//     @Override
//    protected void addAdditionalSaveData(ServerLevel serverWorld, CompoundTag tagCompound) {
//        super.addAdditionalSaveData(serverWorld, tagCompound);
//    }
//
//    @Override
//    public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
////        if (!this.isInsideBounds(world, boundingBox, 0)) return false;
////
////        SimplexNoiseMapper noise0 = new SimplexNoiseMapper();
////        SimplexNoiseMapper noise1 = new SimplexNoiseMapper();
////        SimplexNoiseMapper noise2 = new SimplexNoiseMapper();
////        SimplexNoiseMapper noise3 = new SimplexNoiseMapper();
////        SimplexNoiseMapper noise4 = new SimplexNoiseMapper();
////
////        double baseYaw = 45;
////        double yaw;
////        double basePitch = 145;
////        double pitch;
////
////        pos = new BlockPos(pos.getX(), chunkGen.getHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG), pos.getZ());
////
////        for (int i = 0; i < 100; i++) {
////            yaw = baseYaw + ((noise4.noise(0.5F, 0.7F * i)) * 10) + ((noise4.noise(0.5F, 0.3F * i)) * 30) + ((0.5 + noise0.noise(0.5F, 0.013F * i)) * 70) + ((0.5 + noise1.noise(0.5F, 0.03F * i)) * 60);
////            pitch = Mth.wrapDegrees(basePitch + ((noise2.noise(6.5F, 0.6F * i) - 0.5F) * 20) + ((noise2.noise(4.5F, 0.2F * i) - 0.5F) * 40) + ((noise2.noise(0.5F, 0.02F * i) - 0.5F) * 55) + ((noise3.noise(0.5F, 0.1F * i) - 0.5F) * 39));
////
////            Vec3 rotVector = MathUtil.fromPitchYaw((float)pitch, (float)yaw);
////
////            pos = pos.add(0.5 - rotVector.x, rotVector.y, 0.5 - rotVector.z);
////
////            float radius = (6.4F + (float)noise2.noise(6.5F, 0.3F * i) * 0.3F) * (0.3F + Easing.EASE_IN_OUT_SINE.progression((float) (100 - i) / 100));
////            float noiseSize = 4.6F;
////            int fR = Mth.floor(radius + noiseSize) + 1;
////            for (int x = -fR; x < radius + noiseSize; x++) {
////                for (int y = -fR; y < radius + noiseSize; y++) {
////                    for (int z = -fR; z < radius + noiseSize; z++) {
////                        double dist = MathUtil.getDistance(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + rotVector.x + x, pos.getY() + rotVector.y + y, pos.getZ() + rotVector.z + z);
////                        float yawToIter = MathUtil.getTargetYaw(pos.getX(), pos.getZ(), pos.getX() + rotVector.x + x, pos.getZ() + rotVector.z + z);
////                        float radYaw = (float)Math.toRadians(yawToIter) * 1;
////                        if (dist < radius + (noiseSize * noise0.noise(20 + pos.getY() * 0.05, MathUtil.sin(radYaw) * 0.64, MathUtil.cos(radYaw) * 0.64))) {
////                            PieceUtil.setBlock(world, pos.add(x, y, z), Blocks.AIR.getDefaultState(), 1);
////                        }
////                    }
////                }
////            }
////
////            //world.setBlockState(, 0);
////        }
//
//        return true;
//    }

}


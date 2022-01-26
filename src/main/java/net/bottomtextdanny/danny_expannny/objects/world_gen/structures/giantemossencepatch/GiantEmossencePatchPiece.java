package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.giantemossencepatch;

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


public class GiantEmossencePatchPiece extends ScatteredFeaturePiece {
	protected GiantEmossencePatchPiece(StructurePieceType p_163188_, int p_163189_, int p_163190_, int p_163191_, int p_163192_, int p_163193_, int p_163194_, Direction p_163195_) {
		super(p_163188_, p_163189_, p_163190_, p_163191_, p_163192_, p_163193_, p_163194_, p_163195_);
	}
	
	@Override
	public void postProcess(WorldGenLevel p_192637_, StructureFeatureManager p_192638_, ChunkGenerator p_192639_, Random p_192640_, BoundingBox p_192641_, ChunkPos p_192642_, BlockPos p_192643_) {
	
	}

//    public GiantEmossencePatchPiece(Random random, int x, int z) {
//        super(DannyStructures.GIANT_EMOSSENCE_PATCH.piece, x, 64, z, 1, 8, 1, Direction.NORTH);
//    }
//
//    public GiantEmossencePatchPiece(ServerLevel templateManager, CompoundTag compoundNBT) {
//        super(DannyStructures.GIANT_EMOSSENCE_PATCH.piece, compoundNBT);
//    }
//
//     @Override
//    protected void addAdditionalSaveData(ServerLevel serverWorld, CompoundTag tagCompound) {
//        super.addAdditionalSaveData(serverWorld, tagCompound);
//    }
//
//    @Override
//    public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
////        long start = System.currentTimeMillis();
////        SplittableRandom localRandom = DEUtil.SS_RANDOM.split();
////        if (!this.isInsideBounds(world, boundingBox, 0)) return false;
////        if (pos.getY() < 5) {
////
////            return false;
////        }
////
////
////        pos = new BlockPos(pos.getX(), chunkGen.getHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG) - 1, pos.getZ());
////        SimplexNoiseMapper noise = new SimplexNoiseMapper(localRandom);
////        float radius = 24.4F;
////        float noiseRange = 8.5F;
////        List<BlockPos> avPositions = new ArrayList<>();
////        float noiseRadius = radius + noiseRange;
////
////        for (int x = -Mth.ceil(noiseRadius); x <= noiseRadius; x++) {
////            for (int z = -Mth.ceil(noiseRadius); z <= noiseRadius; z++) {
////                BlockPos iterationPos = pos.add(x, 0, z);
////
////                iterationPos = PieceUtil.vBlocksNoCollision(world, iterationPos, 9);
////
////                if (iterationPos != null) {
////                    if (world.getBlockState(iterationPos).getBlock() == Blocks.END_STONE && !PieceUtil.fullCollision(world, iterationPos.up())) {
////                        double dist = MathUtil.getHorizontalDistance(pos.getX(), pos.getZ(), iterationPos.getX(), iterationPos.getZ());
////                        float yawToIter = MathUtil.getTargetYaw(pos.getX(), pos.getZ(), pos.getX() + x, pos.getZ() + z);
////                        float radianYaw = (float)Math.toRadians(yawToIter);
////
////                        if (dist < radius + (noiseRange * ((noise.noise(67.5, MathUtil.sin(radianYaw) * 0.8, MathUtil.cos(radianYaw) * 0.8))))) {
////                            world.setBlockState(iterationPos, DannyBlocks.EMOSSENCE.getDefaultState(), 2);
////                            avPositions.add(iterationPos);
////                        }
////                    }
////                }
////            }
////        }
////
////        final double giantShroomFactor = localRandom.nextDouble();
////        int gS = 1;
////        if (giantShroomFactor < 0.3) {
////            gS = 2;
////        } else if (giantShroomFactor < 0.9) {
////            gS = 3;
////        } else {
////            gS = 4;
////        }
////
////        PieceUtil.decorateBPs(new GiantFoamshroomDeco(world, rand), PieceUtil.randomPickedBlocksDistantType1(localRandom, avPositions, gS, 12, 4));
////
////        PieceUtil.decorateBPs(new SmallFoamshroomDeco(world, localRandom), PieceUtil.randomPickedBlocks(localRandom, avPositions, 6));
////
////        PieceUtil.decorateBPs(new EmossenceGrassPatchDeco(world, localRandom), PieceUtil.randomPickedBlocks(localRandom, avPositions, avPositions.size() / 64));
////
////
////        ClientInstance.chatMsg(System.currentTimeMillis() - start);
//        return true;
//    }
}

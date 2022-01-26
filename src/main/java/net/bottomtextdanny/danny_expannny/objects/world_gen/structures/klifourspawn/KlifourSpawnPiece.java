package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.klifourspawn;

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

public class KlifourSpawnPiece extends ScatteredFeaturePiece {
	protected KlifourSpawnPiece(StructurePieceType p_163188_, int p_163189_, int p_163190_, int p_163191_, int p_163192_, int p_163193_, int p_163194_, Direction p_163195_) {
		super(p_163188_, p_163189_, p_163190_, p_163191_, p_163192_, p_163193_, p_163194_, p_163195_);
	}
	
	@Override
	public void postProcess(WorldGenLevel p_192637_, StructureFeatureManager p_192638_, ChunkGenerator p_192639_, Random p_192640_, BoundingBox p_192641_, ChunkPos p_192642_, BlockPos p_192643_) {
	
	}

//    public KlifourSpawnPiece(Random random, int x, int z) {
//        super(DannyStructures.KLIFOUR_CLUSTER.piece, x, 64, z, 7, 7, 9, Direction.NORTH);
//    }
//
//    public KlifourSpawnPiece(ServerLevel templateManager, CompoundTag compoundNBT) {
//        super(DannyStructures.KLIFOUR_CLUSTER.piece, compoundNBT);
//    }
//
//     @Override
//    protected void addAdditionalSaveData(ServerLevel serverWorld, CompoundTag tagCompound) {
//        super.addAdditionalSaveData(serverWorld, tagCompound);
//    }
//
//    @Override
//    public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
//        if (!this.updateAverageGroundHeight(world, boundingBox, 0)) {
//            return false;
//
//
//        } else {
//            pos = new BlockPos(pos.getX(), chunkGen.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE, world) + Mth.nextInt(rand, 0, 5), pos.getZ());
//
//
//            PieceUtil.PositionStack posStack = new PieceUtil.PositionStack(pos, -5, -1, -5, 5, 1, 5);
//
//            Set<Pair<BlockPos, Director>> randomizedPosSet = new HashSet<>();
//            List<Pair<BlockPos, Director>> availablePositions = new ArrayList<>();
//            BlockPos finalPos = pos;
//            posStack.positions.forEach(bp -> {
//                if (bp != null) {
//                    if (world.getBlockState(bp).getFluidState().isEmpty() && PieceUtil.noCollision(world, bp)) {
//                        Director director = new Director();
//
//                        for (Direction dir : Direction.values()) {
//                            if (world.getBlockState(bp.relative(dir)).getCollisionShape(world, bp.relative(dir)).equals(Shapes.block())) {
//                                director.setDirection(dir, true);
//                            }
//                        }
//
//                        if (director.getFirst() != null) {
//                            availablePositions.add(new Pair<BlockPos, Director>(bp, director));
//                        }
//
//                    }
//                }
//
//
//            });
//
//            int random = Mth.nextInt(rand, 4, 7);
//
//            if (availablePositions.size() > 1) {
//                for (int i = 0; i < random; i++) {
//
//                    randomizedPosSet.add(availablePositions.get(rand.nextInt(availablePositions.size() - 1)));
//                }
//            }
//
//
//            randomizedPosSet.forEach(pair -> {
//                KlifourEntity klifour = new KlifourEntity(DannyEntities.KLIFOUR, world.getLevel());
//                Direction attachedDir = DEUtil.getRandomObject(pair.getValue().getDirections());
//                world.setBlock(pair.getKey(), Blocks.AIR.defaultBlockState(), 0);
//                klifour.absMoveTo(pair.getKey().getX(), pair.getKey().getY(), pair.getKey().getZ(), 0, 0);
//                klifour.setPersistenceRequired();
//                klifour.setAttachingDirection(attachedDir);
//                world.setBlock(pair.getKey().relative(attachedDir), DannyBlocks.PLANT_MATTER.defaultBlockState(), 0);
//                world.addFreshEntityWithPassengers(klifour);
//            });
//            return true;
//        }
//
//    }
}

package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.cavetreasure;

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

public class CaveTreasurePiece extends ScatteredFeaturePiece {
	protected CaveTreasurePiece(StructurePieceType p_163188_, int p_163189_, int p_163190_, int p_163191_, int p_163192_, int p_163193_, int p_163194_, Direction p_163195_) {
		super(p_163188_, p_163189_, p_163190_, p_163191_, p_163192_, p_163193_, p_163194_, p_163195_);
	}
	
	@Override
	public void postProcess(WorldGenLevel p_192637_, StructureFeatureManager p_192638_, ChunkGenerator p_192639_, Random p_192640_, BoundingBox p_192641_, ChunkPos p_192642_, BlockPos p_192643_) {
	
	}

//    public CaveTreasurePiece(int x, int z) {
//        super(DannyStructures.CAVE_TREASURE.piece, x, 64, z, 7, 7, 9, Direction.NORTH);
//    }
//
//    public CaveTreasurePiece(ServerLevel templateManager, CompoundTag compoundNBT) {
//        super(DannyStructures.CAVE_TREASURE.piece, compoundNBT);
//    }
//
//     @Override
//    protected void addAdditionalSaveData(ServerLevel serverWorld, CompoundTag tagCompound) {
//        super.addAdditionalSaveData(serverWorld, tagCompound);
//    }
//
//    @Override
//    public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
//        if (!this.updateAverageGroundHeight(world, boundingBox, 0)) return false;
//
//
//        pos = new BlockPos(pos.getX(), 80, pos.getZ());
//
//        Direction direction = Direction.from2DDataValue(rand.nextInt(4));
//        PieceUtil.CubeRoom room;
//
//        if (direction.getAxis() == Direction.Axis.X) {
//
//            room = new PieceUtil.CubeRoom(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + Mth.nextInt(rand, 10, 13), pos.getY() + 3, pos.getZ()  + Mth.nextInt(rand, 5, 7));
//        } else {
//
//            room = new PieceUtil.CubeRoom(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + Mth.nextInt(rand, 5, 7), pos.getY() + 3, pos.getZ() + Mth.nextInt(rand, 10, 13));
//        }
//
//        PieceUtil.fillRegion(world, room.innerCube.expand(direction.getAxis() == Direction.Axis.Z ? 1 : 0, 1, direction.getAxis() == Direction.Axis.X ? 1 : 0, 0), Blocks.AIR.defaultBlockState());
//        PieceUtil.fillRegion(world, room.floor, Blocks.OAK_PLANKS.defaultBlockState());
//        PieceUtil.fillRegion(world, room.ceiling.expand(2, 0, 2), Blocks.OAK_PLANKS.defaultBlockState());
//
//        PieceUtil.RoomWall[] axisWalls;
//        PieceUtil.RoomWall[] opAxisWalls;
//
//        if (direction.getAxis() == Direction.Axis.X) {
//            axisWalls = room.getXWalls();
//            opAxisWalls = room.getZWalls();
//        } else {
//            axisWalls = room.getZWalls();
//            opAxisWalls = room.getXWalls();
//        }
//
//        axisWalls[0].positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        axisWalls[1].positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        opAxisWalls[0].move(opAxisWalls[0].getOuterDirection().getNormal()).positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        opAxisWalls[1].move(opAxisWalls[1].getOuterDirection().getNormal()).positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        room.ceiling.positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        room.floor.move(0, -1, 0).positions.forEach(bp -> {
//            if (bp != null && !world.getBlockState(bp).getFluidState().isEmpty())
//                world.setBlock(bp, Blocks.STONE.defaultBlockState(), 0);
//        });
//
//        List<BlockPos> randomFloorBlocks0 = PieceUtil.randomPickedBlocks(rand, room.floor.positions, 5);
//        List<BlockPos> randomFloorBlocks1 = PieceUtil.randomPickedBlocks(rand, room.floor.positions, 7);
//
//        PieceUtil.decorateBPs(new PushBlockVerticallyDecorator(world, rand, Direction.DOWN, Blocks.OAK_PLANKS), randomFloorBlocks0);
//        PieceUtil.decorateBPs(new ATSlabStripesDecorator(world, rand, Blocks.OAK_SLAB.defaultBlockState(), Blocks.OAK_PLANKS), randomFloorBlocks1);
//
//        int pillarSeparationCounter = 0;
//
//        for (int i = 0; i < room.getDimensionFromAxis(direction.getAxis()); i++) {
//            if (pillarSeparationCounter >= 2) {
//                List<BlockPos> pillarPosPair = room.getWallPairPos(direction.getAxis(), i);
//
//                PieceUtil.decorateBPA(new CTPillarsDecorator(world, rand, direction.getClockWise().getAxis(), 3, room.getDimensionFromAxis(direction.getClockWise().getAxis())), pillarPosPair.get(0), pillarPosPair.get(1));
//                pillarSeparationCounter = 0;
//            } else pillarSeparationCounter++;
//        }
//
//        return true;
//    }

}


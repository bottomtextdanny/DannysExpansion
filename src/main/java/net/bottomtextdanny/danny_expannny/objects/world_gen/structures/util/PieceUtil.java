package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.*;

public class PieceUtil {

    public static boolean isSpace(LevelAccessor world, BlockPos pos) {
        return !world.getBlockState(pos).getFluidState().isEmpty() || world.getBlockState(pos).isAir();
    }

    public static boolean isAir(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).isAir();
    }

    public static boolean fullCollision(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getCollisionShape(world, pos) == Shapes.block();
    }

    public static boolean noCollision(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getCollisionShape(world, pos) == Shapes.empty();
    }

    public static boolean water(LevelAccessor world, BlockPos pos) {

        return world.isWaterAt(pos);
    }

    public static void setBlockOnSpace(WorldGenLevel world, BlockPos pos, BlockState newState) {
        if (noCollision(world, pos)){
            world.setBlock(pos, newState, 0);
        }
    }

    public static boolean setBlock(WorldGenLevel world, BlockPos pos, BlockState newState, int flag) {
        if (world.hasChunkAt(pos)) {
            world.setBlock(pos, newState, flag);

            return true;
        }
        return false;
    }

    public static void setWLBlock(WorldGenLevel world, BlockPos pos, BlockState newState) {

        if (!world.getBlockState(pos).getFluidState().isEmpty()) {
            world.setBlock(pos, newState.setValue(BlockStateProperties.WATERLOGGED, true), 0);
        } else if (world.getBlockState(pos).isAir()){
            world.setBlock(pos, newState, 0);
        }
    }

    public static void setWLBlockSolid(WorldGenLevel world, BlockPos pos, BlockState newState) {

        if (!world.getBlockState(pos).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.UP)).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.DOWN)).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.NORTH)).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.SOUTH)).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.EAST)).getFluidState().isEmpty() ||
                !world.getBlockState(pos.relative(Direction.WEST)).getFluidState().isEmpty())  {

            setBlock(world, pos, newState.setValue(BlockStateProperties.WATERLOGGED, true), 0);
        } else {
            world.setBlock(pos, newState, 0);
        }
    }

    public static void setAirOrWater(WorldGenLevel world, BlockPos pos) {

        if (!world.getBlockState(pos).getFluidState().isEmpty()) {
            world.setBlock(pos, Blocks.WATER.defaultBlockState(), 0);
        } else if (world.getBlockState(pos).isAir()){
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
    }

    public static PositionStack fillRegion(WorldGenLevel world, BlockPos pos, int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, BlockState newState) {


        BlockPos blockPos = pos.offset(xStart, yStart, zStart);
        for (int i = 0; i <= xEnd - xStart; i++) {
            for (int j = 0; j <= yEnd - yStart; j++) {
                for (int k = 0; k <= zEnd - zStart; k++) {
                    world.setBlock(blockPos.offset(i, j, k), newState, 0);

                }
            }
        }

        return new PositionStack(pos.getX() + xStart, pos.getY() + yStart, pos.getZ() + zStart, pos.getX() + xEnd, pos.getY() + yEnd, pos.getZ() + zEnd);
    }

    public static void fillRegion(WorldGenLevel world, PositionStack posStack, BlockState newState) {
        posStack.positions.forEach(bp -> {
            if (bp != null) {
                world.setBlock(bp, newState, 0);
            }
        });
    }

    public static void placeFencesInFrameAT(WorldGenLevel world, FlatFrame flatFrame, BlockState fence, BlockState corner) {

        for (int i = 0; i < flatFrame.blocks.getKey().length; i++) {
            BlockPos iterationPos = flatFrame.blocks.getKey()[i];
            Direction iterationDirection = flatFrame.blocks.getValue()[i];

            if (iterationPos != null && iterationDirection != null) {

                if (world.getBlockState(iterationPos).getBlock() != Blocks.WATER || !world.getBlockState(iterationPos).canOcclude()) {


                    placeFence(world, fence, iterationPos);
                }
            }
        }

        for (int i = 0; i < 4; i++) {

            world.setBlock(flatFrame.corners.getKey()[i], corner, 0);
        }
    }

    public static void placeFence(WorldGenLevel world, BlockState fence, BlockPos pos) {
        setWLBlock(world, pos, fence);

        for (Direction direction : Direction.values()){
            if (direction.get2DDataValue() >= 0) {
                BlockPos offPos = pos.relative(direction);
                BlockState offState = world.getBlockState(offPos);
                if (world.getBlockState(pos).getBlock() instanceof FenceBlock && ((FenceBlock)fence.getBlock()).connectsTo(offState, fence.isFaceSturdy(world, offPos, direction), direction)) {
                    world.setBlock(pos, world.getBlockState(pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(direction, PipeBlock.NORTH), true), 0);

                    if (offState.getBlock() instanceof FenceBlock) {
                        world.setBlock(offPos, world.getBlockState(offPos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(direction.getOpposite(), PipeBlock.NORTH), true), 0);
                    }
                }
            }
        }
    }

    public static void placeUpdateFence(WorldGenLevel world, BlockPos pos, BlockState fence) {

        world.setBlock(pos, fence, 0);

        for (Direction direction : Direction.values()){
            if (direction.get2DDataValue() >= 0) {
                BlockPos offPos = pos.relative(direction);
                BlockState offState = world.getBlockState(offPos);
                if (fence.canOcclude()) {

                    if (offState.getBlock() instanceof FenceBlock) {
                        world.setBlock(offPos, world.getBlockState(offPos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(direction.getOpposite(), PipeBlock.NORTH), true), 0);
                    }
                }
            }
        }
    }

    public static void delete1AxisFenceAT(WorldGenLevel world, BlockPos pos, Direction middleDirection, Block fence) {
        Direction dirY = middleDirection.getClockWise();
        Direction dirYCCW = middleDirection.getCounterClockWise();
        BlockState stateYOff = world.getBlockState(pos.relative(dirY));
        BlockState stateYCCWOff = world.getBlockState(pos.relative(dirYCCW));

        setAirOrWater(world, pos);

        if (stateYOff.getBlock() == fence) {
            world.setBlock(pos.relative(dirY), stateYOff.setValue(dirToProp(dirYCCW), false), 0);
        }

        if (stateYCCWOff.getBlock() == fence) {
            world.setBlock(pos.relative(dirYCCW), stateYCCWOff.setValue(dirToProp(dirY), false), 0);
        }
    }

    public static void placeFencesInFrameDesintegratedAT(WorldGenLevel world, Random random, FlatFrame flatFrame, BlockState fence, BlockState corner, float integration) {

        for (int i = 0; i < flatFrame.blocks.getKey().length; i++) {
            BlockPos iterationPos = flatFrame.blocks.getKey()[i];
            Direction iterationDirection = flatFrame.blocks.getValue()[i];

            if (iterationPos != null && iterationDirection != null) {


                placeFence(world, fence, iterationPos);
            }
        }

        for (int i = 0; i < flatFrame.blocks.getKey().length; i++) {
            BlockPos bp = flatFrame.blocks.getKey()[i];
            Direction dir0 = flatFrame.blocks.getValue()[i];

            if (bp != null && dir0 != null) {
                Direction dirY = dir0.getClockWise();
                Direction dirYCCW = dir0.getCounterClockWise();

                if (random.nextFloat() > integration) {
                    setAirOrWater(world, bp);

                    BlockState stateYOff = world.getBlockState(bp.relative(dirY));
                    BlockState stateYCCWOff = world.getBlockState(bp.relative(dirYCCW));

                    if (stateYOff.getBlock() == fence.getBlock()) {
                        world.setBlock(bp.relative(dirY), stateYOff.setValue(dirToProp(dirYCCW), false), 0);
                    }

                    if (stateYCCWOff.getBlock() == fence.getBlock()) {
                        world.setBlock(bp.relative(dirYCCW), stateYCCWOff.setValue(dirToProp(dirY), false), 0);
                    }

                }
            }
        }

        for (int i = 0; i < 4; i++) {
            world.setBlock(flatFrame.corners.getKey()[i], corner, 0);
        }
    }

    public static BlockPos randomlyPlaceBlockAT(WorldGenLevel world, Random random, BlockPos pos, int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, BlockState newState) {
        xEnd++;
        yEnd++;
        zEnd++;

        BlockPos blockPos = new BlockPos(
                pos.getX() + xStart + random.nextInt(xEnd - xStart),
                pos.getY() + yStart + random.nextInt(yEnd - yStart),
                pos.getZ() + zStart + random.nextInt(zEnd - zStart));
        setWLBlock(world, blockPos, newState);

        return blockPos;
    }

    public static void cornerFencesAT(WorldGenLevel world, Random random, FlatFrame frame, int corner, int range) {

        if (frame.corners.getKey()[corner] != null)  {

        frame.corners.getKey()[corner] = frame.corners.getKey()[corner].above(2);

            makePillarUpAT(world, frame.corners.getKey()[corner], Blocks.SPRUCE_FENCE.defaultBlockState(), range);

            frame.corners.getValue()[corner].getDirections().forEach(cornerDirection -> {

                for (int i = 1; i < 3; i++) {
                    makePillarUpUpdateNeigbourAT(world, frame.corners.getKey()[corner].relative(cornerDirection, -i), cornerDirection, Blocks.SPRUCE_FENCE.defaultBlockState(), Mth.nextInt(random, range / (i + 1), range / i));
                }
            });
        }
    }

    public static BooleanProperty dirToProp(Direction dir) {
        return PipeBlock.PROPERTY_BY_DIRECTION.get(dir);
    }

    public static void makePillarUpAT(WorldGenLevel world, BlockPos pos, BlockState newState, int height) {

        for (int i = 0; i <= height; i++) {
            placeFence(world, newState, pos.above(i));
        }
    }

    public static void makePillarUpUpdateNeigbourAT(WorldGenLevel world, BlockPos pos, Direction nbDir, BlockState newState, int height) {

        for (int i = 0; i <= height; i++) {
            placeFence(world, newState, pos.above(i));
        }
    }

    public static void decorateBPs(PosDecorator decorator, Collection<BlockPos> positions) {
        positions.forEach(bp -> {

            if (bp != null) {

                decorator.generate(bp);
            }

        });
    }



    public static void decorateBPA(PosArrayDecorator decorator, BlockPos... positions) {
        decorator.generate(positions);
    }

    public static void decorateStackRandomlyBPs(PosDecorator decorator, Random random, int tries, PositionStack positions) {
        positions.randomPickedBlocks(random, tries).forEach(bp -> {
            if (bp != null) {
                decorator.generate(bp);
            }

        });
    }

    public static List<BlockPos> randomPickedBlocks(Random random, List<BlockPos> positions, int blockAmount) {
        List<BlockPos> randomPositions = Arrays.asList(new BlockPos[blockAmount]);

        if (positions.size() > 0) {
            for (int i = 0; i < blockAmount; i++) {
                randomPositions.set(i, positions.get(random.nextInt(positions.size())));
            }
        }

        return randomPositions;
    }

    public static List<BlockPos> randomPickedBlocks(SplittableRandom random, List<BlockPos> positions, int blockAmount) {
        List<BlockPos> randomPositions = Arrays.asList(new BlockPos[blockAmount]);

        if (positions.size() > 0) {
            for (int i = 0; i < blockAmount; i++) {
                randomPositions.set(i, positions.get(random.nextInt(positions.size())));
            }
        }

        return randomPositions;
    }

    public static List<BlockPos> randomPickedBlocksDistantType1(SplittableRandom random, List<BlockPos> positions, int blockAmount, double distance, int tries) {
        List<BlockPos> randomPositions = Arrays.asList(new BlockPos[blockAmount]);

        if (positions.size() > 0) {
            for (int i = 0; i < blockAmount; i++) {
                if (i > 0) {
                    gen:{
                    for (int j = 0; j < tries; j++) {
                        int randomPos = Mth.clamp((int) (random.nextDouble() * positions.size()), 0, positions.size() - 1);

                        for (int k = 0; k < i - 1; k++) {
                            BlockPos prevRandom = randomPositions.get(k);
                            BlockPos newRandom = positions.get(randomPos);

                            if (prevRandom != null && DEMath.getHorizontalDistance(prevRandom.getX(), prevRandom.getZ(), newRandom.getX(), newRandom.getZ()) > distance) {
                                randomPositions.set(i, newRandom);
                                break gen;
                            }
                        }
                    }}
                } else {
                    randomPositions.set(i, positions.get(random.nextInt(positions.size())));
                }
            }
        }

        return randomPositions;
    }

    @Nullable
    public static BlockPos vBlocksNoCollision(LevelAccessor world, BlockPos pos, int max) {
        int j = 0;
        int k = 0;
        if (!PieceUtil.fullCollision(world, pos)) {
            for(int i = 0; !PieceUtil.fullCollision(world, pos.offset(0F, k, 0F)); ++i) {
                k--;
                if (i < max) {
                    j++;
                } else {
                    return null;
                }
            }
        }

        if (PieceUtil.fullCollision(world, pos)) {
            k--;
            /*&& PieceUtil.isSpace(world, pos.add(0F, k += 1 + 1, 0F))*/
            for(int i = 0; PieceUtil.fullCollision(world, pos.offset(0F, k + 1, 0F)); ++i) {
                k++;
                if (i < max) {
                    j++;
                } else {
                    return null;
                }
            }
        }

        return pos.offset(0, k, 0);
    }

    public static class FlatFrame {
        public int height;
        public Vec3i dimensions;
        public BlockPos startPos;
        public Pair<BlockPos[], Director[]> corners = new Pair<>(new BlockPos[4], new Director[4]);
        public Pair<BlockPos[], Direction[]> blocks = new Pair<>(new BlockPos[100], new Direction[100]);

        public FlatFrame(int xStart, int zStart, int xEnd, int zEnd, int yPos) {
            this.height = yPos;
            this.dimensions = new Vec3i(xEnd - xStart, 0, zEnd - zStart);
            this.startPos = new BlockPos(xStart, this.height, zStart);


            int blocksIndex = 0;

            for (int i = 0; i <= xEnd - xStart; i++) {
                int xPos = xStart + i;

                if (i == 0) {
                    this.corners.getKey()[0] = new BlockPos(xPos, this.height, zStart);
                    this.corners.getValue()[0] = new Director(Direction.NORTH, Direction.WEST);
                }
                else if (i == xEnd - xStart) {
                    this.corners.getKey()[1] = new BlockPos(xPos, this.height, zStart);
                    this.corners.getValue()[1] = new Director(Direction.NORTH, Direction.EAST);
                } else {
                    this.blocks.getValue()[blocksIndex] = Direction.NORTH;
                }


                this.blocks.getKey()[blocksIndex] = new BlockPos(xPos, this.height, zStart);
                blocksIndex++;
            }

            for (int i = 0; i <= zEnd - zStart; i++) {
                int zPos = zStart + i;

                this.blocks.getKey()[blocksIndex] = new BlockPos(xStart, this.height, zPos);
                this.blocks.getValue()[blocksIndex] = Direction.WEST;
                blocksIndex++;

                this.blocks.getKey()[blocksIndex] = new BlockPos(xEnd, this.height, zPos);
                this.blocks.getValue()[blocksIndex] = Direction.EAST;
                blocksIndex++;
            }

            for (int i = 0; i <= xEnd - xStart; i++) {
                int xPos = xStart + i;

                if (i == 0) {
                    this.corners.getKey()[2] = new BlockPos(xPos, this.height, zEnd);
                    this.corners.getValue()[2] = new Director(Direction.SOUTH, Direction.WEST);
                } else if (i == xEnd - xStart) {
                    this.corners.getKey()[3] = new BlockPos(xPos, this.height, zEnd);
                    this.corners.getValue()[3] = new Director(Direction.SOUTH, Direction.EAST);
                } else {
                    this.blocks.getValue()[blocksIndex] = Direction.SOUTH;
                }

                this.blocks.getKey()[blocksIndex] = new BlockPos(xPos, this.height, zEnd);
                blocksIndex++;
            }
        }
    }

    public static class CubeRoom {
        public Vec3i dimansions;
        public PositionStack innerCube;
        public PositionStack floor;
        public PositionStack ceiling;
        public RoomWall northWall;
        public RoomWall southWall;
        public RoomWall westWall;
        public RoomWall eastWall;
        public BlockPos startPos;
        @Nullable
        public RoomWall[] walls, xAxisWalls, zAxisWalls;

        public CubeRoom(int xStart, int yBottom, int zStart, int xEnd, int yTop, int zEnd) {
            this.dimansions = new Vec3i(xEnd - xStart, yTop - yBottom, zEnd - zStart);
            this.innerCube = new PositionStack(xStart, yBottom, zStart, xEnd, yTop, zEnd);
            this.floor = new PositionStack(xStart, yBottom - 1, zStart, xEnd, yBottom - 1, zEnd);
            this.ceiling = new PositionStack(xStart, yTop + 1, zStart, xEnd, yTop + 1, zEnd);
            this.startPos = new BlockPos(xStart, yBottom, zStart);

            this.northWall = new RoomWall(this.startPos.north(), Direction.NORTH, 0, xEnd - xStart, yTop - yBottom);
            this.southWall = new RoomWall(this.startPos.south(zEnd - zStart + 1), Direction.SOUTH, 0, xEnd - xStart, yTop - yBottom);
            this.westWall = new RoomWall(this.startPos.west(), Direction.WEST, 0, zEnd - zStart, yTop - yBottom);
            this.eastWall = new RoomWall(this.startPos.east(xEnd - xStart + 1), Direction.EAST, 0, zEnd - zStart, yTop - yBottom);

        }

        public RoomWall[] getWalls() {
            if (this.walls == null) {
                this.walls = new RoomWall[] {this.northWall, this.southWall, this.westWall, this.eastWall};
            }

            return this.walls;
        }

        public RoomWall[] getXWalls() {
            if (this.xAxisWalls == null) {
                this.xAxisWalls = new RoomWall[] {this.westWall, this.eastWall};
            }

            return this.xAxisWalls;
        }

        public RoomWall[] getZWalls() {
            if (this.zAxisWalls == null) {
                this.zAxisWalls = new RoomWall[] {this.northWall, this.southWall};
            }

            return this.zAxisWalls;
        }

        public List<BlockPos> getWallPairPos(Direction.Axis axis, int index) {
            List<BlockPos> positions = Arrays.asList(new BlockPos[2]);
            if (axis == Direction.Axis.X) {

                if (this.northWall.horizontalIndex.size() < index) throw new Error("Danny's Expansion: h index is bigger than wall's size");

                positions.set(0, this.northWall.horizontalIndex.get(index));
                positions.set(1, this.southWall.horizontalIndex.get(index));
            } else if (axis == Direction.Axis.Z) {

                if (this.westWall.horizontalIndex.size() < index) throw new Error("Danny's Expansion: h index is bigger than wall's size");

                positions.set(0, this.westWall.horizontalIndex.get(index));
                positions.set(1, this.eastWall.horizontalIndex.get(index));
            } else throw new Error("Danny's Exansion: axis cannot be vertical");

            return positions;
        }

        public int getDimensionFromAxis(Direction.Axis axis) {
            if (axis == Direction.Axis.X) {
                return this.dimansions.getX();
            } else if (axis == Direction.Axis.Z) {
                return this.dimansions.getZ();
            } else return this.dimansions.getY();
        }


    }

    public static Direction.Axis getOppositeAxis(Direction.Axis axis) {
        return axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
    }

    public static class RoomWall extends PositionStack {
        protected Direction.Axis axis;
        protected Direction outerDirection;
        public List<BlockPos> horizontalIndex;

        public RoomWall(BlockPos pivot, Direction outerDirection, int startOffset, int endOffset, int height) {
            int xStart;
            int yStart = pivot.getY();
            int zStart;
            int xEnd;
            int yEnd = pivot.getY() + height;
            int zEnd;
            int blockCounter = 0;
            int hiCounter = 0;

            this.axis = outerDirection.getClockWise().getAxis();
            this.outerDirection = outerDirection;


            if (this.axis == Direction.Axis.X) {
                xStart = pivot.getX() + startOffset;
                xEnd = pivot.getX() + endOffset;
                zStart = pivot.getZ();
                zEnd = pivot.getZ();

                this.dimensions = new Vec3i(xEnd - xStart, yEnd - yStart, zEnd - zStart);
                this.startPos = new BlockPos(xStart, yStart, zStart);
                this.endPos = new BlockPos(xEnd, yEnd, zEnd);
                this.pivot = pivot;
                this.horizontalIndex = Arrays.asList(new BlockPos[xEnd - xStart + 1]);
                this.positions = Arrays.asList(new BlockPos[(this.endPos.getX() - this.startPos.getX() + 1) * (this.endPos.getY() - this.startPos.getY() + 1) + 1]);

                for (int i = 0; i <= xEnd - xStart; i++) {

                    for (int j = 0; j <= height; j++) {

                        this.positions.set(blockCounter, this.startPos.offset(i, j, 0));
                        blockCounter++;
                    }

                    this.horizontalIndex.set(hiCounter, this.startPos.offset(i, 0, 0));
                    hiCounter++;
                }

            } else if (this.axis == Direction.Axis.Z) {
                xStart = pivot.getX();
                xEnd = pivot.getX();
                zStart = pivot.getZ() + startOffset;
                zEnd = pivot.getZ() + endOffset;

                this.dimensions = new Vec3i(xEnd - xStart, yEnd - yStart, zEnd - zStart);
                this.startPos = new BlockPos(xStart, yStart, zStart);
                this.endPos = new BlockPos(xEnd, yEnd, zEnd);
                this.pivot = pivot;
                this.horizontalIndex = Arrays.asList(new BlockPos[zEnd - zStart + 1]);
                this.positions = Arrays.asList(new BlockPos[(this.endPos.getY() - this.startPos.getY() + 1) * (this.endPos.getZ() - this.startPos.getZ() + 1) + 1]);

                for (int i = 0; i <= zEnd - zStart; i++) {

                    for (int j = 0; j <= height; j++) {

                        this.positions.set(blockCounter, this.startPos.offset(0, j, i));
                        blockCounter++;
                    }

                    this.horizontalIndex.set(hiCounter, this.startPos.offset(0, 0, i));
                    hiCounter++;
                }
            } else throw new Error("Danny's Expansion: wall axis cannot be vertical");
        }

        public Direction.Axis getAxis() {
            return this.axis;
        }

        public Direction getOuterDirection() {
            return this.outerDirection;
        }

        public Direction getInnerDirection() {
            return this.outerDirection.getOpposite();
        }
    }

    public static class PositionStack {
        public Vec3i dimensions;
        public BlockPos pivot;
        public BlockPos startPos;
        public BlockPos endPos;
        public List<BlockPos> positions;

        public PositionStack() {
        }

        public PositionStack(BlockPos pivot, int xStartOffset, int yStartOffset, int zStartOffset, int xEndOffset, int yEndOffset, int zEndOffset) {
            this.dimensions = new Vec3i(xEndOffset - xStartOffset, yEndOffset - yStartOffset, zEndOffset - zStartOffset);
            this.pivot = pivot;
            this.startPos = pivot.offset(xStartOffset, yStartOffset, zStartOffset);
            this.endPos = pivot.offset(xEndOffset, yEndOffset, zEndOffset);
            this.positions = Arrays.asList(new BlockPos[(this.endPos.getX() - this.startPos.getX() + 1) * (this.endPos.getY() - this.startPos.getY() + 1) * (this.endPos.getZ() - this.startPos.getZ() + 1) + 1]);

            int blockCounter = 0;

            for (int i = 0; i <= xEndOffset - xStartOffset; i++) {
                for (int j = 0; j <= yEndOffset - yStartOffset; j++) {
                    for (int k = 0; k <= zEndOffset - zStartOffset; k++) {
                        this.positions.set(blockCounter, pivot.offset(i + xStartOffset, j + yStartOffset, k + zStartOffset));
                        blockCounter++;
                    }
                }
            }


        }

        public PositionStack(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd) {
            this.dimensions = new Vec3i(xEnd - xStart, yEnd - yStart, zEnd - zStart);
            this.startPos = new BlockPos(xStart, yStart, zStart);
            this.endPos = new BlockPos(xEnd, yEnd, zEnd);
            this.pivot = new BlockPos((xEnd - xStart) / 2 + xStart, (yEnd - yStart) / 2 + yStart, (zEnd - zStart) / 2 + zStart);
            this.positions = Arrays.asList(new BlockPos[(this.endPos.getX() - this.startPos.getX() + 1) * (this.endPos.getY() - this.startPos.getY() + 1) * (this.endPos.getZ() - this.startPos.getZ() + 1) + 1]);

            int blockCounter = 0;

            for (int i = 0; i <= xEnd - xStart; i++) {
                for (int j = 0; j <= yEnd - yStart; j++) {
                    for (int k = 0; k <= zEnd - zStart; k++) {
                        this.positions.set(blockCounter, this.startPos.offset(i, j, k));
                        blockCounter++;
                    }
                }
            }
        }

        public PositionStack expand(int x, int y, int z) {
            return new PositionStack(this.startPos.getX() - x, this.startPos.getY() - y, this.startPos.getZ() - z, this.endPos.getX() + x, this.endPos.getY() + y, this.endPos.getZ() + z);
        }

        public PositionStack expand(int x, int yBottom, int z, int yTop) {
            return new PositionStack(this.startPos.getX() - x, this.startPos.getY() - yBottom, this.startPos.getZ() - z, this.endPos.getX() + x, this.endPos.getY() + yTop, this.endPos.getZ() + z);
        }

        public PositionStack move(int x, int y, int z) {
            return new PositionStack(this.startPos.getX() + x, this.startPos.getY() + y, this.startPos.getZ() + z, this.endPos.getX() + x, this.endPos.getY() + y, this.endPos.getZ() + z);
        }

        public PositionStack move(Vec3i vector) {
            return new PositionStack(this.startPos.getX() + vector.getX(), this.startPos.getY() + vector.getY(), this.startPos.getZ() + vector.getZ(), this.endPos.getX() + vector.getX(), this.endPos.getY() + vector.getY(), this.endPos.getZ() + vector.getZ());
        }

        public static PositionStack frameToStack(FlatFrame frame) {
            BlockPos[] bps = new BlockPos[500];

            for (int i = 0; i < frame.blocks.getKey().length; i++) {

                if (frame.blocks.getKey()[i] != null) bps[i] = frame.blocks.getKey()[i];
            }

            PositionStack posStack = new PositionStack(frame.startPos.getX(), frame.startPos.getY(), frame.startPos.getZ(), frame.startPos.getX() + frame.dimensions.getX(), frame.startPos.getY() + frame.dimensions.getY(), frame.startPos.getZ() + frame.dimensions.getZ());

            posStack.positions = Arrays.asList(bps);

            return posStack;
        }



        public List<BlockPos> randomPickedBlocks(Random random, int blockAmount) {
            List<BlockPos> randomPositions = Arrays.asList(new BlockPos[blockAmount]);

            for (int i = 0; i < blockAmount; i++) {
                randomPositions.set(i, this.positions.get(random.nextInt(this.positions.size())));
            }

            return randomPositions;
        }
    }
}

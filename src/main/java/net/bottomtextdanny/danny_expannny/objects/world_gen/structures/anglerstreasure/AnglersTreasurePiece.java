package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.anglerstreasure;

import com.google.common.collect.Lists;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.object_tables.DEBlocks;
import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.ATGroundCoralDecorator;
import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.ATSlabStripesDecorator;
import net.bottomtextdanny.danny_expannny.objects.world_gen.decorations.ATStackedBlocksDecorator;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.dannys_expansion.core.Registries.DannyStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AnglersTreasurePiece extends ScatteredFeaturePiece {

    public AnglersTreasurePiece(Random random, int x, int z) {
        super(DannyStructures.ANGLERS_TREASURE.getPieceByIndex(0), x, 64, z, 7, 7, 9, Direction.NORTH);
    }

    public AnglersTreasurePiece(CompoundTag p_192664_) {
        super(DannyStructures.ANGLERS_TREASURE.getPieceByIndex(0), p_192664_);
    }



    @Override
    public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        if (!this.updateAverageGroundHeight(world, boundingBox, 0)) {
			return;


        } else {
            pos = new BlockPos(pos.getX(), chunkGen.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.OCEAN_FLOOR, world) + 3 + rand.nextInt(5), pos.getZ());
            if (pos.getY() + 6 > chunkGen.getSeaLevel())  return;
            MainPlatformPiece flatFrame = new MainPlatformPiece(world, pos, Mth.nextInt(rand, 8, 12), Mth.nextInt(rand, 11, 17));

        }

    }

    class MainPlatformPiece {
        public BlockPos platformCenterPos;
        public Leg[] legs = new Leg[4];

        public MainPlatformPiece(WorldGenLevel world, BlockPos pos, int xWidth, int zWidth) {
            PieceUtil.FlatFrame flatFrame0 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2 + 1, pos.getZ() - zWidth / 2 + 1, pos.getX() + xWidth / 2 - 1, pos.getZ() + zWidth / 2 -1, pos.getY() - 1);
            PieceUtil.FlatFrame flatFrame1 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2, pos.getZ() - zWidth / 2, pos.getX() + xWidth / 2, pos.getZ() + zWidth / 2, pos.getY() + 1);
            Random random = new Random();
            int longLegIndex = 0;
            int extraSpaces = 0;


            this.platformCenterPos = pos;
            this.legs[0] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[1] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[2] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[3] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);

            PieceUtil.PositionStack posStack0 = PieceUtil.fillRegion(world, pos, -xWidth / 2, 0, -zWidth / 2, xWidth / 2, 0, zWidth / 2, Blocks.SPRUCE_PLANKS.defaultBlockState());

            List<BlockPos> randomBorderBlocks1 = posStack0.expand(-2, 0, -2).move(0, 1, 0).randomPickedBlocks(random, Mth.nextInt(random, 2, 4));
            List<BlockPos> randomBorderBlocks2 = posStack0.expand(-2, 0, -2).move(0, 1, 0).randomPickedBlocks(random, Mth.nextInt(random, 0, 5));

            Set<BlockPos> platformIterator = new HashSet<BlockPos>(posStack0.positions);

            TreasurePlatform platformPiece1 = new TreasurePlatform(world, random, new BlockPos(
                    pos.getX() - xWidth / 2 + Mth.nextInt(random, 0, xWidth / 2),
                    pos.getY() + Mth.nextInt(random, 0, 5) + 5,
                    pos.getZ() - zWidth / 2 + Mth.nextInt(random, 0, zWidth / 2)),  Mth.nextInt(random, 5, 7), Mth.nextInt(random, 6, 8));

            PieceUtil.decorateBPs(new ATStackedBlocksDecorator(world, pos, random, Blocks.HAY_BLOCK.defaultBlockState(), Blocks.DRIED_KELP_BLOCK.defaultBlockState(), Blocks.CLAY.defaultBlockState()), randomBorderBlocks1);
            PieceUtil.decorateBPs(new ATGroundCoralDecorator(world, random), randomBorderBlocks2);


            PieceUtil.placeFencesInFrameAT(world, flatFrame1, Blocks.OAK_FENCE.defaultBlockState(), Blocks.STRIPPED_OAK_WOOD.defaultBlockState());

            for (int i = 0; i < flatFrame1.blocks.getKey().length; i++) {
                BlockPos bp = flatFrame1.blocks.getKey()[i];
                Direction outDirection = flatFrame1.blocks.getValue()[i];

                if (bp != null && outDirection != null && extraSpaces < 6) {

                    if (random.nextFloat() < 0.05) {
                        int halfXWidth = Mth.nextInt(random, 1, 4);
                        int halfZWidth = Mth.nextInt(random, 1, 4);

                        PieceUtil.PositionStack extraPositions = PieceUtil.fillRegion(world, bp, -halfXWidth, -1, -halfZWidth, halfXWidth, -1, halfZWidth, Blocks.SPRUCE_PLANKS.defaultBlockState());

                        platformIterator.addAll(extraPositions.positions);

                        Leg extraLeg = new Leg(world, bp.relative(outDirection, outDirection.getAxis() == Direction.Axis.X ? halfXWidth - 1 : halfZWidth - 1).below(2), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);

                        extraLeg.getPillarStream().forEach(legPos -> PieceUtil.placeUpdateFence(world, legPos, Blocks.OAK_LOG.defaultBlockState()));

                        if (flatFrame1.blocks.getValue()[i].getAxis() == Direction.Axis.X) {

                            PieceUtil.setAirOrWater(world, bp);
                            for (int j = 0; j < halfZWidth; j++) {
                                PieceUtil.delete1AxisFenceAT(world, bp.relative(Direction.NORTH, j), outDirection, Blocks.OAK_FENCE);
                                PieceUtil.delete1AxisFenceAT(world, bp.relative(Direction.SOUTH, j), outDirection, Blocks.OAK_FENCE);
                            }
                        } else {
                            for (int j = 0; j < halfXWidth; j++) {
                                PieceUtil.delete1AxisFenceAT(world, bp.relative(Direction.WEST, j), outDirection, Blocks.OAK_FENCE);
                                PieceUtil.delete1AxisFenceAT(world, bp.relative(Direction.EAST, j), outDirection, Blocks.OAK_FENCE);
                            }
                        }

                        extraSpaces++;
                    }
                }
            }

            for (int i = 0; i < flatFrame0.blocks.getKey().length; i++) {
                BlockPos bp = flatFrame0.blocks.getKey()[i];
                Direction dir0 = flatFrame0.blocks.getValue()[i];

                if (bp != null && dir0 != null) {
                    if (world.getBlockState(bp).getBlock() != Blocks.WATER || !world.getBlockState(bp).canOcclude()) {

                        Direction dir1 = dir0.getClockWise();
                        PieceUtil.setWLBlock(world, bp, Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                        PieceUtil.setWLBlock(world, bp.below(2), Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                    }
                }
            }

            for (int i = 0; i < 4; i++) {

                if (random.nextFloat() < 0.8) PieceUtil.cornerFencesAT(world, random, flatFrame0, i, Mth.nextInt(random, 2, 6));

                this.legs[i].getPillarStream().forEach(bp -> {
                    float f0 = random.nextFloat();
                    if (f0 < 0.1) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState());
                    } else if (f0 < 0.2) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState());
                    }
                    else PieceUtil.placeUpdateFence(world, bp, Blocks.OAK_LOG.defaultBlockState());

                });

                if (this.legs[i].heightDown > longLegIndex) {
                    longLegIndex = i;
                }
            }

            if (this.legs[longLegIndex].heightDown > 5) {

                PlatformPiece platformPiece0 = new PlatformPiece(world, random, this.legs[longLegIndex].supportPos.below(Mth.nextInt(random, 4, 7)),  Mth.nextInt(random, 7, 10), Mth.nextInt(random, 11, 15));

            } else if (this.legs[longLegIndex].heightDown > 3) {
                if (random.nextFloat() < 0.6) {
                    PlatformPiece platformPiece0 = new PlatformPiece(world, random, this.legs[longLegIndex].supportPos.below(Mth.nextInt(random, 4, 5)),  Mth.nextInt(random, 9, 12), Mth.nextInt(random, 13, 17));
                }
            }


            List<BlockPos> randomBorderBlocks0 = PieceUtil.randomPickedBlocks(random, Lists.newArrayList(platformIterator), 26);

            PieceUtil.decorateBPs(new ATSlabStripesDecorator(world, random, Blocks.SPRUCE_SLAB.defaultBlockState(), Blocks.SPRUCE_PLANKS), randomBorderBlocks0);
        }
    }

    class PlatformPiece {
        public BlockPos platformCenterPos;
        public Leg[] legs = new Leg[4];

        public PlatformPiece(BlockPos pos) {
            this.platformCenterPos = pos;
        }

        public PlatformPiece(WorldGenLevel world, Random random, BlockPos pos, int xWidth, int zWidth) {

            this.platformCenterPos = pos;

            PieceUtil.PositionStack posStack0 = PieceUtil.fillRegion(world, pos, -xWidth / 2, 0, -zWidth / 2, xWidth / 2, 0, zWidth / 2, Blocks.SPRUCE_PLANKS.defaultBlockState());
            PieceUtil.PositionStack posStack1 = posStack0.expand(-2, 0, -2).move(0, 1, 0);

            List<BlockPos> randomBorderBlocks0 = posStack0.randomPickedBlocks(random, Mth.nextInt(random, 12, 16));
            List<BlockPos> randomBorderBlocks1 = posStack1.randomPickedBlocks(random, Mth.nextInt(random, 2, 4));

            PieceUtil.FlatFrame flatFrame0 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2 + 1, pos.getZ() - zWidth / 2 + 1, pos.getX() + xWidth / 2 - 1, pos.getZ() + zWidth / 2 -1, pos.getY() - 1);
            PieceUtil.FlatFrame flatFrame1 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2, pos.getZ() - zWidth / 2, pos.getX() + xWidth / 2, pos.getZ() + zWidth / 2, pos.getY() + 1);

            this.legs[0] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[1] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[2] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);
            this.legs[3] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR);


            for (int i = 0; i < 4; i++) {

                PieceUtil.cornerFencesAT(world, random, flatFrame0, i, Mth.nextInt(random, 2, 3));

                this.legs[i].getPillarStream().forEach(bp -> {
                    float f0 = random.nextFloat();
                    if (f0 < 0.3) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState());
                    } else if (f0 < 0.65) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState());
                    }
                    else PieceUtil.placeUpdateFence(world, bp, Blocks.OAK_LOG.defaultBlockState());

                });
            }

            for (int i = 0; i < flatFrame0.blocks.getKey().length; i++) {
                BlockPos bp = flatFrame0.blocks.getKey()[i];
                Direction dir0 = flatFrame0.blocks.getValue()[i];
                if (bp != null && dir0 != null) {
                    if (world.getBlockState(bp).getBlock() != Blocks.WATER || !world.getBlockState(bp).canOcclude()) {

                        Direction dir1 = dir0.getClockWise();
                        PieceUtil.setWLBlock(world, bp, Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                        PieceUtil.setWLBlock(world, bp.below(2), Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                    }
                }
            }

            PieceUtil.placeFencesInFrameDesintegratedAT(world, random, flatFrame1, Blocks.OAK_FENCE.defaultBlockState(), Blocks.STRIPPED_OAK_WOOD.defaultBlockState(), 0.6F);



            PieceUtil.decorateBPs(new ATSlabStripesDecorator(world, random, Blocks.SPRUCE_SLAB.defaultBlockState(), Blocks.SPRUCE_PLANKS), randomBorderBlocks0);
            PieceUtil.decorateBPs(new ATGroundCoralDecorator(world, random), randomBorderBlocks1);

        }
    }

    class TreasurePlatform extends PlatformPiece {

        public TreasurePlatform(WorldGenLevel world, Random random, BlockPos pos, int xWidth, int zWidth) {
            super(pos);

            PieceUtil.fillRegion(world, pos, -xWidth / 2, 0, -zWidth / 2, xWidth / 2, 0, zWidth / 2, Blocks.SPRUCE_PLANKS.defaultBlockState());
            PieceUtil.fillRegion(world, pos.above(3), -xWidth / 2 - 1, 0, -zWidth / 2 - 1, xWidth / 2 + 1, 0, zWidth / 2 + 1, Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState());
            PieceUtil.fillRegion(world, pos.above(3), -xWidth / 2 + 1, 1, -zWidth / 2 + 1, xWidth / 2 - 1, 1, zWidth / 2 - 1, Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState());

            PieceUtil.FlatFrame flatFrame0 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2 + 1, pos.getZ() - zWidth / 2 + 1, pos.getX() + xWidth / 2 - 1, pos.getZ() + zWidth / 2 -1, pos.getY() - 1);
            PieceUtil.FlatFrame flatFrame1 = new PieceUtil.FlatFrame(pos.getX() - xWidth / 2, pos.getZ() - zWidth / 2, pos.getX() + xWidth / 2, pos.getZ() + zWidth / 2, pos.getY() + 1);

            for (int i = 0; i < flatFrame0.blocks.getKey().length; i++) {
                BlockPos bp = flatFrame0.blocks.getKey()[i];
                Direction dir0 = flatFrame0.blocks.getValue()[i];
                if (bp != null && dir0 != null) {
                    if (world.getBlockState(bp).getBlock() != Blocks.WATER || !world.getBlockState(bp).canOcclude()) {

                        Direction dir1 = dir0.getClockWise();
                        PieceUtil.setWLBlock(world, bp, Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                        PieceUtil.setWLBlock(world, bp.below(2), Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                    }
                }
            }

            this.legs[0] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR || bs.getBlock() == Blocks.SPRUCE_FENCE);
            this.legs[1] = new Leg(world, new BlockPos(pos.getX() - xWidth / 2 + 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR || bs.getBlock() == Blocks.SPRUCE_FENCE);
            this.legs[2] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() - zWidth / 2 + 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR || bs.getBlock() == Blocks.SPRUCE_FENCE);
            this.legs[3] = new Leg(world, new BlockPos(pos.getX() + xWidth / 2 - 1, pos.getY() - 1, pos.getZ() + zWidth / 2 - 1), bs -> bs.getBlock() == Blocks.WATER || bs.getBlock() == Blocks.AIR || bs.getBlock() == Blocks.SPRUCE_FENCE);

            for (int i = 0; i < 4; i++) {

                BlockPos originPos = this.legs[i].supportPos.above(2);

                for (int j = 0; j < 3; j++) {
                    PieceUtil.setWLBlock(world, originPos.above(j), Blocks.SPRUCE_FENCE.defaultBlockState());
                }

                PieceUtil.placeUpdateFence(world, flatFrame1.corners.getKey()[i], Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState());

                this.legs[i].getPillarStream().forEach(bp ->{
                    float f0 = random.nextFloat();
                    if (f0 < 0.6) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState());
                    } else if (f0 < 0.7) {
                        PieceUtil.placeUpdateFence(world, bp, Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState());
                    }
                    else PieceUtil.placeUpdateFence(world, bp, Blocks.OAK_LOG.defaultBlockState());

                });
            }



            BlockPos chestPos = PieceUtil.randomlyPlaceBlockAT(world, random, pos.above(), -xWidth / 2 + 2, 0, -zWidth / 2 + 2, xWidth / 2 - 2, 0, zWidth / 2 - 2, DEBlocks.SEA_CHEST.get().defaultBlockState().rotate(world, this.platformCenterPos.above(), Rotation.getRandom(random)));

            BlockEntity tileentity = world.getBlockEntity(chestPos);
            if (tileentity instanceof ChestBlockEntity) {
                ((ChestBlockEntity)tileentity).setLootTable(new ResourceLocation(DannysExpansion.ID, "chests/anglers_treasure"), random.nextLong());
            }
            for (int i = 0; i < flatFrame1.blocks.getKey().length; i++) {
                BlockPos bp = flatFrame1.blocks.getKey()[i];
                Direction dir0 = flatFrame1.blocks.getValue()[i];
                if (bp != null && dir0 != null) {
                    if (world.getBlockState(bp).getBlock() != Blocks.WATER || !world.getBlockState(bp).canOcclude()) {

                        Direction dir1 = dir0.getClockWise();
                        PieceUtil.setWLBlock(world, bp, Blocks.SPRUCE_FENCE.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1, PipeBlock.NORTH), true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.getOrDefault(dir1.getOpposite(), PipeBlock.NORTH), true));
                    }
                }
            }
        }
    }

    static class Leg {
        public BlockPos supportPos;
        public Predicate<BlockState> predicate;
        public int heightDown;

        public Leg(WorldGenLevel world, BlockPos pos, Predicate<BlockState> predicate) {
            this.supportPos = pos;
            this.predicate = predicate;

            do this.heightDown++;
            while (predicate.test(world.getBlockState(pos.below(this.heightDown))));
        }

        public Stream<BlockPos> getPillarStream() {
            BlockPos[] pillarPositions = new BlockPos[this.heightDown];
            for (int i = 0; i < this.heightDown; i++) {
                pillarPositions[i] = new BlockPos(this.supportPos.getX(), this.supportPos.getY() - i, this.supportPos.getZ());
            }
            return Arrays.stream(pillarPositions);
        }
    }
}


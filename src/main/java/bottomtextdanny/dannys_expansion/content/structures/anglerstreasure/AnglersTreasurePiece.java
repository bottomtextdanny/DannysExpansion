package bottomtextdanny.dannys_expansion.content.structures.anglerstreasure;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.tables.DEStructures;
import bottomtextdanny.braincell.base.Axis2D;
import bottomtextdanny.braincell.base.BCRandomUtil;
import bottomtextdanny.braincell.mod._base.plotter.*;
import bottomtextdanny.braincell.mod._base.plotter.iterator.PlotterData;
import bottomtextdanny.braincell.mod._base.plotter.iterator.PlotterIterator;
import bottomtextdanny.braincell.mod._base.plotter.schema.FlagsEntry;
import bottomtextdanny.braincell.mod._base.plotter.schema.SchemaGetter;
import bottomtextdanny.braincell.mod._base.plotter.schema.SchemaMemo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.*;

import static bottomtextdanny.braincell.mod._base.plotter.iterator_support.PlotterIterators.*;
import static bottomtextdanny.braincell.mod._base.plotter.iterator_support.PlotterPredicates.*;
import static bottomtextdanny.braincell.mod._base.plotter.schema.SchemaManager.parseStatically;
import static net.minecraft.world.level.block.Blocks.*;


public class AnglersTreasurePiece extends ScatteredFeaturePiece {
    public static final SchemaMemo ROOF_SCH = parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/roof_0"));
    public static final List<SchemaMemo> FLOOR_DEFORMATIONS = List.of(
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_0")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_1")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_2")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_3")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_4")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/floor_deformation_5")));
    public static final List<SchemaMemo> LANTERN_HANGS = List.of(
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_0")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_1")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_2")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_3")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_4")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/lantern_hang_5")));
    public static final List<SchemaMemo> ARCS = List.of(
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/arc_0")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/arc_1")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/arc_2")),
            parseStatically(SchemaMemo.schDir(DannysExpansion.ID, "cave_treasure/arc_3")));
    public static final SchemaGetter ARC = (lvl, r) -> BCRandomUtil.randomOf(ARCS, r).getSchema();
    public static final SchemaGetter LANTERN_HANG = (lvl, r) -> BCRandomUtil.randomOf(LANTERN_HANGS, r).getSchema();
    public static final SchemaGetter FLOOR_DEFORMATION = (lvl, r) -> BCRandomUtil.randomOf(FLOOR_DEFORMATIONS, r).getSchema();
    public static final SchemaGetter ROOF = SchemaGetter.of(ROOF_SCH.getSchema());

    public AnglersTreasurePiece(Random random, int x, int z) {
        super(DEStructures.ANGLERS_TREASURE.getPiece(0), x, 64, z, 7, 7, 9, Direction.NORTH);
    }

    public AnglersTreasurePiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag nbt) {
        super(DEStructures.ANGLERS_TREASURE.getPiece(0), nbt);
    }

    @Override
    public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        if (!this.updateAverageGroundHeight(world, boundingBox, 0)) {
			return;
        }
        pos = new BlockPos(pos.getX(), chunkGen.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.OCEAN_FLOOR, world) + 3 + rand.nextInt(5), pos.getZ());
        if (pos.getY() + 6 > chunkGen.getSeaLevel())  return;

        makePlot(world, pos);
//        MainPlatformPiece flatFrame = new MainPlatformPiece(world, pos, Mth.nextInt(rand, 8, 12), Mth.nextInt(rand, 11, 17));
    }

    public static void makePlot(LevelAccessor level, BlockPos root) {
        SchemaPlotter arcPlot = new SchemaPlotter(level, ARC);
        arcPlot.modify(0, arcSolver(BlockFamilies.SPRUCE_PLANKS));

        BoxPlotter roomCube = new BoxPlotter(level, new IntBox(-3, 0, -5, 4, 6, 5));
        roomCube.modify(0, place(AIR.defaultBlockState()));

        SchemaPlotter roof = new SchemaPlotter(level, ROOF);
        roomCube.addChild(Vec3i.ZERO.offset(0, roomCube.getBox().y2, 0), roof);
        roof.modify(0, roofSolver(
                OAK_PLANKS.defaultBlockState(),
                POLISHED_DEEPSLATE_STAIRS.defaultBlockState(),
                COBBLED_DEEPSLATE_STAIRS.defaultBlockState(),
                POLISHED_DEEPSLATE.defaultBlockState()));

        PlanePlotter floor = new PlanePlotter(level, roomCube.getBox().getPlane(Direction.Axis.Y).expand(Axis2D.X, -1));
        roomCube.addChild(Vec3i.ZERO.offset(0, roomCube.getBox().y1, 0), floor);
        floor.modify(0, place(OAK_PLANKS.defaultBlockState()));
        SchemaPlotter floorDeformer = new SchemaPlotter(level, FLOOR_DEFORMATION);
        floorDeformer.modify(0,
                ifBlockState(bsIs(OAK_PLANKS),
                        place(OAK_SLAB.defaultBlockState())));
        floor.modify(0, randomly(0.2F, decorate(floorDeformer)));

        CustomFillerPlotter cobblestoneLayer = new CustomFillerPlotter(level, roomCube.getBox().layerIterable(1, true, true));
        roomCube.addChild(Vec3i.ZERO, cobblestoneLayer);
        cobblestoneLayer.modify(0, outerLayer());

        pillars: {
            IntBox box = roomCube.getBox();
            DeferredPlotter<DeferredPlotters.PillarCache> pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 0, 0
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x1, 5, box.z1), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));

            pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 0, 0
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x2, 5, box.z1), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));

            pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 0, 0
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x1, 5, box.z2), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));

            pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 0, 0
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x2, 5, box.z2), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));

            pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 1, 1
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x1, 5, -1), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));

            pillar = new DeferredPlotter<>(level, DeferredPlotters.pillarUntilBlocked(
                    Direction.DOWN, bpBlockState(level, bsHasTag(BlockTags.BASE_STONE_OVERWORLD).negate()),
                    60, 0, 0, 1, 1
            ));
            roomCube.addChild(Vec3i.ZERO.offset(box.x2 - 1, 5, -1), pillar);
            pillar.modify(0, place(Blocks.OAK_LOG.defaultBlockState()));
        }
        Plotter.compute(roomCube, root, Rotation.NONE);
    }

    private static PlotterIterator<PlotterData> outerLayer() {
        return ifBlockState(bsHasTag(BlockTags.BASE_STONE_OVERWORLD),
                ifBlockState(bsIs(Blocks.DEEPSLATE),
                        place(Blocks.COBBLED_DEEPSLATE.defaultBlockState()))
                        .orElse(place(Blocks.COBBLESTONE.defaultBlockState())));
    }

    private static PlotterIterator<SchemaPlotter.Data> roofSolver(BlockState base, BlockState stairs, BlockState imperfection, BlockState randomBump) {
        return (data) -> {
            FlagsEntry entry = data.flags();
            int flag = entry.firstFlag();
            if (flag == 0) {
                data.setState(base);
            } else if (flag == 3) {
                outerLayer().accept(data);
            } else {
                if (data.random().nextFloat() < 0.05F) data.setState(data.infer(imperfection));
                else if (flag == 1) {
                    data.setState(data.infer(stairs));
                } else if (flag == 2) {
                    if (data.random().nextFloat() < 0.85F)
                        data.setState(data.infer(randomBump));
                    else data.setState(data.infer(stairs));
                }
            }
        };
    }

    private static PlotterIterator<SchemaPlotter.Data> arcSolver(BlockFamily family) {
        Block stairs = family.get(BlockFamily.Variant.STAIRS);
        Block slab = family.get(BlockFamily.Variant.SLAB);
        return (data) -> {
            FlagsEntry entry = data.flags();
            int flag = entry.firstFlag();
            if (flag == 0) {
                data.setState(data.infer(stairs.defaultBlockState()));
            } else if (flag == 1) {
                data.setState(data.infer(slab.defaultBlockState()));
            }
        };
    }
}


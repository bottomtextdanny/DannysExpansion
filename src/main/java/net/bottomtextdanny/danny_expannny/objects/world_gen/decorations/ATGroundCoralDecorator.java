package net.bottomtextdanny.danny_expannny.objects.world_gen.decorations;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.FlagMatrix;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PieceUtil;
import net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util.PosDecorator;
import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class ATGroundCoralDecorator extends PosDecorator {
    public ImmutableList<FlagMatrix> shapeMatrices;
    public final BlockState[] blocks;
    public final BlockState[] corals;

    public ATGroundCoralDecorator(WorldGenLevel world, Random rand) {
        super(world, rand);
        this.blocks = new BlockState[] {
                Blocks.FIRE_CORAL_BLOCK.defaultBlockState(),
                Blocks.BRAIN_CORAL_BLOCK.defaultBlockState(),
                Blocks.BUBBLE_CORAL_BLOCK.defaultBlockState(),
                Blocks.TUBE_CORAL_BLOCK.defaultBlockState(),
                Blocks.HORN_CORAL_BLOCK.defaultBlockState()};
        this.corals = new BlockState[] {
                Blocks.FIRE_CORAL_FAN.defaultBlockState(),
                Blocks.BRAIN_CORAL_FAN.defaultBlockState(),
                Blocks.BUBBLE_CORAL_FAN.defaultBlockState(),
                Blocks.TUBE_CORAL_FAN.defaultBlockState(),
                Blocks.HORN_CORAL_FAN.defaultBlockState(),
                Blocks.FIRE_CORAL_WALL_FAN.defaultBlockState(),
                Blocks.BRAIN_CORAL_WALL_FAN.defaultBlockState(),
                Blocks.BUBBLE_CORAL_WALL_FAN.defaultBlockState(),
                Blocks.TUBE_CORAL_WALL_FAN.defaultBlockState(),
                Blocks.HORN_CORAL_WALL_FAN.defaultBlockState()};
    }

    @Override
    public void generate(BlockPos position) {
        FlagMatrix matrix0 = new FlagMatrix(position, this.random).rotative();
        matrix0.addRegion(position, position.offset(1, 0, 1));
        matrix0.addRegionWithIntegrity(position.above(), position.offset(1, 1, 1), 0.35F);

        FlagMatrix matrix1 = new FlagMatrix(position, this.random).rotative();
        matrix1.addPosition(Pair.bp(position));
        matrix1.mayAddPosition(Pair.bp(position.above().north()), 0.5F);
        matrix1.mayAddPosition(Pair.bp(position.above().south()), 0.5F);
        matrix1.mayAddPosition(Pair.bp(position.above().west()), 0.5F);
        matrix1.mayAddPosition(Pair.bp(position.above().east()), 0.5F);

        BlockState block = DEUtil.getRandomObject(this.blocks);

        this.shapeMatrices = ImmutableList.of(matrix0, matrix1);
        DEUtil.getRandomObject(this.shapeMatrices).getFinal().forEach(pbp -> {
            BlockPos bp = pbp.getKey();
            PieceUtil.setBlockOnSpace(getWorld(), bp, block);

            if (this.random.nextFloat() > 0.4) {
                for (Direction dir : Direction.values()) {
                    BlockPos coralPos = bp.relative(dir);

                    if (this.random.nextFloat() > 0.25 && getWorld().getBlockState(coralPos).getBlock() == Blocks.WATER) {
                        int i = this.random.nextInt(5);

                        if (dir.get2DDataValue() != -1) {
                            i += 5;

                            getWorld().setBlock(coralPos, this.corals[i].setValue(BlockStateProperties.WATERLOGGED, true).setValue(CoralWallFanBlock.FACING, dir), 0);
                        } else {

                            getWorld().setBlock(coralPos, this.corals[i].setValue(BlockStateProperties.WATERLOGGED, true), 0);
                        }
                    }
                }
            }

        });
    }
}

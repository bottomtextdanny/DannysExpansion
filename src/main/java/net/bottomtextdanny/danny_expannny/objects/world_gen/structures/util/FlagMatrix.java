package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlagMatrix implements IPosMatrix {
    protected BlockPos focalPoint;
    public byte flag;
    protected List<Pair<BlockPos, Byte>> matrix = new ArrayList<>();
    public boolean rotative;
    private final Random random;

    public FlagMatrix(BlockPos position, Random rand) {
        this.focalPoint = position;
        this.random = rand;
    }

    public FlagMatrix(BlockPos position, Random rand, List<Pair<BlockPos, Byte>> sup) {
        this.focalPoint = position;
        this.matrix = sup;
        this.random = rand;
    }

    public FlagMatrix rotative() {
        this.rotative = true;

        return this;
    }

    /**
     *
     * @return a rotated matrix for the given direction supposing the the matrix direction is north.
     * doesn't change the instance's matrix on call.
     * doesn't rotate blockstates.
     */
    public List<Pair<BlockPos, Byte>> rotateMatrix(Rotation rotation) {
        List<Pair<BlockPos, Byte>> list = Arrays.asList(new Pair[this.matrix.size()]);

        switch (rotation) {
            case CLOCKWISE_90:
                for (int i = 0; i < this.matrix.size(); i++) {
                    BlockPos bpOffset = getPosAt(i).offset(-this.focalPoint.getX(), -this.focalPoint.getY(), -this.focalPoint.getZ());

                    list.set(i, Pair.of(new BlockPos(this.focalPoint.getX() + bpOffset.getZ(), this.focalPoint.getY() + bpOffset.getY(), this.focalPoint.getZ() + -bpOffset.getX()), getFlagAt(i)));
                }
                break;
            case COUNTERCLOCKWISE_90:
                for (int i = 0; i < this.matrix.size(); i++) {
                    BlockPos bpOffset = getPosAt(i).offset(-this.focalPoint.getX(), -this.focalPoint.getY(), -this.focalPoint.getZ());

                    list.set(i, Pair.of(new BlockPos(this.focalPoint.getX() + -bpOffset.getZ(), this.focalPoint.getY() + bpOffset.getY(), this.focalPoint.getZ() + bpOffset.getX()), getFlagAt(i)));
                }
                break;
            case CLOCKWISE_180:
                for (int i = 0; i < this.matrix.size(); i++) {
                    BlockPos bpOffset = getPosAt(i).offset(-this.focalPoint.getX(), -this.focalPoint.getY(), -this.focalPoint.getZ());

                    list.set(i, Pair.of(new BlockPos(this.focalPoint.getX() + -bpOffset.getZ(), this.focalPoint.getY() + bpOffset.getY(), this.focalPoint.getZ() + -bpOffset.getX()), getFlagAt(i)));
                }
                break;
            default:
                return this.matrix;
        }
        return list;
    }

    public void setMatrix(List<Pair<BlockPos, Byte>> matrix) {
        this.matrix = matrix;
    }

    public void addPosition(Pair<BlockPos, Byte> pos) {
        this.matrix.add(pos);
    }

    public boolean mayAddPosition(Pair<BlockPos, Byte> pos, float probability) {
        if (this.random.nextFloat() < probability) {

            addPosition(pos);
            return true;
        } else return false;
    }

    public void addRegionWithIntegrity(BlockPos posStart, BlockPos posEnd, float integrity) {
        addRegionWithIntegrity(posStart, posEnd, integrity, 0);
    }

    public void addRegionWithIntegrity(BlockPos posStart, BlockPos posEnd, float integrity, int flag) {
        for (int i = 0; i <= posEnd.getX() - posStart.getX(); i++) {
            for (int j = 0; j <= posEnd.getY() - posStart.getY(); j++) {
                for (int k = 0; k <= posEnd.getZ() - posStart.getZ(); k++) {
                    if (this.random.nextFloat() < integrity) addPosition(Pair.of(new BlockPos(posStart.getX() + i, posStart.getY() + j, posStart.getZ() + k), (byte)flag));
                }
            }
        }
    }

    public void addRegion(BlockPos posStart, BlockPos posEnd) {
        addRegion(posStart, posEnd, 0);
    }

    public void addRegion(BlockPos posStart, BlockPos posEnd, int flag) {
        for (int i = 0; i <= posEnd.getX() - posStart.getX(); i++) {
            for (int j = 0; j <= posEnd.getY() - posStart.getY(); j++) {
                for (int k = 0; k <= posEnd.getZ() - posStart.getZ(); k++) {
                    addPosition(Pair.of(new BlockPos(posStart.getX() + i, posStart.getY() + j, posStart.getZ() + k), (byte)flag));
                }
            }
        }
    }

    public boolean mayAddRegion(BlockPos posStart, BlockPos posEnd, float probability) {
        if (this.random.nextFloat() < probability) {
            addRegion(posStart, posEnd);
            return true;
        } else return false;
    }

    public boolean mayAddRegion(BlockPos posStart, BlockPos posEnd, float probability, int flag) {
        if (this.random.nextFloat() < probability) {
            addRegion(posStart, posEnd, flag);
            return true;
        } else return false;
    }

    @Override
    public List<Pair<BlockPos, Byte>> getMatrixList() {
        return this.matrix;
    }

    public List<Pair<BlockPos, Byte>> getFinal() {
        return this.rotative ? rotateMatrix(Rotation.getRandom(this.random)) : this.matrix;
    }

    public BlockPos getPosAt(int index) {
        return getMatrixList().get(index).getKey();
    }

    public byte getFlagAt(int index) {
        return getMatrixList().get(index).getValue();
    }

    public BlockPos getFocalPoint() {
        return this.focalPoint;
    }
}

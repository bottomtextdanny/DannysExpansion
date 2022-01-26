package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public abstract class PosDecorator {
    protected final Random random;
    protected final SplittableRandom split;
    protected WorldGenLevel world;
    public List<Block> mask;

    @Deprecated
    public PosDecorator(WorldGenLevel world, Random rand) {
        this.world = world;
        this.random = rand;
        this.split = null;
    }

    public PosDecorator(WorldGenLevel world, SplittableRandom split) {
        this.world = world;
        this.split = split;
        this.random = ThreadLocalRandom.current();
    }

    public int nextInt() {
        if (this.split != null) {
            return this.split.nextInt();
        } else {
            return this.random.nextInt();
        }
    }

    public int nextInt(int bound) {
        if (this.split != null) {
            return this.split.nextInt(bound);
        } else {
            return this.random.nextInt(bound);
        }
    }

    public float nextFloat() {
        if (this.split != null) {
            return (float) this.split.nextDouble();
        } else {
            return this.random.nextFloat();
        }
    }

    public double nextDouble() {
        if (this.split != null) {
            return this.split.nextDouble();
        } else {
            return this.random.nextDouble();
        }
    }

    public abstract void generate(BlockPos position);

    public WorldGenLevel getWorld() {
        return this.world;
    }
}

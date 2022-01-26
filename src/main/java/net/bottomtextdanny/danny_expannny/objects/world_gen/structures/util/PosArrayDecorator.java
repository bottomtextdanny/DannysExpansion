package net.bottomtextdanny.danny_expannny.objects.world_gen.structures.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Random;

public abstract class PosArrayDecorator {
    protected final Random random;
    private final WorldGenLevel world;
    public List<Block> mask;

    public PosArrayDecorator(WorldGenLevel world, Random rand) {
        this.world = world;
        this.random = rand;
    }

    public abstract void generate(BlockPos... position);

    public WorldGenLevel getWorld() {
        return this.world;
    }
}

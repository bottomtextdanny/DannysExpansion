package net.bottomtextdanny.dannys_expansion.common.crumbs;

import net.minecraft.core.BlockPos;

public class CrumbSection {
    public static final int CHUNK_X_SHIFT = 21;
    public static final int CHUNK_SERIAL_SHIFT = 4;
    public static final int CHUNK_DISTRIBUTION = 24;
    public static final long CHUNK_Z_OFFSET = 2097152L;
    private final CrumbMultiMap bulks = new CrumbMultiMap(CrumbRoot.searchableIterator());

    public CrumbSection() {
        super();
    }

    public CrumbMultiMap getBulks() {
        return this.bulks;
    }

    public static long computeChunkSectionSerial(BlockPos pos) {
        return computeChunkSectionSerial(pos.getX() >> 4, pos.getZ() >> 4, pos.getY() / CHUNK_DISTRIBUTION);
    }

    public static long computeChunkSectionSerial(long x, long z, int section) {
        return ((x << CHUNK_X_SHIFT) + z + CHUNK_Z_OFFSET << CHUNK_SERIAL_SHIFT) + section;
    }
}

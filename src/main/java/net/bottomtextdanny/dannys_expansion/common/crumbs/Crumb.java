package net.bottomtextdanny.dannys_expansion.common.crumbs;

import net.bottomtextdanny.dannys_expansion.common.crumbs.crumb_extensions.CrumbSearchable;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.chunk.ChunkCapability;
import net.bottomtextdanny.danny_expannny.capabilities.chunk.ChunkCrumbModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCrumbsModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;

public abstract class Crumb {
    private boolean removed;
    protected final LevelCrumbsModule levelModule;
    protected final Level level;
    protected ChunkCrumbModule chunkModule;
    private final int transientId;
    protected LevelChunk chunk;
    private final CrumbRoot<?> root;
    private Vec3 position;
    private BlockPos blockPosition;

    protected Crumb(CrumbRoot<?> root, Level level) {
        super();
        this.root = root;
        this.level = level;
        this.levelModule = CapabilityHelper.get(this.level, LevelCapability.CAPABILITY).getCrumbsModule();
        this.transientId = this.levelModule.getIdForCrumb(this);
    }

    public void setup(double x, double y, double z) {
        setPosition(x, y, z);
        this.levelModule.addCrumb(this);
        setChunk(this.level.getChunkAt(this.blockPosition));
    }

    public void setPosition(double x, double y, double z) {
        if (this instanceof CrumbSearchable searchable) {
            if (this.chunk == null || !this.level.hasChunk(this.chunk.getPos().x, this.chunk.getPos().z)) return;
            long newSection = CrumbSection.computeChunkSectionSerial(this.blockPosition);
            this.position = new Vec3(x, y, z);
            this.blockPosition = new BlockPos(x, y, z);
            if (newSection != searchable.getChunkSection()) {
                this.levelModule.removeFromSection(searchable.getChunkSection(), this);
                this.levelModule.addToSection(newSection, this);
                searchable.setChunkSection(newSection);
            }
        } else {
            this.position = new Vec3(x, y, z);
            this.blockPosition = new BlockPos(x, y, z);

        }
    }

    public final void setChunk(LevelChunk chunk) {
        if (chunk == null) return;
        if (chunk != this.chunk) {
            if (this.chunkModule != null) {
                this.chunkModule.removeCrumb(this);
            }

            this.chunk = chunk;
            updateChunkModule();
            this.chunkModule.addCrumb(this);
        }
    }

    public int getTransientId() {
        return this.transientId;
    }

    public ChunkCrumbModule getChunkModule() {
        return this.chunkModule;
    }

    public void updateChunkModule() {
        this.chunkModule = CapabilityHelper.get(this.chunk, ChunkCapability.CAPABILITY).getCrumbsModule();
    }

    public void remove() {
        this.removed = true;
        this.levelModule.deferRemoval(this);
    }

    public LevelChunk getChunk() {
        return this.chunk;
    }

    public Level getLevel() {
        return this.level;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }

    public CrumbRoot<?> getRoot() {
        return this.root;
    }
}

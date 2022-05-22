package bottomtextdanny.flagged_schema_block;

import bottomtextdanny.dannys_expansion.tables.DEBlockEntities;
import com.google.common.collect.Maps;
import bottomtextdanny.braincell.mod.network.Connection;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.Map;

public class FlaggedSchemaBlockEntity extends BlockEntity {
    private String T_SHOW = "show";
    private String T_POSITION = "posi";
    private String T_ENTRIES = "entr";
    private String T_BLOCK_ID = "bk";
    private int boxPosX = 1;
    private int boxPosY = 0;
    private int boxPosZ = 1;
    private int boxSizeX = 5;
    private int boxSizeY = 5;
    private int boxSizeZ = 5;
    private int boxOffsetX = 0;
    private int boxOffsetY = 0;
    private int boxOffsetZ = 0;
    private boolean show = true;
    private Map<Block, SerializableSchemaMakerEntry> entries = Maps.newIdentityHashMap();

    public FlaggedSchemaBlockEntity(BlockPos position, BlockState blockState) {
        super(DEBlockEntities.FLAGGED_SCHEMA_BLOCK.get(), position, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tagMap) {
        ListTag serializedEntries = new ListTag();
        tagMap.putBoolean(T_SHOW, this.show);
        tagMap.putIntArray(T_POSITION, new int[] {
                this.boxPosX,
                this.boxPosY,
                this.boxPosZ,
                this.boxSizeX,
                this.boxSizeY,
                this.boxSizeZ,
                this.boxOffsetX,
                this.boxOffsetY,
                this.boxOffsetZ
        });
        entries.forEach((block, entry) -> {
            CompoundTag entryTag = entry.getTag();
            entryTag.putInt(T_BLOCK_ID, Registry.BLOCK.getId(block));
            serializedEntries.add(entryTag);
        });
        tagMap.put(T_ENTRIES, serializedEntries);
        super.saveAdditional(tagMap);
    }

    @Override
    public void load(CompoundTag tagMap) {
        int[] position = tagMap.getIntArray(T_POSITION);
        this.boxPosX = position[0];
        this.boxPosY = position[1];
        this.boxPosZ = position[2];
        this.boxSizeX = position[3];
        this.boxSizeY = position[4];
        this.boxSizeZ = position[5];
        this.boxOffsetX = position[6];
        this.boxOffsetY = position[7];
        this.boxOffsetZ = position[8];
        this.show = tagMap.getBoolean(T_SHOW);
        this.entries = Maps.newIdentityHashMap();
        ListTag serializedEntries = tagMap.getList(T_ENTRIES, 10);
        serializedEntries.stream().map(t -> (CompoundTag)t).forEach((tag) -> {
            Block block = Registry.BLOCK.byId(tag.getInt(T_BLOCK_ID));
            entries.put(block, SerializableSchemaMakerEntry.fromCompoundTag(tag));
        });
        super.load(tagMap);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public boolean usedBy(Player player) {
        if (!player.canUseGameMasterBlocks()) {
            return false;
        } else {
            Connection.doClientSide(() -> {
                setClientScreen();
            });

            return true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void setClientScreen() {
        Minecraft.getInstance().setScreen(new FlaggedSchemaScreen(this));
    }

    public void setEntryMap(Map<Block, SerializableSchemaMakerEntry> entries) {
        this.entries = entries;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public void setBoxPosition(int x, int y, int z) {
        this.boxPosX = x;
        this.boxPosY = y;
        this.boxPosZ = z;
    }

    public void setSize(int x, int y, int z) {
        this.boxSizeX = x;
        this.boxSizeY = y;
        this.boxSizeZ = z;
    }

    public void setOffset(int x, int y, int z) {
        this.boxOffsetX = x;
        this.boxOffsetY = y;
        this.boxOffsetZ = z;
    }

    public Map<Block, SerializableSchemaMakerEntry> getEntries() {
        return entries;
    }

    public int getBoxPosX() {
        return boxPosX;
    }

    public int getBoxPosY() {
        return boxPosY;
    }

    public int getBoxPosZ() {
        return boxPosZ;
    }

    public int getBoxSizeX() {
        return boxSizeX;
    }

    public int getBoxSizeY() {
        return boxSizeY;
    }

    public int getBoxSizeZ() {
        return boxSizeZ;
    }

    public int getBoxOffsetX() {
        return boxOffsetX;
    }

    public int getBoxOffsetY() {
        return boxOffsetY;
    }

    public int getBoxOffsetZ() {
        return boxOffsetZ;
    }
}

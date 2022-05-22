package bottomtextdanny.flagged_schema_block;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import bottomtextdanny.braincell.mod.network.BCPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MSGUpdateClientFlaggedSchemaBlock implements BCPacket<MSGUpdateClientFlaggedSchemaBlock> {
    private final long blockEntity;
    private final int posX, posY, posZ,
            sizeX, sizeY, sizeZ,
            offsetX, offsetY, offsetZ;
    private Map<Block, SerializableSchemaMakerEntry> serverEntries;
    private Map<Block, SerializableSchemaMakerEntry> clientEntries;

    public MSGUpdateClientFlaggedSchemaBlock(long blockEntity, int posX, int posY, int posZ,
                                             int sizeX, int sizeY, int sizeZ,
                                             int offsetX, int offsetY, int offsetZ) {
        super();
        this.blockEntity = blockEntity;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public void setServerEntries(Map<Block, SerializableSchemaMakerEntry> serverEntries) {
        this.serverEntries = serverEntries;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeLong(this.blockEntity);
        stream.writeVarInt(this.posX);
        stream.writeVarInt(this.posY);
        stream.writeVarInt(this.posZ);
        stream.writeVarInt(this.sizeX);
        stream.writeVarInt(this.sizeY);
        stream.writeVarInt(this.sizeZ);
        stream.writeVarInt(this.offsetX);
        stream.writeVarInt(this.offsetY);
        stream.writeVarInt(this.offsetZ);
        stream.writeVarInt(this.serverEntries.size());
        Block block;
        SerializableSchemaMakerEntry entry;

        boolean hasFlags;
        boolean hasProperties;
        for (Map.Entry<Block, SerializableSchemaMakerEntry> en : this.serverEntries.entrySet()) {
            block = en.getKey();
            entry = en.getValue();
            hasProperties = entry.properties() != null;
            hasFlags = entry.flags() != null;
            stream.writeVarInt(Registry.BLOCK.getId(block));
            stream.writeUtf(entry.name());
            stream.writeVarInt(hasProperties ? entry.properties().size() : -1);

            if (hasProperties) {
                entry.properties().forEach(stream::writeUtf);
            }

            stream.writeBoolean(hasFlags);
            if (hasFlags) {
                stream.writeVarIntArray(entry.flags().toIntArray());
            }
        }
    }

    @Override
    public MSGUpdateClientFlaggedSchemaBlock deserialize(FriendlyByteBuf stream) {
        MSGUpdateClientFlaggedSchemaBlock packet = null;
        Map<Block, SerializableSchemaMakerEntry> entryMap = null;
        try {
            packet = new MSGUpdateClientFlaggedSchemaBlock(
                    stream.readLong(),
                    stream.readVarInt(), stream.readVarInt(), stream.readVarInt(),
                    stream.readVarInt(), stream.readVarInt(), stream.readVarInt(),
                    stream.readVarInt(), stream.readVarInt(), stream.readVarInt());
            Map<Block, SerializableSchemaMakerEntry> local = Maps.newIdentityHashMap();
            int size = stream.readVarInt();

            for (int i = 0; i < size; i++) {
                Block blockId = Registry.BLOCK.byId(stream.readVarInt());
                String name = stream.readUtf();
                List<String> properties = Lists.newArrayList();
                int propertiesSize = stream.readVarInt();

                if (propertiesSize != -1) {
                    for (int j = 0; j < propertiesSize; j++) {
                        properties.add(stream.readUtf());
                    }
                }

                boolean hasFlags = stream.readBoolean();
                IntList flags = new IntArrayList();
                if (hasFlags) {
                    int[] rawFlags = stream.readVarIntArray();
                    flags.addAll(Arrays.stream(rawFlags).boxed().toList());
                }
                local.put(blockId, new SerializableSchemaMakerEntry(name, properties, flags));
            }
            entryMap = local;
        } catch (Exception ignored) {}



        if (packet != null) {
            packet.clientEntries = entryMap;
        }
        return packet;
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (this.clientEntries != null &&
                world.getBlockEntity(BlockPos.of(this.blockEntity)) instanceof FlaggedSchemaBlockEntity be) {
            be.setBoxPosition(this.posX, this.posY, this.posZ);
            be.setSize(this.sizeX, this.sizeY, this.sizeZ);
            be.setOffset(this.offsetX, this.offsetY, this.offsetZ);
            be.setEntryMap(this.clientEntries);
        }
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    @Override
    public SimpleChannel mainChannel() {
        return DEPacketInitialization.CHANNEL;
    }
}

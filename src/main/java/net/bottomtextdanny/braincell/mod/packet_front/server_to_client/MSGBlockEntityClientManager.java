package net.bottomtextdanny.braincell.mod.packet_front.server_to_client;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnServer;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_front.BCPacketInitialization;
import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.bottomtextdanny.braincell.mod.serialization.serializers.util.H_WorldDataParser;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.dannys_expansion.core.interfaces.BEClientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;

public final class MSGBlockEntityClientManager implements BCPacket<MSGBlockEntityClientManager> {
    private final BlockPos pos;
    private final int flag;
    @OnlyHandledOnServer private WorldPacketData<?>[] serverObjects;
    @OnlyHandledOnServer private ServerLevel serverLevel;
    @OnlyHandledOnClient private Object[] clientObjects;

    @OnlyIn(Dist.CLIENT)
    public MSGBlockEntityClientManager(FriendlyByteBuf stream) {
        this.pos = BlockPos.of(stream.readLong());
        this.flag = stream.readInt();
        final int size = stream.readInt();
        this.clientObjects = new Object[size];
        Connection.doClientSide(() -> {
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    final int serializerId = stream.readInt();
                    if (serializerId == -1) continue;
                    final SerializerMark<?> serializer =
                            Braincell.common().getSerializerLookUp().getSerializerFromId(serializerId);
                    if (serializer != null) {
                        this.clientObjects[i] = H_WorldDataParser.readDataFromPacket(
                                stream,
                                serializer,
                                Minecraft.getInstance().level);
                    }
                }
            }
        });
    }

    public MSGBlockEntityClientManager(BlockPos blockPosition, int flag, ServerLevel level, @Nullable WorldPacketData<?>[] objects) {
        this.pos = blockPosition;
        this.flag = flag;
        this.serverObjects = objects;
        this.serverLevel = level;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeLong(this.pos.asLong());
        stream.writeInt(this.flag);

        if (this.serverObjects == null) {
            stream.writeInt(0);
        } else {
            stream.writeInt(this.serverObjects.length);

            for (WorldPacketData<?> object : this.serverObjects) {

                if (object != null) {
                    stream.writeInt(Braincell.common().getSerializerLookUp().getIdFromSerializer(
                            object.getSerializer()));
                    object.writeToStream(stream, this.serverLevel);
                } else {
                    stream.writeInt(-1);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public MSGBlockEntityClientManager deserialize(FriendlyByteBuf stream) {
        return new MSGBlockEntityClientManager(stream);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        Connection.doClientSide(() -> {
            if (world.getBlockEntity(this.pos) instanceof BEClientManager blockEntity) {
                blockEntity.clientCallOutHandler(this.flag, ObjectFetcher.of(this.clientObjects));
            }
        });
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    @Override
    public SimpleChannel mainChannel() {
        return BCPacketInitialization.CHANNEL;
    }
}

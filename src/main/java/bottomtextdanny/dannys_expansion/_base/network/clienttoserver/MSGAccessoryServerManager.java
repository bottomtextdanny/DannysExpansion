package bottomtextdanny.dannys_expansion._base.network.clienttoserver;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod.network.BCEntityPacket;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import bottomtextdanny.braincell.mod._base.serialization.SerializerMark;
import bottomtextdanny.braincell.mod._base.serialization.util.H_WorldDataParser;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGAccessoryServerManager extends BCEntityPacket<MSGAccessoryServerManager, Player> {
    private final int accessoryIndex;
    private final int flag;
    private WorldPacketData<?>[] packetObjects;
    private FriendlyByteBuf stream;
    private Object[] objects;

    public MSGAccessoryServerManager(FriendlyByteBuf stream) {
        super(stream.readInt());
        this.accessoryIndex = stream.readInt();
        this.flag = stream.readInt();
        this.stream = stream;
    }
    
    public MSGAccessoryServerManager(int entityId, int accessoryIndex, int flag, WorldPacketData<?>[] objects) {
        super(entityId);
        this.accessoryIndex = accessoryIndex;
        this.flag = flag;
        this.packetObjects = objects;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        stream.writeInt(this.accessoryIndex);
        stream.writeInt(this.flag);

        Connection.doClientSide(() -> {
            if (this.packetObjects == null) {
                stream.writeInt(0);
            } else {

                int bound = Math.min(5, this.packetObjects.length);

                stream.writeInt(bound);

                for (int i = 0; i < bound; i++) {
                    WorldPacketData<?> object = this.packetObjects[i];
                    if (object != null) {
                        stream.writeInt(Braincell.common().getSerializerLookUp().getIdFromSerializer(object.getSerializer()));
                        object.writeToStream(stream, Minecraft.getInstance().level);
                    } else {
                        stream.writeInt(-1);
                    }
                }
            }
        });
    }

    @Override
    public MSGAccessoryServerManager deserialize(FriendlyByteBuf stream) {
        return new MSGAccessoryServerManager(stream);
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (world.getEntity(this.getEntityId()) instanceof Player player && player == ctx.getSender()) {
            final int size = this.stream.readInt();
            this.objects = new Object[size];
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    final int serializerId = this.stream.readInt();
                    final SerializerMark<?> serializer = Braincell.common().getSerializerLookUp().getSerializerFromId(serializerId);
                    if (serializer != null) {
                        this.objects[i] = H_WorldDataParser.readDataFromPacket(this.stream, serializer, world);
                    }
                }
            }

            DEAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
            if (this.accessoryIndex >= 0 && this.accessoryIndex < accessoryModule.getCoreAccessoryList().size()) {

                accessoryModule.getCoreAccessoryList().get(this.accessoryIndex).accessoryServerManager(this.flag, ObjectFetcher.of(this.objects));
            }
        }
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.SERVER;
    }

    @Override
    public SimpleChannel mainChannel() {
        return DEPacketInitialization.CHANNEL;
    }
}

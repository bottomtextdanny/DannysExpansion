package bottomtextdanny.dannys_expansion._base.network.servertoclient;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.Braincell;
import bottomtextdanny.braincell.base.ObjectFetcher;
import bottomtextdanny.braincell.mod._base.serialization.SerializerMark;
import bottomtextdanny.braincell.mod._base.serialization.util.H_WorldDataParser;
import bottomtextdanny.braincell.mod.network.BCEntityPacket;
import bottomtextdanny.braincell.mod.network.Connection;
import bottomtextdanny.braincell.mod._base.serialization.WorldPacketData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableObject;

public class MSGCoreAccessoryClientManager extends BCEntityPacket<MSGCoreAccessoryClientManager, Player> {
    private int accessoryIndex;
    private int flag;
    private WorldPacketData<?>[] packetObjects;
    private Object[] objects;
	private ServerLevel level;

    public MSGCoreAccessoryClientManager(int playerId) {
	    super(playerId);
    }

    public MSGCoreAccessoryClientManager(
			int entityId,
			int accessoryIndex,
			int flag,
			WorldPacketData<?>[] objects,
			ServerLevel level) {
    	super(entityId);
        this.accessoryIndex = accessoryIndex;
        this.flag = flag;
        this.packetObjects = objects;
		this.level = level;
    }

	@Override
	public void serialize(FriendlyByteBuf stream) {
		super.serialize(stream);
		stream.writeInt(this.accessoryIndex);
		stream.writeInt(this.flag);
		
		if (this.packetObjects == null) {
			stream.writeInt(0);
		} else {
			int bound = this.packetObjects.length;
			
			stream.writeInt(bound);
			
			for (int i = 0; i < bound; i++) {
				WorldPacketData<?> object = this.packetObjects[i];
				if (object != null) {
					stream.writeInt(Braincell.common().getSerializerLookUp().getIdFromSerializer(object.getSerializer()));
					object.writeToStream(stream, this.level);
				} else {
					stream.writeInt(-1);
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public MSGCoreAccessoryClientManager deserialize(FriendlyByteBuf stream) {
		MutableObject<MSGCoreAccessoryClientManager> msg = new MutableObject(null);
		Connection.doClientSide(() -> {
			msg.setValue(new MSGCoreAccessoryClientManager(stream.readInt()));

			msg.getValue().accessoryIndex = stream.readInt();
			msg.getValue().flag = stream.readInt();

			final int size = stream.readInt();
			msg.getValue().objects = new Object[size];

			if (size > 0) {
				for (int i = 0; i < size; i++) {
					final int serializerId = stream.readInt();
					final SerializerMark<?> serializer = Braincell.common().getSerializerLookUp().getSerializerFromId(serializerId);

					if (serializer != null) {
						msg.getValue().objects[i] = H_WorldDataParser.readDataFromPacket(stream, serializer, Minecraft.getInstance().level);
					}
				}
			}
		});
		return msg.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void postDeserialization(NetworkEvent.Context ctx, Level world) {
    	Player player = getEntityAsReceptor(world);
		Connection.doClientSide(() -> {
			if (player != null && player.isAlive()) {
				DEAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);

				accessoryModule.getCoreAccessoryList().get(this.accessoryIndex).accessoryClientManager(this.flag, ObjectFetcher.of(this.objects));
			}
		});
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

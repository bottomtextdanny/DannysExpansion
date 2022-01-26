package net.bottomtextdanny.danny_expannny.network.servertoclient;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.bottomtextdanny.braincell.mod.serialization.serializers.util.H_WorldDataParser;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.Util;
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

import java.util.List;
import java.util.function.Function;

public class MSGAccessoryClientManager extends BCEntityPacket<MSGAccessoryClientManager, Player> {
	private final List<Function<PlayerAccessoryModule, IAccessory>> ACCESSORY_GETTER = Util.make(() -> {
		List<Function<PlayerAccessoryModule, IAccessory>> list = Lists.newArrayListWithCapacity(PlayerAccessoryModule.ALL_ACCESSORIES_SIZE);
		list.add(module -> module.getCoreAccessoryList().get(0));
		list.add(module -> module.getCoreAccessoryList().get(1));
		list.add(module -> module.getCoreAccessoryList().get(2));
		list.add(module -> module.getCoreAccessoryList().get(3));
		list.add(module -> module.getCoreAccessoryList().get(4));
		list.add(module -> module.getHandManager().getMain());
		list.add(module -> module.getHandManager().getOff());
		return list;
	});
    private int accessoryIndex;
    private int flag;
    private WorldPacketData<?>[] packetObjects;
    private Object[] objects;
	private ServerLevel level;


    public MSGAccessoryClientManager(int playerId) {
	    super(playerId);
    }

    public MSGAccessoryClientManager(
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
	public MSGAccessoryClientManager deserialize(FriendlyByteBuf stream) {
		MutableObject<MSGAccessoryClientManager> msg = new MutableObject(null);
		Connection.doClientSide(() -> {
			msg.setValue(new MSGAccessoryClientManager(stream.readInt()));

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
				PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
                this.ACCESSORY_GETTER.get(this.accessoryIndex).apply(accessoryModule).accessoryClientManager(this.flag, ObjectFetcher.of(this.objects));
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

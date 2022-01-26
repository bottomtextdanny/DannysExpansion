package net.bottomtextdanny.braincell.mod.packet_front;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGBlockEntityClientManager;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGEntityAnimation;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGEntityClientManager;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGUpdateDataManager;
import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

public final class BCPacketInitialization {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Braincell.ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void initializeNetworkPackets() {
        MutableInt id = new MutableInt(0);
        initializeClientNetworkPackets(id);
    }

    private static void initializeClientNetworkPackets(MutableInt token) {
        register(token, new MSGBlockEntityClientManager(null, -1, null, null));
        register(token, new MSGEntityAnimation(-1, -1, -1));
        register(token, new MSGEntityClientManager(-1, -1, null, null));
        register(token, new MSGUpdateDataManager(-1, null));
    }

    @SuppressWarnings("unchecked")
    private static <T extends BCPacket<T>> T register(MutableInt token, T packet) {
        CHANNEL.registerMessage(
                token.getAndIncrement(),
                (Class<T>) packet.getClass(),
                packet::_serializeHandler,
                packet::deserialize,
                packet::_postDeserializationHandler);
        return packet;
    }
}

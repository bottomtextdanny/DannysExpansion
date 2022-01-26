package net.bottomtextdanny.danny_expannny.network;

import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.network.clienttoserver.*;
import net.bottomtextdanny.danny_expannny.network.servertoclient.*;
import net.bottomtextdanny.danny_expannny.network.servertoclient.player.MSGSendPlayerAccessories;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

public class DEPacketInitialization {
    private static final String PROTOCOL_VERSION = "0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DannysExpansion.ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void initializeNetworkPackets() {
        MutableInt id = new MutableInt(0);
        register(id, new MSGUpdateLazyCraftInventory());
        register(id, new MSGSetLazyCraftResult(-1));
	    register(id, new MSGMountActions(-1, -1));
	    register(id, new MSGCheckJump(-1));
	    register(id, new MSGAccessoryClientManager(-1, -1, -1, null, null));
        register(id, new MSGSendPlayerAccessories(-1, null, null));
	    register(id, new MSGCuriosAccessory(-1, -1, -1));
	    register(id, new MSGCuriosAccessory(-1, -1, -1));
	    register(id, new MSGUpdateGunCooldown(-1, -1));
	    register(id, new MSGCShootGun(-1, -1));
        register(id, new MSGTrivialEntityActions(-1, -1));
        register(id, new MSGAccessoryServerManager(-1, -1, -1, null));
        register(id, new MSGUpdateRecipes((Iterable<LazyRecipe>) null));
        register(id, new MSGOpenAccessoriesMenu());
        register(id, new MSGSShoot(-1));
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

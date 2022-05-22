package bottomtextdanny.dannys_expansion._base.network;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.*;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.player.MSGSendPlayerAccessories;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGCShootGun;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGCoreAccessoryClientManager;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGUpdateGunCooldown;
import bottomtextdanny.dannys_expansion._base.network.servertoclient.MSGUpdateRecipes;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyRecipe;
import bottomtextdanny.flagged_schema_block.MSGUpdateClientFlaggedSchemaBlock;
import bottomtextdanny.flagged_schema_block.MSGUpdateServerFlaggedSchemaBlock;
import bottomtextdanny.braincell.mod.network.BCPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.Map;

public class DEPacketInitialization {
    private static final String PROTOCOL_VERSION = "0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DannysExpansion.ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void initializeNetworkPackets() {
        MutableInt id = new MutableInt(0);
        register(id, new MSGUpdateLazyCraftInventory());
        register(id, new MSGSetLazyCraftResult(-1, -1));
	    register(id, new MSGCheckJump(-1));
	    register(id, new MSGCoreAccessoryClientManager(-1, -1, -1, null, null));
        register(id, new MSGSendPlayerAccessories(-1, null, null));
	    register(id, new MSGUpdateGunCooldown(-1, -1));
	    register(id, new MSGCShootGun(-1, -1));
        register(id, new MSGAccessoryServerManager(-1, -1, -1, null));
        register(id, new MSGUpdateRecipes((Map<LazyRecipeType, List<LazyRecipe>>) null));
        register(id, new MSGOpenAccessoriesMenu());
        register(id, new MSGSShoot(-1));

        register(id, new MSGUpdateServerFlaggedSchemaBlock(-1L, -1, -1, -1, -1, -1, -1, -1, -1, -1));
        register(id, new MSGUpdateClientFlaggedSchemaBlock(-1L, -1, -1, -1, -1, -1, -1, -1, -1, -1));
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

package bottomtextdanny.dannys_expansion._base.network.clienttoserver;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.LazyCraftMenu;
import bottomtextdanny.braincell.mod.network.BCPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGUpdateLazyCraftInventory implements BCPacket<MSGUpdateLazyCraftInventory> {

    public MSGUpdateLazyCraftInventory() {
        super();
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
    }

    @Override
    public MSGUpdateLazyCraftInventory deserialize(FriendlyByteBuf stream) {
        return new MSGUpdateLazyCraftInventory();
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (ctx.getSender() != null && ctx.getSender().containerMenu instanceof LazyCraftMenu container) {
            container.onInventoryChange();
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

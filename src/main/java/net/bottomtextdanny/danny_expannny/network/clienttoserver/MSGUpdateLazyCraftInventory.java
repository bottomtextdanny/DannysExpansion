package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.LazyCraftContainer;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
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
        if (ctx.getSender() != null && ctx.getSender().containerMenu instanceof LazyCraftContainer container) {
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

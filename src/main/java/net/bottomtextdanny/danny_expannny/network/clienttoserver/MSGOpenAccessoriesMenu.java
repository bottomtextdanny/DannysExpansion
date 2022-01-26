package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.objects.containers.DannyAccessoriesContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGOpenAccessoriesMenu implements BCPacket<MSGOpenAccessoriesMenu> {

    public MSGOpenAccessoriesMenu() {
        super();
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {}

    @Override
    public MSGOpenAccessoriesMenu deserialize(FriendlyByteBuf stream) {
        return new MSGOpenAccessoriesMenu();
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (ctx.getSender() != null) {
            ServerPlayer player = ctx.getSender();
            player.openMenu(new SimpleMenuProvider(DannyAccessoriesContainer::new, new TranslatableComponent("container.danny_accessories")));
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

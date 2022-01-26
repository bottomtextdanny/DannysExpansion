package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.accessory.IQueuedJump;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGCheckJump extends BCEntityPacket<MSGCheckJump, Player> {

    public MSGCheckJump(int entityId) {
        super(entityId);
    }

    @Override
    public MSGCheckJump deserialize(FriendlyByteBuf stream) {
        return new MSGCheckJump(stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        Player player = getEntityAsReceptor(world);

        if (player == ctx.getSender() && !player.isOnGround()) {
            PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);

            for (IQueuedJump jump : accessoryModule.getJumpSet()) {
                if (jump.canPerform()) {
                    jump.performJump();
                    break;
                }
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

package bottomtextdanny.dannys_expansion._base.network.clienttoserver;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.accessory.IQueuedJump;
import bottomtextdanny.braincell.mod.network.BCEntityPacket;
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
            BCAccessoryModule accessoryModule = PlayerHelper.braincellAccessoryModule(player);

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

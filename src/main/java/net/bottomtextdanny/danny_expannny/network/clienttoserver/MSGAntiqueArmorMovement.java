package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.objects.items.armor.AntiqueArmorItem;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGAntiqueArmorMovement extends BCEntityPacket<MSGAntiqueArmorMovement, Player> {
    private final int movementIndex;

    public MSGAntiqueArmorMovement(int entityId, int movementIndex) {
        super(entityId);
        this.movementIndex = movementIndex;

    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        stream.writeInt(this.movementIndex);
    }

    @Override
    public MSGAntiqueArmorMovement deserialize(FriendlyByteBuf stream) {
        return new MSGAntiqueArmorMovement(stream.readInt(), stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        Player entity = getEntityAsReceptor(world);
        if (entity != null && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AntiqueArmorItem) {
            AntiqueArmorItem armorItem = (AntiqueArmorItem) entity.getItemBySlot(EquipmentSlot.CHEST).getItem();

            if (this.movementIndex == DENetwork.AA_FORWARD_DIR) {
                armorItem.forwardDirection.addTimesPressed(1);
            } else if (this.movementIndex == DENetwork.AA_BACK_DIR) {
                armorItem.backDirection.addTimesPressed(1);
            } else if (this.movementIndex == DENetwork.AA_RIGHT_DIR) {
                armorItem.rightDirection.addTimesPressed(1);
            } else if (this.movementIndex == DENetwork.AA_LEFT_DIR) {
                armorItem.leftDirection.addTimesPressed(1);
            }
        }
    }

    public void sendToServer() {
        if (side() == LogicalSide.CLIENT)
            throw new IllegalStateException("Danny's Expansion: Tried to send a client-configured packet to the server.");

        mainChannel().sendToServer(this);
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

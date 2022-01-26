package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.objects.items.gun.GunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGSShoot implements BCPacket<MSGSShoot> {
    private final byte hand;

    public MSGSShoot(int hand) {
        super();
        this.hand = (byte) hand;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeByte(this.hand);
    }

    @Override
    public MSGSShoot deserialize(FriendlyByteBuf stream) {
        return new MSGSShoot(stream.readByte());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (ctx.getSender() != null) {
            InteractionHand hand = InteractionHand.values()[this.hand];
            ItemStack gunItemStack = ctx.getSender().getItemInHand(hand);
            if (gunItemStack.getItem() instanceof GunItem<?> gun) {
                gun.baseShoot(ctx.getSender(), gunItemStack, hand);
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

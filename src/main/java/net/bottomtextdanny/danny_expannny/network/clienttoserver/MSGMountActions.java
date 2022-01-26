package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MountEntity;
import net.bottomtextdanny.dannys_expansion.core.Packets.DENetwork;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGMountActions extends BCEntityPacket<MSGMountActions, MountEntity> {
    private final int intEnum;

    public MSGMountActions(int entityId, int intEnum) {
        super(entityId);
        this.intEnum = intEnum;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        stream.writeInt(this.intEnum);
    }

    @Override
    public MSGMountActions deserialize(FriendlyByteBuf stream) {
        return new MSGMountActions(stream.readInt(), stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        MountEntity entity = getEntityAsReceptor(world);
        if(entity != null && entity.getControllingPassenger() == ctx.getSender()) {
            if (this.intEnum == DENetwork.MA_SET_PROGRESS_INCREASING) {
                entity.setProgressIsIncreasing(true);
            } else if (this.intEnum == DENetwork.MA_EXECUTE_ACTION) {
                entity.doAct();
            } else if (this.intEnum == DENetwork.MA_EXECUTE_ABILITY) {
                entity.doAbility();
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

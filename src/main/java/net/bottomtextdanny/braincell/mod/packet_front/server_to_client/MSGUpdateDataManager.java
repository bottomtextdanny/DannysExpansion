package net.bottomtextdanny.braincell.mod.packet_front.server_to_client;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManager;
import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.packet_front.BCPacketInitialization;
import net.bottomtextdanny.braincell.mod.packet_helper.BCEntityPacket;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.simple.SimpleChannel;

public final class MSGUpdateDataManager extends BCEntityPacket<MSGUpdateDataManager, Entity> {
    private final BCDataManager manager;

    public MSGUpdateDataManager(int entityId, BCDataManager manager) {
        super(entityId);
        this.manager = manager;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        super.serialize(stream);
        this.manager.writePacket(stream);
    }

    @Override
    public MSGUpdateDataManager deserialize(FriendlyByteBuf stream) {
        MSGUpdateDataManager packet = new MSGUpdateDataManager(stream.readInt(), null);
        Entity entity = packet.getEntityAsReceptor(ClientInstance.player().level);
        if (entity instanceof BCDataManagerProvider dataHolder) {
            dataHolder.bcDataManager().readPacket(stream);
            dataHolder.afterClientDataUpdate();

        }
        return packet;
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    @Override
    public SimpleChannel mainChannel() {
        return BCPacketInitialization.CHANNEL;
    }
}

package net.bottomtextdanny.braincell.mod.packet_helper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public abstract class BCEntityPacket<T extends BCEntityPacket<T, E>, E extends Entity> implements WorldEntityPacket<T, E> {
    private final int entityId;

    public BCEntityPacket(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeInt(this.entityId);
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }
}

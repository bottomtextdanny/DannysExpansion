package net.bottomtextdanny.braincell.mod.serialization.serializers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface SimpleSerializer<T> extends SerializerMark<T> {
    Logger LOGGER = LogManager.getLogger();

    void writeNBT(CompoundTag nbt, T obj, String storage);

    T readNBT(CompoundTag nbt, String storage);

    void writePacketStream(FriendlyByteBuf stream, T obj);

    T readPacketStream(FriendlyByteBuf stream);
}

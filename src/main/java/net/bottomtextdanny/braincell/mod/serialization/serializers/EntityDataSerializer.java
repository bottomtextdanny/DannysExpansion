package net.bottomtextdanny.braincell.mod.serialization.serializers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface EntityDataSerializer<T> extends SerializerMark<T> {
    Logger LOGGER = LogManager.getLogger();

    void writeNBT(CompoundTag nbt, T obj, ServerLevel level, String storage);

    T readNBT(CompoundTag nbt, T baseObj, ServerLevel level, String storage);

    void writePacketStream(FriendlyByteBuf stream, Level level, T obj);

    T readPacketStream(FriendlyByteBuf stream, T baseObj, Level level);
}

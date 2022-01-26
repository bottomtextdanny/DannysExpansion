package net.bottomtextdanny.braincell.mod.serialization.serializers.util;

import net.bottomtextdanny.braincell.mod.entity.serialization.EntityDataReference;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class H_DataParser {

    public static <T> void writeDataToPacket(PacketWriteData<T> data) {
        if (data.reference.serializer() instanceof SimpleSerializer<T> simple) {
            simple.writePacketStream(data.stream, data.object);
        } else {
            throw writeUnsupportedSerializerException(data.reference().serializer().getClass(), data.reference.serializer().key());
        }
    }

    public static <T> T readDataFromPacket(PacketReadData<T> data) {
        if (data.reference.serializer() instanceof SimpleSerializer<T> simple) {
            return simple.readPacketStream(data.stream);
        } else {
            throw readUnsupportedSerializerException(data.reference().serializer().getClass(), data.reference.serializer().key());
        }
    }

    public static <T> void writeDataToNBT(NBTWriteData<T> data) {
        if (data.reference.serializer() instanceof SimpleSerializer<T> simple) {
            simple.writeNBT(data.nbt, data.object, data.reference.storageKey());
        } else {
            throw writeUnsupportedSerializerException(data.reference().serializer().getClass(), data.reference.serializer().key());
        }
    }

    public static <T> T readDataFromNBT(NBTReadData<T> data) {
        if (data.reference.serializer() instanceof SimpleSerializer<T> simple) {
            return simple.readNBT(data.nbt, data.reference.storageKey());
        } else {
            throw readUnsupportedSerializerException(data.reference().serializer().getClass(), data.reference.serializer().key());
        }
    }

    public static UnsupportedOperationException readUnsupportedSerializerException(Class<? extends SerializerMark> serializerClass, ResourceLocation serializerKey) {
        String message = new StringBuilder("Parser can only read simple image serializers, tried to read: ")
                .append(serializerClass.getSimpleName())
                .append(", for serializer: ")
                .append(serializerKey.toString())
                .append('.')
                .toString();
        return new UnsupportedOperationException(message);
    }

    public static UnsupportedOperationException writeUnsupportedSerializerException(Class<? extends SerializerMark> serializerClass, ResourceLocation serializerKey) {
        String message = new StringBuilder("Parser can only write from simple image serializers, tried to write from: ")
                .append(serializerClass.getSimpleName())
                .append(", for serializer: ")
                .append(serializerKey.toString())
                .append('.')
                .toString();
        return new UnsupportedOperationException(message);
    }

    public record PacketWriteData<T>(FriendlyByteBuf stream, EntityDataReference<T> reference, T object, Level level) {}

    public record PacketReadData<T>(FriendlyByteBuf stream, EntityDataReference<T> reference, Level level) {}

    public record NBTWriteData<T>(CompoundTag nbt, EntityDataReference<T> reference, T object, ServerLevel level) {}

    public record NBTReadData<T>(CompoundTag nbt, EntityDataReference<T> reference, ServerLevel level) {}
}

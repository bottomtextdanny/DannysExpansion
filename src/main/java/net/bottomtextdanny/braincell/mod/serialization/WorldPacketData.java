package net.bottomtextdanny.braincell.mod.serialization;

import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.WorldDataSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.util.H_WorldDataParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public final class WorldPacketData<T> {
    public final SerializerMark<T> serializer;
    private T objInstance;

    public WorldPacketData(SerializerMark<T> ser, T obj) {
        this.serializer = ser;
        this.objInstance = obj;
    }

    public T get() {
        return this.objInstance;
    }

    public void set(T t) {
        this.objInstance = t;
    }

    public static <E> WorldPacketData<E> of(SerializerMark<E> ser, E obj) {
        return new WorldPacketData<>(ser, obj);
    }

    public void writeToStream(FriendlyByteBuf stream, Level level) {
        if (this.serializer instanceof SimpleSerializer<T> simple) {
            simple.writePacketStream(stream, this.objInstance);
        } else if (this.serializer instanceof WorldDataSerializer<T> entityOriented) {
            entityOriented.writePacketStream(stream, level, this.objInstance);
        } else {
            throw H_WorldDataParser.writeUnsupportedSerializerException(this.serializer.getClass(), this.serializer.key());
        }
    }

    public SerializerMark<T> getSerializer() {
        return this.serializer;
    }
}

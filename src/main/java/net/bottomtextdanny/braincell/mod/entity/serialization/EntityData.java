package net.bottomtextdanny.braincell.mod.entity.serialization;

import net.bottomtextdanny.braincell.mod.serialization.serializers.EntityDataSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SerializerMark;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.WorldDataSerializer;
import net.bottomtextdanny.braincell.mod.serialization.serializers.util.H_EntityDataParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class EntityData<T> { // 8
    private final EntityDataReference<T> reference; // 4
    private T objInstance; // 4

    public EntityData(EntityDataReference<T> reference) {
        this.reference = reference;
        this.objInstance = reference.defaultProvider().get();
    }

    public static <E> EntityData<E> of(EntityDataReference<E> reference) {
        return new EntityData<>(reference);
    }

    public T get() {
        return this.objInstance;
    }

    public void set(T t) {
        this.objInstance = t;
    }

    public SerializerMark<T> getSerializer() {
        return this.reference.serializer();
    }

    public void writeToPacketStream(FriendlyByteBuf stream, Level level) {
        if (getSerializer() instanceof SimpleSerializer<T> simple) {
            simple.writePacketStream(stream, this.objInstance);
        } else if (getSerializer() instanceof WorldDataSerializer<T> worldData) {
            worldData.writePacketStream(stream, level, this.objInstance);
        } else if (getSerializer() instanceof EntityDataSerializer<T> entityData) {
            entityData.writePacketStream(stream, level, this.objInstance);
        } else {
            throw H_EntityDataParser.writeUnsupportedSerializerException(getSerializer().getClass(), getSerializer().key());
        }
    }

    public T readFromPacketStream(FriendlyByteBuf stream, Level level) {
        if (getSerializer() instanceof SimpleSerializer<T> simple) {
            set(simple.readPacketStream(stream));
            return this.objInstance;
        } else if (getSerializer() instanceof WorldDataSerializer<T> worldData) {
            set(worldData.readPacketStream(stream, level));
            return this.objInstance;
        } else if (getSerializer() instanceof EntityDataSerializer<T> entityData) {
            set(entityData.readPacketStream(stream, this.objInstance, level));
            return this.objInstance;
        }  else {
            throw H_EntityDataParser.writeUnsupportedSerializerException(getSerializer().getClass(), getSerializer().key());
        }
    }

    public void writeToNBT(CompoundTag nbt, ServerLevel level) {
        if (getSerializer() instanceof SimpleSerializer<T> simple) {
            simple.writeNBT(nbt, this.objInstance, this.reference.storageKey());
        } else if (getSerializer() instanceof WorldDataSerializer<T> worldData) {
            worldData.writeNBT(nbt, this.objInstance, level, this.reference.storageKey());
        } else if (getSerializer() instanceof EntityDataSerializer<T> entityData) {
            entityData.writeNBT(nbt, this.objInstance, level, this.reference.storageKey());

        } else {
            throw H_EntityDataParser.writeUnsupportedSerializerException(getSerializer().getClass(), getSerializer().key());
        }
    }

    public T readFromNBT(CompoundTag nbt, ServerLevel level) {
        if (getSerializer() instanceof SimpleSerializer<T> simple) {
            set(simple.readNBT(nbt, this.reference.storageKey()));
            return this.objInstance;
        } else if (getSerializer() instanceof WorldDataSerializer<T> worldData) {
            set(worldData.readNBT(nbt, level, this.reference.storageKey()));
            return this.objInstance;
        } else if (getSerializer() instanceof EntityDataSerializer<T> entityData) {
            set(entityData.readNBT(nbt, this.objInstance, level, this.reference.storageKey()));
            return this.objInstance;
        } else {
            throw H_EntityDataParser.writeUnsupportedSerializerException(getSerializer().getClass(), getSerializer().key());
        }
    }
}


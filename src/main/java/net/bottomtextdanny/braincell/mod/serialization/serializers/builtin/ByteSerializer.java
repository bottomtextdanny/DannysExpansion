package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class ByteSerializer implements SimpleSerializer<Byte> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "byte");

    @Override
    public void writeNBT(CompoundTag nbt, Byte obj, String storage) {
        nbt.putByte(storage, obj);
    }

    @Override
    public Byte readNBT(CompoundTag nbt, String storage) {
        return nbt.getByte(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Byte obj) {
        stream.writeByte(obj);
    }

    @Override
    public Byte readPacketStream(FriendlyByteBuf stream) {
        return stream.readByte();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class DoubleSerializer implements SimpleSerializer<Double> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "double");

    @Override
    public void writeNBT(CompoundTag nbt, Double obj, String storage) {
        nbt.putDouble(storage, obj);
    }

    @Override
    public Double readNBT(CompoundTag nbt, String storage) {
        return nbt.getDouble(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Double obj) {
        stream.writeDouble(obj);
    }

    @Override
    public Double readPacketStream(FriendlyByteBuf stream) {
        return stream.readDouble();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

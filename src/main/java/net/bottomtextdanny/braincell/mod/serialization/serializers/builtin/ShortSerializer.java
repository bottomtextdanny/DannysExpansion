package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class ShortSerializer implements SimpleSerializer<Short> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "short");

    @Override
    public void writeNBT(CompoundTag nbt, Short obj, String storage) {
        nbt.putShort(storage, obj);
    }

    @Override
    public Short readNBT(CompoundTag nbt, String storage) {
        return nbt.getShort(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Short obj) {
        stream.writeShort(obj);
    }

    @Override
    public Short readPacketStream(FriendlyByteBuf stream) {
        return stream.readShort();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

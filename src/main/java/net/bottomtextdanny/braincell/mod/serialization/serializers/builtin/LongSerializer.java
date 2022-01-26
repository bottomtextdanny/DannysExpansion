package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class LongSerializer implements SimpleSerializer<Long> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "long");

    @Override
    public void writeNBT(CompoundTag nbt, Long obj, String storage) {
        nbt.putLong(storage, obj);
    }

    @Override
    public Long readNBT(CompoundTag nbt, String storage) {
        return nbt.getLong(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Long obj) {
        stream.writeLong(obj);
    }

    @Override
    public Long readPacketStream(FriendlyByteBuf stream) {
        return stream.readLong();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

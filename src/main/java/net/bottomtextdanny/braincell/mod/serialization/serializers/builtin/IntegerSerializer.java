package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class IntegerSerializer implements SimpleSerializer<Integer> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "integer");

    @Override
    public void writeNBT(CompoundTag nbt, Integer obj, String storage) {
        nbt.putInt(storage, obj);
    }

    @Override
    public Integer readNBT(CompoundTag nbt, String storage) {
        return nbt.getInt(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Integer obj) {
        stream.writeInt(obj);
    }

    @Override
    public Integer readPacketStream(FriendlyByteBuf stream) {
        return stream.readInt();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

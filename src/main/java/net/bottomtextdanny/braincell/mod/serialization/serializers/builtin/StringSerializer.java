package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class StringSerializer implements SimpleSerializer<String> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "string");

    @Override
    public void writeNBT(CompoundTag nbt, String obj, String storage) {
        nbt.putString(storage, obj);
    }

    @Override
    public String readNBT(CompoundTag nbt, String storage) {
        return nbt.getString(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, String obj) {
        stream.writeUtf(obj);
    }

    @Override
    public String readPacketStream(FriendlyByteBuf stream) {
        return stream.readUtf();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

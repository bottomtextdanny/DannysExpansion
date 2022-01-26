package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class FloatSerializer implements SimpleSerializer<Float> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "float");

    @Override
    public void writeNBT(CompoundTag nbt, Float obj, String storage) {
        nbt.putFloat(storage, obj);
    }

    @Override
    public Float readNBT(CompoundTag nbt, String storage) {
        return nbt.getFloat(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Float obj) {
        stream.writeFloat(obj);
    }

    @Override
    public Float readPacketStream(FriendlyByteBuf stream) {
        return stream.readFloat();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

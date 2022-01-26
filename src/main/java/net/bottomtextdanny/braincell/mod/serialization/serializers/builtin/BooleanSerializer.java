package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class BooleanSerializer implements SimpleSerializer<Boolean> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "boolean");

    @Override
    public void writeNBT(CompoundTag nbt, Boolean obj, String storage) {
        nbt.putBoolean(storage, obj);
    }

    @Override
    public Boolean readNBT(CompoundTag nbt, String storage) {
        return nbt.getBoolean(storage);
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Boolean obj) {
        stream.writeBoolean(obj);
    }

    @Override
    public Boolean readPacketStream(FriendlyByteBuf stream) {
        return stream.readBoolean();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class DirectionSerializer implements SimpleSerializer<Direction> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "direction");

    @Override
    public void writeNBT(CompoundTag nbt, Direction obj, String storage) {
        nbt.putByte(storage, (byte)obj.ordinal());
    }

    @Override
    public Direction readNBT(CompoundTag nbt, String storage) {
        return Direction.values()[(int) nbt.getByte(storage)];
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Direction obj) {
        stream.writeByte(obj.ordinal());
    }

    @Override
    public Direction readPacketStream(FriendlyByteBuf stream) {
        return Direction.values()[(int) stream.readByte()];
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

package net.bottomtextdanny.braincell.mod.serialization.serializers.braincell;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class IntSchedulerSerializer implements SimpleSerializer<IntScheduler.Simple> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "simple_int_scheduler");

    @Override
    public void writeNBT(CompoundTag nbt, IntScheduler.Simple obj, String storage) {
        nbt.putInt(String.join(storage, "_bound"), obj.bound());
        nbt.putInt(storage, obj.current());
    }

    @Override
    public IntScheduler.Simple readNBT(CompoundTag nbt, String storage) {
        return new IntScheduler.Simple(
                nbt.getInt(String.join(storage, "_bound")),
                nbt.getInt(storage));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, IntScheduler.Simple obj) {
        stream.writeInt(obj.bound());
        stream.writeInt(obj.current());
    }

    @Override
    public IntScheduler.Simple readPacketStream(FriendlyByteBuf stream) {
        return new IntScheduler.Simple(
                stream.readInt(),
                stream.readInt());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

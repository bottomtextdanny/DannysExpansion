package net.bottomtextdanny.braincell.mod.serialization.serializers.braincell.entity;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.braincell.mod.serialization.serializers.EntityDataSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class VariableIntSchedulerSerializer implements EntityDataSerializer<IntScheduler.Variable> {
    public static final ResourceLocation REF = new ResourceLocation(Braincell.ID, "variable_int_scheduler");

    @Override
    public void writeNBT(CompoundTag nbt, IntScheduler.Variable obj, ServerLevel level, String storage) {
        nbt.putInt(String.join(storage, "_bound"), obj.bound());
        nbt.putInt(storage, obj.current());
    }

    @Override
    public IntScheduler.Variable readNBT(CompoundTag nbt, IntScheduler.Variable baseObj, ServerLevel level, String storage) {
        return new IntScheduler.Variable(
                baseObj.getNextBoundSupplier(),
                nbt.getInt(String.join(storage, "_bound")),
                nbt.getInt(storage));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Level level, IntScheduler.Variable obj) {
        stream.writeInt(obj.bound());
        stream.writeInt(obj.current());
    }

    @Override
    public IntScheduler.Variable readPacketStream(FriendlyByteBuf stream, IntScheduler.Variable baseObj, Level level) {
        return new IntScheduler.Variable(
                baseObj.getNextBoundSupplier(),
                stream.readInt(),
                stream.readInt());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

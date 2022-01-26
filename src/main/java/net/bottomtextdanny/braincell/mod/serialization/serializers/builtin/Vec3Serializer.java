package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public final class Vec3Serializer implements SimpleSerializer<Vec3> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "vec3");

    @Override
    public void writeNBT(CompoundTag nbt, Vec3 obj, String storage) {
        ListTag list = new ListTag();
        list.add(DoubleTag.valueOf(obj.x));
        list.add(DoubleTag.valueOf(obj.y));
        list.add(DoubleTag.valueOf(obj.z));
        nbt.put(storage, list);
    }

    @Override
    public Vec3 readNBT(CompoundTag nbt, String storage) {
        ListTag list = nbt.getList(storage, 6);
        return new Vec3(
                list.getDouble(0),
                list.getDouble(1),
                list.getDouble(2));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Vec3 obj) {
        stream.writeDouble(obj.x);
        stream.writeDouble(obj.y);
        stream.writeDouble(obj.z);
    }

    @Override
    public Vec3 readPacketStream(FriendlyByteBuf stream) {
        return new Vec3(
                stream.readDouble(),
                stream.readDouble(),
                stream.readDouble());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

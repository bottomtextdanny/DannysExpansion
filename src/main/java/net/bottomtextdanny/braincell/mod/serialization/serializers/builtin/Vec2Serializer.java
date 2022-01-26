package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public final class Vec2Serializer implements SimpleSerializer<Vec2> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "vec2");

    @Override
    public void writeNBT(CompoundTag nbt, Vec2 obj, String storage) {
        ListTag list = new ListTag();
        list.add(FloatTag.valueOf(obj.x));
        list.add(FloatTag.valueOf(obj.y));
        nbt.put(storage, list);
    }

    @Override
    public Vec2 readNBT(CompoundTag nbt, String storage) {
        ListTag list = nbt.getList(storage, 5);
        return new Vec2(list.getFloat(0), list.getFloat(1));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Vec2 obj) {
        stream.writeFloat(obj.x);
        stream.writeFloat(obj.y);
    }

    @Override
    public Vec2 readPacketStream(FriendlyByteBuf stream) {
        return new Vec2(stream.readFloat(), stream.readFloat());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

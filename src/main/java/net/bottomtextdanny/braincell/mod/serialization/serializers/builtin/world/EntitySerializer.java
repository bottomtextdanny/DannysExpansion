package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.world;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.WorldDataSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EntitySerializer implements WorldDataSerializer<Entity> {
    public static final ResourceLocation REF = new ResourceLocation(Braincell.ID, "entity");

    @Override
    public void writeNBT(CompoundTag nbt, Entity obj, ServerLevel level, String storage) {
        nbt.put(storage, obj.serializeNBT());
    }

    @Nullable
    @Override
    public Entity readNBT(CompoundTag nbt, ServerLevel level, String storage) {
        return EntityType.create(nbt.getCompound(storage), level).get();
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Level level, Entity obj) {
        stream.writeNbt(obj.serializeNBT());
    }

    @Nullable
    @Override
    public Entity readPacketStream(FriendlyByteBuf stream, Level level) {
        CompoundTag tag = stream.readAnySizeNbt();
        if (tag == null || tag.isEmpty()) return null;
        else return EntityType.create(tag, level).get();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

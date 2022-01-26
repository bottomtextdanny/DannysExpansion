package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.world;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.WorldDataSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class EntityReferenceSerializer implements WorldDataSerializer<Entity> {
    public static final ResourceLocation REF = new ResourceLocation(Braincell.ID, "entity_reference");

    @Override
    public void writeNBT(CompoundTag nbt, Entity obj, ServerLevel level, String storage) {
        nbt.putUUID(storage, obj != null ? obj.getUUID() : UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }

    @Override
    public Entity readNBT(CompoundTag nbt, ServerLevel level, String storage) {
        return level.getEntity(nbt.getUUID(storage));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, Level level, Entity obj) {
        stream.writeInt(obj != null ? obj.getId() : -1);
    }

    @Override
    public Entity readPacketStream(FriendlyByteBuf stream, Level level) {
        int id = stream.readInt();
        if (id == -1) return null;
        else return level.getEntity(stream.readInt());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

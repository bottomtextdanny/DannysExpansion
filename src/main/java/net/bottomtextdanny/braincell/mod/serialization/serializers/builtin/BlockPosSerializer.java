package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class BlockPosSerializer implements SimpleSerializer<BlockPos> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "block_pos");

    @Override
    public void writeNBT(CompoundTag nbt, BlockPos obj, String storage) {
        nbt.putLong(storage, obj.asLong());
    }

    @Override
    public BlockPos readNBT(CompoundTag nbt, String storage) {
        return BlockPos.of(nbt.getLong(storage));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, BlockPos obj) {
        stream.writeLong(obj.asLong());
    }

    @Override
    public BlockPos readPacketStream(FriendlyByteBuf stream) {
        return BlockPos.of(stream.readLong());
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

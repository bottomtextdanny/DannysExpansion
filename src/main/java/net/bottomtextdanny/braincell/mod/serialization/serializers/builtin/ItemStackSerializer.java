package net.bottomtextdanny.braincell.mod.serialization.serializers.builtin;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.serialization.serializers.SimpleSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class ItemStackSerializer implements SimpleSerializer<ItemStack> {
    public static final ResourceLocation REF =
            new ResourceLocation(Braincell.ID, "item_stack");

    @Override
    public void writeNBT(CompoundTag nbt, ItemStack obj, String storage) {
        nbt.put(storage, obj.serializeNBT());
    }

    @Override
    public ItemStack readNBT(CompoundTag nbt, String storage) {
        return ItemStack.of(nbt.getCompound(storage));
    }

    @Override
    public void writePacketStream(FriendlyByteBuf stream, ItemStack obj) {
        stream.writeItem(obj);
    }

    @Override
    public ItemStack readPacketStream(FriendlyByteBuf stream) {
        return stream.readItem();
    }

    @Override
    public ResourceLocation key() {
        return REF;
    }
}

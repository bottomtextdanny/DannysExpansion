package net.bottomtextdanny.danny_expannny.network.servertoclient;

import com.google.common.collect.Lists;
import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.bottomtextdanny.dannys_expansion.core.events.ResourceReloadHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MSGUpdateRecipes implements BCPacket<MSGUpdateRecipes> {
    private final int recipesSize;
    private final List<CompoundTag> recipeNBT;

    @OnlyIn(Dist.CLIENT)
    public MSGUpdateRecipes(FriendlyByteBuf packetBuffer) {
        this.recipesSize = packetBuffer.readInt();
        List<CompoundTag> newList = Arrays.asList(new CompoundTag[this.recipesSize]);
        for (int i = 0; i < this.recipesSize; i++) {
            CompoundTag comp = packetBuffer.readNbt();
            if (comp != null) {
                newList.set(i, comp);
            }
        }
        this.recipeNBT = newList;
    }

    public MSGUpdateRecipes(Iterable<LazyRecipe> recipes) {
        List<CompoundTag> nbtList = Lists.newArrayList();

        if (recipes != null) recipes.forEach(rec -> nbtList.add(rec.serialize()));

        this.recipesSize = nbtList.size();
        this.recipeNBT = nbtList;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeInt(this.recipesSize);
        this.recipeNBT.forEach(tag -> stream.writeNbt(tag));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public MSGUpdateRecipes deserialize(FriendlyByteBuf stream) {
        return new MSGUpdateRecipes(stream);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        ResourceReloadHandler.recipeManager.resetRecipeCache();
        List<CompoundTag> compoundTags = this.recipeNBT;
        for (int i = 0, compoundTagsSize = compoundTags.size(); i < compoundTagsSize; i++) {
            CompoundTag nbt = compoundTags.get(i);
            ResourceReloadHandler.recipeManager.addRecipe(LazyRecipe.deserialize(i, nbt));
        }
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.CLIENT;
    }

    @Override
    public SimpleChannel mainChannel() {
        return DEPacketInitialization.CHANNEL;
    }
}

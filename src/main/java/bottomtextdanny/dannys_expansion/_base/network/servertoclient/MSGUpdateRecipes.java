package bottomtextdanny.dannys_expansion._base.network.servertoclient;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeManager;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyRecipe;
import com.google.common.collect.Lists;
import bottomtextdanny.braincell.mod.network.BCPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MSGUpdateRecipes implements BCPacket<MSGUpdateRecipes> {
    private static final String TYPE_TAG = "type";
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

    public MSGUpdateRecipes(Map<LazyRecipeType, List<LazyRecipe>> allRecipes) {
        List<CompoundTag> nbtList = Lists.newArrayList();

        if (allRecipes != null) allRecipes.forEach((type, recipes) -> {
            int typeIndex = DannysExpansion.common().getLazyRecipeManager().getTypeId(type);
            recipes.forEach((rec) -> {
                CompoundTag tag = rec.serialize();
                tag.putInt(TYPE_TAG, typeIndex);
                nbtList.add(tag);
            });
        });

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
        LazyRecipeManager manager = DannysExpansion.common().getLazyRecipeManager();
        manager.resetRecipeCache();
        int[] indexer = new int[manager.recipeTypeSize()];
        for (CompoundTag nbt : this.recipeNBT) {
            //should always be an existing value.
            LazyRecipeType type = manager.getTypeById(nbt.getInt(TYPE_TAG));
            int typeIndex = manager.getTypeId(type);
            manager.addRecipe(type, LazyRecipe.deserialize(indexer[typeIndex], nbt));
            indexer[typeIndex]++;
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

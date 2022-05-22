package bottomtextdanny.dannys_expansion._base.network.clienttoserver;

import bottomtextdanny.dannys_expansion._base.network.DEPacketInitialization;
import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeManager;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.LazyCraftMenu;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.LazyRecipe;
import bottomtextdanny.braincell.mod.network.BCPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGSetLazyCraftResult implements BCPacket<MSGSetLazyCraftResult> {
    private final int typeIndex;
    private final int recipeIndex;

    public MSGSetLazyCraftResult(int typeIndex, int recipeIndex) {
        super();
        this.typeIndex = typeIndex;
        this.recipeIndex = recipeIndex;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeInt(this.typeIndex);
        stream.writeInt(this.recipeIndex);
    }

    @Override
    public MSGSetLazyCraftResult deserialize(FriendlyByteBuf stream) {
        return new MSGSetLazyCraftResult(stream.readInt(), stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (ctx.getSender() != null && ctx.getSender().containerMenu instanceof LazyCraftMenu container) {
            LazyRecipeManager manager = DannysExpansion.common().getLazyRecipeManager();
            LazyRecipeType type = manager.getTypeById(this.typeIndex);
            container.setResultRecipe(this.recipeIndex < 0 || this.recipeIndex >= manager.getRecipes(type).size() ? LazyRecipe.EMPTY : manager.getRecipe(type, this.recipeIndex));
            container.makeResultItemstack();
        }
    }

    @Override
    public LogicalSide side() {
        return LogicalSide.SERVER;
    }

    @Override
    public SimpleChannel mainChannel() {
        return DEPacketInitialization.CHANNEL;
    }
}

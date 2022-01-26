package net.bottomtextdanny.danny_expannny.network.clienttoserver;

import net.bottomtextdanny.braincell.mod.packet_helper.BCPacket;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.LazyCraftContainer;
import net.bottomtextdanny.dannys_expansion.core.events.ResourceReloadHandler;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class MSGSetLazyCraftResult implements BCPacket<MSGSetLazyCraftResult> {
    private final int recipeIndex;

    public MSGSetLazyCraftResult(int recipeIndex) {
        super();
        this.recipeIndex = recipeIndex;
    }

    @Override
    public void serialize(FriendlyByteBuf stream) {
        stream.writeInt(this.recipeIndex);
    }

    @Override
    public MSGSetLazyCraftResult deserialize(FriendlyByteBuf stream) {
        return new MSGSetLazyCraftResult(stream.readInt());
    }

    @Override
    public void postDeserialization(NetworkEvent.Context ctx, Level world) {
        if (ctx.getSender() != null && ctx.getSender().containerMenu instanceof LazyCraftContainer container) {
            container.setResultRecipe(this.recipeIndex < 0 || this.recipeIndex > ResourceReloadHandler.recipeManager.size() ? LazyRecipe.EMPTY : ResourceReloadHandler.recipeManager.getRecipe(this.recipeIndex));

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

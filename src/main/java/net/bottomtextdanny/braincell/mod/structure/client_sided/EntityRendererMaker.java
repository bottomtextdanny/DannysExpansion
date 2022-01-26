package net.bottomtextdanny.braincell.mod.structure.client_sided;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

public final class EntityRendererMaker {

    @OnlyIn(Dist.CLIENT)
    public static <T extends Entity> EntityRendererProvider<T> makeOf(Function<?,?> provider) {
        return context -> ((Function<EntityRendererProvider.Context, EntityRenderer<T>>)provider).apply(context);
    }
}

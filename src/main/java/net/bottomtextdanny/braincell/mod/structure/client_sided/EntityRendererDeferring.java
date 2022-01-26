package net.bottomtextdanny.braincell.mod.structure.client_sided;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public record EntityRendererDeferring<T extends Entity>(
        Supplier<? extends EntityType<T>> wrappedEntityType,
        Supplier<EntityRendererProvider<T>> rendererFactory) {

    public void register() {
        EntityRenderers.register(this.wrappedEntityType.get(), this.rendererFactory.get());
    }
}

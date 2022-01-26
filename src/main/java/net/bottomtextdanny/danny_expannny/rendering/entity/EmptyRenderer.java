package net.bottomtextdanny.danny_expannny.rendering.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer<T extends Entity> extends EntityRenderer<T> {

    public EmptyRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public EmptyRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}

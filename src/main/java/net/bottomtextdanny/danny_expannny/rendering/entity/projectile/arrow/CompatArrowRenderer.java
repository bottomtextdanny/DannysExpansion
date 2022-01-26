package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

import javax.annotation.Nullable;

public class CompatArrowRenderer extends DannyArrowRenderer<AbstractArrow> {
    public ResourceLocation TEXTURE;
    public ResourceLocation FULLBRIGHT_TEXTURE;

    public CompatArrowRenderer(EntityRendererProvider.Context renderManager, String texture,@Nullable String fullbright) {
        super(renderManager);
        this.TEXTURE = new ResourceLocation(texture);

        if (fullbright != null) {
            this.FULLBRIGHT_TEXTURE = new ResourceLocation(fullbright);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {
        return this.TEXTURE;
    }

    @Override
    public ResourceLocation getFullbrightTexture(AbstractArrow entity) {
        return this.FULLBRIGHT_TEXTURE;
    }
}

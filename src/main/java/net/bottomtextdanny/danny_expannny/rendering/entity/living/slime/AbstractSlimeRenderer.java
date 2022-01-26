package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.AbstractSlimeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSlimeRenderer <E extends AbstractSlimeEntity> extends MobRenderer<E, DannySlimeModel<E>> {
    public static final ResourceLocation TEXTURES =
            new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/slime.png");

    public AbstractSlimeRenderer(EntityRendererProvider.Context renderManagerIn, DannySlimeModel<E> entityModelIn, float shadowSizeIn) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);
    }

    @Override
    protected float getFlipDegrees(E entityLivingBaseIn) {
        return 0;
    }

    public ResourceLocation getTextureLocation(AbstractSlimeEntity entity) {
        return TEXTURES;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.IceSlimeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class IceSlimeRenderer extends AbstractSlimeRenderer<IceSlimeEntity> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/ice_slime.png");

    public IceSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public IceSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(12, 9), 0.4F);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(IceSlimeEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(TEXTURES);
    }

    public ResourceLocation getTextureLocation(IceSlimeEntity entity) {
        return TEXTURES;
    }
}
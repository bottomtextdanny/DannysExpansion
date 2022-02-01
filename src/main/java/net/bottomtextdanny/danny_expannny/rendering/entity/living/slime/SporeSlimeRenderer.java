package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.SporeSlime;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class SporeSlimeRenderer extends AbstractSlimeRenderer<SporeSlime> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/spore_slime.png");

    public SporeSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SporeSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(15, 13), 0.4F);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(SporeSlime entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(TEXTURES);
    }

    public ResourceLocation getTextureLocation(SporeSlime entity) {
        return TEXTURES;
    }
}

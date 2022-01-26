package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.MagmaSlimeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MagmaSlimeRenderer extends AbstractSlimeRenderer<MagmaSlimeEntity> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/magma_slime.png");

    public MagmaSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public MagmaSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(16, 14), 0.4F);
    }

    @Override
    protected int getBlockLightLevel(MagmaSlimeEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    public ResourceLocation getTextureLocation(MagmaSlimeEntity entity) {
        return TEXTURES;
    }


}

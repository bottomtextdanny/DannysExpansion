package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow.IceArrowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IceArrowRenderer extends DannyArrowRenderer<IceArrowEntity> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/arrow/ice_arrow.png");

    public IceArrowRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public IceArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(IceArrowEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(IceArrowEntity entity) {
        return TEXTURES;
    }
}

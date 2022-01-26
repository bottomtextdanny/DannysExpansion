package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.TumefendModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.TumefendEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class TumefendRenderer extends MobRenderer<TumefendEntity, TumefendModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/tumefend.png");

    public TumefendRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public TumefendRenderer(EntityRendererProvider.Context manager) {
        super(manager, new TumefendModel(), 0.4F);
    }

    @Override
    public void render(TumefendEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public boolean shouldRender(TumefendEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
//        livingEntityIn.setLightPosition(entityModel.getLightPos().getAbsolutePosition(Vec3.ZERO, ClientInstance.partialTicks(), livingEntityIn));
//        EntityUtil.tryPlaceLight(livingEntityIn.getLightPosition(), new PointLight(new Vec3(1, 0, 0.8), 7, 0.7F + 0.7F * (float)Math.sin(0.05F * (livingEntityIn.ticksExisted + ClientInstance.partialTicks())), 1.6F, 2.8F));

        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }

    public ResourceLocation getTextureLocation(TumefendEntity entity) {
        return TEXTURES;
    }

    protected int getBlockLightLevel(TumefendEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}

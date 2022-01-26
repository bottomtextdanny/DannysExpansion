package net.bottomtextdanny.danny_expannny.rendering.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.VertexHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ImageRenderer <T extends Entity> extends EntityRenderer<T> {
    public int width, height;

    public ImageRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    protected ImageRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }

    @Override
    protected int getBlockLightLevel(T entityIn, BlockPos partialTicks) {
        return this.renderLit() ? 15 : super.getBlockLightLevel(entityIn, partialTicks);
    }

    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entityIn.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entityIn) < 12.25D)) {
            RenderSystem.setShaderTexture(0, getTextureLocation(entityIn));
            matrixStackIn.pushPose();
            matrixStackIn.scale(this.width * 0.0625F, this.height * 0.0625F, this.height * 0.0625F);
            matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            new VertexHelper(bufferIn.getBuffer(RenderType.entityCutout(getTextureLocation(entityIn))), matrixStackIn, packedLightIn).addQuad(
                    new Vec3(-0.5, 0.5, 0),
                    new Vec3(-0.5, -0.5, 0),
                    new Vec3(0.5, -0.5, 0),
                    new Vec3(0.5, 0.5, 0),
                    0, 0, 1, 1, new Quaternion(1, 1, 1, 1), packedLightIn
            );
            matrixStackIn.popPose();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean renderLit() {
        return false;
    }
}

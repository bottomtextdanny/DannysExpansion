package net.bottomtextdanny.danny_expannny.rendering.entity.projectile.arrow;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BCRenderTypes;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.VertexHelper;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.config.ClientConfigurationHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;

public abstract class DannyArrowRenderer <T extends AbstractArrow> extends EntityRenderer<T> {


    public DannyArrowRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (ClientConfigurationHandler.CONFIG.wip_arrow_rendering.get()) {
            float scale = (float) ClientConfigurationHandler.CONFIG.wip_arrow_rendering_scale.get().doubleValue();
            float colorMult = 1;

            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
            matrixStackIn.scale(scale, scale, scale);
            matrixStackIn.scale(rescale(), rescale(), rescale());

            float shake = (float)entityIn.shakeTime - partialTicks;
            if (shake > 0.0F) {
                float f10 = -DEMath.sin(shake * 3.0F) * shake;
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f10));
            }
            matrixStackIn.translate(-0.5, 0, 0.0);

            VertexHelper polygonHelper = new VertexHelper(bufferIn.getBuffer(BCRenderTypes.getFlatShading(getTextureLocation(entityIn))), matrixStackIn, packedLightIn);
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-45));

            polygonHelper.addQuad(
                    new Vec3(0.5, 0, -0.5),
                    new Vec3(-0.5, 0, -0.5),
                    new Vec3(-0.5, 0, 0.5),
                    new Vec3(0.5, 0, 0.5),
                    1, 1, 0, 0, new Quaternion(colorMult, colorMult, colorMult,1), packedLightIn
            );
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-45));

            polygonHelper.addQuad(
                    new Vec3(-0.5, -0.5, 0),
                    new Vec3(-0.5, 0.5, 0),
                    new Vec3(0.5, 0.5, 0),
                    new Vec3(0.5, -0.5, 0),
                    0, 0, 1, 1, new Quaternion(colorMult, colorMult, colorMult, 1), packedLightIn
            );
            matrixStackIn.popPose();

            if (getFullbrightTexture(entityIn) != null) {
                VertexHelper polygonHelper1 = new VertexHelper(bufferIn.getBuffer(RenderType.dragonExplosionAlpha(getFullbrightTexture(entityIn))), matrixStackIn, packedLightIn);
                matrixStackIn.pushPose();
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-45));

                polygonHelper1.addQuad(
                        new Vec3(0.5, 0, -0.5),
                        new Vec3(-0.5, 0, -0.5),
                        new Vec3(-0.5, 0, 0.5),
                        new Vec3(0.5, 0, 0.5),
                        1, 1, 0, 0, new Quaternion(colorMult, colorMult, colorMult,1), packedLightIn
                );
                matrixStackIn.popPose();

                matrixStackIn.pushPose();
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-45));

                polygonHelper1.addQuad(
                        new Vec3(-0.5, -0.5, 0),
                        new Vec3(-0.5, 0.5, 0),
                        new Vec3(0.5, 0.5, 0),
                        new Vec3(0.5, -0.5, 0),
                        0, 0, 1, 1, new Quaternion(colorMult, colorMult, colorMult, 1), packedLightIn
                );
                matrixStackIn.popPose();
            }
            matrixStackIn.popPose();
        } else {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
            float f9 = (float)entityIn.shakeTime - partialTicks;
            if (f9 > 0.0F) {
                float f10 = -DEMath.sin(f9 * 3.0F) * f9;
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f10));
            }

            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(45.0F));
            matrixStackIn.scale(0.05625F, 0.05625F, 0.05625F);
            matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(new ResourceLocation(getTextureLocation(entityIn).toString().substring(0, getTextureLocation(entityIn).toString().length() - 4) + "_classic.png")));
            PoseStack.Pose matrixstack$entry = matrixStackIn.last();
            Matrix4f matrix4f = matrixstack$entry.pose();
            Matrix3f matrix3f = matrixstack$entry.normal();
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

            for(int j = 0; j < 4; ++j) {
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
            }
            matrixStackIn.popPose();
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     *Reflection.
     */
    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, int offsetX, int offsetY, int offsetZ, float textureX, float textureY, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn) {
        vertexBuilder.vertex(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
    }

    /**
     * Returns the image of an entity's texture.
     *
     * @param entity
     */
    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }

    public ResourceLocation getFullbrightTexture(T entity) {
        return null;
    }

    public float rescale() {
        return 1;
    }
}

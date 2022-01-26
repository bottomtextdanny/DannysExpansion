package net.bottomtextdanny.danny_expannny.rendering.kite;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.VertexHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.kites.*;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpecialKiteRenderer extends EntityRenderer<SpecialKiteEntity> {
    protected final List<RenderLayer<SpecialKiteEntity, KiteBaseModel<SpecialKiteEntity>>> layerRenderers = Lists.newArrayList();
    KiteBaseModel<SpecialKiteEntity> entityModel;

    public SpecialKiteRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SpecialKiteRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    public final boolean addLayer(RenderLayer<SpecialKiteEntity, KiteBaseModel<SpecialKiteEntity>> layer) {
        return this.layerRenderers.add(layer);
    }

    public void render(SpecialKiteEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entityIn.cachedModel == 0) {
            this.entityModel = new KiteModel<>();
        } else if (entityIn.cachedModel == 1) {
            this.entityModel = new LargeKiteModel<>();
        } else if (entityIn.cachedModel == 3) {
            this.entityModel = new HugeHalfKiteModel<>();
        } else if (entityIn.cachedModel == 4) {
            this.entityModel = new HugeKiteModel<>();
        }

        matrixStackIn.pushPose();
        float f1 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 180;
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
        float ageInTicks = this.handleRotationFloat(entityIn, partialTicks);

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer ivertexbuilder0 = bufferIn.getBuffer(getRender(entityIn));
        matrixStackIn.scale(1F, 1F, 1.0F);
        this.entityModel.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, f1, f2);
        this.entityModel.renderToBuffer(matrixStackIn, ivertexbuilder0, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for(RenderLayer<SpecialKiteEntity, KiteBaseModel<SpecialKiteEntity>> layerrenderer : this.layerRenderers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 1.0F, 0.0F, partialTicks, ageInTicks, f1, f2);
        }

        if (entityIn.cachedIsFullbright) {
            VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(DannysExpansion.ID, getTextureLocation(entityIn).getPath().replaceFirst(".png", "_fullbright.png"))));

            this.entityModel.renderToBuffer(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float easedPosX = (float) Mth.lerp(partialTicks, entityIn.xo, entityIn.getX());
        float easedPosY = (float) Mth.lerp(partialTicks, entityIn.yo, entityIn.getY());
        float easedPosZ = (float) Mth.lerp(partialTicks, entityIn.zo, entityIn.getZ());

        if (entityIn.getKnot() != null) {
            float knotEasedPosX = (float) Mth.lerp(partialTicks, entityIn.getKnot().xo, entityIn.getKnot().getX());
            float knotEasedPosY = (float) Mth.lerp(partialTicks, entityIn.getKnot().yo, entityIn.getKnot().getY());
            float knotEasedPosZ = (float) Mth.lerp(partialTicks, entityIn.getKnot().zo, entityIn.getKnot().getZ());

            matrixStackIn.pushPose();
            float horizontalDistance = DEMath.getHorizontalDistance(easedPosX, easedPosZ, knotEasedPosX, knotEasedPosZ);
            float verticalDistance = easedPosY - knotEasedPosY;
            float yawToKnot = DEMath.getTargetYaw(easedPosX, easedPosZ, knotEasedPosX, knotEasedPosZ);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-yawToKnot));

            matrixStackIn.pushPose();
            VertexConsumer ivertexbuilder2 = bufferIn.getBuffer(getCordRenderer(entityIn));

            VertexHelper polygonHelper2 = new VertexHelper(ivertexbuilder2, matrixStackIn, packedLightIn);


            float texPos = 0.0625F / 4;

            int instances = 24;

            float yOffset = 0;

            for (int i = 0; i < instances; i++) {
                float uneasedProg = (float) (i + 1) / 23;
                float prog = Easing.EASE_OUT_SQUARE.progression(uneasedProg) * verticalDistance;
                matrixStackIn.pushPose();

                polygonHelper2.addQuadPair(
                        new Vec3(texPos, -prog, horizontalDistance / instances),
                        new Vec3(texPos, -yOffset, 0),
                        new Vec3(-texPos, -yOffset, 0),
                        new Vec3(-texPos, -prog, horizontalDistance / instances),
                        0, 0, texPos * 2, horizontalDistance / instances, new Quaternion(1, 1, 1, 255), packedLightIn);
                matrixStackIn.translate(0, -prog, 0);
                matrixStackIn.popPose();

                matrixStackIn.pushPose();
                polygonHelper2.addQuadPair(
                        new Vec3(0, texPos - prog, horizontalDistance / instances),
                        new Vec3(0, texPos - yOffset, 0),
                        new Vec3(0, -texPos - yOffset, 0),
                        new Vec3(0, -texPos - prog, horizontalDistance / instances),
                        0, 0, texPos * 2, horizontalDistance / instances, new Quaternion(1, 1, 1, 255), packedLightIn);
                matrixStackIn.translate(0, -prog, 0);
                matrixStackIn.popPose();

                matrixStackIn.translate(0, 0, horizontalDistance / instances);

                yOffset = prog;
            }

            matrixStackIn.popPose();
            matrixStackIn.popPose();
        }
    }

    public void renderStatic(SpecialKiteEntity entityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        KiteBaseModel<SpecialKiteEntity> entityModel1 = null;

        if (entityIn.cachedModel == 0) {
            entityModel1 = new KiteModel<>();
        } else if (entityIn.cachedModel == 1){
            entityModel1 = new LargeKiteModel<>();
        } else if (entityIn.cachedModel == 3){
            entityModel1 = new HugeHalfKiteModel<>();
        } else if (entityIn.cachedModel == 4){
            entityModel1 = new HugeKiteModel<>();
        }
        matrixStackIn.pushPose();

        float f1 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 180;
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());        float ageInTicks = this.handleRotationFloat(entityIn, partialTicks);

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.scale(1F, 1F, 1.0F);

        VertexConsumer ivertexbuilder0 = bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn)));
        entityModel1.renderToBuffer(matrixStackIn, ivertexbuilder0, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for(RenderLayer<SpecialKiteEntity, KiteBaseModel<SpecialKiteEntity>> layerrenderer : this.layerRenderers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 1.0F, 0.0F, partialTicks, ageInTicks, f1, f2);
        }

        if (entityIn.cachedIsFullbright) {
            VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderType.dragonExplosionAlpha(new ResourceLocation(DannysExpansion.ID, getTextureLocation(entityIn).getPath().replaceFirst(".png", "_fullbright.png"))));

            entityModel1.renderToBuffer(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStackIn.popPose();

        VertexConsumer ivertexbuilder3 = bufferIn.getBuffer(RenderType.entitySolid(new ResourceLocation("textures/block/white_wool.png")));
    }

    @Override
    public boolean shouldRender(SpecialKiteEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    public RenderType getCordRenderer(SpecialKiteEntity entityIn) {
        return RenderType.entitySolid(new ResourceLocation("textures/block/white_wool.png"));
    }

    protected float handleRotationFloat(SpecialKiteEntity livingBase, float partialTicks) {
        return (float)livingBase.tickCount + livingBase.tickOffset + partialTicks;
    }

    public RenderType getRender(SpecialKiteEntity entity) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entity));
    }

    public ResourceLocation getTextureLocation(SpecialKiteEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/kite/special/" + entity.cachedTextureId + ".png");
    }
}

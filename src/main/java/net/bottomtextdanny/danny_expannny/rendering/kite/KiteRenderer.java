package net.bottomtextdanny.danny_expannny.rendering.kite;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.VertexHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.kites.KiteModel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

public class KiteRenderer extends EntityRenderer<KiteEntity> implements RenderLayerParent<KiteEntity, KiteModel<KiteEntity>> {
    protected final List<RenderLayer<KiteEntity, KiteModel<KiteEntity>>> layerRenderers = Lists.newArrayList();
    KiteModel<KiteEntity> entityModel;
    public static final List<String> COLORS = Arrays.asList(
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black"
    );

    public KiteRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public KiteRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.entityModel = new KiteModel<>();
    }

    public final boolean addLayer(RenderLayer<KiteEntity, KiteModel<KiteEntity>> layer) {
        return this.layerRenderers.add(layer);
    }

    public void render(KiteEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        float f1 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 180;
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn)));
        matrixStackIn.scale(1F, 1F, 1.0F);
        float ageInTicks = this.handleRotationFloat(entityIn, partialTicks);
        this.entityModel.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, f1, f2);
        this.entityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        for(RenderLayer<KiteEntity, KiteModel<KiteEntity>> layerrenderer : this.layerRenderers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 1.0F, 0.0F, partialTicks, ageInTicks, f1, f2);
        }


        matrixStackIn.popPose();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);


        float easedPosX = (float) Mth.lerp(partialTicks, entityIn.xo, entityIn.getX());
        float easedPosY = (float) Mth.lerp(partialTicks, entityIn.yo, entityIn.getY());
        float easedPosZ = (float) Mth.lerp(partialTicks, entityIn.zo, entityIn.getZ());



        VertexConsumer ivertexbuilder3 = bufferIn.getBuffer(getPolygonRenderType());
        VertexHelper polygonHelper2 = new VertexHelper(ivertexbuilder3, matrixStackIn, packedLightIn);


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
                        0, 0, texPos * 2, horizontalDistance / instances, new Quaternion(1, 1, 1, 1), packedLightIn);
                matrixStackIn.translate(0, -prog, 0);
                matrixStackIn.popPose();

                matrixStackIn.pushPose();
                polygonHelper2.addQuadPair(
                        new Vec3(0, texPos - prog, horizontalDistance / instances),
                        new Vec3(0, texPos - yOffset, 0),
                        new Vec3(0, -texPos - yOffset, 0),
                        new Vec3(0, -texPos - prog, horizontalDistance / instances),
                        0, 0, texPos * 2, horizontalDistance / instances, new Quaternion(1, 1, 1, 1), packedLightIn);
                matrixStackIn.translate(0, -prog, 0);
                matrixStackIn.popPose();

                matrixStackIn.translate(0, 0, horizontalDistance / instances);

                yOffset = prog;
            }


            matrixStackIn.popPose();
            matrixStackIn.popPose();

        }

        if (!entityIn.cachedDesign.equals("none")) {
            matrixStackIn.pushPose();
            float texPos = 0.0625F;
            VertexConsumer ivertexbuilder0 = bufferIn.getBuffer(getDesignRenderType(entityIn));
            VertexHelper polygonHelper1 = new VertexHelper(ivertexbuilder0, matrixStackIn, packedLightIn);

            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));
            this.entityModel.kite.translateRotateWithParentsInverted(matrixStackIn);
            matrixStackIn.translate(texPos * 5.5F, texPos * -9F, texPos * 0.515);
            polygonHelper1.addQuad(
                    new Vec3(texPos, 0, 0),
                    new Vec3(texPos, texPos * 18, 0),
                    new Vec3(-texPos * 12, texPos * 18, 0),
                    new Vec3(-texPos * 12, 0, 0),
                    0, 0, -1, 1, new Quaternion((float)entityIn.designColor.x() / 255, (float)entityIn.designColor.y() / 255, (float)entityIn.designColor.z() / 255, 1), packedLightIn);
            matrixStackIn.translate(0.0F, 0.0F, texPos * -0.03);
            polygonHelper1.addQuad(
                    new Vec3(texPos, 0, 0),
                    new Vec3(texPos, texPos * 18, 0),
                    new Vec3(-texPos * 12, texPos * 18, 0),
                    new Vec3(-texPos * 12, 0, 0),
                    0, 0, -1, 1, new Quaternion((float)entityIn.designColor.x() / 510, (float)entityIn.designColor.y() / 510, (float)entityIn.designColor.z() / 510, 0.6F), packedLightIn);

            matrixStackIn.popPose();
        }
    }

    public void renderStatic(KiteEntity entityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        KiteModel<KiteEntity> entityModel1 = new KiteModel<KiteEntity>();

        matrixStackIn.pushPose();

        float f1 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 180;
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
        float ageInTicks = this.handleRotationFloat(entityIn, partialTicks);

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.scale(1F, 1F, 1.0F);

        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn)));
        entityModel1.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for(RenderLayer<KiteEntity, KiteModel<KiteEntity>> layerrenderer : this.layerRenderers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 1.0F, 0.0F, partialTicks, ageInTicks, f1, f2);
        }


        matrixStackIn.popPose();

        VertexConsumer ivertexbuilder3 = bufferIn.getBuffer(getPolygonRenderType());
        VertexHelper polygonHelper2 = new VertexHelper(ivertexbuilder3, matrixStackIn, packedLightIn);


        if (!entityIn.cachedDesign.equals("none")) {
            matrixStackIn.pushPose();
            float texPos = 0.0625F;
            VertexConsumer ivertexbuilder0 = bufferIn.getBuffer(getDesignRenderType(entityIn));
            VertexHelper polygonHelper1 = new VertexHelper(ivertexbuilder0, matrixStackIn, packedLightIn);

            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 - f1));
            entityModel1.kite.translateRotateWithParentsInverted(matrixStackIn);
            matrixStackIn.translate(texPos * 5.5F, texPos * -9F, texPos * 0.515);
            polygonHelper1.addQuad(
                    new Vec3(texPos, 0, 0),
                    new Vec3(texPos, texPos * 18, 0),
                    new Vec3(-texPos * 12, texPos * 18, 0),
                    new Vec3(-texPos * 12, 0, 0),
                    0, 0, -1, 1, new Quaternion((float)entityIn.designColor.x() / 255, (float)entityIn.designColor.y() / 255, (float)entityIn.designColor.z() / 255, 1), packedLightIn);
            matrixStackIn.translate(0.0F, 0.0F, texPos * -0.03);
            polygonHelper1.addQuad(
                    new Vec3(texPos, 0, 0),
                    new Vec3(texPos, texPos * 18, 0),
                    new Vec3(-texPos * 12, texPos * 18, 0),
                    new Vec3(-texPos * 12, 0, 0),
                    0, 0, -1, 1, new Quaternion((float)entityIn.designColor.x() / 510, (float)entityIn.designColor.y() / 510, (float)entityIn.designColor.z() / 510, 0.6F), packedLightIn);

            matrixStackIn.popPose();
        }
    }

    @Override
    public boolean shouldRender(KiteEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    protected float handleRotationFloat(KiteEntity livingBase, float partialTicks) {
        return (float)livingBase.tickCount + livingBase.tickOffset + partialTicks;
    }

    @Override
    public KiteModel<KiteEntity> getModel() {
        return this.entityModel;
    }

    public ResourceLocation getTextureLocation(KiteEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/kite/" + COLORS.get(entity.cachedKiteColor) + "_kite.png");
    }

    public RenderType getPolygonRenderType() {
        return RenderType.entitySolid(new ResourceLocation("textures/block/white_wool.png"));
    }

    public RenderType getDesignRenderType(KiteEntity entity) {
        return RenderType.entityTranslucent(new ResourceLocation(DannysExpansion.ID, "textures/entity/kite/designs/" + entity.cachedDesign + ".png"));
    }
}


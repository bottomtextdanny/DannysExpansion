package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.HangingKlifourModel;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.KlifourModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.klifour.KlifourEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class KlifourRenderer extends MobRenderer<KlifourEntity, BCEntityModel<KlifourEntity>> {
    public KlifourModel klifourModel = new KlifourModel();
    public HangingKlifourModel hangingKlifourModel = new HangingKlifourModel();

    public KlifourRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public KlifourRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KlifourModel(), 0.0F);
    }

    /**
     * Returns the image of an entity's texture.
     *
     * @param entity
     */
    @Override
    public ResourceLocation getTextureLocation(KlifourEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/klifour/klifour.png");
    }


    @Override
    public void render(KlifourEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<KlifourEntity, BCEntityModel<KlifourEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;

        matrixStackIn.pushPose();
        BCEntityModel<KlifourEntity> kModel = entityIn.getAttachingDirection().get3DDataValue() > 1 ? this.hangingKlifourModel : this.klifourModel;
        int i0 = entityIn.getAttachingDirection().get3DDataValue() == 1 ? -1 : 1;

        float f0 = i0 * Mth.rotLerp(partialTicks, entityIn.yHeadRotO, entityIn.yHeadRot) - 180;
        float f1 = i0 * Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entityIn, matrixStackIn, partialTicks);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn)));
        matrixStackIn.scale(1F, 1F, 1.0F);


        if (i0 == -1) {
            matrixStackIn.translate(0.0D, -entityIn.getBbHeight(), 0.0D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        }

        int i = getOverlayCoords(entityIn, this.getWhiteOverlayProgress(entityIn, partialTicks));

        kModel.prepareMobModel(entityIn, 0, 0, partialTicks);
        kModel.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, f0, f1);
        kModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, 1.0F);

        if (!entityIn.isSpectator()) {
            for(RenderLayer<KlifourEntity, BCEntityModel<KlifourEntity>> layerrenderer : this.layers) {
                layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 0, 0, partialTicks, entityIn.tickCount + partialTicks, f0, f1);
            }
        }

        matrixStackIn.popPose();

        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(entityIn))) {
            this.renderNameTag(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
    }
}

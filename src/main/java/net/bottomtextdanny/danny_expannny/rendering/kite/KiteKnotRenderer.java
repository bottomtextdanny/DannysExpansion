package net.bottomtextdanny.danny_expannny.rendering.kite;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.kites.KiteKnotModel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteKnotEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;


public class KiteKnotRenderer extends EntityRenderer<KiteKnotEntity> {
    private static final ResourceLocation KITE_KNOT_TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/kite_knot.png");
    private final KiteKnotModel<KiteKnotEntity> kiteKnotModel = new KiteKnotModel<>();

    public KiteKnotRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public KiteKnotRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(KiteKnotEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.kiteKnotModel.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.kiteKnotModel.renderType(KITE_KNOT_TEXTURES));
        this.kiteKnotModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    /**
     * Returns the image of an entity's texture.
     */
    public ResourceLocation getTextureLocation(KiteKnotEntity entity) {
        return KITE_KNOT_TEXTURES;
    }

    public int getBlockLightLevel(KiteKnotEntity entityIn, BlockPos partialTicks) {
        return entityIn.isOnFire() ? 15 : entityIn.level.getBrightness(LightLayer.BLOCK, partialTicks);
    }
}

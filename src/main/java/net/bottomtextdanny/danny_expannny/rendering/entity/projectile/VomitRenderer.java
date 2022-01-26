package net.bottomtextdanny.danny_expannny.rendering.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.projectiles.SporeModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.VomitEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VomitRenderer extends EntityRenderer<VomitEntity> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/projectile/vomit.png");
    private final SporeModel model = new SporeModel();

    public VomitRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public VomitRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(VomitEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()) + 90.0F));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entityIn)));
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Returns the image of an entity's texture.
     */
    public ResourceLocation getTextureLocation(VomitEntity entity) {
        return TEXTURES;
    }

    @Override
    protected int getBlockLightLevel(VomitEntity entityIn, BlockPos partialTicks) {
        return 15;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.GrandRammerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.GrandRammerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GrandRammerSaddleLayer extends RenderLayer<GrandRammerEntity, GrandRammerModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/grand_rammer_saddle.png");
    private final GrandRammerModel model = new GrandRammerModel(0.25F);

    public GrandRammerSaddleLayer(RenderLayerParent<GrandRammerEntity, GrandRammerModel> rendererIn) {
        super(rendererIn);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, GrandRammerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        if (entitylivingbaseIn.isSaddled()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}

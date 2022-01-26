package net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.RammerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.RammerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RammerEyesLayer extends RenderLayer<RammerEntity, RammerModel> {
    private static final ResourceLocation PLACIDNESS = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer_eyes_placidness.png");
    private static final ResourceLocation HAPPINESS = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer_eyes_happiness.png");
    private static final ResourceLocation SADNESS = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer_eyes_sadness.png");
    private static final ResourceLocation FEAR = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer_eyes_fear.png");
    private static final ResourceLocation ANGER = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer_eyes_anger.png");

    public RammerEyesLayer(RenderLayerParent<RammerEntity, RammerModel> entityRendererIn) {
        super(entityRendererIn);
    }

    public static final ResourceLocation getEyesTexture(RammerEntity entity) {
        if (entity.isAggressive()) {
            return ANGER;
        } else {
            if (entity.getHealth() >= entity.getAttribute(Attributes.MAX_HEALTH).getValue() / 2) {
                return PLACIDNESS;
            } else if (entity.getHealth() < entity.getAttribute(Attributes.MAX_HEALTH).getValue() / 2 && entity.getHealth() >= entity.getAttribute(Attributes.MAX_HEALTH).getValue() / 4) {
                return SADNESS;
            } else {
                return FEAR;
            }
        }
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, RammerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation resourcelocation = getEyesTexture(entity);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(resourcelocation));
        this.getParentModel().setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}

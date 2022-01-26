package net.bottomtextdanny.danny_expannny.rendering.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.MummyModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MummyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;

public class MummyForeheadItemLayer  extends RenderLayer<MummyEntity, MummyModel> {
    public MummyForeheadItemLayer(RenderLayerParent<MummyEntity, MummyModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, MummyEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            matrixStackIn.pushPose();
            ItemStack itemstack = entitylivingbaseIn.getForeheadItem();

            getParentModel().head.translateRotateWithParents(matrixStackIn);

            matrixStackIn.scale(0.6F, 0.7F, 0.6F);
            matrixStackIn.translate(0.0F, -0.7, -0.48);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180.0F));

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);

            matrixStackIn.popPose();
        }

    }
}

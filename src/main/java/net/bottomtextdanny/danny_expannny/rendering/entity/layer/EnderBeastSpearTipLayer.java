package net.bottomtextdanny.danny_expannny.rendering.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ender_beasts.EnderBeastLancerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast.EnderBeastLancerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class EnderBeastSpearTipLayer extends RenderLayer<EnderBeastLancerEntity, EnderBeastLancerModel> {
    public EnderBeastSpearTipLayer(RenderLayerParent<EnderBeastLancerEntity, EnderBeastLancerModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EnderBeastLancerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (!entitylivingbaseIn.isInvisible()) {
//            matrixStackIn.push();
//            ItemStack itemstack = new ItemStack(DannyItems.ENDER_BEAST_SPEAR_TIP.get());
//
//            getEntityModel().spear.translateRotateWithParents(matrixStackIn);
//
//            matrixStackIn.scale(1.2F, 2.35F, 2.35F);
//            matrixStackIn.translate(0.0F, 0.13, -1.02);
//            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90));
//            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180.0F));
//
//            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
//
//            matrixStackIn.pop();
//        }
    }

}

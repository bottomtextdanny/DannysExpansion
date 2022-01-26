package net.bottomtextdanny.danny_expannny.rendering.entity.layer.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.SporeBombModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SporeBombEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class SporeBombItemLayer extends RenderLayer<SporeBombEntity, SporeBombModel> {

    public SporeBombItemLayer(RenderLayerParent<SporeBombEntity, SporeBombModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, SporeBombEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
        ItemStack itemstack = new ItemStack(DEItems.SPORE_BOMB.get());
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemrenderer = mc.getItemRenderer();

        getParentModel().model.translateRotate(matrixStackIn);

        matrixStackIn.scale(1.0F, 1.0F, 1.0F);
        matrixStackIn.translate(0.0F, 0.0, 0.0);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180.0F));

        itemrenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, 0);

        matrixStackIn.popPose();
    }
}

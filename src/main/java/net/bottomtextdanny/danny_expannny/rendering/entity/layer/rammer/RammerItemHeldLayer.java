package net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.RammerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.RammerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RammerItemHeldLayer extends RenderLayer<RammerEntity, RammerModel> {

    public RammerItemHeldLayer(RenderLayerParent<RammerEntity, RammerModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, RammerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
        getParentModel().model.translateRotate(matrixStackIn);
        getParentModel().body.translateRotate(matrixStackIn);
        getParentModel().head.translateRotate(matrixStackIn);

        matrixStackIn.translate(0.0F, 0.2, -0.62F);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStackIn.scale(0.65F, 0.65F, 0.65F);

        ItemStack itemstack = entitylivingbaseIn.getItemBySlot(EquipmentSlot.MAINHAND);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.popPose();
    }
}

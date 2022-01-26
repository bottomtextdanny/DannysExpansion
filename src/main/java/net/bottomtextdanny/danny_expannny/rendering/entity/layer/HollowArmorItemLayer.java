package net.bottomtextdanny.danny_expannny.rendering.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.HollowArmorModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HollowArmorItemLayer extends RenderLayer<HollowArmor, HollowArmorModel> {

    public HollowArmorItemLayer(RenderLayerParent<HollowArmor, HollowArmorModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, HollowArmor entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.mainAnimationHandler.isPlaying(entitylivingbaseIn.getHealAnimation()) && entitylivingbaseIn.mainAnimationHandler.getTick() > 6 && entitylivingbaseIn.mainAnimationHandler.getTick() <= 22 ) {
            matrixStackIn.pushPose();
            getParentModel().leftHand.translateRotateWithParents(matrixStackIn);

            matrixStackIn.translate(0.02F, 0.13, -0.12F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            ItemStack itemstack = new ItemStack(Items.HONEY_BOTTLE);

            if (entitylivingbaseIn.mainAnimationHandler.getTick() > 18) {
                itemstack = new ItemStack(Items.GLASS_BOTTLE);
            }
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);

            matrixStackIn.popPose();
        }
    }
}

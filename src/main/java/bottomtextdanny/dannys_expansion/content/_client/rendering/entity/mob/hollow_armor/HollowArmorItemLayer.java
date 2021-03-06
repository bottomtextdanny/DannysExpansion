package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.hollow_armor;

import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.HollowArmorModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.hollow_armor.HollowArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
        if (entitylivingbaseIn.mainHandler.isPlaying(HollowArmor.HEAL) && entitylivingbaseIn.mainHandler.getTick() > 6 && entitylivingbaseIn.mainHandler.getTick() <= 22 ) {
            matrixStackIn.pushPose();
            getParentModel().leftHand.translateRotateWithParents(matrixStackIn);

            matrixStackIn.translate(0.02F, 0.13, -0.12F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            ItemStack itemstack = new ItemStack(Items.HONEY_BOTTLE);

            if (entitylivingbaseIn.mainHandler.getTick() > 18) {
                itemstack = new ItemStack(Items.GLASS_BOTTLE);
            }
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);

            matrixStackIn.popPose();
        }
    }
}

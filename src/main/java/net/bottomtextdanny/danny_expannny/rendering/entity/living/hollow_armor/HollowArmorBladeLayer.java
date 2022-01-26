package net.bottomtextdanny.danny_expannny.rendering.entity.living.hollow_armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.HollowArmorModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.hollow_armor.HollowArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;

public class HollowArmorBladeLayer extends RenderLayer<HollowArmor, HollowArmorModel> {

    public HollowArmorBladeLayer(RenderLayerParent<HollowArmor, HollowArmorModel> p_i50938_1_) {
        super(p_i50938_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, HollowArmor entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            matrixStackIn.pushPose();
            ItemStack itemstack = new ItemStack(DEItems.HOLLOW_ARMOR_BLADE.get());

            getParentModel().sword.translateRotateWithParents(matrixStackIn);

            matrixStackIn.scale(1.2F, 2.35F, 2.35F);
            matrixStackIn.translate(0.0F, 0.125, -0.368);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180.0F));

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);

            matrixStackIn.popPose();
        }
    }
}

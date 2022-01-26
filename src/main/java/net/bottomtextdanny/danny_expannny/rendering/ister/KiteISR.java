package net.bottomtextdanny.danny_expannny.rendering.ister;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.danny_expannny.ClientInstance;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.SpecialKiteEntity;
import net.bottomtextdanny.danny_expannny.objects.items.KiteItem;
import net.bottomtextdanny.danny_expannny.objects.items.SpecialKiteItem;
import net.bottomtextdanny.danny_expannny.rendering.kite.KiteRenderer;
import net.bottomtextdanny.danny_expannny.rendering.kite.SpecialKiteRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class KiteISR extends BlockEntityWithoutLevelRenderer {

    public KiteISR() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        boolean flag0 = transformType == ItemTransforms.TransformType.GUI;
        boolean flag1 = transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
        boolean flag2 = transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND;

        matrixStack.pushPose();

        matrixStack.translate(0.5F, 0.5F, 0.5F);

        if (flag0 || transformType == ItemTransforms.TransformType.GROUND) matrixStack.scale(0.75F, 0.75F, 0.75F);
        else if (flag1) matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));

        if (transformType != ItemTransforms.TransformType.GUI) matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        if (flag0 || flag1) matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-22.5F));

        if (stack.getItem() instanceof KiteItem) {
            KiteEntity kiteEntity = ((KiteItem)stack.getItem()).createKite(ClientInstance.player().level, stack);
            kiteEntity.updateData();

            if (flag0) Lighting.setupForFlatItems();

            ((KiteRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(kiteEntity)).renderStatic(kiteEntity, 0, matrixStack, buffer, combinedLight);

            if (flag0) Lighting.setupFor3DItems();

        } else if (stack.getItem() instanceof SpecialKiteItem) {
            SpecialKiteItem item = ((SpecialKiteItem)stack.getItem());
            SpecialKiteEntity specialKiteEntity = (SpecialKiteEntity) item.createKite(ClientInstance.player().level, stack);
            specialKiteEntity.updateData();

            if (flag0) Lighting.setupForFlatItems();

            if (item.getModel() > 2) {
                if (flag2 || flag1)
                matrixStack.scale(0.55F, 0.55F, 0.55F);
                else if (flag0) {
                    matrixStack.scale(0.75F, 0.75F, 0.75F);
                }
            }

            ((SpecialKiteRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(specialKiteEntity)).renderStatic(specialKiteEntity, 0, matrixStack, buffer, combinedLight);

            if (flag0) Lighting.setupFor3DItems();
        }

        matrixStack.popPose();
    }
}

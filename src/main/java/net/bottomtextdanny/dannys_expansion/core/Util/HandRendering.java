package net.bottomtextdanny.dannys_expansion.core.Util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class HandRendering {

    public static void render(PoseStack matrixStackIn, Camera activeRenderInfoIn, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        GameRenderer renderer = mc.gameRenderer;

        renderer.resetProjectionMatrix(RenderSystem.getProjectionMatrix());
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        matrixstack$entry.pose().setIdentity();
        matrixstack$entry.normal().setIdentity();
        matrixStackIn.pushPose();
        hurtCameraEffect(matrixStackIn, partialTicks);
        if (mc.options.bobView) {
            aBob(matrixStackIn, partialTicks);
        }

        boolean flag = mc.getCameraEntity() instanceof LivingEntity && ((LivingEntity)mc.getCameraEntity()).isSleeping();
        if (mc.options.getCameraType().isFirstPerson() && !flag && !mc.options.hideGui && mc.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            renderer.lightTexture().turnOnLightLayer();
            renderer.itemInHandRenderer.renderHandsWithItems(partialTicks, matrixStackIn, mc.renderBuffers().bufferSource(), mc.player, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks));
            renderer.lightTexture().turnOffLightLayer();
        }

        matrixStackIn.popPose();

        if (mc.options.getCameraType().isFirstPerson() && !flag) {
            ScreenEffectRenderer.renderScreenEffect(mc, matrixStackIn);
        }
    }

    private static void hurtCameraEffect(PoseStack matrixStackIn, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getCameraEntity() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)mc.getCameraEntity();
            float f = (float)livingentity.hurtTime - partialTicks;
            if (livingentity.isDeadOrDying()) {
                float f1 = Math.min((float)livingentity.deathTime + partialTicks, 20.0F);
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(40.0F - 8000.0F / (f1 + 200.0F)));
            }

            if (f < 0.0F) {
                return;
            }

            f /= livingentity.hurtDuration;
            f = DEMath.sin(f * f * f * f * (float)Math.PI);
            float f2 = livingentity.hurtDir;
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f2));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-f * 14.0F));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f2));
        }

    }

    private static void aBob(PoseStack matrixStackIn, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getCameraEntity() instanceof Player) {
            Player playerentity = (Player)mc.getCameraEntity();
            float f = playerentity.walkDist - playerentity.walkDistO;
            float f1 = -(playerentity.walkDist + f * partialTicks);
            float f2 = Mth.lerp(partialTicks, playerentity.oBob, playerentity.bob);
            matrixStackIn.translate(DEMath.sin(f1 * (float)Math.PI) * f2 * 0.5F, -Math.abs(DEMath.cos(f1 * (float)Math.PI) * f2), 0.0D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(DEMath.sin(f1 * (float)Math.PI) * f2 * 3.0F));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(Math.abs(DEMath.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
        }
    }
}

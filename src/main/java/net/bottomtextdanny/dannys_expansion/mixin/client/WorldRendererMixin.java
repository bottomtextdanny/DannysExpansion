package net.bottomtextdanny.dannys_expansion.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.bottomtextdanny.braincell.Braincell;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {


    @Inject(at = @At(value = "HEAD"), method = "renderLevel", remap = false)
    private void renderLevelHead(PoseStack matrixStackIn, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera activeRenderInfoIn, GameRenderer gameRendererIn, LightTexture lightmapIn, Matrix4f projectionIn, CallbackInfo ci) {
        Braincell.client().getRenderingHandler().captureRenderInitialInformation(matrixStackIn, projectionIn, activeRenderInfoIn);
    }


    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch(Lnet/minecraft/client/renderer/RenderType;)V", ordinal = 23), remap = false)
    private void worldRenderTail(PoseStack matrixStackIn, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera activeRenderInfoIn, GameRenderer gameRendererIn, LightTexture lightmapIn, Matrix4f projectionIn, CallbackInfo ci) {
        Braincell.client().getRenderingHandler().captureLevelDepth();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", ordinal = 1), remap = false)
    private void worldGetFog(PoseStack matrixStackIn, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera activeRenderInfoIn, GameRenderer gameRendererIn, LightTexture lightmapIn, Matrix4f projectionIn, CallbackInfo ci) {
        Braincell.client().getRenderingHandler().captureTerrainFog();
    }
}

package net.bottomtextdanny.braincell.mod.structure.client_sided;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.port.BCClippingHelper;
import net.bottomtextdanny.braincell.mod.opengl_helpers.BCFramebuffer;
import net.bottomtextdanny.braincell.mod.structure.BCStaticData;
import net.bottomtextdanny.dannys_expansion.core.Util.HandRendering;
import net.bottomtextdanny.dannys_expansion.core.base.pl.PhysicalLight;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class RenderingHandler {
    private BCFramebuffer worldDepthFramebuffer;
    private Matrix4f projectionMatrix;
    private Matrix4f modelViewMatrix;
    private Matrix4f invertedModelViewMatrix;
    private float terrainFogStart;
    private float terrainFogEnd;
    private float[] terrainFogColor;
    private BCClippingHelper frustum;
    private boolean updatedToCurrentFrame;

    public void postProcessing() {
        Camera info = mc().gameRenderer.getMainCamera();
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.worldDepthFramebuffer.getId());
        GlStateManager._enableDepthTest();
        GlStateManager._depthFunc(GL11.GL_ALWAYS);
        HandRendering.render(new PoseStack(), info, BCStaticData.partialTick());
        GlStateManager._enableBlend();
        RenderSystem.defaultBlendFunc();
        GlStateManager._disableDepthTest();
        GlStateManager._glBindFramebuffer(GL30.GL_FRAMEBUFFER, mainTarget().frameBufferId);
        Braincell.client().getPostprocessingHandler().frame();
    }

    public void captureWindowModification() {
        Minecraft mc = Minecraft.getInstance();
        RenderTarget fbo = mc.getMainRenderTarget();
        this.worldDepthFramebuffer = new BCFramebuffer(fbo.width, fbo.height, true);
    }

    public void captureInitialization(float partialTick) {
        if (!this.updatedToCurrentFrame) {
            BCStaticData.setPartialTick(partialTick, new BCClientToken());
            this.updatedToCurrentFrame = true;
        }
    }

    public void setDataOutdated() {
        this.updatedToCurrentFrame = false;
    }

    public void captureRenderInitialInformation(PoseStack matrixStackIn,
                                                Matrix4f projectionIn,
                                                Camera activeRenderInfo) {
        Vec3 vector3d = activeRenderInfo.getPosition();
        double d0 = vector3d.x();
        double d1 = vector3d.y();
        double d2 = vector3d.z();
        this.projectionMatrix = projectionIn.copy();
        this.modelViewMatrix = matrixStackIn.last().pose().copy();

        Matrix4f mat = RenderSystem.getProjectionMatrix().copy();
        mat.transpose();
        mat.invert();
        mat.multiply(this.modelViewMatrix);
        this.invertedModelViewMatrix = mat;

        this.frustum = new BCClippingHelper(matrixStackIn.last().pose(), projectionIn);
        this.frustum.setCameraPosition(d0, d1, d2);

        LevelCapability capability = CapabilityHelper.get(mc().level, LevelCapability.CAPABILITY);

        capability.getPhysicalLightModule().getLightList()
                .forEach(PhysicalLight::processRender);
    }

    public void captureLevelDepth() {
        if (this.worldDepthFramebuffer == null) {
            this.worldDepthFramebuffer =
                    new BCFramebuffer(mainTarget().width, mainTarget().height, true);
        }
        GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, mainTarget().frameBufferId);
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.worldDepthFramebuffer.getId());
        GlStateManager._glBlitFrameBuffer(
                0, 0,
                mainTarget().width, mainTarget().height,
                0, 0,
                this.worldDepthFramebuffer.width, this.worldDepthFramebuffer.height,
                GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
        GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, mainTarget().frameBufferId);
    }

    public void captureTerrainFog() {
        this.terrainFogStart = RenderSystem.getShaderFogStart();
        this.terrainFogEnd = RenderSystem.getShaderFogEnd();
        this.terrainFogColor = RenderSystem.getShaderFogColor();
    }

    public BCClippingHelper getClipping() {
        return this.frustum;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getModelViewMatrix() {
        return this.modelViewMatrix;
    }

    public Matrix4f getInvertedModelViewMatrix() {
        return this.invertedModelViewMatrix;
    }

    public BCFramebuffer getWorldDepthFramebuffer() {
        return this.worldDepthFramebuffer;
    }

    public float getTerrainFogStart() {
        return this.terrainFogStart;
    }

    public float getTerrainFogEnd() {
        return this.terrainFogEnd;
    }

    public float[] getTerrainFogColor() {
        return this.terrainFogColor;
    }

    private Minecraft mc() {
        return Minecraft.getInstance();
    }

    private RenderTarget mainTarget() {
        return mc().getMainRenderTarget();
    }
}

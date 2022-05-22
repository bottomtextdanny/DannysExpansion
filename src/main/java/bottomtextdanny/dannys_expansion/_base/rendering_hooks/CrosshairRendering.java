package bottomtextdanny.dannys_expansion._base.rendering_hooks;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.gun_rendering.GunClientData;
import bottomtextdanny.dannys_expansion._base.gun_rendering.ScopeRenderingData;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.content.items.gun.GunItem;
import bottomtextdanny.dannys_expansion.content.items.gun.ScopingGun;
import bottomtextdanny.dannys_expansion._util._client.RenderUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import bottomtextdanny.braincell.mod._base.BCStaticData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public final class CrosshairRendering {
    private static final ResourceLocation CROSSHAIR =
            new ResourceLocation(DannysExpansion.ID, "textures/gui/gun_crosshair.png");

    public static void changeToGunCrosshairIfShould(RenderGameOverlayEvent.PreLayer event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = CMC.player();
        Options options = minecraft.options;
        PoseStack pose = event.getMatrixStack();

        if (options.hideGui) return;

        if (!options.getCameraType().isFirstPerson()) return;

        if (minecraft.gameMode == null
                || (minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR
                && !isTargetNamedMenuProvider(minecraft.hitResult))) return;

        if (player == null || !player.isAlive()) return;

        if (event.getOverlay() == ForgeIngameGui.CROSSHAIR_ELEMENT) {
            if (render(pose, player, options)) event.setCanceled(true);
        } else if (event.getOverlay() == ForgeIngameGui.HOTBAR_ELEMENT) {
            PlayerGunModule gunModule = PlayerHelper.gunModule(player);

            if (gunModule.getGunScoping() != ItemStack.EMPTY) {
                event.setCanceled(true);
            }
        }
    }
    
    public static boolean render(PoseStack pose, @Nonnull LocalPlayer player, Options options) {
        Minecraft minecraft = Minecraft.getInstance();

        if (player.getUseItem() != ItemStack.EMPTY) return false;

        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();

        PlayerGunModule gunModule = PlayerHelper.gunModule(player);
        if (gunModule.getPreviousGun().getItem() instanceof GunItem<?>) {
            if (gunModule.getGunScoping().getItem() instanceof ScopingGun scopingGun) {
                renderScope(pose, width, height, scopingGun);

                return true;
            } else if (!options.renderDebug
                    || player.isReducedDebugInfo()
                    || options.reducedDebugInfo) {
                RenderSystem.getModelViewStack().pushPose();
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, CROSSHAIR);
                renderGunCrosshair(pose, width, height);
                RenderSystem.getModelViewStack().popPose();

                return true;
            }
        }

        return false;
    }

    private static void renderScope(PoseStack pose, int width, int height, ScopingGun gun) {
        GunClientData gunData = DannysExpansion.client().getGunData();
        ScopeRenderingData data = DannysExpansion.client().getGunRenderData().getScopeData(gun);

        float tickOffset = BCStaticData.partialTick();
        int widthByTwo = width / 2;
        int heightByTwo = height / 2;
        int scopeSizeDivTwo = data.scopeSize() / 2;
        int scopeSizeByTwo = data.scopeSize() * 2;

        float innerScopeOffsetX = Mth.lerp(tickOffset, CMC.player().yRotO, CMC.player().getYRot())
                - Mth.lerp(tickOffset, gunData.yawO, gunData.yaw);

        float innerScopeOffsetY = Mth.lerp(tickOffset, CMC.player().xRotO, CMC.player().getXRot())
                - Mth.lerp(tickOffset, gunData.pitchO, gunData.pitch);

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[2]);

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR, GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR);

        RenderUtils.fBlit(pose, widthByTwo - scopeSizeDivTwo - innerScopeOffsetX,
                heightByTwo - scopeSizeDivTwo - innerScopeOffsetY,
                0, 0, 0,
                data.scopeSize(), data.scopeSize(), data.scopeSize(), data.scopeSize());

        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[1]);

        RenderUtils.fBlit(pose, widthByTwo - data.scopeSize() - innerScopeOffsetX,
                heightByTwo - data.scopeSize() - innerScopeOffsetY,
                0, 0, 0,
                scopeSizeByTwo, scopeSizeByTwo, scopeSizeByTwo, scopeSizeByTwo);

        RenderSystem.setShaderTexture(0, data.scopeTexturePaths()[0]);

        RenderUtils.fBlit(pose, widthByTwo - scopeSizeDivTwo,
                heightByTwo - scopeSizeDivTwo,
                0, 0, 0,
                data.scopeSize(), data.scopeSize(), data.scopeSize(), data.scopeSize());

        RenderSystem.getModelViewStack().pushPose();

        int x1 = 0, x2 = width, y1 = 0, y2 = heightByTwo - scopeSizeDivTwo;

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tess.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pose.last().pose(), x1, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x1, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.end();

        BufferUploader.end(bufferbuilder);

        y1 = heightByTwo + scopeSizeDivTwo;
        y2 = height;

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pose.last().pose(), x1, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x1, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.end();

        BufferUploader.end(bufferbuilder);

        x2 = widthByTwo - scopeSizeDivTwo;
        y1 = heightByTwo - scopeSizeDivTwo;
        y2 = heightByTwo + scopeSizeDivTwo;

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pose.last().pose(), x1, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x1, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.end();

        BufferUploader.end(bufferbuilder);

        x1 = widthByTwo + scopeSizeDivTwo;
        x2 = width;

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pose.last().pose(), x1, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y2, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x2, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.vertex(pose.last().pose(), x1, y1, 0)
                .color(10, 0, 20, 255).endVertex();
        bufferbuilder.end();

        BufferUploader.end(bufferbuilder);

        RenderSystem.getModelViewStack().popPose();
    }

    private static boolean renderGunCrosshair(PoseStack poseStack, int width, int height) {
        Options options = Minecraft.getInstance().options;
        float tickOffset = BCStaticData.partialTick();

        if (options.renderDebug && !options.hideGui
                && !Minecraft.getInstance().player.isReducedDebugInfo()
                && !options.reducedDebugInfo) {
            PoseStack pose = RenderSystem.getModelViewStack();

            pose.pushPose();
            pose.translate((float)(width / 2), (float)(height / 2), 0);
            Camera activerenderinfo = Minecraft.getInstance().gameRenderer.getMainCamera();
            pose.mulPose(new Quaternion(activerenderinfo.getXRot(), -1.0F, 0.0F, 0.0F));
            pose.mulPose(new Quaternion(activerenderinfo.getYRot(), 0.0F, 1.0F, 0.0F));
            pose.scale(-1.0F, -1.0F, -1.0F);
            RenderSystem.renderCrosshair(10);

            RenderSystem.getModelViewStack().popPose();
        } else {
            GunClientData gunData = DannysExpansion.client().getGunData();
            double fov = Minecraft.getInstance().options.fov;
            float visualDispersion = (float)(width * Mth.lerp(tickOffset, gunData.renderDispersionO, gunData.renderDispersion) / fov) / 2;
            int widthByTwo = width / 2;
            int heightByTwo = height / 2;

            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            RenderUtils.fBlit(poseStack, widthByTwo - 5 - visualDispersion, heightByTwo - 3, 0, 0, 7, 6, 5, 19, 19);

            RenderUtils.fBlit(poseStack, widthByTwo + visualDispersion, heightByTwo - 3, 0, 13, 7, 6, 5, 19, 19);

            RenderUtils.fBlit(poseStack, widthByTwo - 2, heightByTwo - 6 - visualDispersion, 0, 7, 0, 5, 6, 19, 19);

            RenderUtils.fBlit(poseStack, widthByTwo - 2, heightByTwo - 1 + visualDispersion, 0, 7, 13, 5, 6, 19, 19);

            return true;
        }

        return false;
    }

    private static boolean isTargetNamedMenuProvider(@Nullable HitResult rayTraceIn) {
        if (rayTraceIn == null) {
            return false;
        } else if (rayTraceIn.getType() == HitResult.Type.ENTITY) {
            return ((EntityHitResult)rayTraceIn).getEntity() instanceof MenuProvider;
        } else if (rayTraceIn.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)rayTraceIn).getBlockPos();
            Level world = Minecraft.getInstance().level;
            return world.getBlockState(blockpos).getMenuProvider(world, blockpos) != null;
        } else {
            return false;
        }
    }

    private CrosshairRendering() {}
}

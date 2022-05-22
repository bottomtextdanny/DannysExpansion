package bottomtextdanny.dannys_expansion.content._client.rendering.entity.misc;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.misc_entities.TestDummyModel;
import bottomtextdanny.dannys_expansion.content.entities.misc.TestDummyEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Formatter;

public class TestDummyRenderer extends EntityRenderer<TestDummyEntity> {
    public TestDummyModel dummyModel = new TestDummyModel();

    public TestDummyRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public TestDummyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(TestDummyEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        float f0 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 180;

        matrixStackIn.pushPose();
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn)));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, 0.005F, 0);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(entityIn.getYRot()));
        this.dummyModel.prepareMobModel(entityIn, 0, 0, partialTicks);
        this.dummyModel.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, f0, 0);
        this.dummyModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0, entityIn.getBbHeight() + 0.25F, 0);
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.scale(-0.025F, -0.025F, 0.025F);


        TextComponent dps = new TextComponent("DPS: " + new Formatter().format("%.2f", entityIn.getDPS()));


        Font fontrenderer = this.getFont();
        float f1 = (float)(-fontrenderer.width(dps) / 2);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR, GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_DST_COLOR);

        fontrenderer.drawInBatch(dps, f1, (float)0, -1, false, matrixStackIn.last().pose(), bufferIn, false, 0, packedLightIn);

        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(TestDummyEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/test_dummy.png");
    }
}

package net.bottomtextdanny.braincell.mod.minecraft_front_rendering;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class SpellRenderer <E extends Entity, M extends BCEntityModel<E>> extends EntityRenderer<E> implements RenderLayerParent<E, M>  {
    private static final ResourceLocation TEXTURES = null;
    private final M entityModel;
    protected final List<RenderLayer<E, M>> layerRenderers = Lists.newArrayList();

    public SpellRenderer(EntityRendererProvider.Context renderManagerIn, M entityModelIn) {
        super(renderManagerIn);
        this.entityModel = entityModelIn;
    }

    public final boolean addLayer(RenderLayer<E, M> layer) {
        return this.layerRenderers.add(layer);
    }

    public void render(E entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();

        float f1 = -Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot());
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());

        if (applyRotations()) {
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180 + f1));
        } 
	    matrixStackIn.scale(-1F, -1F, 1F);
	    VertexConsumer ivertexbuilder = bufferIn.getBuffer(getRenderType(entityIn));
	    matrixStackIn.scale(1F, 1F, 1F);
        preRender(entityIn, matrixStackIn, partialTicks);
        float ageInTicks = this.handleRotationFloat(entityIn, partialTicks);
        this.entityModel.setupAnim(entityIn, 0, 0, ageInTicks, f1, f2);
        this.entityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        for(RenderLayer<E, M> layerrenderer : this.layerRenderers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, 1.0F, 0.0F, partialTicks, ageInTicks, f1, f2);
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	    matrixStackIn.popPose();
    }

    public RenderType getRenderType(E entityIn) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entityIn));
    }

    @Override
    public M getModel() {
        return this.entityModel;
    }

    public void preRender(E entityIn, PoseStack matrixStackIn, float partialTicks) {
    }

    protected float handleRotationFloat(E livingBase, float partialTicks) {
        return (float)livingBase.tickCount + partialTicks;
    }

    protected boolean applyRotations() {
        return true;
    }

    public ResourceLocation getTextureLocation(E entity) {
        return TEXTURES;
    }
}

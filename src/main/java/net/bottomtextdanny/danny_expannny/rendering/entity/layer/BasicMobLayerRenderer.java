package net.bottomtextdanny.danny_expannny.rendering.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nullable;
import java.util.function.Function;

public class BasicMobLayerRenderer<T extends Mob, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public final Function<T, RenderType> type;
    public LightState lightState;

    public BasicMobLayerRenderer(MobRenderer<T, M> entityRendererIn, Function<T, RenderType> type) {
        super(entityRendererIn);
        this.type = type;
        this.lightState = LightState.NORMAL;
    }

    public BasicMobLayerRenderer<T, M> bright() {
        this.lightState = LightState.FULLBRIGHT;
        return this;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    	RenderType renderType = this.getRenderType(entitylivingbaseIn);
    	
    	if (renderType != null) {
		    VertexConsumer ivertexbuilder = bufferIn.getBuffer(renderType);
		    this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, this.lightState == LightState.FULLBRIGHT ? 15728640 : packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	    }
    }

    @Nullable
    public RenderType getRenderType(T entityIn) {
        return this.type.apply(entityIn);
    }

    enum LightState {
        FULLBRIGHT,
        NORMAL
    }
}

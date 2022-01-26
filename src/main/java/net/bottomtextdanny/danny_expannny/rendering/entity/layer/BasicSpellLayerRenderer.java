package net.bottomtextdanny.danny_expannny.rendering.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SpellEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class BasicSpellLayerRenderer<T extends SpellEntity, M extends BCEntityModel<T>> extends RenderLayer<T, M> {
    public final RenderType type;
    public LightState lightState;

    public BasicSpellLayerRenderer(SpellRenderer<T, M> entityRendererIn, RenderType type) {
        super(entityRendererIn);
        this.type = type;
        this.lightState = LightState.NORMAL;
    }

    public BasicSpellLayerRenderer<T, M> bright() {
        this.lightState = LightState.FULLBRIGHT;
        return this;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.getRenderType());
        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, this.lightState == LightState.FULLBRIGHT ? 15728640 : packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

    }

    public RenderType getRenderType() {
        return this.type;
    }

    enum LightState {
        FULLBRIGHT,
        NORMAL
    }
}

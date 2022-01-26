package net.bottomtextdanny.braincell.mod.minecraft_front_rendering;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class BCRenderTypes extends RenderType {
    private static final RenderStateShard.OutputStateShard FLAT_LIGHTING_TARGET = new RenderStateShard.OutputStateShard("outline_target", () -> {
        flatShadingInit();
    }, () -> {
        flatShadingClear();
    });

    private BCRenderTypes() {
        super(null, null, null, 0, false, false, null, null);
    }

    private static void flatShadingInit() {
        RenderSystem.setShaderColor(RenderSystem.getShaderColor()[0] + 1, RenderSystem.getShaderColor()[1] + 1, RenderSystem.getShaderColor()[2] + 1, RenderSystem.getShaderColor()[3]);
        RenderSystem._setShaderLights(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 0.0F, 0.0F));
    }

    private static void flatShadingClear() {
        Lighting.setupLevel(RenderSystem.getModelViewMatrix());
    }

    public static RenderType getFlatShading(ResourceLocation locationIn) {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setOutputState(FLAT_LIGHTING_TARGET).setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setCullState(NO_CULL).createCompositeState(true);
        return RenderType.create("flat_lighting", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, compositeState);
    }
}

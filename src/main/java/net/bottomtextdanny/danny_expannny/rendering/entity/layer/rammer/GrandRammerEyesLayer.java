package net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.GrandRammerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.GrandRammerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class GrandRammerEyesLayer extends EyesLayer<GrandRammerEntity, GrandRammerModel> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/grand_rammer_eyes.png"));

    public GrandRammerEyesLayer(RenderLayerParent<GrandRammerEntity, GrandRammerModel> rendererIn) {
        super(rendererIn);
    }

    public RenderType renderType() {
        return RENDER_TYPE;
    }
}
package net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.ChildRammerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.ChildRammerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class ChildRammerEyesLayer<T extends ChildRammerEntity> extends EyesLayer<T, ChildRammerModel<T>> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/child_rammer_eyes.png"));

    public ChildRammerEyesLayer(RenderLayerParent<T, ChildRammerModel<T>> rendererIn) {
        super(rendererIn);
    }

    public RenderType renderType() {
        return RENDER_TYPE;
    }
}

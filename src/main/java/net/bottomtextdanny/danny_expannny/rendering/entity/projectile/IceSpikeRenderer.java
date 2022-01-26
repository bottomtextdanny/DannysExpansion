package net.bottomtextdanny.danny_expannny.rendering.entity.projectile;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BCRenderTypes;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.projectiles.IceSpikeModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.IceSpike;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IceSpikeRenderer extends SpellRenderer<IceSpike, IceSpikeModel> {
    public ResourceLocation TEXTURE_PATH = new ResourceLocation(DannysExpansion.ID, "textures/entity/elemental/ice_spike.png");

    public IceSpikeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public IceSpikeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new IceSpikeModel());
    }

    @Override
    public RenderType getRenderType(IceSpike entityIn) {
        return BCRenderTypes.getFlatShading(getTextureLocation(entityIn));
    }

    @Override
    public ResourceLocation getTextureLocation(IceSpike entity) {
        return this.TEXTURE_PATH;
    }
}

package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles.IceSpikeModel;
import bottomtextdanny.dannys_expansion.content.entities.projectile.IceSpike;
import bottomtextdanny.braincell.mod.rendering.BCRenderTypes;
import bottomtextdanny.braincell.mod.rendering.SpellRenderer;
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

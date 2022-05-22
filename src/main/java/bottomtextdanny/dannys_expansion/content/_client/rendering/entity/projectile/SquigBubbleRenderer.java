package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles.SquigBubbleModel;
import bottomtextdanny.dannys_expansion.content.entities.projectile.SquigBubbleEntity;
import bottomtextdanny.braincell.mod.rendering.BCRenderTypes;
import bottomtextdanny.braincell.mod.rendering.SpellRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SquigBubbleRenderer extends SpellRenderer<SquigBubbleEntity, SquigBubbleModel> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/squig_bubble.png");

	public SquigBubbleRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public SquigBubbleRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SquigBubbleModel());
	}
	
	@Override
	public RenderType getRenderType(SquigBubbleEntity entityIn) {
		return BCRenderTypes.getFlatShading(getTextureLocation(entityIn));
	}
	
	@Override
	protected boolean applyRotations() {
		return false;
	}
	
	public ResourceLocation getTextureLocation(SquigBubbleEntity entity) {
		return TEXTURES;
	}
}

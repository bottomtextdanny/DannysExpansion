package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.BCRenderTypes;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.SquigBubbleModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SquigBubbleEntity;
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

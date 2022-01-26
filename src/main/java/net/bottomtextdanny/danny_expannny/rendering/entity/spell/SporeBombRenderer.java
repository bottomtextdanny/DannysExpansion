package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.SporeBombModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.spell.SporeBombItemLayer;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SporeBombEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SporeBombRenderer extends SpellRenderer<SporeBombEntity, SporeBombModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/item/spore_bomb.png");

    public SporeBombRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SporeBombRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SporeBombModel());
        addLayer(new SporeBombItemLayer(this));
    }
	
	@Override
	protected boolean applyRotations() {
		return false;
	}
	
	public ResourceLocation getTextureLocation(SporeBombEntity entity) {
        return TEXTURES;
    }

}

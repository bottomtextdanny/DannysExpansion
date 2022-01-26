package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.FoamshroomProjectileModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.FoamshroomProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FoamshroomProjectileRenderer extends SpellRenderer<FoamshroomProjectileEntity, FoamshroomProjectileModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/foamshroom_projectile.png");

    public FoamshroomProjectileRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public FoamshroomProjectileRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new FoamshroomProjectileModel());
    }
	
	@Override
	protected boolean applyRotations() {
		return false;
	}
	
	public ResourceLocation getTextureLocation(FoamshroomProjectileEntity entity) {
        return TEXTURES;
    }
}

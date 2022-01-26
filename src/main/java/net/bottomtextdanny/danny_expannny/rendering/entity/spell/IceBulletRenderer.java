package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.IceBulletModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.IceBulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IceBulletRenderer extends SpellRenderer<IceBulletEntity, IceBulletModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/ice_bullet.png");

    public IceBulletRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public IceBulletRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new IceBulletModel());
    }

    public ResourceLocation getTextureLocation(IceBulletEntity entity) {
        return TEXTURES;
    }
}

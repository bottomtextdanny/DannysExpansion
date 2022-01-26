package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.BarrenOrbModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.BarrenOrbEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BarrenOrbRenderer extends SpellRenderer<BarrenOrbEntity, BarrenOrbModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/barren_orb.png");

    public BarrenOrbRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public BarrenOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BarrenOrbModel());
    }

    @Override
    public RenderType getRenderType(BarrenOrbEntity entityIn) {
        return RenderType.entityTranslucentCull(getTextureLocation(entityIn));
    }

    public ResourceLocation getTextureLocation(BarrenOrbEntity entity) {
        return TEXTURES;
    }
}

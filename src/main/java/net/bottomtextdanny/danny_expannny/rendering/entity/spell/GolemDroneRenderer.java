package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.GolemDroneModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.GolemDroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GolemDroneRenderer extends SpellRenderer<GolemDroneEntity, GolemDroneModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/golem_drone.png");

    public GolemDroneRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public GolemDroneRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GolemDroneModel());
    }

    public ResourceLocation getTextureLocation(GolemDroneEntity entity) {
        return TEXTURES;
    }
}

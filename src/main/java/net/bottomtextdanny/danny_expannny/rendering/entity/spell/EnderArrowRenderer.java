package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.EnderArrowModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.EnderArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class EnderArrowRenderer extends SpellRenderer<EnderArrowEntity, EnderArrowModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/ender_arrow.png");

    public EnderArrowRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public EnderArrowRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new EnderArrowModel());
    }

    public ResourceLocation getTextureLocation(EnderArrowEntity entity) {
        return TEXTURES;
    }
}

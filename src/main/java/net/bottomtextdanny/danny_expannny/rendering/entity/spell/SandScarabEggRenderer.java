package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.SandScarabEggModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.SandScarabEggEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SandScarabEggRenderer extends SpellRenderer<SandScarabEggEntity, SandScarabEggModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/sand_scarab_egg.png");

    public SandScarabEggRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public SandScarabEggRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SandScarabEggModel());
    }

    public ResourceLocation getTextureLocation(SandScarabEggEntity entity) {
        return TEXTURES;
    }
}

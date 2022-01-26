package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.DeserticFangModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.DeserticFangEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DeserticFangRenderer extends SpellRenderer<DeserticFangEntity, DeserticFangModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/desertic_fang.png");

    public DeserticFangRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public DeserticFangRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new DeserticFangModel());
    }

    public ResourceLocation getTextureLocation(DeserticFangEntity entity) {
        return TEXTURES;
    }
}

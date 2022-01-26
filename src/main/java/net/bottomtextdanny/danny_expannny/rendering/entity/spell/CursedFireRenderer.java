package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.CursedFireModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.CursedFireEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class CursedFireRenderer extends SpellRenderer<CursedFireEntity, CursedFireModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/cursed_fire.png");

    public CursedFireRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public CursedFireRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CursedFireModel());
    }

    @Override
    protected int getBlockLightLevel(CursedFireEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    public ResourceLocation getTextureLocation(CursedFireEntity entity) {
        return TEXTURES;
    }
}

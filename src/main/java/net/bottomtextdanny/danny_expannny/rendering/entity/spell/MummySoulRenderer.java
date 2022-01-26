package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.MummySoulModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.BasicSpellLayerRenderer;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.MummySoulEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;

public class MummySoulRenderer extends SpellRenderer<MummySoulEntity, MummySoulModel> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/mummy_soul.png");
    private static final ResourceLocation TEXTURES_EYES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/mummy_soul_bright_layer.png");

    public MummySoulRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public MummySoulRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MummySoulModel());
        this.addLayer(new BasicSpellLayerRenderer<>(this, RenderType.eyes(TEXTURES_EYES)).bright());
    }

    @Override
    protected int getBlockLightLevel(MummySoulEntity entityIn, BlockPos partialTicks) {
        if (entityIn.getCaster() != null) {
            return entityIn.level.getBrightness(LightLayer.BLOCK, entityIn.getCaster().blockPosition());
        } else {
            return super.getBlockLightLevel(entityIn, partialTicks);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MummySoulEntity entity) {
        return TEXTURES;
    }
}

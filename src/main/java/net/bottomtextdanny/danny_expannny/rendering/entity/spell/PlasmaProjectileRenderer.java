package net.bottomtextdanny.danny_expannny.rendering.entity.spell;

import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.SpellRenderer;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.spells.PlasmaProjectileModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.PlasmaProjectileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class PlasmaProjectileRenderer extends SpellRenderer<PlasmaProjectileEntity, PlasmaProjectileModel> {
    private static final ResourceLocation TEXTURES_1 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_1.png");
    private static final ResourceLocation TEXTURES_2 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_2.png");
    private static final ResourceLocation TEXTURES_3 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_3.png");
    private static final ResourceLocation TEXTURES_4 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_4.png");
    private static final ResourceLocation TEXTURES_5 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_5.png");
    private static final ResourceLocation TEXTURES_6 = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/plasma_projectile/plasma_projectile_6.png");
    ResourceLocation[] sprites = {TEXTURES_1, TEXTURES_2, TEXTURES_3, TEXTURES_4, TEXTURES_5, TEXTURES_6};

    public PlasmaProjectileRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public PlasmaProjectileRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new PlasmaProjectileModel());
    }

    @Override
    public RenderType getRenderType(PlasmaProjectileEntity entityIn) {
        return RenderType.entityTranslucentCull(getTextureLocation(entityIn));
    }

    public ResourceLocation getTextureLocation(PlasmaProjectileEntity entity) {
        return this.sprites[entity.spriteTimmer];
    }

    protected int getBlockLightLevel(PlasmaProjectileEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

}

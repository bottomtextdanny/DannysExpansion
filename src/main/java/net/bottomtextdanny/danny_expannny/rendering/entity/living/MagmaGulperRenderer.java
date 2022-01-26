package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.MagmaGulperModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.MagmaGulperEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MagmaGulperRenderer extends MobRenderer<MagmaGulperEntity, MagmaGulperModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/magma_gulper/magma_gulper.png");

    public MagmaGulperRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public MagmaGulperRenderer(EntityRendererProvider.Context manager) {
        super(manager, new MagmaGulperModel(), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(MagmaGulperEntity entity) {
        return TEXTURES;
    }

    @Override
    protected int getBlockLightLevel(MagmaGulperEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

   }

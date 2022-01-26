package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.CursedSkullModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.CursedSkullEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CursedSkullRenderer extends MobRenderer<CursedSkullEntity, CursedSkullModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/cursed_skull/cursed_skull.png");

    public CursedSkullRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public CursedSkullRenderer(EntityRendererProvider.Context manager) {
        super(manager, new CursedSkullModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(CursedSkullEntity entity) {
        return TEXTURES;
    }

    }

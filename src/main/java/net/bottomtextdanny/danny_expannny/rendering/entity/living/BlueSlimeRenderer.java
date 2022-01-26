package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.BlueSlimeModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.BlueSlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BlueSlimeRenderer extends MobRenderer<BlueSlimeEntity, BlueSlimeModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/blue_slime.png");

    public BlueSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public BlueSlimeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BlueSlimeModel(), 0.4F);
    }

    @Override
    protected void renderNameTag(BlueSlimeEntity entityIn, Component displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
    }

    @Nullable
    @Override
    protected RenderType getRenderType(BlueSlimeEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        ResourceLocation resourcelocation = this.getTextureLocation(p_230496_1_);
        if (p_230496_3_) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (p_230496_2_) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
        }
    }

    public ResourceLocation getTextureLocation(BlueSlimeEntity entity) {
        return TEXTURES;
    }
}

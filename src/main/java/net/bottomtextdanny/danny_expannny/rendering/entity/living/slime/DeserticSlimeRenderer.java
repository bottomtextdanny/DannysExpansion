package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.VariantRenderer;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slime.desertic_slime.DeserticSlimeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class DeserticSlimeRenderer extends VariantRenderer<DeserticSlimeEntity, DannySlimeModel<DeserticSlimeEntity>> {
	public static DannySlimeModel<DeserticSlimeEntity> BASE_MODEL = new DannySlimeModel<>(12.0F, 9.0F);

	public DeserticSlimeRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

    public DeserticSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, BASE_MODEL, 0.4F);
    }
	
	@Override
	protected float getFlipDegrees(DeserticSlimeEntity entityLivingBaseIn) {
		return 0.0F;
	}
	
    @Nullable
    @Override
    protected RenderType getRenderType(DeserticSlimeEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entity));
    }
    
	@Override
	public ResourceLocation getDefaultEntityTexture(DeserticSlimeEntity entity) {
		return DeserticSlimeEntity.NORMAL_FORM.getRendering().getTexture(entity);
	}
}

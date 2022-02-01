package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.VariantRenderer;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.desertic_slime.DeserticSlime;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class DeserticSlimeRenderer extends VariantRenderer<DeserticSlime, DannySlimeModel<DeserticSlime>> {
	public static DannySlimeModel<DeserticSlime> BASE_MODEL = new DannySlimeModel<>(12.0F, 9.0F);

	public DeserticSlimeRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

    public DeserticSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, BASE_MODEL, 0.4F);
    }
	
	@Override
	protected float getFlipDegrees(DeserticSlime entityLivingBaseIn) {
		return 0.0F;
	}
	
    @Nullable
    @Override
    protected RenderType getRenderType(DeserticSlime entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entity));
    }
    
	@Override
	public ResourceLocation getDefaultEntityTexture(DeserticSlime entity) {
		return DeserticSlime.NORMAL_FORM.getRendering().getTexture(entity);
	}
}

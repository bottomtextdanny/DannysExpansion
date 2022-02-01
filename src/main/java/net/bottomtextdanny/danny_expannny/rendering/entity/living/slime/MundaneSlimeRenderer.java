package net.bottomtextdanny.danny_expannny.rendering.entity.living.slime;

import net.bottomtextdanny.danny_expannny.objects.entities.mob.slimes.mundane_slime.MundaneSlime;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.slimes.DannySlimeModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.VariantRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;



public class MundaneSlimeRenderer extends VariantRenderer<MundaneSlime, DannySlimeModel<MundaneSlime>> {
	public static DannySlimeModel<MundaneSlime> COMMON_MODEL = new DannySlimeModel<>(10, 8);
	public static DannySlimeModel<MundaneSlime> MEDIUM_MODEL = new DannySlimeModel<>(10, 8, 64, 64);
	public static DannySlimeModel<MundaneSlime> BIG_MODEL = new DannySlimeModel<>(16, 13, 64, 64);

	public MundaneSlimeRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

	public MundaneSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, COMMON_MODEL, 0.4F);
    }
	
	@Override
	protected float getFlipDegrees(MundaneSlime entityLivingBaseIn) {
		return 0.0F;
	}
	
	@Nullable
    @Override
    protected RenderType getRenderType(MundaneSlime entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entitySolid(getTextureLocation(entity));
    }

	@Override
	public ResourceLocation getDefaultEntityTexture(MundaneSlime entity) {
		return MundaneSlime.MEDIUM_FORM.getRendering().getTexture(entity);
	}
}
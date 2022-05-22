package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime;

import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.DannySlimeModel;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.MobVariantRenderer;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.mundane_slime.MundaneSlime;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;



public class MundaneSlimeRenderer extends MobVariantRenderer<MundaneSlime, DannySlimeModel<MundaneSlime>> {
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
        return RenderType.entityCutout(getTextureLocation(entity));
    }

	@Override
	public ResourceLocation getDefaultEntityTexture(MundaneSlime entity) {
		return MundaneSlime.MEDIUM_FORM.getRendering().getTexture(entity);
	}
}
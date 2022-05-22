package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob;

import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.MonstrousScorpionModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion.MonstrousScorpion;
import bottomtextdanny.dannys_expansion.content.entities.mob.monstrous_scorpion.MonstrousScorpionVariantRendering;
import bottomtextdanny.dannys_expansion.content._client.rendering.entity.BasicMobLayerRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MonstrousScorpionRenderer extends MobVariantRenderer<MonstrousScorpion, MonstrousScorpionModel> {
	public static final MonstrousScorpionModel COMMON_MODEL = new MonstrousScorpionModel();

	public MonstrousScorpionRenderer(Object manager) {
		this((EntityRendererProvider.Context) manager);
	}

    public MonstrousScorpionRenderer(EntityRendererProvider.Context manager) {
        super(manager, new MonstrousScorpionModel(), 0.35F);
	    addLayer(new BasicMobLayerRenderer<>(this, e -> {
		    if (!e.variableModule().isUpdated() || e.variableModule().getForm() == null || !e.variableModule().isUpdated()) return null;
	    	ResourceLocation loc = ((MonstrousScorpionVariantRendering)e.variableModule().getForm().getRendering()).getEyeTexturePath();
	    	if (loc == null) return null;
		    return RenderType.eyes(loc);
	    }).bright());
    }
    
	@Override
	public ResourceLocation getDefaultEntityTexture(MonstrousScorpion entity) {
		return MonstrousScorpion.SANDY_FORM.getRendering().getTexture(entity);
	}
}

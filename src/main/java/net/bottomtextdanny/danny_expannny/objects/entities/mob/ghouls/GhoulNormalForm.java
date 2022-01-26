package net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.GhoulEntity;
import net.bottomtextdanny.danny_expannny.rendering.entity.living.GhoulRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhoulNormalForm extends Form<GhoulEntity> {

	public GhoulNormalForm() {
		super();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected VariantRenderingData<GhoulEntity> createRenderingHandler() {
		return new SimpleVariantRenderingData<>(
				new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/ghoul.png"),
				GhoulRenderer.COMMON_MODEL
		);
	}
}

package net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls;

import net.bottomtextdanny.braincell.mod.entity.modules.variable.Form;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.SimpleVariantRenderingData;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.GhoulEntity;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls.GhoulSwampModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class GhoulSwampForm extends Form<GhoulEntity> {
	
	public GhoulSwampForm() {
		super();
	}

	@Override
	public void applyAttributeBonuses(GhoulEntity entityIn) {
		AttributeInstance inst = entityIn.getAttribute(Attributes.MAX_HEALTH);
		inst.setBaseValue(inst.getBaseValue() * 0.85);
		inst = entityIn.getAttribute(Attributes.ATTACK_DAMAGE);
		inst.setBaseValue(inst.getBaseValue() * 1.15);
	}

	@Nullable
	@Override
	public EntityDimensions boxSize() {
		return EntityDimensions.scalable(0.625F, 1.925F);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	protected VariantRenderingData<GhoulEntity> createRenderingHandler() {
		return new SimpleVariantRenderingData<>(
				new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/swamp.png"),
				new GhoulSwampModel()
		);
	}
}

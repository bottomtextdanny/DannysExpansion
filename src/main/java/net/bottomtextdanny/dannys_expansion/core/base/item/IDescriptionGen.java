package net.bottomtextdanny.dannys_expansion.core.base.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IDescriptionGen {

	@OnlyIn(Dist.CLIENT)
	String specifyDescriptionPath();

	@OnlyIn(Dist.CLIENT)
	String getGenerationDescription();
}

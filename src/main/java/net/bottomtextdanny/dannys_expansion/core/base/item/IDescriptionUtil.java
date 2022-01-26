package net.bottomtextdanny.dannys_expansion.core.base.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public interface IDescriptionUtil {
	@OnlyIn(Dist.CLIENT)
	String NEWLINE_KEY = "@nl<";

	@OnlyIn(Dist.CLIENT)
	default String[] getDividedDescription(String descriptionRaw) {
		return descriptionRaw.split(NEWLINE_KEY);
	}
}

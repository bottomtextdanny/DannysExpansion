package bottomtextdanny.dannys_expansion._util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public final class DescriptionUtil {
	@OnlyIn(Dist.CLIENT)
	public static final String NEWLINE_KEY = "@nl<";
	@OnlyIn(Dist.CLIENT)
	public static final String INPUT_KEY = "@in<";

	@OnlyIn(Dist.CLIENT)
	public static String[] getDividedDescription(String descriptionRaw) {
		return descriptionRaw.split(NEWLINE_KEY);
	}

	private DescriptionUtil() {}
}

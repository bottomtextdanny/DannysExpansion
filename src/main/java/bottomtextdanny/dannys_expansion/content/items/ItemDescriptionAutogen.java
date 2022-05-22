package bottomtextdanny.dannys_expansion.content.items;

import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ItemDescriptionAutogen extends ItemLike {

	@OnlyIn(Dist.CLIENT)
	String specifyDescriptionPath();

	@OnlyIn(Dist.CLIENT)
	String getGenerationDescription();
}

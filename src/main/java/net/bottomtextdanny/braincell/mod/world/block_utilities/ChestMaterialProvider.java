package net.bottomtextdanny.braincell.mod.world.block_utilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ChestMaterialProvider {
	int SINGLE_SHIFT = 0, LEFT_SHIFT = 1, RIGHT_SHIFT = 2;

	int getChestMaterialSlot();

	void setChestMaterialSlot(int newSlot);

	@OnlyIn(Dist.CLIENT)
	ResourceLocation[] chestTexturePaths();
}

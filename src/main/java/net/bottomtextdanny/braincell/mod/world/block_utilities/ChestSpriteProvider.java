package net.bottomtextdanny.braincell.mod.world.block_utilities;

import net.bottomtextdanny.dannys_expansion.core.Util.Pair;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public interface ChestSpriteProvider extends IForgeRegistryEntry<Block> {
	
	@OnlyIn(Dist.CLIENT)
	void genMaterials(TextureStitchEvent.Pre event);
	
	@OnlyIn(Dist.CLIENT)
	List<Pair<Integer, Object>> getMaterials();
	
	@OnlyIn(Dist.CLIENT)
	default Material getMaterial(int localListIndex) {
		return (Material) getMaterials().get(localListIndex).getValue();
	}
}

package net.bottomtextdanny.danny_expannny.accessory;

import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.world.level.ItemLike;

public interface StackAccSoftSave extends ItemLike {
	Object[] save();
	
	void retrieve(ObjectFetcher save);
}

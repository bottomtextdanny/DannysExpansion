package net.bottomtextdanny.dannys_expansion.core.base.item;

import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.objects.accessories.StackAccessory;

public interface StackAccessoryProvider {
	AccessoryKey<? extends StackAccessory> getKey();
}

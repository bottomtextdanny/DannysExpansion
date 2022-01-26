package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.minecraft.world.entity.player.Player;

public class KlifourTalismanAccessory extends CoreAccessory {
	
	public KlifourTalismanAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}
	
	@Override
	public String getGeneratedDescription() {
		return "Makes Klifour neutral towards the holder.";
	}
}

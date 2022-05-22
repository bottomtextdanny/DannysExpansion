package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
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

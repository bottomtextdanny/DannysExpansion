package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.minecraft.world.entity.player.Player;

public class AntitoxinAccessory extends CoreAccessory {
	private boolean poisonHurtSwitch0, poisonHurtSwitch1;
	
	public AntitoxinAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}
	
	public boolean switchThenGet() {
		if (this.poisonHurtSwitch0 = !this.poisonHurtSwitch0) {
			this.poisonHurtSwitch1 = false;
			return false;
		} else {
			return this.poisonHurtSwitch1 = !this.poisonHurtSwitch1;
		}
	}
	
	@Override
	public String getGeneratedDescription() {
		return "Increases the time gap of poison damage";
	}
}

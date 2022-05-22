package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.DEEvaluations;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import net.minecraft.world.entity.player.Player;

public class PiglinMedalAccessory extends CoreAccessory {
	static {
		DEEvaluations.COOL_WITH_PIGLINS.addTest(eval -> {
			if (PlayerHelper.hasAccessory(eval.getEvaluated(), PiglinMedalAccessory.class)) {
				eval.set(true);
				eval.stopEvaluation();
			}
		});
	}
	
	public PiglinMedalAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}
	
	@Override
	public String getGeneratedDescription() {
		return "Piglins wont attack the holder on sight or even opening chests";
	}
}

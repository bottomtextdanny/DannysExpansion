package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.world.entity.player.Player;

public class PiglinMedalAccessory extends CoreAccessory {
	static {
		Evaluations.COOL_WITH_PIGLINS.addTest(eval -> {
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

package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.world.entity.player.Player;

public class GelatinousDiplomacyAccessory extends CoreAccessory {
	static {
		Evaluations.COOL_WITH_SURFACE_SLIMES.addTest(eval -> {
			if (PlayerHelper.hasAccessory(eval.getEvaluated(), GelatinousDiplomacyAccessory.class)) {
				eval.set(true);
			}
			eval.stopEvaluation();
		});
	}
	
	public GelatinousDiplomacyAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}
	
	@Override
	public String getGeneratedDescription() {
		return "Makes surface slimes neutral towards the holder@nl<@nl<\\\"xd\\\"";
	}
}

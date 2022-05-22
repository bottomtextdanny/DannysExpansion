package bottomtextdanny.dannys_expansion.content.accessories;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.dannys_expansion.DEEvaluations;
import bottomtextdanny.braincell.mod.capability.player.accessory.AccessoryKey;
import net.minecraft.world.entity.player.Player;

public class GelatinousDiplomacyAccessory extends CoreAccessory {
	static {
		DEEvaluations.COOL_WITH_SURFACE_SLIMES.addTest(eval -> {
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
		return "Makes surface slimes neutral towards the holder.";
	}
}

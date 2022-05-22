package bottomtextdanny.dannys_expansion;

import bottomtextdanny.braincell.base.evaluation.Evaluation;
import net.minecraft.world.entity.player.Player;

public class DEEvaluations {
	
	public static Evaluation<Player, Boolean> COOL_WITH_SURFACE_SLIMES = Evaluation.create("Cool With Surface Slimes", player -> false);
	
	public static Evaluation<Player, Boolean> COOL_WITH_PIGLINS = Evaluation.create("Cool With Piglins", player -> false);

	public static void loadClass() {}
}

package net.bottomtextdanny.dannys_expansion.common;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.Evaluation;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EvaluationTuple;
import net.bottomtextdanny.danny_expannny.accessory.ModifierType;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.MiniAttribute;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.minecraft.world.entity.player.Player;

public class Evaluations {
	
	public static Evaluation<Player, Boolean> COOL_WITH_SURFACE_SLIMES = Evaluation.create("Cool With Surface Slimes", player -> false);
	
	public static Evaluation<Player, Boolean> COOL_WITH_PIGLINS = Evaluation.create("Cool With Piglins", player -> false);
	
	public static EvaluationTuple<Player, Float, Float> POISON_DAMAGE_MODIFIER = EvaluationTuple.create("Poison Damage Modifier", (player, aFloat) -> aFloat);
	
	public static Evaluation<Player, Float> EXTRA_JUMP_FORWARD = Evaluation.create("Extra Jump Forward", player -> 0.0F, list -> {
		list.add(eval -> {
			if (eval.getEvaluated().level.isClientSide) {
				eval.stopEvaluation();
			} else {
				float[] aFloat = {eval.get()};
				CapabilityHelper.get(eval.getEvaluated(), PlayerCapability.CAPABILITY).accessoryModule().getOrCreateAccessoryAttributeModifiers(ModifierType.MOVEMENT_SPEED_MLT).forEach(accessory -> aFloat[0] += accessory.getFinalSpeedMultiplier());

				eval.set(aFloat[0]);
			}
		});
		return list;
	});
	
	public static EvaluationTuple<PlayerAccessoryModule, ModifierType, Double> MODIFIER_VALUE = EvaluationTuple.create("Accessory Speed Multiplier", (accessoryModule, modifierType) -> 0.0, list -> {
		list.add(eval -> {
			double[] aDouble = {eval.get()};
			if (eval.getEvaluated1().baseValue == 1.0) {
				eval.getEvaluated0().getOrCreateAccessoryAttributeModifiers(eval.getEvaluated1()).forEach(accessory -> aDouble[0] += eval.getEvaluated1().valueGetter.apply(accessory) - 1.0);
			} else {
				eval.getEvaluated0().getOrCreateAccessoryAttributeModifiers(eval.getEvaluated1()).forEach(accessory -> aDouble[0] += eval.getEvaluated1().valueGetter.apply(accessory));
			}
			eval.set(aDouble[0]);
		});
		return list;
	});
	
	public static EvaluationTuple<PlayerAccessoryModule, MiniAttribute, Float> LESSER_MODIFIER_VALUE = EvaluationTuple.create("Accessory Lesser Attribute Modifier", (accessoryModule, miniAttribute) -> 0.0F, list -> {
		list.add(eval -> {
			float[] aFloat = {eval.get()};

			eval.getEvaluated0().getCoreAccessoryList().forEach(accessory -> {
				float localMod = accessory.getFinalLesserAttributeValue(eval.getEvaluated1());
				aFloat[0] += localMod;
			});

			eval.set(aFloat[0]);
		});
		return list;
	});
	
	public static void loadClass() {}
}

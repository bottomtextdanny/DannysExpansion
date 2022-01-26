package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import com.google.gson.JsonObject;

import java.util.Optional;

public class ApplyBonusFunction extends LTFunction<ApplyBonusFunction> {
	private Optional<Integer> extra = Optional.empty();
	private Optional<Float> probability = Optional.empty();
	private Optional<Float> bonusMultiplier = Optional.empty();
	private Formula formula;
	private final String enchantmentID;

	public ApplyBonusFunction(String enchantmentID) {
		super("minecraft:apply_bonus");
		this.enchantmentID = enchantmentID;
	}

	public ApplyBonusFunction formula(Formula formula) {
		this.formula = formula;
		return this;
	}

	public ApplyBonusFunction extra(int value) {
		this.extra = Optional.of(value);
		return this;
	}

	public ApplyBonusFunction probability(float value) {
		this.probability = Optional.of(value);
		return this;
	}

	public ApplyBonusFunction bonusMultiplier(float value) {
		this.bonusMultiplier = Optional.of(value);
		return this;
	}

	@Override
	public void bakeExtra() {
		JsonObject parameters = new JsonObject();

        this.jsonObj.add("enchantment", cString(this.enchantmentID));
        this.jsonObj.add("formula", cString("minecraft:" + this.formula.str()));

		if (this.extra.isPresent()) parameters.add("extra", cInt(this.extra.get()));
		if (this.probability.isPresent()) parameters.add("probability", cFloat(this.probability.get()));
		if (this.bonusMultiplier.isPresent()) parameters.add("bonusMultiplier", cFloat(this.bonusMultiplier.get()));

        this.jsonObj.add("parameters", parameters);
	}

	public enum Formula {
		BINOMIAL("binomial_with_bonus_count"),
		UNIFORM("uniform_bonus_count"),
		ORE_DROP("ore_drops");

		private final String str;

		Formula(String type) {
			this.str = type;
		}

		public String str() {
			return this.str;
		}
	}
}

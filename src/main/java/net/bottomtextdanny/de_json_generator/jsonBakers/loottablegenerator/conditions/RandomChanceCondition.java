package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions;

public class RandomChanceCondition extends LTCondition<RandomChanceCondition> {
	float chance;

    public RandomChanceCondition(float chance) {
        super("minecraft:random_chance");
        this.chance = chance;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("chance", cFloat(this.chance));
    }
}

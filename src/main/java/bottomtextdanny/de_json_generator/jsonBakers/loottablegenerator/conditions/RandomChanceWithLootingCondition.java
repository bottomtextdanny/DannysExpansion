package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions;

public class RandomChanceWithLootingCondition extends LTCondition<RandomChanceWithLootingCondition> {
	float chance;
	float lootingMultiplier;

    public RandomChanceWithLootingCondition(float chance, float lootingMultiplier) {
        super("minecraft:random_chance");
        this.chance = chance;
        this.lootingMultiplier = lootingMultiplier;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("chance", cFloat(this.chance));
        this.jsonObj.add("looting_multiplier", cFloat(this.lootingMultiplier));
    }
}

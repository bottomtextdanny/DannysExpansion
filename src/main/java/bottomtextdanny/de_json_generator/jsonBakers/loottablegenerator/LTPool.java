package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;
import bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public class LTPool extends JsonBaker<LTPool> {
    private final LinkedHashSet<LTFunction> functions = new LinkedHashSet<>(0);
    private final LinkedHashSet<LTCondition> conditions = new LinkedHashSet<>(0);
    private final LinkedHashSet<LTEntry> entries = new LinkedHashSet<>(0);
    private final MojValue rolls;
    private Optional<MojValue> bonus_rolls = Optional.empty();

    public LTPool(MojValue rolls) {
        this.rolls = rolls;
        this.jsonObj.add("rolls", rolls.get());
    }

    public LTPool bonusRolls(MojValue bonusRolls) {
        this.bonus_rolls = Optional.of(bonusRolls);
        return this;
    }

    public LTPool functions(LTFunction... aFunc) {
        this.functions.addAll(Arrays.asList(aFunc));
        return this;
    }

    public LTPool conditions(LTCondition... aCon) {
        this.conditions.addAll(Arrays.asList(aCon));
        return this;
    }

    public LTPool entries(LTEntry... aEntry) {
        this.entries.addAll(Arrays.asList(aEntry));
        return this;
    }

    @Override
    public LTPool bake() {
        this.jsonObj.add("rolls", this.rolls.get());

        if (this.bonus_rolls.isPresent()) this.jsonObj.add("bonus_rolls", this.bonus_rolls.get().get());

        if (!this.entries.isEmpty()) this.jsonObj.add("entries", cObjectCollectionBake(this.entries));

        if (!this.functions.isEmpty()) this.jsonObj.add("functions", cObjectCollectionBake(this.functions));

        if (!this.conditions.isEmpty()) this.jsonObj.add("conditions", cObjectCollection(this.conditions));

        return this;
    }

    public LinkedHashSet<LTEntry> getEntries() {
        return this.entries;
    }

    public LinkedHashSet<LTFunction> getFunctions() {
        return this.functions;
    }

    public LinkedHashSet<LTCondition> getConditions() {
        return this.conditions;
    }

}

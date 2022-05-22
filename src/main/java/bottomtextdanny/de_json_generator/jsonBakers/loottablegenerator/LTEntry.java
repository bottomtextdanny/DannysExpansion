package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public class LTEntry extends JsonBaker<LTEntry> {
    private final LinkedHashSet<LTFunction> functions = new LinkedHashSet<>(0);
    private final LinkedHashSet<LTCondition> conditions = new LinkedHashSet<>(0);
    private final LinkedHashSet<LTEntry> children = new LinkedHashSet<>(0);
    EntryType type = EntryType.ITEM;
    Optional<String> name = Optional.empty();
    Optional<Integer> weight = Optional.empty();

    public LTEntry() {
    }

    public LTEntry itemID(String name) {
        this.name = Optional.of(name);
        return this;
    }

    public LTEntry type(EntryType type) {
        this.type = type;
        return this;
    }

    public LTEntry weight(int weight) {
        this.weight = Optional.of(weight);
        return this;
    }

    public LTEntry functions(LTFunction... aFunc) {
        this.functions.addAll(Arrays.asList(aFunc));
        return this;
    }

    public LTEntry conditions(LTCondition... aCon) {
        this.conditions.addAll(Arrays.asList(aCon));
        return this;
    }

    public LTEntry children(LTEntry... aEntry) {
        this.children.addAll(Arrays.asList(aEntry));
        return this;
    }

    @Override
    public LTEntry bake() {
        this.jsonObj.add("type", cString("minecraft:" + this.type.str()));
        if (!this.functions.isEmpty()) this.jsonObj.add("functions", cObjectCollectionBake(this.functions));
        if (!this.conditions.isEmpty()) this.jsonObj.add("conditions", cObjectCollectionBake(this.conditions));
        if (this.name.isPresent()) this.jsonObj.add("name", cString(this.name.get()));
        if (this.weight.isPresent()) this.jsonObj.add("weight", cInt(this.weight.get()));
        if (!this.children.isEmpty()) this.jsonObj.add("children", cObjectCollectionBake(this.children));
        return this;
    }
}

package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;

import java.util.Arrays;
import java.util.LinkedHashSet;

public abstract class LTFunction<T extends LTFunction<T>> extends JsonBaker<T> {
    private final LinkedHashSet<LTCondition<?>> conditions = new LinkedHashSet<>(0);
    public final String functionName;

    public LTFunction(String name) {
        this.functionName = name;

    }

    public abstract void bakeExtra();

    public LTFunction<T> conditions(LTCondition<?>... aCon) {
        this.conditions.addAll(Arrays.asList(aCon));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T bake() {
        this.jsonObj.add("function", cString(this.functionName));
        bakeExtra();
        if (!this.conditions.isEmpty()) this.jsonObj.add("conditions", cObjectCollection(this.conditions));
        return (T) this;
    }
}

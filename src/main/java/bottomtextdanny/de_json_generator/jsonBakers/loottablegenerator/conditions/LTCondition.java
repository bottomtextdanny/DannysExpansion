package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

public abstract class LTCondition<T extends LTCondition<T>> extends JsonBaker<T> {
    public String conditionID;

    public LTCondition(String conditionID) {
        this.conditionID = conditionID;
    }

    public abstract void bakeExtra();

    @SuppressWarnings("unchecked")
    @Override
    public T bake() {
        this.jsonObj.add("condition", cString(this.conditionID));
        bakeExtra();
        return (T) this;
    }
}
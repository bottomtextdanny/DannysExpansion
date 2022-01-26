package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item;

import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;

public class MatchToolCondition extends LTCondition<MatchToolCondition> {
    private final TLItemPred predicate;

    public MatchToolCondition(TLItemPred predicate) {
        super("match_tool");
       this.predicate = predicate;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("predicate", this.predicate.bake().getAsJson());
    }
}

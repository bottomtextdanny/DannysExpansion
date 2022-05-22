package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.block;

import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;

public class BlockstateCondition extends LTCondition<BlockstateCondition> {
    private final JsonMap propertyMap;
    private final String blockID;

    public BlockstateCondition(String blockID, JsonMap propertyMap) {
        super("block_state_property");
        this.propertyMap = propertyMap;
        this.blockID = blockID;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("block", cString(this.blockID));
        this.jsonObj.add("properties", this.propertyMap.parse().decode());
    }
}

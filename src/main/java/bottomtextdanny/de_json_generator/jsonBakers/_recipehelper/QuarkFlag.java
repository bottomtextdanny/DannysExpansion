package bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;

public class QuarkFlag extends LTCondition<QuarkFlag> {
	private final String flag;

	public QuarkFlag(String typeID, String flag) {
		super(typeID);
		this.flag = flag;
	}

	@Override
	public void bakeExtra() {
        this.jsonObj.add("entry", cString(this.flag));
	}
}

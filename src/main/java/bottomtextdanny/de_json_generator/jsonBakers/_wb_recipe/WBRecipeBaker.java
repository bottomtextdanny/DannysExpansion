package bottomtextdanny.de_json_generator.jsonBakers._wb_recipe;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WBRecipeBaker extends JsonBaker<WBRecipeBaker> {
	public final List<WBObjectBaker> ingredients = new LinkedList<>();
	private int index;
	private LazyType type;
	private WBObjectBaker result;
	
	public WBRecipeBaker() {
	}

	public WBRecipeBaker type(LazyType type) {
		this.type = type;
		return this;
	}

	public WBRecipeBaker index(int index) {
		this.index = index;
		return this;
	}
	
	public WBRecipeBaker add(WBObjectBaker ingredient) {
        this.ingredients.add(ingredient);
		return this;
	}
	
	public WBRecipeBaker add(WBObjectBaker... ingredientArr) {
		Collections.addAll(this.ingredients, ingredientArr);
		return this;
	}
	
	public WBRecipeBaker result(WBObjectBaker result) {
		this.result = result;
		return this;
	}
	
	public WBObjectBaker getResult() {
		return this.result;
	}
	
	@Override
	public WBRecipeBaker bake() {
		this.jsonObj.add("type", cString(this.type.key));
        this.jsonObj.add("index", cInt(this.index));
        this.jsonObj.add("ingredients", cObjectCollectionBake(this.ingredients));
        this.jsonObj.add("result", this.result.bake().decode());
		return this;
	}
}

package bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeShapelessMaster extends RecipeMaster<RecipeShapelessMaster> {
	private final LinkedHashSet<String> ingredients = new LinkedHashSet<>();
	
	public RecipeShapelessMaster() {
	}
	
	public RecipeShapelessMaster ingredients(String... newIngredientsIDs) {
        this.ingredients.addAll(Arrays.asList(newIngredientsIDs));
		return this;
	}
	
	@Override
	public RecipeShapelessMaster bake() {

        this.jsonObj.add("type", cString("minecraft:crafting_shapeless"));
		if (this.group.isPresent()) this.jsonObj.add("group", cString(this.group.get()));
		
		Set<JsonObject> tempSet = new LinkedHashSet<>(0);
        this.ingredients.forEach(element -> {
			JsonObject itemObj = new JsonObject();
			itemObj.add("item", cString(element));
			tempSet.add(itemObj);
			
		});
        this.jsonObj.add("ingredients", cObjectJsonCollection(tempSet));

        this.jsonObj.add("result", this.result.bake().decode());
		if (!this.conditions.isEmpty()) this.jsonObj.add("conditions", cObjectCollectionBake(this.conditions));
		return this;
	}
}

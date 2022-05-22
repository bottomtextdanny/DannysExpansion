package bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import com.google.gson.JsonObject;
import bottomtextdanny.braincell.base.pair.Pair;

public class RecipeSmeltingMaster extends RecipeMaster<RecipeSmeltingMaster> {
	private String result;
	private Pair<String, String> ingredient;
	private float experience;
	private int cookingTime;
	
	public RecipeSmeltingMaster() {
	}
	
	public RecipeSmeltingMaster ingredient(String type, String newIngredientID) {
		this.ingredient = Pair.of(type, newIngredientID);
		return this;
	}
	
	public RecipeSmeltingMaster ingredient( String newIngredientID) {
		this.ingredient = Pair.of("item", newIngredientID);
		return this;
	}
	
	public RecipeSmeltingMaster result(String resultID) {
		this.result = resultID;
		return this;
	}
	
	public RecipeSmeltingMaster experience(float experienceAmount) {
		this.experience = experienceAmount;
		return this;
	}
	
	public RecipeSmeltingMaster cookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
		return this;
	}
	
	@Deprecated
	@Override
	public RecipeSmeltingMaster result(SRResult result) {
		return super.result(result);
	}
	
	@Override
	public RecipeSmeltingMaster bake() {
		JsonObject ingredientObject = new JsonObject();

        this.jsonObj.add("type", cString("minecraft:smelting"));
		if (this.group.isPresent())
            this.jsonObj.add("group", cString(this.group.get()));
		ingredientObject.add(this.ingredient.left(), cString(this.ingredient.right()));
        this.jsonObj.add("ingredient", ingredientObject);
        this.jsonObj.add("result", cString(this.result));
		if (this.experience > 0.0F)
            this.jsonObj.add("experience", cFloat(this.experience));
        this.jsonObj.add("cookingtime", cInt(this.cookingTime));
		if (!this.conditions.isEmpty())
            this.jsonObj.add("conditions", cObjectCollectionBake(this.conditions));
		return this;
	}
}

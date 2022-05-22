package bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class RecipeShapedMaster extends RecipeMaster<RecipeShapedMaster> {
	private final LinkedHashSet<SRKey> keys = new LinkedHashSet<>(0);
	private final List<String> craftStr = new ArrayList<>(0);

	public RecipeShapedMaster(String... craft) {
		this.craftStr.addAll(Arrays.asList(craft));
	}

	public RecipeShapedMaster keys(SRKey... aKey) {
        this.keys.addAll(Arrays.asList(aKey));
		return this;
	}
	
	@Override
	public RecipeShapedMaster bake() {
		JsonObject key = new JsonObject();
        this.keys.forEach(kObj -> key.add(String.valueOf(kObj.getKey()), kObj.decode()));
        this.jsonObj.add("type", cString("minecraft:crafting_shaped"));
		if (this.group.isPresent()) this.jsonObj.add("group", cString(this.group.get()));

        this.jsonObj.add("pattern", cStringCollection(this.craftStr));
        this.jsonObj.add("key", key);
        this.jsonObj.add("result", this.result.bake().decode());
		if (!this.conditions.isEmpty()) this.jsonObj.add("conditions", cObjectCollectionBake(this.conditions));
		return this;
	}
}

package bottomtextdanny.de_json_generator.jsonBakers._wb_recipe;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;

public class WBObjectBaker extends JsonBaker<WBObjectBaker> {
	private final String type, itemID;
	private final JsonMap extraMetadata;
	
	public WBObjectBaker(String type, String itemID, JsonMap extraMeta) {
		this.type = type;
		this.itemID = itemID;
		this.extraMetadata = extraMeta;
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public WBObjectBaker bake() {
        this.jsonObj.add(this.type, cString(this.itemID));
		this.extraMetadata.getMap().forEach((id, element) -> this.jsonObj.add(id, element));
		return this;
	}
}

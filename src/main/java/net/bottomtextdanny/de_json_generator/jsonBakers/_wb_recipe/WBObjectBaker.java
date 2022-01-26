package net.bottomtextdanny.de_json_generator.jsonBakers._wb_recipe;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

public class WBObjectBaker extends JsonBaker<WBObjectBaker> {
	private final String type, itemID;
	private final int count;
	
	public WBObjectBaker(String type, String itemID, int count) {
		this.type = type;
		this.itemID = itemID;
		this.count = count;
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public WBObjectBaker bake() {
        this.jsonObj.add(this.type, cString(this.itemID));
        this.jsonObj.add("count", cInt(this.count));
		return this;
	}
}

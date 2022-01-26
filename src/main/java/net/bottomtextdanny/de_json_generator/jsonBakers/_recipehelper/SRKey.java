package net.bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

public class SRKey extends JsonBaker<SRKey> {
	private final char key;
	private final String itemID;
	private final String type;

	public SRKey(char key, String type, String itemID) {
		this.key = key;
		this.type = type;
		this.itemID = itemID;
	}

	public SRKey(char key, String itemID) {
		this.key = key;
		this.type = "item";
		this.itemID = itemID;
	}

	public char getKey() {
		return this.key;
	}

	@Override
	public SRKey bake() {
        this.jsonObj.add(this.type, cString(this.itemID));
		return this;
	}
}

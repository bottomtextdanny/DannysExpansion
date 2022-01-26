package net.bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonDecoder;

import java.util.Optional;

public class SRResult extends JsonDecoder {
	private final String itemID;
	private Optional<Integer> count = Optional.empty();

	public SRResult(String itemID) {
		this.itemID = itemID;
	}

	public String getItemID() {
		return this.itemID;
	}

	public SRResult count(int value) {
		this.count = Optional.of(value);
		return this;
	}

	public SRResult bake() {
        this.jsonObj.add("item", cString(this.itemID));
		if (this.count.isPresent()) this.jsonObj.add("count", cInt(this.count.get()));
		return this;
	}
}

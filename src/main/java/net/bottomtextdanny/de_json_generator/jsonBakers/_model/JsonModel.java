package net.bottomtextdanny.de_json_generator.jsonBakers._model;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import net.bottomtextdanny.de_json_generator.jsonBakers.JsonMap;

public class JsonModel extends JsonBaker<JsonModel> {
	private final String parent;
	private final JsonMap textureMap;
	
	public JsonModel(String parent, JsonMap textureMap) {
		this.parent = parent;
		this.textureMap = textureMap;
	}
	
	public JsonMap getTextureMap() {
		return this.textureMap;
	}
	
	@Override
	public JsonModel bake() {
		if (!"".equals(this.parent)) {
            this.jsonObj.add("parent", cString(this.parent));
		}
		if (!this.textureMap.getMap().isEmpty())
            this.jsonObj.add("textures", this.textureMap.parse().decode());
		return this;
	}
}

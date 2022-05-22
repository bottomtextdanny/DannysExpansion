package bottomtextdanny.de_json_generator.jsonBakers;

import com.google.gson.JsonElement;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonMap extends JsonDecoder {
    private final Map<String, JsonElement> map = new LinkedHashMap<>(0);

    private JsonMap() {
    }

    public static JsonMap create() {
        return new JsonMap();
    }

    public JsonMap put(String key, JsonElement value) {
        this.map.put(key, value);
        return this;
    }

	public JsonMap put(String key, String value) {
        this.map.put(key, cString(value));
		return this;
	}

	public JsonMap put(String key, float value) {
        this.map.put(key, cFloat(value));
		return this;
	}

	public JsonMap put(String key, int value) {
        this.map.put(key, cInt(value));
		return this;
	}
	
	public JsonMap put(String key, boolean value) {
        this.map.put(key, cBool(value));
		return this;
	}
	
	public Map<String, JsonElement> getMap() {
		return this.map;
	}


	
	public JsonMap parse() {
        this.map.forEach((str, element) -> {
            this.jsonObj.add(str, element);
        });
        return this;
    }
}

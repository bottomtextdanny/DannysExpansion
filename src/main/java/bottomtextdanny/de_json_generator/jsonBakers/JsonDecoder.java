package bottomtextdanny.de_json_generator.jsonBakers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class JsonDecoder implements JsonUtilsBackend {
    protected JsonObject jsonObj = new JsonObject();

    public JsonObject getAsJson() {
        return this.jsonObj;
    }

    public JsonElement decode() {
        return this.jsonObj;
    }

    public String jsonString() {
        return JsonUtilsMiddleEnd.parse(this.jsonObj);
    }

    public void print() {
    	System.out.print(JsonUtilsMiddleEnd.parse(this.jsonObj));
	}
}

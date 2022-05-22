package bottomtextdanny.de_json_generator.jsonBakers.mojValue;

import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsBackend;
import com.google.gson.JsonElement;

public class MojValue implements JsonUtilsBackend {
    protected JsonElement element;

    public MojValue(JsonElement element) {
        this.element = element;
    }

    public JsonElement get() {
        return this.element;
    }
}

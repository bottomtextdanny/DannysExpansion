package net.bottomtextdanny.de_json_generator.jsonBakers.mojValue;

import com.google.gson.JsonElement;
import net.bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsBackend;

public class MojValue implements JsonUtilsBackend {
    protected JsonElement element;

    public MojValue(JsonElement element) {
        this.element = element;
    }

    public JsonElement get() {
        return this.element;
    }
}

package bottomtextdanny.de_json_generator.jsonBakers;

import bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;
import com.google.gson.*;

import java.util.Collection;

public interface JsonUtilsMiddleEnd {

    static String parse(JsonElement obj) {
        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(obj);
    }

    static MojValue constant(float constant) {
        return new MojValue(new JsonPrimitive(constant));
    }

    static MojValue uniform(float min, float max) {
        JsonObject object = new JsonObject();
        object.add("val1", new JsonPrimitive(min));
        object.add("val2", new JsonPrimitive(max));
        object.add("type", cString("minecraft:uniform"));
        return new MojValue(object);
    }

    static JsonPrimitive cString(String str) {
        return new JsonPrimitive(str);
    }

    static JsonPrimitive cFloat(float f) {
        return new JsonPrimitive(f);
    }

    static JsonPrimitive cInt(int i) {
        return new JsonPrimitive(i);
    }
	
	static JsonArray cStringArray(String... aObj) {
		JsonArray objectArray = new JsonArray();
		
		for (String dec : aObj) {
			objectArray.add(dec);
		}
		return objectArray;
	}
	
	static JsonArray cFloatArray(float... aObj) {
		JsonArray objectArray = new JsonArray();
		
		for (float dec : aObj) {
			objectArray.add(cFloat(dec));
		}
		return objectArray;
	}
	
	static <T extends String> JsonArray cStringCollection(Collection<T> objCol) {
		JsonArray objectArray = new JsonArray();
		
		objCol.forEach(obj -> objectArray.add(obj));
		return objectArray;
	}
	
	static <T extends JsonDecoder> JsonArray cObjectArray(T... aObj) {
		JsonArray objectArray = new JsonArray();
		
		for (JsonDecoder dec : aObj) {
			objectArray.add(dec.decode());
		}
		
		return objectArray;
	}
	
	static <T extends JsonDecoder> JsonArray cObjectCollection(Collection<T> objCol) {
		JsonArray objectArray = new JsonArray();
		
		objCol.forEach(obj -> objectArray.add(obj.decode()));
		return objectArray;
	}
	
	static JsonArray cObjectCollectionRaw(Collection<JsonObject> objCol) {
		JsonArray objectArray = new JsonArray();
		
		objCol.forEach(obj -> objectArray.add(obj));
		return objectArray;
	}
	
	static <T extends JsonBaker> JsonArray cObjectArrayBake(T... aObj) {
		JsonArray objectArray = new JsonArray();
		
		
		for (JsonBaker dec : aObj) {
			objectArray.add(dec.bake().decode());
		}
		
		return objectArray;
	}
	
	static <T extends JsonBaker> JsonArray cObjectCollectionBake(Collection<T> objCol) {
		JsonArray objectArray = new JsonArray();
		
		objCol.forEach(obj -> {
			objectArray.add(obj.bake().decode());
		});
		return objectArray;
	}
}

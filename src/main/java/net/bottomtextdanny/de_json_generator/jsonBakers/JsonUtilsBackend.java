package net.bottomtextdanny.de_json_generator.jsonBakers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;

import java.util.Collection;

public interface JsonUtilsBackend {

    default MojValue constant(float constant) {
        return new MojValue(new JsonPrimitive(constant));
    }

    default MojValue uniform(float min, float max) {
        JsonObject object = new JsonObject();
        object.add("min", new JsonPrimitive(min));
        object.add("max", new JsonPrimitive(max));
        return new MojValue(object);
    }

    default JsonPrimitive cBool(boolean b) {
        return new JsonPrimitive(b);
    }

    default JsonPrimitive cString(String str) {
        return new JsonPrimitive(str);
    }

    default JsonPrimitive cFloat(float f) {
        return new JsonPrimitive(f);
    }

    default JsonPrimitive cInt(int i) {
        return new JsonPrimitive(i);
    }

    default JsonArray cStringArray(String... aObj) {
        JsonArray objectArray = new JsonArray();

        for (String dec : aObj) {
            objectArray.add(dec);
        }
        return objectArray;
    }
	
	default JsonArray cFloatArray(float... aObj) {
		JsonArray objectArray = new JsonArray();
		
		for (float dec : aObj) {
			objectArray.add(cFloat(dec));
		}
		return objectArray;
	}

    default <T extends String> JsonArray cStringCollection(Collection<T> objCol) {
        JsonArray objectArray = new JsonArray();

        objCol.forEach(obj -> objectArray.add(obj));
        return objectArray;
    }

    default <T extends JsonDecoder> JsonArray cObjectArray(T... aObj) {
        JsonArray objectArray = new JsonArray();

        for (JsonDecoder dec : aObj) {
            objectArray.add(dec.decode());
        }

        return objectArray;
    }
	
	default <T extends JsonObject> JsonArray cObjectJsonArray(T... aObj) {
		JsonArray objectArray = new JsonArray();
		
		for (JsonObject dec : aObj) {
			objectArray.add(dec);
		}
		
		return objectArray;
	}
	default <T extends JsonObject> JsonArray cObjectJsonCollection(Collection<T> objCol) {
		JsonArray objectArray = new JsonArray();
		
		objCol.forEach(obj -> objectArray.add(obj));
		return objectArray;
	}

    default <T extends JsonDecoder> JsonArray cObjectCollection(Collection<T> objCol) {
        JsonArray objectArray = new JsonArray();

        objCol.forEach(obj -> objectArray.add(obj.decode()));
        return objectArray;
    }

    default <T extends JsonBaker> JsonArray cObjectArrayBake(T... aObj) {
        JsonArray objectArray = new JsonArray();


        for (JsonBaker dec : aObj) {
			objectArray.add(dec.bake().decode());
		}

        return objectArray;
    }

    default <T extends JsonBaker> JsonArray cObjectCollectionBake(Collection<T> objCol) {
        JsonArray objectArray = new JsonArray();

        objCol.forEach(obj -> {
        	objectArray.add(obj.bake().decode());
        });
        return objectArray;
    }
}

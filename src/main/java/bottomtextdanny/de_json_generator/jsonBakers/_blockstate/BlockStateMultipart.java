package bottomtextdanny.de_json_generator.jsonBakers._blockstate;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class BlockStateMultipart extends JsonBaker<BlockStateMultipart> {
	private JsonMap whenMap;
	private  Optional<Integer>
		x = Optional.empty(),
		y = Optional.empty();
	private boolean uvLock;
	private Optional<String> modelPath = Optional.empty();
	private final List<StateModel> models = new LinkedList<>();
	
	public BlockStateMultipart() {}
	
	public BlockStateMultipart when(JsonMap parameters) {
		this.whenMap = parameters;
		return this;
	}
	
	public BlockStateMultipart path(String path) {
        this.modelPath = Optional.of(path);
		return this;
	}
	
	public BlockStateMultipart addModel(StateModel variant) {
        this.models.add(variant);
		return this;
	}
	
	public BlockStateMultipart addModels(StateModel... variants) {
		Collections.addAll(this.models, variants);
		return this;
	}
	
	public BlockStateMultipart xRot(int rotation) {
        this.x = Optional.of(rotation);
		return this;
	}
	
	public BlockStateMultipart yRot(int rotation) {
        this.y = Optional.of(rotation);
		return this;
	}
	
	public BlockStateMultipart uvLock() {
        this.uvLock = true;
		return this;
	}
	
	@Override
	public BlockStateMultipart bake() {
		JsonElement whenObj = new JsonObject();
		if (!this.whenMap.getMap().isEmpty()) {
			if (this.whenMap.getMap().size() == 1) {
				for (Map.Entry<String, JsonElement> entry : this.whenMap.getMap().entrySet()) {
					((JsonObject)whenObj).add(entry.getKey(), entry.getValue());
					break;
				}
			} else {
				whenObj = this.whenMap.parse().decode();
			}
			
		}
        this.jsonObj.add("when", whenObj);
		
		if (this.modelPath.isPresent()) {
			JsonObject applyObj = new JsonObject();
			applyObj.add("model", cString(this.modelPath.get()));
			
			if (this.x.isPresent()) {
				applyObj.add("x", cInt(this.x.get()));
			}
			
			if (this.y.isPresent()) {
				applyObj.add("y", cInt(this.y.get()));
			}
			
			if (this.uvLock) {
				applyObj.add("uvlock", cBool(true));
			}

            this.jsonObj.add("apply", applyObj);
		} else if (!this.models.isEmpty()) {
			JsonArray arrayVer = new JsonArray();
			
			for (StateModel model : this.models) {
				arrayVer.add(model.bake().decode());
			}

            this.jsonObj.add("apply", arrayVer);
		}
		
		return this;
	}
}

package bottomtextdanny.de_json_generator.jsonBakers._blockstate;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BlockStateVariant extends JsonBaker<BlockStateVariant> {
	public final String parametersAsKey;
	private JsonArray arrayModelVersion;
	private  Optional<Integer>
		x = Optional.empty(),
		y = Optional.empty();
	private boolean uvLock;
	private Optional<String> modelPath = Optional.empty();
	private final List<StateModel> models = new LinkedList<>();
	
	public BlockStateVariant(JsonMap mapParams) {
		StringBuilder keyBuilder = new StringBuilder();
		
		int[] counter = {0};
		mapParams.getMap().forEach((str, element) -> {
			keyBuilder.append(str);
			keyBuilder.append('=');
			keyBuilder.append(element.getAsString());
			counter[0]++;
			if (counter[0] < mapParams.getMap().size()) {
				keyBuilder.append(",");
			}
		});

        this.parametersAsKey = keyBuilder.toString();
	}
	
	public BlockStateVariant() {
        this.parametersAsKey = "";
	}
	
	public BlockStateVariant path(String path) {
        this.modelPath = Optional.of(path);
		return this;
	}
	
	public BlockStateVariant addModel(StateModel variant) {
        this.models.add(variant);
		return this;
	}
	
	public BlockStateVariant addModels(StateModel... variants) {
		Collections.addAll(this.models, variants);
		return this;
	}
	
	public BlockStateVariant xRot(int rotation) {
        this.x = Optional.of(rotation);
		return this;
	}
	
	public BlockStateVariant yRot(int rotation) {
        this.y = Optional.of(rotation);
		return this;
	}
	
	public BlockStateVariant uvLock() {
        this.uvLock = true;
		return this;
	}
	
	@Override
	public BlockStateVariant bake() {
		if (this.modelPath.isPresent()) {
            this.jsonObj.add("model", cString(this.modelPath.get()));
			
			if (this.x.isPresent()) {
                this.jsonObj.add("x", cInt(this.x.get()));
			}
			
			if (this.y.isPresent()) {
                this.jsonObj.add("y", cInt(this.y.get()));
			}
			
			if (this.uvLock) {
                this.jsonObj.add("uvlock", cBool(true));
			}
			
		} else if (!this.models.isEmpty()) {
			JsonArray arrayVer = new JsonArray();
			
			for (StateModel model : this.models) {
				arrayVer.add(model.bake().decode());
			}
			
			this.arrayModelVersion = arrayVer;
		}
		
		return this;
	}
	
	@Override
	public JsonElement decode() {
		if (this.arrayModelVersion != null)
			return this.arrayModelVersion;
		return super.decode();
	}
}

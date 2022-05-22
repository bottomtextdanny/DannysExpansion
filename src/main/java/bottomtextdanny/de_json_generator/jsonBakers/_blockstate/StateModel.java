package bottomtextdanny.de_json_generator.jsonBakers._blockstate;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class StateModel extends JsonBaker<StateModel> {
	private Optional<Integer>
		weight = Optional.empty(),
		x = Optional.empty(),
		y = Optional.empty();
	private boolean uvLock;
	private Optional<String> modelPath = Optional.empty();
	private final List<StateModel> models = new LinkedList<>();
	
	public StateModel() {
	}
	
	public StateModel path(String path) {
        this.modelPath = Optional.of(path);
		return this;
	}
	
	public StateModel weight(int weight) {
		this.weight = Optional.of(weight);
		return this;
	}
	
	public StateModel xRot(int rotation) {
        this.x = Optional.of(rotation);
		return this;
	}
	
	public StateModel yRot(int rotation) {
        this.y = Optional.of(rotation);
		return this;
	}
	
	public StateModel uvLock() {
        this.uvLock = true;
		return this;
	}
	
	@Override
	public StateModel bake() {
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
		
		if (this.weight.isPresent()) {
            this.jsonObj.add("weight", cInt(this.weight.get()));
		}
		
		return this;
	}
}

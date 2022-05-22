package bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemModel<T extends ItemModel<T>> extends JsonBaker<T> {
	public static final String
		THIRD_PERSON_RIGHT_HAND = "thirdperson_righthand",
		THIRD_PERSON_LEFT_HAND = "thirdperson_lefthand",
		FIRST_PERSON_RIGHT_HAND = "firstperson_righthand",
		FIRST_PERSON_LEFT_HAND = "firstperson_lefthand",
		GROUND = "ground",
		FIXED = "fixed",
		GUI = "gui";
	public MatrixModifier thirdPersonRightHand = new MatrixModifier(THIRD_PERSON_RIGHT_HAND);
	public MatrixModifier thirdPersonLeftHand = new MatrixModifier(THIRD_PERSON_LEFT_HAND);
	public MatrixModifier firstPersonRightHand = new MatrixModifier(FIRST_PERSON_RIGHT_HAND);
	public MatrixModifier firstPersonLeftHand = new MatrixModifier(FIRST_PERSON_LEFT_HAND);
	public MatrixModifier ground = new MatrixModifier(GROUND);
	public MatrixModifier fixed = new MatrixModifier(FIXED);
	public MatrixModifier gui = new MatrixModifier(GUI);
	protected LinkedHashMap<String, MatrixModifier> matrices;
	protected String parent;
	protected final ArrayList<String> layers = new ArrayList<>(0);
	
	public ItemModel(String parent) {
		this.parent = parent;
        this.matrices = new LinkedHashMap<>(0);
        this.matrices.put(THIRD_PERSON_RIGHT_HAND, this.thirdPersonRightHand);
        this.matrices.put(THIRD_PERSON_LEFT_HAND, this.thirdPersonLeftHand);
        this.matrices.put(FIRST_PERSON_RIGHT_HAND, this.firstPersonRightHand);
        this.matrices.put(FIRST_PERSON_LEFT_HAND, this.firstPersonLeftHand);
        this.matrices.put(GROUND, this.ground);
        this.matrices.put(FIXED, this.fixed);
        this.matrices.put(GUI, this.gui);
	}
	
	@SuppressWarnings("unchecked")
	public T layers(String... layerTextureDirs) {
		this.layers.addAll(Arrays.asList(layerTextureDirs));
		return (T)this;
	}
	
	public ArrayList<String> getLayers() {
		return this.layers;
	}
	
	public Map<String, MatrixModifier> getMatrices() {
		return this.matrices;
	}
	
	@SuppressWarnings("unchecked")
	public T setScaleMatrix(String id, float x, float y, float z) {
		getMatrices().get(id).scale(x, y, z);
		return (T)this;
	}
	
	@SuppressWarnings("unchecked")
	public T setRotationMatrix(String id, float x, float y, float z) {
		getMatrices().get(id).rotation(x, y, z);
		return (T)this;
	}
	
	@SuppressWarnings("unchecked")
	public T setTranslationMatrix(String id, float x, float y, float z) {
		getMatrices().get(id).translation(x, y, z);
		return (T)this;
	}
	
	public T setParent(String parent) {
		this.parent = parent;
		return (T)this;
	}
	
	public boolean isTemplate() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T bake() {
		JsonObject textures = new JsonObject();
		JsonObject display = new JsonObject();
		boolean[] validator = {false};

        this.jsonObj.add("parent", cString(this.parent));
		for (int i = 0; i < this.layers.size(); i++) {
			textures.add("layer"+i, cString(this.layers.get(i)));
		}
        this.matrices.forEach((s, matrixModifier) -> {
			if (matrixModifier.isModified()) {
				validator[0] = true;
				display.add(matrixModifier.getName(), matrixModifier.bake().decode());
			}
		});
		if (!this.layers.isEmpty()) this.jsonObj.add("textures", textures);
		if (validator[0]) this.jsonObj.add("display", display);
		return (T)this;
	}
}
